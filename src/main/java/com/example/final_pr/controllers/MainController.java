package com.example.final_pr.controllers;

import com.example.final_pr.dto.*;
import com.example.final_pr.enums.FloorType;
import com.example.final_pr.enums.Heating;
import com.example.final_pr.enums.RoomType;
import com.example.final_pr.enums.UserRole;
import com.example.final_pr.model.*;
import com.example.final_pr.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static com.example.final_pr.enums.FloorType.*;
import static com.example.final_pr.enums.Heating.*;
import static com.example.final_pr.enums.RoomType.BEDROOM;
import static com.example.final_pr.enums.RoomType.HALL;

@Controller
public class MainController {

    private static final int PAGE_SIZE = 5;

    private final PasswordEncoder passwordEncoder;
    private final GeneralService generalService;

    public MainController(GeneralService generalService,
                          PasswordEncoder passwordEncoder){
        this.generalService = generalService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/")
    public String main(@RequestParam(required = false, defaultValue = "0") Integer page,
                       @RequestParam(required = false, defaultValue = "0") Integer pageH,
                       Model model){

        User user = getCurrentUser();
        String login = user.getUsername();


        model.addAttribute("user",generalService.findByLogin(login));

        Page<Flat> list = generalService.flats(PageRequest.of
                (page,PAGE_SIZE, Sort.Direction.DESC,"id"));
        model.addAttribute("list",list);

        Page<PrivateHouse> houses = generalService.privateHouses(PageRequest.of
                (pageH,PAGE_SIZE, Sort.Direction.DESC,"id"));
        model.addAttribute("houses",houses);
        return "index";
    }

    @GetMapping("/create_flat")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createFlat(){
        return "flat";
    }

    @PostMapping(value = "/create_flat/data")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addFlat(@RequestParam String address,
                          @RequestParam String price,
                          @RequestParam String area,
                          @RequestParam String storey,
                          @RequestParam String heating,
                          @RequestParam String gas,
                          @RequestParam String balcony,
                          @RequestParam String areaK,
                          @RequestParam String dishWash,
                          @RequestParam String microwaveOven,
                          @RequestParam String oven,
                          @RequestParam String refrigerator,
                          @RequestParam String cooker,
                          @RequestParam String floor
                          ){

        if (!hasDigits(price)|| !hasDigits(area) || !hasDigits(storey) || !hasDigits(areaK)){
            return "error";
        }
        FlatDTO flatDTO = new FlatDTO();
        flatDTO.setAddress(address);
        int pr = Integer.parseInt(price);
        flatDTO.setPrice(pr);
        int ar = Integer.parseInt(area);
        flatDTO.setArea(ar);
        int st = Integer.parseInt(storey);
        flatDTO.setStorey(st);
        flatDTO.setHeating(heating(heating));
        flatDTO.setGas(setBoolean(gas));
        flatDTO.setBalcony(setBoolean(balcony));

        KitchenDTO kitchenDTO = new KitchenDTO();
        int ar1 = Integer.parseInt(areaK);
        kitchenDTO.setArea(ar1);
        kitchenDTO.setDishWash(setBoolean(dishWash));
        kitchenDTO.setMicrowaveOven(setBoolean(microwaveOven));
        kitchenDTO.setOven(setBoolean(oven));
        kitchenDTO.setRefrigerator(setBoolean(refrigerator));
        kitchenDTO.setCooker(setBoolean(cooker));
        kitchenDTO.setFloor(floorType(floor));

        generalService.addFlat(flatDTO,kitchenDTO);

        return "redirect:/";
    }



    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }


    @GetMapping("/kitchenFlat")
    public String kitchenData(@RequestParam(name = "id") Long id, Model model){
        Flat flat = generalService.flatFind(id);
        KitchenDTO kitchenDTO = flat.getKitchen().toDTO();
        model.addAttribute("kitchen",kitchenDTO);
        return "kitchen";
    }

    @GetMapping ("/bathroomFlat")
    public String bathroom(@RequestParam(name = "id") Long id,Model model){
        Flat flat = generalService.flatFind(id);
        List<BathroomDTO> bathroomDTOList = new ArrayList<>();
        flat.getBathrooms().forEach(x-> bathroomDTOList.add(x.toDTO()));
        model.addAttribute("bathroom",bathroomDTOList);
        model.addAttribute("flatId",flat.getId());
        return "bathroom";
    }

    @GetMapping("/add_bath")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addBath(@RequestParam Long flatId,Model model){

        Flat flat = generalService.flatFind(flatId);
        model.addAttribute("a",flat);
        return "addBath";
    }

