package com.quinbay.advertiz.Repositories;


import com.quinbay.advertiz.model.Adviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdviewsRepository extends JpaRepository<Adviews, Integer> {

    Optional<Adviews> findByAdidAndUserid(int adid, int userid);

    List<Adviews> findByAdid(int adid);
}
