package com.unglibaaz.ungli;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
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

/**
 * Created by abhishek.maurya on 11/22/2017.
 */

public class ForgotPassword extends AppCompatActivity
{
    private EditText edit_phone_forget;
    private String input_email;
    private ImageView menuBtn;
    private String[] menuContents;
    private PopupWindow popupMenuWindow;
    TextView txtForget;
    TextView txtHeadingLabel;
    TextView txtLabel;
    TextView txtPassword;
    Typeface typeface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        typeface = Typeface.createFromAsset(getAssets(), "Verdana.ttf");
        menuBtn = (ImageView) findViewById(R.id.menuBtn);
        txtForget = (TextView) findViewById(R.id.txtForget);
        txtPassword = (TextView) findViewById(R.id.forgetPassword);
        txtHeadingLabel = (TextView) findViewById(R.id.txtPageLabel);
        txtLabel = (TextView) findViewById(R.id.txtPageLabelHeading);
        txtHeadingLabel.setTypeface(this.typeface);
    //    txtLabel.setTypeface(this.typeface);
        txtForget.setTypeface(this.typeface);
        txtPassword.setTypeface(this.typeface);

        this.edit_phone_forget = (EditText) findViewById(R.id.editTextEmailForget);
        List<String> menuList = new ArrayList();
        menuList.add("Share this app");
        menuList.add("Rate this app");
        this.menuContents = new String[menuList.size()];
        menuList.toArray(this.menuContents);
        popupMenuWindow = getMenuWindow();

        menuBtn.setOnClickListener(new View.OnClickListener() {
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
        popupWindow.setHeight((int) TypedValue.applyDimension(1, 60.0f, getResources().getDisplayMetrics()));
        popupWindow.setContentView(view);
        return popupWindow;
    }
    public void performForgotPassword(View view) {
        input_email = edit_phone_forget.getText().toString().trim();
        if (ForgotPassword.this.input_email.equals("")) {
            Toast.makeText(ForgotPassword.this, getString(R.string.empty_message), Toast.LENGTH_SHORT).show();
        } else if (ForgotPassword.this.input_email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            forgetData();
        } else {
            Toast.makeText(ForgotPassword.this, getString(R.string.invalid_email_message), Toast.LENGTH_SHORT).show();
        }
    }

    void forgetData() {
        Volley.newRequestQueue(this).add(new StringRequest(1, "https://www.ungalbaaz.com/services/forgot_password", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
                if (response.contains("TRUE")) {
                //txtPassword.setText("Congratulation! Please check your mail and reset your password");
                    Toast.makeText(ForgotPassword.this, "Congratulation! Please check your mail and reset your password", Toast.LENGTH_SHORT).show();
                } else if (response.contains("FALSE")) {
                    Toast.makeText(ForgotPassword.this, getString(R.string.incorrect_message), Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(ForgotPassword.this, getString(R.string.incor_message), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", error.getMessage());
                Toast.makeText(ForgotPassword.this, getString(R.string.incor_message), Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap();
                params.put("identity", input_email);
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
}
