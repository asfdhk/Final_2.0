package com.example.final_pr.controllers;

import com.example.final_pr.dto.*;
import com.example.final_pr.service.GeneralService;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.example.final_pr.enums.FloorType.LAMINATE;
import static com.example.final_pr.enums.FloorType.TILES;
import static com.example.final_pr.enums.Heating.GAS;
import static com.example.final_pr.enums.Heating.WITH_FIREWOOD;
import static com.example.final_pr.enums.RoomType.HALL;

@Controller
public class StartController {

    private final GeneralService generalService;

    public StartController (GeneralService generalService){
        this.generalService = generalService;
    }

    @PostConstruct
    public void startFlat(){
        FlatDTO flatDTO = new FlatDTO();
        flatDTO.setAddress("Lvivska");

        flatDTO.setPrice(45000);

        flatDTO.setArea(90);

        flatDTO.setStorey(2);

        flatDTO.setHeating(GAS);
        flatDTO.setGas(true);
        flatDTO.setBalcony(false);

        KitchenDTO kitchenDTO = new KitchenDTO();
        kitchenDTO.setDishWash(true);
        kitchenDTO.setArea(30);
        kitchenDTO.setMicrowaveOven(true);
        kitchenDTO.setOven(false);
        kitchenDTO.setRefrigerator(true);
        kitchenDTO.setCooker(true);
        kitchenDTO.setFloor(TILES);

        List<BathroomDTO> bathroomDTOList = new ArrayList<>();
        BathroomDTO bathroomDTO = new BathroomDTO();
        bathroomDTO.setBoiler(true);
        bathroomDTO.setArea(20);
        bathroomDTO.setFloor(TILES);
        bathroomDTO.setMirror(true);
        bathroomDTO.setShower(true);
        bathroomDTO.setWashingMachine(true);
        bathroomDTO.setBath(true);

        bathroomDTOList.add(bathroomDTO);

        List<RoomDTO> roomDTOList = new ArrayList<>();
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setType(HALL);
        roomDTO.setFloor(LAMINATE);
        roomDTO.setTv(true);
        roomDTO.setArea(40);
        roomDTO.setBoard(true);
        roomDTOList.add(roomDTO);

        generalService.addFlat1(flatDTO,kitchenDTO,bathroomDTOList,roomDTOList);
    }

    @PostConstruct
    public void startHouse(){
        PrivateHouseDTO privateHouseDTO = new PrivateHouseDTO();
        privateHouseDTO.setAddress("Franka");
        privateHouseDTO.setPrice(100000);
        privateHouseDTO.setArea(200);
        privateHouseDTO.setAmountStoreys(2);
        privateHouseDTO.setHeating(WITH_FIREWOOD);
        privateHouseDTO.setGas(false);
        privateHouseDTO.setGarage(true);

        KitchenDTO kitchenDTO = new KitchenDTO();
        kitchenDTO.setDishWash(true);
        kitchenDTO.setArea(40);
        kitchenDTO.setMicrowaveOven(true);
        kitchenDTO.setOven(false);
        kitchenDTO.setRefrigerator(true);
        kitchenDTO.setCooker(true);
        kitchenDTO.setFloor(TILES);

        List<BathroomDTO> bathroomDTOList = new ArrayList<>();
        BathroomDTO bathroomDTO = new BathroomDTO();
        bathroomDTO.setBoiler(true);
        bathroomDTO.setArea(20);
        bathroomDTO.setFloor(TILES);
        bathroomDTO.setMirror(true);
        bathroomDTO.setShower(true);
        bathroomDTO.setWashingMachine(true);
        bathroomDTO.setBath(true);

        bathroomDTOList.add(bathroomDTO);

        List<RoomDTO> roomDTOList = new ArrayList<>();
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setType(HALL);
        roomDTO.setFloor(LAMINATE);
        roomDTO.setTv(true);
        roomDTO.setArea(100);
        roomDTO.setBoard(true);
        roomDTOList.add(roomDTO);

        generalService.addPrivateHouse(privateHouseDTO,kitchenDTO,bathroomDTOList,roomDTOList);
    }
}
