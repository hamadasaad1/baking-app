package com.hamada.android.baking;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsTest {

    @Rule
    public ActivityTestRule<DetailsViewPagerActivity> mDetailsActivityActivityTestRule=
            new ActivityTestRule<>(DetailsViewPagerActivity.class);


    @Before
    public void init(){
        mDetailsActivityActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }
    @Test
    public void recipeActivityTabletTest() {
        onView(ViewMatchers.withId(R.id.item_details_container_fragment))
                .check(matches(isDisplayed()));
    }

    @Test
    public void TestFab(){
        onView(withId(R.id.fab)).perform(click());
        onView(allOf(withId(android.support.design.R.id.snackbar_text)
                , withText("This Test Works")))
                .check(matches(isDisplayed()));
    }
}
