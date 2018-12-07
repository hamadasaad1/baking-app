package com.hamada.android.baking;

import android.content.pm.ActivityInfo;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity>mainActivityActivityTestRule=new
            ActivityTestRule<>(MainActivity.class);
    @Test
    public void rcyclerViewTest_Text(){

//        onView(withId(R.id.main_recycler)).check(matches(hasDescendant(withText("Yellow Cake"))));
//        onView(withId(R.id.main_recycler)).check(matches(hasDescendant(withText("Brownies"))));
//        onView(withId(R.id.main_recycler)).check(matches(hasDescendant(withText("Nutella Pie"))));
//        onView(withId(R.id.main_recycler)).check(matches(hasDescendant(withText("Cheesecake"))));
//
//        onView(withId(R.id.main_recycler)).check(matches(hasDescendant(withText("Yellow Cake"))))
//                .perform(click());
//        onView(withId(R.id.main_recycler)).check(matches(hasDescendant(withText("Brownies"))))
//                .perform(click());
//        onView(withId(R.id.main_recycler)).check(matches(hasDescendant(withText("Nutella Pie"))))
//                .perform(click());
//        onView(withId(R.id.main_recycler)).check(matches(hasDescendant(withText("Cheesecake"))))
//                .perform(click());

        mainActivityActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mainActivityActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
