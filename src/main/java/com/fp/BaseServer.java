package com.fp;

import java.util.*;
import java.net.*;
import org.apache.commons.lang3.tuple.*;

public abstract class BaseServer implements Runnable
{
    BaseServer( int port, String addr )
    {
        this.port = port;
        try {
            this.addr = InetAddress.getByName( addr );
        } catch( Exception e ) {
            e.printStackTrace();
        }

        for( int i = 0; i < nodes.length; ++i )
        {
            nodes[i] = new Node();
        }
    }

    public abstract void run();
    public abstract void handleEvents() throws Exception;

    protected void startEQ( int eqPort )
    {
        try {

            eq = new EventQueue( eqPort );
            eq.start();

        } catch( Exception e ) {
            e.printStackTrace();
        }
    }

    protected void setupSender() throws Exception
    {
        sender = new DatagramSocket();
    }


    protected synchronized void send( String message, InetAddress serverAddress, int port ) throws Exception
    {
        send( message, Pair.of( serverAddress, port ) );
    }

    protected synchronized void send( String message ) throws Exception
    {
        send( message, Pair.of( addr, port ) );
    }

    protected synchronized void send( String message, Pair<InetAddress, Integer> pair ) throws Exception
    {
        byte[] buffer = message.getBytes();
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length, pair.getLeft(), pair.getRight() );
        System.out.println( "Sending data to remote port#" + pair.getRight() + ": " + message );
        sender.send( dp );
    }

    protected synchronized void register( InetAddress addr, int port1, int port2 )
    {
        register( Pair.of( addr, port1 ), Pair.of( addr, port2 ) );
    }

    protected synchronized void register( Pair<InetAddress, Integer> pair1, Pair<InetAddress,Integer> pair2 )
    {
        registered.put( pair1, pair2 );
    }

    protected int port;
    protected InetAddress addr;
    protected EventQueue eq;
    protected DatagramSocket sender;

    protected Node[] nodes = new Node[100];

    protected Map<Pair<InetAddress, Integer>, Pair<InetAddress, Integer>> registered = new HashMap<Pair<InetAddress, Integer>, Pair<InetAddress, Integer>>();
}
