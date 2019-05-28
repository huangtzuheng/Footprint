package com.example.user.project;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // 表格名稱
    public static final String TABLE_NAME = "user";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String USER_NAME = "user_name";
    public static final String USER_PICTURE = "user_picture";
    public static final String USER_PHONE = "user_phone";
    public static final String USER_SCORE = "user_score";


    public static final String[] COLUMNS = {KEY_ID, USER_NAME, USER_PHONE,USER_SCORE};

    public static final String[]SHOW_COLUMNS ={KEY_ID, USER_NAME, USER_PHONE,USER_SCORE};

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_NAME + " TEXT NOT NULL, " +
                    USER_PICTURE + " TEXT NOT NULL, " +
                    USER_PHONE + " TEXT NOT NULL, " +
                    USER_SCORE + " INTEGER NOT NULL DEFAULT 0) ";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public UserDAO(Context context) {
        db = MyDBHelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    // 新增參數指定的物件
    public User insert(User user) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
//        USER_NAME
//        USER_PICTURE
//        USER_PHONE
//        USER_SCORE

        cv.put(USER_NAME, user.getName());
        cv.put(USER_PICTURE, user.getPicture());
        cv.put(USER_PHONE, user.getPhone());
        cv.put(USER_SCORE, user.getScore());

        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);

        // 設定編號
        user.setID(id);

        // 回傳結果
        return user;
    }

    // 修改參數指定的物件
    public boolean update(User user) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(USER_NAME, user.getName());
        cv.put(USER_PICTURE, user.getPicture());
        cv.put(USER_PHONE, user.getPhone());
        cv.put(USER_SCORE, user.getScore());

        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + user.getId();

        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    // 刪除參數指定編號的資料
    public boolean delete(long id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    // 讀取所有記事資料
    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    // 取得指定編號的資料物件
    public User get(long id) {
        // 準備回傳結果用的物件
        User user = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            //讀取包裝一筆資料的物件
            user = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return user;
    }

    // 把Cursor目前的資料包裝為物件
    public User getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        User result = new User();

//        USER_NAME
//        USER_PICTURE
//        USER_PHONE
//        USER_SCORE

        result.setID(cursor.getLong(0));
        result.setName(cursor.getString(1));
        result.setPicture(cursor.getString(2));
        result.setPhone(cursor.getString(3));
        result.setSCORE(cursor.getInt(4));

        // 回傳結果
        return result;
    }

    // 取得資料數量
    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }


    // 建立範例資料
    public void sample() {
        User user = new User(0, "梁修誠", "https://i.imgur.com/EgEPqWZ.jpg", "0932532093", 0);
        User user2 = new User(0, "陳家源", "https://i.imgur.com/XucfZr8.jpg", "0903994839", 100);
        User user3 = new User(0, "張家齊", "https://i.imgur.com/wNFhC3G.jpg", "0909383727", 5);
        User user4 = new User(0, "廖昊廷", "https://i.imgur.com/wNFhC3G.jpg", "0978023727", 200);

        insert(user);
        insert(user2);
        insert(user3);
        insert(user4);
    }
}
