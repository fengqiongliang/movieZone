package com.pxe.iscsi.cdb16;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.moviezone.util.ByteUtil;
import com.pxe.iscsi.ENUM.CDBOpcode;
 
/**
<pre>
6.6 INQUIRY command

6.6.1 INQUIRY command introduction
	
    The INQUIRY command (see table 163) requests that information regarding the logical unit and SCSI target device
    be sent to the application client.

                     Table 163 — INQUIRY command
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    |                  OPERATION CODE (12h)                         |
   |-------------------------------------------------------------------------|
   |    1    |                  Reserved                     |Obsolet| EVPD  |
   |-------------------------------------------------------------------------|
   |    2    |                  PAGE CODE                                    |
   |-------------------------------------------------------------------------|
   |    3    | (MSB) |                                                       |
   |         |                  ALLOCATION LENGTH                            |
   |    4    |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   |    5    |                 CONTROL                                       |
   |-------------------------------------------------------------------------|
   
   The OPERATION CODE field is defined in 4.3.5.1 and shall be set as shown in table 163 for the INQUIRY command.
   
   An enable vital product data (EVPD) bit set to one specifies that the device server shall return the vital product data
   specified by the PAGE CODE field (see 7.8). If the device server does not implement the requested vital product data
   page, then the command shall be terminated with CHECK CONDITION status, with the sense key set to ILLEGAL
   REQUEST, and the additional sense code set to INVALID FIELD IN CDB.
   
   An EVPD bit is set to zero specified that the device server shall return the standard INQUIRY data (see 6.6.2). If the
   PAGE CODE field is not set to zero when the EVPD bit is set to zero, the command shall be terminated with CHECK
   CONDITION status, with the sense key set to ILLEGAL REQUEST, and the additional sense code set to INVALID
   FIELD IN CDB.
   
   When the EVPD bit is set to one, the PAGE CODE field specifies which page of vital product data information the
   device server shall return (see 7.8).

   The ALLOCATION LENGTH field is defined in 4.3.5.6.
   
   The CONTROL field is defined in SAM-5.
   
   In response to an INQUIRY command received by an incorrect logical unit, the SCSI target device shall return the
   INQUIRY data with the peripheral qualifier set to the value defined in 6.6.2. The INQUIRY command shall return
   CHECK CONDITION status only when the device server is unable to return the requested INQUIRY data. 
	
   If an INQUIRY command is received from an initiator port with a pending unit attention condition (i.e., before the
   device server reports CHECK CONDITION status), the device server shall perform the INQUIRY command and
   shall not clear the unit attention condition (see SAM-4).
	
   The device server should be able to process the INQUIRY command even when an error occurs that prohibits
   normal command completion.

   INQUIRY data (i.e., standard INQUIRY data (see 6.6.2) and all VPD pages (see 7.8)) should be returned even
   though the device server is not ready for other commands. Standard INQUIRY data, the Extended INQUIRY Data
   VPD page (see 7.8.7), and the Device Identification VPD page (see 7.8.6) should be available without incurring any
   media access delays. If the device server does store some of the standard INQUIRY data or VPD data on the
   media, it may return ASCII spaces (20h) in ASCII fields and zeros in other fields until the data is available from the
   media.
	
   INQUIRY data may change as the SCSI target device and its logical units perform their initialization sequence.
   (E.g., logical units may provide a minimum command set from nonvolatile memory until they load the final firmware
   from the media. After the firmware has been loaded, more options may be supported and therefore different
   INQUIRY data may be returned.)
	
   If INQUIRY data changes for any reason, the device server shall establish a unit attention condition for the initiator
   port associated with every I_T nexus (see SAM-4), with the additional sense code set to INQUIRY DATA HAS
   CHANGED.
	
   NOTE 30 - The INQUIRY command may be used by an application client after a hard reset or power on condition 
   to determine the device types for system configuration.
	
6.6.2 Standard INQUIRY data
	
    The standard INQUIRY data (see table 164) shall contain at least 36 bytes.

                     Table 164 — Standard INQUIRY data format 
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    | PERIPHERAL QUALIFIER  |      PERIPHERAL DEVICE TYPE           |
   |-------------------------------------------------------------------------|
   |    1    |  RMB  |          Reserved                                     |
   |-------------------------------------------------------------------------|
   |    2    |                  VERSION                                      |
   |-------------------------------------------------------------------------|
   |    3    |Obsolet|Obsolet|NORMACA| HISUP |   RESPONSE DATA FORMAT (2h)   |
   |-------------------------------------------------------------------------|
   |    4    |                  ADDITIONAL LENGTH (n-4)                      |
   |-------------------------------------------------------------------------|
   |    5    | SCCS  |  ACC  |     TPGS      |  3PC  |   Reserved    |PROTECT|
   |-------------------------------------------------------------------------|
   |    6    |Obsolet|ENCSERV|  VS   |MULTIP |Obsolet|Obsolet|Obsolet|ADDR16*|
   |-------------------------------------------------------------------------|
   |    7    |Obsolet|Obsolet|WBUS16*| SYNC* |Obsolet|Obsolet|CMDQUE |  VS   |
   |-------------------------------------------------------------------------|
   |    8    | (MSB) |                                                       |
   |   ...   |                  T10 VENDOR IDENTIFICATION                    |
   |    15   |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   |    16   | (MSB) |                                                       |
   |   ...   |                  PRODUCT IDENTIFICATION                       |
   |    31   |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   |    32   | (MSB) |                                                       |
   |   ...   |                  PRODUCT REVISION LEVEL                       |
   |    35   |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   |    36   |                                                               |
   |   ...   |                  Vendor specific                              |
   |    55   |                                                               |
   |-------------------------------------------------------------------------|
   |    56   |          Reserved             |   CLOCKING*   |  QAS* |  IUS* |
   |-------------------------------------------------------------------------|
   |    57   |                  Reserved                                     |
   |-------------------------------------------------------------------------|
   |    58   | (MSB) |                                                       |
   |         |                  VERSION DESCRIPTOR 1                         |
   |    59   |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   |         |                     ...                                       |
   |-------------------------------------------------------------------------|
   |    72   | (MSB) |                                                       |
   |         |                  VERSION DESCRIPTOR 8                         |
   |    73   |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   |    74   |                                                               |
   |   ...   |                  Reserved                                     |
   |    95   |                                                               |
   |-------------------------------------------------------------------------|
   |         |                  Vendor specific parameters                   |
   |-------------------------------------------------------------------------|
   |    96   |                                                               |
   |   ...   |                  Vendor specific                              |
   |    n    |                                                               |
   |-------------------------------------------------------------------------|   
   
  \* - The meanings of these fields are specific to SPI-5 (see 6.6.3). For SCSI transport protocols other than the 
       SCSI Parallel Interface, these fields are reserved.
	   
   The PERIPHERAL QUALIFIER field and PERIPHERAL DEVICE TYPE field identify the peripheral device connected to the
   logical unit. If the SCSI target device is not capable of supporting a peripheral device connected to this logical unit,
   the device server shall set these fields to 7Fh (i.e., PERIPHERAL QUALIFIER field set to 011b and PERIPHERAL DEVICE
   TYPE field set to 1Fh).

   The peripheral qualifier is defined as :
   
      000b - A peripheral device having the specified peripheral device type is connected to this 
             logical unit. If the device server is unable to determine whether or not a peripheral 
             device is connected, it also shall use this peripheral qualifier. This peripheral qualifier 
             does not mean that the peripheral device connected to the logical unit is ready for 
             access. 
   
      001b - A peripheral device having the specified peripheral device type is not connected to this 
             logical unit. However, the device server is capable of supporting the specified periph-eral 
			 device type on this logical unit. 
      
      010b - Reserved. 

      011b - The device server is not capable of supporting a peripheral device on this logical unit. 
             For this peripheral qualifier the peripheral device type shall be set to 1Fh. All other 
             peripheral device type values are reserved for this peripheral qualifier.
   			 
      100b to 111b - Vendor specific.

   The peripheral device type is defined as : 
   
      00h - Direct access block device (e.g., magnetic disk).
      01h - Sequential-access device (e.g., magnetic tape).
      02h - Printer device.
      03h - Processor device.
      04h - Write-once device (e.g., some optical disks).
      05h - CD/DVD device.
      06h - Scanner device (obsolete).
      07h - Optical memory device (e.g., some optical disks).
      08h - Media changer device (e.g., jukeboxes).
      09h - Communications device (obsolete).
      0Ah to 0Bh - Obsolete.
      0Ch - Storage array controller device (e.g., RAID).
      0Dh - Enclosure services device.
      0Eh - Simplified direct-access device (e.g., magnetic disk).
      0Fh - Optical card reader/writer device.
      10h - Bridge Controller Commands.
      11h - Object-based Storage Device.
      12h - Automation/Drive Interface.	  
      13h - Security manager device.
      14h to 1Dh - Reserved.
      1Eh - Well known logical unit. *
      1Fh - Unknown or no device type.	  
   
   * - All well known logical units use the same peripheral device type code.
   
   A removable medium (RMB) bit set to zero indicates that the medium is not removable. 
   A RMB bit set to one indicates that the medium is removable.
   
   The VERSION field indicates the implemented version of this standard and is defined as : 
   
      00h - The device server does not claim conformance to any standard..
      01h to 02h - Obsolete.
      03h - The device server complies to ANSI INCITS 301-1997 (a withdrawn standard).
      04h - The device server complies to ANSI INCITS 351-2001 (SPC-2).
      05h - The device server complies to ANSI INCITS 408-2005 (SPC-3).
      06h - The device server complies to this standard.
      07h - Reserved.
      08h to 0Ch - Obsolete.
      0Dh to 3Fh - Reserved.
      40h to 44h - Obsolete.
      45h to 47h - Reserved.
      48h to 4Ch - Obsolete.
      4Dh to 7Fh - Reserved.
      80h to 84h - Obsolete.
      85h to 87h - Reserved.
      88h to 8Ch - Obsolete.
      8Dh to FFh - Reserved.
      
   The Normal ACA Supported (NORMACA) bit set to one indicates that the device server supports a NACA bit set to
   one in the CDB CONTROL byte and supports the ACA task attribute (see SAM-4). A NORMACA bit set to zero
   indicates that the device server does not support a NACA bit set to one and does not support the ACA task attribute.
   
   A hierarchical support (HISUP) bit set to zero indicates the SCSI target device does not use the hierarchical
   addressing model to assign LUNs to logical units. A HISUP bit set to one indicates the SCSI target device uses the
   hierarchical addressing model to assign LUNs to logical units.	

   The RESPONSE DATA FORMAT field indicates the format of the standard INQUIRY data and shall be set as shown in
   table 164. A RESPONSE DATA FORMAT field set to 2h indicates that the standard INQUIRY data is in the format
   defined in this standard. Response data format values less than 2h are obsolete. Response data format values
   greater than 2h are reserved. 

   The ADDITIONAL LENGTH field indicates the length in bytes of the remaining standard INQUIRY data. The
   relationship between the ADDITIONAL LENGTH field and the CDB ALLOCATION LENGTH field is defined in 4.3.5.6.   
   
   An SCC Supported (SCCS) bit set to one indicates that the SCSI target device contains an embedded storage array
   controller component that is addressable through this logical unit. See SCC-2 for details about storage array
   controller devices. An SCCS bit set to zero indicates that no embedded storage array controller component is
   addressable through this logical unit.
   
   An Access Controls Coordinator (ACC) bit set to one indicates that the SCSI target device contains an access
   controls coordinator (see 3.1.4) that is addressable through this logical unit. An ACC bit set to zero indicates that no
   access controls coordinator is addressable through this logical unit. If the SCSI target device contains an access
   controls coordinator that is addressable through any logical unit other than the ACCESS CONTROLS well known
   logical unit (see 8.3), then the ACC bit shall be set to one for LUN 0.
   
   The contents of the target port group support (TPGS) field (see table 168) indicate the support for asymmetric
   logical unit access (see 5.11).   
   
   Table 168 — TPGS field

      00b - The logical unit does not support asymmetric logical unit access or supports a form of asymmetric 
            access that is vendor specific. Neither the REPORT TARGET GROUPS nor the SET TARGET 
            PORT GROUPS commands is supported.
			
      01b - The logical unit supports only implicit asymmetric logical unit access (see 5.11.2.7). The logical 
            unit is capable of changing target port asymmetric access states without a SET TARGET PORT 
            GROUPS command. The REPORT TARGET PORT GROUPS command is supported and the 
            SET TARGET PORT GROUPS command is not supported.
			
      10b - The logical unit supports only explicit asymmetric logical unit access (see 5.11.2.8). The logical 
            unit only changes target port asymmetric access states as requested with the SET TARGET 
            PORT GROUPS command. Both the REPORT TARGET PORT GROUPS command and the SET 
            TARGET PORT GROUPS command are supported.
			
      11b - The logical unit supports both explicit and implicit asymmetric logical unit access. Both the 
            REPORT TARGET PORT GROUPS command and the SET TARGET PORT GROUPS 
            commands are supported.
      

   A third-party copy (3PC) bit set to one indicates that the logical unit supports third-party copy commands 
   (see 5.19.3). A 3PC bit set to zero indicates that the logical unit does not support third-party copy commands.  
   
   A PROTECT bit set to zero indicates that the logical unit does not support protection information. A PROTECT bit set to
   one indicates that the logical unit supports:
   
      a) type 1 protection, type 2 protection, or type 3 protection (see SBC-3); or
      b) logical block protection (see SSC-4).
	  
   More information about the type of protection the logical unit supports is available in the SPT field (see 7.8.7).
   
   An Enclosure Services (ENCSERV) bit set to one indicates that the SCSI target device contains an embedded
   enclosure services component that is addressable through this logical unit. See SES-3 for details about enclosure
   services. An ENCSERV bit set to zero indicates that no embedded enclosure services component is addressable
   through this logical unit.   
   
   A Multi Port (MULTIP) bit set to one indicates that this is a multi-port (two or more ports) SCSI target device and
   conforms to the SCSI multi-port device requirements found in the applicable standards (e.g., SAM-4, a SCSI
   transport protocol standard and possibly provisions of a command standard). A MULTIP bit set to zero indicates that
   this SCSI target device has a single port and does not implement the multi-port requirements.   
   
   The CMDQUE bit shall be set to one indicating that the logical unit supports the command management model
   defined in SAM-4.   
   
   The T10 VENDOR IDENTIFICATION field contains eight bytes of left-aligned ASCII data (see 4.4.1) identifying the
   vendor of the logical unit. The T10 vendor identification shall be one assigned by INCITS. A list of assigned T10
   vendor identifications is in Annex F and on the T10 web site (http://www.t10.org).
   
   NOTE 31 - The T10 web site (http://www.t10.org) provides a convenient means to request an identification code.   

   The PRODUCT IDENTIFICATION field contains sixteen bytes of left-aligned ASCII data (see 4.4.1) defined by the
   vendor.
   The PRODUCT REVISION LEVEL field contains four bytes of left-aligned ASCII data defined by the vendor.   
   
   The VERSION DESCRIPTOR fields provide for identifying up to eight standards to which the SCSI target device and/or
   logical unit claim conformance. The value in each VERSION DESCRIPTOR field shall be selected from table 169. All
   version descriptor values not listed in table 169 are reserved. Technical Committee T10 of INCITS maintains an
   electronic copy of the information in table 169 on its world wide web site (http://www.t10.org/). In the event that the
   T10 world wide web site is no longer active, access may be possible via the INCITS world wide web site
   (http://www.incits.org), the ANSI world wide web site (http://www.ansi.org), the IEC site (http://www.iec.ch/), the
   ISO site (http://www.iso.ch/), or the ISO/IEC JTC 1 web site (http://www.jtc1.org/). It is recommended that the first
   version descriptor be used for the SCSI architecture standard, followed by the physical transport standard if any,
   followed by the SCSI transport protocol standard, followed by the appropriate SPC-x version, followed by the
   device type command set, followed by a secondary command set if any.
   
   Table 169 — Version descriptor values :
   
	1761h	ACS-2 (no version claimed) 
	03C0h	ADC (no version claimed) 
	03D7h	ADC ANSI INCITS 403-2005 
	03D6h	ADC T10/1558-D revision 7 
	03D5h	ADC T10/1558-D revision 6 
	04A0h	ADC-2 (no version claimed) 
	04ACh	ADC-2 ANSI INCITS 441-2008 
	04A7h	ADC-2 T10/1741-D revision 7 
	04AAh	ADC-2 T10/1741-D revision 8 
	0500h	ADC-3 (no version claimed) 
	0502h	ADC-3 T10/1895-D revision 04 
	0504h	ADC-3 T10/1895-D revision 05 
	09C0h	ADP (no version claimed) 
	09E0h	ADT (no version claimed) 
	09FDh	ADT ANSI INCITS 406-2005 
	09FAh	ADT T10/1557-D revision 14 
	09F9h	ADT T10/1557-D revision 11 
	0A20h	ADT-2 (no version claimed) 
	0A2Bh	ADT-2 ANSI INCITS 472-2011 
	0A22h	ADT-2 T10/1742-D revision 06 
	0A27h	ADT-2 T10/1742-D revision 08 
	0A28h	ADT-2 T10/1742-D revision 09 
	15E0h	ATA/ATAPI-6 (no version claimed) 
	15FDh	ATA/ATAPI-6 ANSI INCITS 361-2002 
	1600h	ATA/ATAPI-7 (no version claimed) 
	161Eh	ATA/ATAPI-7 ISO/IEC 24739 
	161Ch	ATA/ATAPI-7 ANSI INCITS 397-2005 
	1602h	ATA/ATAPI-7 T13/1532-D revision 3
	1620h	ATA/ATAPI-8 ATA8-AAM (no version claimed)
	1628h	ATA/ATAPI-8 ATA8-AAM ANSI INCITS 451-2008
	1621h	ATA/ATAPI-8 ATA8-APT Parallel Transport (no version claimed)
	1622h	ATA/ATAPI-8 ATA8-AST Serial Transport (no version claimed)
	1623h	ATA/ATAPI-8 ATA8-ACS ATA/ATAPI Command Set (no version claimed) 
	162Ah	ATA/ATAPI-8 ATA8-ACS ANSI INCITS 452-2009 w/ Amendment 1
	0380h	BCC (no version claimed)
	0B20h	EPI (no version claimed)
	0B3Ch	EPI ANSI INCITS TR-23 1999
	0B3Bh	EPI T10/1134 revision 16
	0AC0h	Fast-20 (no version claimed)
	0ADCh	Fast-20 ANSI INCITS 277-1996
	0ADBh	Fast-20 T10/1071 revision 06
	0EA0h	FC 10GFC (no version claimed)
	0EA3h	FC 10GFC ISO/IEC 14165-116
	0EA2h	FC 10GFC ANSI INCITS 364-2003
	0EA5h	FC 10GFC ISO/IEC 14165-116 with AM1
	0EA6h	FC 10GFC ANSI INCITS 364-2003 with AM1 ANSI INCITS 364/AM1-2007 
	0D40h	FC-AL (no version claimed)
	0D5Ch	FC-AL ANSI INCITS 272-1996
	0D60h	FC-AL-2 (no version claimed)
	0D65h	FC-AL-2 ISO/IEC 14165-122 with AM1 & AM2
	0D63h	FC-AL-2 ANSI INCITS 332-1999 with AM1-2003 & AM2-2006
	0D64h	FC-AL-2 ANSI INCITS 332-1999 with Amnd 2 AM2-2006
	0D7Dh	FC-AL-2 ANSI INCITS 332-1999 with Amnd 1 AM1-2003
	0D7Ch	FC-AL-2 ANSI INCITS 332-1999
	0D61h	FC-AL-2 T11/1133-D revision 7.0
	12E0h	FC-DA (no version claimed)
	12E9h	FC-DA ISO/IEC 14165-341
	12E8h	FC-DA ANSI INCITS TR-36 2004
	12E2h	FC-DA T11/1513-DT revision 3.1
	12C0h	FC-DA-2 (no version claimed)
	12C3h	FC-DA-2 T11/1870DT revision 1.04
	12C5h	FC-DA-2 T11/1870DT revision 1.06
	1320h	FC-FLA (no version claimed)
	133Ch	FC-FLA ANSI INCITS TR-20 1998
	133Bh	FC-FLA T11/1235 revision 7
	0DA0h	FC-FS (no version claimed)
	0DBDh	FC-FS ISO/IEC 14165-251
	0DBCh	FC-FS ANSI INCITS 373-2003
	0DB7h	FC-FS T11/1331-D revision 1.2
	0DB8h	FC-FS T11/1331-D revision 1.7
	0E00h	FC-FS-2 (no version claimed)
	0E03h	FC-FS-2 ANSI INCITS 242-2007 with AM1 ANSI INCITS 242/AM1-2007 
	0E02h	FC-FS-2 ANSI INCITS 242-2007
	0EE0h	FC-FS-3 (no version claimed)
	0EEBh	FC-FS-3 ANSI INCITS 470-2011
	0EE2h	FC-FS-3 T11/1861-D revision 0.9
	0EE7h	FC-FS-3 T11/1861-D revision 1.0
	0EE9h	FC-FS-3 T11/1861-D revision 1.10
	0F60h	FC-FS-4 (no version claimed)
	0E20h	FC-LS (no version claimed)
	0E29h	FC-LS ANSI INCITS 433-2007
	0E21h	FC-LS T11/1620-D revision 1.62
	0F00h	FC-LS-2 (no version claimed)
	0F07h	FC-LS-2 ANSI INCITS 477-2011
	0F03h	FC-LS-2 T11/2103-D revision 2.11
	0F05h	FC-LS-2 T11/2103-D revision 2.21
	0F80h	FC-LS-3 (no version claimed)
	08C0h	FCP (no version claimed)
	08DCh	FCP ANSI INCITS 269-1996
	08DBh	FCP T10/0993-D revision 12
	0D20h	FC-PH (no version claimed)
	0D3Bh	FC-PH ANSI INCITS 230-1994
	0D3Ch	FC-PH ANSI INCITS 230-1994 with Amnd 1 ANSI INCITS 230/AM1-1996 
	0D80h	FC-PH-3 (no version claimed)
	0D9Ch	FC-PH-3 ANSI INCITS 303-1998
	0DC0h	FC-PI (no version claimed)
	0DDCh	FC-PI ANSI INCITS 352-2002
	0DE0h	FC-PI-2 (no version claimed)
	0DE4h	FC-PI-2 ANSI INCITS 404-2006
	0DE2h	FC-PI-2 T11/1506-D revision 5.0
	0E60h	FC-PI-3 (no version claimed)
	0E6Eh	FC-PI-3 ANSI INCITS 460-2011
	0E62h	FC-PI-3 T11/1625-D revision 2.0
	0E68h	FC-PI-3 T11/1625-D revision 2.1
	0E6Ah	FC-PI-3 T11/1625-D revision 4.0
	0E80h	FC-PI-4 (no version claimed)
	0E88h	FC-PI-4 ANSI INCITS 450-2009
	0E82h	FC-PI-4 T11/1647-D revision 8.0
	0F20h	FC-PI-5 (no version claimed)
	0F2Eh	FC-PI-5 ANSI INCITS 479-2011
	0F27h	FC-PI-5 T11/2118-D revision 2.00
	0F28h	FC-PI-5 T11/2118-D revision 3.00
	0F2Ah	FC-PI-5 T11/2118-D revision 6.00
	0F2Bh	FC-PI-5 T11/2118-D revision 6.10
	0F40h	FC-PI-6 (no version claimed)
	1340h	FC-PLDA (no version claimed)
	135Ch	FC-PLDA ANSI INCITS TR-19 1998
	135Bh	FC-PLDA T11/1162 revision 2.1
	0900h	FCP-2 (no version claimed)
	0917h	FCP-2 ANSI INCITS 350-2003
	0918h	FCP-2 T10/1144-D revision 8
	0901h	FCP-2 T10/1144-D revision 4
	0915h	FCP-2 T10/1144-D revision 7
	0916h	FCP-2 T10/1144-D revision 7a
	0A00h	FCP-3 (no version claimed)
	0A1Ch	FCP-3 ISO/IEC 14776-223
	0A11h	FCP-3 ANSI INCITS 416-2006
	0A0Fh	FCP-3 T10/1560-D revision 4
	0A07h	FCP-3 T10/1560-D revision 3f
	0A40h	FCP-4 (no version claimed)
	0A46h	FCP-4 ANSI INCITS 481-2011
	0A42h	FCP-4 T10/1828-D revision 01
	0A44h	FCP-4 T10/1828-D revision 02
	0A45h	FCP-4 T10/1828-D revision 02b
	12A0h	FC-SCM (no version claimed)
	12A3h	FC-SCM T11/1824DT revision 1.0
	12A5h	FC-SCM T11/1824DT revision 1.1
	12A7h	FC-SCM T11/1824DT revision 1.4
	0E40h	FC-SP (no version claimed)
	0E45h	FC-SP ANSI INCITS 426-2007
	0E42h	FC-SP T11/1570-D revision 1.6
	0EC0h	FC-SP-2 (no version claimed)
	1300h	FC-Tape (no version claimed)
	131Ch	FC-Tape ANSI INCITS TR-24 1999
	131Bh	FC-Tape T11/1315 revision 1.17
	1301h	FC-Tape T11/1315 revision 1.16
	14A0h	EEE 1394 (no version claimed)
	14BDh	ANSI IEEE 1394-1995
	14C0h	EEE 1394a (no version claimed)
	14E0h	EEE 1394b (no version claimed)
	FFC0h	EEE 1667 (no version claimed)
	FFC1h	EEE 1667-2006
	FFC2h	EEE 1667-2009
	0960h	SCSI (no version claimed)
	0140h	MMC (no version claimed)
	015Ch	MMC ANSI INCITS 304-1997
	015Bh	MMC T10/1048-D revision 10a
	0240h	MMC-2 (no version claimed)
	025Ch	MMC-2 ANSI INCITS 333-2000
	025Bh	MMC-2 T10/1228-D revision 11a
	0255h	MMC-2 T10/1228-D revision 11
	02A0h	MMC-3 (no version claimed)
	02B8h	MMC-3 ANSI INCITS 360-2002
	02B6h	MMC-3 T10/1363-D revision 10g
	02B5h	MMC-3 T10/1363-D revision 9
	03A0h	MMC-4 (no version claimed)
	03BFh	MMC-4 ANSI INCITS 401-2005
	03BDh	MMC-4 T10/1545-D revision 3
	03BEh	MMC-4 T10/1545-D revision 3d
	03B0h	MMC-4 T10/1545-D revision 5
	03B1h	MMC-4 T10/1545-D revision 5a
	0420h	MMC-5 (no version claimed)
	0432h	MMC-5 T10/1675-D revision 04
	0434h	MMC-5 ANSI INCITS 430-2007
	042Fh	MMC-5 T10/1675-D revision 03
	0431h	MMC-5 T10/1675-D revision 03b
	04E0h	MMC-6 (no version claimed)
	04E3h	MMC-6 T10/1836-D revision 02b
	04E5h	MMC-6 T10/1836-D revision 02g
	04E6h	MMC-6 ANSI INCITS 468-2010
	0280h	OCRW (no version claimed)
	029Eh	OCRW ISO/IEC 14776-381
	0340h	OSD (no version claimed)
	0356h	OSD ANSI INCITS 400-2004
	0355h	OSD T10/1355-D revision 10
	0341h	OSD T10/1355-D revision 0
	0342h	OSD T10/1355-D revision 7a
	0343h	OSD T10/1355-D revision 8
	0344h	OSD T10/1355-D revision 9
	0440h	OSD-2 (no version claimed)
	0448h	OSD-2 ANSI INCITS 458-2011
	0444h	OSD-2 T10/1729-D revision 4
	0446h	OSD-2 T10/1729-D revision 5
	0560h	OSD-3 (no version claimed)
	2200h	PQI (no version claimed)
	0220h	RBC (no version claimed)
	023Ch	RBC ANSI INCITS 330-2000
	0238h	RBC T10/1240-D revision 10a
	0020h	SAM (no version claimed)
	003Ch	SAM ANSI INCITS 270-1996
	003Bh	SAM T10/0994-D revision 18
	0040h	SAM-2 (no version claimed)
	005Eh	SAM-2 ISO/IEC 14776-412
	005Ch	SAM-2 ANSI INCITS 366-2003
	0055h	SAM-2 T10/1157-D revision 24
	0054h	SAM-2 T10/1157-D revision 23
	0060h	SAM-3 (no version claimed)
	0077h	SAM-3 ANSI INCITS 402-2005
	0076h	SAM-3 T10/1561-D revision 14
	0062h	SAM-3 T10/1561-D revision 7
	0075h	SAM-3 T10/1561-D revision 13
	0080h	SAM-4 (no version claimed)
	0092h	SAM-4 ISO/IEC 14776-414
	0090h	SAM-4 ANSI INCITS 447-2008
	0087h	SAM-4 T10/1683-D revision 13
	008Bh	SAM-4 T10/1683-D revision 14
	00A0h	SAM-5 (no version claimed)
	00A2h	SAM-5 T10/2104-D revision 4
	0BE0h	SAS (no version claimed)
	0BFDh	SAS ANSI INCITS 376-2003
	0BFCh	SAS T10/1562-D revision 05
	0BE1h	SAS T10/1562-D revision 01
	0BF5h	SAS T10/1562-D revision 03
	0BFAh	SAS T10/1562-D revision 04
	0BFBh	SAS T10/1562-D revision 04
	0C00h	SAS-1.1 (no version claimed)
	0C12h	SAS-1.1 ISO/IEC 14776-151
	0C11h	SAS-1.1 ANSI INCITS 417-2006
	0C07h	SAS-1.1 T10/1601-D revision 9
	0C0Fh	SAS-1.1 T10/1601-D revision 10
	0C20h	SAS-2 (no version claimed)
	0C2Ah	SAS-2 ANSI INCITS 457-2010
	0C23h	SAS-2 T10/1760-D revision 14
	0C27h	SAS-2 T10/1760-D revision 15
	0C28h	SAS-2 T10/1760-D revision 16
	0C40h	SAS-2.1 (no version claimed)
	0C4Eh	SAS-2.1 ANSI INCITS 478-2011
	0C48h	SAS-2.1 T10/2125-D revision 04
	0C4Ah	SAS-2.1 T10/2125-D revision 06
	0C4Bh	SAS-2.1 T10/2125-D revision 07
	0C60h	SAS-3 (no version claimed)
	1EA0h	SAT (no version claimed)
	1EADh	SAT ANSI INCITS 431-2007
	1EABh	SAT T10/1711-D revision 9
	1EA7h	SAT T10/1711-D revision 8
	1EC0h	SAT-2 (no version claimed)
	1ECAh	SAT-2 ANSI INCITS 465-2010
	1EC4h	SAT-2 T10/1826-D revision 06
	1EC8h	SAT-2 T10/1826-D revision 09
	1EE0h	SAT-3 (no version claimed)
	1F00h	SAT-4 (no version claimed)
	0180h	SBC (no version claimed)
	019Ch	SBC ANSI INCITS 306-1998
	019Bh	SBC T10/0996-D revision 08c
	0320h	SBC-2 (no version claimed)
	033Eh	SBC-2 ISO/IEC 14776-322
	033Dh	SBC-2 ANSI INCITS 405-2005
	033Bh	SBC-2 T10/1417-D revision 16
	0322h	SBC-2 T10/1417-D revision 5a
	0324h	SBC-2 T10/1417-D revision 15
	04C0h	SBC-3 (no version claimed)
	08E0h	SBP-2 (no version claimed)
	08FCh	SBP-2 ANSI INCITS 325-1998
	08FBh	SBP-2 T10/1155-D revision 04
	0980h	SBP-3 (no version claimed)
	099Ch	SBP-3 ANSI INCITS 375-2004
	099Bh	SBP-3 T10/1467-D revision 5
	0982h	SBP-3 T10/1467-D revision 1f
	0994h	SBP-3 T10/1467-D revision 3
	099Ah	SBP-3 T10/1467-D revision 4
	0160h	SCC (no version claimed)
	017Ch	SCC ANSI INCITS 276-1997
	017Bh	SCC T10/1047-D revision 06c
	01E0h	SCC-2 (no version claimed)
	01FCh	SCC-2 ANSI INCITS 318-1998
	01FBh	SCC-2 T10/1125-D revision 04
	01C0h	SES (no version claimed)
	01DCh	SES ANSI INCITS 305-1998
	01DBh	SES T10/1212-D revision 08b
	01DEh	SES ANSI INCITS 305-1998 w/ Amendment ANSI INCITS.305/AM1-2000 
	01DDh	SES T10/1212 revision 08b w/ Amendment ANSI INCITS.305/AM1-2000 
	03E0h	SES-2 (no version claimed)
	03F2h	SES-2 ISO/IEC 14776-372
	03F0h	SES-2 ANSI INCITS 448-2008
	03E1h	SES-2 T10/1559-D revision 16
	03E7h	SES-2 T10/1559-D revision 19
	03EBh	SES-2 T10/1559-D revision 20
	0580h	SES-3 (no version claimed)
	08A0h	SIP (no version claimed)
	08BCh	SIP ANSI INCITS 292-1997
	08BBh	SIP T10/0856-D revision 10
	01A0h	SMC (no version claimed)
	01BEh	SMC ISO/IEC 14776-351
	01BCh	SMC ANSI INCITS 314-1998
	01BBh	SMC T10/0999-D revision 10a
	02E0h	SMC-2 (no version claimed)
	02FEh	SMC-2 ANSI INCITS 382-2004
	02FDh	SMC-2 T10/1383-D revision 7
	02F5h	SMC-2 T10/1383-D revision 5
	02FCh	SMC-2 T10/1383-D revision 6
	0480h	SMC-3 (no version claimed)
	0482h	SMC-3 T10/1730-D revision 15
	0484h	SMC-3 T10/1730-D revision 16
	21E0h	SOP (no version claimed)
	0120h	SPC (no version claimed)
	013Ch	SPC ANSI INCITS 301-1997
	013Bh	SPC T10/0995-D revision 11a
	0260h	SPC-2 (no version claimed)
	0278h	SPC-2 ISO/IEC 14776-452
	0277h	SPC-2 ANSI INCITS 351-2001
	0276h	SPC-2 T10/1236-D revision 20
	0267h	SPC-2 T10/1236-D revision 12
	0269h	SPC-2 T10/1236-D revision 18
	0275h	SPC-2 T10/1236-D revision 19
	0300h	SPC-3 (no version claimed)
	0316h	SPC-3 ISO/IEC 14776-453
	0314h	SPC-3 ANSI INCITS 408-2005
	0301h	SPC-3 T10/1416-D revision 7
	0307h	SPC-3 T10/1416-D revision 21
	030Fh	SPC-3 T10/1416-D revision 22
	0312h	SPC-3 T10/1416-D revision 23
	0460h	SPC-4 (no version claimed)
	0461h	SPC-4 T10/1731-D revision 16
	0462h	SPC-4 T10/1731-D revision 18
	0463h	SPC-4 T10/1731-D revision 23
	0AA0h	SPI (no version claimed)
	0ABAh	SPI ANSI INCITS 253-1995
	0AB9h	SPI T10/0855-D revision 15a
	0ABCh	SPI ANSI INCITS 253-1995 with SPI Amnd ANSI INCITS 253/AM1-1998 
	0ABBh	SPI T10/0855-D revision 15a with SPI Amnd revision 3a
	0AE0h	SPI-2 (no version claimed)
	0AFCh	SPI-2 ANSI INCITS 302-1999
	0AFBh	SPI-2 T10/1142-D revision 20b
	0B00h	SPI-3 (no version claimed)
	0B1Ch	SPI-3 ANSI INCITS 336-2000
	0B1Ah	SPI-3 T10/1302-D revision 14
	0B18h	SPI-3 T10/1302-D revision 10
	0B19h	SPI-3 T10/1302-D revision 13a
	0B40h	SPI-4 (no version claimed)
	0B56h	SPI-4 ANSI INCITS 362-2002
	0B54h	SPI-4 T10/1365-D revision 7
	0B55h	SPI-4 T10/1365-D revision 9
	0B59h	SPI-4 T10/1365-D revision 10
	0B60h	SPI-5 (no version claimed)
	0B7Ch	SPI-5 ANSI INCITS 367-2003
	0B7Bh	SPI-5 T10/1525-D revision 6
	0B79h	SPI-5 T10/1525-D revision 3
	0B7Ah	SPI-5 T10/1525-D revision 5
	20A0h	SPL (no version claimed)
	20A7h	SPL ANSI INCITS 476-2011
	20A3h	SPL T10/2124-D revision 6a
	20A5h	SPL T10/2124-D revision 7
	20C0h	SPL-2 (no version claimed)
	20C2h	SPL-2 T10/2228-D revision 4
	20E0h	SPL-3 (no version claimed)
	0940h	SRP (no version claimed)
	095Ch	SRP ANSI INCITS 365-2002
	0955h	SRP T10/1415-D revision 16a
	0954h	SRP T10/1415-D revision 10
	1360h	SSA-PH2 (no version claimed)
	137Ch	SSA-PH2 ANSI INCITS 293-1996
	137Bh	SSA-PH2 T10.1/1145-D revision 09c
	1380h	SSA-PH3 (no version claimed)
	139Ch	SSA-PH3 ANSI INCITS 307-1998
	139Bh	SSA-PH3 T10.1/1146-D revision 05b
	0880h	SSA-S2P (no version claimed)
	089Ch	SSA-S2P ANSI INCITS 294-1996
	089Bh	SSA-S2P T10.1/1121-D revision 07b
	0860h	SSA-S3P (no version claimed)
	087Ch	SSA-S3P ANSI INCITS 309-1998
	087Bh	SSA-S3P T10.1/1051-D revision 05b
	0840h	SSA-TL1 (no version claimed)
	085Ch	SSA-TL1 ANSI INCITS 295-1996
	085Bh	SSA-TL1 T10.1/0989-D revision 10b
	0820h	SSA-TL2 (no version claimed)
	083Ch	SSA-TL2 ANSI INCITS 308-1998
	083Bh	SSA-TL2 T10.1/1147-D revision 05b
	0200h	SSC (no version claimed)
	021Ch	SSC ANSI INCITS 335-2000
	0207h	SSC T10/0997-D revision 22
	0201h	SSC T10/0997-D revision 17
	0360h	SSC-2 (no version claimed)
	037Dh	SSC-2 ANSI INCITS 380-2003
	0375h	SSC-2 T10/1434-D revision 9
	0374h	SSC-2 T10/1434-D revision 7
	0400h	SSC-3 (no version claimed)
	0409h	SSC-3 ANSI INCITS 467-2011
	0403h	SSC-3 T10/1611-D revision 04a
	0407h	SSC-3 T10/1611-D revision 05
	0520h	SSC-4 (no version claimed)
	0523h	SSC-4 T10/2123-D revision 2
	0920h	SST (no version claimed)
	0935h	SST T10/1380-D revision 8b
	1740h	UAS (no version claimed)
	1743h	UAS T10/2095-D revision 02
	1747h	UAS T10/2095-D revision 04
	1748h	UAS ANSI INCITS 471-2010
	1780h	UAS-2 (no version claimed)
	1728h	Universal Serial Bus Specification, Revision 1.1
	1729h	Universal Serial Bus Specification, Revision 2.0
	1730h	USB Mass Storage Class Bulk-Only Transport, Revision 1.0
	0000h	Version Descriptor Not Supported or No Standard Identified
	all others	Reserved

6.6.3 SCSI Parallel Interface specific INQUIRY data

   Portions of bytes 6 and 7 and all of byte 56 of the standard INQUIRY data shall be used only by SCSI target
   devices that implement the SCSI Parallel Interface. These fields are noted in table 164. For details on how the
   SPI-specific fields relate to the SCSI Parallel Interface see SPI-n (where n is 2 or greater). Table 170 shows just the
   SPI-specific standard INQUIRY fields. The definitions of the SCSI Parallel Interface specific fields shall be as
   follows.
   
                     Table 170 — SPI-specific standard INQUIRY bits
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    6    |    see (Table 164 — Standard INQUIRY data format)     |ADDR16 |
   |-------------------------------------------------------------------------|
   |    7    | see table 164 |WBUS16 | SYNC  |asT-164|Obsolet| see table 164 |
   |-------------------------------------------------------------------------|
   |         |                     ...                                       |
   |-------------------------------------------------------------------------|
   |    56   |          Reserved             |   CLOCKING    |  QAS  |  IUS  |
   |-------------------------------------------------------------------------|
  
   A wide SCSI address 16 (ADDR16) bit of one indicates that the SCSI target device supports 16-bit wide SCSI
   addresses. A value of zero indicates that the SCSI target device does not support 16-bit wide SCSI addresses.  
   
   A wide bus 16 (WBUS16) bit of one indicates that the SCSI target device supports 16-bit wide data transfers. A
   value of zero indicates that the SCSI target device does not support 16-bit wide data transfers.

   A synchronous transfer (SYNC) bit of one indicates that the SCSI target device supports synchronous data transfer.
   A value of zero indicates the SCSI target device does not support synchronous data transfer.

   The obsolete bit 2 in byte 7 indicates whether the SCSI target device supports an obsolete data transfers
   management mechanism defined in SPI-2.

   Table 171 defines the relationships between the ADDR16 and WBUS16 bits.   
   
      Table 171 — Maximum logical device configuration table
	  ADDR16  WBUS16  Description
        0       0     8 bit wide data path on a single cable with 8 SCSI IDs supported
        0       1     16 bit wide data path on a single cable with 8 SCSI IDs supported
        1       1     16 bit wide data path on a single cable with 16 SCSI IDs supported
				  
   The CLOCKING field shall not apply to asynchronous transfers and is defined in table 172.			  
	  00b - Indicates the target port supports only ST
	  01b - Indicates the target port supports only DT
	  10b - Reserved
	  11b - Indicates the target port supports ST and DT
	  
   A quick arbitration and selection supported (QAS) bit of one indicates that the target port supports quick arbitration
   and selection. A value of zero indicates that the target port does not support quick arbitration and selection.

   An information units supported (IUS) bit of one indicates that the SCSI target device supports information unit
   transfers. A value of zero indicates that the SCSI target device does not support information unit transfers.

   NOTE 32 - The acronyms ST and DT and the terms 'quick arbitration and selection' and 'information units' are
   defined in SPI-5.
	
	
	
	

   
   
</pre>
 * 
 *
 */
