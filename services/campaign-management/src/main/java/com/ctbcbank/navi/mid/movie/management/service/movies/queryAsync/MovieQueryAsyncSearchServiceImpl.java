package com.ctbcbank.navi.mid.movie.management.service.movies.queryAsync;

import com.ctbcbank.navi.mid.movie.management.dao.MovieOrdersDao;
import com.ctbcbank.navi.mid.movie.management.dao.MoviesDao;
import com.ctbcbank.navi.mid.movie.management.dao.UsersDao;
import com.ctbcbank.navi.mid.movie.management.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MovieQueryAsyncSearchServiceImpl implements MovieQueryAsyncSearchService {

    private final MovieOrdersDao movieOrdersDao;
    private final MoviesDao moviesDao;
    private final UsersDao usersDao;

    // 非同步查詢用戶資料 (Async 在需要回傳值時需使用 CompletableFuture 作為回傳值)
    @Async
    public CompletableFuture<List<MovieOrderDto>> asyncQueryMovieOrders(){
        System.out.println("查詢電影訂單資料……");
        return CompletableFuture.supplyAsync(movieOrdersDao::queryAllMovieOrders);
    }

    @Async
    public CompletableFuture<List<BigInteger>> asyncQueryMovieId(List<MovieOrderDto> orders){
        return CompletableFuture.supplyAsync(() ->
            orders.stream()
                .map(MovieOrderDto::getMovieId)
                .peek(movieId -> System.out.println("取得 Movie ID: " + movieId))
                .distinct()
                .toList()
        );
    }

    @Async
    public CompletableFuture<List<BigInteger>> asyncQueryUserId(List<MovieOrderDto> orders){
        return CompletableFuture.supplyAsync(() ->
            orders.stream()
                .map(MovieOrderDto::getUserId)
                .peek(userId -> System.out.println("取得 User ID: " + userId))
                .distinct()
                .toList()
        );
    }

    @Async
    public CompletableFuture<List<UserDto>> asyncQueryUsers(List<BigInteger> userIds) {
        return CompletableFuture.supplyAsync(() ->
                userIds.stream().map(userId -> {
                    QueryUserConditionDto queryUserConditionDto = new QueryUserConditionDto();
                    queryUserConditionDto.setId(userId);
                    System.out.println("取得 User 詳細資料: " + userId);
                    return usersDao.queryUsers(queryUserConditionDto).get(0);
                }).toList()
        );
    }

    // 非同步查詢電影資料
    @Async
    public CompletableFuture<List<MovieDto>> asyncQueryMovies(List<BigInteger> movieIds) {
        return CompletableFuture.supplyAsync(() ->
                movieIds.stream().map(movieId -> {
                    QueryMovieConditionDto queryMovieConditionDto = new QueryMovieConditionDto();
                    queryMovieConditionDto.setId(movieId);
                    System.out.println("取得 Movie 詳細資料: " + movieId);
                    return moviesDao.queryMovies(queryMovieConditionDto).get(0);
                }).toList()
        );
    }
}
