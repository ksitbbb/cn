import java.io.*;
import java.net.*;
import java.util.*;

public class server
{
    
    //Array of all connected clients
    static InetAddress client_addr[] = new InetAddress[15];
    static int client_port[] = new int[15];

    //Number of clients
    static int n = 0;

    //Define server parameters
    static int my_port_reg  =  3333;
    static int my_port_rec  =  3334;
    static int my_port_send =  3335;


    public static void main(String[] args) 
    {

        register_clients();
        recieve_messages();
        
    }

    public static void register_clients()
    {
        try{
        //Initialze values
        byte[] recvbuf = new byte[1024];
        DatagramSocket reg_sock = new DatagramSocket(my_port_reg);

        while(true)
        {
            //Recieve registration messages
            DatagramPacket reg_pkt = new DatagramPacket(recvbuf, recvbuf.length);
            reg_sock.receive(reg_pkt);

            //Add new registration to broadcast list
            client_addr[n] = reg_pkt.getAddress();
            client_port[n] = reg_pkt.getPort();
            n=n+1;

            //Extract data
            String reg_data = new String(reg_pkt.getData());
            String reg_data_arr[] = reg_data.split(";;");

            //Send message that client joined chat
            String send = reg_data_arr[0]+" joined the chat over ip :" + client_addr[n-1];
            if((n-1)!=0)
                send_messages(send);


            //Re-initialize all data
            for (int i=0; i< recvbuf.length ; i++) {
                recvbuf[i]  = 0;
            }
        }

    }
    catch(Exception e)
    {
        System.out.println("ERROR : "+e.toString());
    }
    }

    public static void recieve_messages()
    {
        try{
        //Initialze values
        byte[] recv_buf = new byte[1024];
        DatagramSocket rec_sock = new DatagramSocket(my_port_rec);
        

        while(true)
        {
            //Recieve  messages
            DatagramPacket rec_pkt = new DatagramPacket(recv_buf, recv_buf.length);
            rec_sock.receive(rec_pkt);

            //Extract data
            String rec_data = new String(rec_pkt.getData());
            String rec_data_arr[] = rec_data.split(";;");

            System.out.println(rec_data);

            //Send message that client sent
            String send = rec_data_arr[0]+":\n" + rec_data_arr[1];
            
            send_messages(send);


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

    public static void send_messages(String message)
    {

        
        try{
        byte[] sendbuf = new byte[1024];
        DatagramSocket sock = new DatagramSocket(my_port_send);
        sendbuf = message.getBytes();
     
        for(int i=0;i<=n;i++)
        {
            //create the UDP packet to be sent and send it on socket
            DatagramPacket sendpkt = new DatagramPacket(sendbuf,sendbuf.length,client_addr[i],client_port[i]);
            sock.send(sendpkt);
        }
    }
    catch(Exception e)
    {
        System.out.println("ERROR : "+e.toString());
    }
    }
}