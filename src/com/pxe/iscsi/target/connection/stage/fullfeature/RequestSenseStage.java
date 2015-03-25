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
import com.pxe.iscsi.target.scsi.cdb.RequestSenseCdb;
import com.pxe.iscsi.target.scsi.sense.AdditionalSenseBytes;
import com.pxe.iscsi.target.scsi.sense.AdditionalSenseCodeAndQualifier;
import com.pxe.iscsi.target.scsi.sense.DescriptorFormatSenseData;
import com.pxe.iscsi.target.scsi.sense.ErrorType;
import com.pxe.iscsi.target.scsi.sense.FixedFormatSenseData;
import com.pxe.iscsi.target.scsi.sense.SenseData;
import com.pxe.iscsi.target.scsi.sense.SenseKey;
import com.pxe.iscsi.target.scsi.sense.information.FourByteInformation;
import com.pxe.iscsi.target.scsi.sense.senseDataDescriptor.SenseDataDescriptor;
import com.pxe.iscsi.target.scsi.sense.senseDataDescriptor.senseKeySpecific.FieldPointerSenseKeySpecificData;
import com.pxe.iscsi.target.settings.SettingsException;


/**
 * A stage for processing <code>REQUEST SENSE</code> SCSI commands.
 * <p>
 * The <code>REQUEST SENSE</code> command requests that the device server transfer {@link SenseData} to the application
 * client.
 * 
 * @author Andreas Ergenzinger
 */
public class RequestSenseStage extends TargetFullFeatureStage {

    public RequestSenseStage (TargetFullFeaturePhase targetFullFeaturePhase) {
        super(targetFullFeaturePhase);
    }

    @Override
    public void execute (ProtocolDataUnit pdu) throws IOException , InterruptedException , InternetSCSIException , DigestException , SettingsException {

        final BasicHeaderSegment bhs = pdu.getBasicHeaderSegment();
        final SCSICommandParser parser = (SCSICommandParser) bhs.getParser();

        ProtocolDataUnit responsePDU = null;// the response PDU

        // get command details in CDB
        final RequestSenseCdb cdb = new RequestSenseCdb(parser.getCDB());
        final FieldPointerSenseKeySpecificData[] illegalFieldPointers = cdb.getIllegalFieldPointers();

        if (illegalFieldPointers != null) {
            // an illegal request has been made

            SenseData senseData;

            if (cdb.getDescriptorFormat()) {
                // descriptor format sense data has been requested

                senseData = new DescriptorFormatSenseData(ErrorType.CURRENT,// errorType
                SenseKey.ILLEGAL_REQUEST,// sense key
                AdditionalSenseCodeAndQualifier.INVALID_FIELD_IN_CDB,// additional
                                                                     // sense
                                                                     // code
                                                                     // and
                                                                     // qualifier
                new SenseDataDescriptor[0]);// sense data descriptors

            } else {
                // fixed format sense data has been requested

                senseData = new FixedFormatSenseData(false,// valid
                ErrorType.CURRENT,// error type
                false,// file mark
                false,// end of medium
                false,// incorrect length indicator
                SenseKey.ILLEGAL_REQUEST,// sense key
                new FourByteInformation(),// information
                new FourByteInformation(),// command specific
                                          // information
                AdditionalSenseCodeAndQualifier.INVALID_FIELD_IN_CDB,// additional
                                                                     // sense
                                                                     // code
                                                                     // and
                                                                     // qualifier
                (byte) 0,// field replaceable unit code
                illegalFieldPointers[0],// sense key specific data, only
                                        // report first problem
                new AdditionalSenseBytes());// additional sense bytes
            }

            responsePDU = TargetPduFactory.createSCSIResponsePdu(false,// bidirectionalReadResidualOverflow
                    false,// bidirectionalReadResidualUnderflow
                    false,// residualOverflow
                    false,// residualUnderflow,
                    SCSIResponseParser.ServiceResponse.TARGET_FAILURE,// response,
                    SCSIStatus.CHECK_CONDITION,// status,
                    bhs.getInitiatorTaskTag(),// initiatorTaskTag,
                    0,// snackTag
                    0,// expectedDataSequenceNumber
                    0,// bidirectionalReadResidualCount
                    0,// residualCount
                    new ScsiResponseDataSegment(senseData, parser.getExpectedDataTransferLength()));// data
                                                                                                    // segment

        } else {
            /*
             * PDU is okay carry out command Sense data shall be available and cleared under the conditions defined in
             * SAM-3. If the device server has no other sense data available to return, it shall return the sense key
             * set to NO SENSE and the additional sense code set to NO ADDITIONAL SENSE INFORMATION. This will always be
             * the case with the jSCSI Target.
             */

            SenseData senseData;

            final SenseKey senseKey = SenseKey.NO_SENSE;
            final AdditionalSenseCodeAndQualifier additionalSense = AdditionalSenseCodeAndQualifier.NO_ADDITIONAL_SENSE_INFORMATION;

            if (cdb.getDescriptorFormat()) {
                // descriptor format sense data has been requested

                senseData = new DescriptorFormatSenseData(ErrorType.CURRENT,// errorType
                senseKey,// sense key
                additionalSense,// additional sense code and qualifier
                new SenseDataDescriptor[0]);// sense data descriptors

            } else {
                // fixed format sense data has been requested

                senseData = new FixedFormatSenseData(false,// valid
                ErrorType.CURRENT,// error type
                false,// file mark
                false,// end of medium
                false,// incorrect length indicator
                senseKey,// sense key
                new FourByteInformation(),// information
                new FourByteInformation(),// command specific
                                          // information
                additionalSense,// additional sense code and qualifier
                (byte) 0,// field replaceable unit code
                null,// sense key specific data, only report first
                     // problem
                null);// additional sense bytes
            }

            responsePDU = TargetPduFactory.createSCSIResponsePdu(false,// bidirectionalReadResidualOverflow
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
                    new ScsiResponseDataSegment(senseData, parser.getExpectedDataTransferLength()));// data
                                                                                                    // segment
        }

        // send response
        connection.sendPdu(responsePDU);
    }

}
