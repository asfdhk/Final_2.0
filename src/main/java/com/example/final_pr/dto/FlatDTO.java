package com.example.final_pr.dto;


import com.example.final_pr.enums.Heating;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class FlatDTO {

    private String address;

    private Integer price;

    private Integer area;

    private Integer storey;

    @Enumerated(EnumType.STRING)
    private Heating heating;

    private boolean balcony;

    private boolean gas;

    @JsonCreator
    public FlatDTO(@JsonProperty(required = true) String address,
                   @JsonProperty(required = true) Integer price,
                   @JsonProperty(required = true) Integer area,
                   @JsonProperty(required = true) Integer storey) {
        this.address = address;
        this.area = area;
        this.price = price;
        this.storey= storey;
    }

    private FlatDTO( String address, Integer price, Integer area, Integer storey,
                   Heating heating, boolean balcony, boolean gas){
        this.address = address;
        this.price = price;
        this.area = area;
        this.storey = storey;
        this.heating = heating;
        this.balcony = balcony;
        this.gas = gas;
    }



    public static FlatDTO of(String address, Integer price, Integer area, Integer storey,
                             Heating heating, boolean balcony, boolean gas){
        return new FlatDTO(address,price,area,storey,heating,balcony,gas);
    }

}
