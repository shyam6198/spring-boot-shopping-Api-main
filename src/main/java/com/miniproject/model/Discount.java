package com.miniproject.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "DISCOUNT")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String discountName;
    private String discountPer;
    private String status;

}
