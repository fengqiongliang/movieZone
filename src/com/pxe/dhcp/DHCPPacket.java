package com.pxe.dhcp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;








import com.moviezone.util.SecurityUtil;

/**
 * The basic class for manipulating DHCP packets.
 * 
 * @author ahone
 *
 * <p>There are two basic ways to build a new DHCPPacket object.
 * <p>First one is to build an object from scratch using the constructor and setters.
 * If you need to set repeatedly the same set of parameters and options,
 * you can create a "master" object and clone it many times.
 * 
 * <pre>
 * DHCPPacket discover = new DHCPPacket();
 * discover.setOp(DHCPPacket.BOOTREQUEST);
 * discover.setHtype(DHCPPacket.HTYPE_ETHER);
 * discover.setHlen((byte) 6);
 * discover.setHops((byte) 0);
 * discover.setXid( (new Random()).nextInt() );
 * ...
 * </pre>
 * Second is to decode a DHCP datagram received from the network.
 * In this case, the object is created through a factory.
 * 
 * <p>Example: simple DHCP sniffer
 * <pre>
 * DHCPSocket socket = new DHCPSocket(67);
 * while (true) {
 *     DatagramPacket pac = new DatagramPacket(new byte[1500], 1500);
 *     socket.receive(pac);
 *     DHCPPacket dhcp = DHCPPacket.getPacket(pac);
 *     System.out.println(dhcp.toString());
 * }
 * </pre>
 * In this second way, beware that a <tt>BadPacketExpcetion</tt> is thrown
 * if the datagram contains invalid DHCP data.
 * 
 * 
 * <p><b>Getters and Setters</b>: methods are provided with high-level data structures
 * wherever it is possible (String, InetAddress...). However there are also low-overhead
 * version (suffix <tt>Raw</tt>) dealing directly with <tt>byte[]</tt> for maximum performance.
 * They are useful in servers for copying parameters in a servers from a request to a response without
 * any type conversion. All parameters are copies, you may modify them as you like without
 * any side-effect on the <tt>DHCPPacket</tt> object.
 * 
 * <h4>DHCP datagram format description:</h4>
 * <blockquote><table cellspacing=2>
 * 	<tr><th>Field</th><th>Octets</th><th>Description</th></tr>
 * 	<tr><td valign=top><tt>op</tt></td><td valign=top>1</td>
 * 						<td>Message op code / message type.<br>
 * 						use constants
 * 						<tt>BOOTREQUEST</tt>,
 * 						<tt>BOOTREPLY</tt></td></tr>
 * 	<tr><td valign=top><tt>htype</tt></td>
 * 						<td valign=top>1</td><td>Hardware address type, see ARP section in
 * 						"Assigned Numbers" RFC<br>
 * 						use constants
 * 						<tt>HTYPE_ETHER</tt>,
 * 						<tt>HTYPE_IEEE802</tt>,
 * 						<tt>HTYPE_FDDI</tt></td></tr>
 * 	<tr><td valign=top><tt>hlen</tt></td><td>1</td><td>Hardware address length 
 * 						(e.g.  '6' for ethernet).</td></tr>
 * 	<tr><td valign=top><tt>hops</tt></td><td valign=top>1</td><td>Client sets to zero, optionally used 
 * 						by relay agents when booting via a relay agent.</td></tr>
 * 	<tr><td valign=top><tt>xid</tt></td><td valign=top>4</td>
 * 						<td>Transaction ID, a random number chosen by the 
 * 						client, used by the client and server to associate
 * 						messages and responses between a client and a
 * 						server.</td></tr>
 * 	<tr><td valign=top><tt>secs</tt></td><td valign=top>2</td>
 * 						<td>Filled in by client, seconds elapsed since client
 * 						began address acquisition or renewal process.</td></tr>
 * 	<tr><td valign=top><tt>flags</tt></td><td valign=top>2</td>
 * 						<td>Flags (see below).</td></tr>
 * 	<tr><td valign=top><tt>ciaddr</tt></td><td valign=top>4</td>
 * 						<td>Client IP address; only filled in if client is in
 * 						BOUND, RENEW or REBINDING state and can respond
 * 						to ARP requests.</td></tr>
 * 	<tr><td valign=top><tt>yiaddr</tt></td><td valign=top>4</td>
 * 						<td>'your' (client) IP address.</td></tr>
 * 	<tr><td valign=top><tt>siaddr</tt></td><td valign=top>4</td>
 * 						<td>IP address of next server to use in bootstrap;
 * 						returned in DHCPOFFER, DHCPACK by server.</td></tr>
 * 	<tr><td valign=top><tt>giaddr</tt></td><td valign=top>4</td>
 * 						<td>Relay agent IP address, used in booting via a
 * 						relay agent.</td></tr>
 * 	<tr><td valign=top><tt>chaddr</tt></td><td valign=top>16</td>
 * 						<td>Client hardware address.</td></tr>
 * 	<tr><td valign=top><tt>sname</tt></td><td valign=top>64</td>
 * 						<td>Optional server host name, null terminated string.</td></tr>
 * 	<tr><td valign=top><tt>file</tt></td><td valign=top>128</td>
 * 						<td>Boot file name, null terminated string; "generic"
 * 						name or null in DHCPDISCOVER, fully qualified
 * 						directory-path name in DHCPOFFER.</td></tr>
 * 	<tr><td valign=top><tt>isDhcp</tt></td><td valign=top>4</td>
 * 						<td>Controls whether the packet is BOOTP or DHCP.
 * 						DHCP contains the "magic cookie" of 4 bytes.
 * 						0x63 0x82 0x53 0x63.</td></tr>
 * 	<tr><td valign=top><tt>DHO_*code*</tt></td><td valign=top>*</td>
 * 						<td>Optional parameters field.  See the options
 * 						documents for a list of defined options. See below.</td></tr>
 * 	<tr><td valign=top><tt>padding</tt></td><td valign=top>*</td>
 * 						<td>Optional padding at the end of the packet.</td></tr>
 * </table></blockquote>
 * 
 * <h4>DHCP Option</h4>
 * 
 * The following options are codes are supported:
 * <pre>
 * DHO_SUBNET_MASK(1)
 * DHO_TIME_OFFSET(2)
 * DHO_ROUTERS(3)
 * DHO_TIME_SERVERS(4)
 * DHO_NAME_SERVERS(5)
 * DHO_DOMAIN_NAME_SERVERS(6)
 * DHO_LOG_SERVERS(7)
 * DHO_COOKIE_SERVERS(8)
 * DHO_LPR_SERVERS(9)
 * DHO_IMPRESS_SERVERS(10)
 * DHO_RESOURCE_LOCATION_SERVERS(11)
 * DHO_HOST_NAME(12)
 * DHO_BOOT_SIZE(13)
 * DHO_MERIT_DUMP(14)
 * DHO_DOMAIN_NAME(15)
 * DHO_SWAP_SERVER(16)
 * DHO_ROOT_PATH(17)
 * DHO_EXTENSIONS_PATH(18)
 * DHO_IP_FORWARDING(19)
 * DHO_NON_LOCAL_SOURCE_ROUTING(20)
 * DHO_POLICY_FILTER(21)
 * DHO_MAX_DGRAM_REASSEMBLY(22)
 * DHO_DEFAULT_IP_TTL(23)
 * DHO_PATH_MTU_AGING_TIMEOUT(24)
 * DHO_PATH_MTU_PLATEAU_TABLE(25)
 * DHO_INTERFACE_MTU(26)
 * DHO_ALL_SUBNETS_LOCAL(27)
 * DHO_BROADCAST_ADDRESS(28)
 * DHO_PERFORM_MASK_DISCOVERY(29)
 * DHO_MASK_SUPPLIER(30)
 * DHO_ROUTER_DISCOVERY(31)
 * DHO_ROUTER_SOLICITATION_ADDRESS(32)
 * DHO_STATIC_ROUTES(33)
 * DHO_TRAILER_ENCAPSULATION(34)
 * DHO_ARP_CACHE_TIMEOUT(35)
 * DHO_IEEE802_3_ENCAPSULATION(36)
 * DHO_DEFAULT_TCP_TTL(37)
 * DHO_TCP_KEEPALIVE_INTERVAL(38)
 * DHO_TCP_KEEPALIVE_GARBAGE(39)
 * DHO_NIS_SERVERS(41)
 * DHO_NTP_SERVERS(42)
 * DHO_VENDOR_ENCAPSULATED_OPTIONS(43)
 * DHO_NETBIOS_NAME_SERVERS(44)
 * DHO_NETBIOS_DD_SERVER(45)
 * DHO_NETBIOS_NODE_TYPE(46)
 * DHO_NETBIOS_SCOPE(47)
 * DHO_FONT_SERVERS(48)
 * DHO_X_DISPLAY_MANAGER(49)
 * DHO_DHCP_REQUESTED_ADDRESS(50)
 * DHO_DHCP_LEASE_TIME(51)
 * DHO_DHCP_OPTION_OVERLOAD(52)
 * DHO_DHCP_MESSAGE_TYPE(53)
 * DHO_DHCP_SERVER_IDENTIFIER(54)
 * DHO_DHCP_PARAMETER_REQUEST_LIST(55)
 * DHO_DHCP_MESSAGE(56)
 * DHO_DHCP_MAX_MESSAGE_SIZE(57)
 * DHO_DHCP_RENEWAL_TIME(58)
 * DHO_DHCP_REBINDING_TIME(59)
 * DHO_VENDOR_CLASS_IDENTIFIER(60)
 * DHO_DHCP_CLIENT_IDENTIFIER(61)
 * DHO_NWIP_DOMAIN_NAME(62)
 * DHO_NWIP_SUBOPTIONS(63)
 * DHO_NIS_DOMAIN(64)
 * DHO_NIS_SERVER(65)
 * DHO_TFTP_SERVER(66)
 * DHO_BOOTFILE(67)
 * DHO_MOBILE_IP_HOME_AGENT(68)
 * DHO_SMTP_SERVER(69)
 * DHO_POP3_SERVER(70)
 * DHO_NNTP_SERVER(71)
 * DHO_WWW_SERVER(72)
 * DHO_FINGER_SERVER(73)
 * DHO_IRC_SERVER(74)
 * DHO_STREETTALK_SERVER(75)
 * DHO_STDA_SERVER(76)
 * DHO_USER_CLASS(77)
 * DHO_FQDN(81)
 * DHO_DHCP_AGENT_OPTIONS(82)
 * DHO_NDS_SERVERS(85)
 * DHO_NDS_TREE_NAME(86)
 * DHO_USER_AUTHENTICATION_PROTOCOL(98)
 * DHO_AUTO_CONFIGURE(116)
 * DHO_NAME_SERVICE_SEARCH(117)
 * DHO_SUBNET_SELECTION(118)
 * </pre>
 * 
 * <p>These options can be set and get through basic low-level <tt>getOptionRaw</tt> and
 * <tt>setOptionRaw</tt> passing <tt>byte[]</tt> structures. Using these functions, data formats
 * are under your responsibility. Arrays are always passed by copies (clones) so you can modify
 * them freely without side-effects. These functions allow maximum performance, especially
 * when copying options from a request datagram to a response datagram.
 * 
 * <h4>Special case: DHO_DHCP_MESSAGE_TYPE</h4>
 * The DHCP Message Type (option 53) is supported for the following values
 * <pre>
 * DHCPDISCOVER(1)
 * DHCPOFFER(2)
 * DHCPREQUEST(3)
 * DHCPDECLINE(4)
 * DHCPACK(5)
 * DHCPNAK(6)
 * DHCPRELEASE(7)
 * DHCPINFORM(8)
 * DHCPFORCERENEW(9)
 * DHCPLEASEQUERY(13)
 * </pre>
 * 
 * <h4>DHCP option formats</h4>
 * 
 * A limited set of higher level data-structures are supported. Type checking is enforced
 * according to rfc 2132. Check corresponding methods for a list of option codes allowed for
 * each datatype.
 * 
 * <blockquote>
 * <br>Inet (4 bytes - IPv4 address)
 * <br>Inets (X*4 bytes - list of IPv4 addresses)
 * <br>Short (2 bytes - short)
 * <br>Shorts (X*2 bytes - list of shorts)
 * <br>Byte (1 byte)
 * <br>Bytes (X bytes - list of 1 byte parameters)
 * <br>String (X bytes - ASCII string)
 * <br>
 * </blockquote>
 * 
 * 
 * <p><b>Note</b>: this class is not synchronized for maximum performance.
 * However, it is unlikely that the same <tt>DHCPPacket</tt> is used in two different 
 * threads in real life DHPC servers or clients. Multi-threading acces
 * to an instance of this class is at your own risk.
 * 
 * <p><b>Limitations</b>: this class doesn't support spanned options or options longer than 256 bytes.
 * It does not support options stored in <tt>sname</tt> or <tt>file</tt> fields.
 * 
 * <p>This API is originally a port from my PERL
 * <tt><a href="http://search.cpan.org/~shadinger/">Net::DHCP</a></tt> api.
 * 
 * <p><b>Future extensions</b>: IPv6 support, extended data structure TODO...
 * 
 */
