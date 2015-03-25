package com.pxe.iscsi.target.connection.stage.fullfeature;


import java.io.IOException;
import java.security.DigestException;

import com.pxe.iscsi.exception.InternetSCSIException;
import com.pxe.iscsi.parser.BasicHeaderSegment;
import com.pxe.iscsi.parser.ProtocolDataUnit;
import com.pxe.iscsi.parser.scsi.SCSICommandParser;
import com.pxe.iscsi.parser.scsi.SCSIResponseParser;
import com.pxe.iscsi.parser.scsi.SCSIStatus;
import com.pxe.iscsi.target.connection.TargetPduFactory;
import com.pxe.iscsi.target.connection.phase.TargetFullFeaturePhase;
import com.pxe.iscsi.target.scsi.ScsiResponseDataSegment;
import com.pxe.iscsi.target.scsi.cdb.TestUnitReadyCdb;
import com.pxe.iscsi.target.scsi.sense.senseDataDescriptor.senseKeySpecific.FieldPointerSenseKeySpecificData;
import com.pxe.iscsi.target.settings.SettingsException;


/**
 * A stage for processing <code>TEST UNIT READY</code> SCSI commands.
 * 
 * @author Andreas Ergenzinger
 */
public class TestUnitReadyStage extends TargetFullFeatureStage {

    public TestUnitReadyStage (TargetFullFeaturePhase targetFullFeaturePhase) {
        super(targetFullFeaturePhase);
    }

    @Override
    public void execute (ProtocolDataUnit pdu) throws IOException , InterruptedException , InternetSCSIException , DigestException , SettingsException {

        final BasicHeaderSegment bhs = pdu.getBasicHeaderSegment();
        final SCSICommandParser parser = (SCSICommandParser) bhs.getParser();

        ProtocolDataUnit responsePdu;// the response PDU

        // get command details in CDB
        final TestUnitReadyCdb cdb = new TestUnitReadyCdb(parser.getCDB());
        final FieldPointerSenseKeySpecificData[] illegalFieldPointers = cdb.getIllegalFieldPointers();

        if (illegalFieldPointers != null) {
            // an illegal request has been made

            responsePdu = createFixedFormatErrorPdu(illegalFieldPointers,// senseKeySpecificData,
                    bhs.getInitiatorTaskTag(),// initiatorTaskTag,
                    parser.getExpectedDataTransferLength());// expDataTransferLength

        } else {
            // PDU is okay
            // carry out command
            // the logical unit is always ready
            responsePdu = TargetPduFactory.createSCSIResponsePdu(false,// bidirectionalReadResidualOverflow
                    false,// bidirectionalReadResidualUnderflow
                    false,// residualOverflow
                    false,// residualUnderflow,
                    SCSIResponseParser.ServiceResponse.COMMAND_COMPLETED_AT_TARGET,// response,
                    SCSIStatus.GOOD,// status,
                    bhs.getInitiatorTaskTag(),// initiatorTaskTag,
                    0,// snackTag
                    0,// expectedDataSequenceNumber
                    0,// bidirectionalReadResidualCount
                    0,// residualCount
                    ScsiResponseDataSegment.EMPTY_DATA_SEGMENT);// data
                                                                // segment
        }

        // send response
        connection.sendPdu(responsePdu);
    }

}
