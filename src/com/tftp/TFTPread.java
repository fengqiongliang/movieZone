package com.tftp;

public final class TFTPread extends TFTPpacket
{
    byte zero = 0;
    short opcode = 1;
  private void initialize(String filename, String reqType)
  {
    this.length = (2 + filename.length() + 1 + reqType.length() + 1);
    this.message = new byte[this.length];

    put(0, opcode); 
    put(2, filename, zero); 
    put(2 + filename.length() + 1, reqType, zero);
  }

  protected TFTPread()
  {
  }

  public TFTPread(String filename) {
    initialize(filename, "octet");
  }

  public TFTPread(String filename, String reqType) {
    initialize(filename, reqType);
  }

  public String fileName()
  {
    return get(2, (byte)0); //TODO
  }

  public String requestType() {
    String fname = fileName();
    return get(2 + fname.length() + 1, (byte)0); //TODO
  }
}