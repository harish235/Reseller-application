package com.quinbay.advertiz.controller;


import com.quinbay.advertiz.Repositories.CategoryRepository;
import com.quinbay.advertiz.Repositories.SubcategoryRepository;
import com.quinbay.advertiz.functions.AdvertisementInterface;
import com.quinbay.advertiz.functions.CategoryInterface;
import com.quinbay.advertiz.model.Advertisement;
import com.quinbay.advertiz.model.Category;
import com.quinbay.advertiz.model.CategoryResponse;
import com.quinbay.advertiz.model.Subcategory;
import com.quinbay.advertiz.pojo.Adpost;
import com.quinbay.advertiz.pojo.AdvertisementRequest;
import com.quinbay.advertiz.pojo.CategoryRequest;
import com.quinbay.advertiz.pojo.PurchasedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AdController {

    @Autowired
    CategoryInterface categoryInterface;

    @Autowired
    AdvertisementInterface advertisementInterface;

    @PostMapping("/loadCategories")
    public Category loadCategories(@RequestBody CategoryRequest request){
        return categoryInterface.loadCategories(request);
    }

    @GetMapping("findAllCategories")
    public List<Category> findAllCategories(){
        return categoryInterface.findAllCategories();
    }

    @GetMapping("/categories")
    public List<Category> getAllCategory(){
        return categoryInterface.getAllCategory();
    }

    @GetMapping("/getCategories")
    public List<CategoryResponse> getCategories(){
        return categoryInterface.getCategories();
    }

    @GetMapping("/Ads")
    public List<Advertisement> getAllAds(){
        return advertisementInterface.getAllAds();
    }

    @GetMapping("/AdById")
    public Optional<Advertisement> getAdById(@RequestParam int adid){
        return advertisementInterface.getAdById(adid);
    }

    @GetMapping("/getMinimumPriceForAd")
    public Object getMinimumPriceForProduct(@RequestParam int adid){
        return advertisementInterface.getMinimumPriceForAd(adid);
    }

    @GetMapping("/AdsOfUser")
    public List<Advertisement> getAdsOfUser(@RequestParam int userid){
        return advertisementInterface.getAdsOfUser(userid);
    }

    @GetMapping("/getTotalViews")
    public int getTotalViews(@RequestParam int adid){
        return advertisementInterface.getTotalViews(adid);
    }

    @GetMapping("/pendingAdvertisements")
    public List<AdvertisementRequest> getpendingAdvertisement(){
        return advertisementInterface.getPendingAdvertisement();
    }

    @GetMapping("/PendingAdvertisementsOfUser")
    public List<Advertisement> getPendingAdvertisementOfUser(@RequestParam int userid){
        return advertisementInterface.getPendingAdvertisementOfUser(userid);
    }

    @GetMapping("/SoldItemsOfUser")
    public List<PurchasedResponse> getSoldAdsOfUser(@RequestParam int userid){
        return advertisementInterface.getSoldAdsOfUser(userid);
    }

    @PutMapping("/updateMinimumPrice")
    public ResponseEntity updateMinimumPriceForAd(@RequestParam int adid, @RequestParam int sellerId,  @RequestParam int minimumPrice){
        return advertisementInterface.updateMinimumPrice(adid, sellerId, minimumPrice);
    }

    @PostMapping("/postAdvertisement")
    public Advertisement postAd(@RequestBody Adpost ad){
        return advertisementInterface.postAdvertisement(ad);
    }

    @DeleteMapping("/deleteAdvertisement")
    public ResponseEntity deleteAd(@RequestParam int sellerid, @RequestParam int adid){
        return advertisementInterface.deleteAdvertisement(sellerid, adid);
    }

    @RequestMapping(path = "/subcategory/{subcategory}", method = RequestMethod.GET)
    public List<Advertisement> getAdsBySubcategory(@PathVariable("subcategory") String subcategoryname) {

        return advertisementInterface.getAdsBySubcategory(subcategoryname);
    }

    @RequestMapping(path = "/{category}", method = RequestMethod.GET)
    public List<Advertisement> getAdsByCategory(@PathVariable("category") String categoryname) {

        return advertisementInterface.getAdsByCategory(categoryname);
    }

    @PostMapping("/addView")
    public Boolean addViewForAd(@RequestParam int adid, @RequestParam int userid){
        return advertisementInterface.addViewCount(adid, userid);
    }

}
