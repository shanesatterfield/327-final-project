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
}