public class InquiryStandardData {
	public enum PeripheralQualifer {

 /**
<pre>
000b - A peripheral device having the specified peripheral device type is connected to this 
       logical unit. If the device server is unable to determine whether or not a peripheral 
       device is connected, it also shall use this peripheral qualifier. This peripheral qualifier 
       does not mean that the peripheral device connected to the logical unit is ready for 
       access..
</pre>
 */
		connected((byte) 0x00),

 /** 
<pre>
001b - A peripheral device having the specified peripheral device type is not connected to this 
       logical unit. However, the device server is capable of supporting the specified periph-eral 
       device type on this logical unit. 

</pre>
 */
	notConnected((byte) 0x01),
	
/**
<pre>
011b - The device server is not capable of supporting a peripheral device on this logical unit. 
       For this peripheral qualifier the peripheral device type shall be set to 1Fh. All other 
       peripheral device type values are reserved for this peripheral qualifier.

</pre>
 */
	notSupported((byte) 0x03),

/**
<pre>
100b to 111b - Vendor specific
</pre>
 */
	Vendor((byte) 0x04);
	    private final byte value;
	    private static Map<Byte , PeripheralQualifer> mapping;
	    static {
	    	PeripheralQualifer.mapping = new HashMap<Byte , PeripheralQualifer>();
	        for (PeripheralQualifer s : values()) {
	        	PeripheralQualifer.mapping.put(s.value, s);
	        }
	    }
	    private PeripheralQualifer (final byte newValue) {
	        value = newValue;
	    }
	    public final byte value () {
	        return value;
	    }
	    public static final PeripheralQualifer valueOf (final byte value) {
	        return PeripheralQualifer.mapping.get(value);
	    }
	}

