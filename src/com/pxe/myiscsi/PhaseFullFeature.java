package com.pxe.myiscsi;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Map.Entry;

import com.pxe.myiscsi.ENUM.CDBOpcode;
import com.pxe.myiscsi.cdb16.Inquiry;
import com.pxe.myiscsi.cdb16.InquiryStandardData;
import com.pxe.myiscsi.cdb16.InquiryStandardData.PeripheralDeviceType;
import com.pxe.myiscsi.cdb16.InquiryStandardData.PeripheralQualifer;
import com.pxe.myiscsi.cdb16.InquiryStandardData.TPGS;
import com.pxe.myiscsi.cdb16.InquiryStandardData.Version;
import com.pxe.myiscsi.cdb16.LUN;
import com.pxe.myiscsi.cdb16.ReportLUN;
import com.pxe.myiscsi.cdb16.ReportLUNParam;
import com.pxe.myiscsi.pdu.LogoutRequest;
import com.pxe.myiscsi.pdu.LogoutResponse;
import com.pxe.myiscsi.pdu.SCSICommand;
import com.pxe.myiscsi.pdu.SCSIDataIn;
import com.pxe.myiscsi.pdu.TextRequest;
import com.pxe.myiscsi.pdu.TextResponse;

/**
 <pre>

3.2.4.  iSCSI Full Feature Phase

   Once the initiator is authorized to do so, the iSCSI session is in
   the iSCSI Full Feature Phase.  A session is in Full Feature Phase
   after successfully finishing the Login Phase on the first (leading)
   connection of a session.  A connection is in Full Feature Phase if
   the session is in Full Feature Phase and the connection login has
   completed successfully.  An iSCSI connection is not in Full Feature
   Phase

      a) when it does not have an established transport connection,

         OR

      b) when it has a valid transport connection, but a successful
         login was not performed or the connection is currently logged
         out.

   In a normal Full Feature Phase, the initiator may send SCSI commands
   and data to the various LUs on the target by encapsulating them in
   iSCSI PDUs that go over the established iSCSI session.


 </pre>
 @author ahone 49163653@qq.com
 */
