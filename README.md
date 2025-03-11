# Introduction 
(由於資料庫設定與專案底層設定屬於專案機密，因此僅保留本人實作之部分程式碼，以及離職前的測試結果截圖。)
這是為了熟悉專案開發流程的練習，主要使用到以下三張資料表：`TB_BRUCE_MOVIES`、`TB_BRUCE_USERS` 和 `TB_BRUCE_MOVIE_ORDERS`，練習開發 CRUD 相關操作的 API。

- 資料表使用的是軟刪除，被刪除的資料 Status 會從 0 變成 1。
- 專案採用 Controller > Service > Dao > Entity 的分層架構

# Swagger Demo

# Unit Test
當時專案對單元測試的要求是：對每支 Controller 層 API 進行一正一負的測試，並達到覆蓋率 80% 以上。
