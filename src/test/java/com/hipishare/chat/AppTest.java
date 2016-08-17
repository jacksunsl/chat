package com.hipishare.chat;

import redis.clients.jedis.Jedis;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public static void main(String[] args) {
    	for(int i=0;i<100;i++) {
        	Jedis jedis = new Jedis("120.25.160.18", 6379);
        	System.out.println(jedis.get("aadaf2"));
    	}
	}
}
