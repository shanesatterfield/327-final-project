package com.fp;

import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import org.apache.commons.lang3.tuple.*;

public class Server extends BaseServer
{
    Server( int port, String addr )
    {
        super( port, addr );
        for (int i = 0; i < 100; i++) {
            nodes[i].hasToken = true;
            queues.add(new ConcurrentLinkedQueue<Pair<InetAddress, Integer>>());
        }
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

            int node_num = 0;

            switch( mes[0] )
            {
                case "reg":
                    register( dp.getAddress(), dp.getPort(), Integer.parseInt(mes[1].trim()) );
                    // send("Response", registered.get( Pair.of(dp.getAddress(), dp.getPort()) ) );
                    break;
                case "req":
                    node_num = Integer.parseInt(mes[1].trim());

                    System.out.printf("Received command req for node %d\n", node_num);

                    if (nodes[node_num].hasToken) {
                        nodes[node_num].hasToken = false;

                        System.out.printf("Sending %d\n", node_num);

                        send("OK:" + node_num, registered.get( Pair.of(dp.getAddress(), dp.getPort()) ) );
                    }
                    else {
                        queues.get(node_num).add( registered.get( Pair.of(dp.getAddress(), dp.getPort()) ));
                        System.out.printf("Queuing %d\n", node_num);
                    }
                    break;
                case "rel":
                    node_num = Integer.parseInt(mes[1].trim());
                    String updated_value = mes[2].trim();

                    System.out.printf("Node %d: %s -> %s\n", node_num, nodes[node_num].string, updated_value);

                    nodes[node_num].string = updated_value;
                    nodes[node_num].hasToken = true;
                    if (queues.get(node_num).size() > 0) {
                        Pair<InetAddress, Integer> destination = queues.get(node_num).poll();
                        nodes[node_num].hasToken = false;
                        send("OK:" + node_num, destination);
                    }

                    break;
            }

        }
    }

    private DatagramSocket socket;
    private ArrayList<ConcurrentLinkedQueue<Pair<InetAddress, Integer>>> queues = new ArrayList<ConcurrentLinkedQueue<Pair<InetAddress, Integer>>>();
}
