package com.quinbay.advertiz.Repositories;


import com.quinbay.advertiz.model.Quotehistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuotehistoryRepository extends JpaRepository<Quotehistory, Integer> {

    List<Quotehistory> findByAdvertisementid(int id);

    List<Quotehistory> findByBuyerid(int buyerid);

    Optional<Quotehistory> findByAdvertisementidAndBuyerid(int adid, int buyerid);
}