	public enum PeripheralDeviceType {

 /**
<pre>
00h - Direct access block device (e.g., magnetic disk).
</pre>
 */
		DirectBlockDevice((byte) 0x00),

/**
<pre>
01h - Sequential-access device (e.g., magnetic tape).
</pre>
 */
		SequentialDevice((byte) 0x01),
		
/**
<pre>
02h - Printer device.
</pre>
 */
		PrinterDevice((byte) 0x02),

/**
<pre>
03h - Processor device.
</pre>
 */
		ProcessorDevice((byte) 0x03),

/**
<pre>
04h - Write-once device (e.g., some optical disks).
</pre>
 */
		WriteOnceDevice((byte) 0x04),
		
/**
<pre>
05h - CD/DVD device.
</pre>
 */
		CDorDVDDevice((byte) 0x05),
		
/**
<pre>
06h - Scanner device (obsolete).
</pre>
 */
		ScannerDevice((byte) 0x06),
		
/**
<pre>
07h - Optical memory device (e.g., some optical disks).
</pre>
 */
		OpticalMemoryDevice((byte) 0x07),
		
/**
<pre>
08h - Media changer device (e.g., jukeboxes).
</pre>
 */
		MediaChangerDevice((byte) 0x08),
		
/**
<pre>
09h - Communications device (obsolete).
</pre>
 */
		CommunicationsDevice((byte) 0x09),
		
/**
<pre>
0Ch - Storage array controller device (e.g., RAID).
</pre>
 */
		ArrayDevice((byte) 0x0C),
		