public class DHCPPacket {
	// static structure of the packet
    private byte    op;        // Op code
    private byte    htype;    // HW address Type
    private byte    hlen;      // hardware address length
    private byte    hops;     // Hw options
    private int       xid;       // transaction id
    private short   secs;      // elapsed time from trying to boot
    private short   flags;     // flags
    private byte[]  ciaddr = new byte[4];    // client IP
    private byte[]  yiaddr = new byte[4];    // your client IP
    private byte[]  siaddr = new byte[4];    // Server IP
    private byte[]  giaddr = new byte[4];    // relay agent IP
    private byte[]  chaddr = new byte[16];    // Client HW address
    private byte[]  sname  = new byte[64];     // Optional server host name
    private byte[]  file       = new byte[128];      // Boot file name
    private byte[]  options  = new byte[0];         // Option
    
    //use for pragramming only
    private Map<Byte, DHCPOption> optionMap = new LinkedHashMap<Byte,DHCPOption>();
    private boolean isDhcp = true;
    private byte messageType = 1;  //默认Discover Message
	private DHCPSocket socket;
	private InetAddress remote_ip;
	private int port;
	private String clientMac;
			
	//packet max size and min size
	public static final int PACKET_MAX  = 1500;  /** default MTU for ethernet */
    public static final int PACKET_MIN  = 236;
    public static final int MAGIC_COOKIE = 1669485411;
    public static final byte DHO_PAD  =   0;
    public static final byte DHO_END  =  (byte)0xff;
    
