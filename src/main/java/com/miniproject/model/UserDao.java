package com.miniproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String emailId;
    @Column
    private String password;
    @Column
    private String mobileno;
    @Column
    private String address;
    @Column
    private String pincode;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "customers_roles",
            joinColumns = @JoinColumn(name = "use_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Role role;
}
