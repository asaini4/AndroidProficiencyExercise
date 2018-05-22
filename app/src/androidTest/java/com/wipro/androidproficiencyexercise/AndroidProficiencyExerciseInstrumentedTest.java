package com.wipro.androidproficiencyexercise;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.wipro.androidproficiencyexercise.view.AndroidExerciseMainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class AndroidProficiencyExerciseInstrumentedTest {

    private static final int ITEM_BELOW_THE_FOLD = 5;

    @Rule
    public ActivityTestRule<AndroidExerciseMainActivity> mActivityRule =
            new ActivityTestRule(AndroidExerciseMainActivity.class);

    @Test
    public void checkAppBarTest() throws Exception {

        onView(withId(R.id.imgNotifySync))
                .perform(click());

        Thread.sleep(6000);

        onView(withId(R.id.title_text)).check(matches(withText("About Canada")));
    }

    @Test
    public void scrollToItemBelowFold_checkItsText() {

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_BELOW_THE_FOLD, click()));

        // Match the text in an item below the fold and check that it's displayed.
        String itemElementText = "Eh";
        onView(withText(itemElementText)).check(matches(isDisplayed()));
    }

    @Test
    public void recyclerViewItemPositionTest() {
        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Match the correct position of item of recyclerView
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
    }

}

