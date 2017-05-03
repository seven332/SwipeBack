/*
 * Copyright 2016 Hippo Seven
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

package com.hippo.swipeback.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hippo.swipeback.SwipeBackLayout;

public class TestActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSwipeBackLayout().setSwipeEdge(SwipeBackLayout.EDGE_LEFT);

        final CheckBox swipeEnabled = (CheckBox) findViewById(R.id.swipe_enabled);
        swipeEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getSwipeBackLayout().setSwipeEnabled(isChecked);
            }
        });

        final RadioGroup swipeEdges = (RadioGroup) findViewById(R.id.swipe_edge);
        swipeEdges.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.swipe_edge_none:
                        getSwipeBackLayout().setSwipeEdge(SwipeBackLayout.EDGE_NONE);
                        break;
                    case R.id.swipe_edge_left:
                        getSwipeBackLayout().setSwipeEdge(SwipeBackLayout.EDGE_LEFT);
                        break;
                    case R.id.swipe_edge_right:
                        getSwipeBackLayout().setSwipeEdge(SwipeBackLayout.EDGE_RIGHT);
                        break;
                    case R.id.swipe_edge_both:
                        getSwipeBackLayout().setSwipeEdge(SwipeBackLayout.EDGE_LEFT | SwipeBackLayout.EDGE_RIGHT);
                        break;
                }
            }
        });

        final TextView swipePercent = (TextView) findViewById(R.id.swipe_percent);
        final TextView swipeTrackingEdge = (TextView) findViewById(R.id.swipe_tracking_edge);
        final TextView swipeState = (TextView) findViewById(R.id.swipe_state);
        getSwipeBackLayout().addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onSwipe(float percent) {
                swipePercent.setText("Swipe Percent : " + String.format("%.2f", percent));
            }

            @Override
            public void onStateChange(int edge, int state) {
                swipeTrackingEdge.setText("Tracking Edge: " + edge);
                swipeState.setText("Swipe State: " + state);
            }

            @Override
            public void onSwipeOverThreshold() {}

            @Override
            public void onFinish() {}
        });

        findViewById(R.id.swipe_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSwipeBackLayout().swipeToFinish(SwipeBackLayout.EDGE_LEFT);
            }
        });

        findViewById(R.id.swipe_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSwipeBackLayout().swipeToFinish(SwipeBackLayout.EDGE_RIGHT);
            }
        });

        findViewById(R.id.new_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestActivity.this, TestActivity.class));
            }
        });
    }
}
