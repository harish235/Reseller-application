package com.quinbay.advertiz.controller;


import com.quinbay.advertiz.Service.PurchaseService;
import com.quinbay.advertiz.model.Purchase;
import com.quinbay.advertiz.model.Quotehistory;
import com.quinbay.advertiz.pojo.PurchasedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @GetMapping("/getSoldListOfSeller")
    public List<Purchase> getSoldList(@RequestParam int sellerid){
        return purchaseService.getSoldListOfSeller(sellerid);
    }

    @GetMapping("/unApprovedQuoteList")
    public List<Purchase> getUnApprovedQuotes(@RequestParam int sellerid){
        return purchaseService.getUnApprovedQuotes(sellerid);
    }

    @GetMapping("/getPurchasedListOfBuyer")
    public List<PurchasedResponse> getPurchasedListOfBuyer(@RequestParam int buyerid){
        return purchaseService.getPurchasedListOfBuyer(buyerid);
    }

    @GetMapping("/getTopQuoteForAd")
    public Object getTopQuote(@RequestParam int adid){
        return purchaseService.getTopQuotedAmount(adid);
    }

    @GetMapping("/getTopPriorityQuotesOfBuyer")
    public List<Purchase> getTopPriorityQuoteOfBuyer(@RequestParam int buyerid){
        return purchaseService.getTopPriorityQuoteOfBuyer(buyerid);
    }

    @GetMapping("/getLeastPriorityQuotesOfBuyer")
    public List<Quotehistory> getLeastPriorityQuotesOfBuyer(@RequestParam int buyerid){
        return purchaseService.getLeastPriorityQuotesOfBuyer(buyerid);
    }

    @PostMapping("/purchase")
    public ResponseEntity purchaseItem(@RequestParam int adid, @RequestParam int buyerid){
        return purchaseService.purchaseItem(adid, buyerid);
    }

    @PostMapping("/makeQuote")
    public ResponseEntity quoteAmountForAdvertisement(@RequestParam int adid, @RequestParam int buyerid, @RequestParam int quoteamount){
        return purchaseService.quoteAmountForAdvertisement(adid, buyerid, quoteamount);
    }

    @PutMapping("/approveQuote")
    public ResponseEntity approveQuotedAmount(@RequestParam int adid, @RequestParam int sellerid){
        return purchaseService.approveQuotedAmount(adid, sellerid);
    }

    @DeleteMapping("/revokeQuoteRequest")
    public ResponseEntity revokeQuoteRequest(@RequestParam int adid, @RequestParam int buyerid){
        return purchaseService.revokeQuoteRequest(adid, buyerid);
    }

}
