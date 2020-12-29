package bjfu.it.zhanghesu.classdesign_starbuzz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKID = "drinkId";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_selected);

        int drinkId = getIntent().getIntExtra(EXTRA_DRINKID,0);

        SQLiteOpenHelper starbuzzDatabaseHelper = new DatabaseHelper(this);
        try(SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase()){
            //读数据库
            Cursor cursor = db.query("DRINK",new String[]{"NAME","PRICE","DESCRIPTION","IMAGE_RESOURCE_ID","FAVORITE"},
                    "_id=?",
                    new String[]{Integer.toString(drinkId)},
                    null,null,null);

            if(cursor.moveToFirst()){
                //取出记录
                String nameText = cursor.getString(0);
                int priceText=cursor.getInt(1);
                String descriptionText = cursor.getString(2);
                int photoId = cursor.getInt(3);
                boolean isFavorite = (cursor.getInt(4)==1);
                //显示name
                TextView name = findViewById(R.id.name);
                name.setText(nameText);
                //显示description
                TextView description = findViewById(R.id.description);
                description.setText(descriptionText);
                //显示图片
                ImageView photo = findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
                //显示收藏
                CheckBox favorite = findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);
                //显示价格
                TextView price=findViewById(R.id.price);
                price.setText("RMB:"+priceText);
            }
            cursor.close();
        }catch (SQLiteException e){
            Log.e("sqlite",e.getMessage());
            Toast toast = Toast.makeText(this,"Database unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    private class UpdateDrinkTask extends AsyncTask<Integer,Void,Boolean> {
        private ContentValues drinkValues;

        protected void onPreExecute(){
            CheckBox favorite = findViewById(R.id.favorite);
            drinkValues = new ContentValues();
            drinkValues.put("FAVORITE",favorite.isChecked());
        }

        protected Boolean doInBackground(Integer... drinks){
            int drinkId = drinks[0];
            SQLiteOpenHelper starbuzzDatabaseHelper = new DatabaseHelper(DrinkActivity.this);
            try(SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase()){
                db.update("DRINK",drinkValues,"_id=?",new String[]{Integer.toString(drinkId)});
                return true;
            }catch(SQLiteException e){
                Log.e("sqlite",e.getMessage());
                return false;
            }
        }

        protected void onPostExecute(Boolean success){
            if(!success){
                Toast toast = Toast.makeText(DrinkActivity.this,"Database unavailable",Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }


    public void onFavoriteClicked(View view) {

        int drinkId =getIntent().getIntExtra(EXTRA_DRINKID,0);
        new UpdateDrinkTask().execute(drinkId);

    }

    public void addToShopClick(View view){
        int drinkId =getIntent().getIntExtra(EXTRA_DRINKID,0);
        SQLiteOpenHelper starbuzzDatabaseHelper = new DatabaseHelper(this);
        try(SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase()){
            //读数据库
            Cursor cursor = db.query("DRINK",new String[]{"NAME","PRICE","DESCRIPTION","IMAGE_RESOURCE_ID","FAVORITE"},
                    "_id=?",
                    new String[]{Integer.toString(drinkId)},
                    null,null,null);
            ContentValues contentValues=new ContentValues();

            if(cursor.moveToFirst()){
                //取出记录
                String nameText = cursor.getString(0);
                int priceText=cursor.getInt(1);
                String descriptionText = cursor.getString(2);
                int photoId = cursor.getInt(3);
                boolean isFavorite = (cursor.getInt(4)==1);
                //显示name
                contentValues.put("NAME",nameText);
                //插入价格
                contentValues.put("PRICE",priceText);
                //显示图片
                contentValues.put("IMAGE_RESOURCE_ID",photoId);
                //显示收藏
                contentValues.put("NUM",1);
                //显示价格
                contentValues.put("NUMPRICE",priceText);
                long result=db.insert("SHOP",null,contentValues);
                Log.d("sqlite","insert"+nameText+"_id"+result);
            }
            cursor.close();
        }catch (SQLiteException e){
            Log.e("sqlite",e.getMessage());
            Toast toast = Toast.makeText(this,"Database unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
