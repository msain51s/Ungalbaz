package com.unglibaaz.ungli;

import android.app.ProgressDialog;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.LawersPojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import SessionManagement.SessionManager;
import adapter.LawAdpator;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class DetailsActivity extends AppCompatActivity {
    public static final String TAG = "";
    private Button backbutton;
    String imageUrl1;
    String imageUrl2;
    ImageView mainImage;
    ImageView mainImage2;
    private ImageView menuBtn;
    private String[] menuContents;
    private PopupWindow popupMenuWindow;
    private String res;
    SessionManager session;
    TextView txtCaseResult;
    TextView txtCaseResultLabel;
    TextView txtCaseStudy;
    TextView txtCaseStudyLabel;
    Typeface typeface;
    ImageView header;
    SmoothProgressBar loader,loader2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.detail_fragment);
        this.session = new SessionManager(getApplicationContext());
        this.typeface = Typeface.createFromAsset(getAssets(), "Verdana.ttf");
        this.mainImage = (ImageView) findViewById(R.id.mImageViewStudy);
        this.mainImage2 = (ImageView) findViewById(R.id.mImageViewResult);
        this.txtCaseStudyLabel = (TextView) findViewById(R.id.txtCaseStudyLabel);
        this.txtCaseResultLabel = (TextView) findViewById(R.id.txtCaseResultLabel);
        this.txtCaseStudy = (TextView) findViewById(R.id.txtCaseStudy);
        this.txtCaseResult = (TextView) findViewById(R.id.txtCaseResult);
        this.txtCaseStudyLabel.setTypeface(this.typeface);
        this.txtCaseResultLabel.setTypeface(this.typeface);
        this.txtCaseStudy.setTypeface(this.typeface);
        this.txtCaseResult.setTypeface(this.typeface);
        this.backbutton = (Button) findViewById(R.id.backBtn);
        header = (ImageView) findViewById(R.id.header2);

        loader=(SmoothProgressBar)findViewById(R.id.loader);
        loader2=(SmoothProgressBar)findViewById(R.id.loader2);





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
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.imageUrl1 = extras.getString("image1");
            this.imageUrl2 = extras.getString("image2");


            final Callback loadedCallback = new Callback() {
                @Override
                public void onSuccess() {
                    loader.setVisibility(View.GONE);
                    loader2.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    loader.setVisibility(View.GONE);
                    loader2.setVisibility(View.GONE);
                }
            };

            if (extras.getString("Type").equals("Ungals")) {
                this.txtCaseStudyLabel.setText("UNGALBAAZ TOPIC:".toUpperCase());
                this.txtCaseResultLabel.setText("Result after Ungal:".toUpperCase());
                this.txtCaseStudy.setText(extras.getString("description").replaceAll("\\<.*?>", ""));
                this.txtCaseResult.setText(extras.getString("shor_description").replaceAll("\\<.*?>", ""));

                loader.setVisibility(View.VISIBLE);
                loader2.setVisibility(View.VISIBLE);
                Picasso.with(this).load(this.imageUrl1).into(mainImage,loadedCallback);
                Picasso.with(this).load(this.imageUrl2).into(mainImage2,loadedCallback);
            } else if (extras.getString("Type").equals("Sahayata")) {
                this.txtCaseStudyLabel.setText("Sahayata by Ungalbaaz:".toUpperCase());
                this.txtCaseResultLabel.setText("Result after Sahayata:".toUpperCase());
                this.txtCaseStudy.setText(extras.getString("description").replaceAll("\\<.*?>", ""));
                this.txtCaseResult.setText(extras.getString("shor_description").replaceAll("\\<.*?>", ""));
                loader.setVisibility(View.VISIBLE);
                loader2.setVisibility(View.VISIBLE);
                Picasso.with(this).load(this.imageUrl1).into(mainImage,loadedCallback);
                Picasso.with(this).load(this.imageUrl2).into(mainImage2,loadedCallback);
                ;

            }else if (extras.getString("Type").equals("Suggestion")) {
                this.txtCaseStudyLabel.setText("Suggestion by Ungalbaaz:".toUpperCase());
                this.txtCaseResultLabel.setText("Result after Suggestion:".toUpperCase());
                this.txtCaseStudy.setText(extras.getString("description").replaceAll("\\<.*?>", ""));
                this.txtCaseResult.setText(extras.getString("shor_description").replaceAll("\\<.*?>", ""));

                loader.setVisibility(View.VISIBLE);
                loader2.setVisibility(View.VISIBLE);
                Picasso.with(this).load(this.imageUrl1).into(mainImage,loadedCallback);
                Picasso.with(this).load(this.imageUrl1).into(mainImage2,loadedCallback);
            }
        }
        menuBtn = (ImageView) findViewById(R.id.menuBtn);
        List<String> menuList = new ArrayList();
        menuList.add("Share this app");
        menuList.add("Rate this app");
        menuList.add("Logout");
        menuContents = new String[menuList.size()];
        menuList.toArray(this.menuContents);
        popupMenuWindow = getMenuWindow();
        menuBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenuWindow.showAsDropDown(view, -5, 8);
            }
        });

        backbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
            return menuContents.length;
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
            tv.setTypeface(typeface);
            tv.setText(menuContents[pos]);
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
                            startActivity(Intent.createChooser(sharingIntent, "UNGALBAAZ Mobile app ..."));
                            return;
                        case 0:
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.unglibaaz.ungli")));
                            return;
                        case 1:
                            session.logoutUser();
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

    private void getDataData(String url) {
        final ProgressDialog pDialog = new ProgressDialog(DetailsActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {



            public void onResponse(JSONObject jsonObject) {
                pDialog.hide();
                try {
                    JSONArray contacts = jsonObject.getJSONArray("Posts");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String Id = c.getString("Id");
                        String title = c.getString("Title");
                        String slug = c.getString("Slug");
                        String short_description = c.getString("ShortDesc");
                        c.getString("FullDesc");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                pDialog.hide();
            }
        });
        jsonObjectRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}



