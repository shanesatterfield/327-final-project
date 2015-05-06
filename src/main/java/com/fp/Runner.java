package com.fp;

import java.util.Arrays;

public class Runner
{
    public static void main( String args[] )
    {
        setProperties( args );
        // System.out.printf("Running %s on port %d\n", ((type == SCType.SERVER) ? "server":"client"), port);

        // Determine which type of server to run. Whether it's the server or client.
        Runnable runnable;
        if( type == SCType.SERVER )
            runnable = new Server( port, addr );
        else
            runnable = new Client( port, addr );

        // Create and start the thread.
        server = new Thread( runnable );
        server.start();

        // Wait for the thread to finish.
        try {
            server.join();
        } catch( InterruptedException e ) {
            e.printStackTrace();
        }
    }

    public static void setProperties( String args[] )
    {
        type = SCType.SERVER;
        port = 0;
        addr = "localhost";

        for( String i: args )
        {
            // Sets the server type.
            if( i.matches("[a-zA-Z]+") )
            {
                switch(i)
                {
                    case "server":
                    case "s":
                        type = SCType.SERVER;
                        break;

                    case "client":
                    case "c":
                        type = SCType.CLIENT;
                        break;
                }
            }

            // Sets the port number. If it's the server, it's the listening port.
            // If it's the client, it's the target port.
            else if( i.matches("[-+]?[0-9]+") )
            {
                int tempPort = Integer.parseInt(i);

                if( tempPort <= 1023 || tempPort > 65535 )
                    tempPort = 0;

                port = tempPort;
            }

            // Sets the address.
            else
            {
                addr = i;
            }
        }
    }

    public static SCType type = SCType.SERVER;
    public static    int port = 0;
    public static String addr = "localhost";
    public static Thread server;
}
