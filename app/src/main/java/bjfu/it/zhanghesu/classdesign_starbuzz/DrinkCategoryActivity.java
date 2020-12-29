package bjfu.it.zhanghesu.classdesign_starbuzz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DrinkCategoryActivity extends AppCompatActivity {
    private Cursor cursor;

    public void onDestroy(){
        super.onDestroy();
        cursor.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_list);
        ListView listDrinks = findViewById(R.id.list_drinks);
        SQLiteOpenHelper starbuzzDatabaseHelper = new DatabaseHelper(this);
        try (SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase()) {
            Intent intent=getIntent();
            if(intent.getIntExtra("position",0) == 0){
                cursor = db.query("DRINK",
                        new String[]{"_id", "NAME","IMAGE_RESOURCE_ID"},
                        "TYPE = 0", null, null, null, null);
            }if(intent.getIntExtra("position",0) == 1){
                cursor = db.query("DRINK",
                        new String[]{"_id", "NAME","IMAGE_RESOURCE_ID"},
                        "TYPE = 1", null, null, null, null);
            }if(intent.getIntExtra("position",0) == 2){
                cursor = db.query("DRINK",
                        new String[]{"_id", "NAME","IMAGE_RESOURCE_ID"},
                        "TYPE = 2", null, null, null, null);
            }
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1}, 0);
            listDrinks.setAdapter(listAdapter);

        } catch (SQLiteException e) {
            Log.e("sqlite", e.getMessage());
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listDrinks, View view, int position, long id) {
                Intent intent=new Intent(DrinkCategoryActivity.this,DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID,(int)id);
                startActivity(intent);

            }
        };
        listDrinks.setOnItemClickListener(itemClickListener);
    }
}
