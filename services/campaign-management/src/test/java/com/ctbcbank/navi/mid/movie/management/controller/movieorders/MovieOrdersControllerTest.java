package com.ctbcbank.navi.mid.movie.management.controller.movieorders;

import com.ctbcbank.navi.mid.movie.management.CampaignManagementApplication;
import com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload.MovieOrderCreateRq;
import com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload.MovieOrderDeleteRq;
import com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload.MovieOrderQueryRq;
import com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload.MovieOrderUpdateRq;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.create.MovieOrderCreateRsBo;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.create.MovieOrdersCreateServiceImpl;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.delete.MovieOrdersDeleteServiceImpl;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.query.MovieOrderQueryRsBo;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.query.MovieOrdersQueryServiceImpl;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.update.MovieOrderUpdateRsBo;
import com.ctbcbank.navi.mid.movie.management.service.movieorders.update.MovieOrdersUpdateServiceImpl;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.test.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = CampaignManagementApplication.class)
public class MovieOrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieOrdersCreateServiceImpl movieOrdersCreateServiceImpl;
    @MockBean
    private MovieOrdersQueryServiceImpl movieOrdersQueryServiceImpl;
    @MockBean
    private MovieOrdersUpdateServiceImpl movieOrdersUpdateServiceImpl;
    @MockBean
    private MovieOrdersDeleteServiceImpl movieOrdersDeleteServiceImpl;

    private static final String create = "/v1/movie-orders/create";
    private static final String query = "/v1/movie-orders/query-by-id";
    private static final String update = "/v1/movie-orders/update";
    private static final String delete = "/v1/movie-orders/delete";

    @Test
    @Order(1)
    @DisplayName("MovieOrdersController.create()_success")
    public void create_success() throws Exception {
        when(movieOrdersCreateServiceImpl.create(any())).thenReturn(MovieOrderCreateRsBo.builder()
                .totalPrice(BigInteger.valueOf(1500))
                .build());

        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(create, MovieOrderCreateRq.builder()
                        .quantity(BigInteger.valueOf(10))
                        .movieId(BigInteger.valueOf(15))
                        .userId(BigInteger.valueOf(7))
                .build())
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Order(2)
    @DisplayName("MovieOrdersController.create()_failed")
    public void create_failed() throws Exception {
        when(movieOrdersCreateServiceImpl.create(any())).thenThrow(new NaviException(FabricResponseCode.DATA_NOT_FOUND, "此電影不存在。"));
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(create, MovieOrderCreateRq.builder()
                .quantity(BigInteger.valueOf(10))
                .movieId(BigInteger.valueOf(50))
                .userId(BigInteger.valueOf(7))
                .build())
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Order(3)
    @DisplayName("MovieOrdersController.query()_success")
    public void query_success() throws Exception {
        when(movieOrdersQueryServiceImpl.query(any())).thenReturn(MovieOrderQueryRsBo.builder()
                .movieOrderInfoBo(List.of(
                        MovieOrderQueryRsBo.MovieOrderInfoBo.builder()
                                .quantity(BigInteger.valueOf(10))
                                .totalPrice(BigInteger.valueOf(1500))
                                .movieName("StringMovie")
                                .userName("StringUser")
                                .build()
                ))
                .build());

        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(query, MovieOrderQueryRq.builder()
                .id(BigInteger.valueOf(12))
                .build())
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Order(4)
    @DisplayName("MovieOrdersController.query()_failed")
    public void query_failed() throws Exception {
        when(movieOrdersQueryServiceImpl.query(any())).thenThrow(new NaviException(FabricResponseCode.DATA_NOT_FOUND, "找不到對應的 ID。"));
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(query, MovieOrderQueryRq.builder()
                .id(BigInteger.valueOf(12))
                .build())
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Order(5)
    @DisplayName("MovieOrdersController.update()_success")
    public void update_success() throws Exception {
        when(movieOrdersUpdateServiceImpl.update(any())).thenReturn(MovieOrderUpdateRsBo.builder()
                .movieOrdersInfoBo(
                        MovieOrderUpdateRsBo.MovieOrdersInfoBo.builder()
                                .quantity(BigInteger.valueOf(15))
                                .totalPrice(BigInteger.valueOf(2250))
                                .build()
                )
                .build());

        mockMvc.perform(TestUtils.putMockMvcRequestBuilders(update, MovieOrderUpdateRq.builder()
                .id(BigInteger.valueOf(12))
                .movieOrdersInfo(
                        MovieOrderUpdateRq.MovieOrdersInfo.builder()
                                .quantity(BigInteger.valueOf(15))
                                .build()
                )
                .build())
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Order(6)
    @DisplayName("MovieOrdersController.update()_failed")
    public void update_failed() throws Exception {
        when(movieOrdersUpdateServiceImpl.update(any())).thenThrow(new NaviException(FabricResponseCode.DATA_NOT_FOUND, "此資料已被刪除，無法操作。"));
        mockMvc.perform(TestUtils.putMockMvcRequestBuilders(update, MovieOrderUpdateRq.builder()
                .id(BigInteger.valueOf(200))
                .movieOrdersInfo(
                        MovieOrderUpdateRq.MovieOrdersInfo.builder()
                                .quantity(BigInteger.valueOf(15))
                                .build()
                )
                .build())
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Order(7)
    @DisplayName("MovieOrdersController.delete()_success")
    public void delete_success() throws Exception {
        doNothing().when(movieOrdersDeleteServiceImpl).delete(any());

        mockMvc.perform(TestUtils.deleteMockMvcRequestBuilders(delete, MovieOrderDeleteRq.builder()
                        .id(BigInteger.valueOf(12))
                        .build())
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));;
    }

    @Test
    @Order(8)
    @DisplayName("MovieOrdersController.delete()_failed")
    public void delete_failed() throws Exception {
        doThrow(new NaviException(FabricResponseCode.DATA_NOT_FOUND, "找不到對應的 ID。")).when(movieOrdersDeleteServiceImpl).delete(any());
        mockMvc.perform(TestUtils.deleteMockMvcRequestBuilders(delete, MovieOrderDeleteRq.builder()
                .id(BigInteger.valueOf(30))
                .build())
        ).andDo(print()).andExpect(status().isOk());
    }
}
