package com.qdrs.sketchxu.wechat.activity;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.qdrs.sketchxu.wechat.R;
import com.qdrs.sketchxu.wechat.fragment.ContactsFragment;
import com.qdrs.sketchxu.wechat.fragment.MeFragment;
import com.qdrs.sketchxu.wechat.fragment.MessageFragment;
import com.qdrs.sketchxu.wechat.fragment.NoticeFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        MessageFragment.OnMessageFragmentInteractionListener,
        ContactsFragment.OnContactsFragmentInteractionListener,
        NoticeFragment.OnNoticeFragmentInteractionListener,
        MeFragment.OnMeFragmentInteractionListener {

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    public static final int FRAGMENT_COUNT = 4;
    public static final int FRAGMENT_MESSAGE = 0;
    public static final int FRAGMENT_CONTACTS = 1;
    public static final int FRAGMENT_NOTICE = 2;
    public static final int FRAGMENT_ME = 3;

    private LinearLayout mTabMessage, mTabContacts, mTabNotice, mTabMe;

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int index = mViewPager.getCurrentItem();
            switch (view.getId()) {
                case R.id.tab_message:
                    index = 0;
                    break;
                case R.id.tab_contacts:
                    index = 1;
                    break;
                case R.id.tab_notice:
                    index = 2;
                    break;
                case R.id.tab_me:
                    index = 3;
                    break;
                default:
                    break;
            }
            mViewPager.setCurrentItem(index, true);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabMessage = findViewById(R.id.tab_message);
        mTabMessage.setOnClickListener(mListener);
        mTabContacts = findViewById(R.id.tab_contacts);
        mTabContacts.setOnClickListener(mListener);
        mTabNotice = findViewById(R.id.tab_notice);
        mTabNotice.setOnClickListener(mListener);
        mTabMe = findViewById(R.id.tab_me);
        mTabMe.setOnClickListener(mListener);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {

            }

            public void onPageScrolled(int item, float percent, int offset) {

            }

            public void onPageScrollStateChanged(int position) {

            }
        });
    }

    @Override
    public void onContactsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onMeFragmentInteraction(Uri uri) {

    }

    @Override
    public void onMessageFragmentInteraction(Uri uri) {

    }

    @Override
    public void onNoticeFragmentInteraction(Uri uri) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case FRAGMENT_MESSAGE:
                    fragment = new MessageFragment();
                    break;
                case FRAGMENT_CONTACTS:
                    fragment = new ContactsFragment();
                    break;
                case FRAGMENT_NOTICE:
                    fragment = new NoticeFragment();
                    break;
                case FRAGMENT_ME:
                    fragment = new MeFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return FRAGMENT_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case FRAGMENT_MESSAGE:
                    return getString(R.string.tab_message).toUpperCase(l);
                case FRAGMENT_CONTACTS:
                    return getString(R.string.tab_contacts).toUpperCase(l);
                case FRAGMENT_NOTICE:
                    return getString(R.string.tab_notice).toUpperCase(l);
                case FRAGMENT_ME:
                    return getString(R.string.tab_me).toUpperCase(l);
            }
            return null;
        }
    }
}
