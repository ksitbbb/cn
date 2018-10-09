import java.io.*;
import java.net.*;
import java.util.*;

public class sender
{
    public static void main(String[] args) {

        try{
        Scanner scan = new Scanner(System.in);
            
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        byte[] sendbuf = new byte[1024];
        InetAddress server_addr = null;
        int server_port = 3334;

        String my_name = "";

        DatagramSocket sock = new DatagramSocket(server_port);
        DatagramSocket reg_sock = new DatagramSocket(server_port-1);

        System.out.println("Enter server ip:");
        my_name = scan.nextLine();


        server_addr = InetAddress.getByName(my_name);

        System.out.println("Enter your name:");
        my_name = scan.nextLine();

        sendbuf = my_name.getBytes();

        System.out.println("\n\n Connecting to server :"+server_addr+"\n---------");
        DatagramPacket sendpkt = new DatagramPacket(sendbuf, sendbuf.length,server_addr,server_port-1);
        reg_sock.send(sendpkt);

        for (int i=0; i< sendbuf.length; i++) {
            sendbuf[i] = 0;
        }

        reg_sock.close();

        System.out.println("\n\n Now connected,you can start chatting\n------------");
        while(true)
        {
            System.out.println("\n\nEnter data: ");

            String sentence = userIn.readLine();

            // exit the client when user enters "Exit"
            if (sentence.equals("exit")) {
                System.out.println("Closing the chat. Exiting");
                sock.close();
                System.exit(0);
                break;
            }

            sentence = my_name+";;"+sentence;

            sendbuf = sentence.getBytes();

            DatagramPacket sendpkt2 = new DatagramPacket(sendbuf, sendbuf.length,server_addr,server_port);
            sock.send(sendpkt2);

            for (int i=0; i< sendbuf.length; i++) {
                sendbuf[i] = 0;
            }
        }
    }
    catch(Exception e)
    {
        System.out.println("ERROR : "+e.toString());
    }
    }
}
