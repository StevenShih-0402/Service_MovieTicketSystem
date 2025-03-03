package com.ctbcbank.navi.mid.movie.management.service.movies.queryAsync;

import com.ctbcbank.navi.mid.movie.management.dto.MovieDto;
import com.ctbcbank.navi.mid.movie.management.dto.MovieOrderDto;
import com.ctbcbank.navi.mid.movie.management.dto.UserDto;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MovieQueryAsyncSearchService {
    CompletableFuture<List<MovieOrderDto>> asyncQueryMovieOrders();
    CompletableFuture<List<BigInteger>> asyncQueryMovieId(List<MovieOrderDto> orders);
    CompletableFuture<List<BigInteger>> asyncQueryUserId(List<MovieOrderDto> orders);
    CompletableFuture<List<UserDto>> asyncQueryUsers(List<BigInteger> userIds);
    CompletableFuture<List<MovieDto>> asyncQueryMovies(List<BigInteger> movieIds);
}
