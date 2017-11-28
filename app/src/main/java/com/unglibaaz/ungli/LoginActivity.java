package com.unglibaaz.ungli;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SessionManagement.SessionManager;

/**
 * Created by abhishek.maurya on 11/21/2017.
 */

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "";

    Button btn_login;
    Button btn_signUpFacebook;

    EditText edit_mobile;
    EditText edit_password;
    private String faceBookEmail;
    private String faceBookID;
    private String faceBookName;

    String input_email;
    String input_password;
    private Bitmap mBackground;
    private Drawable mBackgroundDrawable;
    RelativeLayout mainLayout;
    private ImageView menuBtn;
    private String[] menuContents;
    private PopupWindow popupMenuWindow;

    SessionManager session;
    SharedPreferences spref;
    TextView txtEmail;
    TextView txtHeadingLabel;
    TextView txtLabel;
    TextView txtPassword;
    TextView txt_forgetPassword;
    TextView txt_join;
    Typeface typeface;

    ImageView header;
    CallbackManager callbackManager;
LoginButton fbLoginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

       // FacebookSdk.sdkInitialize(getApplicationContext());
       // callbackManager = CallbackManager.Factory.create();

        this.session = new SessionManager(getApplicationContext());
        menuBtn = (ImageView) findViewById(R.id.menuBtn);
        spref = getSharedPreferences("file1", 0);
       // fbLoginButton=findViewById(R.id.fbLoginButton);

        btn_login = (Button) findViewById(R.id.btnLogin);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
        txtHeadingLabel = (TextView) findViewById(R.id.txtPageLabel);
        txtLabel = (TextView) findViewById(R.id.txtPageLabelHeading);
        txtHeadingLabel.setTypeface( typeface);
    //    txtLabel.setTypeface( typeface);
        txtEmail.setTypeface( typeface);
        txtPassword.setTypeface( typeface);
        edit_mobile = (EditText) findViewById(R.id.editTextMobile);
        edit_password = (EditText) findViewById(R.id.editTextPassword);
        txt_join = (TextView) findViewById(R.id.signUP);
        txt_join.setTypeface( typeface);
        txt_forgetPassword = (TextView) findViewById(R.id.forgetPassword);
        txt_forgetPassword.setTypeface( typeface);

        header = (ImageView) findViewById(R.id.header2);
        List<String> menuList = new ArrayList();
        menuList.add("Share this app");
        menuList.add("Rate this app");
        menuContents = new String[menuList.size()];
        menuList.toArray(this.menuContents);
        popupMenuWindow = getMenuWindow();

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


    public void performMoreOption(View view) {
        popupMenuWindow.showAsDropDown(view, -5, 8);
    }



    public void preformSignUp(View view) {
      startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }



    public void preformForgetPassword(View view) {
       startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
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


    public void prefromLogin(View view) {

        input_email = edit_mobile.getText().toString().trim();
        input_password = edit_password.getText().toString().trim();
        SharedPreferences.Editor editpref =spref.edit();
        editpref.putString("mobile", input_email);
        editpref.commit();
        if (input_password.equals("") || input_email.equals("")) {
            Toast.makeText(LoginActivity.this,getString(R.string.empty_messages), Toast.LENGTH_SHORT).show();
        } else if (input_password.isEmpty() || input_email.isEmpty()) {
            Toast.makeText(LoginActivity.this, getString(R.string.empty_message), Toast.LENGTH_SHORT).show();
        } else if (!input_email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            Toast.makeText(LoginActivity.this, getString(R.string.invalid_email_message), Toast.LENGTH_SHORT).show();
        } else if (input_password.length() >= 6) {
            loginData();
        } else {
            Toast.makeText(LoginActivity.this, getString(R.string.min_char_message), Toast.LENGTH_LONG).show();
        }


    }



    void loginData() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        pDialog.setCancelable(false);
        Volley.newRequestQueue(this).add(new StringRequest(1, "http://ungalbaaz.com/services/login", new Response.Listener<String>() {
            public void onResponse(String response) {
                if (response.contains("Logged In Successfully")) {
                    pDialog.dismiss();
                    session.createLoginSession("ungalbaaz",input_email);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else if (response.contains("Incorrect Login")) {
                    pDialog.dismiss();
                    Toast.makeText(LoginActivity.this, getString(R.string.incorrect_message), Toast.LENGTH_SHORT).show();
                }else {
                    pDialog.dismiss();
                    Toast.makeText(LoginActivity.this, getString(R.string.incorrect_message), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Internet is not working please try again latter", Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap();
                params.put("identity", input_email);
                params.put("password", input_password);
                return params;
            }
        });
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
    public void perfromFB(View view)
    {
        Toast.makeText(LoginActivity.this,"Work in progress",Toast.LENGTH_LONG).show();
    }
}
