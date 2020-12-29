package bjfu.it.zhanghesu.classdesign_starbuzz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import bjfu.it.zhanghesu.classdesign_starbuzz.R;
import bjfu.it.zhanghesu.classdesign_starbuzz.ShopActivity;


public class ShopFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_shop,container,false);
        startActivity(new Intent(getActivity(), ShopActivity.class));
        return view;
    }

}
