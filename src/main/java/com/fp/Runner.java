package com.fp;

public class Runner
{
    public static void main( String args[] )
    {
        setProperties( args );
    }

    public static void setProperties( String args[] )
    {
        for( String i: args )
        {
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

            else if( i.matches("[0-9]+") )
            {
                port = Integer.parseInt(i);
            }
        }
    }

    public static SCType type = SCType.SERVER;
    public static    int port = 9001;
}
