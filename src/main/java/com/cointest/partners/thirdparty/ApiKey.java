package com.cointest.partners.thirdparty;
public class ApiKey {
    /**
     * @APIKEY is the key given for API usage @ blockchain
     * @ServerInstance is the service.
     */
    public static final String APIKEY;
    public static final String ServerInstance;
    public static final Long satoshi;

    static {
        APIKEY = "";
        ServerInstance = "http://localhost:3000/";
        satoshi = 100000000L;
    }


}
