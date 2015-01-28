package com.tftp;

public final class TFTPwrite extends TFTPpacket
{
  private void initialize(String filename, String reqType)
  {
    this.length = (2 + filename.length() + 1 + reqType.length() + 1);
    this.message = new byte[this.length];

    put(0, (short)2);
    put(2, filename, (byte)0);
    put(2 + filename.length() + 1, reqType, (byte)0);
  }

  protected TFTPwrite()
  {
  }

  public TFTPwrite(String filename) {
    initialize(filename, "octet");
  }

  public TFTPwrite(String filename, String reqType) {
    initialize(filename, reqType);
  }

  public String fileName()
  {
    return get(2, (byte)0);
  }

  public String requestType() {
    String fname = fileName();
    return get(2 + fname.length() + 1, (byte)0);
  }
}