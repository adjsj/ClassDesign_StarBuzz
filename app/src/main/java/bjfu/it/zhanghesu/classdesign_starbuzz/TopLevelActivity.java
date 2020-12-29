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
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TopLevelActivity extends AppCompatActivity {
    private Cursor favoriteCursor;
    private SQLiteDatabase db;
    private int type;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list);
        setupOptionsListView();
        setupFavoriteListView();
    }
    public void onRestart(){
        super.onRestart();
        Cursor newCursor = db.query("DRINK",new String[]{"_id","NAME"},"FAVORITE = 1",null,null,null,null);
        ListView listFavorites = findViewById(R.id.list_favorites);
        CursorAdapter adapter = (CursorAdapter)listFavorites.getAdapter();
        adapter.changeCursor(newCursor);
        favoriteCursor = newCursor;
    }
    public void onDestroy(){
        super.onDestroy();
        favoriteCursor.close();
        db.close();
    }

    private void setupOptionsListView() {
        AdapterView.OnItemClickListener itemClickListener=
                new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> listView,
                                            View itemView,
                                            int position,
                                            long id){
                        Intent intent = new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                        intent.putExtra("position",position);
                        startActivity(intent);
                    }
                };

        ListView listView = findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }
    private void setupFavoriteListView(){
        ListView listFavorites = findViewById(R.id.list_favorites);

        SQLiteOpenHelper starbuzzDatabaseHelper = new DatabaseHelper(this);
        try{
            db = starbuzzDatabaseHelper.getReadableDatabase();
            favoriteCursor = db.query("DRINK",new String[]{"_id","NAME"},"FAVORITE = 1",null,null,null,null);

            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(TopLevelActivity.this,android.R.layout.simple_list_item_1,favoriteCursor,new String[]{"NAME"},new int[]{android.R.id.text1},0);
            listFavorites.setAdapter(favoriteAdapter);
        }catch(SQLiteException e){
            Log.e("sqlite",e.getMessage());
            Toast toast = Toast.makeText(this,"Database unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }

        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> listView,View v,int position,long id){
                Intent intent = new Intent(TopLevelActivity.this,DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID,(int)id);
                startActivity(intent);
            }
        });
    }

}
