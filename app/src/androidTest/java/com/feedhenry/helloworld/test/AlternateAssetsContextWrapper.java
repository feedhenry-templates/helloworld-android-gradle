package com.feedhenry.helloworld.test;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.ContextThemeWrapper;

public class AlternateAssetsContextWrapper extends ContextThemeWrapper {

    private final Context assetsContext;

    public AlternateAssetsContextWrapper(Context targetContext, int theme, Context assetsContext) {
        super(targetContext, theme);
        this.assetsContext = assetsContext;
    }

    @Override
    public AssetManager getAssets() {
        return assetsContext.getAssets();
    }

}
