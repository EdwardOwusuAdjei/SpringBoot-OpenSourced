package com.cointest.bitCoin;

import info.blockchain.api.APIException;
import info.blockchain.api.wallet.Wallet;
import info.blockchain.api.wallet.entity.Address;
import info.blockchain.api.wallet.entity.CreateWalletResponse;
import info.blockchain.api.wallet.entity.PaymentResponse;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by edward on 6/21/17.
 */

//TODO:sendMany
public class BitcoinWallet extends Bitcoin {
    private String serverInstance;
    private String Password;
    private String APIKEY;
    private String identifier;
    private Long satoshi = super.satoshi;


    private  Wallet walletx;


    private static final Logger LOGGER = Logger.getLogger(BitcoinWallet.class.getName());

    public BitcoinWallet(){
        //Default constructor called when creating wallet only
    }
    //Second constructor called when using wallet functions.
    public BitcoinWallet(String Identifier, String Password) {
        this.identifier = Identifier;
        this.Password = Password;
        walletx = new Wallet(
                super.ServerInstance,
                super.APIKEY,
                this.identifier,
                this.Password);
    }


    /**
     * @param Password YOUR_SUPER_SECURE_PASSWORD like ghgvhug@@!@@#@#fgdgdsg79797-9yu7g will you remember? must be ten
     *                 length. implement checker in app.
     * @return Map<String,String>
     */

    public Map<String, String> createWallet(String Password) {

        return createWallet(Password, null, null, null);
    }
    //TODO:Due to blockchain.info bad code write a helper for this
    public Map<String, String> createWallet(String Password, String Email) {
        return createWallet(Password, Email, null, null);
    }

    public Map<String, String> createWallet(String Password, String Email, String label) {

        return createWallet(Password, Email, label, null);
    }

    public Map<String, String> createWallet(String Password, String email, String label, String PrivKey) {
        Map<String, String> retvalue = new HashMap<>();
        try {
            //CreateWalletResponse create(String serviceURL, String password, String apiCode, String privateKey, String label, String email)

            this.APIKEY = super.APIKEY;
            this.serverInstance = super.ServerInstance;
            this.Password = Password;
            //only one instance of this condition is supposed to run :)
            if (PrivKey == null && label == null && email == null) {
                CreateWalletResponse wallet = Wallet.create(
                        this.serverInstance,
                        this.Password,
                        this.APIKEY);

                if (!wallet.getAddress().isEmpty()) {
                    retvalue.put("Address", wallet.getAddress());
                    retvalue.put("Label", wallet.getLable());
                    retvalue.put("Identifier", wallet.getIdentifier());
                    this.identifier = wallet.getIdentifier();
                }
            } else if (PrivKey == null && label == null) {
                CreateWalletResponse wallet = Wallet.create(
                        this.serverInstance,
                        this.Password,
                        this.APIKEY,
                        null,
                        null,
                        email);

                if (!wallet.getAddress().isEmpty()) {
                    retvalue.put("Address", wallet.getAddress());
                    retvalue.put("Identifier", wallet.getIdentifier());
                    this.identifier = wallet.getIdentifier();
                }
            } else if (label == null && email == null) {
                CreateWalletResponse wallet = Wallet.create(
                        this.serverInstance,
                        this.Password,
                        this.APIKEY,
                        PrivKey,
                        null,
                        null);
                if (!wallet.getAddress().isEmpty()) {
                    retvalue.put("Address", wallet.getAddress());
                    retvalue.put("Label", wallet.getLable());
                    retvalue.put("Identifier", wallet.getIdentifier());
                    this.identifier = wallet.getIdentifier();
                }
            } else if (PrivKey == null && email == null) {
                CreateWalletResponse wallet = Wallet.create(
                        this.serverInstance,
                        this.Password,
                        this.APIKEY,
                        null,
                        label,
                        null);
                if (!wallet.getAddress().isEmpty()) {
                    retvalue.put("Address", wallet.getAddress());
                    retvalue.put("Label", wallet.getLable());
                    retvalue.put("Identifier", wallet.getIdentifier());
                    this.identifier = wallet.getIdentifier();
                }
            } else if (PrivKey == null) {
                CreateWalletResponse wallet = Wallet.create(
                        this.serverInstance,
                        this.Password,
                        this.APIKEY,
                        null,
                        label,
                        email);
                if (!wallet.getAddress().isEmpty()) {
                    retvalue.put("Address", wallet.getAddress());
                    retvalue.put("Label", wallet.getLable());
                    retvalue.put("Identifier", wallet.getIdentifier());
                    this.identifier = wallet.getIdentifier();
                }
            } else if (email == null) {
                CreateWalletResponse wallet = Wallet.create(
                        this.serverInstance,
                        this.Password,
                        this.APIKEY,
                        PrivKey,
                        label,
                        null);
                if (!wallet.getAddress().isEmpty()) {
                    retvalue.put("Address", wallet.getAddress());
                    retvalue.put("Label", wallet.getLable());
                    retvalue.put("Identifier", wallet.getIdentifier());
                    this.identifier = wallet.getIdentifier();
                }
            } else if (label == null) {
                CreateWalletResponse wallet = Wallet.create(
                        this.serverInstance,
                        this.Password,
                        this.APIKEY,
                        PrivKey,
                        null,
                        email);
                if (!wallet.getAddress().isEmpty()) {
                    retvalue.put("Address", wallet.getAddress());
                    retvalue.put("Label", wallet.getLable());
                    retvalue.put("Identifier", wallet.getIdentifier());
                    this.identifier = wallet.getIdentifier();
                }
            }
        } catch (APIException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return Collections.emptyMap();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return  Collections.emptyMap();
        }catch (NullPointerException ex){
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return  Collections.emptyMap();
        }

        return retvalue;
    }

