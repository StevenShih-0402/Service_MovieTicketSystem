# Introduction 
>由於資料庫與專案底層相關設定屬於專案機密，因此僅保留本人實作之部分程式碼，以及測試結果截圖。

這是為了熟悉專案流程的開發練習，主題是電影購票系統，主要使用到以下三張資料表，設計 CRUD 相關操作的 API。：

- `TB_BRUCE_MOVIES`：電影資訊，即電影院有上映的電影。
- `TB_BRUCE_USERS`：用戶資訊，即在系統上進行訂票的用戶。
- `TB_BRUCE_MOVIE_ORDERS`：電影訂單資訊，即用戶購買電影票後產生的訂單紀錄。

// 資料庫資訊

- 資料表使用的是軟刪除，被刪除的資料 Status 會從 0 變成 1。
- 採用 Controller > Service > Dao > Repository 的分層架構，使用者輸入資料為 Rq，需轉換成 RqBo 後傳入 Service 處理業務邏輯，再將 RqBo 轉換成 Dto，傳入 DAO 進行資料庫操作
- 資料庫操作多數採用 JPA，但基於技術練習的用意，有些功能會採用 JDBC 去進行操作。

# Swagger Demo
http://localhost:8104/campaign-management/swagger-ui/index.html#/

該系統合計包含 14 支 API，在電影功能的 API 有額外開發列表查詢與非同步查詢兩項功能。

# Unit Test
當時專案對單元測試的要求是：對每支 Controller 層 API 進行一正一負的測試，並達到覆蓋率 80% 以上。
以新增電影 API 舉例說明：
