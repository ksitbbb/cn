// Exp 10 - UDP Client (Sender)
import java.io.*;
import java.net.*;
public class lab10client {
  public static void main(String[] args) throws IOException {
    InetAddress saddr = null;
    int sport = 0;
    DatagramSocket sock = new DatagramSocket();
    BufferedReader userIn =
      new BufferedReader(new InputStreamReader(System.in));
    byte[] sendbuf = new byte[1024];
    byte[] recvbuf = new byte[1024];

    // extract the server IP Address and port number from command line arguments
    saddr = InetAddress.getByName(args[0]); 
    sport = Integer.parseInt(args[1]);
    
    while (true) {
      System.out.println("Enter chat data: ");
      // read user input
      String sentence = userIn.readLine();
      // exit the client when user enters "Exit"
      if (sentence.equals("Exit")) {
        break;
      }
      // convert the input data to byte sequence
      sendbuf = sentence.getBytes();
      // prepare the packet to be sent to server.
      DatagramPacket sendpkt = new DatagramPacket(sendbuf, sendbuf.length,saddr,sport);
      // send the packet
      sock.send(sendpkt);

      // prepare a packet to received the response
      DatagramPacket recvpkt =  new DatagramPacket(recvbuf,recvbuf.length);
      sock.receive(recvpkt);
      // convert the received UDP data to string text and display the same.
      String recvdata = new String(recvpkt.getData());
      System.out.println("Recd from Server: " + recvdata);
      // initialize the send and receive buffer
      for (int i=0; i< sendbuf.length; i++) {
      	sendbuf[i] = 0;
      }
      for (int i=0; i< recvbuf.length; i++) {
      	recvbuf[i] = 0;
      }
    }
    System.out.println("Closing the chat. Exiting");
    sock.close();
  }
}
