/*
 * GlowNotifier Application for Android
 * Copyright (C) 2013 Youngbin Han<sukso96100@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.hybdms.glownotifier;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class Tutorial extends ActionBarActivity {

    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(new PagerAdapterClass(getApplicationContext()));
    }

    @SuppressWarnings("unused")
    private void setCurrentInflateItem(int type){
        if(type==0){
            mPager.setCurrentItem(0);
        }
        else if(type==1){
            mPager.setCurrentItem(1);
        }
        else if(type==2){
            mPager.setCurrentItem(2);
        }
        else if(type==3){
            mPager.setCurrentItem(3);
        }
        else if(type==4){
            mPager.setCurrentItem(4);
        }
        else{
            mPager.setCurrentItem(5);
        }
    }
    /**
     * PagerAdapter
     */
    private class PagerAdapterClass extends PagerAdapter {

        private LayoutInflater mInflater;
        public PagerAdapterClass(Context c){
            super();
            mInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return 6;
        }
        private View.OnClickListener mPagerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2){
                    Intent accessibility = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(accessibility);
                }
                else{
                    Intent notiintent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                    startActivity(notiintent);
                }
            }
        };

        @Override
        public Object instantiateItem(View pager, int position) {
            View v = null;
            if(position == 0){
                v = mInflater.inflate(R.layout.tutorial_0, null);
            }
            else if(position == 1){
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2){
                    v = mInflater.inflate(R.layout.tutorial_1_old, null);
                }
                else{
                    v = mInflater.inflate(R.layout.tutorial_1_new, null);
                }
                v.findViewById(R.id.button).setOnClickListener(mPagerListener);
            }
            else if(position == 2){
                v = mInflater.inflate(R.layout.tutorial_2, null);
            }
            else if(position == 3){
                v = mInflater.inflate(R.layout.tutorial_3, null);
            }
            else if(position == 4){
                v = mInflater.inflate(R.layout.tutorial_done, null);
            }
            else{
                v = mInflater.inflate(R.layout.tutorial_done, null);
                finish();
            }
            ((ViewPager)pager).addView(v, 0);
            return v;
        }
        @Override
        public void destroyItem(View pager, int position, Object view){
            ((ViewPager)pager).removeView((View)view);
        }

        @Override
        public boolean isViewFromObject(View pager, Object obj){
            return pager == obj;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0){
                return getString(R.string.tutorial_0);
            }
            else if(position == 1){
               return getString(R.string.tutorial_1);
            }
            else if(position == 2){
               return getString(R.string.tutorial_2);
            }
            else if(position == 3){
                return getString(R.string.tutorial_3);
            }
            else if(position == 4){
                return getString(R.string.tutorial_done);
            }
            else{
                return getString(R.string.tutorial_done);
            }
        }
    }


}