	public DHCPPacket(DHCPSocket socket,InetAddress remote_ip,int port){
		this.socket = socket;
		this.remote_ip = remote_ip;
		this.port = port;
	}
	
	public byte getOp() {
		return op;
	}

	public void setOp(byte op) {
		this.op = op;
	}

	public byte getHtype() {
		return htype;
	}

	public void setHtype(byte htype) {
		this.htype = htype;
	}

	public byte getHlen() {
		return hlen;
	}

	public void setHlen(byte hlen) {
		this.hlen = hlen;
	}

	public byte getHops() {
		return hops;
	}

	public void setHops(byte hops) {
		this.hops = hops;
	}

	public int getXid() {
		return xid;
	}

	public void setXid(int xid) {
		this.xid = xid;
	}

	public short getSecs() {
		return secs;
	}

	public void setSecs(short secs) {
		this.secs = secs;
	}

	public short getFlags() {
		return flags;
	}

	public void setFlags(short flags) {
		this.flags = flags;
	}

	public byte[] getCiaddr() {
		return ciaddr;
	}

	public byte[] getYiaddr() {
		return yiaddr;
	}

	public byte[] getSiaddr() {
		return siaddr;
	}

	public byte[] getGiaddr() {
		return giaddr;
	}

	public byte[] getChaddr() {
		return chaddr;
	}

