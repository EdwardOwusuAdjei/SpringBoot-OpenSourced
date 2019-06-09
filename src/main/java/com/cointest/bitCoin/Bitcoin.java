package com.cointest.bitCoin;

/**
 * Created by edward on 6/21/17.
 */
public class Bitcoin {
    /**
     * @APIKEY is the key given for API usage @ blockchain
     * @ServerInstance is the service.
     */
    public static final String APIKEY;
    public static final String ServerInstance;
    public static final Long satoshi;

    static {
        APIKEY = "stuff goes here";
        ServerInstance = "http://localhost:3000/";
        satoshi = 100000000L;
    }


}
