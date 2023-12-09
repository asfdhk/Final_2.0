package com.example.final_pr.model;

import com.example.final_pr.dto.FlatDTO;
import com.example.final_pr.enums.Heating;


import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Flat {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer area;

    @Column(nullable = false)
    private Integer storey;

    @Enumerated(EnumType.STRING)
    private Heating heating;

    private boolean balcony;

    private boolean gas;


    @OneToMany(mappedBy = "flat",cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "flat",cascade = CascadeType.ALL)
    private List<Bathroom> bathrooms = new ArrayList<>();

    @OneToOne(mappedBy = "flat",cascade = CascadeType.ALL)
    private Kitchen kitchen;


    private Flat(String address, Integer price, Integer area, Integer storey,
                 Heating heating, boolean balcony, boolean gas){
        this.address = address;
        this.price = price;
        this.area = area;
        this.storey = storey;
        this.heating = heating;
        this.balcony = balcony;
        this.gas = gas;
    }

    public static Flat of(String address, Integer price, Integer area, Integer storey,
                          Heating heating, boolean balcony, boolean gas){
        return new Flat(address,price,area,storey,heating,balcony,gas);
    }

    public FlatDTO toDTO(){return FlatDTO.of(address,price,area,storey,heating,balcony,gas);}

    public static Flat fromDTO(FlatDTO flatDTO){
        return Flat.of(flatDTO.getAddress(), flatDTO.getPrice(), flatDTO.getArea(),
                flatDTO.getStorey(), flatDTO.getHeating(),flatDTO.isBalcony(), flatDTO.isGas());
    }

    public void addBathroom(Bathroom bathroom){
        bathroom.setFlat(this);
        bathrooms.add(bathroom);
    }

    public void addRoom(Room room){
        room.setFlat(this);
        rooms.add(room);
    }
}