    @PostMapping("/bathroomFlat/add_bath/bathroom_date")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String bathroomData(@RequestParam Long flatId,
                              @RequestParam String area,
                              @RequestParam String bath,
                              @RequestParam String shower,
                              @RequestParam String mirror,
                              @RequestParam String washingMachine,
                              @RequestParam String boiler,
                              @RequestParam String floor){

        if (!hasDigits(area)){
            return "error";
        }else {

            Flat flat = generalService.flatFind(flatId);
            BathroomDTO bathroomDTO = new BathroomDTO();
            int a = Integer.parseInt(area);
            bathroomDTO.setArea(a);
            bathroomDTO.setBath(setBoolean(bath));
            bathroomDTO.setShower(setBoolean(shower));
            bathroomDTO.setMirror(setBoolean(mirror));
            bathroomDTO.setWashingMachine(setBoolean(washingMachine));
            bathroomDTO.setBoiler(setBoolean(boiler));
            bathroomDTO.setFloor(floorType(floor));

            generalService.addBathroomFlat(flat, bathroomDTO);
            return "redirect:/";
        }
    }


    @GetMapping("/roomFlat")
    public String room(@RequestParam(name = "id") Long id,Model model){
        Flat flat = generalService.flatFind(id);
        List<RoomDTO> result = new ArrayList<>();
        flat.getRooms().forEach(x->result.add(x.toDTO()));
        model.addAttribute("rooms",result);
        model.addAttribute("flatId",flat.getId());
        return "room";
    }

    @GetMapping("/add_room")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addRoomFlat(@RequestParam Long flatId,Model model){
        Flat flat = generalService.flatFind(flatId);
        model.addAttribute("a",flat);
        return "addRoom";
    }

    @PostMapping("/roomFlat/add_room/room_date")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addRoom(@RequestParam Long flatId,
                          @RequestParam String area,
                          @RequestParam String tv,
                          @RequestParam String board,
                          @RequestParam String floor,
                          @RequestParam String type){
        if (!hasDigits(area)){
            return "error";
        }
        Flat flat = generalService.flatFind(flatId);
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setArea(Integer.parseInt(area));
        roomDTO.setTv(setBoolean(tv));
        roomDTO.setBoard(setBoolean(board));
        roomDTO.setFloor(floorType(floor));
        roomDTO.setType(roomType(type));
        generalService.addRoomFlat(flat,roomDTO);
        return "redirect:/";
    }


    @GetMapping("/create_house")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createHouse(){
        return "house";
    }


    @PostMapping("/create_house/data")
    public String addHouse(@RequestParam String address,
                           @RequestParam String price,
                           @RequestParam String area,
                           @RequestParam String amountStorey,
                           @RequestParam String heating,
                           @RequestParam String gas,
                           @RequestParam String garage,
                           @RequestParam String areaK,
                           @RequestParam String dishWash,
                           @RequestParam String microwaveOven,
                           @RequestParam String oven,
                           @RequestParam String refrigerator,
                           @RequestParam String cooker,
                           @RequestParam String floor){
        if (!hasDigits(price)|| !hasDigits(area) || !hasDigits(amountStorey) || !hasDigits(areaK)){
            return "error";
        }

        PrivateHouseDTO privateHouseDTO = new PrivateHouseDTO();
        privateHouseDTO.setAddress(address);
        privateHouseDTO.setPrice(Integer.parseInt(price));
        privateHouseDTO.setArea(Integer.parseInt(area));
        privateHouseDTO.setAmountStoreys(Integer.parseInt(amountStorey));
        privateHouseDTO.setHeating(heating(heating));
        privateHouseDTO.setGas(setBoolean(gas));
        privateHouseDTO.setGarage(setBoolean(garage));

        KitchenDTO kitchenDTO = new KitchenDTO();
        int ar1 = Integer.parseInt(areaK);
        kitchenDTO.setArea(ar1);
        kitchenDTO.setDishWash(setBoolean(dishWash));
        kitchenDTO.setMicrowaveOven(setBoolean(microwaveOven));
        kitchenDTO.setOven(setBoolean(oven));
        kitchenDTO.setRefrigerator(setBoolean(refrigerator));
        kitchenDTO.setCooker(setBoolean(cooker));
        kitchenDTO.setFloor(floorType(floor));

        generalService.addHouse(privateHouseDTO,kitchenDTO);
        return "redirect:/";
    }

    @GetMapping("/kitchenHouse")
    public String kitchenDate(@RequestParam(name = "id") Long id, Model model){
        PrivateHouse house = generalService.houseFind(id);
        KitchenDTO kitchenDTO = house.getKitchen().toDTO();
        model.addAttribute("kitchen",kitchenDTO);
        return "kitchen";
    }

