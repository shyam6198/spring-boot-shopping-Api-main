package com.miniproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    LocalDate localDate=LocalDate.now();

    @Column(name = "total_price")
    private Double totalPrice;


    @OneToMany( fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    private Long customer_id;

}