	public byte[] getSname() {
		return sname;
	}

	public byte[] getFile() {
		return file;
	}

	public Map<Byte, DHCPOption> getOptions() {
		return optionMap;
	}

	public boolean isDHCP() {
		return isDhcp;
	}

	public void setDHCP(boolean isDHCP) {
		this.isDhcp = isDHCP;
	}

	public byte getMessageType() {
		return messageType;
	}

	public void setMessageType(byte messageType) {
		this.messageType = messageType;
	}
	
    public void setCiaddr(byte[] ciaddr) {
		this.ciaddr = ciaddr;
	}

	public void setYiaddr(byte[] yiaddr) {
		this.yiaddr = yiaddr;
	}

	public void setSiaddr(byte[] siaddr) {
		this.siaddr = siaddr;
	}

	public void setGiaddr(byte[] giaddr) {
		this.giaddr = giaddr;
	}

	public void setChaddr(byte[] chaddr) {
		this.chaddr = chaddr;
	}

	public void setSname(String sname) {
		if(sname==null)return;
		Arrays.fill(this.sname, (byte)0);
		byte[] data = sname.getBytes();
		System.arraycopy(data, 0, this.sname, 0, data.length);
	}

	public void setFile(String file){
		if(file==null)return;
		Arrays.fill(this.file, (byte)0);
		byte[] data = file.getBytes();
		System.arraycopy(data, 0, this.file, 0, data.length);
	}
	
