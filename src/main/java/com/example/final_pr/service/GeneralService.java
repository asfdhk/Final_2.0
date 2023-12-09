package com.example.final_pr.service;

import com.example.final_pr.dto.*;
import com.example.final_pr.enums.UserRole;
import com.example.final_pr.model.*;
import com.example.final_pr.repo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.final_pr.enums.FloorType.PARQUET;
import static com.example.final_pr.enums.Heating.ELECTRIC;

@Service
public class GeneralService {



    private final BathroomRepository bathroomRepository;

    private final CustomUsersRepository customUsersRepository;

    private final FlatRepository flatRepository;

    private final KitchenRepository kitchenRepository;

    private final PrivateHouseRepository privateHouseRepository;

    private final RoomRepository roomRepository;

    public GeneralService (BathroomRepository bathroomRepository, CustomUsersRepository customUsersRepository,
                           FlatRepository flatRepository, KitchenRepository kitchenRepository,
                           PrivateHouseRepository privateHouseRepository, RoomRepository roomRepository){
        this.bathroomRepository = bathroomRepository;
        this.customUsersRepository = customUsersRepository;
        this.flatRepository = flatRepository;
        this.kitchenRepository = kitchenRepository;
        this.privateHouseRepository = privateHouseRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public void addFlat1(FlatDTO flatDTO,KitchenDTO kitchenDTO,
                                List<BathroomDTO> bathroomDTOList, List<RoomDTO> roomDTOList){
        Flat flat = Flat.fromDTO(flatDTO);

        Kitchen kitchen = Kitchen.fromDTO(kitchenDTO);
        flat.setKitchen(kitchen);
        kitchen.setFlat(flat);
        kitchenRepository.save(kitchen);

        bathroomDTOList.forEach((x) ->{
            Bathroom bathroom = Bathroom.fromDTO(x);
            flat.addBathroom(bathroom);
            bathroomRepository.save(bathroom);
        });

        roomDTOList.forEach((x)->{
            Room room = Room.fromDTO(x);
            flat.addRoom(room);
            roomRepository.save(room);
        });

        flatRepository.save(flat);
    }

    @Transactional
    public void addPrivateHouse(PrivateHouseDTO privateHouseDTO,KitchenDTO kitchenDTO,
                                List<BathroomDTO> bathroomDTOList, List<RoomDTO> roomDTOList){
        PrivateHouse privateHouse = PrivateHouse.fromDTO(privateHouseDTO);

        Kitchen kitchen = Kitchen.fromDTO(kitchenDTO);
        privateHouse.setKitchen(kitchen);
        kitchen.setPrivateHouse(privateHouse);
        kitchenRepository.save(kitchen);

        bathroomDTOList.forEach((x) ->{
            Bathroom bathroom = Bathroom.fromDTO(x);
            privateHouse.addBathroom(bathroom);
            bathroomRepository.save(bathroom);
        });

        roomDTOList.forEach((x)->{
            Room room = Room.fromDTO(x);
            privateHouse.addRoom(room);
            roomRepository.save(room);
        });

        privateHouseRepository.save(privateHouse);
    }

    @Transactional(readOnly = true)
    public Page<Flat> flats(Pageable pageable){
        Page<Flat> result = flatRepository.findAll(pageable);
        return result;
    }

    @Transactional
    public void addFlat(FlatDTO flatDTO,KitchenDTO kitchenDTO){

        Flat flat = Flat.fromDTO(flatDTO);
        Kitchen kitchen = Kitchen.fromDTO(kitchenDTO);
        flat.setKitchen(kitchen);
        kitchen.setFlat(flat);
        kitchenRepository.save(kitchen);
        flatRepository.save(flat);
    }

    @Transactional
    public void addBathroomFlat(Flat flat,BathroomDTO bathroomDTO){
        Bathroom bathroom = Bathroom.fromDTO(bathroomDTO);
        flat.addBathroom(bathroom);
        bathroomRepository.save(bathroom);
    }

    @Transactional
    public void addRoomFlat(Flat flat,RoomDTO roomDTO){
        Room room = Room.fromDTO(roomDTO);
        flat.addRoom(room);
        roomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public Flat flatFind(Long id){
        return flatRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public PrivateHouse houseFind(Long id){
        return privateHouseRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public Page<PrivateHouse> privateHouses (Pageable pageable){
        Page<PrivateHouse> result = privateHouseRepository.findAll(pageable);
        return result;
    }

    @Transactional
    public void addHouse(PrivateHouseDTO privateHouseDTO,KitchenDTO kitchenDTO){
        PrivateHouse privateHouse = PrivateHouse.fromDTO(privateHouseDTO);
        Kitchen kitchen = Kitchen.fromDTO(kitchenDTO);
        privateHouse.setKitchen(kitchen);
        kitchen.setPrivateHouse(privateHouse);
        kitchenRepository.save(kitchen);
        privateHouseRepository.save(privateHouse);
    }

    @Transactional
    public void addBathroomHouse(PrivateHouse privateHouse,BathroomDTO bathroomDTO){
        Bathroom bathroom = Bathroom.fromDTO(bathroomDTO);
        privateHouse.addBathroom(bathroom);
        bathroomRepository.save(bathroom);
    }

    @Transactional
    public void addRoomHouse(PrivateHouse privateHouse,RoomDTO roomDTO){
        Room room = Room.fromDTO(roomDTO);
        privateHouse.addRoom(room);
        roomRepository.save(room);
    }

    @Transactional
    public boolean addUser(String login, String passHash,
                           UserRole role, String phone) {
        if (customUsersRepository.existsByLogin(login))
            return false;

        CustomUser user = new CustomUser(passHash,login,role,phone);
        customUsersRepository.save(user);

        return true;
    }

    @Transactional(readOnly = true)
    public CustomUser findByLogin(String login) {
        return customUsersRepository.findByLogin(login);
    }

    @Transactional(readOnly = true)
    public CustomUser findById(Long id){
        return customUsersRepository.findById(id).get();
    }

}
