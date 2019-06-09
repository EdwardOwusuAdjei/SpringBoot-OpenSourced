package com.cointest.bitCoin;


import info.blockchain.api.APIException;
import info.blockchain.api.blockexplorer.entity.*;
import info.blockchain.api.blockexplorer.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by edward on 6/21/17.
 */


public class ExploreBlock extends Bitcoin {
    private static final Logger LOGGER = Logger.getLogger(ExploreBlock.class.getName());
    public ExploreBlock() {

    }
//TODO:make this method more functional. now not part of core wallet usage.
    public void getTransaction(String txHash) {
        try {

            // get a transaction by hash and list the value of all its inputs
            BlockExplorer blockExplorer = new BlockExplorer();
            Transaction tx = blockExplorer.getTransaction(txHash);
            for (Input i : tx.getInputs()) {
                System.out.println(i.getPreviousOutput().getValue());
            }

            // get a block by hash and read the number of transactions in the block
            Block block = blockExplorer.getBlock("0000000000000000050fe18c9b961fc7c275f02630309226b15625276c714bf1");
            int numberOfTxsInBlock = block.getTransactions().size();

            // get an address and read its final balance
            Address address = blockExplorer.getAddress("1EjmmDULiZT2GCbJSeXRbjbJVvAPYkSDBw");
            long finalBalance = address.getFinalBalance();
            System.out.println("Final Balance" + finalBalance);

            // get an address and read its final balance with filter, limit, and offset
            //  Address address = client.getAddress("1jH7K4RJrQBXijtLj1JpzqPRhR7MdFtaW", FilterType.All, 10, 5);
            // long finalBalance = address.getFinalBalance();


            // get a list of currently unconfirmed transactions and print the relay IP address for each

            // calculate the balanace of an address by fetching a list of all its unspent outputs
            List<UnspentOutput> outs = blockExplorer.getUnspentOutputs("1EjmmDULiZT2GCbJSeXRbjbJVvAPYkSDBw");
            long totalUnspentValue = 0;
            for (UnspentOutput out : outs)
                totalUnspentValue += out.getValue();

//            // calculate the balanace of an address by fetching a list of all its unspent outputs with confirmations and limit
//            List<UnspentOutput> outs = blockExplorer.zgetUnspentOutputs(Arrays.asList("1EjmmDULiZT2GCbJSeXRbjbJVvAPYkSDBw"), 5, 10);
//            //long totalUnspentValue = 0;
//            for (UnspentOutput out : outs)
//                totalUnspentValue += out.getValue();

            // returns the address balance summary for each address provided
            Map<String, Balance> balances = blockExplorer.getBalance(Arrays.asList("1EjmmDULiZT2GCbJSeXRbjbJVvAPYkSDBw"), FilterType.All);

            // returns an aggregated summary on all addresses provided.
            MultiAddress multiAddr = blockExplorer.getMultiAddress(Arrays.asList("1EjmmDULiZT2GCbJSeXRbjbJVvAPYkSDBw"), FilterType.All, 10, 5);

            // returns xpub summary on a xpub provided, with its overall balance and its transactions.
            //XpubFull xpub = blockExplorer.getXpub("xpub6CmZamQcHw2TPtbGmJNEvRgfhLwitarvzFn3fBYEEkFTqztus7W7CNbf48Kxuj1bRRBmZPzQocB6qar9ay6buVkQk73ftKE1z4tt9cPHWRn", FilterType.All, 10, 5);

            // get the latest block on the main chain and read its height
            LatestBlock latestBlock = blockExplorer.getLatestBlock();
            long latestBlockHeight = latestBlock.getHeight();

            // use the previous block height to get a list of blocks at that height
            // and detect a potential chain fork
            List<Block> blocksAtHeight = blockExplorer.getBlocksAtHeight(latestBlockHeight);
            if (blocksAtHeight.size() > 1)
                System.out.println("The chain has forked!");
            else
                System.out.println("The chain is still in one piece :)");

            // get a list of all blocks that were mined today since 00:00 UTC
            List<SimpleBlock> todaysBlocks = blockExplorer.getBlocks();
            System.out.println(todaysBlocks.size());
        } catch (APIException ex) {
            System.out.println("API got a prob " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("internalz got a prob " + ex.getMessage());
        }
    }


    public List<Transaction> getListOfTransaction() {
        List<Transaction> unconfirmedTxs = Collections.emptyList();

        try {
            BlockExplorer blockExplorer = new BlockExplorer();
            unconfirmedTxs = blockExplorer.getUnconfirmedTransactions();

        } catch (APIException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return unconfirmedTxs;

    }

}
