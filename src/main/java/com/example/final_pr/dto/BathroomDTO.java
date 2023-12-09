package com.example.final_pr.dto;


import com.example.final_pr.enums.FloorType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@Data
public class BathroomDTO {

    private Integer area;

    private boolean bath;

    private boolean shower;

    private boolean mirror;

    private boolean washingMachine;

    private boolean boiler;

    @Enumerated(EnumType.STRING)
    private FloorType floor;

    private BathroomDTO(Integer area, boolean bath, boolean shower,
                        boolean mirror, boolean washingMachine, boolean boiler, FloorType floor){
        this.area = area;
        this.bath = bath;
        this.shower = shower;
        this.mirror = mirror;
        this.washingMachine = washingMachine;
        this.boiler = boiler;
        this.floor = floor;
    }

    public static BathroomDTO of(Integer area, boolean bath, boolean shower,
                                 boolean mirror, boolean washingMachine,boolean boiler, FloorType floor){
        return new BathroomDTO(area, bath, shower, mirror, washingMachine, boiler, floor);
    }
}
