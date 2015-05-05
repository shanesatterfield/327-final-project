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

            /*
            socket = new DatagramSocket( port );
            System.out.printf(" on port %d\n", socket.getLocalPort());

            byte[] buffer = new byte[255];

            while( true )
            {
                DatagramPacket dp = new DatagramPacket( buffer, buffer.length );
                socket.receive( dp );
                System.out.println( new String(dp.getData(), 0, buffer.length) );
            }
            */

            startEQ( port );
            setupSender();

            while( true )
            {
                while( eq.isEmpty() == false )
                    System.out.println( eq.poll() + "\t" + eq.events.size() );

                send( "Hello World!", InetAddress.getByName("localhost"), 1230 );
                Thread.sleep( 500 );
            }

        } catch( Exception e ) {
            e.printStackTrace();
        }
    }

    // private DatagramSocket socket;
}
