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
package com.feedhenry.helloworld;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.feedhenry.helloworld_android.R;
import com.feedhenry.sdk.FH;
import com.feedhenry.sdk.FHActCallback;
import com.feedhenry.sdk.FHResponse;
import com.feedhenry.sdk.api.FHCloudRequest;
import com.feedhenry.sdk.exceptions.FHNotReadyException;
import org.json.fh.JSONObject;

public class HelloFragment extends Fragment {

    private static final String TAG = InitFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = View.inflate(getActivity(), R.layout.hello_fragment, null);

        final TextView mResponse = (TextView) view.findViewById(R.id.cloud_response);
        final EditText mName = (EditText) view.findViewById(R.id.name);
        final Button mCloudButton = (Button) view.findViewById(R.id.button);
        mCloudButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mResponse.setText("");
                mCloudButton.setEnabled(false);
                cloudCall(v, mName.getText().toString(), mResponse);
                mName.setText("");
            }
        });

        return view;
    }

    private void cloudCall(final View button, final String name, final TextView response) {
        try {
            JSONObject params = new JSONObject();
            params.put("hello", name);  // { 'hello': $name }

            FHCloudRequest request = FH.buildCloudRequest("hello", "POST", null, params);
            request.executeAsync(new FHActCallback() {
                @Override
                public void success(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - success");
                    button.setEnabled(true);
                    response.setText(fhResponse.getJson().getString("msg"));
                }

                @Override
                public void fail(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - fail");
                    Log.e(TAG, fhResponse.getErrorMessage(), fhResponse.getError());
                    button.setEnabled(true);
                    response.setText(fhResponse.getErrorMessage());
                }
            });
        } catch (FHNotReadyException e) {
            Log.e(TAG, e.getMessage(), e.getCause());
            button.setEnabled(true);
            response.setText(e.getMessage());
        }
    }

}
