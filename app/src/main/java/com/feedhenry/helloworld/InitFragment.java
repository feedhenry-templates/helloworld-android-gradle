package com.feedhenry.helloworld;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feedhenry.helloworld_android.R;
import com.feedhenry.sdk.FH;
import com.feedhenry.sdk.FHActCallback;
import com.feedhenry.sdk.FHResponse;

public class InitFragment extends Fragment {

    private static final String TAG = InitFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.init_fragment, null);

        FH.init(getActivity(), new FHActCallback() {
            @Override
            public void success(FHResponse fhResponse) {
                Log.d(TAG, "init - success");
                MainActivity activity = (MainActivity) getActivity();
                activity.showHelloScreen();
            }

            @Override
            public void fail(FHResponse fhResponse) {
                Log.d(TAG, "init - fail");
                Log.e(TAG, fhResponse.getErrorMessage(), fhResponse.getError());
            }
        });

        return view;
    }

}
