package com.quinbay.advertiz.service;


import com.quinbay.advertiz.Repositories.AdvertisementRepository;
import com.quinbay.advertiz.Repositories.AdviewsRepository;
import com.quinbay.advertiz.Repositories.CategoryRepository;
import com.quinbay.advertiz.Repositories.SubcategoryRepository;
import com.quinbay.advertiz.functions.AdvertisementInterface;
import com.quinbay.advertiz.model.*;
import com.quinbay.advertiz.pojo.Adpost;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdvertisementService implements AdvertisementInterface {

    @Autowired
    AdvertisementRepository advertisementRepository;

    @Autowired
    SubcategoryRepository subcategoryRepository;

    @Autowired
    AdviewsRepository adviewsRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Advertisement> getAllAds(){
        return advertisementRepository.findAll();
    }

    @Override
    public Optional<Advertisement> getAdById(int id){
        return advertisementRepository.findById(id);
    }

    @Override
    public List<Advertisement> getPendingAdvertisement(){
        return advertisementRepository.findByStatus(false);
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
//            ad.setCategoryid(categoryid);
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
        return advertisementRepository.findByCategoryid(category.get().getId());
    }

    @Override
    public List<Advertisement> getAdsBySubcategory(String cname, String sname){
        return advertisementRepository.findByCategoryidAndSubcategoryid(cname, sname);
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
            advertisementRepository.delete(adToBeDeleted.get());
            return new ResponseEntity("Successfully deleted",HttpStatus.OK);
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
