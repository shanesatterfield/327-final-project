package com.fp;

import java.net.*;

public class Client extends BaseServer
{

    Client( int port )
    {
        super( port );
    }

    public void run()
    {
        System.out.print("Running client code");
        try {

            // TODO: Fix this so that the clients register with the server. Can't have sender and receiver on the same port.
            socket = new DatagramSocket();

            System.out.printf(" on port %d\n", socket.getLocalPort());
            serverAddress = InetAddress.getByName("localhost");

            // Starts the EventQeuue thread.
            startEQ( socket.getLocalPort() );

            while( true )
            {
                // Handles the events
                while( eq.isEmpty() == false )
                {
                    eq.poll();
                }

                send("Things");
            }

        } catch( Exception e ) {
            e.printStackTrace();
        } finally {

            if( socket != null )
                socket.close();
        }
    }

    private void send(String command) throws Exception {
      byte[] data = command.getBytes();
      DatagramPacket dp = new DatagramPacket(data, data.length, serverAddress, port);
      socket.send(dp);
    }

    private DatagramSocket socket;
    InetAddress serverAddress;
}
