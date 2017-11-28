package com.unglibaaz.ungli;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SessionManagement.SessionManager;

/**
 * Created by abhishek.maurya on 11/22/2017.
 */

public class RegisterActivity extends AppCompatActivity {


   // private CallbackManager callbackManager;
    private CheckBox check_agree_regi;
    private EditText edit_email_regi;
    private EditText edit_name_regi;
    private EditText edit_password_regi;
    private EditText edit_ungal_regi;
    private String faceBookEmail;
    private String faceBookID;
    private String faceBookName;
    private String input_email;
    private String input_name;
    private String input_password;
    private String input_ungalbaaz_name;
    private ImageView menuBtn;
    private String[] menuContents;
    private PopupWindow popupMenuWindow;
    //private ProfileTracker profileTracker;
    private String res;
    SessionManager session;
    TextView txtAlreadyRegi;
    TextView txtEmailRegi;
    TextView txtHeadingLabel;
    TextView txtLabel;
    TextView txtPassRegi;
    TextView txtUngalRegi;
    TextView txtUserNameRegi;
    Typeface typeface;





    ImageView header;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        typeface = Typeface.createFromAsset(getAssets(), "Verdana.ttf");
        session = new SessionManager(getApplicationContext());
        menuBtn = (ImageView) findViewById(R.id.menuBtn);
        header = (ImageView) findViewById(R.id.header2);
        txtEmailRegi = (TextView) findViewById(R.id.txtEmail);
        txtPassRegi = (TextView) findViewById(R.id.txtPassword);
        txtUngalRegi = (TextView) findViewById(R.id.txtUngalName);
        txtHeadingLabel = (TextView) findViewById(R.id.txtPageLabel);
        txtLabel = (TextView) findViewById(R.id.txtPageLabelHeading);
        txtHeadingLabel.setTypeface(this.typeface);
    //    txtLabel.setTypeface(this.typeface);
        txtEmailRegi.setTypeface(this.typeface);
        txtPassRegi.setTypeface(this.typeface);
        txtUngalRegi.setTypeface(this.typeface);


        edit_email_regi = (EditText) findViewById(R.id.editTextMobile);
        edit_ungal_regi = (EditText) findViewById(R.id.ungalbazName);
        edit_password_regi = (EditText) findViewById(R.id.editTextPassword);


        List<String> menuList = new ArrayList();
        menuList.add("Share this app");
        menuList.add("Rate this app");
        this.menuContents = new String[menuList.size()];
        menuList.toArray(this.menuContents);
        this.popupMenuWindow = getMenuWindow();

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenuWindow.showAsDropDown(view, -5, 8);
            }
        });

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

    public void prefromRegister(View view) {

       input_email = edit_email_regi.getText().toString().trim();
        input_ungalbaaz_name = edit_ungal_regi.getText().toString().trim();
        input_password = edit_password_regi.getText().toString().trim();
        if (input_email.equals("") || input_password.equals("")) {
            if (input_email.equals("") || input_ungalbaaz_name.equals("") ||input_password.equals("")) {
                Toast.makeText(RegisterActivity.this, getString(R.string.empty_messages), Toast.LENGTH_SHORT).show();
            }
        } else if (input_password.length() < 8) {
            Toast.makeText(RegisterActivity.this, getString(R.string.min_char_message), Toast.LENGTH_SHORT).show();
        } else if (input_email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            registerData();
        } else {
            Toast.makeText(RegisterActivity.this, getString(R.string.invalid_email_message), Toast.LENGTH_SHORT).show();
        }
    }

    public PopupWindow getMenuWindow() {
        PopupWindow popupWindow = new PopupWindow(this);
        View view = getLayoutInflater().inflate(R.layout.menulist, new FrameLayout(this));
        ((ListView) view.findViewById(R.id.menuList)).setAdapter(new MenuListAdaptor(this));
        popupWindow.setFocusable(true);
        popupWindow.setWidth((getResources().getDisplayMetrics().widthPixels / 2) + 21);
        popupWindow.setHeight((int) TypedValue.applyDimension(1, 60.0f, getResources().getDisplayMetrics()));
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
            v.setOnClickListener(new View.OnClickListener() {
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
                        default:
                            return;
                    }
                }
            });
            return v;
        }
    }





    void registerData() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        pDialog.setCancelable(false);
        Volley.newRequestQueue(this).add(new StringRequest(1, "http://ungalbaaz.com/services/registration", new Response.Listener<String>() {
            public void onResponse(String response) {
                if (response.contains("successfully registered")) {
                    pDialog.dismiss();
                    session.createLoginSession("ungalbaaz", input_email);
                   startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                   finish();
                } else if (response.contains("Email already registered")) {
                    pDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Email already registered",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap();
                params.put("email", input_email);
                params.put("password", input_password);
                params.put("username", input_email);
                params.put("ungalbaaz_name", input_ungalbaaz_name);
                return params;
            }
        });
    }
}
