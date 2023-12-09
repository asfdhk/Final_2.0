package com.example.final_pr.model;

import com.example.final_pr.dto.BathroomDTO;
import com.example.final_pr.enums.FloorType;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Bathroom {

    @Id
    @GeneratedValue
    private Long id;

    private Integer area;

    private boolean bath;

    private boolean shower;

    private boolean mirror;

    private boolean washingMachine;

    private boolean boiler;

    @Enumerated(EnumType.STRING)
    private FloorType floor;

    @ManyToOne
    @JoinColumn(name = "flat_id")
    private Flat flat;

    @ManyToOne
    @JoinColumn(name = "private_house_id")
    private PrivateHouse privateHouse;

    private Bathroom (Integer area ,boolean bath, boolean shower, boolean mirror,
                      boolean washingMachine, boolean boiler, FloorType floor){
        this.area = area;
        this.bath = bath;
        this.shower = shower;
        this.mirror = mirror;
        this.washingMachine = washingMachine;
        this.boiler = boiler;
        this.floor = floor;
    }

    public static Bathroom of(Integer area, boolean bath, boolean shower, boolean mirror,
                              boolean washingMachine, boolean boiler, FloorType floor){
        return new Bathroom(area,bath,shower,mirror,washingMachine,boiler,floor);
    }

    public BathroomDTO toDTO(){
        return BathroomDTO.of(area,bath,shower,mirror,washingMachine,boiler,floor);
    }

    public static Bathroom fromDTO(BathroomDTO bathroomDTO){
        return Bathroom.of(bathroomDTO.getArea(), bathroomDTO.isBath(), bathroomDTO.isShower(),
                bathroomDTO.isMirror(), bathroomDTO.isWashingMachine(), bathroomDTO.isBoiler(), bathroomDTO.getFloor());
    }

}
