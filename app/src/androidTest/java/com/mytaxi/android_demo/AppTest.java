package com.mytaxi.android_demo;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;

import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.adapters.DriverAdapter;
import com.mytaxi.android_demo.models.Driver;
import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.Map;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;


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
    }




    @Test
    public void test1VerifyLoginToApp() throws Exception {
        Log.i("test1LoginToApp", "###Logging into the App");
        try {
            userdata = UserCredentials.getuserdata();
            System.out.println(userdata);
        }
        catch(Exception e){
            System.out.println("Error"+ e);
        }
    //Sleep Thread for a 1 sec delay app load
        onView(isRoot()).perform(AppTestUtils.waitFor(1000));
        // Type username
        onView(withId(R.id.edt_username)).perform(typeText(userdata.get("username")), closeSoftKeyboard()).check(matches(withText(containsString(userdata.get("username")))));
        //Type password
        onView(isRoot()).perform(AppTestUtils.waitFor(1000));
        onView(withId(R.id.edt_password)).perform(typeText(userdata.get("password")), closeSoftKeyboard()).check(matches(withText(containsString(userdata.get("password")))));;
        // press login button
        onView(isRoot()).perform(AppTestUtils.waitFor(1000));
        onView(withId(R.id.btn_login)).perform(click()).check(matches(withText(containsString("Login"))));
        Log.i("test1LoginToApp", "###Logging into the App Successful");
    }

    @Test
    public void test2SearchDriverAndCall() throws InterruptedException {
        Log.i("test2SearchDriverAndCall", "###Search Driver Name and Call");

        onView(isRoot()).perform(AppTestUtils.waitFor(2000));
        onView(withId(R.id.textSearch)).perform(typeText("sa"));
        onView(isRoot()).perform(AppTestUtils.waitFor(2000));
        //Select Second values as per name input
        onView(withText("Sarah Scott"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .perform(click());

        onView(isRoot()).perform(AppTestUtils.waitFor(2000));
        //click on Call button
        onView(withId(R.id.fab)).perform(click());

        Log.i("test2SearchDriverAndCall", "###Search for Driver name and call Success");
    }








}
