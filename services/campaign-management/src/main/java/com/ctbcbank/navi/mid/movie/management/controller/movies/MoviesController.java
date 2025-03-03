package com.ctbcbank.navi.mid.movie.management.controller.movies;

import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.*;
import com.ctbcbank.navi.mid.movie.management.service.movies.create.MoviesCreateConverter;
import com.ctbcbank.navi.mid.movie.management.service.movies.create.MovieCreateRqBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.create.MovieCreateRsBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.create.MoviesCreateService;
import com.ctbcbank.navi.mid.movie.management.service.movies.delete.MovieDeleteRqBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.delete.MoviesDeleteConverter;
import com.ctbcbank.navi.mid.movie.management.service.movies.delete.MoviesDeleteService;
import com.ctbcbank.navi.mid.movie.management.service.movies.query.MoviesQueryConverter;
import com.ctbcbank.navi.mid.movie.management.service.movies.query.MovieQueryRqBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.query.MovieQueryRsBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.query.MoviesQueryService;
import com.ctbcbank.navi.mid.movie.management.service.movies.queryAsync.MovieQueryAsyncRsBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.queryAsync.MoviesQueryAsyncConverter;
import com.ctbcbank.navi.mid.movie.management.service.movies.queryAsync.MoviesQueryAsyncService;
import com.ctbcbank.navi.mid.movie.management.service.movies.querybyname.MovieQueryByNameRqBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.querybyname.MovieQueryByNameRsBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.querybyname.MoviesQueryByNameConverter;
import com.ctbcbank.navi.mid.movie.management.service.movies.querybyname.MoviesQueryByNameService;
import com.ctbcbank.navi.mid.movie.management.service.movies.update.MoviesUpdateConverter;
import com.ctbcbank.navi.mid.movie.management.service.movies.update.MovieUpdateRqBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.update.MovieUpdateRsBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.update.MoviesUpdateService;
import com.ibm.cbmp.fabric.web.api.annotation.DeleteApiMapping;
import com.ibm.cbmp.fabric.web.api.annotation.GetApiMapping;
import com.ibm.cbmp.fabric.web.api.annotation.PostApiMapping;
import com.ibm.cbmp.fabric.web.api.annotation.PutApiMapping;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "v1/movies/", produces = MediaType.APPLICATION_JSON_VALUE)
public class MoviesController {

    private final MoviesCreateService moviesCreateService;
    private final MoviesQueryService moviesQueryService;
    private final MoviesQueryByNameService moviesQueryByNameService;
    private final MoviesQueryAsyncService moviesQueryAsyncService;
    private final MoviesUpdateService moviesUpdateService;
    private final MoviesDeleteService moviesDeleteService;

    @Operation(summary = "新增電影", description = "新增電影")
    @PostApiMapping(value = "create")
    MovieCreateRs create(@Valid @RequestBody MovieCreateRq movieCreateRq) {
        MovieCreateRqBo movieCreateRqBo = MoviesCreateConverter.parseToBo(movieCreateRq);
        MovieCreateRsBo movieCreateRsBo = moviesCreateService.create(movieCreateRqBo);
        return MoviesCreateConverter.parseToRs(movieCreateRsBo);
    }

    @Operation(summary = "透過 ID 查詢電影", description = "透過 ID 查詢電影")
    @PostApiMapping(value = "query-by-id")
    MovieQueryRs query(@Valid @RequestBody MovieQueryRq movieQueryRq) {
        MovieQueryRqBo movieQueryRqBo = MoviesQueryConverter.parseToBo(movieQueryRq);
        MovieQueryRsBo movieQueryRsBo = moviesQueryService.query(movieQueryRqBo);
        return MoviesQueryConverter.parseToRs(movieQueryRsBo);
    }

    @Operation(summary = "透過 電影名稱 列表查詢電影", description = "透過 電影名稱 列表查詢電影")
    @GetApiMapping(value = "query-by-name")
    MovieQueryByNameRs queryByName(@Valid @Parameter MovieQueryByNameRq movieQueryByNameRq) {
        MovieQueryByNameRqBo movieQueryByNameRqBo = MoviesQueryByNameConverter.parseToBo(movieQueryByNameRq);
        MovieQueryByNameRsBo movieQueryByNameRsBo = moviesQueryByNameService.queryByName(movieQueryByNameRqBo);
        return MoviesQueryByNameConverter.parseToRs(movieQueryByNameRsBo);
    }

    @Operation(summary = "非同步查詢所有電影訂單的用戶詳細資料與電影詳細資料", description = "非同步查詢所有電影訂單的用戶詳細資料與電影詳細資料")
    @GetApiMapping(value = "query-async")
    MovieQueryAsyncRs queryAsync() {
        MovieQueryAsyncRsBo movieQueryAsyncRsBo = moviesQueryAsyncService.queryAsync();
        return MoviesQueryAsyncConverter.parseToRs(movieQueryAsyncRsBo);
    }

    @Operation(summary = "更新電影", description = "更新電影")
    @PutApiMapping(value = "update")
    MovieUpdateRs update(@Valid @RequestBody MovieUpdateRq movieUpdateRq) {
        MovieUpdateRqBo movieUpdateRqBo = MoviesUpdateConverter.parseToBo(movieUpdateRq);
        MovieUpdateRsBo movieUpdateRsBo = moviesUpdateService.update(movieUpdateRqBo);
        return MoviesUpdateConverter.parseToRs(movieUpdateRsBo);
    }

    @Operation(summary = "刪除電影", description = "刪除電影")
    @DeleteApiMapping(value = "delete")
    ApiResponsePayload delete(@Valid @RequestBody MovieDeleteRq moviesDeleteRq) {
        MovieDeleteRqBo movieDeleteRqBo = MoviesDeleteConverter.parseToBo(moviesDeleteRq);
        moviesDeleteService.delete(movieDeleteRqBo);
        return new ApiResponsePayload();
    }
}