/**
 * Copyright 2015 Red Hat, Inc., and individual contributors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feedhenry.helloworld.test;

import android.content.ContextWrapper;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;

import com.feedhenry.helloworld.HelloFragment;
import com.feedhenry.helloworld.MainActivity;
import com.feedhenry.helloworld_android.R;
import com.feedhenry.sdk.FH;
import com.feedhenry.sdk.FHActCallback;
import com.feedhenry.sdk.FHResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private CountDownLatch latch;
    private ContextWrapper ctx;
    private long startTime;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        latch = new CountDownLatch(1);
        ctx = new AlternateAssetsContextWrapper(
                getInstrumentation().getTargetContext(),
                R.style.MyTheme, getInstrumentation().getContext());

        FH.init(ctx, new FHActCallback() {
            @Override
            public void success(FHResponse pResponse) {
                latch.countDown();
            }

            @Override
            public void fail(FHResponse pResponse) {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);

        startTime = System.currentTimeMillis();
    }

    @Test
    public void testActivityCallsFHInitOnStartup() throws IOException {

        MainActivity activity = mActivityRule.launchActivity(new Intent(ctx, MainActivity.class));

        Fragment f;
        while (!((f = activity.getSupportFragmentManager()
                .findFragmentById(R.id.content)) instanceof HelloFragment)) {
            assertTrue("Timeout after 10 seconds", System.currentTimeMillis() - startTime < 10000);
        }

        Assert.assertEquals(HelloFragment.class, f.getClass());
    }

}
