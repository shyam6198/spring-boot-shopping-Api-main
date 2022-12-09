package com.miniproject.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class JwtSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String emailId;
    @Column(length = 500)
    private String token;
}
