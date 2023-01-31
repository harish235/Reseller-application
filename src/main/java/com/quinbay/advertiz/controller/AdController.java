package com.quinbay.advertiz.controller;


import com.quinbay.advertiz.Service.AdvertisementService;
import com.quinbay.advertiz.Service.CategoryService;
import com.quinbay.advertiz.model.Advertisement;
import com.quinbay.advertiz.model.Category;
import com.quinbay.advertiz.model.CategoryResponse;
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
    CategoryService categoryService;

    @Autowired
    AdvertisementService advertisementService;

    @PostMapping("/loadCategories")
    public Category loadCategories(@RequestBody CategoryRequest request){
        return categoryService.loadCategories(request);
    }

    @GetMapping("findAllCategories")
    public List<Category> findAllCategories(){
        return categoryService.findAllCategories();
    }

    @GetMapping("/categories")
    public List<Category> getAllCategory(){
        return categoryService.getAllCategory();
    }

    @GetMapping("/getCategories")
    public List<CategoryResponse> getCategories(){
        return categoryService.getCategories();
    }

    @GetMapping("/Ads")
    public List<Advertisement> getAllAds(){
        return advertisementService.getAllAds();
    }

    @GetMapping("/AdById")
    public Optional<Advertisement> getAdById(@RequestParam int adid){
        return advertisementService.getAdById(adid);
    }

    @GetMapping("/getMinimumPriceForAd")
    public Object getMinimumPriceForProduct(@RequestParam int adid){
        return advertisementService.getMinimumPriceForAd(adid);
    }

    @GetMapping("/AdsOfUser")
    public List<Advertisement> getAdsOfUser(@RequestParam int userid){
        return advertisementService.getAdsOfUser(userid);
    }

    @GetMapping("/getTotalViews")
    public int getTotalViews(@RequestParam int adid){
        return advertisementService.getTotalViews(adid);
    }

    @GetMapping("/pendingAdvertisements")
    public List<AdvertisementRequest> getpendingAdvertisement(){
        return advertisementService.getPendingAdvertisement();
    }

    @GetMapping("/PendingAdvertisementsOfUser")
    public List<Advertisement> getPendingAdvertisementOfUser(@RequestParam int userid){
        return advertisementService.getPendingAdvertisementOfUser(userid);
    }

    @GetMapping("/SoldItemsOfUser")
    public List<PurchasedResponse> getSoldAdsOfUser(@RequestParam int userid){
        return advertisementService.getSoldAdsOfUser(userid);
    }

    @PutMapping("/updateMinimumPrice")
    public ResponseEntity updateMinimumPriceForAd(@RequestParam int adid, @RequestParam int sellerId,  @RequestParam int minimumPrice){
        return advertisementService.updateMinimumPrice(adid, sellerId, minimumPrice);
    }

    @PostMapping("/postAdvertisement")
    public Advertisement postAd(@RequestBody Adpost ad){
        return advertisementService.postAdvertisement(ad);
    }

    @DeleteMapping("/deleteAdvertisement")
    public ResponseEntity deleteAd(@RequestParam int sellerid, @RequestParam int adid){
        return advertisementService.deleteAdvertisement(sellerid, adid);
    }

    @RequestMapping(path = "/subcategory/{subcategory}", method = RequestMethod.GET)
    public List<Advertisement> getAdsBySubcategory(@PathVariable("subcategory") String subcategoryname) {

        return advertisementService.getAdsBySubcategory(subcategoryname);
    }

    @RequestMapping(path = "/{category}", method = RequestMethod.GET)
    public List<Advertisement> getAdsByCategory(@PathVariable("category") String categoryname) {

        return advertisementService.getAdsByCategory(categoryname);
    }

    @PostMapping("/addView")
    public Boolean addViewForAd(@RequestParam int adid, @RequestParam int userid){
        return advertisementService.addViewCount(adid, userid);
    }

}
