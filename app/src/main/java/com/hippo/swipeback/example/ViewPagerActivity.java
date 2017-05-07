/*
 * Copyright 2017 Hippo Seven
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

/*
 * Created by Hippo on 5/7/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hippo.swipeback.SwipeBackLayout;

public class ViewPagerActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pager);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
    ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

    viewPager.setAdapter(new PagerAdapter() {
      @Override
      public int getCount() {
        return 3;
      }

      @Override
      public CharSequence getPageTitle(int position) {
        return Integer.toString(position);
      }

      @Override
      public Object instantiateItem(final ViewGroup container, int position) {
        final View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_pager, container, false);
        final SwipeBackLayout layout = (SwipeBackLayout) view.findViewById(R.id.swipe_back);
        final TextView textView = (TextView) layout.findViewById(R.id.text);
        container.addView(view);

        if (position == 1) {
          layout.setSwipeEdge(SwipeBackLayout.EDGE_LEFT);
          layout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onSwipe(float percent) {}
            @Override
            public void onStateChange(int edge, int state) {}
            @Override
            public void onSwipeOverThreshold() {}
            @Override
            public void onFinish() {
              container.removeView(layout);
            }
          });
          textView.setText("Swipe the left edge");
        } else {
          textView.setText("Page " + position);
        }
        return view;
      }

      @Override
      public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
      }

      @Override
      public boolean isViewFromObject(View view, Object object) {
        return view == object;
      }
    });

    tabLayout.setupWithViewPager(viewPager);
  }
}
