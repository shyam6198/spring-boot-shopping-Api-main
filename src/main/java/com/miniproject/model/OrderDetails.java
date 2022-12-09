package com.miniproject.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ORDER_DETAILS")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productQty;
    private String product_price;
    private String subTotal;

}
