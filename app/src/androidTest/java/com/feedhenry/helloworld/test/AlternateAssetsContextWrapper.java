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
