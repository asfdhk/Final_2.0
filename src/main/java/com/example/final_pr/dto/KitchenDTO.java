package com.example.final_pr.dto;


import com.example.final_pr.enums.FloorType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class KitchenDTO {

    private Integer area;

    private boolean dishWash;

    private boolean microwaveOven;

    private boolean refrigerator;

    private boolean oven;

    private boolean cooker;

    @Enumerated(EnumType.STRING)
    private FloorType floor;

    private KitchenDTO(Integer area, boolean dishWash, boolean microwaveOven,
                       boolean refrigerator, boolean oven, boolean cooker,FloorType floor){
        this.area = area;
        this.dishWash = dishWash;
        this.microwaveOven = microwaveOven;
        this.refrigerator = refrigerator;
        this.oven = oven;
        this.cooker = cooker;
        this.floor = floor;
    }

    public static KitchenDTO of(Integer area, boolean dishWash, boolean microwaveOven,
                                boolean refrigerator, boolean oven, boolean cooker,FloorType floor){
        return new KitchenDTO(area, dishWash, microwaveOven, refrigerator, oven, cooker,floor);
    }
}