public class PhaseFullFeature {
	
	
	/**
	 <pre>
	 Appendix D.  SendTargets Operation

   To reduce the amount of configuration required on an initiator, iSCSI
   provides the SendTargets text request.  The initiator uses the
   SendTargets request to get a list of targets to which it may have
   access, as well as the list of addresses (IP address and TCP port) on
   which these targets may be accessed.

   To make use of SendTargets, an initiator must first establish one of
   two types of sessions.  If the initiator establishes the session
   using the key "SessionType=Discovery", the session is a discovery
   session, and a target name does not need to be specified.  Otherwise,
   the session is a normal, operational session.  The SendTargets
   command MUST only be sent during the Full Feature Phase of a normal
   or discovery session.

   A system that contains targets MUST support discovery sessions on
   each of its iSCSI IP address-port pairs, and MUST support the
   SendTargets command on the discovery session.  In a discovery
   session, a target MUST return all path information (target name and
   IP address-port pairs and portal group tags) for the targets on the
   target network entity which the requesting initiator is authorized to
   access.

   A target MUST support the SendTargets command on operational
   sessions; these will only return path information about the target to
   which the session is connected, and do not need to return information
   about other target names that may be defined in the responding
   system.

   An initiator MAY make use of the SendTargets as it sees fit.

   A SendTargets command consists of a single Text request PDU.  This
   PDU contains exactly one text key and value.  The text key MUST be
   SendTargets.  The expected response depends upon the value, as well
   as whether the session is a discovery or operational session.

   The value must be one of:

     All

     The initiator is requesting that information on all relevant
       targets known to the implementation be returned.  This value
       MUST be supported on a discovery session, and MUST NOT be
       supported on an operational session.

     <iSCSI-target-name>

     If an iSCSI target name is specified, the session should respond
      with addresses for only the named target, if possible.  This
      value MUST be supported on discovery sessions.  A discovery
      session MUST be capable of returning addresses for those
      targets that would have been returned had value=All had been
      designated.

     <nothing>

     The session should only respond with addresses for the target to
       which the session is logged in.  This MUST be supported on
       operational sessions, and MUST NOT return targets other than
       the one to which the session is logged in.

   The response to this command is a text response that contains a list
   of zero or more targets and, optionally, their addresses.  Each
   target is returned as a target record.  A target record begins with
   the TargetName text key, followed by a list of TargetAddress text
   keys, and bounded by the end of the text response or the next
   TargetName key, which begins a new record.  No text keys other than
   TargetName and TargetAddress are permitted within a SendTargets
   response.

   For the format of the TargetName, see Section 12.4 TargetName.

   In a discovery session, a target MAY respond to a SendTargets request
   with its complete list of targets, or with a list of targets that is
   based on the name of the initiator logged in to the session.

   A SendTargets response MUST NOT contain target names if there are no
   targets for the requesting initiator to access.

   Each target record returned includes zero or more TargetAddress
   fields.

   Each target record starts with one text key of the form:

     TargetName=<target-name-goes-here>

   Followed by zero or more address keys of the form:

     TargetAddress=<hostname-or-ipaddress>[:<tcp-port>],
       <portal-group-tag>

   The hostname-or-ipaddress contains a domain name, IPv4 address, or
   IPv6 address, as specified for the TargetAddress key.

   A hostname-or-ipaddress duplicated in TargetAddress responses for a
   given node (the port is absent or equal) would probably indicate that
   multiple address families are in use at once (IPV6 and IPV4).

   Each TargetAddress belongs to a portal group, identified by its
   numeric portal group tag (as in Section 12.9 TargetPortalGroupTag).
   The iSCSI target name, together with this tag, constitutes the SCSI
   port identifier; the tag only needs to be unique within a given
   target's name list of addresses.

   Multiple-connection sessions can span iSCSI addresses that belong to
   the same portal group.

   Multiple-connection sessions cannot span iSCSI addresses that belong
   to different portal groups.

   If a SendTargets response reports an iSCSI address for a target, it
   SHOULD also report all other addresses in its portal group in the
   same response.

   A SendTargets text response can be longer than a single Text Response
   PDU, and makes use of the long text responses as specified.

   After obtaining a list of targets from the discovery target session,
   an iSCSI initiator may initiate new sessions to log in to the
   discovered targets for full operation.  The initiator MAY keep the
   discovery session open, and MAY send subsequent SendTargets commands
   to discover new targets.

   Examples:

   This example is the SendTargets response from a single target that
   has no other interface ports.

   Initiator sends text request that contains:

         SendTargets=All

   Target sends a text response that contains:

         TargetName=iqn.1993-11.com.example:diskarray.sn.8675309

   All the target had to return in the simple case was the target name.
   It is assumed by the initiator that the IP address and TCP port for
   this target are the same as used on the current connection to the
   default iSCSI target.

   The next example has two internal iSCSI targets, each accessible via
   two different ports with different IP addresses.  The following is
   the text response:

      TargetName=iqn.1993-11.com.example:diskarray.sn.8675309
      TargetAddress=10.1.0.45:3000,1 TargetAddress=10.1.1.45:3000,2
      TargetName=iqn.1993-11.com.example:diskarray.sn.1234567
      TargetAddress=10.1.0.45:3000,1 TargetAddress=10.1.1.45:3000,2

   Both targets share both addresses; the multiple addresses are likely
   used to provide multi-path support.  The initiator may connect to
   either target name on either address.  Each of the addresses has its
   own portal group tag; they do not support spanning
   multiple-connection sessions with each other.  Keep in mind that the
   portal group tags for the two named targets are independent of one
   another; portal group "1" on the first target is not necessarily the
   same as portal group "1" on the second target.

   In the above example, a DNS host name or an IPv6 address could have
   been returned instead of an IPv4 address.

   The next text response shows a target that supports spanning sessions
   across multiple addresses, and further illustrates the use of the
   portal group tags:

       TargetName=iqn.1993-11.com.example:diskarray.sn.8675309

      TargetAddress=10.1.0.45:3000,1 TargetAddress=10.1.1.46:3000,1
      TargetAddress=10.1.0.47:3000,2 TargetAddress=10.1.1.48:3000,2
      TargetAddress=10.1.1.49:3000,3

   In this example, any of the target addresses can be used to reach the
   same target.  A single-connection session can be established to any
   of these TCP addresses.  A multiple-connection session could span
   addresses .45 and .46 or .47 and .48, but cannot span any other
   combination.  A TargetAddress with its own tag (.49) cannot be
   combined with any other address within the same session.

   This SendTargets response does not indicate whether .49 supports
   multiple connections per session; it is communicated via the
   MaxConnections text key upon login to the target.


	 </pre> 
	 */
	public void discovery(Socket socket,TextRequest request) throws Exception{
		if(!"All".equalsIgnoreCase(request.getParameter("SendTargets")))return;
		System.out.println(socket.getRemoteSocketAddress()+" --> discovery[SendTargets=All]");
		TextResponse response = new TextResponse();
		OutputStream os = socket.getOutputStream();
		response.setFinal(true);
		response.setContinue(false);
		response.setInitiatorTaskTag(request.getInitiatorTaskTag());
		response.setTargetTransferTag(-1);
		response.setParameter("TargetName", "iqn.2007-08.name.dns.target.my:iscsiboot");
		response.setParameter("TargetAddress", socket.getLocalSocketAddress().toString().replace("/", "")+",1");
		response.setStatSN(request.getExpStatSN());
		response.setExpCmdSN(request.getCmdSN()+1);
		response.setMaxCmdSN(response.getExpCmdSN());
		System.out.println(response);
		os.write(response.toByte());
		os.flush();
	}
	
	
	/**
	 <pre>
	
3.5.3.3.  Logout Request and Response

   Logout Requests and Responses are used for the orderly closing of
   connections for recovery or maintenance.  The logout request may be
   issued following a target prompt (through an asynchronous message) or
   at an initiators initiative.  When issued on the connection to be
   logged out, no other request may follow it.

   The Logout Response indicates that the connection or session cleanup
   is completed and no other responses will arrive on the connection (if
   received on the logging out connection).  In addition, the Logout
   Response indicates how long the target will continue to hold
   resources for recovery (e.g., command execution that continues on a
   new connection) in the text key Time2Retain and how long the
   initiator must wait before proceeding with recovery in the text key
   Time2Wait.

	 </pre> 
	 */
	public void logout(Socket socket,LogoutRequest request) throws Exception{
		System.out.println(socket.getRemoteSocketAddress()+" --> logout");
		LogoutResponse response = new LogoutResponse();
		OutputStream os = socket.getOutputStream();
		response.setInitiatorTaskTag(request.getInitiatorTaskTag());
		response.setResponse(LogoutResponse.Response.SUCCESS);
		response.setStatSN(request.getExpStatSN());
		response.setExpCmdSN(request.getCmdSN());
		response.setMaxCmdSN(response.getExpCmdSN());
		response.setTime2Retain((short)2);
		System.out.println(response);
		os.write(response.toByte());
		os.close();
		socket.close();
	}


