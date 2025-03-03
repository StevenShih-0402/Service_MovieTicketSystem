package com.ctbcbank.navi.mid.movie.management.controller.movies;

import com.ctbcbank.navi.mid.movie.management.CampaignManagementApplication;
import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.*;
import com.ctbcbank.navi.mid.movie.management.service.movies.create.MovieCreateRsBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.create.MoviesCreateServiceImpl;
import com.ctbcbank.navi.mid.movie.management.service.movies.delete.MoviesDeleteServiceImpl;
import com.ctbcbank.navi.mid.movie.management.service.movies.query.MovieQueryRsBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.query.MoviesQueryServiceImpl;
import com.ctbcbank.navi.mid.movie.management.service.movies.querybyname.MovieQueryByNameRsBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.querybyname.MoviesQueryByNameServiceImpl;
import com.ctbcbank.navi.mid.movie.management.service.movies.update.MovieUpdateRsBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.update.MoviesUpdateServiceImpl;
import com.ibm.cbmp.fabric.test.utils.TestUtils;
import com.ibm.cbmp.fabric.web.enums.ApiResponseCode;
import com.ibm.cbmp.fabric.web.utils.ApiExceptionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = CampaignManagementApplication.class)
public class MoviesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // 建立一個假的 Beans，專門用來取代被 Spring 管理的 Beans，如 @Service、@Repository、@Component
    // 確保測試時使用的是 Mock 版本，而不是實際的實作。
    @MockBean
    private MoviesCreateServiceImpl moviesCreateServiceImpl;
    @MockBean
    private MoviesQueryServiceImpl moviesQueryServiceImpl;
    @MockBean
    private MoviesQueryByNameServiceImpl moviesQueryByNameServiceImpl;
    @MockBean
    private MoviesUpdateServiceImpl moviesUpdateServiceImpl;
    @MockBean
    private MoviesDeleteServiceImpl moviesDeleteServiceImpl;

    private static final String create = "/v1/movies/create";
    private static final String query = "/v1/movies/query-by-id";
    private static final String queryByName = "/v1/movies/query-by-name";
    private static final String update = "/v1/movies/update";
    private static final String delete = "/v1/movies/delete";

    @Test
    @Order(1)
    @DisplayName("MoviesController.create()_success")
    public void create_success() throws Exception {

        MovieCreateRsBo movieCreateRsBo = MovieCreateRsBo.builder()
                .id(BigInteger.valueOf(1))
                .movieName("StringMovie123")
                .build();

        // 模擬 Service 執行 create 方法後回傳 RsBo 的成功情境
        when(moviesCreateServiceImpl.create(any())).thenReturn(movieCreateRsBo);

        // 模擬新增一部電影，驗證印出的回傳值有沒有符合格式
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(
                        create,
                        MovieCreateRq.builder()
                                .movieName("StringMovie123")
                                .price(BigInteger.valueOf(350))
                                .build())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.movieName").exists());
    }

    @Test
    @Order(2)
    @DisplayName("MoviesController.create()_failed")
    public void create_failed() throws Exception {

        MovieCreateRsBo movieCreateRsBo = MovieCreateRsBo.builder()
                .id(BigInteger.valueOf(1))
                .movieName("StringMovie123")
                .build();

        // 模擬 Service 執行 create 方法的情境
        when(moviesCreateServiceImpl.create(any())).thenReturn(movieCreateRsBo);

        // 情境 1. 模擬新增一部電影，驗證 Controller 是否有判斷到電影價格不為正數的錯誤。
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(
                        create,
                        MovieCreateRq.builder()
                                .movieName("stringMovie")
                                .price(BigInteger.valueOf(0))
                                .build()
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error.code").value(ApiExceptionUtils.getErrorCode(ApiResponseCode.INVALID_REQUEST_DATA)));
//                .andExpect(jsonPath("$.error.message").value(containsString("must be greater than 0")));

        // 情境 2. 模擬新增一部電影，驗證 Controller 是否有判斷到電影名稱為空值的錯誤。
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(
                        create,
                        MovieCreateRq.builder()
                                .movieName(" ")
                                .price(BigInteger.valueOf(100))
                                .build()
                ))
                .andDo(print())
                .andExpect(status().isOk())
                // 其實只要驗證 Error code 就好，CAMPAIGN-P-900002 代表從 Rq 回傳的 Error。
                .andExpect(jsonPath("$.error.code").value(ApiExceptionUtils.getErrorCode(ApiResponseCode.INVALID_REQUEST_DATA)));
//                .andExpect(jsonPath("$.error.message").value("invalid request data;[movieName= ]must not be blank"));

        // 情境 3. 模擬新增一部電影，驗證 Controller 是否有判斷到電影價格不在 300 - 500 之間的錯誤。
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(
                        create,
                        MovieCreateRq.builder()
                                .movieName("stringMovie")
                                .price(BigInteger.valueOf(600))
                                .build()
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error.code").value(ApiExceptionUtils.getErrorCode(ApiResponseCode.INVALID_REQUEST_DATA)));

    }

    @Test
    @Order(3)
    @DisplayName("MoviesController.query()_success")
    public void query_success() throws Exception {

        MovieQueryRsBo movieQueryRsBo = MovieQueryRsBo.builder()
                .moviesInfoList(List.of(
                        MovieQueryRsBo.MoviesInfoBo.builder()
                                .movieName("StringMovie123")
                                .price(BigInteger.valueOf(350))
                                .build()
                ))
                .build();

        // 模擬 Service 執行 query 方法的情境
        when(moviesQueryServiceImpl.query(any())).thenReturn(movieQueryRsBo);

        // 模擬查詢一部電影，驗證印出的回傳值有沒有符合格式
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(
                        query,
                        MovieQueryRq.builder()
                                .id(BigInteger.valueOf(1))
                                .build())
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.moviesInfoList").isArray())
        .andExpect(jsonPath("$.moviesInfoList").isNotEmpty())
        .andExpect(jsonPath("$.moviesInfoList[0]").exists());
    }

    @Test
    @Order(4)
    @DisplayName("MoviesController.query()_failed")
    public void query_failed() throws Exception {

        MovieQueryRsBo movieQueryRsBo = MovieQueryRsBo.builder()
                .moviesInfoList(List.of(
                        MovieQueryRsBo.MoviesInfoBo.builder()
                                .movieName("StringMovie123")
                                .price(BigInteger.valueOf(350))
                                .build()
                ))
                .build();

        // 模擬 Service 執行 query 方法的情境
        when(moviesQueryServiceImpl.query(any())).thenReturn(movieQueryRsBo);

        // 模擬查詢一部電影，驗證 ID 是否不為正數
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(query, MovieQueryRq.builder()
                        .id(BigInteger.valueOf(0))
                        .build())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error.code").value(ApiExceptionUtils.getErrorCode(ApiResponseCode.INVALID_REQUEST_DATA)));
//                .andExpect(jsonPath("$.error.message").value(containsString("must be greater than 0")));
    }

    @Test
    @Order(5)
    @DisplayName("MoviesController.queryByName()_success")
    public void queryByName_success() throws Exception {

        MovieQueryByNameRsBo movieQueryByNameRsBo = MovieQueryByNameRsBo.builder()
                .moviesInfoList(List.of(
                        MovieQueryByNameRsBo.MoviesInfoBo.builder()
                                .movieName("StringMovie123")
                                .price(BigInteger.valueOf(350))
                                .build()
                ))
                .build();

        // 模擬 Service 執行 queryByName 方法的情境
        when(moviesQueryByNameServiceImpl.queryByName(any())).thenReturn(movieQueryByNameRsBo);

        // 模擬用關鍵字查詢電影，驗證印出的回傳值有沒有符合格式
        mockMvc.perform(TestUtils.getMockMvcRequestBuilders(queryByName, MovieQueryByNameRq.builder()
                        .keyWord("String")
                        .price(BigInteger.valueOf(350))
                        .pageNo(BigInteger.valueOf(1))
                .build())
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.moviesInfoList").isArray())
        .andExpect(jsonPath("$.moviesInfoList").isNotEmpty())
        .andExpect(jsonPath("$.moviesInfoList[0]").exists());
    }

    @Test
    @Order(6)
    @DisplayName("MoviesController.queryByName()_failed")
    public void queryByName_failed() throws Exception {

        MovieQueryByNameRsBo movieQueryByNameRsBo = MovieQueryByNameRsBo.builder()
                .moviesInfoList(List.of(
                        MovieQueryByNameRsBo.MoviesInfoBo.builder()
                                .movieName("StringMovie123")
                                .price(BigInteger.valueOf(350))
                                .build()
                ))
                .build();

        // 模擬 Service 執行 queryByName 方法的情境
        when(moviesQueryByNameServiceImpl.queryByName(any())).thenReturn(movieQueryByNameRsBo);

        // 情境 1. 模擬用關鍵字查詢電影，驗證關鍵字是否為空值
        mockMvc.perform(TestUtils.getMockMvcRequestBuilders(queryByName, MovieQueryByNameRq.builder()
                        .keyWord(" ")
                        .price(BigInteger.valueOf(350))
                        .pageNo(BigInteger.valueOf(1))
                .build())
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.error.code").value(ApiExceptionUtils.getErrorCode(ApiResponseCode.INVALID_REQUEST_DATA)));
//        .andExpect(jsonPath("$.error.message").value(containsString("must not be blank")));

        // 情境 2. 模擬用關鍵字查詢電影，驗證頁數是否不為正數
        mockMvc.perform(TestUtils.getMockMvcRequestBuilders(queryByName, MovieQueryByNameRq.builder()
                        .keyWord("String")
                        .price(BigInteger.valueOf(350))
                        .pageNo(BigInteger.valueOf(0))
                        .build())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error.code").value(ApiExceptionUtils.getErrorCode(ApiResponseCode.INVALID_REQUEST_DATA)));
//                .andExpect(jsonPath("$.error.message").value(containsString("must be greater than 0")));

        // 情境 3. 模擬用關鍵字查詢電影，驗證 Controller 是否有判斷到電影價格不在 300 - 500 之間的錯誤。
        mockMvc.perform(TestUtils.getMockMvcRequestBuilders(queryByName, MovieQueryByNameRq.builder()
                        .keyWord("String")
                        .price(BigInteger.valueOf(600))
                        .pageNo(BigInteger.valueOf(1))
                        .build())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error.code").value(ApiExceptionUtils.getErrorCode(ApiResponseCode.INVALID_REQUEST_DATA)));
    }

    @Test
    @Order(7)
    @DisplayName("MoviesController.update()_success")
    public void update_success() throws Exception {

        MovieUpdateRsBo movieUpdateRsBo = MovieUpdateRsBo.builder()
                .moviesInfoBo(
                        MovieUpdateRsBo.MoviesInfoBo.builder()
                                .movieName("StringM")
                                .price(BigInteger.valueOf(500))
                                .build()
                )
                .build();

        // 模擬 Service 執行 Update 方法的情境
        when(moviesUpdateServiceImpl.update(any())).thenReturn(movieUpdateRsBo);

        // 模擬更新電影資料，驗證印出的回傳值有沒有符合格式
        mockMvc.perform(TestUtils.putMockMvcRequestBuilders(update, MovieUpdateRq.builder()
                        .id(BigInteger.valueOf(1))
                        .moviesInfo(
                                MovieUpdateRq.MoviesInfo.builder()
                                        .movieName("StringM")
                                        .price(BigInteger.valueOf(500))
                                        .build()
                        )
                .build())
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.moviesInfo").exists())
        .andExpect(jsonPath("$.moviesInfo").isNotEmpty());
    }

    @Test
    @Order(8)
    @DisplayName("MoviesController.update()_failed")
    public void update_failed() throws Exception {

        MovieUpdateRsBo movieUpdateRsBo = MovieUpdateRsBo.builder()
                .moviesInfoBo(
                        MovieUpdateRsBo.MoviesInfoBo.builder()
                                .movieName("StringM")
                                .price(BigInteger.valueOf(500))
                                .build()
                )
                .build();

        // 模擬 Service 執行 Update 方法的情境
        when(moviesUpdateServiceImpl.update(any())).thenReturn(movieUpdateRsBo);

        // 情境 1. 模擬更新電影資料，驗證更新的電影名稱是否為空值
        mockMvc.perform(TestUtils.putMockMvcRequestBuilders(update, MovieUpdateRq.builder()
                .id(BigInteger.valueOf(1))
                .moviesInfo(
                        MovieUpdateRq.MoviesInfo.builder()
                                .movieName(" ")
                                .price(BigInteger.valueOf(500))
                                .build()
                )
                .build())
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.error.code").value(ApiExceptionUtils.getErrorCode(ApiResponseCode.INVALID_REQUEST_DATA)));
//        .andExpect(jsonPath("$.error.message").value(containsString("must not be blank")));

        // 情境 2. 模擬更新電影資料，驗證更新的電影價格是否不為正數
        mockMvc.perform(TestUtils.putMockMvcRequestBuilders(update, MovieUpdateRq.builder()
                .id(BigInteger.valueOf(1))
                .moviesInfo(
                        MovieUpdateRq.MoviesInfo.builder()
                                .movieName("StringM")
                                .price(BigInteger.valueOf(-10))
                                .build()
                )
                .build())
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.error.code").value(ApiExceptionUtils.getErrorCode(ApiResponseCode.INVALID_REQUEST_DATA)));
//        .andExpect(jsonPath("$.error.message").value(containsString("must be greater than 0")));

        // 情境 3. 模擬更新電影資料，驗證更新的電影價格是否介於 300 - 500 的區間
        mockMvc.perform(TestUtils.putMockMvcRequestBuilders(update, MovieUpdateRq.builder()
                        .id(BigInteger.valueOf(1))
                        .moviesInfo(
                                MovieUpdateRq.MoviesInfo.builder()
                                        .movieName("StringM")
                                        .price(BigInteger.valueOf(600))
                                        .build()
                        )
                        .build())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error.code").value(ApiExceptionUtils.getErrorCode(ApiResponseCode.INVALID_REQUEST_DATA)));
    }

    @Test
    @Order(9)
    @DisplayName("MoviesController.delete()_success")
    @Transactional
    public void delete_success() throws Exception {

        // 模擬 Service 執行 delete 方法的情境
        doNothing().when(moviesDeleteServiceImpl).delete(any());

        // 模擬刪除電影資料，驗證印出的回傳值有沒有符合格式
        mockMvc.perform(TestUtils.deleteMockMvcRequestBuilders(delete, MovieDeleteRq.builder()
               .id(BigInteger.valueOf(1))
               .build())
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    @Order(10)
    @DisplayName("MoviesController.delete()_failed")
    public void delete_failed() throws Exception {

        // 模擬 Service 執行 delete 方法的情境
        doNothing().when(moviesDeleteServiceImpl).delete(any());

        // 模擬刪除電影資料，驗證 ID 是否不為正數
        mockMvc.perform(TestUtils.deleteMockMvcRequestBuilders(delete, MovieDeleteRq.builder()
                .id(BigInteger.valueOf(0))
                .build())
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.error.code").value(ApiExceptionUtils.getErrorCode(ApiResponseCode.INVALID_REQUEST_DATA)));
//        .andExpect(jsonPath("$.error.message").value(containsString("must be greater than 0")));
    }
}