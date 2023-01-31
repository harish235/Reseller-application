package com.quinbay.advertiz.Implementation;


import com.quinbay.advertiz.Repositories.*;
import com.quinbay.advertiz.Service.AdvertisementService;
import com.quinbay.advertiz.model.*;
import com.quinbay.advertiz.pojo.Adpost;
import com.quinbay.advertiz.pojo.AdvertisementRequest;
import com.quinbay.advertiz.pojo.PurchasedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdvertisementImpl implements AdvertisementService {

    @Autowired
    AdvertisementRepository advertisementRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    QuotehistoryRepository quotehistoryRepository;

    @Autowired
    SubcategoryRepository subcategoryRepository;

    @Autowired
    AdviewsRepository adviewsRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Advertisement> getAllAds(){
        System.out.println("\n\tentering");
        return (List<Advertisement>)advertisementRepository.findAll();
    }

    @Override
    public Optional<Advertisement> getAdById(int id){
        return advertisementRepository.findById(id);
    }

//    @Override
//    public List<Advertisement> getPendingAdvertisement(){
//        return advertisementRepository.findByStatus(false);
//    }

    @Override
    public List<AdvertisementRequest> getPendingAdvertisement(){
        List<AdvertisementRequest> advertisementRequests = new ArrayList<>();
        List<Advertisement> advertisementList = advertisementRepository.findByStatus(false);
        for(Advertisement ad: advertisementList){
            AdvertisementRequest adrequest = new AdvertisementRequest();
            Optional<Purchase> purchase = purchaseRepository.findByAdvertisementidAndStatus(ad.getAdid(), false);
            if(purchase.isPresent()){
                adrequest.setTopQuotedAmount(purchase.get().getFinalprice());
            }
            else{
                adrequest.setTopQuotedAmount(0);
            }
            adrequest.setAdid(ad.getAdid());
            adrequest.setSellerid(ad.getSellerid());
            adrequest.setSubcategoryid(ad.getSubcategoryid());
            adrequest.setCategoryid(ad.getCategoryid());
            adrequest.setTitle(ad.getTitle());
            adrequest.setDescription(ad.getDescription());
            adrequest.setPrice(ad.getPrice());
            adrequest.setMinimumprice(ad.getMinimumprice());
            adrequest.setPostedDate(ad.getPosetdDate());
            adrequest.setStatus(ad.getStatus());
            adrequest.setViewcount(ad.getViewcount());
            adrequest.setImgpath(ad.getImgpath());

            advertisementRequests.add(adrequest);
        }
        return advertisementRequests;
    }

    @Override
    public  List<Advertisement> getPendingAdvertisementOfUser(int userid){
        return advertisementRepository.findBySelleridAndStatus(userid, false);
    }

//    @Override
//    public List<Advertisement> getSoldAdsOfUser(int userid){
//        return advertisementRepository.findBySelleridAndStatus(userid, true);
//    }

    @Override
    public List<PurchasedResponse> getSoldAdsOfUser(int userid){
        List<PurchasedResponse> purchasedResponseList = new ArrayList<>();
        List<Purchase> purchaseList = purchaseRepository.findByStatusAndSelllerid(true, userid);

        for(Purchase purchase: purchaseList){
            PurchasedResponse pr = new PurchasedResponse();
            Optional<User> user = userRepository.findById(userid);
            Optional<Advertisement> advertisement = advertisementRepository.findById(purchase.getAdvertisementid());
            pr.setBuyerName(user.get().getUsername());
            pr.setAdTitle(advertisement.get().getTitle());
            pr.setFinalprice(purchase.getFinalprice());
            pr.setDate(purchase.getDate());
            purchasedResponseList.add(pr);
        }
        return purchasedResponseList;
    }

    @Override
    public boolean addViewCount(int adid, int userid){
        try{
            Optional<Adviews> adview = adviewsRepository.findByAdidAndUserid(adid, userid);
            if(!adview.isPresent()){
                Adviews newView = new Adviews();
                newView.setAdid(adid);
                newView.setUserid(userid);
                adviewsRepository.save(newView);
                Optional<Advertisement> ad = advertisementRepository.findById(adid);
                int totalViews = getTotalViews(adid);
                ad.get().setViewcount(totalViews);
                advertisementRepository.save(ad.get());
                return true;
            }
            return false;
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    @Override
    public int getTotalViews(int adid){
        List<Adviews> adviews = adviewsRepository.findByAdid(adid);
        return adviews.size();
    }

    @Override
    public Advertisement postAdvertisement(Adpost ad){
        try{

            String subcategoryname = ad.getSubcategoryname();
            Optional<Subcategory> subcategory = subcategoryRepository.findByName(subcategoryname);
            System.out.println(subcategory.get().getName());

            int categoryid = subcategory.get().getCategoryid();
            Advertisement newAd = new Advertisement();
            newAd.setTitle(ad.getTitle());
            newAd.setDescription(ad.getDescription());
            newAd.setCategoryid(categoryid);
            newAd.setSellerid(ad.getSellerid());
            newAd.setSubcategoryid(subcategory.get().getId());
            newAd.setPrice(ad.getPrice());
            newAd.setMinimumprice(ad.getMinimumprice());
            newAd.setPosetdDate(LocalDateTime.now());
            newAd.setStatus(false);
            newAd.setViewcount(0);
            newAd.setImgpath(ad.getImgPath());
            return advertisementRepository.save(newAd);
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List<Advertisement> getAdsOfUser(int userid){
        return advertisementRepository.findBySellerid(userid);
    }


    @Override
    public List<Advertisement> getAdsByCategory(String cname){
        Optional<Category> category = categoryRepository.findByName(cname);
        return advertisementRepository.findByCategoryidAndStatus(category.get().getId(), false);
    }

    @Override
    public List<Advertisement> getAdsBySubcategory(String sname){
        Optional<Subcategory> subcategory = subcategoryRepository.findByName(sname);
        return advertisementRepository.findBySubcategoryidAndStatus(subcategory.get().getId(), false);
    }

    @Override
    public  Object getMinimumPriceForAd(int adid){
        Optional<Advertisement> advertisement = advertisementRepository.findById(adid);
        if(advertisement.isPresent()){
            return advertisement.get().getMinimumprice();
        }
        return new ResponseEntity("no such ad!!!", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity deleteAdvertisement(int sId, int adid){
        try {
            Optional<Advertisement> adToBeDeleted = advertisementRepository.findBySelleridAndAdidAndStatus(sId, adid, false);
            if(adToBeDeleted.isPresent()) {
                advertisementRepository.delete(adToBeDeleted.get());
                Optional<Purchase> purchase = purchaseRepository.findByAdvertisementid(adToBeDeleted.get().getAdid());
                purchaseRepository.delete(purchase.get());
                List<Quotehistory> quotehistoryList = quotehistoryRepository.findByAdvertisementid(adToBeDeleted.get().getAdid());
                quotehistoryRepository.deleteAll(quotehistoryList);
                return new ResponseEntity("Successfully deleted advertisement and all its associated quotes", HttpStatus.OK);
            }
            else{
                return new ResponseEntity("No such advertisement or the item may have been purchased", HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            System.out.println(e);
            return new ResponseEntity("Error occured", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity updateMinimumPrice(int adid, int sellerid,  int minimumPrice){
        try{
            Optional<Advertisement> advertisement = advertisementRepository.findByAdidAndSellerid(adid, sellerid);
            if(advertisement.isPresent()){
                advertisement.get().setMinimumprice(minimumPrice);
                advertisementRepository.save(advertisement.get());
                return new ResponseEntity("Minimum price updated!!!", HttpStatus.OK);
            }
            else{
                return new ResponseEntity("The advertisement not found!!!", HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity("Error occured", HttpStatus.BAD_REQUEST);
        }
    }

}
