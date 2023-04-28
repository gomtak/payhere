package com.payhere.domain.model;

import com.payhere.domain.common.BaseTimeEntity;
import com.payhere.domain.common.Size;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "product")
public class ProductDataModel extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;
    //카테고리
    private String category;
    //가격
    private double price;
    //원가
    private double cost;
    //이름
    private String name;
    //설명
    private String description;
    //바코드
    private String barcode;
    //유통기한
    private Timestamp expirationDate;
    //사이즈 small or large
    @Enumerated(EnumType.STRING)
    private Size size;

}
