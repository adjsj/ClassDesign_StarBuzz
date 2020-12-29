package bjfu.it.zhanghesu.classdesign_starbuzz;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentTransaction;
import bjfu.it.zhanghesu.classdesign_starbuzz.fragment.*;
import android.os.Bundle;
import android.view.View;

import bjfu.it.zhanghesu.classdesign_starbuzz.fragment.IndexFragment;

public class MainActivity extends AppCompatActivity {
    private IndexFragment index;
    private ListFragment list;
    private MyFragment my;
    private ShopFragment shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        index=new IndexFragment();
        list=new ListFragment();
        my=new MyFragment();
        shop=new ShopFragment();

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container,index) ;
        transaction.commit();


    }

    public void indexClick(View v){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        switch (v.getId()){
            case R.id.index:
                transaction.replace(R.id.container,new IndexFragment());

                break;
            case R.id.list:
                transaction.replace(R.id.container,new ListFragment());

                break;

            case R.id.shop:
                transaction.replace(R.id.container,new ShopFragment());

                break;
            case R.id.my:
                transaction.replace(R.id.container,new MyFragment());

                break;
        }
        transaction.commit();
    }
}
