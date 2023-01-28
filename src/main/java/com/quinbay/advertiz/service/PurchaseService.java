package com.quinbay.advertiz.service;

import java.time.LocalDateTime;
import com.quinbay.advertiz.Repositories.AdvertisementRepository;
import com.quinbay.advertiz.Repositories.PurchaseRepository;
import com.quinbay.advertiz.Repositories.QuotehistoryRepository;
import com.quinbay.advertiz.functions.PurchaseInterface;
import com.quinbay.advertiz.model.Advertisement;
import com.quinbay.advertiz.model.Purchase;
import com.quinbay.advertiz.model.Quotehistory;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService implements PurchaseInterface {


    @Autowired
    AdvertisementRepository advertisementRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    QuotehistoryRepository quotehistoryRepository;

    @Override
    public List<Purchase> getPurchasedList(){
        return purchaseRepository.findByStatus(true);
    }

    @Override
    public List<Purchase> getUnApprovedQuotes(){
        return purchaseRepository.findByStatus(false);
    }

    @Override
    public ResponseEntity revokeQuoteRequest(int adid, int buyerid){
        try{
            Optional<Purchase> quoteToBeDeleted = purchaseRepository.findByAdvertisementidAndBuyerid(adid, buyerid);
            purchaseRepository.delete(quoteToBeDeleted.get());
            return new ResponseEntity("Quote Request deleted for the advertisement!!!", HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity("Error occured", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity approveQuotedAmount(int adid, int sellerid){
        try{
            Optional<Purchase> purchase = purchaseRepository.findByAdvertisementidAndSellleridAndStatus(adid, sellerid, false);
            System.out.println(purchase.get().getAdvertisementid());
            if(purchase.isPresent()){
                purchase.get().setStatus(true);
                purchaseRepository.save(purchase.get());

                Optional<Advertisement> advertisement = advertisementRepository.findById(adid);
                advertisement.get().setStatus(true);
                advertisementRepository.save(advertisement.get());
                return new ResponseEntity("The product is sold for the highest quoted amount!!", HttpStatus.OK);
            }
            else{
                return new ResponseEntity("The advertisement not found", HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity("Error occured", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Object getTopQuotedAmount(int adid){
        try{
            Optional<Purchase> quotedForAd = purchaseRepository.findByAdvertisementidAndStatus(adid, false);
            System.out.println(quotedForAd.get().getAdvertisementid());
            if(quotedForAd.isPresent()){
                return quotedForAd.get().getFinalprice();
            }
            return new ResponseEntity("Product may be sold or not found!!!", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity("Error occured", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity purchaseItem(int adId, int buyerId){
        try {
            Optional<Advertisement> advertisement = advertisementRepository.findById(adId);
            int sellerId = advertisement.get().getSellerid();
            Purchase currentPurchase = new Purchase();

            currentPurchase.setAdvertisementid(adId);
            currentPurchase.setBuyerid(buyerId);
            currentPurchase.setSelllerid(sellerId);
            currentPurchase.setFinalprice(advertisement.get().getPrice());
            currentPurchase.setStatus(true);
            currentPurchase.setDate(java.time.LocalDateTime.now());
            purchaseRepository.save(currentPurchase);
            advertisement.get().setStatus(true);
            advertisementRepository.save(advertisement.get());
            return new ResponseEntity("Purchased Item!!!",HttpStatus.OK);
        }
        catch(Exception e){
            System.out.println(e);
            return new ResponseEntity("Error occured", HttpStatus.BAD_REQUEST);

        }
    }

    @Override
    public ResponseEntity quoteAmountForAdvertisement(int adid, int buyerid, int quoteAmount){
        try {
            Optional<Advertisement> ad = advertisementRepository.findById(adid);
            if (ad.get().getStatus() == false) {
                Optional<Purchase>  purchase = purchaseRepository.findByAdvertisementid(adid);
                if(purchase.isPresent()) {
                    if (purchase.get().getFinalprice() < quoteAmount) {
                        Quotehistory quotebackup = new Quotehistory();
                        quotebackup.setAdvertisementid(adid);
                        quotebackup.setBuyerid(purchase.get().getBuyerid());
                        quotebackup.setSelllerid(purchase.get().getSelllerid());
                        quotebackup.setPrice(purchase.get().getFinalprice());
                        quotebackup.setDate(purchase.get().getDate());
                        quotehistoryRepository.save(quotebackup);

                        purchase.get().setBuyerid(buyerid);
                        purchase.get().setDate(LocalDateTime.now());
                        purchase.get().setFinalprice(quoteAmount);
                        purchaseRepository.save(purchase.get());
                        return new ResponseEntity("The quote is updated with the current amount and the previous quote is stored in the history", HttpStatus.OK);
                    } else {
                        Quotehistory quotebackup = new Quotehistory();
                        quotebackup.setAdvertisementid(adid);
                        quotebackup.setBuyerid(buyerid);
                        quotebackup.setSelllerid(purchase.get().getSelllerid());
                        quotebackup.setPrice(quoteAmount);
                        quotebackup.setDate(LocalDateTime.now());
                        quotehistoryRepository.save(quotebackup);

                        return  new ResponseEntity("The quote is stored in the priority list", HttpStatus.OK);
                    }
                }
                else{
                    Purchase newQuote = new Purchase();
                    newQuote.setAdvertisementid(adid);
                    newQuote.setBuyerid(buyerid);
                    newQuote.setSelllerid(ad.get().getSellerid());
                    newQuote.setFinalprice(quoteAmount);
                    newQuote.setDate(LocalDateTime.now());
                    newQuote.setStatus(false);
                    purchaseRepository.save(newQuote);

                    return new ResponseEntity("New quote for the product is added!!!", HttpStatus.OK);
                }
            } else {
                return new ResponseEntity("The item has been purchased already", HttpStatus.ALREADY_REPORTED);
            }
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Error occured", HttpStatus.BAD_REQUEST);
        }
    }
}
