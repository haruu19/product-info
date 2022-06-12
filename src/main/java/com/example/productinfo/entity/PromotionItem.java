package com.example.productinfo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_promotion_item")
public class PromotionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promotionItemId;

    @Column(nullable = false)
    private Long promotionId;

    @Column(nullable = false)
    private Long itemId;
}