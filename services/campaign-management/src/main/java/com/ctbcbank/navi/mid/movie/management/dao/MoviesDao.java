package com.ctbcbank.navi.mid.movie.management.dao;

import com.ctbcbank.navi.mid.movie.management.dto.MovieDto;
import com.ctbcbank.navi.mid.movie.management.dto.MovieNameDto;
import com.ctbcbank.navi.mid.movie.management.dto.QueryMovieConditionDto;
import com.ctbcbank.navi.mid.movie.management.entity.MovieEntity;
import com.ctbcbank.navi.mid.movie.management.repository.MoviesRepository;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MoviesDao {

    private final MoviesRepository moviesRepository;
    private final JdbcTemplate jdbcTemplate;

//    public MoviesDto saveMovies(MoviesDto moviesDto){
//        MoviesDto reMoviesDto = new MoviesDto();
//        MoviesEntity moviesEntity = new MoviesEntity();
//
//        BeanUtils.copyProperties(moviesDto, moviesEntity);
//        moviesRepository.save(moviesEntity);
//        BeanUtils.copyProperties(moviesEntity, reMoviesDto);
//
//        return reMoviesDto;
//    }

    public Boolean existName(String name){
        return moviesRepository.existsByMovieName(name);
    }

    public Boolean existsById(BigInteger id){
        return moviesRepository.existsById(id);
    }

    public Boolean checkStatus(BigInteger id){
        return moviesRepository.checkStatus(id).equals(BigInteger.valueOf(1));
    }

    public Boolean existsByMovieNameExceptId(String name, BigInteger id){
        return moviesRepository.existsByMovieNameExceptId(name, id) == 1;
    }

    @Transactional
    public MovieDto saveMovies(MovieDto movieDto) {

//        String sql = """
//                INSERT INTO TB_MOVIES_BRUCE
//                ( CREATE_DTTM
//                , UPDATE_DTTM
//                , MOVIE_NAME
//                , PRICE
//                )
//                VALUES
//                ( SYSTIMESTAMP
//                , SYSTIMESTAMP
//                , ?
//                , ?
//                )
//                """;
//
//        jdbcTemplate.update(sql, ps -> {
//            ps.setObject(1, movieDto.getMovieName());
//            ps.setObject(2, movieDto.getPrice());
//        });
//
//        Optional<MovieEntity> newMovie = moviesRepository.findByMovieName(movieDto.getMovieName());


        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setMovieName(movieDto.getMovieName());
        movieEntity.setPrice(movieDto.getPrice());

        MovieEntity saveEntity = moviesRepository.save(movieEntity);

        MovieDto reMovieDto = new MovieDto();

        reMovieDto.setId(saveEntity.getId());
        reMovieDto.setMovieName(saveEntity.getMovieName());
        reMovieDto.setPrice(saveEntity.getPrice());

//        reMovieDto.setId(newMovie.get().getId());
//        reMovieDto.setMovieName(movieDto.getMovieName());
//        reMovieDto.setPrice(movieDto.getPrice());

        return reMovieDto;
    }

    public List<MovieDto> queryMovies(QueryMovieConditionDto moviesConditionDto){
        List<MovieDto> reDataList = new ArrayList<>();

        List<MovieEntity> moviesEntities = moviesRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(moviesConditionDto.getId())) {
                predicates.add(builder.equal(root.get("id"), moviesConditionDto.getId()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });

        CollectionUtils.emptyIfNull(moviesEntities).forEach(entity -> {
            MovieDto dto = new MovieDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    public List<MovieDto> queryMoviesByName(MovieNameDto movieNameDto){
        List<MovieDto> reDataList = new ArrayList<>();

        Pageable pageable = PageRequest.of(movieNameDto.getPageNo().intValue(), 2);
        Page<MovieEntity> moviePage = moviesRepository.findByMovieNameContaining(movieNameDto.getKeyWord(), movieNameDto.getPrice(), pageable);

        List<MovieEntity> moviesEntities = moviePage.getContent();
        CollectionUtils.emptyIfNull(moviesEntities).forEach(entity -> {
            MovieDto dto = new MovieDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    @Transactional
    public MovieDto updateMovies(MovieDto movieDto) {

        String sql = """
                UPDATE TB_MOVIES_BRUCE
                SET
                MOVIE_NAME = ?,
                PRICE = ?,
                UPDATE_DTTM = SYSTIMESTAMP
                WHERE ID = ?
                """;

        jdbcTemplate.update(sql, ps -> {
            ps.setString(1, movieDto.getMovieName());
            ps.setObject(2, movieDto.getPrice());
            ps.setObject(3, movieDto.getId());
        });

        MovieDto reMovieDto = new MovieDto();

        reMovieDto.setMovieName(movieDto.getMovieName());
        reMovieDto.setPrice(movieDto.getPrice());

        return reMovieDto;
    }

    @Transactional
    public void deleteMovie(QueryMovieConditionDto queryMovieConditionDto){
        moviesRepository.deleteById(queryMovieConditionDto.getId());
    }
}