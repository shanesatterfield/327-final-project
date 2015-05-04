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

            socket = new DatagramSocket();
            System.out.printf(" on port %d\n", socket.getLocalPort());

            InetAddress serverAddress = InetAddress.getByName("localhost");

            for( int i = 0; i < 10; ++i )
            {
                String message = Integer.toString( i );
                byte[] data = message.getBytes();
                DatagramPacket dp = new DatagramPacket( data, data.length, serverAddress, port );

                socket.send( dp );
            }

        } catch( Exception e ) {
            e.printStackTrace();
        } finally {

            if( socket != null )
                socket.close();
        }
    }

    private DatagramSocket socket;
}
