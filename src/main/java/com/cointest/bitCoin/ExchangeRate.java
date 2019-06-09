package com.cointest.bitCoin;

//Blockchain API imports

import info.blockchain.api.exchangerates.*;
import info.blockchain.api.APIException;
import info.blockchain.api.exchangerates.Currency;
//import com.mkudi.model.*;

//Java Imports
import java.math.BigDecimal;
import java.util.*;
import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by edward on 6/21/17.
 */

public class ExchangeRate extends Bitcoin {

    private String Currency;
    private String ApiKey;
    private Double Amount;
    private String OneBTCequals = "";

    private static final Logger LOGGER = Logger.getLogger(ExchangeRate.class.getName());

    public ExchangeRate() {
        this.ApiKey = super.APIKEY;
        //this.Currency = Currency;
    }

    /**
     *
     * @param currency The Currency to convert one BTC is selling at
     * @return The value of BTC you will get at a unit of the currency. thats one
     */
    public BigDecimal oneBTCtoCurrency(String currency, String type) {
        BigDecimal ReturnValue = new BigDecimal(0);
        if (!currency.isEmpty()) {
            this.Currency = currency;

            try {
                FileHandler fh = new FileHandler("Exchange.log");
                LOGGER.addHandler(fh);
                // get the ticker map
                //Switch case.
                //Currency Example here is USD
                ExchangeRates exchange = new ExchangeRates();
                Map<String, Currency> ticker = exchange.getTicker();
                if (type.matches("selling")) {
                    ReturnValue = ticker.get(this.Currency).getSell();
                } else if (type.matches("buying")) {
                    ReturnValue = ticker.get(this.Currency).getBuy();
                }
                this.OneBTCequals = ReturnValue.toString();
                return ReturnValue;
            } catch (APIException ex) {
                LOGGER.log(Level.WARNING, ex.getMessage(), ex);
                // throw new BitException();
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            } catch (NullPointerException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * @param Currency The currency to convert into satoshi.
     * @param Amount   The amount you want to convert into.
     * @return The value of BTC at that amount.
     */

    public BigDecimal currencytoBTC(String Currency, Double Amount) {
        BigDecimal returnValue = new BigDecimal(0);
        if (!Currency.isEmpty() && Amount != 0) {
            this.Currency = Currency;
            this.Amount = Amount;
            try {
                ExchangeRates exchange = new ExchangeRates();
                // convert certain amount
                /**
                 * First Param here is the currency.
                 * Second Param here is the Amount.
                 * Example here is the toBTC("EUR", new BigDecimal(i:53620));
                 * Converting 5362 EURO to BTC. letter i is Integer for this case.
                 */
                returnValue = exchange.toBTC(this.Currency, new BigDecimal(this.Amount));
                // convert 100,000,000 satoshi to USD
                //BigDecimal BTCToUSD = exchange.toFiat("USD", new BigDecimal(100000000));
            } catch (APIException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                //OnError it should return just 0.
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                //OnError it should return just 0.
            }
        }
        //incase of problem for now ret value is zero
        return returnValue;
    }


    public Map<String, Currency> getallExchanges() {
        ExchangeRates exchange = new ExchangeRates();
        try {

            return exchange.getTicker();
        } catch (APIException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return Collections.emptyMap();
    }

}