	public void setOptions(byte[] options) {
		this.options = options;
	}
	
	public DatagramSocket getSocket() {
		return socket.getSocket();
	}
	
	public DHCPSocket getDHCPSocket() {
		return socket;
	}

	public InetAddress getRemote_ip() {
		return remote_ip;
	}

	public void setRemote_ip(InetAddress remote_ip) {
		this.remote_ip = remote_ip;
	}
	
	public String getClientMac() {
		return clientMac;
	}

	public void fromDatagramPacket(DatagramPacket packet) throws Exception{
		ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData(),packet.getOffset(),packet.getLength());
		DataInputStream dis         = new DataInputStream(bis);
		this.op = dis.readByte();
		this.htype = dis.readByte();
		this.hlen = dis.readByte();
		this.hops = dis.readByte();
		this.xid = dis.readInt();
		this.secs = dis.readShort();
		this.flags = dis.readShort();
		dis.readFully(this.ciaddr,0,4);
		dis.readFully(this.yiaddr,0,4);
		dis.readFully(this.siaddr,0,4);
		dis.readFully(this.giaddr,0,4);
		dis.readFully(this.chaddr,0,16);
		dis.readFully(this.sname,0,64);
		dis.readFully(this.file,0,128);
		//设置ClientMac
		this.clientMac = SecurityUtil.toHex(Arrays.copyOfRange(this.chaddr, 0, this.hlen));
		
		//设置option
		int optionLen = dis.available();
		if(optionLen<1) return;
		this.options = new byte[optionLen];
		dis.mark(optionLen);
		dis.readFully(options, 0, optionLen);
		dis.reset();   //返回到原来的头部
		
