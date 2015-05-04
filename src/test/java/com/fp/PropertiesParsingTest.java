package com.fp;

import static org.junit.Assert.*;
import org.junit.*;

public class PropertiesParsingTest
{
    @Test
    public void parseTest()
    {
        Runner runner = new Runner();
        String args[] = new String[]{"server", "1234"};
        runner.setProperties( args );

        assertEquals( runner.type, SCType.SERVER );
        assertEquals( runner.port, Integer.parseInt(args[1]) );
    }

    @Test
    public void badPortsMaxTest()
    {
        Runner runner = new Runner();
        String args[] = new String[2];

        for( String type: types )
        {
            args[0] = type;

            // Test for that the highest works.
            args[1] = "65535";
            runner.setProperties( args );
            assertEquals( runner.port, Integer.parseInt(args[1]) );

            // Test that over the max will be set to 0 for random.
            args[1] = "65536";
            runner.setProperties( args );
            assertEquals( runner.port, 0 );
        }
    }

    @Test
    public void badPortsPriviledgedTest()
    {
        Runner runner = new Runner();
        String args[] = new String[2];

        for( String type: types )
        {
            args[0] = type;

            // Test that the privileged ports don't get used and below.
            for( int i = -2; i <= 1023; ++i )
            {
                args[1] = Integer.toString(i);
                runner.setProperties( args );
                assertEquals( runner.port, 0 );
            }
        }
    }

    @Test
    public void defaultsTest()
    {
        Runner runner = new Runner();
        String args[] = new String[1];

        for( String type: types )
        {
            args[0] = type;
            runner.setProperties( args );
            assertEquals( runner.port, 0 );
        }

        // Test that the privileged ports don't get used and below.
        for( int i = -2; i <= 1023; ++i )
        {
            args[0] = Integer.toString(i);
            runner.setProperties( args );
            assertEquals( runner.port, 0 );
        }

        // Test for that the highest works.
        args[0] = "65535";
        runner.setProperties( args );
        assertEquals( runner.port, Integer.parseInt(args[0]) );

        // Test that over the max will be set to 0 for random.
        args[0] = "65536";
        runner.setProperties( args );
        assertEquals( runner.port, 0 );
    }

    @Test
    public void typeNicknamesTest()
    {
        Runner runner = new Runner();
        String args[] = new String[1];

        for( int i = 0; i < types.length; ++i )
        {
            args[0] = types[i];
            runner.setProperties( args );

            SCType shouldBe = (i < 2) ? SCType.SERVER : SCType.CLIENT;
            assertEquals( runner.type, shouldBe );
        }
    }

    private String types[] = new String[]{"server", "s", "client", "c"};
}
