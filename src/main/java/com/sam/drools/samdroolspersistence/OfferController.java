package com.sam.drools.samdroolspersistence;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfferController {

    @Autowired
    @Qualifier("kieSession")
    private KieSession kieSession;

    @GetMapping("/order/{card-type}/{price}")
    public Order order(@PathVariable("card-type") String cardType, @PathVariable int price) {

        Order order = new Order(cardType, price);
        kieSession.insert(order);
        kieSession.fireAllRules();
        return order;
    }

}
