package com.unglibaaz.ungli;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.VideoView;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import java.util.List;

import SessionManagement.SessionManager;

public class DetailsActivitySuggestion extends AppCompatActivity {
    public static final String TAG = "";
    private Button backbutton;
    VideoView mVideoView;
    private ImageView menuBtn;
    private String[] menuContents;
    private PopupWindow popupMenuWindow;
    private String res;
    SessionManager session;
    TextView txtCaseStudyLabel;
    TextView txtStoryLabel;
    TextView txtStoryResult;
    TextView txtTitleEntery;
    TextView txtTitleLabel;
    Typeface typeface;
    ImageView header;
    class MenuListAdaptor extends BaseAdapter {
        private final Context context;

        public MenuListAdaptor(Context context) {
            this.context = context;
        }

        public int getCount() {
            return DetailsActivitySuggestion.this.menuContents.length;
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
            tv.setTypeface(DetailsActivitySuggestion.this.typeface);
            tv.setText(DetailsActivitySuggestion.this.menuContents[pos]);
            v.setTag(Integer.valueOf(pos));
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
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
                            DetailsActivitySuggestion.this.startActivity(Intent.createChooser(sharingIntent, "UNGALBAAZ Mobile app ..."));
                            return;
                        case 0:
                            DetailsActivitySuggestion.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.unglibaaz.ungli")));
                            return;
                        case 1:
                            DetailsActivitySuggestion.this.session.logoutUser();
                            LoginManager.getInstance().logOut();
                            return;
                        default:
                            return;
                    }
                }
            });
            return v;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView( R.layout.detail_fragment);
        this.session = new SessionManager(getApplicationContext());
        this.typeface = Typeface.createFromAsset(getAssets(), "Verdana.ttf");
        this.mVideoView = (VideoView) findViewById(R.id.videoView);
        this.txtCaseStudyLabel = (TextView) findViewById(R.id.txtCaseStudyLabel);
        this.txtTitleLabel = (TextView) findViewById(R.id.txtTitleLabel);
        this.txtTitleEntery = (TextView) findViewById(R.id.txtTitleEntery);
        this.txtStoryLabel = (TextView) findViewById(R.id.txtStoryLabel);
        this.txtStoryResult = (TextView) findViewById(R.id.txtStoryResult);
        this.txtCaseStudyLabel.setTypeface(this.typeface);
        this.txtTitleLabel.setTypeface(this.typeface);
        this.txtTitleEntery.setTypeface(this.typeface);
        this.txtStoryLabel.setTypeface(this.typeface);
        this.txtStoryResult.setTypeface(this.typeface);

        header = (ImageView) findViewById(R.id.header2);
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

       // this.backbutton = (Button) findViewById(R.id.backBtn);
        //this.backbutton.setOnClickListener(new C06291());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.txtTitleEntery.setText(extras.getString(ShareConstants.WEB_DIALOG_PARAM_TITLE));
            this.txtStoryResult.setText(extras.getString("description").replaceAll("\\<.*?>", ""));
            String videoUrl = extras.getString("video");
            this.menuBtn = (ImageView) findViewById(R.id.menuBtn);
            List<String> menuList = new ArrayList();
            menuList.add("Share this app");
            menuList.add("Rate this app");
            menuList.add("Logout");
            this.menuContents = new String[menuList.size()];
            menuList.toArray(this.menuContents);
            this.popupMenuWindow = getMenuWindow();
            this.menuBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupMenuWindow.showAsDropDown(view, -5, 8);
                }
            });
        }
    }

    public PopupWindow getMenuWindow() {
        PopupWindow popupWindow = new PopupWindow(this);
        View view = getLayoutInflater().inflate(R.layout.menulist, new FrameLayout(this));
        ((ListView) view.findViewById(R.id.menuList)).setAdapter(new MenuListAdaptor(this));
        popupWindow.setFocusable(true);
        popupWindow.setWidth((getResources().getDisplayMetrics().widthPixels / 2) + 21);
        popupWindow.setHeight((int) TypedValue.applyDimension(1, 80.0f, getResources().getDisplayMetrics()));
        popupWindow.setContentView(view);
        return popupWindow;
    }
}
