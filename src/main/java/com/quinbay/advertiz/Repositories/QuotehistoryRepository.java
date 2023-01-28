package com.quinbay.advertiz.Repositories;


import com.quinbay.advertiz.model.Quotehistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotehistoryRepository extends JpaRepository<Quotehistory, Integer> {
}