	public void scsiCommand(Socket socket, SCSICommand scsiCommand) throws Exception{
		System.out.println(socket.getRemoteSocketAddress()+" --> scsi command");
		byte[] CDB = scsiCommand.getCDB();
		if(CDB[0] == CDBOpcode.ReportLUN.value())SCSI_ReportLUN(socket,scsiCommand);
		if(CDB[0] == CDBOpcode.Inquiry.value())SCSI_Inquiry(socket,scsiCommand);
		
	}
	
	private void SCSI_ReportLUN(Socket socket, SCSICommand scsiCommand) throws Exception{
		System.out.println(socket.getRemoteSocketAddress()+" --> scsi report luns");
		ReportLUN command = new ReportLUN(scsiCommand.getCDB());
		System.out.println(command);
		OutputStream os = socket.getOutputStream();
		SCSIDataIn dataIn = new SCSIDataIn();
		dataIn.setInitiatorTaskTag(scsiCommand.getInitiatorTaskTag());
		dataIn.setTargetTransferTag(-1);
		dataIn.setFinal(true);
		dataIn.setStatus(true);
		dataIn.setStatSN(scsiCommand.getExpStatSN());
		dataIn.setExpCmdSN(scsiCommand.getCmdSN()+1);
		dataIn.setMaxCmdSN(dataIn.getExpCmdSN());
		ReportLUNParam param = new ReportLUNParam();
		param.setLUN(new LUN(0));
		dataIn.setDataSegment(param.toByte());
		os.write(dataIn.toByte());
		os.flush();
	}
	
	private void SCSI_Inquiry(Socket socket, SCSICommand scsiCommand) throws Exception{
		System.out.println(socket.getRemoteSocketAddress()+" --> scsi inquiry");
		Inquiry command = new Inquiry(scsiCommand.getCDB());
		System.out.println(command);
		OutputStream os = socket.getOutputStream();
		SCSIDataIn dataIn = new SCSIDataIn();
		dataIn.setInitiatorTaskTag(scsiCommand.getInitiatorTaskTag());
		dataIn.setTargetTransferTag(-1);
		dataIn.setFinal(true);
		dataIn.setStatus(true);
		dataIn.setStatSN(scsiCommand.getExpStatSN());
		dataIn.setExpCmdSN(scsiCommand.getCmdSN()+1);
		dataIn.setMaxCmdSN(dataIn.getExpCmdSN());
		dataIn.setLUN(scsiCommand.getLUN());
		byte[] dataSegment=new byte[0];
		
		if(!command.getEVPD()){
			//standard inquiry format
			InquiryStandardData param = new InquiryStandardData();
			param.setPeripheralQulifer(PeripheralQualifer.connected);
			param.setPeripheralType(PeripheralDeviceType.DirectBlockDevice);
			param.setVersion(Version.ANSI_INCITS_408_2005);
			param.setTPGS(TPGS.noAsyAccess);
			param.setT10VendorID("disyUKon");
			param.setProductID("ahone SCSI");
			param.setProductRevisionLevel("1.00");
			dataSegment = param.toByte();
		}else{
			//vital product data
			
			
		}
		
		dataIn.setDataSegment(dataSegment);
		os.write(dataIn.toByte());
		os.flush();
	}
	
}
