/*
 * Copyright (C) 2017 RoM ROM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.six.settings;

import android.app.Activity;
import android.content.Context;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.app.Fragment;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v14.preference.SwitchPreference;
import android.provider.Settings;
import com.android.settings.SettingsPreferenceFragment;
import com.android.internal.logging.nano.MetricsProto;

import com.android.internal.util.six.AwesomeAnimationHelper;

import com.android.settings.R;

public class AnimationSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String ACTIVITY_OPEN = "activity_open";
    private static final String ACTIVITY_CLOSE = "activity_close";
    private static final String TASK_OPEN = "task_open";
    private static final String TASK_CLOSE = "task_close";
    private static final String TASK_MOVE_TO_FRONT = "task_move_to_front";
    private static final String TASK_MOVE_TO_BACK = "task_move_to_back";
    private static final String ANIMATION_NO_OVERRIDE = "animation_no_override";
    private static final String WALLPAPER_OPEN = "wallpaper_open";
    private static final String WALLPAPER_CLOSE = "wallpaper_close";
    private static final String WALLPAPER_INTRA_OPEN = "wallpaper_intra_open";
    private static final String WALLPAPER_INTRA_CLOSE = "wallpaper_intra_close";
    private static final String KEY_TOAST_ANIMATION = "toast_animation";
    private static final String KEY_LISTVIEW_ANIMATION = "listview_animation";
    private static final String KEY_LISTVIEW_INTERPOLATOR = "listview_interpolator";

    ListPreference mActivityOpenPref;
    ListPreference mActivityClosePref;
    ListPreference mTaskOpenPref;
    ListPreference mTaskClosePref;
    ListPreference mTaskMoveToFrontPref;
    ListPreference mTaskMoveToBackPref;
    ListPreference mWallpaperOpen;
    ListPreference mWallpaperClose;
    ListPreference mWallpaperIntraOpen;
    ListPreference mWallpaperIntraClose;
    SwitchPreference mAnimNoOverride;
    private ListPreference mToastAnimation;
    private ListPreference mListViewAnimation;
    private ListPreference mListViewInterpolator;


    private int[] mAnimations;
    private String[] mAnimationsStrings;
    private String[] mAnimationsNum;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.six_animation_settings);

        final ContentResolver resolver = getContentResolver();
        final Resources res = getResources();
        PreferenceScreen prefs = getPreferenceScreen();

        mAnimations = AwesomeAnimationHelper.getAnimationsList();
        int animqty = mAnimations.length;
        mAnimationsStrings = new String[animqty];
        mAnimationsNum = new String[animqty];
        for (int i = 0; i < animqty; i++) {
            mAnimationsStrings[i] = AwesomeAnimationHelper.getProperName(mContext, mAnimations[i]);
            mAnimationsNum[i] = String.valueOf(mAnimations[i]);
        }

        //mAnimNoOverride = (SwitchPreference) findPreference(ANIMATION_NO_OVERRIDE);
        //mAnimNoOverride.setChecked(Settings.System.getBoolean(mContentRes,
        //        Settings.System.ANIMATION_CONTROLS_NO_OVERRIDE, false));

        mActivityOpenPref = (ListPreference) findPreference(ACTIVITY_OPEN);
        mActivityOpenPref.setOnPreferenceChangeListener(this);
        mActivityOpenPref.setSummary(getProperSummary(mActivityOpenPref));
        mActivityOpenPref.setEntries(mAnimationsStrings);
        mActivityOpenPref.setEntryValues(mAnimationsNum);

        mActivityClosePref = (ListPreference) findPreference(ACTIVITY_CLOSE);
        mActivityClosePref.setOnPreferenceChangeListener(this);
        mActivityClosePref.setSummary(getProperSummary(mActivityClosePref));
        mActivityClosePref.setEntries(mAnimationsStrings);
        mActivityClosePref.setEntryValues(mAnimationsNum);

        mTaskOpenPref = (ListPreference) findPreference(TASK_OPEN);
        mTaskOpenPref.setOnPreferenceChangeListener(this);
        mTaskOpenPref.setSummary(getProperSummary(mTaskOpenPref));
        mTaskOpenPref.setEntries(mAnimationsStrings);
        mTaskOpenPref.setEntryValues(mAnimationsNum);

        mTaskClosePref = (ListPreference) findPreference(TASK_CLOSE);
        mTaskClosePref.setOnPreferenceChangeListener(this);
        mTaskClosePref.setSummary(getProperSummary(mTaskClosePref));
        mTaskClosePref.setEntries(mAnimationsStrings);
        mTaskClosePref.setEntryValues(mAnimationsNum);

        mTaskMoveToFrontPref = (ListPreference) findPreference(TASK_MOVE_TO_FRONT);
        mTaskMoveToFrontPref.setOnPreferenceChangeListener(this);
        mTaskMoveToFrontPref.setSummary(getProperSummary(mTaskMoveToFrontPref));
        mTaskMoveToFrontPref.setEntries(mAnimationsStrings);
        mTaskMoveToFrontPref.setEntryValues(mAnimationsNum);

        mTaskMoveToBackPref = (ListPreference) findPreference(TASK_MOVE_TO_BACK);
        mTaskMoveToBackPref.setOnPreferenceChangeListener(this);
        mTaskMoveToBackPref.setSummary(getProperSummary(mTaskMoveToBackPref));
        mTaskMoveToBackPref.setEntries(mAnimationsStrings);
        mTaskMoveToBackPref.setEntryValues(mAnimationsNum);

        mWallpaperOpen = (ListPreference) findPreference(WALLPAPER_OPEN);
        mWallpaperOpen.setOnPreferenceChangeListener(this);
        mWallpaperOpen.setSummary(getProperSummary(mWallpaperOpen));
        mWallpaperOpen.setEntries(mAnimationsStrings);
        mWallpaperOpen.setEntryValues(mAnimationsNum);

        mWallpaperClose = (ListPreference) findPreference(WALLPAPER_CLOSE);
        mWallpaperClose.setOnPreferenceChangeListener(this);
        mWallpaperClose.setSummary(getProperSummary(mWallpaperClose));
        mWallpaperClose.setEntries(mAnimationsStrings);
        mWallpaperClose.setEntryValues(mAnimationsNum);

        mWallpaperIntraOpen = (ListPreference) findPreference(WALLPAPER_INTRA_OPEN);
        mWallpaperIntraOpen.setOnPreferenceChangeListener(this);
        mWallpaperIntraOpen.setSummary(getProperSummary(mWallpaperIntraOpen));
        mWallpaperIntraOpen.setEntries(mAnimationsStrings);
        mWallpaperIntraOpen.setEntryValues(mAnimationsNum);

        mWallpaperIntraClose = (ListPreference) findPreference(WALLPAPER_INTRA_CLOSE);
        mWallpaperIntraClose.setOnPreferenceChangeListener(this);
        mWallpaperIntraClose.setSummary(getProperSummary(mWallpaperIntraClose));
        mWallpaperIntraClose.setEntries(mAnimationsStrings);
        mWallpaperIntraClose.setEntryValues(mAnimationsNum);

        mToastAnimation = (ListPreference) findPreference(KEY_TOAST_ANIMATION);
        mToastAnimation.setSummary(mToastAnimation.getEntry());
        int CurrentToastAnimation = Settings.System.getInt(getContentResolver(), Settings.System.TOAST_ANIMATION, 1);
        mToastAnimation.setValueIndex(CurrentToastAnimation); //set to index of default value
        mToastAnimation.setSummary(mToastAnimation.getEntries()[CurrentToastAnimation]);
        mToastAnimation.setOnPreferenceChangeListener(this);

        mListViewAnimation = (ListPreference) prefSet.findPreference(KEY_LISTVIEW_ANIMATION);
        int listviewanimation = Settings.System.getInt(getContentResolver(),
                Settings.System.LISTVIEW_ANIMATION, 0);
        mListViewAnimation.setValue(String.valueOf(listviewanimation));
        mListViewAnimation.setSummary(mListViewAnimation.getEntry());
        mListViewAnimation.setOnPreferenceChangeListener(this);

        mListViewInterpolator = (ListPreference) prefSet.findPreference(KEY_LISTVIEW_INTERPOLATOR);
        int listviewinterpolator = Settings.System.getInt(getContentResolver(),
                Settings.System.LISTVIEW_INTERPOLATOR, 0);
        mListViewInterpolator.setValue(String.valueOf(listviewinterpolator));
        mListViewInterpolator.setSummary(mListViewInterpolator.getEntry());
        mListViewInterpolator.setOnPreferenceChangeListener(this);
        mListViewInterpolator.setEnabled(listviewanimation > 0);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mActivityOpenPref) {
            int val = Integer.parseInt((String) newValue);
            result = Settings.System.putInt(mContentRes,
                    Settings.System.ACTIVITY_ANIMATION_CONTROLS[0], val);
            return true;
        } else if (preference == mActivityClosePref) {
            int val = Integer.parseInt((String) newValue);
            result = Settings.System.putInt(mContentRes,
                    Settings.System.ACTIVITY_ANIMATION_CONTROLS[1], val);
            return true;
        } else if (preference == mTaskOpenPref) {
            int val = Integer.parseInt((String) newValue);
            result = Settings.System.putInt(mContentRes,
                    Settings.System.ACTIVITY_ANIMATION_CONTROLS[2], val);
            return true;
        } else if (preference == mTaskClosePref) {
            int val = Integer.parseInt((String) newValue);
            result = Settings.System.putInt(mContentRes,
                    Settings.System.ACTIVITY_ANIMATION_CONTROLS[3], val);
            return true;
        } else if (preference == mTaskMoveToFrontPref) {
            int val = Integer.parseInt((String) newValue);
            result = Settings.System.putInt(mContentRes,
                    Settings.System.ACTIVITY_ANIMATION_CONTROLS[4], val);
            return true;
        } else if (preference == mTaskMoveToBackPref) {
            int val = Integer.parseInt((String) newValue);
            result = Settings.System.putInt(mContentRes,
                    Settings.System.ACTIVITY_ANIMATION_CONTROLS[5], val);
            return true;
        } else if (preference == mWallpaperOpen) {
            int val = Integer.parseInt((String) newValue);
            result = Settings.System.putInt(mContentRes,
                    Settings.System.ACTIVITY_ANIMATION_CONTROLS[6], val);
            return true;
        } else if (preference == mWallpaperClose) {
            int val = Integer.parseInt((String) newValue);
            result = Settings.System.putInt(mContentRes,
                    Settings.System.ACTIVITY_ANIMATION_CONTROLS[7], val);
            return true;
        } else if (preference == mWallpaperIntraOpen) {
            int val = Integer.parseInt((String) newValue);
            result = Settings.System.putInt(mContentRes,
                    Settings.System.ACTIVITY_ANIMATION_CONTROLS[8], val);
             return true;
        } else if (preference == mWallpaperIntraClose) {
            int val = Integer.parseInt((String) newValue);
            result = Settings.System.putInt(mContentRes,
                    Settings.System.ACTIVITY_ANIMATION_CONTROLS[9], val);
            return true;
        } else if (preference == mToastAnimation) {
            int index = mToastAnimation.findIndexOfValue((String) newValue);
            Settings.System.putString(getContentResolver(), Settings.System.TOAST_ANIMATION, (String) newValue);
            mToastAnimation.setSummary(mToastAnimation.getEntries()[index]);
            Toast.makeText(mContext, "Toast Test", Toast.LENGTH_SHORT).show();
            return true;
        } else if (preference == mListViewAnimation) {
            int value = Integer.parseInt((String) newValue);
            int index = mListViewAnimation.findIndexOfValue((String) newValue);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.LISTVIEW_ANIMATION, value);
            mListViewAnimation.setSummary(mListViewAnimation.getEntries()[index]);
            mListViewInterpolator.setEnabled(value > 0);
            return true;
        } else if (preference == mListViewInterpolator) {
            int value = Integer.parseInt((String) newValue);
            int index = mListViewInterpolator.findIndexOfValue((String) newValue);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.LISTVIEW_INTERPOLATOR, value);
            mListViewInterpolator.setSummary(mListViewInterpolator.getEntries()[index]);
            return true;
        }
        preference.setSummary(getProperSummary(preference));
        return false;
    }

     private String getProperSummary(Preference preference) {
        String mString = "";
        if (preference == mActivityOpenPref) {
            mString = Settings.System.ACTIVITY_ANIMATION_CONTROLS[0];
        } else if (preference == mActivityClosePref) {
            mString = Settings.System.ACTIVITY_ANIMATION_CONTROLS[1];
        } else if (preference == mTaskOpenPref) {
            mString = Settings.System.ACTIVITY_ANIMATION_CONTROLS[2];
        } else if (preference == mTaskClosePref) {
            mString = Settings.System.ACTIVITY_ANIMATION_CONTROLS[3];
        } else if (preference == mTaskMoveToFrontPref) {
            mString = Settings.System.ACTIVITY_ANIMATION_CONTROLS[4];
        } else if (preference == mTaskMoveToBackPref) {
            mString = Settings.System.ACTIVITY_ANIMATION_CONTROLS[5];
        } else if (preference == mWallpaperOpen) {
            mString = Settings.System.ACTIVITY_ANIMATION_CONTROLS[6];
        } else if (preference == mWallpaperClose) {
            mString = Settings.System.ACTIVITY_ANIMATION_CONTROLS[7];
        } else if (preference == mWallpaperIntraOpen) {
            mString = Settings.System.ACTIVITY_ANIMATION_CONTROLS[8];
        } else if (preference == mWallpaperIntraClose) {
            mString = Settings.System.ACTIVITY_ANIMATION_CONTROLS[9];
        }

        int mNum = Settings.System.getInt(mContentRes, mString, 0);
        return mAnimationsStrings[mNum];
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.SIX;
    }

}
