package bjfu.it.zhanghesu.classdesign_starbuzz;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopActivity extends AppCompatActivity {
    private Cursor shopCursor;

    private List<Map<String,Object>> data=new ArrayList<>();
    private SQLiteDatabase db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_shop);
        ListView listView=findViewById(R.id.shop_listView);
        initData();
        String[] from={"NAME","PRICE","NUM","IMAGE_RESOURCE_ID","NUMPRICE"};
        int[] to={R.id.item_name,R.id.item_price,R.id.item_num,R.id.item_img,R.id.item_allPrice};

        SimpleAdapter adapter=new SimpleAdapter(this,data,R.layout.shop_listview,from,to);


        listView.setAdapter(adapter);


    }

    private void initData() {
        SQLiteOpenHelper starbuzzDatabaseHelper = new DatabaseHelper(this);
        try{
            db = starbuzzDatabaseHelper.getReadableDatabase();
            shopCursor = db.query("SHOP",new String[]{"NAME","PRICE","NUM","IMAGE_RESOURCE_ID INTEGER","NUMPRICE"},null,null,null,null,null);
            if(shopCursor.getCount()>0){
                while(shopCursor.moveToNext()){
                    String name=shopCursor.getString(0);
                    int price=shopCursor.getInt(1);
                    int num=shopCursor.getInt(2);
                    int imgId=shopCursor.getInt(3);
                    int numPrice=shopCursor.getInt(4);
                    Map<String,Object> map=new HashMap<>();
                    map.put("NAME",name);
                    map.put("PRICE",price);
                    map.put("NUM",num);
                    map.put("IMAGE_RESOURCE_ID",imgId);
                    map.put("NUMPRICE",numPrice);
                    data.add(map);
                }
            }

        }catch(SQLiteException e){
            Log.e("sqlite",e.getMessage());
            Toast toast = Toast.makeText(this,"Database unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }


    }


}
