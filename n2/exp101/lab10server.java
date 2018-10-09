// Exp 10 - UDP Server (Receiver)
import java.io.*;
import java.net.*;
public class lab10server {
  public static void main(String[] args) throws IOException {
    int sport = 0;
    InetAddress caddr = null;
    int cport = 0;
    DatagramSocket sock = null;
    BufferedReader userIn = null;

    // get server port number as the first argument.
    sport = Integer.parseInt(args[0]);
    sock = new DatagramSocket(sport);
    userIn = new BufferedReader(new InputStreamReader(System.in));
    byte[] sendbuf = new byte[1024];
    byte[] recvbuf = new byte[1024];

    while (true) {
      System.out.println("Waiting for Client input: ");
      // create the UDP packet to receive data crom client
      DatagramPacket recvpkt = new DatagramPacket(recvbuf, recvbuf.length);
        
      sock.receive(recvpkt);
      // convert the received data into string text 
      String recvdata = new String(recvpkt.getData());

      // extract the client IP Address and port number
      caddr = recvpkt.getAddress();
      cport = recvpkt.getPort();
      System.out.println("Recd from Client " + caddr.toString() + ":" + cport);
      System.out.println(recvdata);

      System.out.println("Enter the Response: ");
      // Read the response from console
      String sentence = userIn.readLine();
      sendbuf = sentence.getBytes();
      // create the UDP packet to be sent and send it on socket
      DatagramPacket sendpkt = new DatagramPacket(sendbuf,sendbuf.length,caddr,cport);
      sock.send(sendpkt);
      // initialize the receive and send buffer
      for (int i=0; i<recvbuf.length ; i++) {
      	recvbuf[i]  = 0;
      }
      for (int i=0; i< sendbuf.length; i++) {
      	sendbuf[i]=0;
      }
    }
  }
}

