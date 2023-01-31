package com.quinbay.advertiz.Implementation;

import java.time.LocalDateTime;
import com.quinbay.advertiz.Repositories.AdvertisementRepository;
import com.quinbay.advertiz.Repositories.PurchaseRepository;
import com.quinbay.advertiz.Repositories.QuotehistoryRepository;
import com.quinbay.advertiz.Repositories.UserRepository;
import com.quinbay.advertiz.Service.PurchaseService;
import com.quinbay.advertiz.model.Advertisement;
import com.quinbay.advertiz.model.Purchase;
import com.quinbay.advertiz.model.Quotehistory;
import com.quinbay.advertiz.model.User;
import com.quinbay.advertiz.pojo.EmailRequest;
import com.quinbay.advertiz.pojo.PurchasedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.*;

@Service
public class PurchaseImpl implements PurchaseService {


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    AdvertisementRepository advertisementRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    QuotehistoryRepository quotehistoryRepository;

    @Autowired
    UserRepository userRepository;



    @Override
    public List<Purchase> getSoldListOfSeller(int sellerid){
        return purchaseRepository.findByStatusAndSelllerid(true, sellerid);
    }

    @Override
    public List<Purchase> getUnApprovedQuotes(int sellerid){
        return purchaseRepository.findByStatusAndSelllerid(false, sellerid);
    }

    @Override
    public List<Purchase> getTopPriorityQuoteOfBuyer(int buyerid){
        return purchaseRepository.findByBuyeridAndStatus(buyerid, false);
    }

    @Override
    public List<Quotehistory> getLeastPriorityQuotesOfBuyer(int buyerid){
        return quotehistoryRepository.findByBuyerid(buyerid);
    }

    @Override
    public List<PurchasedResponse> getPurchasedListOfBuyer(int buyerid){
//        return purchaseRepository.findByBuyeridAndStatus(buyerid, true);
        List<PurchasedResponse> purchasesResponses = new ArrayList<>();
        List<Purchase> purchases = purchaseRepository.findByBuyeridAndStatus(buyerid, true);
        for(Purchase p: purchases){
            Optional<Advertisement> ad = advertisementRepository.findById(p.getAdvertisementid());
            String adTitle = ad.get().getTitle();
            Optional<User> buyer = userRepository.findById(buyerid);
            Optional<User> seller = userRepository.findById(ad.get().getSellerid());
            PurchasedResponse pr = new PurchasedResponse();
            pr.setAdvertisementid(p.getAdvertisementid());
            pr.setDate(p.getDate());
            pr.setFinalprice(p.getFinalprice());
            pr.setPid(p.getPid());
            pr.setAdTitle(adTitle);
            pr.setBuyerName(buyer.get().getUsername());
            pr.setSelllerEmail(seller.get().getEmail());
            purchasesResponses.add(pr);
        }
        return purchasesResponses;
    }

