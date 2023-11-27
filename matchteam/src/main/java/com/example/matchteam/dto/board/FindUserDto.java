package com.example.matchteam.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FindUserDto {
    private int writeCount;
    private int commentCount;
    private int projectCount;
}
