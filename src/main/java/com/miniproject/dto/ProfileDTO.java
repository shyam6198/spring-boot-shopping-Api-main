package com.miniproject.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProfileDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String emailId;
    private String mobileno;
    private String address;
    private String pincode;
}
