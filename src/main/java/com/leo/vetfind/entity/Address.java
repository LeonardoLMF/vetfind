package com.leo.vetfind.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Column(name = "street")
    private String street;

    @Column(name = "number", length = 10)
    private String number;

    @Column(name = "complement")
    private String complement;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "city")
    private String city;

    @Column(name = "state", length = 2)
    private String state;  // SP RJ etc...

    @Column(name = "zip_code", length = 8)
    private String zipCode;  // 12000100
}
