//package com.miniproject.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@ToString
//@Table(name="cart")
//public class Cart {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//
//    @Column(name = "created_date")
//    private Date createdDate;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id", referencedColumnName = "id")
//    private long product;
//
//    @JsonIgnore
//    @OneToOne(targetEntity = UserDao.class, fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false, name = "user_id")
//    private UserDao customer;
//
//
//    private int quantity;
//
//}
