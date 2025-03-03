package com.ctbcbank.navi.mid.movie.management.dao;

import com.ctbcbank.navi.mid.movie.management.dto.*;
import com.ctbcbank.navi.mid.movie.management.entity.MovieEntity;
import com.ctbcbank.navi.mid.movie.management.entity.MovieOrderEntity;
import com.ctbcbank.navi.mid.movie.management.repository.MovieOrdersRepository;
import com.ctbcbank.navi.mid.movie.management.repository.MoviesRepository;
import com.ctbcbank.navi.mid.movie.management.repository.UsersRepository;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.NumberUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MovieOrdersDao {

    private final MovieOrdersRepository movieOrdersRepository;
    private final MoviesRepository moviesRepository;
    private final JdbcTemplate jdbcTemplate;

    public Boolean existsById(BigInteger id){
        return movieOrdersRepository.existsById(id);
    }

    public Boolean checkStatus(BigInteger id){
        return movieOrdersRepository.checkStatus(id).equals(BigInteger.valueOf(2));
    }

    @Transactional
    public MovieOrderDto saveMovieOrders(MovieOrderDto movieOrderDto) {

//        String sql = """
//                INSERT INTO TB_MOVIE_ORDERS_BRUCE
//                ( CREATE_DTTM
//                , UPDATE_DTTM
//                , USER_ID
//                , MOVIE_ID
//                , QUANTITY
//                , TOTAL_PRICE
//                )
//                VALUES
//                ( SYSTIMESTAMP
//                , SYSTIMESTAMP
//                , ?
//                , ?
//                , ?
//                , (SELECT PRICE * ? FROM TB_MOVIES_BRUCE WHERE ID = ?)
//                )
//                """;
//
//        jdbcTemplate.update(sql, ps -> {
//            ps.setObject(1, movieOrderDto.getUserId());
//            ps.setObject(2, movieOrderDto.getMovieId());
//            ps.setObject(3, movieOrderDto.getQuantity());
//            ps.setObject(4, movieOrderDto.getQuantity());
//            ps.setObject(5, movieOrderDto.getMovieId());
//        });
        BigInteger moviePrice = moviesRepository.findById(movieOrderDto.getMovieId()).get().getPrice();

        MovieOrderEntity movieOrderEntity = new MovieOrderEntity();
        movieOrderEntity.setMovieId(movieOrderDto.getMovieId());
        movieOrderEntity.setUserId(movieOrderDto.getUserId());
        movieOrderEntity.setQuantity(movieOrderDto.getQuantity());
        movieOrderEntity.setTotalPrice(moviePrice.multiply(movieOrderDto.getQuantity()));

        MovieOrderEntity saveEntity = movieOrdersRepository.save(movieOrderEntity);

        MovieOrderDto reMovieOrderDto = new MovieOrderDto();

        BeanUtils.copyProperties(saveEntity, reMovieOrderDto);
//        reMovieOrderDto.setId(saveEntity.getId());
//        reMovieOrderDto.setMovieId(saveEntity.getMovieId());
//        reMovieOrderDto.setUserId(saveEntity.getUserId());
//        reMovieOrderDto.setQuantity(saveEntity.getQuantity());
//        reMovieOrderDto.setTotalPrice(saveEntity.getTotalPrice());

        return reMovieOrderDto;
    }

    public List<MovieOrderNameDto> queryMovieOrders(QueryMovieOrderConditionDto movieOrdersConditionDto){

        List<Object[]> movieOrdersNameDtoList = movieOrdersRepository.findMovieAndUserName(movieOrdersConditionDto.getId().longValue());

        return movieOrdersNameDtoList.stream()
                .map(result -> new MovieOrderNameDto(
                        new BigInteger(result[0].toString()),
                        new BigInteger(result[1].toString()),
                        (String) result[2],
                        (String) result[3]
                ))
                .collect(Collectors.toList());
    }

    public List<MovieOrderDto> queryAllMovieOrders(){
        List<MovieOrderDto> reDataList = new ArrayList<>();

        List<MovieOrderEntity> movieOrderList = movieOrdersRepository.findAllMovieOrders();

        CollectionUtils.emptyIfNull(movieOrderList).forEach(entity -> {
            MovieOrderDto dto = new MovieOrderDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    @Transactional
    public MovieOrderNameDto updateMovieOrders(MovieOrderNameDto movieOrderNameDto, QueryMovieOrderUpdateConditionDto queryMovieOrderConditionDto) {

        String sql = """
                UPDATE TB_MOVIE_ORDERS_BRUCE
                SET
                QUANTITY = ?,
                TOTAL_PRICE = (SELECT PRICE * ? FROM TB_MOVIES_BRUCE WHERE ID = ?),
                UPDATE_DTTM = SYSTIMESTAMP
                WHERE ID = ?
                """;

        jdbcTemplate.update(sql, ps -> {
            ps.setObject(1, queryMovieOrderConditionDto.getQuantity());
            ps.setObject(2, queryMovieOrderConditionDto.getQuantity());
            ps.setObject(3, moviesRepository.findByMovieName(movieOrderNameDto.getMovieName()).get().getId());
            ps.setObject(4, queryMovieOrderConditionDto.getId());
        });

        BigInteger moviePrice = moviesRepository.findByMovieName(movieOrderNameDto.getMovieName()).get().getPrice();
        BigInteger movieQuantity = movieOrdersRepository.findById(queryMovieOrderConditionDto.getId()).get().getQuantity();

        MovieOrderNameDto reMovieOrderNameDto = new MovieOrderNameDto();

        reMovieOrderNameDto.setQuantity(queryMovieOrderConditionDto.getQuantity());
        reMovieOrderNameDto.setTotalPrice(moviePrice.multiply(movieQuantity));

        return reMovieOrderNameDto;
    }

    @Transactional
    public void deleteMovieOrder(QueryMovieOrderConditionDto queryMovieOrderConditionDto){
        movieOrdersRepository.deleteById(queryMovieOrderConditionDto.getId());
    }
}