    @GetMapping ("/bathroomHouse")
    public String bathroomHouse(@RequestParam(name = "id") Long id,Model model){
        PrivateHouse house = generalService.houseFind(id);
        List<BathroomDTO> bathroomDTOList = new ArrayList<>();
        house.getBathrooms().forEach(x-> bathroomDTOList.add(x.toDTO()));
        model.addAttribute("bathroom",bathroomDTOList);
        model.addAttribute("houseId",house.getId());
        return "bathHouse";
    }

    @GetMapping("/add_bath_h")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addBathHouse(@RequestParam Long houseId,Model model){

        PrivateHouse privateHouse = generalService.houseFind(houseId);
        model.addAttribute("a",privateHouse);
        return "addBathHouse";
    }

    @PostMapping("/bathroomHouse/add_bath_h/bathroom_date")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String bathroomDataHouse(@RequestParam Long houseId,
                               @RequestParam String area,
                               @RequestParam String bath,
                               @RequestParam String shower,
                               @RequestParam String mirror,
                               @RequestParam String washingMachine,
                               @RequestParam String boiler,
                               @RequestParam String floor){
        if (!hasDigits(area)){
            return "error";
        }
        PrivateHouse privateHouse = generalService.houseFind(houseId);
        BathroomDTO bathroomDTO = new BathroomDTO();
        int a = Integer.parseInt(area);
        bathroomDTO.setArea(a);
        bathroomDTO.setBath(setBoolean(bath));
        bathroomDTO.setShower(setBoolean(shower));
        bathroomDTO.setMirror(setBoolean(mirror));
        bathroomDTO.setWashingMachine(setBoolean(washingMachine));
        bathroomDTO.setBoiler(setBoolean(boiler));
        bathroomDTO.setFloor(floorType(floor));

        generalService.addBathroomHouse(privateHouse,bathroomDTO);
        return "redirect:/";
    }

    @GetMapping("/roomHouse")
    public String roomHouse(@RequestParam(name = "id") Long id,Model model){
        PrivateHouse house = generalService.houseFind(id);
        List<RoomDTO> result = new ArrayList<>();
        house.getRooms().forEach(x->result.add(x.toDTO()));
        model.addAttribute("rooms",result);
        model.addAttribute("houseId",house.getId());
        return "roomHouse";
    }

    @GetMapping("/add_room_h")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addRoomHouse(@RequestParam Long houseId,Model model){
        PrivateHouse house = generalService.houseFind(houseId);
        model.addAttribute("a",house);
        return "addRoomHouse";
    }

    @PostMapping("/roomHouse/add_room_h/room_data")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addRoomHouse(@RequestParam Long houseId,
                          @RequestParam String area,
                          @RequestParam String tv,
                          @RequestParam String board,
                          @RequestParam String floor,
                          @RequestParam String type){
        if (!hasDigits(area)){
            return "error";
        }

        PrivateHouse house = generalService.houseFind(houseId);
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setArea(Integer.parseInt(area));
        roomDTO.setTv(setBoolean(tv));
        roomDTO.setBoard(setBoolean(board));
        roomDTO.setFloor(floorType(floor));
        roomDTO.setType(roomType(type));
        generalService.addRoomHouse(house,roomDTO);
        return "redirect:/";
    }

    @GetMapping("/unauthorized")
    public String unauthorized(Model model) {
        User user = getCurrentUser();
        model.addAttribute("login", user.getUsername());
        return "unauthorized";
    }

    @PostMapping(value = "/newuser")
    public String update(@RequestParam String login,
                         @RequestParam String password,
                         @RequestParam(required = false) String phone,
                         Model model) {
        String passHash = passwordEncoder.encode(password);

        if ( ! generalService.addUser(login,passHash, UserRole.USER,phone)) {
            model.addAttribute("exists", true);
            model.addAttribute("login", login);
            System.out.println(phone);
            return "register";
        }

        return "redirect:/logout";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }



    private boolean hasDigits(String inputString) {
        for (char c : inputString.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
    private User getCurrentUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private boolean setBoolean(String a){
        if (a.equals("true")){
            return true;
        }else {
            return false;
        }
    }

    private Heating heating(String a){
        if (a.equals("WITH_FIREWOOD")){
            return WITH_FIREWOOD;
        }else if (a.equals("ELECTRIC")){
            return ELECTRIC;
        }else {
            return GAS;
        }
    }

    private FloorType floorType(String a){
        if (a.equals("TILES")){
            return TILES;
        }else if(a.equals("LAMINATE")){
            return LAMINATE;
        }else {
            return PARQUET;
        }
    }

    private RoomType roomType(String a){
        if(a.equals("HALL")){
            return HALL;
        }else {
            return BEDROOM;
        }
    }
}
