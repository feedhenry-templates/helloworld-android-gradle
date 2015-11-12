/**
 * Copyright (c) 2015 FeedHenry Ltd, All Rights Reserved.
 *
 * Please refer to your contract with FeedHenry for the software license agreement.
 * If you do not have a contract, you do not have a license to use this software.
 */
package com.feedhenry.helloworld.test;


import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.test.ActivityUnitTestCase;
import android.util.Log;
import android.view.ContextThemeWrapper;

import com.feedhenry.helloworld.HelloFragment;
import com.feedhenry.helloworld.MainActivity;
import com.feedhenry.helloworld_android.R;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {



    private MockWebServer mockWebServer = null;
    private long startTime;
    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mockWebServer = new MockWebServer();
        mockWebServer.start(9000);
        ContextThemeWrapper context = new AlternateAssetsContextWrapper(getInstrumentation().getTargetContext(), R.style.MyTheme_Base, getInstrumentation().getContext());
        setActivityContext(context);
        startTime = System.currentTimeMillis();
    }


    public void testActivityCallsFHInitOnStartup() throws IOException {
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                MainActivity activity = startActivity(new Intent(), Bundle.EMPTY, null);
                activity.onStart();
            }
        });


        MainActivity main = getActivity();


        Fragment f;
        while (!((f = main.getSupportFragmentManager().findFragmentById(R.id.content)) instanceof HelloFragment)) {
            assertTrue("Timeout after 5 seconds", System.currentTimeMillis() - startTime < 5000);
        }

        Assert.assertEquals(HelloFragment.class, f.getClass());

        main.finish();

    }

}
