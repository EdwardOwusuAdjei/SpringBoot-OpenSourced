package com.cointest.web.Customer;

import com.cointest.partners.thirdparty.*;
import com.cointest.helpers.mazzuma.MazzumaHelpers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping(value="/api/v1/")
public class CustomerTransactions {

    //TODO::must be async
    @RequestMapping(value = "/sendmobilemoney", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> sendMobileMoney(@RequestBody MazzumaHelpers mazzumaHelpers) {
        //Check for proper values
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(mazzumaHelpers.toString());
        final MazzumaHelpers mazzumaHelpers1 = restTemplate.postForObject(APICONST.sendmoney, mazzumaHelpers, MazzumaHelpers.class);
        System.out.println(mazzumaHelpers1);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/requestmobilemoney", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> requestMobileMoney(@RequestBody MazzumaHelpers mazzumaHelpers) {
        //TODO::append r to the option
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(mazzumaHelpers.toString());
        final MazzumaHelpers mazzumaHelpers1 = restTemplate.postForObject(APICONST.sendmoney, mazzumaHelpers, MazzumaHelpers.class);
        System.out.println(mazzumaHelpers1);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
