package com.unglibaaz.ungli;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
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
import android.widget.RatingBar;
import android.widget.TextView;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import net.LawersPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import SessionManagement.SessionManager;
import adapter.LawAdpator;

public class DetailsActivityNgo extends AppCompatActivity {
    public static final String TAG = "";
    private Button backbutton;
    ImageView imageView,share,like;
    ImageView header;
    RatingBar mRatingBar;
    private ImageView menuBtn;
    private String[] menuContents;
    private PopupWindow popupMenuWindow;
    private String res;
    SessionManager session;
    TextView txtAboutEntry;
    TextView txtAboutLabel;
    TextView txtAddressEntry;
    TextView txtAddressLabel;
    TextView txtEmailEntry;
    TextView txtEmailLabel;
    TextView txtNameEntery;
    TextView txtNameLabel;
    TextView txtPhoneEntry;
    TextView txtPhoneLabel;
    TextView txtRatingLabel;
    Typeface typeface;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.detail_fragment_ngo);
        this.session = new SessionManager(getApplicationContext());
        this.typeface = Typeface.createFromAsset(getAssets(), "Verdana.ttf");
        //this.mRatingBar = (RatingBar) findViewById(R.id.mRatingBar);
        //this.mRatingBar.setEnabled(false);
        this.share = (ImageView) findViewById(R.id.share);
        this.like = (ImageView) findViewById(R.id.like);
        this.imageView = (ImageView) findViewById(R.id.mImageView);
        this.txtNameLabel = (TextView) findViewById(R.id.txtNameLabel);
        this.txtNameEntery = (TextView) findViewById(R.id.txtNameEntery);
        this.txtAddressLabel = (TextView) findViewById(R.id.txtAddressLabel);
        this.txtAddressEntry = (TextView) findViewById(R.id.txtAddressEntry);
        this.txtPhoneLabel = (TextView) findViewById(R.id.txtPhoneLabel);
        this.txtPhoneEntry = (TextView) findViewById(R.id.txtPhoneEntry);
        this.txtEmailLabel = (TextView) findViewById(R.id.txtEmailLabel);
        this.txtEmailEntry = (TextView) findViewById(R.id.txtEmailEntry);
        this.txtRatingLabel = (TextView) findViewById(R.id.txtRatingLabel);
        //this.txtAboutLabel = (TextView) findViewById(R.id.txtAboutLabel);
        this.txtAboutEntry = (TextView) findViewById(R.id.txtAboutEntry);
        this.txtNameLabel.setTypeface(this.typeface);
        this.txtNameEntery.setTypeface(this.typeface);
        this.txtAddressLabel.setTypeface(this.typeface);
        this.txtAddressEntry.setTypeface(this.typeface);
        this.txtPhoneLabel.setTypeface(this.typeface);
        this.txtPhoneEntry.setTypeface(this.typeface);
        this.txtEmailLabel.setTypeface(this.typeface);
        this.txtEmailEntry.setTypeface(this.typeface);
       // this.txtRatingLabel.setTypeface(this.typeface);
        //this.txtAboutLabel.setTypeface(this.typeface);
        this.txtAboutEntry.setTypeface(this.typeface);
        this.backbutton = (Button) findViewById(R.id.backBtn);


        ImageView imageView1= (ImageView) findViewById(R.id.star1);
        ImageView imageView2= (ImageView) findViewById(R.id.star2);
        ImageView imageView3= (ImageView) findViewById(R.id.star3);
        ImageView imageView4= (ImageView) findViewById(R.id.star4);
        ImageView imageView5= (ImageView) findViewById(R.id.star5);

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
        String sourceString="";
       // this.backbutton.setOnClickListener(new C06261());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("Type").equals("Ngo")) {


                 this.txtNameLabel.setText(extras.getString("Name").trim());
                 txtNameEntery.setText(extras.getString("Type").trim());
                 sourceString = "<b>" + "About NGO : " + "</b> " + extras.getString("About").replaceAll("\\<.*?>", "");
                this.txtAddressEntry.setText(extras.getString("Address").trim());
                share.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickShsre("NGO","Ngo Type: ");
                    }
                });

                like.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }else {

                txtNameLabel.setText(extras.getString("Name").trim());

                 sourceString = "<b>" + "About Lawyer : " + "</b> " + extras.getString("About").replaceAll("\\<.*?>", "");
                this.txtNameEntery.setText(extras.getString("LawerType").trim());


                share.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickShsre("LAWYER","Lawyer Type: ");
                    }
                });

                like.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }


                this.txtPhoneEntry.setText(extras.getString("Phone").trim());
                this.txtEmailEntry.setText(extras.getString("Email").trim());
                //this.txtAboutEntry.setText("About Lawyer : "+extras.getString("About").replaceAll("\\<.*?>", ""));
               // this.mRatingBar.setRating(Float.parseFloat(extras.getString("Rating")));
                //Picasso.with(getApplicationContext()).load(extras.getString("Thumb")).into(this.imageView);
               // String sourceString = "<b>" + "About Lawyer : " + "</b> " + extras.getString("About").replaceAll("\\<.*?>", "");

                String unspanned = String.format(Locale.US, "%s%s", sourceString, 99);

                Spanned spanned;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    spanned = Html.fromHtml(unspanned, Html.FROM_HTML_MODE_LEGACY);
                } else {
                    spanned = Html.fromHtml(unspanned);
                }

                txtAboutEntry.setText(spanned);
                ImageView ratingImageview[]={imageView1,imageView2,imageView3,imageView4,imageView5};
                Picasso.with(getApplicationContext()).load(extras.getString("Thumb")).into(this.imageView);
                int ratingsize=  Integer.parseInt(extras.getString("Rating"));
                for(int i=0;i<ratingsize;i++)
                {
                    ratingImageview[i].setBackgroundResource(R.drawable.star);
                }

            }

            List<String> menuList = new ArrayList();
            menuList.add("Share this app");
            menuList.add("Rate this app");
            menuList.add("Logout");
            this.menuContents = new String[menuList.size()];
            menuList.toArray(this.menuContents);
            this.popupMenuWindow = getMenuWindow();
            this.menuBtn = (ImageView) findViewById(R.id.menuBtn);
            this.menuBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                 popupMenuWindow.showAsDropDown(view, -5, 8);
                }
            });


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

    class MenuListAdaptor extends BaseAdapter {
        private final Context context;

        public MenuListAdaptor(Context context) {
            this.context = context;
        }

        public int getCount() {
            return DetailsActivityNgo.this.menuContents.length;
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
            tv.setTypeface(DetailsActivityNgo.this.typeface);
            tv.setText(DetailsActivityNgo.this.menuContents[pos]);
            v.setTag(Integer.valueOf(pos));
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailsActivityNgo mainActivity = (DetailsActivityNgo) v.getContext();
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(v.getContext(), R.anim.abc_fade_in);
                    fadeInAnimation.setDuration(10);
                    v.startAnimation(fadeInAnimation);
                    mainActivity.popupMenuWindow.dismiss();
                    switch (((Integer) v.getTag()).intValue() - 1) {
                        case -1:
                            Intent sharingIntent = new Intent("android.intent.action.SEND");
                            sharingIntent.setType("text/plain");
                            sharingIntent.putExtra("android.intent.extra.SUBJECT", "UNGALBAAZ");
                            sharingIntent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=com.unglibaaz.ungli");
                            DetailsActivityNgo.this.startActivity(Intent.createChooser(sharingIntent, "UNGALBAAZ Mobile app ..."));
                            return;
                        case 0:
                            DetailsActivityNgo.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.unglibaaz.ungli")));
                            return;
                        case 1:
                            DetailsActivityNgo.this.session.logoutUser();
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



        public void onClickShsre(String str,String type) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", txtNameLabel.getText().toString());
            intent.putExtra("android.intent.extra.TEXT", type + txtNameEntery.getText().toString()+ "\n Phone: " + txtPhoneEntry.getText().toString() + "\nEmail: " +txtEmailEntry.getText().toString()  + "\n About: " + txtAboutEntry.getText().toString());
            startActivity(Intent.createChooser(intent, str));
        }

}
