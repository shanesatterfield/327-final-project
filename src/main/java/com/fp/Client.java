package com.fp;

import java.net.*;

public class Client extends BaseServer
{
    Client( int port, String addr )
    {
        super( port, addr );
    }

    public void run()
    {
        System.out.println("Running client code");
        try {

            // Starts the EventQeuue thread.
            startEQ( 0 );

            // Sets up the socket for sending  datagram packets.
            setupSender();

            // Register with the server.
            send( "reg:" + Integer.toString(eq.getPort()), addr, port );

            Worker[] workers = new Worker[num_workers];
            for (int i = 0; i < num_workers; i++) {
                workers[i] = new Worker(nodes, this);
                workers[i].start();
            }

            while( true )
            {
                // Handles the events
                handleEvents();
            }

        } catch( Exception e ) {
            e.printStackTrace();
        } finally {

            if( socket != null )
                socket.close();
        }
    }

    public void handleEvents()
    {
        while( eq.isEmpty() == false )
        {
            DatagramPacket dp  = eq.poll();
            String message = EventQueue.getString( dp );
            String mes[] = message.split(":");
            int node_num = 0;

            switch( mes[0] )
            {
                case "reg":
                    register( dp.getAddress(), dp.getPort(), Integer.parseInt(mes[1].trim()) );
                    break;
                case "OK":
                    System.out.println("Ok received");
                    node_num = Integer.parseInt(mes[1].trim());
                    nodes[node_num].hasToken = true;
                    nodes[node_num].signalAll();
                    break;

                case "upd":
                    node_num = Integer.parseInt(mes[1].trim());
                    String updated_value = mes[2].trim();
                    nodes[node_num].string = updated_value;
                    break;

                default:
                    System.out.printf("Received Message: %s\n", message);
                    break;
            }
        }
    }

    public synchronized void handleWorkerRequest(int node_num) throws Exception {
        send("req:" + node_num);
    }

    public synchronized void handleWorkerUpdate(int node_num) throws Exception {
        nodes[node_num].hasToken = false;
        send("rel:" + node_num + ":" + nodes[node_num].string);
    }

    private DatagramSocket socket;
    InetAddress serverAddress;
    static final int num_workers = 100;
}
