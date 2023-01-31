package com.quinbay.advertiz.Repositories;


import com.quinbay.advertiz.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    List<Purchase> findByStatusAndSelllerid(boolean b, int sellerid);

    Optional<Purchase> findByAdvertisementidAndBuyerid(int adid, int buyerid);

    Optional<Purchase> findByAdvertisementid(int adid);

    Optional<Purchase> findByAdvertisementidAndSellleridAndStatus(int adid, int sellerid, boolean status);

    Optional<Purchase> findByAdvertisementidAndStatus(int adid, boolean status);

    List<Purchase> findByBuyeridAndStatus(int buyerid, boolean status);
}
