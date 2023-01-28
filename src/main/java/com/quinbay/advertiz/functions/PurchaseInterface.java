package com.quinbay.advertiz.functions;

import com.quinbay.advertiz.model.Purchase;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PurchaseInterface {

    ResponseEntity purchaseItem(int adId, int buyerId);
    ResponseEntity quoteAmountForAdvertisement(int adId, int buyerId, int quotePrice);

    List<Purchase> getPurchasedList();
    List<Purchase> getUnApprovedQuotes();

    ResponseEntity revokeQuoteRequest(int adid, int buyerid);

    ResponseEntity approveQuotedAmount(int adid, int sellerid);

    Object getTopQuotedAmount(int adid);

//    Integer getTopQuote(int adid);
}
