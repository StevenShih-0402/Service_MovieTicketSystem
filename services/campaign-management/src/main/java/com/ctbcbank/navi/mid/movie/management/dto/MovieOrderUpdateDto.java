package com.ctbcbank.navi.mid.movie.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieOrderUpdateDto extends BaseDto{
    private Long quantity;
    private Long totalPrice;
    private String movieName;
    private String userName;
}