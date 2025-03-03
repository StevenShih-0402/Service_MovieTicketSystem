package com.ctbcbank.navi.mid.movie.management.controller.movies;

import com.ctbcbank.navi.mid.movie.management.CampaignManagementApplication;
import com.ctbcbank.navi.mid.movie.management.dao.MoviesDao;
import com.ctbcbank.navi.mid.movie.management.dto.MovieDto;
import com.ctbcbank.navi.mid.movie.management.service.movies.create.MovieCreateRqBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.create.MovieCreateRsBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.create.MoviesCreateServiceImpl;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.web.enums.ApiResponseCode;
import com.ibm.cbmp.fabric.web.utils.ApiExceptionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// 啟用 Mockito 的延伸功能，自動初始化 @Mock 和 @InjectMocks。
// 不加的話 @Mock 標註的物件不會初始化，導致變數是 null，測試會拋出 NullPointerException。
@SpringBootTest(classes = CampaignManagementApplication.class)
@ExtendWith(MockitoExtension.class)
public class MoviesServiceTest {

    @Autowired
    private MoviesCreateServiceImpl moviesCreateService;

    @MockBean
    private MoviesDao moviesDao;  // 建立一個假的 DAO 層

    /**
    正向測試：
    測試當電影名稱不重複時，是否能成功新增電影。
     */

    @Test
    @Order(1)
    @DisplayName("MoviesService.create()_success")
    public void create_success() {

        System.out.println("moviesDao = " + moviesDao);
        System.out.println("moviesDao class = " + moviesDao.getClass());

        // 1. 準備階段 (Arrange)
        // 模擬一個建立電影的 RqBo
        MovieCreateRqBo movieCreateRqBo = new MovieCreateRqBo();
        movieCreateRqBo.setMovieName("MMMMM");
        movieCreateRqBo.setPrice(BigInteger.valueOf(350));

        // 模擬 DAO 層回傳的資料
        MovieDto saveMovieDto = new MovieDto();
        saveMovieDto.setId(BigInteger.valueOf(1));
        saveMovieDto.setMovieName("MMMMM");
        saveMovieDto.setPrice(BigInteger.valueOf(350));

        // 模擬 moviesDao 執行 existName() 的行為，回傳 false 表示電影名稱不重複的情境，因為這邊不能真的進入 DB 驗證名稱是否重複。
        when(moviesDao.existName(movieCreateRqBo.getMovieName())).thenReturn(false);

        // 模擬 moviesDao 執行 saveMovies() 的行為，回傳儲存後的 MovieDto 物件。允許 Mockito 在 when(...) 或 verify(...) 方法中匹配任何 MovieDto 類型的參數。
        when(moviesDao.saveMovies(any(MovieDto.class))).thenReturn(saveMovieDto);

        // 2. 執行階段 (Act)
        // 執行新增電影的邏輯。
        MovieCreateRsBo movieCreateRsBo = moviesCreateService.create(movieCreateRqBo);
        verify(moviesDao, times(1)).saveMovies(any(MovieDto.class));
        // 3. 驗證階段 (Assert)
        // 驗證 RsBo 不是 Null
        assertNotNull(movieCreateRsBo);

        // 驗證 ID 存在
        assertNotNull(movieCreateRsBo.getId());

        // 驗證 MovieName 正確
        assertEquals(movieCreateRqBo.getMovieName(), movieCreateRsBo.getMovieName());

        // 確保 existName() 和 saveMovies() 有被呼叫，確認系統有進行「電影名稱是否重複」的檢查，以及確實有執行業務邏輯，沒有跳過。
        verify(moviesDao).existName(movieCreateRqBo.getMovieName());
        verify(moviesDao).saveMovies(any(MovieDto.class));
    }

    /**
    負向測試：
    測試當電影名稱已存在時，是否正確拋出例外 (NaviException)，並且不會執行 saveMovies()。
     */

    @Test
    @Order(2)
    @DisplayName("MoviesService.create()_failed")
    public void create_failed() {
        // 1. 準備階段 (Arrange)

        // 模擬一個建立電影的 RqBo
        MovieCreateRqBo movieCreateRqBo = new MovieCreateRqBo();
        movieCreateRqBo.setMovieName("MovieString");
        movieCreateRqBo.setPrice(BigInteger.valueOf(350));

        // 模擬電影名稱已存在
        when(moviesDao.existName(movieCreateRqBo.getMovieName())).thenReturn(true);

        // Act & Assert
        NaviException exception = assertThrows(NaviException.class, () -> moviesCreateService.create(movieCreateRqBo));
        assertEquals(ApiExceptionUtils.getErrorCode(ApiResponseCode.INVALID_REQUEST_DATA), exception.getResponseCode().getErrorCode());

        // 確保 saveMovies() 沒有被呼叫
        verify(moviesDao, Mockito.never()).saveMovies(any(MovieDto.class));
    }
}
