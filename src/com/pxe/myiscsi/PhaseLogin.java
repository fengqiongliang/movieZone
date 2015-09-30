package com.pxe.myiscsi;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Map.Entry;

/**
 <pre>
 5.3.  Login Phase

   The Login Phase establishes an iSCSI connection between an initiator
   and a target; it also creates a new session or associates the
   connection to an existing session.  The Login Phase sets the iSCSI
   protocol parameters, security parameters, and authenticates the
   initiator and target to each other.

   The Login Phase is only implemented via Login Request and Responses.
   The whole Login Phase is considered as a single task and has a single
   Initiator Task Tag (similar to the linked SCSI commands).

   The default MaxRecvDataSegmentLength is used during Login.

   The Login Phase sequence of requests and responses proceeds as
   follows:

      - Login initial request
      - Login partial response (optional)
      - More Login Requests and Responses (optional)
      - Login Final-Response (mandatory)

   The initial Login Request of any connection MUST include the
   InitiatorName key=value pair.  The initial Login Request of the first
   connection of a session MAY also include the SessionType key=value
   pair.  For any connection within a session whose type is not
   "Discovery", the first Login Request MUST also include the TargetName
   key=value pair.

   The Login Final-response accepts or rejects the Login Request.

   The Login Phase MAY include a SecurityNegotiation stage and a
   LoginOperationalNegotiation stage or both, but MUST include at least
   one of them.  The included stage MAY be empty except for the
   mandatory names.

   The Login Requests and Responses contain a field (CSG) that indicates
   the current negotiation stage (SecurityNegotiation or
   LoginOperationalNegotiation).  If both stages are used, the
   SecurityNegotiation MUST precede the LoginOperationalNegotiation.

   Some operational parameters can be negotiated outside the login
   through Text Requests and Responses.

   Security MUST be completely negotiated within the Login Phase.  The
   use of underlying IPsec security is specified in Chapter 8 and in
   [RFC3723].  iSCSI support for security within the protocol only
   consists of authentication in the Login Phase.

   In some environments, a target or an initiator is not interested in
   authenticating its counterpart.  It is possible to bypass
   authentication through the Login Request and Response.

   The initiator and target MAY want to negotiate iSCSI authentication
   parameters.  Once this negotiation is completed, the channel is
   considered secure.

   Most of the negotiation keys are only allowed in a specific stage.
   The SecurityNegotiation keys appear in Chapter 11 and the
   LoginOperationalNegotiation keys appear in Chapter 12.  Only a
   limited set of keys (marked as Any-Stage in Chapter 12) may be used
   in any of the two stages.

   Any given Login Request or Response belongs to a specific stage; this
   determines the negotiation keys allowed with the request or response.
   It is considered to be a protocol error to send a key that is not
   allowed in the current stage.

   Stage transition is performed through a command exchange (request/
   response) that carries the T bit and the same CSG code.  During this
   exchange, the next stage is selected by the target through the "next
   stage" code (NSG).  The selected NSG MUST NOT exceed the value stated
   by the initiator.  The initiator can request a transition whenever it
   is ready, but a target can only respond with a transition after one
   is proposed by the initiator.

   In a negotiation sequence, the T bit settings in one pair of Login
   Request-Responses have no bearing on the T bit settings of the next
   pair.  An initiator that has a T bit set to 1 in one pair and is
   answered with a T bit setting of 0, may issue the next request with
   the T bit set to 0.

   When a transition is requested by the initiator and acknowledged by
   the target, both the initiator and target switch to the selected
   stage.

   Targets MUST NOT submit parameters that require an additional
   initiator Login Request in a Login Response with the T bit set to 1.

   Stage transitions during login (including entering and exit) are only
   possible as outlined in the following table:

   +-----------------------------------------------------------+
   |From     To ->   | Security    | Operational | FullFeature |
   | |               |             |             |             |
   | V               |             |             |             |
   +-----------------------------------------------------------+
   | (start)         |  yes        |  yes        |  no         |
   +-----------------------------------------------------------+
   | Security        |  no         |  yes        |  yes        |
   +-----------------------------------------------------------+
   | Operational     |  no         |  no         |  yes        |
   +-----------------------------------------------------------+

   The Login Final-Response that accepts a Login Request can only come
   as a response to a Login Request with the T bit set to 1, and both
   the request and response MUST indicate FullFeaturePhase as the next
   phase via the NSG field.

   Neither the initiator nor the target should attempt to declare or
   negotiate a parameter more than once during login except for
   responses to specific keys that explicitly allow repeated key
   declarations (e.g., TargetAddress).  An attempt to
   renegotiate/redeclare parameters not specifically allowed MUST be
   detected by the initiator and target.  If such an attempt is detected

   by the target, the target MUST respond with Login reject (initiator
   error); if detected by the initiator, the initiator MUST drop the
   connection.

5.3.1.  Login Phase Start

   The Login Phase starts with a Login Request from the initiator to the
   target.  The initial Login Request includes:

      - Protocol version supported by the initiator.
      - iSCSI Initiator Name and iSCSI Target Name
      - ISID, TSIH, and connection Ids
      - Negotiation stage that the initiator is ready to enter.

   A login may create a new session or it may add a connection to an
   existing session.  Between a given iSCSI Initiator Node (selected
   only by an InitiatorName) and a given iSCSI target defined by an
   iSCSI TargetName and a Target Portal Group Tag, the login results are
   defined by the following table:


   +------------------------------------------------------------------+
   |ISID      | TSIH        | CID    |     Target action              |
   +------------------------------------------------------------------+
   |new       | non-zero    | any    |     fail the login             |
   |          |             |        |     ("session does not exist") |
   +------------------------------------------------------------------+
   |new       | zero        | any    |     instantiate a new session  |
   +------------------------------------------------------------------+
   |existing  | zero        | any    |     do session reinstatement   |
   |          |             |        |     (see section 5.3.5)        |
   +------------------------------------------------------------------+
   |existing  | non-zero    | new    |     add a new connection to    |
   |          | existing    |        |     the session                |
   +------------------------------------------------------------------+
   |existing  | non-zero    |existing|     do connection reinstatement|
   |          | existing    |        |    (see section 5.3.4)         |
   +------------------------------------------------------------------+
   |existing  | non-zero    | any    |         fail the login         |
   |          | new         |        |     ("session does not exist") |
   +------------------------------------------------------------------+

   Determination of "existing" or "new" are made by the target.

   Optionally, the Login Request may include:

      - Security parameters
      OR
      - iSCSI operational parameters
      AND/OR
      - The next negotiation stage that the initiator is ready to
      enter.

   The target can answer the login in the following ways:

     - Login Response with Login reject.  This is an immediate rejection
       from the target that causes the connection to terminate and the
       session to terminate if this is the first (or only) connection of
       a new session.  The T bit and the CSG and NSG fields are
       reserved.
     - Login Response with Login Accept as a final response (T bit set
       to 1 and the NSG in both request and response are set to
       FullFeaturePhase).  The response includes the protocol version
       supported by the target and the session ID, and may include iSCSI
       operational or security parameters (that depend on the current
       stage).
     - Login Response with Login Accept as a partial response (NSG not
       set to FullFeaturePhase in both request and response) that
       indicates the start of a negotiation sequence.  The response
       includes the protocol version supported by the target and either
       security or iSCSI parameters (when no security mechanism is
       chosen) supported by the target.

   If the initiator decides to forego the SecurityNegotiation stage, it
   issues the Login with the CSG set to LoginOperationalNegotiation and
   the target may reply with a Login Response that indicates that it is
   unwilling to accept the connection (see Section 10.13 Login Response)
   without SecurityNegotiation and will terminate the connection with a
   response of Authentication failure (see Section 10.13.5 Status-Class
   and Status-Detail).

   If the initiator is willing to negotiate iSCSI security, but is
   unwilling to make the initial parameter proposal and may accept a
   connection without iSCSI security, it issues the Login with the T bit
   set to 1, the CSG set to SecurityNegotiation, and the NSG set to
   LoginOperationalNegotiation.  If the target is also ready to skip
   security, the Login Response only contains the TargetPortalGroupTag
   key (see Section 12.9 TargetPortalGroupTag), the T bit set to 1, the
   CSG set to SecurityNegotiation, and the NSG set to
   LoginOperationalNegotiation.

   An initiator that chooses to operate without iSCSI security, with all
   the operational parameters taking the default values, issues the
   Login with the T bit set to 1, the CSG set to
   LoginOperationalNegotiation, and the NSG set to FullFeaturePhase.  If
   the target is also ready to forego security and can finish its
   LoginOperationalNegotiation, the Login Response has T bit set to 1,
   the CSG set to LoginOperationalNegotiation, and the NSG set to
   FullFeaturePhase in the next stage.

   During the Login Phase the iSCSI target MUST return the
   TargetPortalGroupTag key with the first Login Response PDU with which
   it is allowed to do so (i.e., the first Login Response issued after
   the first Login Request with the C bit set to 0) for all session
   types when TargetName is given and the response is not a redirection.
   The TargetPortalGroupTag key value indicates the iSCSI portal group
   servicing the Login Request PDU.  If the reconfiguration of iSCSI
   portal groups is a concern in a given environment, the iSCSI
   initiator should use this key to ascertain that it had indeed
   initiated the Login Phase with the intended target portal group.





 </pre>
 @author ahone 49163653@qq.com
 */
