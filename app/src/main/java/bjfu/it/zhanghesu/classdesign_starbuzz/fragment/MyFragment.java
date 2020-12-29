package bjfu.it.zhanghesu.classdesign_starbuzz.fragment;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bjfu.it.zhanghesu.classdesign_starbuzz.R;
import bjfu.it.zhanghesu.classdesign_starbuzz.util.DataUtil;

public class MyFragment extends Fragment {
    private List<Map<String,Object>> data=new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_my,container,false);
        ListView menuListView=view.findViewById(R.id.menu_listView);
        initData();
        String[] from={"img","txt"};
        int[] to={R.id.menu_item_img,R.id.menu_item_txt};
        SimpleAdapter adapter=new SimpleAdapter(getContext(),data,R.layout.my_menu_item,from,to);
        menuListView.setAdapter(adapter);
        return view;
    }

    private void initData() {
        for(int i=0;i<6;i++){
            Map<String,Object> map=new HashMap<>();
            map.put("img", DataUtil.my_imgs[i]);
            map.put("txt",DataUtil.my_txts[i]);
            data.add(map);
        }
    }
}
