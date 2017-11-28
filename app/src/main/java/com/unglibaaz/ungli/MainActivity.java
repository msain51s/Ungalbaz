package com.unglibaaz.ungli;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;

import SessionManagement.SessionManager;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import fregment.AboutFragment;
import fregment.FormFragment;
import fregment.NgoFragment;
import fregment.SuggestionFragment;
import fregment.UngalFragment;

public class MainActivity extends AppCompatActivity {

    private ImageView menuBtn;
    private String[] menuContents;
    private PopupWindow popupMenuWindow;
    SessionManager session;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    Typeface typeface;
    private ViewPager viewPager;
    SmoothProgressBar loader;
    ImageView header;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      getWindow().setBackgroundDrawableResource(R.drawable.bg);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
      loader=(SmoothProgressBar)findViewById(R.id.loader);
        setupViewPager(this.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(this.viewPager);
      header = (ImageView) findViewById(R.id.header2);
      tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        List<String> menuList = new ArrayList();
        menuList.add("Share this app");
        menuList.add("Rate this app");
        menuList.add("Logout");
        this.menuContents = new String[menuList.size()];
        menuList.toArray(this.menuContents);
        this.popupMenuWindow = getMenuWindow();
      this.session = new SessionManager(this);
      menuBtn = (ImageView) findViewById(R.id.menuBtn);
      menuBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              popupMenuWindow.showAsDropDown(view, -5, 8);
          }
      });
      if (!checkAndRequestPermissions()) {
      }

      try {
          Display display = getWindowManager().getDefaultDisplay();
          Point size = new Point();
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
              display.getSize(size);
             if(size.x>500 && size.x<720)
             {
                 header.setBackgroundResource(R.drawable.header_five);
             }
             else
             {
                 header.setBackgroundResource(R.drawable.header);
             }
              //int height = size.y;
          }

      }catch (Exception e)
      {

      }
    }


    class MenuListAdaptor extends BaseAdapter {
        private final Context context;


        public MenuListAdaptor(Context context) {
            this.context = context;
        }

        public int getCount() {
            return MainActivity.this.menuContents.length;
        }

        public Object getItem(int index) {
            return "" + index;
        }

        public long getItemId(int arg0) {
            return (long) arg0;
        }

        public View getView(int pos, View view, ViewGroup viewGroup) {
            View v = view;
            if (v == null) {
                v = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.menu_text, null);
            }
            TextView tv = (TextView) v.findViewById(R.id.menuText);
            tv.setTypeface(MainActivity.this.typeface);
            tv.setText(MainActivity.this.menuContents[pos]);
            v.setTag(Integer.valueOf(pos));
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity mainActivity = (MainActivity) v.getContext();
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(v.getContext(), R.anim.abc_fade_in);
                    fadeInAnimation.setDuration(10);
                    v.startAnimation(fadeInAnimation);
                    popupMenuWindow.dismiss();
                    switch (((Integer) v.getTag()).intValue() - 1) {
                        case -1:
                            Intent sharingIntent = new Intent("android.intent.action.SEND");
                            sharingIntent.setType("text/plain");
                            sharingIntent.putExtra("android.intent.extra.SUBJECT", "UNGALBAAZ");
                            sharingIntent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=com.unglibaaz.ungli");
                            MainActivity.this.startActivity(Intent.createChooser(sharingIntent, "UNGALBAAZ Mobile app ..."));
                            return;
                        case 0:
                            MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.unglibaaz.ungli")));
                            return;
                        case 1:
                           session.logoutUser();

                            //open when use facebook
                           // LoginManager.getInstance().logOut();
                            return;
                        default:
                            return;
                    }
                }
            });
            return v;
        }
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public Fragment getItem(int position) {
            return (Fragment) this.mFragmentList.get(position);
        }

        public int getCount() {
            return this.mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(title);
        }

        public CharSequence getPageTitle(int position) {
            return (CharSequence) this.mFragmentTitleList.get(position);
        }
    }

    public PopupWindow getMenuWindow() {
        PopupWindow popupWindow = new PopupWindow(this);
        View view = getLayoutInflater().inflate(R.layout.menulist, new FrameLayout(this));
        ((ListView) view.findViewById(R.id.menuList)).setAdapter(new MenuListAdaptor(this));
        popupWindow.setFocusable(true);
        popupWindow.setWidth((getResources().getDisplayMetrics().widthPixels / 2) + 21);
        popupWindow.setHeight((int) TypedValue.applyDimension(1, 90.0f, getResources().getDisplayMetrics()));
        popupWindow.setContentView(view);
        return popupWindow;
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FormFragment(), "UNGALBAAZ FORM");
        adapter.addFrag(new UngalFragment(), "UNGAL");
        adapter.addFrag(new SuggestionFragment(), "SUGGESTION");
        adapter.addFrag(new SahayataFragment(), "SAHAYATA");
        adapter.addFrag(new NgoFragment(), "NGO");
       adapter.addFrag(new LawersFragment(), "LAWYER");
       adapter.addFrag(new AboutFragment(), "ABOUT");
        viewPager.setAdapter(adapter);
    }

    private boolean checkAndRequestPermissions() {
        int CameraPer = ContextCompat.checkSelfPermission(this, "android.permission.CAMERA");
        int Storage = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        int StorageRead = ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");
        int PhoneState = ContextCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE");
        int NetworkState = ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_NETWORK_STATE");
        int WiFiState = ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_WIFI_STATE");
        int WakeLock = ContextCompat.checkSelfPermission(this, "android.permission.WAKE_LOCK");
        List<String> listPermissionsNeeded = new ArrayList();
        if (CameraPer != 0) {
            listPermissionsNeeded.add("android.permission.CAMERA");
        }
        if (Storage != 0) {
            listPermissionsNeeded.add("android.permission.WRITE_EXTERNAL_STORAGE");
        }
        if (StorageRead != 0) {
            listPermissionsNeeded.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        if (PhoneState != 0) {
            listPermissionsNeeded.add("android.permission.READ_PHONE_STATE");
        }
        if (NetworkState != 0) {
            listPermissionsNeeded.add("android.permission.ACCESS_NETWORK_STATE");
        }
        if (WiFiState != 0) {
            listPermissionsNeeded.add("android.permission.ACCESS_WIFI_STATE");
        }
        if (WakeLock != 0) {
            listPermissionsNeeded.add("android.permission.WAKE_LOCK");
        }
        if (listPermissionsNeeded.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(this, (String[]) listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == 0) {
                    return;
                }
                return;
            default:
                return;
        }
    }
}
