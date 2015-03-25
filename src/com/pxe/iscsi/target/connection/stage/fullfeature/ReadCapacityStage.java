package com.pxe.iscsi.target.connection.stage.fullfeature;


import static com.pxe.iscsi.target.storage.IStorageModule.VIRTUAL_BLOCK_SIZE;

import java.io.IOException;
import java.security.DigestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pxe.iscsi.exception.InternetSCSIException;
import com.pxe.iscsi.parser.BasicHeaderSegment;
import com.pxe.iscsi.parser.ProtocolDataUnit;
import com.pxe.iscsi.parser.scsi.SCSICommandParser;
import com.pxe.iscsi.target.connection.phase.TargetFullFeaturePhase;
import com.pxe.iscsi.target.scsi.cdb.ReadCapacity10Cdb;
import com.pxe.iscsi.target.scsi.cdb.ReadCapacity16Cdb;
import com.pxe.iscsi.target.scsi.cdb.ReadCapacityCdb;
import com.pxe.iscsi.target.scsi.cdb.ScsiOperationCode;
import com.pxe.iscsi.target.scsi.readCapacity.ReadCapacity10ParameterData;
import com.pxe.iscsi.target.scsi.readCapacity.ReadCapacity16ParameterData;
import com.pxe.iscsi.target.scsi.readCapacity.ReadCapacityParameterData;
import com.pxe.iscsi.target.scsi.sense.AdditionalSenseCodeAndQualifier;
import com.pxe.iscsi.target.scsi.sense.senseDataDescriptor.senseKeySpecific.FieldPointerSenseKeySpecificData;
import com.pxe.iscsi.target.settings.SettingsException;


public final class ReadCapacityStage extends TargetFullFeatureStage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadCapacityStage.class);

    public ReadCapacityStage (final TargetFullFeaturePhase targetFullFeaturePhase) {
        super(targetFullFeaturePhase);
    }

    @Override
    public void execute (ProtocolDataUnit pdu) throws IOException , InterruptedException , InternetSCSIException , DigestException , SettingsException {

        // find out the type of READ CAPACITY command ((10) or (16))
        final BasicHeaderSegment bhs = pdu.getBasicHeaderSegment();
        final SCSICommandParser parser = (SCSICommandParser) bhs.getParser();
        final ScsiOperationCode opCode = ScsiOperationCode.valueOf(parser.getCDB().get(0));
        ReadCapacityCdb cdb;
        if (opCode == ScsiOperationCode.READ_CAPACITY_10)
            cdb = new ReadCapacity10Cdb(parser.getCDB());
        else if (opCode == ScsiOperationCode.READ_CAPACITY_16)
            cdb = new ReadCapacity16Cdb(parser.getCDB());
        else {
            // programmer error, we should not be here, close the connection
            throw new InternetSCSIException("wrong SCSI Operation Code " + opCode + " in ReadCapacityStage");
        }

        /*
         * Everything is fine, carry on. The PMI bit of the command descriptor block is ignored, since there is no way
         * to know if "substantial vendor specific delay in data transfer may be encountered" after the address in the
         * LOGICAL BLOCK ADDRESS field. Therefore we always try to return the whole length of the storage medium.
         */

        // make sure that the LOGICAL BLOCK ADDRESS field is valid and send
        // appropriate response
        if (session.getStorageModule().checkBounds(cdb.getLogicalBlockAddress(), 0) != 0) {
            // invalid, log error, send error PDU, and return
            LOGGER.error("encountered " + cdb.getClass() + " in ReadCapacityStage with " + "LOGICAL BLOCK ADDRESS = " + cdb.getLogicalBlockAddress());

            final FieldPointerSenseKeySpecificData fp = new FieldPointerSenseKeySpecificData(true,// senseKeySpecificDataValid
            true,// commandData (i.e. invalid field in CDB)
            false,// bitPointerValid
            0,// bitPointer, reserved since invalid
            0);// fieldPointer to the SCSI OpCode field
            final FieldPointerSenseKeySpecificData[] fpArray = new FieldPointerSenseKeySpecificData[] { fp };
            final ProtocolDataUnit responsePdu = createFixedFormatErrorPdu(fpArray,// senseKeySpecificData
                    AdditionalSenseCodeAndQualifier.LOGICAL_BLOCK_ADDRESS_OUT_OF_RANGE,// additionalSenseCodeAndQualifier
                    bhs.getInitiatorTaskTag(),// initiatorTaskTag
                    parser.getExpectedDataTransferLength());// expectedDataTransferLength
            connection.sendPdu(responsePdu);
            return;
        } else {
            // send PDU with requested READ CAPACITY parameter data
            ReadCapacityParameterData parameterData;
            if (cdb instanceof ReadCapacity10Cdb)
                parameterData = new ReadCapacity10ParameterData(session.getStorageModule().getSizeInBlocks(),// returnedLogicalBlockAddress
                VIRTUAL_BLOCK_SIZE);// logicalBlockLengthInBytes
            else
                parameterData = new ReadCapacity16ParameterData(session.getStorageModule().getSizeInBlocks(),// returnedLogicalBlockAddress
                VIRTUAL_BLOCK_SIZE);// logicalBlockLengthInBytes

            sendResponse(bhs.getInitiatorTaskTag(),// initiatorTaskTag,
                    parser.getExpectedDataTransferLength(),// expectedDataTransferLength,
                    parameterData);// responseData
        }
    }

}
