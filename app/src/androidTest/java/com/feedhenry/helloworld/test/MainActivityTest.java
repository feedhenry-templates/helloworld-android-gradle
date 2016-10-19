/**
 * Copyright 2015 Red Hat, Inc., and individual contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feedhenry.helloworld.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;

import com.feedhenry.helloworld.HelloFragment;
import com.feedhenry.helloworld.MainActivity;
import com.feedhenry.helloworld_android.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private long startTime;

    private MainActivity mMainActivity;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        mMainActivity = mActivityRule.getActivity();
        startTime = System.currentTimeMillis();
    }

    @After
    public void tearDown() throws Exception {
        mMainActivity.finish();
    }

    @Test
    public void testPreconditions() {
        assertNotNull(mMainActivity);
    }

    @Test
    public void testActivityCallsFHInitOnStartup() throws IOException {
        Fragment f;
        while (!((f = mMainActivity.getSupportFragmentManager()
                .findFragmentById(R.id.content)) instanceof HelloFragment)) {
            assertTrue("Timeout after 10 seconds", System.currentTimeMillis() - startTime < 10000);
        }

        Assert.assertEquals(HelloFragment.class, f.getClass());
    }

}
