package com.ibao.premezcla;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    String TAG  = ExampleInstrumentedTest.class.getSimpleName();
    @Test
    public void useAppContext() {
        // cBaseApplication of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        System.out.println("packageName: "+appContext.getPackageName());
        Log.d(TAG,"packageName: "+appContext.getPackageName());
        assertEquals("com.ibao.premezcla", appContext.getPackageName());
    }
}
