package com.quinbay.advertiz.controller;


import com.quinbay.advertiz.functions.PurchaseInterface;
import com.quinbay.advertiz.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PurchaseController {

    @Autowired
    PurchaseInterface purchaseInterface;

    @GetMapping("/purchasedList")
    public List<Purchase> getPurchasedList(){
        return purchaseInterface.getPurchasedList();
    }

    @GetMapping("/unApprovedQuoteList")
    public List<Purchase> getUnApprovedQuotes(){
        return purchaseInterface.getUnApprovedQuotes();
    }

    @GetMapping("/getTopQuoteForAd")
    public Object getTopQuote(@RequestParam int adid){
        return purchaseInterface.getTopQuotedAmount(adid);
    }

    @PostMapping("/purchase")
    public ResponseEntity purchaseItem(@RequestParam int adid, @RequestParam int buyerid){
        return purchaseInterface.purchaseItem(adid, buyerid);
    }

    @PostMapping("/makeQuote")
    public ResponseEntity quoteAmountForAdvertisement(@RequestParam int adid, @RequestParam int buyerid, @RequestParam int quoteamount){
        return purchaseInterface.quoteAmountForAdvertisement(adid, buyerid, quoteamount);
    }

    @PutMapping("/approveQuote")
    public ResponseEntity approveQuotedAmount(@RequestParam int adid, @RequestParam int sellerid){
        return purchaseInterface.approveQuotedAmount(adid, sellerid);
    }

    @DeleteMapping("/revokeQuoteRequest")
    public ResponseEntity revokeQuoteRequest(@RequestParam int adid, @RequestParam int buyerid){
        return purchaseInterface.revokeQuoteRequest(adid, buyerid);
    }

}
