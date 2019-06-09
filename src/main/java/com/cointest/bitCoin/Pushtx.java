package com.cointest.bitCoin;

import info.blockchain.api.APIException;
import info.blockchain.api.pushtx.PushTx;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by edward on 6/21/17.
 */
public class Pushtx extends Bitcoin {
    private static final Logger LOGGER = Logger.getLogger(ExchangeRate.class.getName());
    //Example i got is "0100000001fd468e431cf5797b108e4d22724e1e055b3ecec59af4ef17b063afd36d3c5cf6010000008c4930460221009918eee8be186035be8ca573b7a4ef7bc672c59430785e5390cc375329a2099702210085b86387e3e15d68c847a1bdf786ed0fdbc87ab3b7c224f3c5490ac19ff4e756014104fe2cfcf0733e559cbf28d7b1489a673c0d7d6de8470d7ff3b272e7221afb051b777b5f879dd6a8908f459f950650319f0e83a5cf1d7c1dfadf6458f09a84ba80ffffffff01185d2033000000001976a9144be9a6a5f6fb75765145d9c54f1a4929e407d2ec88ac00000000"
    public boolean Pushtx(String Tx) {
        boolean bool = false;
        if(!Tx.isEmpty() && Tx.length() != 0) {
            if (!super.APIKEY.isEmpty()) {
                try {
                    PushTx.pushTx(Tx);
                    bool = true;
                } catch (APIException ex) {
                    LOGGER.log(Level.SEVERE, ex.getMessage(), ex);

                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        }
        return bool;
    }
}