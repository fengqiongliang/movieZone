package com.tftp;

public final class TFTPerror extends TFTPpacket
{
  protected TFTPerror()
  {
  }

  public TFTPerror(int number, String message)
  {
    if (message != null) {
      this.length = (4 + message.length() + 1);
      this.message = new byte[this.length];

      put(0, (short)5); //TODO 
      put(2, (short)number);
      put(4, message, (byte)0); //TODO 
    }
  }

  public int number()
  {
    return get(2);
  }

  public String message() {
    return get(4, (byte)0); //TODO 
  }
}