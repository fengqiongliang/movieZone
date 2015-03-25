package com.pxe.iscsi.target.connection.stage.fullfeature;


import java.io.IOException;
import java.security.DigestException;

import com.pxe.iscsi.exception.InternetSCSIException;
import com.pxe.iscsi.parser.BasicHeaderSegment;
import com.pxe.iscsi.parser.ProtocolDataUnit;
import com.pxe.iscsi.parser.logout.LogoutResponse;
import com.pxe.iscsi.target.connection.TargetPduFactory;
import com.pxe.iscsi.target.connection.phase.TargetFullFeaturePhase;
import com.pxe.iscsi.target.settings.SettingsException;


/**
 * A stage for processing logout requests.
 * <p>
 * Since <code>MaxConnections</code> is currently limited to <code>1</code>, all logout requests will be treated as
 * requests to close the session.
 * 
 * @author Andreas Ergenzinger
 */
public final class LogoutStage extends TargetFullFeatureStage {

    public LogoutStage (TargetFullFeaturePhase targetFullFeaturePhase) {
        super(targetFullFeaturePhase);
    }

    @Override
    public void execute (ProtocolDataUnit pdu) throws IOException , InterruptedException , InternetSCSIException , DigestException , SettingsException {

        final BasicHeaderSegment bhs = pdu.getBasicHeaderSegment();
        final int initiatorTaskTag = bhs.getInitiatorTaskTag();

        final ProtocolDataUnit responsePDU = TargetPduFactory.createLogoutResponsePdu(LogoutResponse.CONNECTION_CLOSED_SUCCESSFULLY, initiatorTaskTag, (short) settings.getDefaultTime2Wait(),// time2Wait
                (short) settings.getDefaultTime2Retain());// time2Retain

        connection.sendPdu(responsePDU);
    }

}
