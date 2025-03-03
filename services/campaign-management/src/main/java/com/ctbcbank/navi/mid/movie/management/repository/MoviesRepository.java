package com.ctbcbank.navi.mid.movie.management.repository;

import com.ctbcbank.navi.mid.movie.management.entity.MovieEntity;
import com.ibm.cbmp.fabric.foundation.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface MoviesRepository extends BaseRepository<MovieEntity, BigInteger> {
    @Query(
            value = "SELECT STATUS FROM TB_MOVIES_BRUCE WHERE ID = :id",
            nativeQuery = true
    )
    BigInteger checkStatus(BigInteger id);

    Optional<MovieEntity> findById(BigInteger id);
    Optional<MovieEntity> findByMovieName(String name);

    @Query(
            value = "SELECT * FROM TB_MOVIES_BRUCE WHERE MOVIE_NAME LIKE '%' || ? || '%' AND PRICE = ? AND STATUS = 0 ",
            nativeQuery = true
    )
    Page<MovieEntity> findByMovieNameContaining(String name, BigInteger price, Pageable pageable);

    @Query(
            value = "SELECT " +
                    "CASE WHEN COUNT(*) > 0 " +
                    "THEN 1 " +
                    "ELSE 0 " +
                    "END " +
                    "FROM TB_MOVIES_BRUCE " +
                    "WHERE MOVIE_NAME = :movieName AND ID != :id",
            nativeQuery = true
    )
    Integer existsByMovieNameExceptId(String movieName, BigInteger id);

    Boolean existsByMovieName(String movieName);

    @Query(
            value = "UPDATE TB_MOVIES_BRUCE SET STATUS = 1 WHERE ID = :id",
            nativeQuery = true
    )
    @Modifying
    void deleteById(BigInteger id);
}