public class PhaseLogin {
	
	/**
	<pre>
	 Appendix C.  Login Phase Examples

   In the first example, the initiator and target authenticate each
   other via Kerberos:

     I-> Login (CSG,NSG=0,1 T=1)
         InitiatorName=iqn.1999-07.com.os:hostid.77
         TargetName=iqn.1999-07.com.example:diskarray.sn.88
         AuthMethod=KRB5,SRP,None

     T-> Login (CSG,NSG=0,0 T=0)
         AuthMethod=KRB5

     I-> Login (CSG,NSG=0,1 T=1)
         KRB_AP_REQ=<krb_ap_req>

     (krb_ap_req contains the Kerberos V5 ticket and authenticator
        with MUTUAL-REQUIRED set in the ap-options field)

     If the authentication is successful, the target proceeds with:

     T-> Login (CSG,NSG=0,1 T=1)
         KRB_AP_REP=<krb_ap_rep>

     (krb_ap_rep is the Kerberos V5 mutual authentication reply)

     If the authentication is successful, the initiator may proceed
        with:

     I-> Login (CSG,NSG=1,0 T=0) FirstBurstLength=8192
     T-> Login (CSG,NSG=1,0 T=0) FirstBurstLength=4096
          MaxBurstLength=8192
     I-> Login (CSG,NSG=1,0 T=0) MaxBurstLength=8192
         ... more iSCSI Operational Parameters

     T-> Login (CSG,NSG=1,0 T=0)
         ... more iSCSI Operational Parameters

     And at the end:

     I-> Login (CSG,NSG=1,3 T=1)
         optional iSCSI parameters

     T-> Login (CSG,NSG=1,3 T=1) "login accept"
     
     If the initiator's authentication by the target is not
          successful, the target responds with:

     T-> Login "login reject"

     instead of the Login KRB_AP_REP message, and terminates the
        connection.

     If the target's authentication by the initiator is not
       successful, the initiator terminates the connection (without
       responding to the Login KRB_AP_REP message).
	 </pre> 
	 */
	public void execute(Socket socket,PDULoginRequest request){
		try{
			PDUStageEnum stage = request.getCSG();
			if(stage == PDUStageEnum.SecurityNegotiation)SecurityNegotiation(socket,request);
			if(stage == PDUStageEnum.LoginOperationalNegotiation)LoginOperationalNegotiation(socket,request);
			if(stage == PDUStageEnum.FullFeaturePhase)FullFeaturePhase(socket,request);
		}catch(Exception ex){ex.printStackTrace();}
	}
	
	
	/**
	 <pre>
	 5.3.2.  iSCSI Security Negotiation

   The security exchange sets the security mechanism and authenticates
   the initiator user and the target to each other.  The exchange
   proceeds according to the authentication method chosen in the
   negotiation phase and is conducted using the Login Requests' and
   responses' key=value parameters.

   An initiator directed negotiation proceeds as follows:

     - The initiator sends a Login Request with an ordered list of the
       options it supports (authentication algorithm).  The options are
       listed in the initiator's order of preference.  The initiator MAY
       also send private or public extension options.

     - The target MUST reply with the first option in the list it
       supports and is allowed to use for the specific initiator unless
       it does not support any, in which case it MUST answer with
       "Reject" (see Section 5.2 Text Mode Negotiation).  The parameters
       are encoded in UTF8 as key=value.  For security parameters, see
       Chapter 11.

     - When the initiator considers that it is ready to conclude the
       SecurityNegotiation stage, it sets the T bit to 1 and the NSG to
       what it would like the next stage to be.  The target will then
       set the T bit to 1 and set the NSG to the next stage in the Login
       Response when it finishes sending its security keys.  The next
 	stage selected will be the one the target selected.  If the next
       stage is FullFeaturePhase, the target MUST respond with a Login
       Response with the TSIH value.

   If the security negotiation fails at the target, then the target MUST
   send the appropriate Login Response PDU.  If the security negotiation
   fails at the initiator, the initiator SHOULD close the connection.

   It should be noted that the negotiation might also be directed by the
   target if the initiator does support security, but is not ready to
   direct the negotiation (propose options).
	 </pre> 
	 */
	private void SecurityNegotiation(Socket socket,PDULoginRequest request) throws Exception{
		System.out.println(socket.getRemoteSocketAddress()+" --> SecurityNegotiation");
		PDULoginResponse response = new PDULoginResponse();
		OutputStream os = socket.getOutputStream();
		//do nothing just skip to login
		response.setCSG(request.getCSG());
		response.setNSG(PDUStageEnum.LoginOperationalNegotiation);
		response.setTransit(true);
		response.setInitiatorTaskTag(request.getInitiatorTaskTag());
		response.setVersionMax(request.getVersionMax());
		response.setVersionActive(request.getVersionMin());
		response.setTSIH((short)1);
		response.setISID(request.getISID());
		response.setStatSN(1);
		response.setExpCmdSN(1);
		response.setMaxCmdSN(1);
		response.setParameter("AuthMethod", "None");
		System.out.println(response);
		os.write(response.toByte());
		os.flush();
	}
	
