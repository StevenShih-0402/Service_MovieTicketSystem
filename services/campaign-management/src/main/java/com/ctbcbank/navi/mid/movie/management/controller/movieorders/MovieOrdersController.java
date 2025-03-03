package com.ctbcbank.navi.mid.movie.management.controller.movieorders;

import com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload.*;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.create.MovieOrderCreateRqBo;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.create.MovieOrderCreateRsBo;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.create.MovieOrdersCreateConverter;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.create.MovieOrdersCreateService;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.delete.MovieOrderDeleteRqBo;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.delete.MovieOrdersDeleteConverter;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.delete.MovieOrdersDeleteService;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.query.MovieOrderQueryRqBo;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.query.MovieOrderQueryRsBo;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.query.MovieOrdersQueryService;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.query.MovieOrdersQueryConverter;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.update.MovieOrderUpdateRqBo;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.update.MovieOrderUpdateRsBo;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.update.MovieOrdersUpdateConverter;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.update.MovieOrdersUpdateService;
import com.ibm.cbmp.fabric.web.api.annotation.DeleteApiMapping;
import com.ibm.cbmp.fabric.web.api.annotation.PostApiMapping;
import com.ibm.cbmp.fabric.web.api.annotation.PutApiMapping;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "v1/movie-orders/", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieOrdersController {

    private final MovieOrdersCreateService movieOrdersCreateService;
    private final MovieOrdersQueryService movieOrdersQueryService;
    private final MovieOrdersUpdateService movieOrdersUpdateService;
    private final MovieOrdersDeleteService movieOrdersDeleteService;

    @Operation(summary = "新增電影訂單", description = "新增電影訂單")
    @PostApiMapping(value = "create")
    MovieOrderCreateRs create(@Valid @RequestBody MovieOrderCreateRq movieOrderCreateRq) {
        MovieOrderCreateRqBo movieOrderCreateRqBo = MovieOrdersCreateConverter.parseToBo(movieOrderCreateRq);
        MovieOrderCreateRsBo movieOrderCreateRsBo = movieOrdersCreateService.create(movieOrderCreateRqBo);
        return MovieOrdersCreateConverter.parseToRs(movieOrderCreateRsBo);
    }

    @Operation(summary = "透過 ID 查詢電影訂單", description = "透過 ID 查詢電影訂單")
    @PostApiMapping(value = "query-by-id")
    MovieOrderQueryRs query(@Valid @RequestBody MovieOrderQueryRq movieOrderQueryRq) {
        MovieOrderQueryRqBo movieOrderQueryRqBo = MovieOrdersQueryConverter.parseToBo(movieOrderQueryRq);
        MovieOrderQueryRsBo movieOrderQueryRsBo = movieOrdersQueryService.query(movieOrderQueryRqBo);
        return MovieOrdersQueryConverter.parseToRs(movieOrderQueryRsBo);
    }

    @Operation(summary = "更新電影訂單", description = "更新電影訂單")
    @PutApiMapping(value = "update")
    MovieOrderUpdateRs update(@Valid @RequestBody MovieOrderUpdateRq movieOrderUpdateRq) {
        MovieOrderUpdateRqBo movieOrderUpdateRqBo = MovieOrdersUpdateConverter.parseToBo(movieOrderUpdateRq);
        MovieOrderUpdateRsBo movieOrderUpdateRsBo = movieOrdersUpdateService.update(movieOrderUpdateRqBo);
        return MovieOrdersUpdateConverter.parseToRs(movieOrderUpdateRsBo);
    }

    @Operation(summary = "刪除電影訂單", description = "刪除電影訂單")
    @DeleteApiMapping(value = "delete")
    ApiResponsePayload delete(@Valid @RequestBody MovieOrderDeleteRq movieOrdersDeleteRq) {
        MovieOrderDeleteRqBo movieOrderDeleteRqBo = MovieOrdersDeleteConverter.parseToBo(movieOrdersDeleteRq);
        movieOrdersDeleteService.delete(movieOrderDeleteRqBo);
        return new ApiResponsePayload();
    }
}