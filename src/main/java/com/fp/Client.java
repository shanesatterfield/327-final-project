package com.fp;

import java.net.*;

public class Client extends BaseServer
{
    private DatagramSocket out_socket;
    private DatagramSocket in_socket;

    Client( int port )
    {
        super( port );
    }

    public void run()
    {
        System.out.print("Running client code");
        try {

            out_socket = new DatagramSocket();
            in_socket = new DatagramSocket(port);

            System.out.printf(" on port %d\n", out_socket.getLocalPort());
            InetAddress serverAddress = InetAddress.getByName("localhost");

            while (true) {
              byte[] buffer = new byte[255];
              DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
              in_socket.receive(dp);
              String command = new String(dp.getData(), 0, buf.length);
              switch (command) {

              }
            }

        } catch( Exception e ) {
            e.printStackTrace();
        } finally {

            if( socket != null )
                socket.close();
        }
    }

    private void send(String command) {
      byte[] data = command.getBytes();
      DatagramPacket dp = new DatagramPacket(data, data.length, serverAddress, port);
      out_socket.send(dp);
    }
}
