package com.miniproject.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Product_Image")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;


}
