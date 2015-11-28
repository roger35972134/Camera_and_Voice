# Camera_and_Voice
請撰寫一支 Android Application 需求如下：

1. 開啟 App 後，呈現一個空白列表

2. 於最下方設計一顆按鈕為 "Camera"

3. 點擊後開啟相機功能並拍照

4. 拍照完畢，儲存相片於本機，並於列表上增加一筆照片資料

5. 列表照片資料可點擊進入下一頁，並觀看整張照片

6. (Optional) 照片可放大縮小、移動、旋轉

7. 長按照片上任一位置，即可開啟錄音功能

8. 雙擊照片任一其他位置，即可停止錄音

9. 錄音完畢後，顯示一個按鈕於長按位置

10. 點擊按鈕即可播放該錄音檔

11. 可重複增加錄音內容，並於不同按鈕點擊後對應到不同的錄音檔 

2015/11/25 After first meeting:

1.Change ListView to RecyclerView

2.Use Open Source(Glide) to get file into ImageView ---> Big performance improve

3.Use Open Source(ButterKnife) to make code more clearly and easy to read

4.Use floders to do some data save
