package com.fp;

import java.net.*;
import org.apache.commons.lang3.tuple.*;

public class Server extends BaseServer
{
    Server( int port )
    {
        super( port );
    }

    public void run()
    {
        System.out.println("Running server code");
        try {

            // Sets up the thread for receiving datagram packets.
            startEQ( port );

            // Sets up the socket for sending  datagram packets.
            setupSender();

            while( true )
            {
                handleEvents();
            }

        } catch( Exception e ) {
            e.printStackTrace();
        }
    }

    public void handleEvents() throws Exception
    {
        while( eq.isEmpty() == false )
        {
            DatagramPacket dp  = eq.poll();
            String message = EventQueue.getString( dp );
            String mes[] = message.split(":");

            switch( mes[0] )
            {
                case "reg":
                    register( dp.getAddress(), dp.getPort(), Integer.parseInt(mes[1].trim()) );
                    send("Response", registered.get( Pair.of(dp.getAddress(), dp.getPort()) ) );
                    break;
            }

        }
    }

    private DatagramSocket socket;
}
