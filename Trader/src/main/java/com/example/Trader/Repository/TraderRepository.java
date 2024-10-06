package com.example.Trader.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Trader.Model.Trader;

public interface TraderRepository extends JpaRepository<Trader, Long> {
    
}
