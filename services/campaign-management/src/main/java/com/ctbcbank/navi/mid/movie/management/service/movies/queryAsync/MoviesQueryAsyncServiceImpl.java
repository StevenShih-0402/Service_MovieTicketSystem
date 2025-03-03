package com.ctbcbank.navi.mid.movie.management.service.movies.queryAsync;

import com.ctbcbank.navi.mid.movie.management.dao.MovieOrdersDao;
import com.ctbcbank.navi.mid.movie.management.dto.*;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@Log4j2
@RequiredArgsConstructor
public class MoviesQueryAsyncServiceImpl implements MoviesQueryAsyncService{

    private final MovieQueryAsyncSearchService movieQueryAsyncSearchService;

    @Override
    public MovieQueryAsyncRsBo queryAsync(){

        // 1. 查詢所有電影訂單 (用 CompletableFuture 嵌套，方便後續的非同步操作)
//        List<MovieOrderDto> movieOrderDtoList = movieOrdersDao.queryAllMovieOrders();
//        if (CollectionUtils.isEmpty(movieOrderDtoList)) {
//            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "沒有電影訂單。");
//        }
        CompletableFuture<List<MovieOrderDto>> movieOrderDtoList = movieQueryAsyncSearchService.asyncQueryMovieOrders();

        // 2. 收集電影訂單裡面的用戶 id 以及電影 id (同步/非同步)
//        List<BigInteger> userIds = movieOrderDtoList.stream()
//                                                    .map(MovieOrderDto::getUserId)
//                                                    .distinct()
//                                                    .toList();
//
//        List<BigInteger> movieIds = movieOrderDtoList.stream()
//                                                     .map(MovieOrderDto::getMovieId)
//                                                     .distinct()
//                                                     .toList();

        CompletableFuture<List<BigInteger>> userIds = movieOrderDtoList.thenCompose(movieQueryAsyncSearchService::asyncQueryUserId);
        CompletableFuture<List<BigInteger>> movieIds = movieOrderDtoList.thenCompose(movieQueryAsyncSearchService::asyncQueryMovieId);

        // 3. 透過 CompletableFuture 非同步查詢用戶與電影資料
//        CompletableFuture<List<UserDto>> usersDetailFuture = movieQueryAsyncSearchService.asyncQueryUsers(userIds);
//        CompletableFuture<List<MovieDto>> moviesDetailFuture = movieQueryAsyncSearchService.asyncQueryMovies(movieIds);

        CompletableFuture<List<UserDto>> usersDetailFuture = userIds.thenCompose(movieQueryAsyncSearchService::asyncQueryUsers);
        CompletableFuture<List<MovieDto>> moviesDetailFuture = movieIds.thenCompose(movieQueryAsyncSearchService::asyncQueryMovies);

        // 4. 等待非同步結果完成
//        List<UserDto> usersDetail = usersDetailFuture.join();
//        List<MovieDto> moviesDetail = moviesDetailFuture.join();

        // 改用 allOf 方法，確保不會發生阻塞，即 moviesDetailFuture 先完成時，還是要等 usersDetailFuture 先執行 join() 後，才能執行 join() 的狀況。
        // 還有一個好處是傳統 join() 要用 try-catch 去拋出錯誤，但 allOf 可以直接套用 .exceptionally 方法拋錯，確保不影響其它執行
        CompletableFuture.allOf(movieOrderDtoList, usersDetailFuture, moviesDetailFuture)
                .exceptionally(ex -> {
                    log.error("某個查詢失敗: {}", ex.getMessage());
                    return null;
                })
                .join();


        // 取得已經完成查詢的 CompletableFuture 物件，還沒完成的話會回傳設定值(Collections.emptyList())
        List<MovieOrderDto> movieOrdersDetail = movieOrderDtoList.getNow(Collections.emptyList());
        List<UserDto> usersDetail = usersDetailFuture.getNow(Collections.emptyList());
        List<MovieDto> moviesDetail = moviesDetailFuture.getNow(Collections.emptyList());

        // 5. 轉換成 RsBo 後回傳
        MovieQueryAsyncRsBo movieQueryAsyncRsBo = new MovieQueryAsyncRsBo();
        movieQueryAsyncRsBo.setMovieOrderInfoListBo(getMovieOrderInfoList(movieOrdersDetail));
        movieQueryAsyncRsBo.setMovieInfoListBo(getMovieInfoList(moviesDetail));
        movieQueryAsyncRsBo.setUserInfoListBo(getUserInfoList(usersDetail));
        System.out.println(movieQueryAsyncRsBo);

        return movieQueryAsyncRsBo;
    }

    List<MovieQueryAsyncRsBo.MovieOrderInfoBo> getMovieOrderInfoList(List<MovieOrderDto> movieOrderDtoList){

        return movieOrderDtoList.stream().map(x ->{
            MovieQueryAsyncRsBo.MovieOrderInfoBo movieOrderInfoBo = new MovieQueryAsyncRsBo.MovieOrderInfoBo();
            movieOrderInfoBo.setMovieOrderId(x.getId());
            movieOrderInfoBo.setUserId(x.getUserId());
            movieOrderInfoBo.setMovieId(x.getMovieId());
            movieOrderInfoBo.setQuantity(x.getQuantity());
            movieOrderInfoBo.setTotalPrice(x.getTotalPrice());

            return movieOrderInfoBo;
        }).toList();
    }

    List<MovieQueryAsyncRsBo.MovieInfoBo> getMovieInfoList(List<MovieDto> movieDtoList){

        return movieDtoList.stream().map(x ->{
            MovieQueryAsyncRsBo.MovieInfoBo movieInfoBo = new MovieQueryAsyncRsBo.MovieInfoBo();
            movieInfoBo.setMovieId(x.getId());
            movieInfoBo.setMovieName(x.getMovieName());
            movieInfoBo.setPrice(x.getPrice());

            return movieInfoBo;
        }).toList();
    }

    List<MovieQueryAsyncRsBo.UserInfoBo> getUserInfoList(List<UserDto> userDtoList){

        return userDtoList.stream().map(x ->{
            MovieQueryAsyncRsBo.UserInfoBo userInfoBo = new MovieQueryAsyncRsBo.UserInfoBo();
            userInfoBo.setUserId(x.getId());
            userInfoBo.setUserName(x.getUserName());
            userInfoBo.setEmail(x.getEmail());

            return userInfoBo;
        }).toList();
    }
}