		//读option 前四字节找到magic_cookie
		dis.mark(4); //预先读数据
		if(dis.readInt()!=DHCPPacket.MAGIC_COOKIE){ //如果有margic_cookie则option的值放在4字节之后,只有dhcp包才有magic_cookie
			this.isDhcp = false;
			dis.reset();  //返回位置
		}
		while(true){
			int code = bis.read();
			if(code<0)break;          //流结束
			if(code == 0)continue; //padding
			if(code == 0xff)break;  // end
			int len = (int)bis.read();
			byte[] optionData = new byte[Math.min(len, bis.available())];
			bis.read(optionData, 0, Math.min(optionData.length, bis.available()));
			this.optionMap.put((byte)code, new DHCPOption((byte)code,optionData));
			//设置MessageType
			if(code == 53) this.messageType = optionData[0];
		}
		dis.close();
	}
	
	public DatagramPacket toDatagramPacket() {
        // prepare output buffer, pre-sized to maximum buffer length
        // default buffer is half the maximum size of possible packet
        // (this seams reasonable for most uses, worst case only doubles the buffer size once
        ByteArrayOutputStream outBStream = new ByteArrayOutputStream(PACKET_MAX / 2);
        DataOutputStream      outStream     = new DataOutputStream(outBStream);
        try {
            outStream.writeByte (this.op);
            outStream.writeByte (this.htype);
            outStream.writeByte (this.hlen);
            outStream.writeByte (this.hops);
            outStream.writeInt  (this.xid);
            outStream.writeShort(this.secs);
            outStream.writeShort(this.flags);
            outStream.write(this.ciaddr, 0,   4);
            outStream.write(this.yiaddr, 0,   4);
            outStream.write(this.siaddr, 0,   4);
            outStream.write(this.giaddr, 0,   4);
            outStream.write(this.chaddr, 0,  16);
            outStream.write(this.sname,  0,  64);
            outStream.write(this.file,   0, 128);

            if (this.isDhcp) {
                // DHCP and not BOOTP -> magic cookie required
                outStream.writeInt(MAGIC_COOKIE);

                // parse output options in creation order (LinkedHashMap)
                for (DHCPOption opt : this.optionMap.values()) {
                    int size = opt.getValueFast().length;
                    //Options larger than 255 bytes are not yet supported
                    if (size > 255) return null;
                    outStream.writeByte(opt.getCode());        // output option code
                    outStream.writeByte(size);    // output option length
                    outStream.write(opt.getValueFast());    // output option data
                }
                // mark end of options
                outStream.writeByte(DHO_END);
            }

            // add padding if the packet is too small
            int min_padding = (this.isDhcp?PACKET_MIN:64) - outBStream.size();
            if (min_padding > 0) {
                byte[] add_padding = new byte[min_padding];
                outStream.write(add_padding);
            }

            // final packet is here
            byte[] data = outBStream.toByteArray();

            // do some post sanity checks
            if (data.length > PACKET_MAX) {
                throw new Exception("serialize: packet too big (" + data.length + " greater than max MAX_MTU (" + PACKET_MAX + ')');
            }
            
            outStream.close();
            DatagramPacket packet = new DatagramPacket(data,data.length);
            packet.setAddress(remote_ip);
            packet.setPort(port);
            return packet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder(); // output buffer
        try {
            buffer.append(this.isDhcp ? "DHCP Packet" : "BOOTP Packet")
                     .append("\n address="+(this.remote_ip != null ? this.remote_ip.getHostAddress() : "")+"("+this.port+")")
                     .append("\n op    ="+(int)this.op)
                     .append("\n hlen   ="+(int)this.hlen)
                     .append("\n hops  ="+(int)this.hops)
                     .append("\n xid     ="+(int)this.xid)
                     .append("\n secs   ="+(int)this.secs)
                     .append("\n flags   ="+(int)this.flags)
                     .append("\n ciaddr ="+InetAddress.getByAddress(this.ciaddr).getHostAddress())
                     .append("\n yiaddr ="+InetAddress.getByAddress(this.yiaddr).getHostAddress())
                     .append("\n siaddr ="+InetAddress.getByAddress(this.siaddr).getHostAddress())
                     .append("\n giaddr ="+InetAddress.getByAddress(this.giaddr).getHostAddress())
                     .append("\n chaddr = 0x"+SecurityUtil.toHex(this.chaddr))
                     .append("\n sname  ="+new String(this.sname,Charset.forName("US-ASCII")))
                     .append("\n file      ="+new String(this.file,Charset.forName("US-ASCII")))
                     .append("\n option:"+(this.isDhcp?"option:":"vend"));
            for(Entry<Byte,DHCPOption> op: this.optionMap.entrySet()){
            	buffer.append('\n');
            	op.getValue().append(buffer);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return buffer.toString();
    }
	
    @Override
    public int hashCode(){
    	int h = -1;
    	h ^= op;
    	h ^= htype;
    	h ^= hlen;
    	h ^= hops;
    	h ^= xid;
    	h ^= flags;
    	h ^= Arrays.hashCode(ciaddr);
    	h ^= Arrays.hashCode(yiaddr);;
    	h ^= Arrays.hashCode(siaddr);;
    	h ^= Arrays.hashCode(giaddr);;
    	h ^= Arrays.hashCode(chaddr);;
    	h ^= Arrays.hashCode(sname);;
    	h ^= Arrays.hashCode(file);;
    	h ^= Arrays.hashCode(options);;
    	return h;
    }
    
    @Override
    public boolean equals(Object o){
    	if(o==null)return false;
    	if(this==o)return true;
    	if(!(o instanceof DHCPPacket))return false;
    	DHCPPacket p = (DHCPPacket)o;
    	boolean b = true;
    	b &= (this.op == p.op);
    	b &= (this.htype == p.htype);
    	b &= (this.hlen == p.hlen);
    	b &= (this.hops == p.hops);
    	b &= (this.xid == p.xid);
    	b &= (this.flags == p.flags);
    	b &= Arrays.equals(this.ciaddr, p.ciaddr);
    	b &= Arrays.equals(this.yiaddr, p.yiaddr);
    	b &= Arrays.equals(this.siaddr, p.siaddr);
    	b &= Arrays.equals(this.giaddr, p.giaddr);
    	b &= Arrays.equals(this.chaddr, p.chaddr);
    	b &= Arrays.equals(this.sname, p.sname);
    	b &= Arrays.equals(this.file, p.file);
    	b &= Arrays.equals(this.options, p.options);
    	return b;
    }
    
}