	private void LoginOperationalNegotiation(Socket socket,PDULoginRequest request)  throws Exception{
		System.out.println(socket.getRemoteSocketAddress()+" --> LoginOperationalNegotiation");
		PDULoginResponse response = new PDULoginResponse();
		OutputStream os = socket.getOutputStream();
		//do nothing just skip to login
		response.setCSG(request.getCSG());
		response.setNSG(PDUStageEnum.FullFeaturePhase);
		response.setTransit(true);
		response.setInitiatorTaskTag(request.getInitiatorTaskTag());
		response.setVersionMax(request.getVersionMax());
		response.setVersionActive(request.getVersionMin());
		response.setTSIH((short)1);
		response.setISID(request.getISID());
		response.setStatSN(1);
		response.setExpCmdSN(1);
		response.setMaxCmdSN(1);
		response.setParameter("HeaderDigest", "None");
		response.setParameter("DataDigest", "None");
		response.setParameter("MaxRecvDataSegmentLength", request.getParameter("MaxRecvDataSegmentLength"));
		response.setParameter("DefaultTime2Wait", "0");
		response.setParameter("DefaultTime2Retain", request.getParameter("DefaultTime2Retain"));
		System.out.println(response);
		os.write(response.toByte());
		os.flush();
	}
	
	private void FullFeaturePhase(Socket socket, PDULoginRequest request)  throws Exception{
		System.out.println(socket.getRemoteSocketAddress()+" --> FullFeaturePhase");
		
	}
	
}
