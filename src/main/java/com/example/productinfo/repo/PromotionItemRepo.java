package com.example.productinfo.repo;

import com.example.productinfo.entity.PromotionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionItemRepo extends JpaRepository<PromotionItem, Long>  {
    List<PromotionItem> findByItemId(Long itemId);
}
