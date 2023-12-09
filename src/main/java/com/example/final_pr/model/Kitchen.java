package com.example.final_pr.model;

import com.example.final_pr.dto.KitchenDTO;
import com.example.final_pr.enums.FloorType;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Kitchen {

    @Id
    @GeneratedValue
    private Long id;

    private Integer area;

    private boolean dishWash;

    private boolean microwaveOven;

    private boolean refrigerator;

    private boolean oven;

    private boolean cooker;

    @Enumerated(EnumType.STRING)
    private FloorType floor;

    @OneToOne
    @JoinColumn(name = "flat_id")
    private Flat flat;

    @OneToOne
    @JoinColumn(name = "private_house_id")
    private PrivateHouse privateHouse;

    private Kitchen(Integer area , boolean dishWash, boolean microwaveOven,
                    boolean refrigerator, boolean oven, boolean cooker, FloorType floor){
        this.area = area;
        this.dishWash = dishWash;
        this.microwaveOven = microwaveOven;
        this.refrigerator = refrigerator;
        this.oven = oven;
        this.cooker = cooker;
        this.floor = floor;
    }

    public static Kitchen of(Integer area , boolean dishWash, boolean microwaveOven,
                             boolean refrigerator, boolean oven, boolean cooker, FloorType floor){
        return new Kitchen(area,dishWash,microwaveOven,refrigerator,oven,cooker,floor);
    }

    public KitchenDTO toDTO(){
        return KitchenDTO.of(area,dishWash,microwaveOven,refrigerator,oven,cooker,floor);
    }

    public static Kitchen fromDTO(KitchenDTO kitchenDTO){
        return Kitchen.of(kitchenDTO.getArea(), kitchenDTO.isDishWash(), kitchenDTO.isMicrowaveOven(),
                kitchenDTO.isRefrigerator(), kitchenDTO.isOven(), kitchenDTO.isCooker(), kitchenDTO.getFloor());
    }


}
