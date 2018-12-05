package com.hamada.android.baking;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.hamada.android.baking.Adapter.StepPagerAdapter;



public class StepActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);


        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(StepPagerAdapter.EXTRASTEP, getIntent().getParcelableExtra(StepPagerAdapter.EXTRASTEP));

            StepFragment fragment = new StepFragment();
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment)
                    .commit();

        }


    }



}
