package com.example.final_pr.dto;


import com.example.final_pr.enums.FloorType;
import com.example.final_pr.enums.RoomType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class RoomDTO {

    private Integer area;

    private boolean tv;

    private boolean board;

    @Enumerated(EnumType.STRING)
    private FloorType floor;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    private RoomDTO(Integer area, boolean tv, boolean board,FloorType floor, RoomType type){
        this.area = area;
        this.tv = tv;
        this.board = board;
        this.floor = floor;
        this.type = type;
    }

    public static RoomDTO of(Integer area, boolean tv, boolean board,FloorType floor, RoomType type){
        return new RoomDTO(area, tv, board, floor, type);
    }
}
