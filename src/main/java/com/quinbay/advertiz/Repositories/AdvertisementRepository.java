package com.quinbay.advertiz.Repositories;


import com.quinbay.advertiz.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {

    List<Advertisement> findByCategoryidAndSubcategoryid(String cname, String sname);

    List<Advertisement> findByCategoryid(int cid);

    Optional<Advertisement> findBySelleridAndAdidAndStatus(int sId, int adId, boolean b);

    Optional<Advertisement> findByAdidAndSellerid(int adid, int sellerid);

    List<Advertisement> findByStatus(boolean b);

    List<Advertisement> findBySellerid(int userid);
}

