
public class exp7 {

  public static void main(String[] args) {
      // Initial value of CRC and polynomical various CRC-CCITT implementation
      // CRC-16/CCITT: Initial: 0xFFFF, poly = 0x1021
      // CRC-16/XModem: Initial: 0x0000, Poly = 0x1021
      // CRC-16/``````: Initial: 0xFFFF, Poly = 0x8005
      int remainder = 0; // Initial value of CRC.

      int polynomial = 0;//Integer.parseInt("0x11021",16); // ideally it is 0x11021
      int bitvalue; // one bit of data from MSB at a time
      int remMsb; // MSB bit of remainer i.e. current crc calue

    if ((args.length ==  0) || (args.length > 2)) {
        System.out.println("Usage: Crc16: <input in hex> [<CRC Polynomial in hex>]");
    }
    byte[] inpdata = args[0].getBytes(); // convert intput data into bytes.
    if (args.length > 1) { //CRC polynomial is specified
        polynomial = Integer.decode(args[1]);
    }
    for (byte inpbyte: inpdata) {
      for (int count = 0; count < 8; count++) {// for each bit from MSB to LSB
        bitvalue = ((inpbyte >>> (7-count)) & 1); // one bit at a time from MSB side
        remMsb = ((remainder & 0x8000) >>> 15) & 1;
        remainder = remainder << 1; // be ready for next round of division
        // check if XOR of both data bit and remainder bit is true
        if ((bitvalue ^ remMsb) == 1) {
          // if yes, do XOR of CRC with Polynomial
          remainder = remainder ^ polynomial;
        }
      } // end for byte
      remainder = remainder & 0xFFFF; // truncate to 16 bits only
    } // end for input data

    System.out.println("CRC16 value of input data " + args[0] + " is " + 
        Integer.toHexString(remainder));
  }

}
