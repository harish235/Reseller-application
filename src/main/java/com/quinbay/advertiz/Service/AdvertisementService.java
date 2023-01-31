package com.quinbay.advertiz.Service;

import com.quinbay.advertiz.model.Advertisement;
import com.quinbay.advertiz.pojo.Adpost;
import com.quinbay.advertiz.pojo.AdvertisementRequest;
import com.quinbay.advertiz.pojo.PurchasedResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AdvertisementService {

    List<Advertisement> getAllAds();
    Optional<Advertisement> getAdById(int id);

    Advertisement postAdvertisement(Adpost ad);

    List<Advertisement> getAdsBySubcategory(String sname);

    List<Advertisement> getAdsByCategory(String  cname);

    ResponseEntity deleteAdvertisement(int userId, int adId);

    Object getMinimumPriceForAd(int adid);

    ResponseEntity updateMinimumPrice(int adid, int sellerId, int minPrice);

    List<AdvertisementRequest> getPendingAdvertisement();

    List<Advertisement> getPendingAdvertisementOfUser(int userid);

    List<PurchasedResponse> getSoldAdsOfUser(int userid);

    boolean addViewCount(int adid, int userid);

    int getTotalViews(int adid);

    List<Advertisement> getAdsOfUser(int userid);
}
