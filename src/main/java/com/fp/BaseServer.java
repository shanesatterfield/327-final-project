package com.fp;

import java.net.*;

public class BaseServer implements Runnable
{
    BaseServer( int port )
    {
        this.port = port;
    }

    public void run(){}

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

    protected void send( String message, InetAddress serverAddress, int port ) throws Exception
    {
        byte[] buffer = message.getBytes();
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length, serverAddress, port);
        sender.send( dp );
    }

    protected int port;
    protected EventQueue eq;
    protected DatagramSocket sender;
}
