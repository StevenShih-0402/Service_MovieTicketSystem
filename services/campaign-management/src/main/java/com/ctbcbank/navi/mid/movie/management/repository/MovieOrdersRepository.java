package com.ctbcbank.navi.mid.movie.management.repository;

import com.ctbcbank.navi.mid.movie.management.dto.MovieOrderNameDto;
import com.ctbcbank.navi.mid.movie.management.dto.MovieOrderUpdateDto;
import com.ctbcbank.navi.mid.movie.management.entity.MovieOrderEntity;
import com.ibm.cbmp.fabric.foundation.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface MovieOrdersRepository extends BaseRepository<MovieOrderEntity, BigInteger> {

    @Query(
            value = "SELECT STATUS FROM TB_MOVIE_ORDERS_BRUCE WHERE ID = :id",
            nativeQuery = true
    )
    BigInteger checkStatus(BigInteger id);

    @Query(
            value = "UPDATE TB_MOVIE_ORDERS_BRUCE SET STATUS = 2 WHERE ID = :id",
            nativeQuery = true
    )
    @Modifying
    void deleteById(BigInteger id);

    @Query(
            value = "SELECT mo.QUANTITY AS quantity, mo.TOTAL_PRICE AS totalPrice, m.MOVIE_NAME AS movieName, u.USER_NAME AS userName " +
                    "FROM TB_MOVIE_ORDERS_BRUCE mo " +
                    "JOIN TB_USERS_BRUCE u ON mo.USER_ID = u.ID " +
                    "JOIN TB_MOVIES_BRUCE m ON mo.MOVIE_ID = m.ID " +
                    "WHERE mo.ID = :orderId ",
            nativeQuery = true
    )
    List<Object[]> findMovieAndUserName(Long orderId);

    @Query(
            value = "SELECT * FROM TB_MOVIE_ORDERS_BRUCE WHERE STATUS != 2 ",
            nativeQuery = true
    )
    List<MovieOrderEntity> findAllMovieOrders();
}
