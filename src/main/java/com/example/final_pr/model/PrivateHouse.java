package com.example.final_pr.model;

import com.example.final_pr.dto.PrivateHouseDTO;
import com.example.final_pr.enums.Heating;


import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class PrivateHouse {

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
    private Integer amountStoreys;

    @Enumerated(EnumType.STRING)
    private Heating heating;

    private boolean garage;

    private boolean gas;

    @OneToMany(mappedBy = "privateHouse",cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "privateHouse",cascade = CascadeType.ALL)
    private List<Bathroom> bathrooms = new ArrayList<>();

    @OneToOne(mappedBy = "privateHouse",cascade = CascadeType.ALL)
    private Kitchen kitchen;

    private PrivateHouse(String address,Integer price,Integer area,Integer amountStoreys,
                         Heating heating, boolean garage,boolean gas){
        this.address = address;
        this.price = price;
        this.area = area;
        this.amountStoreys = amountStoreys;
        this.heating = heating;
        this.garage = garage;
        this.gas = gas;
    }

    public static PrivateHouse of(String address,Integer price,Integer area,Integer amountStoreys,
                                  Heating heating, boolean garage,boolean gas){
        return new PrivateHouse(address,price,area,amountStoreys,heating,garage,gas);
    }

    public PrivateHouseDTO toDTO(){return PrivateHouseDTO.of(address,price,area,amountStoreys,heating,garage,gas);}

    public static PrivateHouse fromDTO(PrivateHouseDTO privateHouseDTO){
        return PrivateHouse.of(privateHouseDTO.getAddress(), privateHouseDTO.getPrice(), privateHouseDTO.getArea(),
                privateHouseDTO.getAmountStoreys(),privateHouseDTO.getHeating(), privateHouseDTO.isGarage(),
                privateHouseDTO.isGas());
    }

    public void addBathroom(Bathroom bathroom){
        bathroom.setPrivateHouse(this);
        bathrooms.add(bathroom);
    }

    public void addRoom(Room room){
        room.setPrivateHouse(this);
        rooms.add(room);
    }
}