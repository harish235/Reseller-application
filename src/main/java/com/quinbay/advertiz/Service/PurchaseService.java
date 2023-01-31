package com.quinbay.advertiz.Service;

import com.quinbay.advertiz.model.Purchase;
import com.quinbay.advertiz.model.Quotehistory;
import com.quinbay.advertiz.pojo.PurchasedResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PurchaseService {

    ResponseEntity purchaseItem(int adId, int buyerId);
    ResponseEntity quoteAmountForAdvertisement(int adId, int buyerId, int quotePrice);

    List<Purchase> getSoldListOfSeller(int sellerid);
    List<Purchase> getUnApprovedQuotes(int sellerid);

    List<PurchasedResponse> getPurchasedListOfBuyer(int buyerid);

    ResponseEntity revokeQuoteRequest(int adid, int buyerid);

    ResponseEntity approveQuotedAmount(int adid, int sellerid);

    Object getTopQuotedAmount(int adid);

    List<Purchase> getTopPriorityQuoteOfBuyer(int buyerid);

    List<Quotehistory> getLeastPriorityQuotesOfBuyer(int buyerid);

//    Integer getTopQuote(int adid);
}
