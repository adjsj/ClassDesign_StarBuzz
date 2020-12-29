package bjfu.it.zhanghesu.classdesign_starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="starbuzz.db";
    private static final int DB_VERSION=1;

    public DatabaseHelper(Context context){super(context,DB_NAME,null,DB_VERSION);}
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DRINK(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"NAME TEXT,"
                +"TYPE NUMERIC,"
                +"PRICE INTEGER,"
                +"DESCRIPTION TEXT,"
                +"IMAGE_RESOURCE_ID INTEGER,"
                +"FAVORITE NUMERIC);"
        );
        insert(db,"Latte",0,23,"Tasty",R.mipmap.drink_latte);
        insert(db,"Cappuccino",0,22,"Espresso,hot milk and streamed-milk foam",R.mipmap.drink_cappuccino);
        insert(db,"Fliter",0,25,"Our best drip coffee",R.mipmap.drink_filter);
        insert(db,"Muffin",1,18,"Enjoy the wonderful taste of imported blueberry fruit\n" +
                "Starbucks unique American formula production\n" +
                "The top layer is crisp and soft inside",R.mipmap.food_muffin);
        insert(db,"Yoghurt",1,12,"Excellent raw materials: selection of high-quality raw milk + fermentation with imported strains from Europe\n" +
                "Unique technology: adopting Greek physical de whey technology, with mellow texture and rich milk flavor",R.mipmap.food_yoghurt);
        insert(db,"Hamburger",1,15,"Avocado chicken baked fruit contains smoked and boiled chicken breast, with American imported avocado and cheddar cheese, fresh and healthy taste",R.mipmap.food_hamburger);
        insert(db,"Cup",2,200,"200ml tiger bear heat preservation cup",R.mipmap.store_cup);
        insert(db,"Card",2,98,"Green Ambassador member Star gift bag",R.mipmap.store_card);
        insert(db,"Coffee Bean",2,168,"Starbucks ® carticati coffee beans",R.mipmap.store_coffebean);

        db.execSQL("CREATE TABLE SHOP(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"NAME TEXT,"
                +"PRICE INTEGER,"
                +"NUM INTEGER,"
                +"IMAGE_RESOURCE_ID INTEGER,"
                +"NUMPRICE INTEGER);"
        );


    }

    public static void insert(SQLiteDatabase db,String name,int type,int price,String des,int resourceId){
        ContentValues contentValues=new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("TYPE",type);
        contentValues.put("PRICE",price);
        contentValues.put("DESCRIPTION",des);
        contentValues.put("IMAGE_RESOURCE_ID",resourceId);
        long result=db.insert("DRINK",null,contentValues);
        Log.d("sqlite","insert"+name+"_id"+result);

    }

    public static void insertShop(SQLiteDatabase db,String name,int price,int num,int resourceId,int numprice){
        ContentValues contentValues=new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("PERICE",price);
        contentValues.put("NUM",num);
        contentValues.put("IMAGE_RESOURCE_ID",resourceId);
        contentValues.put("NUMPRICE", numprice);
        long result=db.insert("DRINK",null,contentValues);
        Log.d("sqlite","insert"+name+"_id"+result);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
