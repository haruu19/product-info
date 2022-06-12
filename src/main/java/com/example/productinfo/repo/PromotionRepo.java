package com.example.productinfo.repo;

import com.example.productinfo.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepo extends JpaRepository<Promotion, Long> {
}
