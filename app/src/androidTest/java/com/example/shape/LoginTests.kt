package com.example.shape

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LoginTests {

    @get:Rule
    val mActivityRule: ActivityTestRule<ActivityLogin> = ActivityTestRule(ActivityLogin::class.java)


    @Test
    fun login_isSuccess(){
        onView(withId(R.id.emailLogEditText)).perform(ViewActions.typeText("elena@yandex.ru"))
        onView(withId(R.id.passwordLogEditText)).perform(ViewActions.typeText("sdfghjkla1234"),  closeSoftKeyboard())
        onView(withId(R.id.buttonNextPageAfterLogin)).perform(click())
        onView(withId(R.id.nameProfileText)).check(matches(withText("elena")))
    }


    @Test
    fun login_notSuccess(){
        onView(withId(R.id.emailLogEditText)).perform(ViewActions.typeText("test@yandex.ru"))
        onView(withId(R.id.passwordLogEditText)).perform(ViewActions.typeText("test1122341234"),  closeSoftKeyboard())
        onView(withId(R.id.buttonNextPageAfterLogin)).perform(click())
        onView(withId(R.id.buttonExitProfile)).check(doesNotExist())
    }
}