    public Map<String, Object> walletInformation(String Address) {
        Map<String, Object> retval = new HashMap<>();

        try {
            Address addr = walletx.getAddress(Address);
            retval.put("Balance", addr.getBalance());
            retval.put("Address", addr.getAddress());
            retval.put("Label", addr.getLabel());
            retval.put("Received", addr.getTotalReceived());
            retval.put("Hash", addr.hashCode());

            //long totalBalance = wallet.getBalance();
            //System.out.println(String.format("The total wallet balance is %s", totalBalance));


        } catch (APIException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return Collections.emptyMap();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return Collections.emptyMap();
        }catch(NullPointerException ex){
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return Collections.emptyMap();
        }

        return retval;
    }

    public String getNewAddress(String label) {
        String newAddress = new String();
        try {
            // create a new address and attach a label to it
            Address newAddr = walletx.newAddress(label);
            newAddress = newAddr.getAddress();

        } catch (APIException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return newAddress;


    }

    //TODO:can also send to many but bulk sending isnt a matter now
    public String sendto(String Address, long amount, String fromAddress) {
        String PaymentHash = new String();

        try {
                /*
                Example:: Remember its in satoshi so divide by 100,000,000 thats 8 zeros
                send 0.2 bitcoins with a custom fee of 0.01 BTC and a note Miners fee for every transac is 0.0001
                public notes require a minimum transaction size of 0.005 BTC
                PaymentResponse payment = wallet.send("1dice6YgEVBf88erBFra9BHf6ZMoyvG88", 20000000L, null, 1000000L);
                System.out.println(String.format("The payment TX hash is %s", payment.getTxHash()));
                */
            amount = amount*satoshi;
            if (amount >= 500000L) {
                if (fromAddress == null) {
                    PaymentResponse payment = walletx.send(Address, amount, null, 10000L);
                    PaymentHash = payment.getTxHash();
                    Pushtx pushtx = new Pushtx();
                    pushtx.Pushtx(toHex(PaymentHash));

                } else {
                    PaymentResponse payment = walletx.send(Address, amount, fromAddress, 10000L);
                    PaymentHash = payment.getTxHash();
                    Pushtx pushtx = new Pushtx();
                    pushtx.Pushtx(toHex(PaymentHash));
                }

            }
        } catch (APIException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return "Check Sent Information";

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return PaymentHash;
        }



        //payment hash wont be null if sent.
        return PaymentHash;
    }

    public Map<String, Long> listAddressAndBalances() {
        Map<String, Long> AnB = new HashMap<>();
        try {

            // list all addresses and their balances
            List<Address> addresses = walletx.listAddresses();

            for (Address a : addresses) {
                AnB.put(a.getAddress(), a.getBalance());
            }
        } catch (APIException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return AnB;
    }

    /**
     * @param Address the address you want to archive
     * @return boolean value if able to archive
     */
    public boolean archiveAddress(String Address) {
        boolean archived = false;
        try {

            //TODO: properly archive
            walletx.archiveAddress(Address);
            archived = true;
        } catch (APIException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        // archive an old address
        return archived;

    }
    public boolean unarchiveAddress(String Address) {
        boolean unarchived = false;
        try {

            //TODO: properly archive
            walletx.unarchiveAddress(Address);
            unarchived = true;
        } catch (APIException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        // archive an old address
        return unarchived;

    }


    public String toHex(String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes()));
    }

}



