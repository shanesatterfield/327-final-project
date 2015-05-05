package com.fp;

import java.net.*;
// import org.apache.commons.lang3.tuple.*;

public abstract class BaseServer implements Runnable
{
    BaseServer( int port )
    {
        this.port = port;
    }

    public abstract void run();
    // public abstract void handleEvents();

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
        byte[] buffer = message.getBytes();
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length, serverAddress, port);
        sender.send( dp );
    }

    /*
    protected synchronized void register( InetAddress addr, int port )
    {
        register( Pair.of( addr, port ) );
    }

    protected synchronized void register( Pair<InetAddress, Integer> pair )
    {
        registered.put( pair );
    }
    */

    protected int port;
    protected EventQueue eq;
    protected DatagramSocket sender;

    // protected map<Pair<InetAddress, Integer>, Pair<InetAddress, Integer>> registered;
}
