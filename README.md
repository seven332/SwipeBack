# SwipeBack

本项目基于 [SwipeBackLayout](https://github.com/ikew0ng/SwipeBackLayout)。

一划就能关掉 Activity。

Forked from [SwipeBackLayout](https://github.com/ikew0ng/SwipeBackLayout).

Swipe to finish Activity.


# Usage

## Add dependency

在最外面的 `build.gradle` 里加上 jitpack，别加到 buildscript 里了。

Add jitpack repository in top `build.gradle`, DO **NOT** ADD IT TO buildscript.

    allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }

在项目 `build.gradle` 里添加 Image 依赖。

Add SwipeBack as dependency in project `build.gradle`.

    dependencies {
        ...
        compile 'com.github.seven332:swipeback:0.1.0'
    }

## Code

首先要实现 SwipeBackActivity，可以按照自己的要求继承 Activity 或者 AppCompatActivity 或者其他 Activity。

    public abstract class SwipeBackActivity extends Activity implements SwipeBackActivityImpl {
    
        private SwipeBackHelper mSwipeBackHelper;
    
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mSwipeBackHelper = new SwipeBackHelper(this);
        }
    
        @Override
        protected void onPostCreate(@Nullable Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);
            mSwipeBackHelper.onPostCreate();
        }
    
        @Override
        public SwipeBackLayout getSwipeBackLayout() {
            return mSwipeBackHelper.getSwipeBackLayout();
        }
    }

SwipeBackActivity 一被滑动，下一层的 Activity 就应该被显示出来，所以 SwipeBackActivity 的 windowIsTranslucent 属性应为真。但是由于只有 windowBackground 为透明时，才能真正看到下一层的 Activity，我添加了 sb_windowBackground 属性，这是实际会显示的背景，而 windowBackground 会被忽略。所以 SwipeBackActivity 的主题应有如下属性：

    <item name="sb_windowBackground">ACTIVITY_BACKGROUND</item>
    <item name="android:windowBackground">@android:color/transparent</item>
    <item name="android:colorBackgroundCacheHint">@null</item>
    <item name="android:windowIsTranslucent">true</item>

SwipeBackLayout 的具体使用方法可参照 [这里](app/src/main/java/com/hippo/swipeback/example/TestActivity.java)。


# License

    Copyright 2016 Hippo Seven

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
