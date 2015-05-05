package com.fp;

import java.net.*;
import java.util.*;

public class EventQueue extends Thread
{
    EventQueue( int port )
    {
        this.port = port;
    }

    public void run()
    {
        try {

            socket = new DatagramSocket( port );
            while( true )
            {
                receive();
            }

        } catch( Exception e ) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public synchronized String poll()
    {
        return events.poll();
    }

    public void receive() throws Exception
    {
        byte[] buffer = new byte[256];
        DatagramPacket dp = new DatagramPacket( buffer, buffer.length );
        socket.receive( dp );
        events.add( new String( dp.getData(), 0, buffer.length ) );
    }

    public Boolean isEmpty()
    {
        return events.isEmpty();
    }

    public DatagramSocket getSocket()
    {
        return socket;
    }

    public Queue<String> events = new LinkedList<String>();
    private DatagramSocket socket;
    private int port;
}
