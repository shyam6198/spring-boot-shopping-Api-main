package com.miniproject.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "Product_Details")
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productKey;
    private String productValue;
}
