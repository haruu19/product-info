package com.example.productinfo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_promotion")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promotionId;

    @Column(nullable = false)
    private String promotionNm;

    @Column(nullable = false)
    private Integer discountAmount;

    @Column(nullable = false)
    private Double discountRate;

    @Column(nullable = false)
    private Date promotionStartDate;

    @Column(nullable = false)
    private Date promotionEndDate;
}
