package android.edikurniawan.me.bakingapplication;

import android.edikurniawan.me.bakingapplication.controller.activity.MainActivity;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import timber.log.Timber;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Context of the app under test.
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            onView(withId(R.id.recyclerView_recipe))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));

        } catch (NoMatchingViewException e) {
            // View is not in hierarchy
            Timber.e("ERROR : "+e.getMessage());
        }

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            onView(withId(R.id.button_next))
                    .perform(click());
            // View is in hierarchy
        } catch (NoMatchingViewException e) {
            // View is not in hierarchy
        }

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            onView(withId(R.id.button_previous))
                    .perform(click());
            // View is in hierarchy
        } catch (NoMatchingViewException e) {
            // View is not in hierarchy
        }

    }



}
