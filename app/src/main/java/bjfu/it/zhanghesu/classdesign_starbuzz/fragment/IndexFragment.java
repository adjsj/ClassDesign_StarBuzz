package bjfu.it.zhanghesu.classdesign_starbuzz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bjfu.it.zhanghesu.classdesign_starbuzz.R;
import bjfu.it.zhanghesu.classdesign_starbuzz.util.DataUtil;

public class IndexFragment extends Fragment {
    private ViewPager2 pagers;
    private LinearLayout pointers;
    private int index=1;
    private GridView grids;
    private List<Map<String,Object>> data=new ArrayList<>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_index,container,false);
        pagers=view.findViewById(R.id.pagers);
        pointers=view.findViewById(R.id.pointers);
        grids = view.findViewById(R.id.grids);
        setViewPager();
        setGridView();
        return view;
    }

    private void setGridView() {
        for(int i=0;i<DataUtil.index_menu_imgs.length;i++){
            Map<String,Object> map=new HashMap<>();
            map.put("img",DataUtil.index_menu_imgs[i]);
            map.put("txt",DataUtil.index_menu_txts[i]);
            data.add(map);
        }
        String[] from={"img","txt"};
        int[] to={R.id.img,R.id.txt};
        SimpleAdapter adapter=new SimpleAdapter(getContext(),data,R.layout.index_menu_item,from,to);
        grids.setAdapter(adapter);
    }

    private void setViewPager() {
        RecyclerView.Adapter adapter=new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v=LayoutInflater.from(getContext()).inflate(R.layout.adv_item,parent,false);
                return new ViewHolder(v);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ViewHolder vh=(ViewHolder)holder;
                vh.v.setBackgroundResource(DataUtil.adv_imgs[position%4]);
            }

            @Override
            public int getItemCount() {
                return Integer.MAX_VALUE;
            }
        };

        pagers.setAdapter(adapter);

        pagers.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                for(int i=0;i<pointers.getChildCount();i++){
                    ImageView img=(ImageView)pointers.getChildAt(i);
                    img.setImageResource(R.drawable.dot_unselected);

                }
                ((ImageView)pointers.getChildAt(position%4)).setImageResource(R.drawable.dot_selected);

            }
        });

        new Thread(){
            public void run(){
                super.run();
                while(true){
                    try{
                        Thread.sleep(3000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    pagers.setCurrentItem(index );
                    index++;
                }
            }
        }.start();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public View v;
        public ViewHolder(View view){
            super(view);
            v=view;
        }

    }

}