package com.fp;

import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class EventQueue extends Thread
{
    EventQueue( int port )
    {
        this.port = port;
        try {
            socket = new DatagramSocket( port );
            System.out.println("Listening on port " + socket.getLocalPort());
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        try {
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

    public synchronized DatagramPacket poll()
    {
        return events.poll();
    }

    public void receive() throws Exception
    {
        byte[] buffer = new byte[ MAX_LENGTH ];
        DatagramPacket dp = new DatagramPacket( buffer, buffer.length );
        socket.receive( dp );
        events.add( dp );
    }

    public static String getString( DatagramPacket dp )
    {
        return new String( dp.getData(), 0, MAX_LENGTH );
    }

    public Boolean isEmpty()
    {
        return events.isEmpty();
    }

    public DatagramSocket getSocket()
    {
        return socket;
    }

    public int getPort()
    {
        return socket.getLocalPort();
    }

    public Queue< DatagramPacket > events = new ConcurrentLinkedQueue< DatagramPacket >();
    public DatagramSocket socket;
    private int port;

    public static final int MAX_LENGTH = 256;
}
