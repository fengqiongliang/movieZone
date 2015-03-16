package com.pxe.dhcp;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static com.pxe.dhcp.DHCPConstants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DHCPServlet {
	private static final Logger logger = LoggerFactory.getLogger(DHCPServlet.class);
	
	 /**
     * Process DISCOVER request. response dhcp offer packet
     * 
     * @param request DHCP request received from client
     * @return DHCP response to send back, or <tt>null</tt> if no response.
	 * @throws Exception 
     */
    protected DHCPPacket doDiscover(DHCPPacket request,AssignStrategy strategy) throws Exception {
    	logger.debug("DISCOVER packet received");
    	AssignIP  allocatedIP = strategy.getIP(request.getDHCPSocket(), request.getClientMac());
    	byte[] ip                   = allocatedIP.getIp().getAddress();
    	byte[] submask         = allocatedIP.getSubmask().getAddress();
    	byte[] gateway          = allocatedIP.getGateway().getAddress();
    	byte[] nextServer       = request.getSocket().getLocalAddress().getAddress();
    	byte[] nameServer     = gateway;
    	String fileName          = allocatedIP.getFileName();
    	
    	//如果有yiaddr 地址则单播回去，否则广播回去
    	InetAddress broadIp    = InetAddress.getByName("255.255.255.255");
    	InetAddress unkownIp  = InetAddress.getByName("0.0.0.0");
    	InetAddress responseIp= null;
    	try{responseIp =  InetAddress.getByAddress(request.getCiaddr());}catch(Exception ex){}
    	if(responseIp==null || responseIp.equals(unkownIp))responseIp = broadIp;
    	
    	DHCPPacket offer = request;
    	offer.setRemote_ip(responseIp);   //如果有yiaddr 地址则单播回去，否则广播回去
    	offer.setOp(BOOTREPLY);
    	offer.setHops((byte)0);
    	offer.setSecs((short)0);
    	offer.setCiaddr(new byte[]{0,0,0,0});
    	offer.setYiaddr(ip); //ip
    	offer.setSiaddr(nextServer);  //next-server
    	offer.setFile(fileName);
    	
    	Map<Byte,DHCPOption> options = offer.getOptions();
    	DHCPOption seconds     = new DHCPOption(DHO_DHCP_LEASE_TIME,intToByteArray(1000));            //1000 seconds
    	DHCPOption messType   = new DHCPOption(DHO_DHCP_MESSAGE_TYPE,new byte[]{DHCPOFFER}); //dhcp offer message Type
    	DHCPOption serverID     = new DHCPOption(DHO_DHCP_SERVER_IDENTIFIER,request.getSocket().getLocalAddress().getAddress()); //dhcp server ip message Type
    	DHCPOption maskOp     = new DHCPOption(DHO_SUBNET_MASK,submask); //submask
    	DHCPOption gateOp      = new DHCPOption(DHO_ROUTERS,gateway);  //gateway
    	DHCPOption nameOp     = new DHCPOption(DHO_DOMAIN_NAME_SERVERS,nameServer);  //dns name
    	DHCPOption renewTimeOp = new DHCPOption(DHO_DHCP_RENEWAL_TIME,intToByteArray(500));  //renew next time
    	DHCPOption rebindTimeOp = new DHCPOption(DHO_DHCP_REBINDING_TIME,intToByteArray(525));  //rebind next time
    	
    	options.clear();
    	options.remove(DHO_DHCP_REQUESTED_ADDRESS);          //Requested IP address     MUST NOT
    	options.put(DHO_DHCP_LEASE_TIME, seconds);                 //IP address lease time      MUST
    	options.remove(DHO_DHCP_OPTION_OVERLOAD);            //Use 'file'/'sname' fields     MAY            here we don't indicate file/sname to carry dhcp option
    	options.put(DHO_DHCP_MESSAGE_TYPE, messType);         //DHCP message type        DHCPOFFER
    	options.remove(DHO_DHCP_PARAMETER_REQUEST_LIST);  //Parameter request list      MUST NOT
    	options.remove(DHO_DHCP_MESSAGE);                            //Message                        SHOULD        here we don't want for min packet
    	options.remove(DHO_DHCP_CLIENT_IDENTIFIER);              //Client identifier                MUST NOT
    	options.remove(DHO_VENDOR_CLASS_IDENTIFIER);           //Vendor class identifier      MAY            here we don't want
    	options.put(DHO_DHCP_SERVER_IDENTIFIER, serverID);      //Server identifier              MUST
    	options.remove(DHO_DHCP_MAX_MESSAGE_SIZE);            //Maximum message size   MUST NOT
    	//All others                MAY
    	options.put(DHO_SUBNET_MASK, maskOp);      
    	options.put(DHO_ROUTERS, gateOp);      
    	options.put(DHO_DOMAIN_NAME_SERVERS, nameOp);
    	options.put(DHO_DHCP_RENEWAL_TIME, renewTimeOp);
    	options.put(DHO_DHCP_REBINDING_TIME, rebindTimeOp);
        return offer;
    }

    /**
     * Process REQUEST request.
     * 
     * @param request DHCP request received from client
     * @return DHCP response to send back, or <tt>null</tt> if no response.
     */
    protected DHCPPacket doRequest(DHCPPacket request,AssignStrategy strategy) throws Exception {
        logger.debug("REQUEST packet received");
        //如果有server_identify，则确定是否与本机的ip相等，不相等则本dhcp server不是被选中的dhcp(为与同一个网内多个dhcp server 相兼容)
        if(request.getOptions().containsKey(DHO_DHCP_SERVER_IDENTIFIER)){
        	DHCPOption serverIdentify = request.getOptions().get(DHO_DHCP_SERVER_IDENTIFIER);
        	InetAddress localIp   = request.getSocket().getLocalAddress();
        	InetAddress serverIp =null;
        	try{serverIp =  InetAddress.getByAddress(serverIdentify.getValue());}catch(Exception ex){}
        	if(!localIp.equals(serverIp))return null;
        }
        AssignIP  allocatedIP = strategy.getIP(request.getDHCPSocket(), request.getClientMac());
    	byte[] ip                   = allocatedIP.getIp().getAddress();
    	byte[] submask         = allocatedIP.getSubmask().getAddress();
    	byte[] gateway          = allocatedIP.getGateway().getAddress();
    	byte[] nextServer       = request.getSocket().getLocalAddress().getAddress();
    	byte[] nameServer     = gateway;
    	String fileName          = allocatedIP.getFileName();
    	
    	//如果有yiaddr 地址则单播回去，否则广播回去
    	InetAddress broadIp    = InetAddress.getByName("255.255.255.255");
    	InetAddress unkownIp  = InetAddress.getByName("0.0.0.0");
    	InetAddress responseIp= null;
    	try{responseIp =  InetAddress.getByAddress(request.getCiaddr());}catch(Exception ex){}
    	if(responseIp==null || responseIp.equals(unkownIp))responseIp = broadIp;
    	DHCPPacket ack = request;
    	ack.setRemote_ip(responseIp);   //如果有yiaddr 地址则单播回去，否则广播回去
    	ack.setOp(BOOTREPLY);
    	ack.setHops((byte)0);
    	ack.setSecs((short)0);
    	ack.setCiaddr(new byte[]{0,0,0,0});
    	ack.setYiaddr(ip); //ip
    	ack.setSiaddr(nextServer);  //next-server
    	ack.setFile(fileName);
    	
    	Map<Byte,DHCPOption> options = ack.getOptions();
    	
    	DHCPOption seconds     = new DHCPOption(DHO_DHCP_LEASE_TIME,intToByteArray(1000));            //1000 seconds
    	DHCPOption messType   = new DHCPOption(DHO_DHCP_MESSAGE_TYPE,new byte[]{DHCPACK}); //dhcp ack message Type
    	DHCPOption serverID     = new DHCPOption(DHO_DHCP_SERVER_IDENTIFIER,request.getSocket().getLocalAddress().getAddress()); //dhcp server ip message Type
    	DHCPOption maskOp     = new DHCPOption(DHO_SUBNET_MASK,submask); //submask
    	DHCPOption gateOp      = new DHCPOption(DHO_ROUTERS,gateway);  //gateway
    	DHCPOption nameOp     = new DHCPOption(DHO_DOMAIN_NAME_SERVERS,nameServer);  //dns name
    	DHCPOption renewTimeOp = new DHCPOption(DHO_DHCP_RENEWAL_TIME,intToByteArray(500));  //renew next time
    	DHCPOption rebindTimeOp = new DHCPOption(DHO_DHCP_REBINDING_TIME,intToByteArray(525));  //rebind next time
    	
    	options.clear();
    	options.remove(DHO_DHCP_REQUESTED_ADDRESS);          //Requested IP address     MUST NOT
    	options.put(DHO_DHCP_LEASE_TIME, seconds);                 //IP address lease time      MUST (DHCPREQUEST) 
    																						 //                                      MUST NOT (DHCPINFORM)
    	options.remove(DHO_DHCP_OPTION_OVERLOAD);            //Use 'file'/'sname' fields     MAY            here we don't indicate file/sname to carry dhcp option
    	options.put(DHO_DHCP_MESSAGE_TYPE, messType);         //DHCP message type        DHCPOFFER
    	options.remove(DHO_DHCP_PARAMETER_REQUEST_LIST);  //Parameter request list      MUST NOT
    	options.remove(DHO_DHCP_MESSAGE);                            //Message                        SHOULD        here we don't want for min packet
    	options.remove(DHO_DHCP_CLIENT_IDENTIFIER);              //Client identifier                MUST NOT
    	options.remove(DHO_VENDOR_CLASS_IDENTIFIER);           //Vendor class identifier      MAY            here we don't want
    	options.put(DHO_DHCP_SERVER_IDENTIFIER, serverID);      //Server identifier              MUST
    	options.remove(DHO_DHCP_MAX_MESSAGE_SIZE);            //Maximum message size   MUST NOT
    	//All others                MAY
    	options.put(DHO_SUBNET_MASK, maskOp);      
    	options.put(DHO_ROUTERS, gateOp);      
    	options.put(DHO_DOMAIN_NAME_SERVERS, nameOp);
    	options.put(DHO_DHCP_RENEWAL_TIME, renewTimeOp);
    	options.put(DHO_DHCP_REBINDING_TIME, rebindTimeOp);
        return ack;
    }

    /**
     * Process INFORM request,send the dhcpack message with DHCPINFORM format  
     * 
     * @param request DHCP request received from client
     * @return DHCP response to send back, or <tt>null</tt> if no response.
     */
    protected DHCPPacket doInform(DHCPPacket request,AssignStrategy strategy) throws Exception  {
        logger.debug("INFORM packet received");
        return null;
    }

    /**
     * Process DECLINE request.
     * 
     * @param request DHCP request received from client
     * @return DHCP response to send back, or <tt>null</tt> if no response.
     */
    protected DHCPPacket doDecline(DHCPPacket request,AssignStrategy strategy) throws Exception  {
    	//do not need to respone decline message.this message use to mark the ip assign is unvalid
        logger.debug("DECLINE packet received");
        strategy.markInvalidIP(request.getDHCPSocket(), request.getClientMac());
        return null;
    }

    /**
     * Process RELEASE request.
     * 
     * @param request DHCP request received from client
     * @return DHCP response to send back, or <tt>null</tt> if no response.
     */
    protected DHCPPacket doRelease(DHCPPacket request,AssignStrategy strategy) throws Exception  {
    	//do not need to respone release message.
        logger.debug("RELEASE packet received");
        strategy.markValidIP(request.getDHCPSocket(), request.getClientMac());
        return null;
    }
    
    private byte[] intToByteArray(int i){
    	byte[] r = new byte[4];
    	r[0] = (byte) ((i & 0xff000000) >> 24);
    	r[1] = (byte) ((i & 0x00ff0000) >> 16);
    	r[2] = (byte) ((i & 0x0000ff00) >> 8);
    	r[3] = (byte) ((i & 0x000000ff) >> 0);
    	return r;
    }
    
}
