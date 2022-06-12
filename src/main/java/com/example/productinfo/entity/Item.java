package com.example.productinfo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String itemType;

    @Column(nullable = false)
    private Integer itemPrice;

    @Column(nullable = false)
    private Date itemDisplayStartDate;

    @Column(nullable = false)
    private Date itemDisplayEndDate;

    private Double discountPrice;
}
