//package com.mkudi.web;
//
//
//import com.mkudi.bitCoin.BitcoinWallet;
//import com.mkudi.bitCoin.ExchangeRate;
//import com.mkudi.helpers.BitcoinMessages;
//import com.mkudi.json.ArchiveAndUnarchiveAddress;
//import com.mkudi.json.CreateWallet;
//import com.mkudi.json.MakePayments;
//import com.mkudi.json.WalletInfo;
//import com.mkudi.model.User;
//import com.mkudi.repo.PasswordTokenRepository;
//import com.mkudi.repo.UserRepository;
//import com.mkudi.util.CustomErrorType;
//import com.mkudi.util.CustomMessageType;
//import info.blockchain.api.exchangerates.Currency;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigDecimal;
//import java.util.*;
//
////TODO: allow usage of second password
//
//@RestController
//@RequestMapping(value="/api/")
//public class BitCoinController {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private PasswordTokenRepository passwordTokenRepository;
//    private CustomErrorType customErrorType;
//    //private BitcoinMessages bitcoinMessages;
//    private CustomMessageType successMessage;
//
//    @Async
//    @RequestMapping(value = "/blockchain/{currency}", method = RequestMethod.GET, produces = "application/json")
//    private ResponseEntity<?> OneBTC(@PathVariable String currency,
//                                     @RequestParam(value = "type", defaultValue = "") String type,
//                                     @RequestParam(value = "amount", defaultValue = "0.0") Double amount) {
//        if (!type.isEmpty()) {
//            BigDecimal exchange = BigDecimal.ZERO;
//            System.out.println(type);
//            System.out.println(currency + " Currency");
//            ExchangeRate rate = new ExchangeRate();
//            if (type.matches("selling")) {
//
//                exchange = rate.oneBTCtoCurrency(currency, type);
//            } else if (type.matches("buying")) {
//
//                exchange = rate.oneBTCtoCurrency(currency, type);
//            }
//            Map<String, Object> k = new HashMap<String, Object>();
//
//            if (exchange != null && exchange != BigDecimal.ZERO) {
//                // BigDecimal be = Res.oneBTCtoCurrency(currency);
//                k.put("exchange", exchange);
//                return new ResponseEntity<>(k, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } else if (type.isEmpty()) {
//
//            ExchangeRate res = new ExchangeRate();
//            BigDecimal currencyJson = BigDecimal.ZERO;
//            if (!amount.isNaN() && !currency.isEmpty() && currency.length() == 3) {
//                currencyJson = res.currencytoBTC(currency, amount);
//            }
//            List<BigDecimal> k = new ArrayList<>();
//            k.add(currencyJson);
//            if (k != null && !k.isEmpty()) {
//                return new ResponseEntity<>(k, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @Async
//    @RequestMapping(value = "/blockchain/exchanges", method = RequestMethod.GET, produces = "application/json")
//    private ResponseEntity<?> getAllExchanges() {
//        ExchangeRate rate = new ExchangeRate();
//        Map<String, Currency> k = rate.getallExchanges();
//        if (k != null && !k.isEmpty()) {
//            return new ResponseEntity<>(k, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    //Extra protection?? anyone with experience will like to attack this method
//    @Async
//    @RequestMapping(value = "/blockchain/wallet/create", method = RequestMethod.POST, produces = "application/json")
//    private ResponseEntity<?> createWallet(@RequestBody CreateWallet createWallet) {
//        BitcoinWallet createBitcoinWallet = new BitcoinWallet();
//        if (isNullOrEmpty(createWallet.getPassword()) || isNullOrEmpty(createWallet.getEmail())||isNullOrEmpty(createWallet.getLabel())) {
//            customErrorType = new CustomErrorType(BitcoinMessages.emailOrPasswordMessage);
//            return new ResponseEntity<>(customErrorType, HttpStatus.BAD_REQUEST);
//        }
//        User user = userRepository.findByEmail(createWallet.getEmail());
//        //hash password and compare the hash.
//        if(user.getPassword().equals(createWallet.getPassword()))
//        {
//
//        }
//        if(user != null) {
//            Map<String, String> retvalue = createBitcoinWallet.createWallet(createWallet.getPassword(), createWallet.getEmail(), createWallet.getLabel());
//            if (!retvalue.isEmpty()) {
//                return new ResponseEntity<>(retvalue, HttpStatus.OK);
//            } else {
//                customErrorType = new CustomErrorType(BitcoinMessages.createWalletError);
//                return new ResponseEntity<>(customErrorType, HttpStatus.BAD_REQUEST);
//            }
//        }
//        customErrorType = new CustomErrorType(BitcoinMessages.emailOrPasswordMessage);
//        return new ResponseEntity<>(customErrorType,HttpStatus.BAD_REQUEST);
//    }
//
//    //Currently using a fixed custom miners fee
//    @Async
//    @RequestMapping(value = "/blockchain/wallet/transfer", method = RequestMethod.POST, produces = "application/json")
//    private ResponseEntity<?> transferBitcoin(@RequestBody MakePayments makePayments) {
//        String guid = makePayments.getIdentifier();
//        String mainpassword = makePayments.getMain_password();
//        if (!isNullOrEmpty(guid) || !isNullOrEmpty(mainpassword)) {
//            BitcoinWallet bitcoinWallet = new BitcoinWallet(guid, mainpassword);
//            if (isNullOrEmpty(makePayments.getTo()) || isNullOrEmpty(makePayments.getAmount())
//                    || isNullOrEmpty(makePayments.getFrom())) {
//                customErrorType.setErrorMessage(BitcoinMessages.missingImportantParameter);
//                return new ResponseEntity<>(customErrorType, HttpStatus.BAD_REQUEST);
//            }
//            Long amount;
//            try {
//                amount = Long.parseLong(makePayments.getAmount());
//            } catch (NumberFormatException ex) {
//                customErrorType.setErrorMessage(BitcoinMessages.invalidAmountParameter);
//                return new ResponseEntity<>(customErrorType, HttpStatus.BAD_REQUEST);
//            }
//            String PaymentHash = bitcoinWallet.sendto(makePayments.getTo(), amount, makePayments.getFrom());
//            if (!isNullOrEmpty(PaymentHash)) {
//                return new ResponseEntity<>(PaymentHash, HttpStatus.OK);
//            } else {
//                customErrorType.setErrorMessage(BitcoinMessages.transactionfailed);
//                return new ResponseEntity<>(customErrorType, HttpStatus.BAD_REQUEST);
//            }
//
//        }
//        customErrorType.setErrorMessage(BitcoinMessages.guidOrPassword);
//        return new ResponseEntity<>(customErrorType, HttpStatus.BAD_REQUEST);
//    }
//
//    //TODO: currently uses just main password
//    @Async
//    @RequestMapping(value = "/blockchain/wallet/archive", method = RequestMethod.POST, produces = "application/json")
//    private ResponseEntity<?> archiveAddress(@RequestBody ArchiveAndUnarchiveAddress archiveAndUnarchiveAddress) {
//
//        String guid = archiveAndUnarchiveAddress.getIdentifier();
//        String mainpassword = archiveAndUnarchiveAddress.getPassword();
//        String address = archiveAndUnarchiveAddress.getAddress();
//        if (!isNullOrEmpty(guid) || !isNullOrEmpty(mainpassword)) {
//            BitcoinWallet bitcoinWallet = new BitcoinWallet(guid, mainpassword);
//            if (!isNullOrEmpty(address)) {
//                boolean couldArchive = bitcoinWallet.archiveAddress(address);
//                if (couldArchive) {
//                    successMessage.setSuccessMessage(BitcoinMessages.archived);
//                    return new ResponseEntity<>(successMessage,HttpStatus.OK);
//                } else {
//                    customErrorType.setErrorMessage(BitcoinMessages.archivedError);
//                    return new ResponseEntity<>(customErrorType,HttpStatus.BAD_REQUEST);
//                }
//            }
//        }
//        customErrorType.setErrorMessage(BitcoinMessages.guidOrPassword);
//        return new ResponseEntity<>(customErrorType, HttpStatus.BAD_REQUEST);
//
//    }
//
//    @Async
//    @RequestMapping(value = "/blockchain/wallet/unarchive", method = RequestMethod.POST, produces = "application/json")
//    private ResponseEntity<?> unarchiveAddress(@RequestBody ArchiveAndUnarchiveAddress archiveAndUnarchiveAddress) {
//
//        String guid = archiveAndUnarchiveAddress.getIdentifier();
//        String mainpassword = archiveAndUnarchiveAddress.getPassword();
//        String address = archiveAndUnarchiveAddress.getAddress();
//        if (!isNullOrEmpty(guid) || !isNullOrEmpty(mainpassword)) {
//            BitcoinWallet bitcoinWallet = new BitcoinWallet(guid, mainpassword);
//            if (!isNullOrEmpty(address)) {
//                boolean couldArchive = bitcoinWallet.archiveAddress(address);
//                if (couldArchive) {
//                    successMessage.setSuccessMessage(BitcoinMessages.archived);
//                    return new ResponseEntity<>(successMessage,HttpStatus.OK);
//                } else {
//                    customErrorType.setErrorMessage(BitcoinMessages.archivedError);
//                    return new ResponseEntity<>(customErrorType,HttpStatus.BAD_REQUEST);
//                }
//            }
//        }
//        customErrorType.setErrorMessage(BitcoinMessages.guidOrPassword);
//        return new ResponseEntity<>(customErrorType, HttpStatus.BAD_REQUEST);
//    }
//
//    @Async
//    @RequestMapping(value = "/blockchain/wallet/info", method = RequestMethod.POST, produces = "application/json")
//    private ResponseEntity<?> walletInfo(@RequestBody WalletInfo walletInfo) {
//        String guid = walletInfo.getIdentifier();
//        String mainpassword = walletInfo.getPassword();
//        String address = walletInfo.getAddress();
//        if (!isNullOrEmpty(guid) || !isNullOrEmpty(mainpassword)) {
//            BitcoinWallet bitcoinWallet = new BitcoinWallet(guid, mainpassword);
//            if (!isNullOrEmpty(address)) {
//                Map<String, Object> map = bitcoinWallet.walletInformation(address);
//                if(!map.isEmpty()) {
//                    return new ResponseEntity<>(map, HttpStatus.OK);
//                }
//                customErrorType.setErrorMessage(BitcoinMessages.walletInfoError);
//                return new ResponseEntity<>(customErrorType,HttpStatus.BAD_REQUEST);
//
//            }
//        }
//        customErrorType.setErrorMessage(BitcoinMessages.guidOrPassword);
//        return new ResponseEntity<>(customErrorType, HttpStatus.BAD_REQUEST);
//
//
//    }
//    //TODO: validate email.
//    public boolean isValidEmailAddress(String email) {
//        boolean result = true;
////			try {
////				InternetAddress emailAddr = new InternetAddress(email);
////				emailAddr.validate();
////			} catch (AddressException ex) {
////				result = false;
////			}
//        return result;
//    }
//
//    public boolean isNullOrEmpty(String object) {;return StringUtils.isEmpty(object);}
//
//
//}