    @Override
    public ResponseEntity revokeQuoteRequest(int adid, int buyerid){
        try{
            Optional<Quotehistory> quote = quotehistoryRepository.findByAdvertisementidAndBuyerid(adid, buyerid);
            if(quote.isPresent()){
                quotehistoryRepository.delete(quote.get());
                return new ResponseEntity("Quote request deleted from the priority list", HttpStatus.OK);
            }
            else {
                Optional<Purchase> quoteToBeDeleted = purchaseRepository.findByAdvertisementidAndBuyerid(adid, buyerid);
                purchaseRepository.delete(quoteToBeDeleted.get());
                List<Quotehistory> quotehistories = quotehistoryRepository.findByAdvertisementid(adid);
                if (quotehistories.isEmpty()) {
                    return new ResponseEntity("Quote Request deleted for the advertisement!!!", HttpStatus.OK);
                } else {
                    Quotehistory quotehistory = quotehistories.stream()
                            .max(Comparator.comparingInt(Quotehistory::getPrice))
                            .get();
                    Purchase updatedPurchase = new Purchase();
                    updatedPurchase.setStatus(false);
                    updatedPurchase.setFinalprice(quotehistory.getPrice());
                    updatedPurchase.setDate(quotehistory.getDate());
                    updatedPurchase.setSelllerid(quotehistory.getSelllerid());
                    updatedPurchase.setBuyerid(quotehistory.getBuyerid());
                    updatedPurchase.setAdvertisementid(quotehistory.getAdvertisementid());
                    purchaseRepository.save(updatedPurchase);
                    quotehistoryRepository.delete(quotehistory);
                    return new ResponseEntity("Quote request deleted and updated with next higher quote request", HttpStatus.OK);
                }
            }
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

                List<Quotehistory> quotehistoriesTobeRemoved = quotehistoryRepository.findByAdvertisementid(adid);
                if(!quotehistoriesTobeRemoved.isEmpty()){
                    quotehistoryRepository.deleteAll(quotehistoriesTobeRemoved);
                }
                return new ResponseEntity("The product is sold for the highest quoted amount!!", HttpStatus.OK);
            }
            else{
                return new ResponseEntity("No quotes available", HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity("Error occured", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Object getTopQuotedAmount(int adid){
        try{
            Optional<Purchase> quotedForAd = purchaseRepository.findByAdvertisementidAndStatus(adid, false);
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
            Optional<Advertisement> advertisement = advertisementRepository.findByAdidAndStatus(adId, false);
            if(advertisement.get().getSellerid() == buyerId){
                return new ResponseEntity("Your the owner", HttpStatus.NOT_ACCEPTABLE);
            }
            if(advertisement.isPresent()) {
                Optional<Purchase> purchaseAlreadyPresent  = purchaseRepository.findByAdvertisementid(adId);
                if(purchaseAlreadyPresent.isPresent()){
                    purchaseAlreadyPresent.get().setDate(java.time.LocalDateTime.now());
                    purchaseAlreadyPresent.get().setFinalprice(advertisement.get().getPrice());
                    purchaseAlreadyPresent.get().setStatus(true);
                    purchaseAlreadyPresent.get().setBuyerid(buyerId);
                    purchaseRepository.save(purchaseAlreadyPresent.get());
                    List<Quotehistory> quoteHistoryToRemove = quotehistoryRepository.findByAdvertisementid(adId);
                    for(Quotehistory q: quoteHistoryToRemove){
                        quotehistoryRepository.delete(q);
                    }
                    return new ResponseEntity("New purchase is made and the existing quote is replaced", HttpStatus.OK);
                }
                else {
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
                    List<Quotehistory> quoteHistoryToRemove = quotehistoryRepository.findByAdvertisementid(adId);
                    for(Quotehistory q: quoteHistoryToRemove){
                        quotehistoryRepository.delete(q);
                    }
                    return new ResponseEntity("Purchased Item!!!", HttpStatus.OK);
                }
            }
            else{
                return new ResponseEntity("Item already purchased!!! ", HttpStatus.ALREADY_REPORTED);
            }
        }
        catch(Exception e){
            System.out.println(e);
            return new ResponseEntity("Error occured", HttpStatus.BAD_REQUEST);

        }
    }

    @Override
    public ResponseEntity quoteAmountForAdvertisement(int adid, int buyerid, int quoteAmount){
        try {

            EmailRequest newMail = new EmailRequest();

            Optional<Advertisement> ad = advertisementRepository.findById(adid);

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            Optional<User> user = userRepository.findById(buyerid);
            String buyerEmail = user.get().getEmail();

            int sellerid = ad.get().getSellerid();
            Optional<User> Recipient = userRepository.findById(sellerid);
            String recipientEmail = Recipient.get().getEmail();
            String msgBody = "Hello "+ Recipient.get().getUsername()+ ",a quote is made for the product "+ ad.get().getTitle()+ ", from the user "+user.get().getUsername()+ " with email id "+ user.get().getEmail()+"\n The quote amount is "+quoteAmount;
            newMail.setMsgBody(msgBody);
            newMail.setRecipient(buyerEmail);
            newMail.setSubject("New quote for "+ad.get().getTitle());
            String mailsender = UriComponentsBuilder.fromHttpUrl("http://localhost:8084/sendMail")
                    .queryParam("recipient", recipientEmail)
                    .queryParam("msgBody", msgBody)
                    .queryParam("subject", "New Quote for "+ ad.get().getTitle())
                    .toUriString();
            if(ad.get().getMinimumprice() < quoteAmount) {

                if (ad.get().getStatus() == false) {
                    Optional<Purchase> purchase = purchaseRepository.findByAdvertisementid(adid);
                    if (purchase.isPresent()) {
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
                            String s = restTemplate().exchange(mailsender, HttpMethod.POST, entity, String.class).getBody();
//                            String s = restTemplate().exchange("http://localhost:8084/sendMail", HttpMethod.POST, String.class);

                            return new ResponseEntity("The quote is updated with the current amount and the previous quote is stored in the history", HttpStatus.OK);
                        } else {
                            Optional<Quotehistory> quote = quotehistoryRepository.findByAdvertisementidAndBuyerid(adid, buyerid);
                            if(quote.isPresent()){
                                return new ResponseEntity("you have already quoted for an amount "+ quote.get().getPrice(), HttpStatus.ALREADY_REPORTED);
                            }
                            Quotehistory quotebackup = new Quotehistory();
                            quotebackup.setAdvertisementid(adid);
                            quotebackup.setBuyerid(buyerid);
                            quotebackup.setSelllerid(purchase.get().getSelllerid());
                            quotebackup.setPrice(quoteAmount);
                            quotebackup.setDate(LocalDateTime.now());
                            quotehistoryRepository.save(quotebackup);
                            String s = restTemplate().exchange(mailsender, HttpMethod.POST, entity, String.class).getBody();
                            return new ResponseEntity("The quote is stored in the priority list", HttpStatus.OK);
                        }
                    } else {
                        Purchase newQuote = new Purchase();
                        newQuote.setAdvertisementid(adid);
                        newQuote.setBuyerid(buyerid);
                        newQuote.setSelllerid(ad.get().getSellerid());
                        newQuote.setFinalprice(quoteAmount);
                        newQuote.setDate(LocalDateTime.now());
                        newQuote.setStatus(false);
                        purchaseRepository.save(newQuote);
                        String s = restTemplate().exchange(mailsender, HttpMethod.POST, entity, String.class).getBody();
                        return new ResponseEntity("New quote for the product is added!!!", HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity("The item has been purchased already", HttpStatus.ALREADY_REPORTED);
                }
            }
            else{
                return new ResponseEntity("Amount lesser than the minimum quote price, please try above "+ad.get().getMinimumprice(), HttpStatus.NOT_ACCEPTABLE);
            }
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Error occured", HttpStatus.BAD_REQUEST);
        }
    }
}