 /** 
<pre>
0Dh - Enclosure services device.
</pre>
 */
		EnclosureServicesDevice((byte) 0x0D),
	
/**
<pre>
0Eh - Simplified direct-access device (e.g., magnetic disk).
</pre>
 */
		SimplifiedDirectDevice((byte) 0x0E),

/**
<pre>
0Fh - Optical card reader/writer device.
</pre>
 */
		OpticalCardDevice((byte) 0x0F),

		
/**
<pre>
10h - Bridge Controller Commands.
</pre>
 */
		BridgeCommands((byte) 0x10),
		
/**
<pre>
11h - Object-based Storage Device.
</pre>
 */
		ObjectStorageDevice((byte) 0x11),
		
/**
<pre>
12h - Automation/Drive Interface.
</pre>
 */
		AutomationDevice((byte) 0x12),
		
/**
<pre>
13h - Security manager device.
</pre>
 */
		SecurityDevice((byte) 0x13),
		
/**
<pre>
1Eh - Well known logical unit.
</pre>
 */
		WellKnownLUN((byte) 0x1E),
		
/**
<pre>
1Fh - Unknown or no device type.
</pre>
 */
		UnknownDevice((byte) 0x1F);
		
	    private final byte value;
	    private static Map<Byte , PeripheralDeviceType> mapping;
	    static {
	    	PeripheralDeviceType.mapping = new HashMap<Byte , PeripheralDeviceType>();
	        for (PeripheralDeviceType s : values()) {
	        	PeripheralDeviceType.mapping.put(s.value, s);
	        }
	    }
	    private PeripheralDeviceType (final byte newValue) {
	        value = newValue;
	    }
	    public final byte value () {
	        return value;
	    }
	    public static final PeripheralDeviceType valueOf (final byte value) {
	        return PeripheralDeviceType.mapping.get(value);
	    }
	}

