package com.hamada.android.baking;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hamada.android.baking.Adapter.StepPagerAdapter;
import com.hamada.android.baking.Model.Step;


public class StepActivity extends AppCompatActivity  {

    public static final String TAG=StepActivity.class.getSimpleName();
    private Step mBaking;
    private boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);




        if (findViewById(R.id.fragment_container )!=null){

            isTwoPane=true;
            Bundle bundle=new Bundle();
            bundle.putParcelable(StepPagerAdapter.EXTRASTEP,getIntent().getParcelableExtra(StepPagerAdapter.EXTRASTEP));

            StepFragment fragment=new StepFragment();
            fragment.setArguments(bundle);
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment)
                    .commit();

        }else {
            isTwoPane=false;
            Bundle bundle=new Bundle();
            bundle.putParcelable(StepPagerAdapter.EXTRASTEP,getIntent().getParcelableExtra(StepPagerAdapter.EXTRASTEP));

            StepFragment fragment=new StepFragment();
            fragment.setArguments(bundle);

        }




//            FragmentManager fragmentManager=getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment)
//                    .commit();

    }


}
