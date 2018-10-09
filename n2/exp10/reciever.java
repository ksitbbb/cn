import java.io.*;
import java.net.*;
import java.util.*;

public class reciever
{
    public static void main(String[] args) {

        try{
        int my_port =  3335;
        byte[] recv_buf = new byte[1024];
        DatagramSocket rec_sock = new DatagramSocket(my_port);
        
        

        while(true)
        {
            //Recieve  messages
            DatagramPacket rec_pkt = new DatagramPacket(recv_buf, recv_buf.length);
            rec_sock.receive(rec_pkt);

            String rec_data = new String(rec_pkt.getData());

            System.out.println("\n\n"+rec_data);
            //Re-initialize all data
            for (int i=0; i< recv_buf.length ; i++) {
                recv_buf[i]  = 0;
            }
        }
    }

    catch(Exception e)
    {
        System.out.println("ERROR : "+e.toString());
    }
    }
}