	public enum Version {

 /**
<pre>
00h - The device server does not claim conformance to any standard.
</pre>
 */
		noStandard((byte) 0x00),

 /** 
<pre>
03h - The device server complies to ANSI INCITS 301-1997 (a withdrawn standard).
</pre>
 */
		ANSI_INCITS_301_1997((byte) 0x03),
	
/**
<pre>
04h - The device server complies to ANSI INCITS 351-2001 (SPC-2).
</pre>
 */
		ANSI_INCITS_351_2001((byte) 0x04),

/**
<pre>
05h - The device server complies to ANSI INCITS 408-2005 (SPC-3).
</pre>
 */
		ANSI_INCITS_408_2005((byte) 0x05),

/**
<pre>
06h - The device server complies to this standard.
</pre>
 */
		Standard((byte) 0x06);
		
	    private final byte value;
	    private static Map<Byte , Version> mapping;
	    static {
	    	Version.mapping = new HashMap<Byte , Version>();
	        for (Version s : values()) {
	        	Version.mapping.put(s.value, s);
	        }
	    }
	    private Version (final byte newValue) {
	        value = newValue;
	    }
	    public final byte value () {
	        return value;
	    }
	    public static final Version valueOf (final byte value) {
	        return Version.mapping.get(value);
	    }
	}

