/**
 * Copyright (c) 2015 FeedHenry Ltd, All Rights Reserved.
 *
 * Please refer to your contract with FeedHenry for the software license agreement.
 * If you do not have a contract, you do not have a license to use this software.
 */
package com.feedhenry.helloworld.test;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.ContextThemeWrapper;

/**
 * This class allows Android to use a theme from teh Applicaiton Context while using assets from the
 * instrumentation context
 */
public class AlternateAssetsContextWrapper extends ContextThemeWrapper {

    private final Context assetsContext;

    public AlternateAssetsContextWrapper(Context base, int themeres, Context assetsContext) {
        super(base, themeres);
        this.assetsContext = assetsContext;
    }

    @Override
    public AssetManager getAssets() {
        return assetsContext.getAssets();
    }
}
