package com.pxe.iscsi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map.Entry;














import com.moviezone.util.ByteUtil;
import com.pxe.iscsi.ENUM.CDBOpcode;
import com.pxe.iscsi.ENUM.Opcode;
import com.pxe.iscsi.cdb16.Inquiry;
import com.pxe.iscsi.cdb16.InquiryDeviceID;
import com.pxe.iscsi.cdb16.InquiryDeviceIDDescr;
import com.pxe.iscsi.cdb16.InquiryStandardData;
import com.pxe.iscsi.cdb16.InquirySupportedVPD;
import com.pxe.iscsi.cdb16.InquiryUnitSerialNum;
import com.pxe.iscsi.cdb16.LUN;
import com.pxe.iscsi.cdb16.ModeParam6;
import com.pxe.iscsi.cdb16.ModeSense6;
import com.pxe.iscsi.cdb16.Read10;
import com.pxe.iscsi.cdb16.ReadCapacity;
import com.pxe.iscsi.cdb16.ReadCapacityParam;
import com.pxe.iscsi.cdb16.ReportLUN;
import com.pxe.iscsi.cdb16.ReportLUNParam;
import com.pxe.iscsi.cdb16.Write10;
import com.pxe.iscsi.cdb16.Inquiry.PageCode;
import com.pxe.iscsi.cdb16.InquiryDeviceIDDescr.Association;
import com.pxe.iscsi.cdb16.InquiryDeviceIDDescr.CodeSet;
import com.pxe.iscsi.cdb16.InquiryDeviceIDDescr.IDType;
import com.pxe.iscsi.cdb16.InquiryDeviceIDDescr.ProtocolID;
import com.pxe.iscsi.cdb16.InquiryStandardData.PeripheralDeviceType;
import com.pxe.iscsi.cdb16.InquiryStandardData.PeripheralQualifer;
import com.pxe.iscsi.cdb16.InquiryStandardData.TPGS;
import com.pxe.iscsi.cdb16.InquiryStandardData.Version;
import com.pxe.iscsi.cdb16.ModeParam6.CachingParam;
import com.pxe.iscsi.cdb16.ModeParam6.InfoExceptionControl;
import com.pxe.iscsi.cdb16.ModeParam6.ModePage;
import com.pxe.iscsi.cdb16.ModeParam6.ModeParamHead;
import com.pxe.iscsi.cdb16.ModeParam6.ShortBlockDescr;
import com.pxe.iscsi.pdu.BasicHeaderSegment;
import com.pxe.iscsi.pdu.LoginRequest;
import com.pxe.iscsi.pdu.LogoutRequest;
import com.pxe.iscsi.pdu.LogoutResponse;
import com.pxe.iscsi.pdu.NopIn;
import com.pxe.iscsi.pdu.NopOut;
import com.pxe.iscsi.pdu.R2T;
import com.pxe.iscsi.pdu.SCSICommand;
import com.pxe.iscsi.pdu.SCSIDataIn;
import com.pxe.iscsi.pdu.SCSIDataOut;
import com.pxe.iscsi.pdu.SCSIResponse;
import com.pxe.iscsi.pdu.TMRequest;
import com.pxe.iscsi.pdu.TMResponse;
import com.pxe.iscsi.pdu.TextRequest;
import com.pxe.iscsi.pdu.TextResponse;
import com.pxe.iscsi.pdu.SCSIResponse.Response;
import com.pxe.iscsi.pdu.SCSIResponse.Status;

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
	private static StorageManager storage = new StorageManager();
	private int windowSize = 31;
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
		response.setParameter("TargetName", "iqn.2007-08.name.dns.target.my:iscsiboot3261");
		response.setParameter("TargetAddress", socket.getLocalSocketAddress().toString().replace("/", "")+",1");
		response.setStatSN(request.getExpStatSN());
		response.setExpCmdSN(request.getCmdSN());
		response.setMaxCmdSN(response.getExpCmdSN()+windowSize);
		System.out.println(response);
		os.write(response.toByte());
		//os.flush();
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
		response.setMaxCmdSN(response.getExpCmdSN()+1);
		response.setTime2Retain((short)2);
		System.out.println(response);
		os.write(response.toByte());
		os.close();
		socket.close();
	}

	public void tmManager(Socket socket, TMRequest tmRequest) throws Exception{
		OutputStream os = socket.getOutputStream();
		TMResponse response = new TMResponse();
		response.setInitiatorTaskTag(tmRequest.getInitiatorTaskTag());
		response.setStatSN(tmRequest.getExpStatSN());
		response.setExpCmdSN(tmRequest.getCmdSN());
		response.setMaxCmdSN(32);
		response.setResponse(TMResponse.Response.FunctionComplete);
		System.out.println(response);
		os.write(response.toByte());
		//os.flush();
		
	} 
	
	public void nopOut(Socket socket, NopOut nopRequest) throws Exception{
		if(nopRequest.getTargetTransferTag()==-1)throw new Exception("invalid NopOut Request ..."+nopRequest);
		if(nopRequest.getInitiatorTaskTag()==-1)return; //not need to response with nopin pdu
		OutputStream os = socket.getOutputStream();
		NopIn response = new NopIn();
		response.setInitiatorTaskTag(nopRequest.getInitiatorTaskTag());
		response.setTargetTransferTag(-1);
		response.setStatSN(nopRequest.getExpStatSN());
		response.setExpCmdSN(nopRequest.getCmdSN());
		response.setMaxCmdSN(response.getExpCmdSN()+windowSize);
		response.setPingData(nopRequest.getPingData());
		System.out.println(response);
		os.write(response.toByte());
	}
	
	public void scsiCommand(Socket socket, SCSICommand scsiCommand) throws Exception{
		System.out.println(socket.getRemoteSocketAddress()+" --> scsi command");
		byte[] CDB = scsiCommand.getCDB();
		if(CDB[0] == CDBOpcode.ReportLUN.value())SCSI_ReportLUN(socket,scsiCommand);
		if(CDB[0] == CDBOpcode.Inquiry.value())SCSI_Inquiry(socket,scsiCommand);
		if(CDB[0] == CDBOpcode.ReadCapacity10.value())SCSI_ReadCapacity(socket,scsiCommand);
		if(CDB[0] == CDBOpcode.Read10.value())SCSI_Read(socket,scsiCommand);
		if(CDB[0] == CDBOpcode.ModeSense6.value())SCSI_ModeSense(socket,scsiCommand);
		if(CDB[0] == CDBOpcode.TestUnitReady.value())SCSI_TestUnitReady(socket,scsiCommand);
		if(CDB[0] == CDBOpcode.Write10.value())SCSI_Write(socket,scsiCommand);
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
		dataIn.setMaxCmdSN(dataIn.getExpCmdSN()+windowSize);
		ReportLUNParam param = new ReportLUNParam();
		param.setLUN(new LUN(0));
		dataIn.setDataSegment(param.toByte());
		dataIn.setResidualCount(dataIn.getDataSegmentLength()-scsiCommand.getExpDataTransferLen());
		System.out.println(param);
		System.out.println(dataIn);
		os.write(dataIn.toByte());
		//os.flush();
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
		dataIn.setMaxCmdSN(dataIn.getExpCmdSN()+windowSize);
		dataIn.setLUN(scsiCommand.getLUN());
		byte[] dataSegment=new byte[0];
		boolean EVPD = command.getEVPD();
		
		if(!EVPD){
			//standard inquiry format
			InquiryStandardData param = new InquiryStandardData();
			param.setPeripheralQulifer(PeripheralQualifer.connected);
			param.setPeripheralType(PeripheralDeviceType.DirectBlockDevice);
			param.setVersion(Version.ANSI_INCITS_408_2005);
			param.setTPGS(TPGS.noAsyAccess);
			param.setMULTIP(true);
			param.setCMDQUE(true);
			param.setT10VendorID("ahoneBSD");
			param.setProductID("iSCSI DISK");
			param.setProductRevisionLevel("0001");
			//ByteUtil.toByte("001005025b00100246726565425344206953435349204449534b20202020202030303031");
			dataSegment = param.toByte();
			System.out.println(param);
		}
		if(EVPD && command.getPageCode() == PageCode.SupportedVPD){
			//support inquiry Device support
			InquirySupportedVPD param = new InquirySupportedVPD();
			param.setPeripheralQulifer(PeripheralQualifer.connected);
			param.setPeripheralType(PeripheralDeviceType.DirectBlockDevice);
			param.setSupportedPageList(PageCode.SupportedVPD);
			param.setSupportedPageList(PageCode.UnitSerialNumber);
			param.setSupportedPageList(PageCode.DeviceID);
			dataSegment = param.toByte();
			//dataSegment = ByteUtil.toByte("0000000900808385868788b0b1");
			
			
		}
		if(EVPD && command.getPageCode() == PageCode.UnitSerialNumber){
			//Device Identification 
			InquiryUnitSerialNum param = new InquiryUnitSerialNum();
			param.setPeripheralQulifer(PeripheralQualifer.connected);
			param.setPeripheralType(PeripheralDeviceType.DirectBlockDevice);
			param.setProductSerialNumber("NFSN00SZ331T0X");
			
			dataSegment = param.toByte();
			
		}
		if(EVPD && command.getPageCode() == PageCode.DeviceID){
			//Device Identification 
			InquiryDeviceID param = new InquiryDeviceID();
			param.setPeripheralQulifer(PeripheralQualifer.connected);
			param.setPeripheralType(PeripheralDeviceType.DirectBlockDevice);
			
			InquiryDeviceIDDescr descr1 = new InquiryDeviceIDDescr();
			descr1.setPIV(false);
			descr1.setProtocolID(ProtocolID.FibreChannel);
			descr1.setCodeSet(CodeSet.BinaryValue);
			descr1.setAssociation(Association.PhysicalOrLogicDevice);
			descr1.setIDType(IDType.FCNameID);
			descr1.setId("300000002a59be5d");
			
			InquiryDeviceIDDescr descr2 = new InquiryDeviceIDDescr();
			descr2.setPIV(false);
			descr2.setProtocolID(ProtocolID.FibreChannel);
			descr2.setCodeSet(CodeSet.UTF8);
			descr2.setAssociation(Association.PhysicalOrLogicDevice);
			descr2.setIDType(IDType.First8ByteVendorID);
			descr2.setId("ahoneBSD iSCSI DISK      NFSN00SZ331T0X");
			
			InquiryDeviceIDDescr descr3 = new InquiryDeviceIDDescr();
			descr3.setPIV(true);
			descr3.setProtocolID(ProtocolID.ISCSI);
			descr3.setCodeSet(CodeSet.UTF8);
			descr3.setAssociation(Association.SCSITarget);
			descr3.setIDType(IDType.ScsiNameString);
			descr3.setId("iqn.2007-08.name.dns.target.my:iscsiboot3261");
			
			InquiryDeviceIDDescr descr4 = new InquiryDeviceIDDescr();
			descr4.setPIV(true);
			descr4.setProtocolID(ProtocolID.ISCSI);
			descr4.setCodeSet(CodeSet.UTF8);
			descr4.setAssociation(Association.Port);
			descr4.setIDType(IDType.ScsiNameString);
			descr4.setId("iqn.2007-08.name.dns.target.my:iscsiboot3261,t,0x0001");
			
			InquiryDeviceIDDescr descr5 = new InquiryDeviceIDDescr();
			descr5.setPIV(true);
			descr5.setProtocolID(ProtocolID.ISCSI);
			descr5.setCodeSet(CodeSet.BinaryValue);
			descr5.setAssociation(Association.Port);
			descr5.setIDType(IDType.BinaryNumber);
			descr5.setId("00000001");
			
			InquiryDeviceIDDescr descr6 = new InquiryDeviceIDDescr();
			descr6.setPIV(true);
			descr6.setProtocolID(ProtocolID.ISCSI);
			descr6.setCodeSet(CodeSet.BinaryValue);
			descr6.setAssociation(Association.Port);
			descr6.setIDType(IDType.BinaryNumber2);
			descr6.setId("00000001");
			
			InquiryDeviceIDDescr descr7 = new InquiryDeviceIDDescr();
			descr7.setPIV(true);
			descr7.setProtocolID(ProtocolID.ISCSI);
			descr7.setCodeSet(CodeSet.BinaryValue);
			descr7.setAssociation(Association.Port);
			descr7.setIDType(IDType.BinaryNumber3);
			descr7.setId("00000001");
			
			param.setIDDescList(descr1);
			param.setIDDescList(descr2);
			param.setIDDescList(descr3);
			param.setIDDescList(descr4);
			param.setIDDescList(descr5);
			param.setIDDescList(descr6);
			param.setIDDescList(descr7);
			dataSegment = param.toByte();
			
		}
		
		
		dataIn.setDataSegment(dataSegment);
		dataIn.setResidualCount(dataIn.getDataSegmentLength()-scsiCommand.getExpDataTransferLen());
		byte[] data = dataIn.toByte();
		System.out.println(dataIn);
		os.write(data);
		//os.flush();
	}
	
	private void SCSI_ReadCapacity(Socket socket, SCSICommand scsiCommand) throws Exception{
		System.out.println(socket.getRemoteSocketAddress()+" --> scsi ReadCapacity 10");
		ReadCapacity command = new ReadCapacity(scsiCommand.getCDB());
		System.out.println(command);
		OutputStream os = socket.getOutputStream();
		SCSIDataIn dataIn = new SCSIDataIn();
		dataIn.setInitiatorTaskTag(scsiCommand.getInitiatorTaskTag());
		dataIn.setTargetTransferTag(-1);
		dataIn.setFinal(true);
		dataIn.setStatus(true);
		dataIn.setStatSN(scsiCommand.getExpStatSN());
		dataIn.setExpCmdSN(scsiCommand.getCmdSN()+1);
		dataIn.setMaxCmdSN(dataIn.getExpCmdSN()+windowSize);
		dataIn.setLUN(scsiCommand.getLUN());
		ReadCapacityParam param = new ReadCapacityParam();
		param.setReturnLBA(storage.readLBA());
		param.setBlockLength(StorageManager.BlockSize);
		dataIn.setDataSegment(param.toByte());
		dataIn.setResidualCount(dataIn.getDataSegmentLength()-scsiCommand.getExpDataTransferLen());
		System.out.println(dataIn);
		os.write(dataIn.toByte());
		//os.flush();
	}
	
	private void SCSI_Read(Socket socket, SCSICommand scsiCommand) throws Exception{
		System.out.println(socket.getRemoteSocketAddress()+" --> scsi Read 10");
		Read10 command = new Read10(scsiCommand.getCDB());
		if(command.getLBA()==4192256){
			System.out.println("problem here");
		}
		System.out.println(command);
		OutputStream os = socket.getOutputStream();
		
		boolean immediate = true;
		int byteSent = 0;
		int transferLength = command.getTransferLength()*StorageManager.BlockSize;
		int storageIndex   = command.getLBA()* StorageManager.BlockSize; 
		int maxReceivedLength = 262144;
		
		int DataSN = 0;
		while(byteSent<transferLength){
			int oneByteSentLength = Math.min(transferLength-byteSent, maxReceivedLength);
			byte[] dataSeg = new byte[oneByteSentLength]; 
			int readSize = storage.read(storageIndex+byteSent, dataSeg);
			
			SCSIDataIn dataIn = new SCSIDataIn();
			dataIn.setInitiatorTaskTag(scsiCommand.getInitiatorTaskTag());
			dataIn.setTargetTransferTag(-1);
			dataIn.setStatSN(scsiCommand.getExpStatSN());
			dataIn.setExpCmdSN(scsiCommand.getCmdSN()+1);
			dataIn.setMaxCmdSN(dataIn.getExpCmdSN()+windowSize);
			dataIn.setLUN(scsiCommand.getLUN());
			dataIn.setDataSegment(dataSeg);
			dataIn.setResidualCount(dataIn.getDataSegmentLength()-scsiCommand.getExpDataTransferLen());
			dataIn.setDataSN(DataSN++);
			dataIn.setBufferOffset(byteSent);
			byteSent+=dataSeg.length;
			if(byteSent>=transferLength){
				dataIn.setFinal(true);
				dataIn.setStatus(true);
			}
			System.out.println(dataIn);
			os.write(dataIn.toByte());
			scsiCommand.setCmdSN(scsiCommand.getCmdSN()+1);
			scsiCommand.setExpStatSN(scsiCommand.getExpStatSN()+1);
			//os.flush();
		}
		
		//need to send scsi response
		if(!immediate){
			SCSIResponse response = new SCSIResponse();
			response.setInitiatorTaskTag(scsiCommand.getInitiatorTaskTag());
			response.setStatSN(scsiCommand.getExpStatSN());
			response.setExpCmdSN(scsiCommand.getCmdSN()+1);
			response.setMaxCmdSN(response.getExpCmdSN()+windowSize);
			response.setResponse(Response.CommandComplete);
			response.setStatus(Status.GOOD);
			System.out.println(response);
		}
		
		
	}
	
	private void SCSI_Write(Socket socket, SCSICommand scsiCommand) throws Exception{
		System.out.println(socket.getRemoteSocketAddress()+" --> scsi Write 10");
		Write10 command = new Write10(scsiCommand.getCDB());
		System.out.println(command);
		InputStream is     = socket.getInputStream();
		OutputStream os = socket.getOutputStream();
		
		boolean immediate = true;
		boolean initialR2T = false;     //negotiation in login phase
		int byteReceived = 0;
		int transferLength = command.getTransferLength()*StorageManager.BlockSize;
		int storageIndex   = command.getLBA()* StorageManager.BlockSize; 
		
		//receive immediate data
		if(immediate && scsiCommand.getDataSegmentLength()>0) byteReceived += writeImmediate(storageIndex,scsiCommand.getDataSegment());
		
		//receive unsolited data
		if(!initialR2T && byteReceived < transferLength)byteReceived += writeUnsolitedData(storageIndex,socket,byteReceived);
		
		//receive solited data
		if(initialR2T && byteReceived < transferLength)writeSolitedData(storageIndex,socket,byteReceived,transferLength,scsiCommand);
		
		//send the status when complete
		SCSIResponse response = new SCSIResponse();
		response.setInitiatorTaskTag(scsiCommand.getInitiatorTaskTag());
		response.setStatSN(scsiCommand.getExpStatSN());
		response.setExpCmdSN(scsiCommand.getCmdSN()+1);
		response.setMaxCmdSN(response.getExpCmdSN()+windowSize);
		response.setResponse(Response.CommandComplete);
		response.setStatus(Status.GOOD);
		os.write(response.toByte());
		System.out.println(response);
		
	}

	private int writeImmediate(int storageIndex, byte[] dataSegment) throws IOException {
		System.out.println("receive  " + dataSegment.length + " immediate data . . .");
		storage.write(storageIndex, dataSegment);
		return dataSegment.length;
	}
	
	private int writeUnsolitedData(int storageIndex, Socket socket,int bytesReceived) throws Exception {
		System.out.println("receiving unsolited data . . . ");
		int firstBurstLength = 65536; //negotiation in login phase
		while(bytesReceived <= firstBurstLength){
			SCSIDataOut dataOut = readDataOut(socket);
			byte[] dataSeg = dataOut.getDataSegment();
			storage.write(storageIndex+dataOut.getBufferOffset(), dataSeg);
			System.out.println(dataOut);
			bytesReceived += dataSeg.length;
			System.out.println("wrote "+dataSeg.length+" bytes unsolited data");
			if(dataOut.getFinal())return bytesReceived;
		}
		System.out.println("reach the maximum unsolited data length bytesReceived : "+bytesReceived+" firstBurstLength : "+firstBurstLength);
		return bytesReceived;
	}
	
	private void writeSolitedData(int storageIndex, Socket socket,int bytesReceived,int transferLength,SCSICommand scsiCommand) throws Exception {
		System.out.println("receiving Solited data . . . ");
		int maxBurstLength = 262144;
		int R2TSN = 0;
		int desiredDataTransferLength = 0;
		while(bytesReceived < transferLength){
			//send the R2T PDU say ready to receive data
			desiredDataTransferLength = Math.min(maxBurstLength, transferLength - bytesReceived);
			R2T r2t = new R2T();
			r2t.setLUN(scsiCommand.getLUN());
			r2t.setInitiatorTaskTag(scsiCommand.getInitiatorTaskTag());
			r2t.setTargetTransferTag(1);
			r2t.setR2TSN(R2TSN++);
			r2t.setBufferOffset(bytesReceived);
			r2t.setDesiredDataLength(desiredDataTransferLength);
			r2t.setStatSN(scsiCommand.getExpStatSN());	
			r2t.setExpCmdSN(scsiCommand.getCmdSN()+1);
			r2t.setMaxCmdSN(r2t.getExpCmdSN()+windowSize);
			socket.getOutputStream().write(r2t.toByte());
			System.out.println(r2t);
			//receive every R2T Response(SCSIDataOut)
			int bytesReceivedThisCycle = 0;
			while(true){
				SCSIDataOut dataOut = readDataOut(socket);
				if(dataOut==null)return;
				System.out.println(dataOut);
				//save the current state and expcmd,maxcmd
				scsiCommand.setExpStatSN(dataOut.getExpStatSN());
				scsiCommand.setCmdSN(scsiCommand.getCmdSN()+1);
				byte[]  dataSeg = dataOut.getDataSegment();
				bytesReceivedThisCycle += dataSeg.length;
				storage.write(storageIndex+dataOut.getBufferOffset(), dataSeg);
				System.out.println("wrote "+dataSeg.length+" bytes solited data");
				if(dataOut.getFinal() || bytesReceivedThisCycle>=desiredDataTransferLength)break;
			}
			bytesReceived += bytesReceivedThisCycle;
		}
	}
	
	private SCSIDataOut readDataOut(Socket socket) throws Exception{
		InputStream is = socket.getInputStream();
		byte[] BHS = new byte[48];
		is.read(BHS);
		BasicHeaderSegment basicHead = new BasicHeaderSegment(BHS);
		byte[] AHS = new byte[basicHead.getTotalAHSLength()];
		byte[] DataSegment = new byte[basicHead.getDataSegmentLength()];
		is.read(AHS);
		is.read(DataSegment);
		byte[] dataSegmentPadding = new byte[DataSegment.length % 4== 0 ? 0:(4 - DataSegment.length % 4)];
		is.read(dataSegmentPadding);
		if(basicHead.getOpcode()==Opcode.SCSI_DATA_OUT)return new SCSIDataOut(BHS,DataSegment);
		if(basicHead.getOpcode()==Opcode.NOP_OUT)return null;
		throw new Exception("illegial PDU, opcode = 0x"+ByteUtil.toHex(new byte[]{basicHead.getOpcode().value()}));
	}
	
	
	
	private void SCSI_ModeSense(Socket socket, SCSICommand scsiCommand) throws Exception{
		System.out.println(socket.getRemoteSocketAddress()+" --> scsi ModeSense 6");
		ModeSense6 command = new ModeSense6(scsiCommand.getCDB());
		System.out.println(command);
		OutputStream os = socket.getOutputStream();
		SCSIDataIn dataIn = new SCSIDataIn();
		dataIn.setInitiatorTaskTag(scsiCommand.getInitiatorTaskTag());
		dataIn.setTargetTransferTag(-1);
		dataIn.setFinal(true);
		dataIn.setStatus(true);
		dataIn.setStatSN(scsiCommand.getExpStatSN());
		dataIn.setExpCmdSN(scsiCommand.getCmdSN()+1);
		dataIn.setMaxCmdSN(dataIn.getExpCmdSN()+windowSize);
		dataIn.setLUN(scsiCommand.getLUN());
		
		ModeParam6 param = new ModeParam6();
		/**----------------------- Mode Parameter Header  -----------------------------------------*/
		//param.getHeader().setModeDataLengh((byte)123); //donnot need to set here,this value will be set by ModeParam6.setDescript(),setModePage()
		param.getHeader().setWP(false); //write-protected
		
		
		/**----------------------- Block Descriptors --------------------------------------------------*/
		ShortBlockDescr descriptor = param.new ShortBlockDescr();
		descriptor.setNumOfLogicalBlock(storage.readNumBlock());  //一定要+1表示多少块,注意和LBA区别
		descriptor.setLogicalBlockLength(StorageManager.BlockSize);
		param.setDescriptors(descriptor);
		
		/**----------------------- Mode Page ----------------------------------------------------------*/
		//INFORMATIONAL_EXCEPTIONS_CONTROL_MODE_PAGE
		if(command.getPageCode()==0x1c && command.getSubPageCode()==0x00){
			InfoExceptionControl infoModePage = param.new InfoExceptionControl();
			infoModePage.setDEXCPT(true);  //disableExceptionControl
			param.setPages(infoModePage);
		}
		//CACHING_MODE_PAGE
		if(command.getPageCode()==0x08 && command.getSubPageCode()==0x00){
			CachingParam cachModePage = param.new CachingParam();
			cachModePage.setABPF(true); // abortPrefetch
			cachModePage.setSIZE(true); // sizeEnable
			cachModePage.setRCD(true); // readCacheDisable
			cachModePage.setMAXIMUM_PREFETCH((short)65535);
			cachModePage.setMAXIMUM_PREFETCH_CEILING((short)65535);
			cachModePage.setFSW(true);// forceSequentialWrite
			cachModePage.setNUMBER_OF_CACHE_SEGMENTS((byte)20);// numberOfCacheSegments
			cachModePage.setCACHE_SEGMENT_SIZE((short)0); // cacheSegmentSize
			param.setPages(cachModePage);
		}
		//RETURN_ALL_MODE_PAGES_ONLY
		if(command.getPageCode()==0x3f && command.getSubPageCode()==0x00){
			InfoExceptionControl infoModePage = param.new InfoExceptionControl();
			infoModePage.setDEXCPT(true);  //disableExceptionControl
			param.setPages(infoModePage);
			
			CachingParam cachModePage = param.new CachingParam();
			cachModePage.setABPF(true); // abortPrefetch
			cachModePage.setSIZE(true); // sizeEnable
			cachModePage.setRCD(true); // readCacheDisable
			cachModePage.setMAXIMUM_PREFETCH((short)65535);
			cachModePage.setMAXIMUM_PREFETCH_CEILING((short)65535);
			cachModePage.setFSW(true);// forceSequentialWrite
			cachModePage.setNUMBER_OF_CACHE_SEGMENTS((byte)20);// numberOfCacheSegments
			cachModePage.setCACHE_SEGMENT_SIZE((short)0); // cacheSegmentSize
			param.setPages(cachModePage);
		}
		
		
		dataIn.setDataSegment(param.toByte());
		dataIn.setResidualCount(dataIn.getDataSegmentLength()-scsiCommand.getExpDataTransferLen());
		System.out.println(dataIn);
		os.write(dataIn.toByte());
		//os.flush();
	}
	
	private void SCSI_TestUnitReady(Socket socket, SCSICommand scsiCommand) throws Exception{
		System.out.println(socket.getRemoteSocketAddress()+" --> scsi TestUnitReady ");
		OutputStream os = socket.getOutputStream();
		SCSIResponse response = new SCSIResponse();
		response.setInitiatorTaskTag(scsiCommand.getInitiatorTaskTag());
		response.setStatSN(scsiCommand.getExpStatSN());
		response.setExpCmdSN(scsiCommand.getCmdSN()+1);
		response.setMaxCmdSN(response.getExpCmdSN()+windowSize);
		response.setResponse(Response.CommandComplete);
		response.setStatus(Status.GOOD);
		System.out.println(response);
		os.write(response.toByte());
		//os.flush();
	}


	


	
	
	
}
