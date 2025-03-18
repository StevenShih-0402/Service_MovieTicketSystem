# Introduction 
>由於資料庫與專案底層相關設定屬於專案機密，因此僅保留本人實作之部分程式碼，以及測試結果截圖。

這是為了熟悉專案流程的開發練習，主題是電影購票系統，主要使用到以下三張資料表，設計 CRUD 相關操作的 API：

- `TB_BRUCE_MOVIES`：電影資訊，即電影院有上映的電影。
![電影資料表](https://github.com/user-attachments/assets/01710d34-5028-481a-a48e-836db3ad2381)
- `TB_BRUCE_USERS`：用戶資訊，即在系統上進行訂票的用戶。
![用戶資料表](https://github.com/user-attachments/assets/30a4277c-f3c4-4dff-a259-6d8690cc9c94)
- `TB_BRUCE_MOVIE_ORDERS`：電影訂單資訊，即用戶購買電影票後產生的訂單紀錄。
![電影訂單資料表](https://github.com/user-attachments/assets/69da2122-7a10-4c5f-ad62-9103626451d3)

- clone 後，請在 bootRun 的環境變數貼上 `--spring.profiles.active=local`
- 資料表使用的是軟刪除，被刪除的資料 Status 會從 0 變成 1 (`TB_BRUCE_MOVIE_ORDERS` 會從 0 變成 2)。
- 採用 Controller > Service > Dao > Repository 的分層架構，使用者輸入資料為 Rq，需轉換成 RqBo 後傳入 Service 處理業務邏輯，再將 RqBo 轉換成 Dto，傳入 DAO 進行資料庫操作，並在 Repository 繼承 JPA 介面。
- 資料庫操作多數採用 JPA，但基於技術練習的用意，有些功能會刻意採用 JDBC 去進行操作。
- 在 Code Review 時基於技術練習的用意，除 CRUD 以外還有開發列表查詢、非同步查詢的 API，並撰寫單元測試，當時考量開發時間效益，因此僅在電影資料表 (`TB_BRUCE_MOVIES`) 實作。

# Swagger Demo
該系統合計包含 14 支 API。
http://localhost:8104/campaign-management/swagger-ui/index.html#/
![image](https://github.com/user-attachments/assets/e7075513-03c9-48b5-bc4f-3d7f132357dd)
![image](https://github.com/user-attachments/assets/f5524663-2c46-42de-af63-15a4f2d3ace5)
![image](https://github.com/user-attachments/assets/1b4c1918-35d9-4b14-ad19-3dfda95963d0)

1. 新增電影資訊

新增一部名稱不重複，價格 300~500 元的電影，回傳 id 與電影名稱表示新增成功。
![image](https://github.com/user-attachments/assets/3c03cdf6-ba2a-44e6-825e-e31e192afda8)

2. 透過 id 查詢電影資訊
![image](https://github.com/user-attachments/assets/12eec048-1b7d-4786-bb28-c5c81d3dec44)

3. 修改電影資訊
![image](https://github.com/user-attachments/assets/3440e865-5065-4958-ad15-0be676e241ca)

4. 刪除電影資訊
![image](https://github.com/user-attachments/assets/2bdb5fe2-e6df-453c-801e-aace4a6c0a61)

5. 列表查詢電影資訊
![image](https://github.com/user-attachments/assets/6564b0d8-5dda-4241-93c3-e801df8ad7e3)
![image](https://github.com/user-attachments/assets/bfa81b21-9648-45a5-9b8a-39838ac0b605)

6. 非同步查詢所有電影訂單、與其對應之用戶、電影詳細資訊
![image](https://github.com/user-attachments/assets/d20c2d82-cac3-4d79-951b-8b7bd00db463)
![image](https://github.com/user-attachments/assets/ec1987b3-3d61-4442-a52b-f6cc16bfb2af)

7. 新增用戶資訊
![image](https://github.com/user-attachments/assets/49d4173b-0b21-46e7-ac5e-d06180f231ee)

8. 透過 id 查詢用戶資訊
![image](https://github.com/user-attachments/assets/20494c3d-c74d-4f9f-bcac-2344fd46cb6b)

9. 修改用戶資訊
![image](https://github.com/user-attachments/assets/d6c71fd7-0fc6-489d-bba5-2f7026d5e69e)

10. 刪除用戶資訊
![image](https://github.com/user-attachments/assets/95561fc0-26b2-484c-9122-99284748b41c)

11. 新增電影訂單資訊
![image](https://github.com/user-attachments/assets/8a7e158c-9b9b-4325-9ba2-b302308d0b24)

12. 透過 id 查詢電影資訊
![image](https://github.com/user-attachments/assets/dc4e4f97-a315-4e35-9e24-b632a0a305a4)

13. 修改電影資訊
![image](https://github.com/user-attachments/assets/a643a48a-b212-4b91-9866-25d9db17edca)

14. 刪除電影資訊
![image](https://github.com/user-attachments/assets/2bf302f7-1d63-4b4b-bd1c-ed54573fabd5)

# Unit Test
當時專案對單元測試的要求是：對每支 Controller 層 API 進行一正一負的測試，並達到覆蓋率 80% 以上。
以新增電影 API 舉例說明：
1. 正向測試
先建立一筆模擬用的回傳物件 RsBo，再用 when 定義 API 被執行後回傳 RsBo 的情境，最後用 Mock 模擬 API 執行，帶入應可成功執行的 Rq，並驗證回傳內容是否符合執行成功後的規格。
```java
@Test
@Order(1)
@DisplayName("MoviesController.create()_success")
public void create_success() throws Exception {

   // 建立測資
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
```
2. 負向測試
先建立一筆模擬用的回傳物件 RsBo，再用 when 定義 API 被執行後回傳 RsBo 的情境，最後用 Mock 模擬 API 執行，帶入應報錯的 Rq，並驗證回傳內容是否符合規格。
```java
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
```
最後附上單元測試的測報：

![電影測報](https://github.com/user-attachments/assets/c1a1c1e4-4858-4853-a74f-6bac4bd7eef1)
![用戶測報](https://github.com/user-attachments/assets/2c5b7abf-31b7-4cb9-af82-c74f54cc4cbd)
![電影訂單測報](https://github.com/user-attachments/assets/498dbe12-9282-4522-b2b4-88849905a672)
