package com.quinbay.advertiz.functions;

import com.quinbay.advertiz.model.Advertisement;
import com.quinbay.advertiz.pojo.Adpost;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AdvertisementInterface {

    List<Advertisement> getAllAds();
    Optional<Advertisement> getAdById(int id);

    Advertisement postAdvertisement(Adpost ad);

    List<Advertisement> getAdsBySubcategory(String cname, String sname);

    List<Advertisement> getAdsByCategory(String  cname);

    ResponseEntity deleteAdvertisement(int userId, int adId);

    Object getMinimumPriceForAd(int adid);

    ResponseEntity updateMinimumPrice(int adid, int sellerId, int minPrice);

    List<Advertisement> getPendingAdvertisement();

    boolean addViewCount(int adid, int userid);

    int getTotalViews(int adid);

    List<Advertisement> getAdsOfUser(int userid);

//    ResponseEntity updateAdvertisement(int userId, int adId);
}
