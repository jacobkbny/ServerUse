package com.tistroy.jacob;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
//SQLiteOpenHelper 클래스는 Default constructor가 없어서
//생성자를 만들고 매개변슈가 있는 생성자를 호출하지 않으면 에러가 발생
public class ItemDB extends SQLiteOpenHelper {
    //생성
    //Context는 어떤 정보를 저장한 객체
    //안드로드에서는 Context를 매개변수로 하는 메서드가 많은데
    //Context를 대입하라고 하면 Activity 클래스의 인스턴스를 대입하면 됩니다.

    public ItemDB(@Nullable Context context) {
        super(context,"item.db",null,1);
    }
    //업그레이드 될 떄 호출되는 메서드
    //여기서는 테이블을 삭제하고 새로 생성하는 코드를 주로 작성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table item(itemid Integer primary key,"+"itemname,price integer, description,pictureurl,email)");

    }
    //업그레이드 될 떄 호출되는 메서드
    //여기서는 테이블을 삭제하고 새로 생성하는 코드를 주로 작성
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS item");
        onCreate(db);
    }
}
