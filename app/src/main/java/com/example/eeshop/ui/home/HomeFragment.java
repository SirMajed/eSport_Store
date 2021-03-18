package com.example.eeshop.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asksira.loopingviewpager.LoopingViewPager;
import com.example.eeshop.Adapter.MyBestDealAdapter;
import com.example.eeshop.Adapter.MyPopularCategoriesAdapter;
import com.example.eeshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    Unbinder unbinder;

    //For popular categories
    @BindView(R.id.recycler_popular)
    RecyclerView recycler_popular;
    //For best deals
    @BindView(R.id.viewpager)
    LoopingViewPager viewPager; // عشان البست ديلز تروح من اليمين لليسار ( انميشن )
    /////////

    LayoutAnimationController layoutAnimationController;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this,root);
        init();
        homeViewModel.getPopularList().observe(this,popularCategoryModels -> { // يجيب الببيلر ايتمز

            //Create Adapter
            MyPopularCategoriesAdapter adapter = new MyPopularCategoriesAdapter(getContext(),popularCategoryModels);
            recycler_popular.setAdapter(adapter);
            //recycler_popular.setLayoutAnimation(layoutAnimationController); // هنا بيخلي الانميشن يجي للكاتيقوري

        });

        homeViewModel.getBestDealList().observe(this,bestDealModels -> { // عشان نجيب افضل العروض
            MyBestDealAdapter adapter = new MyBestDealAdapter(getContext(),bestDealModels,true);
            viewPager.setAdapter(adapter);

        });
        return root;
    }

    private void init() {
        // السطر الي تحت عشان نعرف الانميشن
       // layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_item_from_left);
        recycler_popular.setHasFixedSize(true);
        recycler_popular.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.resumeAutoScroll();
    }

    @Override
    public void onPause() {
        viewPager.pauseAutoScroll();
        super.onPause();
    }
}
