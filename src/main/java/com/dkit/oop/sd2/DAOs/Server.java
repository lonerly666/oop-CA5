package com.dkit.oop.sd2.DAOs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main (String [] args)
    {
        Server server = new Server();
        server.start();
    }
    public void start()
    {
        try
        {
            ServerSocket ss = new ServerSocket(8080);
            System.out.println("Server: Server started. Listening for connections...");
            int numOfClient =0;

            while(true)
            {
                Socket s = ss.accept();
                numOfClient++;
                System.out.println("Server: Client "+numOfClient+" connected to the server");
                System.out.println("Server: Port# of remote client: " + s.getPort());
                System.out.println("Server: Port# of this server: " + s.getLocalPort());
                Thread t = new Thread(new ClientHandler(s,numOfClient));
                t.start();
                System.out.println("Server: ClientHandler started in thread for client " + numOfClient + ". ");
                System.out.println("Server: Listening for further connections...");
            }
        }
        catch (IOException e)
        {
            System.out.println("Server: IOException: "+e);
        }
        System.out.println("Server: Server exiting, Goodbye!");
    }

}