	public enum TPGS {

 /**
<pre>
00b - The logical unit does not support asymmetric logical unit access or supports a form of asymmetric 
      access that is vendor specific. Neither the REPORT TARGET GROUPS nor the SET TARGET 
      PORT GROUPS commands is supported.
</pre>
 */
		noAsyAccess((byte) 0x00),

 /** 
<pre>
01b - The logical unit supports only implicit asymmetric logical unit access (see 5.11.2.7). The logical 
      unit is capable of changing target port asymmetric access states without a SET TARGET PORT 
      GROUPS command. The REPORT TARGET PORT GROUPS command is supported and the 
      SET TARGET PORT GROUPS command is not supported.
</pre>
 */
		ImplicitAsyAccess((byte) 0x01),
	
/**
<pre>
10b - The logical unit supports only explicit asymmetric logical unit access (see 5.11.2.8). The logical 
      unit only changes target port asymmetric access states as requested with the SET TARGET 
      PORT GROUPS command. Both the REPORT TARGET PORT GROUPS command and the SET 
      TARGET PORT GROUPS command are supported.
</pre>
 */
		ExplicitAsyAccess((byte) 0x02),

/**
<pre>
11b - The logical unit supports both explicit and implicit asymmetric logical unit access. Both the 
      REPORT TARGET PORT GROUPS command and the SET TARGET PORT GROUPS 
      commands are supported.
</pre>
 */
		BothAsyAccess((byte) 0x03);

		
	    private final byte value;
	    private static Map<Byte , TPGS> mapping;
	    static {
	    	TPGS.mapping = new HashMap<Byte , TPGS>();
	        for (TPGS s : values()) {
	        	TPGS.mapping.put(s.value, s);
	        }
	    }
	    private TPGS (final byte newValue) {
	        value = newValue;
	    }
	    public final byte value () {
	        return value;
	    }
	    public static final TPGS valueOf (final byte value) {
	        return TPGS.mapping.get(value);
	    }
	}

	  
	private byte peripheralQulifer;
	private byte peripheralType;
	private boolean RMB;
	private byte version;
	private boolean NORMACA;
	private boolean HISUP;
	private byte responseDataFormat = 0x02;
	private byte additionalLength = 91;
	private boolean SCCS;
	private boolean ACC;
	private byte tpgs;
	private boolean threePC;
	private boolean PROTECT;
	private boolean ENCSERV;
	private boolean VS1;
	private boolean MULTIP;
	private boolean ADDR16;
	private boolean WBUS16;
	private boolean SYNC;
	private boolean CMDQUE;
	private boolean VS2;
	private byte[] T10VendorID = new byte[8];
	private byte[] productID = new byte[16];
	private byte[] productRevisionLevel = new byte[4];
	public InquiryStandardData(){}
	public InquiryStandardData(byte[] CDB) throws Exception{
		if(CDB.length!=36)throw new Exception("illegic CDB Size , the proper length is 36");
		peripheralQulifer = (byte)((CDB[0] & 0xE0) >> 5);
		peripheralType = (byte)(CDB[0] & 0x1F);
		RMB = (ByteUtil.getBit(CDB[1], 0) == 1);
		version = CDB[2];
		NORMACA=(ByteUtil.getBit(CDB[3], 2) == 1);
		HISUP=(ByteUtil.getBit(CDB[3], 3) == 1);
		additionalLength = CDB[4];
		SCCS=(ByteUtil.getBit(CDB[5], 0) == 1);
		ACC=(ByteUtil.getBit(CDB[5], 1) == 1);
		tpgs=(byte)((CDB[5] & 0x30) >> 4);
		threePC=(ByteUtil.getBit(CDB[5], 4) == 1);
		PROTECT=(ByteUtil.getBit(CDB[5], 7) == 1);
		ENCSERV=(ByteUtil.getBit(CDB[6], 1) == 1);
		VS1=(ByteUtil.getBit(CDB[6], 2) == 1);
		MULTIP=(ByteUtil.getBit(CDB[6], 3) == 1);
		ADDR16=(ByteUtil.getBit(CDB[6], 7) == 1);
		WBUS16=(ByteUtil.getBit(CDB[7], 2) == 1);
		SYNC=(ByteUtil.getBit(CDB[7], 3) == 1);
		CMDQUE=(ByteUtil.getBit(CDB[7], 6) == 1);
		VS2=(ByteUtil.getBit(CDB[7], 7) == 1);
		System.arraycopy(CDB, 8, T10VendorID, 0, T10VendorID.length); 
		System.arraycopy(CDB, 16, productID, 0, productID.length); 
		System.arraycopy(CDB, 32, productRevisionLevel, 0, productRevisionLevel.length); 
	}
	
