package com.fp;

public class BaseServer implements Runnable
{
    BaseServer( int port )
    {
        this.port = port;
    }

    public void run(){}

    protected int port;
}
