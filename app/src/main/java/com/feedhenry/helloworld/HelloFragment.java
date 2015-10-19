/**
 * Copyright (c) 2015 FeedHenry Ltd, All Rights Reserved.
 *
 * Please refer to your contract with FeedHenry for the software license agreement.
 * If you do not have a contract, you do not have a license to use this software.
 */
package com.feedhenry.helloworld;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.feedhenry.helloworld_android.R;
import com.feedhenry.sdk.FH;
import com.feedhenry.sdk.FHActCallback;
import com.feedhenry.sdk.FHResponse;
import com.feedhenry.sdk.api.FHCloudRequest;

import org.json.fh.JSONObject;

public class HelloFragment extends Fragment {

    private static final String TAG = InitFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.hello_fragment, null);
        final TextView responseTextView = (TextView) view.findViewById(R.id.cloud_response);

        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                responseTextView.setText("");
                v.setEnabled(false);
                cloudCall(v, responseTextView);
            }
        });

        return view;
    }

    private void cloudCall(final View v, final TextView responseTextView) {
        try {
            JSONObject params = new JSONObject("{hello: 'world'}");

            FHCloudRequest request = FH.buildCloudRequest("hello", "POST", null, params);
            request.executeAsync(new FHActCallback() {
                @Override
                public void success(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - success");
                    v.setEnabled(true);
                    responseTextView.setText(fhResponse.getJson().getString("msg"));
                }

                @Override
                public void fail(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - fail");
                    Log.e(TAG, fhResponse.getErrorMessage(), fhResponse.getError());
                    v.setEnabled(true);
                    responseTextView.setText(fhResponse.getErrorMessage());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e.getCause());
        }
    }

}