	public PeripheralQualifer getPeripheralQulifer() {
		return PeripheralQualifer.valueOf(this.peripheralQulifer);
	}
	public void setPeripheralQulifer(PeripheralQualifer peripheralQulifer) {
		this.peripheralQulifer = peripheralQulifer.value;
	}
	public PeripheralDeviceType getPeripheralType() {
		return PeripheralDeviceType.valueOf(this.peripheralType);
	}
	public void setPeripheralType(PeripheralDeviceType peripheralType) {
		this.peripheralType = peripheralType.value;
	}
	public boolean getRMB() {
		return RMB;
	}
	public void setRMB(boolean rMB) {
		RMB = rMB;
	}
	public Version getVersion() {
		return Version.valueOf(this.version);
	}
	public void setVersion(Version version) {
		this.version = version.value;
	}
	public boolean getNORMACA() {
		return this.NORMACA;
	}
	public void setNORMACA(boolean nORMACA) {
		this.NORMACA = nORMACA;
	}
	public boolean getHISUP() {
		return this.HISUP;
	}
	public void setHISUP(boolean hISUP) {
		this.HISUP = hISUP;
	}
	public byte getResponseDataFormat() {
		return responseDataFormat;
	}
	public byte getAdditionalLength() {
		return additionalLength;
	}
	public boolean getSCCS() {
		return this.SCCS;
	}
	public void setSCCS(boolean sCCS) {
		this.SCCS = sCCS;
	}
	public boolean getACC() {
		return this.ACC;
	}
	public void setACC(boolean aCC) {
		this.ACC = aCC;
	}
	public TPGS getTPGS() {
		return TPGS.valueOf(this.tpgs);
	}
	public void setTPGS(TPGS tPGS) {
		this.tpgs = tPGS.value;
	}
	public boolean get3PC() {
		return this.threePC;
	}
	public void set3PC(boolean threePC) {
		this.threePC = threePC;
	}
	public boolean getPROTECT() {
		return this.PROTECT;
	}
	public void setPROTECT(boolean pROTECT) {
		this.PROTECT = pROTECT;
	}
	public boolean getENCSERV() {
		return this.ENCSERV;
	}
	public void setENCSERV(boolean eNCSERV) {
		this.ENCSERV = eNCSERV;
	}
	public boolean getVS1() {
		return this.VS1;
	}
	public void setVS1(boolean vS1) {
		this.VS1 = vS1;
	}
	public boolean getMULTIP() {
		return this.MULTIP;
	}
	public void setMULTIP(boolean mULTIP) {
		this.MULTIP = mULTIP;
	}
	public boolean getADDR16() {
		return this.ADDR16;
	}
	public void setADDR16(boolean aDDR16) {
		this.ADDR16 = aDDR16;
	}
	public boolean getWBUS16() {
		return this.WBUS16;
	}
	public void setWBUS16(boolean wBUS16) {
		this.WBUS16 = wBUS16;
	}
	public boolean getSYNC() {
		return this.SYNC;
	}
	public void setSYNC(boolean sYNC) {
		this.SYNC = sYNC;
	}
	public boolean getCMDQUE() {
		return this.CMDQUE;
	}
	public void setCMDQUE(boolean cMDQUE) {
		this.CMDQUE = cMDQUE;
	}
	public boolean getVS2() {
		return this.VS2;
	}
	public void setVS2(boolean vS2) {
		this.VS2 = vS2;
	}
	public String getT10VendorID() {
		return getASCIIString(this.T10VendorID);
	}
	public void setT10VendorID(String t10VendorID) {
		setASCIIString(this.T10VendorID,t10VendorID);
	}
	public String getProductID() {
		return getASCIIString(this.productID);
	}
	public void setProductID(String productID) {
		setASCIIString(this.productID,productID);
	}
	public String getProductRevisionLevel() {
		return getASCIIString(this.productRevisionLevel);
	}
	public void setProductRevisionLevel(String productRevisionLevel) {
		setASCIIString(this.productRevisionLevel,productRevisionLevel);
	}
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" PERIPHERAL QUALIFIER : "+PeripheralQualifer.valueOf(this.peripheralQulifer));
		build.append(System.getProperty("line.separator")+" PERIPHERAL DEVICE TYPE   : "+PeripheralDeviceType.valueOf(this.peripheralType));
		build.append(System.getProperty("line.separator")+" RMB : "+this.RMB);
		build.append(System.getProperty("line.separator")+" VERSION : "+Version.valueOf(this.version));
		build.append(System.getProperty("line.separator")+" NORMACA : "+this.NORMACA);
		build.append(System.getProperty("line.separator")+" HISUP  : "+this.HISUP );
		build.append(System.getProperty("line.separator")+" RESPONSE DATA FORMAT  : "+this.responseDataFormat);
		build.append(System.getProperty("line.separator")+" ADDITIONAL LENGTH (n-4)  : "+this.additionalLength);
		build.append(System.getProperty("line.separator")+" SCCS   : "+this.SCCS);
		build.append(System.getProperty("line.separator")+" ACC   : "+this.ACC);
		build.append(System.getProperty("line.separator")+" TPGS   : "+TPGS.valueOf(this.tpgs));
		build.append(System.getProperty("line.separator")+" 3PC   : "+this.threePC);
		build.append(System.getProperty("line.separator")+" PROTECT   : "+this.PROTECT);
		build.append(System.getProperty("line.separator")+" ENCSERV   : "+this.ENCSERV);
		build.append(System.getProperty("line.separator")+" VS1   : "+this.VS1);
		build.append(System.getProperty("line.separator")+" MULTIP   : "+this.MULTIP);
		build.append(System.getProperty("line.separator")+" ADDR16   : "+this.ADDR16);
		build.append(System.getProperty("line.separator")+" WBUS16   : "+this.WBUS16);
		build.append(System.getProperty("line.separator")+" SYNC   : "+this.SYNC);
		build.append(System.getProperty("line.separator")+" CMDQUE    : "+this.CMDQUE );
		build.append(System.getProperty("line.separator")+" VS2   : "+this.VS2);
		build.append(System.getProperty("line.separator")+" T10 VENDOR IDENTIFICATION   : "+this.getASCIIString(T10VendorID));
		build.append(System.getProperty("line.separator")+" PRODUCT IDENTIFICATION   : "+this.getASCIIString(this.productID));
		build.append(System.getProperty("line.separator")+" PRODUCT REVISION LEVEL   : "+this.getASCIIString(this.productRevisionLevel));
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			byte b1 = (byte)(this.peripheralQulifer << 5);
			b1 = (byte)(b1 | this.peripheralType);
			dos.writeByte(b1);
			dos.writeByte(this.RMB?0x80:0x00);
			dos.writeByte(this.version);
			byte b3 = (byte)0x02;
			b3 = (byte)(this.NORMACA?b3 | 0x20:b3);
			b3 = (byte)(this.HISUP?b3 | 0x10:b3);
			dos.writeByte(b3);
			dos.writeByte(this.additionalLength); 
			byte b5 = (byte)(this.tpgs << 4);
			b5 = (byte)(this.SCCS?b5 | 0x80:b5);
			b5 = (byte)(this.ACC?b5 | 0x40:b5);
			b5 = (byte)(this.threePC?b5 | 0x08:b5);
			b5 = (byte)(this.PROTECT?b5 | 0x01:b5);
			dos.writeByte(b5);
			byte b6 = 0;
			b6 = (byte)(this.ENCSERV?b6 | 0x40:b6);
			b6 = (byte)(this.VS1?b6 | 0x20:b6);
			b6 = (byte)(this.MULTIP?b6 | 0x10:b6);
			b6 = (byte)(this.ADDR16?b6 | 0x01:b6);
			dos.writeByte(b6);
			byte b7 = 0;
			b7 = (byte)(this.WBUS16?b7 | 0x20:b7);
			b7 = (byte)(this.SYNC?b7 | 0x10:b7);
			b7 = (byte)(this.CMDQUE?b7 | 0x02:b7);
			b7 = (byte)(this.VS2?b7 | 0x01:b7);
			dos.writeByte(b7);
			dos.write(this.T10VendorID);
			dos.write(this.productID);
			dos.write(this.productRevisionLevel);
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		return new byte[0];
		
	}
	
	private void setASCIIString(byte[] src,String desc){
		int length = Math.min(src.length, desc.length());
		for(int i=0;i<length;i++){
			src[i] = (byte)desc.charAt(i);
		}
		for(int i=length;i<src.length;i++){
			src[i] = 0;
		}
	}
	private String getASCIIString(byte[] src){
		StringBuilder build = new StringBuilder();
		for(int i=0;i<src.length;i++){
			if(src[i]==0)continue;
			build.append((char)src[i]);
		}
		return build.toString();
	}
	public static void main(String[] args) throws Exception{
		InquiryStandardData original = new InquiryStandardData();
		original.setPeripheralQulifer(PeripheralQualifer.notSupported);
		original.setPeripheralType(PeripheralDeviceType.CDorDVDDevice);
		original.setRMB(true);
		original.setVersion(Version.ANSI_INCITS_351_2001);
		original.setNORMACA(true);
		original.setHISUP(true);
		original.setSCCS(true);
		original.setACC(false);
		original.setTPGS(TPGS.ImplicitAsyAccess);
		original.set3PC(true);
		original.setPROTECT(true);
		original.setENCSERV(true);
		original.setVS1(false);
		original.setMULTIP(true);
		original.setADDR16(true);
		original.setWBUS16(true);
		original.setSYNC(true);
		original.setCMDQUE(false);
		original.setVS2(true);
		original.setT10VendorID("fuck your ");
		original.setProductID("what are you doing ? ");
		original.setProductRevisionLevel("four");
		System.out.println(original);
		byte[] data = original.toByte();
		System.out.println("data size === "+data.length);
		InquiryStandardData after = new InquiryStandardData(data);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
