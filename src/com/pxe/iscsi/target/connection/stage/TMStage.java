package com.pxe.iscsi.target.connection.stage;


import java.io.IOException;
import java.security.DigestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pxe.iscsi.exception.InternetSCSIException;
import com.pxe.iscsi.parser.BasicHeaderSegment;
import com.pxe.iscsi.parser.ProtocolDataUnit;
import com.pxe.iscsi.parser.tmf.TaskManagementFunctionRequestParser;
import com.pxe.iscsi.parser.tmf.TaskManagementFunctionResponseParser;
import com.pxe.iscsi.parser.tmf.TaskManagementFunctionResponseParser.ResponseCode;
import com.pxe.iscsi.target.connection.TargetPduFactory;
import com.pxe.iscsi.target.connection.phase.TargetFullFeaturePhase;
import com.pxe.iscsi.target.connection.stage.fullfeature.TargetFullFeatureStage;
import com.pxe.iscsi.target.settings.SettingsException;


/**
 * A stage for processing Task Management Function Request defined in RFC(7320).
 * 
 * Warning, this class is only a dummy to react on the request without functionality except for response.
 * 
 * @author Andreas Rain
 */
public class TMStage extends TargetFullFeatureStage {

    private static final Logger LOGGER = LoggerFactory.getLogger(TMStage.class);

    /**
     * @param targetFullFeaturePhase
     */
    public TMStage (TargetFullFeaturePhase targetFullFeaturePhase) {
        super(targetFullFeaturePhase);
    }

    @Override
    public void execute (ProtocolDataUnit pdu) throws IOException , InterruptedException , InternetSCSIException , DigestException , SettingsException {

        final BasicHeaderSegment bhs = pdu.getBasicHeaderSegment();
        final TaskManagementFunctionRequestParser parser = (TaskManagementFunctionRequestParser) bhs.getParser();
        final int initiatorTaskTag = bhs.getInitiatorTaskTag();

        TaskManagementFunctionResponseParser.ResponseCode responseCode = ResponseCode.TASK_DOES_NOT_EXIST;

        switch (parser.getFunction()) {
            case ABORT_TASK :
                LOGGER.error("ABORT_TASK");
                break;
            case ABORT_TASK_SET :
                LOGGER.error("ABORT_TASK_SET");
                break;
            case CLEAR_ACA :
                responseCode = ResponseCode.FUNCTION_COMPLETE;
                break;
            case CLEAR_TASK_SET :
                responseCode = ResponseCode.FUNCTION_COMPLETE;
                break;
            case LUN_RESET :
                LOGGER.error("LUN_RESET");
                break;
            case TARGET_WARM_RESET :
                LOGGER.error("TARGET_WARM_RESET");
                break;
            case TARGET_COLD_RESET :
                LOGGER.error("TARGET_COLD_RESET");
                break;
            case TASK_REASSIGN :
                LOGGER.error("TASK_REASSIGN");
                break;
            default :
                break;
        }

        final ProtocolDataUnit responsePDU = TargetPduFactory.createTMResponsePdu(responseCode, initiatorTaskTag);
        connection.sendPdu(responsePDU);

    }

}
