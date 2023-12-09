package com.example.final_pr.dto;


import com.example.final_pr.enums.Heating;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrivateHouseDTO {

    private String address;

    private Integer price;

    private Integer area;

    private Integer amountStoreys;

    @Enumerated(EnumType.STRING)
    private Heating heating;

    private boolean garage;

    private boolean gas;

    private PrivateHouseDTO(String address, Integer price, Integer area, Integer amountStoreys,
                            Heating heating, boolean garage, boolean gas){
        this.address = address;
        this.price = price;
        this.area = area;
        this.amountStoreys = amountStoreys;
        this.heating = heating;
        this.garage = garage;
        this.gas = gas;
    }

    public static PrivateHouseDTO of(String address, Integer price, Integer area, Integer amountStoreys,
                                     Heating heating, boolean garage, boolean gas){
        return new PrivateHouseDTO(address, price, area, amountStoreys, heating, garage, gas);
  }
}
