package com.fp;

import java.net.*;

public class Server extends BaseServer
{
    Server( int port )
    {
        super( port );
    }

    public void run()
    {
        System.out.print("Running server code");
        try {

            socket = new DatagramSocket( port );
            System.out.printf(" on port %d\n", socket.getLocalPort());

            byte[] buffer = new byte[255];

            while( true )
            {
                DatagramPacket dp = new DatagramPacket( buffer, buffer.length );
                socket.receive( dp );
                System.out.println( new String(dp.getData(), 0, buffer.length) );
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
