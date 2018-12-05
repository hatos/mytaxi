package com.mytaxi.android_demo;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;

import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.models.Driver;
import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.Map;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;


@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest {
    Map<String,String> userdata=new HashMap<>();

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setActivity() {
        mActivity = activityTestRule.getActivity();
    }
    @Before
    public void getUserCredentials() throws Exception{
        //clear app data if logged in previously
        Context context = getInstrumentation().getTargetContext();
        SharedPrefStorage pref = new SharedPrefStorage(context);
        pref.resetUser();
        //Allow user to perform phone call
        getInstrumentation().getUiAutomation().executeShellCommand(
                "pm grant " + getTargetContext().getPackageName()
                        + " android.permission.CALL_PHONE");

        userdata=UserCredentials.getuserdata();
        System.out.println(userdata);

    }


    @Test
    public void test1VerifyLoginToApp() throws InterruptedException {
        Log.i("test1LoginToApp", "###Logging into the App");
    //Sleep Thread for a 1 sec delay app load
        Thread.sleep(1000);
        // Type username
        onView(withId(R.id.edt_username)).perform(typeText(userdata.get("username")), closeSoftKeyboard());
        //Wait for 1 sec before typing password
        Thread.sleep(1000);
        //Type password
        onView(withId(R.id.edt_password)).perform(typeText(userdata.get("password")), closeSoftKeyboard());
        // press login button
        onView(withId(R.id.btn_login)).perform(click());
        //Wait for 1 sec and perform next action
        Thread.sleep(1000);
        Log.i("test1LoginToApp", "###Logging into the App Successful");
    }

    @Test
    public void test2SearchDriverAndCall() throws InterruptedException {
        Log.i("test2SearchDriverAndCall", "###Search Driver Name and Call");

        Thread.sleep(1000);
        onView(withId(R.id.textSearch)).perform(typeText("sa"));
        Thread.sleep(2000);
        //Select Second values as per name input
        onView(withText("Sarah Scott"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .perform(click());
        Thread.sleep(2000);
        //click on Call button
        onView(withId(R.id.fab)).perform(click());
       //
        Log.i("test2SearchDriverAndCall", "###Search for Driver name and call Success");
    }


    public static Matcher withDriverName(final String driverName) {
        return new TypeSafeMatcher<Driver>() {
            @Override
            public void describeTo(Description description) {

            }

            @Override
            protected boolean matchesSafely(Driver driver) {
                return driver.getName().equalsIgnoreCase(driverName);
            }
        };
    }




    @After
    public void clearAppData(){

    }



}
