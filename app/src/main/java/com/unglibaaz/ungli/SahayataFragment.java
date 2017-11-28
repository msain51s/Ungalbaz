package com.unglibaaz.ungli;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import net.UngalPojo;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import adapter.SahayataAdpator;

public class SahayataFragment extends Fragment {
    private UngalPojo category;
    private SahayataAdpator categoryAdapter;
    private ArrayList<UngalPojo> categoryList;
    private RecyclerView categoryRecyclerView;
    private int currentId;
    private LayoutManager layoutManager;
    private View view_dilaog;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sahayata_fragment, container, false);
        this.categoryRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.categoryRecyclerView.setHasFixedSize(false);
        view_dilaog = getActivity().findViewById(R.id.loader);
        this.layoutManager = new LinearLayoutManager(getActivity());
        this.categoryRecyclerView.setLayoutManager(this.layoutManager);
        DividerItemDecoration verticalDecoration = new DividerItemDecoration(this.categoryRecyclerView.getContext(), 0);
        verticalDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.vertical_divider));
        this.categoryRecyclerView.addItemDecoration(verticalDecoration);
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(this.categoryRecyclerView.getContext(), 1);
        horizontalDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.horizontal_divider));
        this.categoryRecyclerView.addItemDecoration(horizontalDecoration);
        this.categoryList = new ArrayList();
        requestCategoryData();
        return view;
    }

    private void requestCategoryData() {

        view_dilaog.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(0, "http://ungalbaaz.com/mobile-app/ungalbaaz-sahayta.php", null, new Listener<JSONObject>() {
            public void onResponse(JSONObject jsonObject) {
                view_dilaog.setVisibility(View.INVISIBLE);
                try {
                    if (SahayataFragment.this.categoryList.isEmpty()) {
                        JSONArray contacts = jsonObject.getJSONArray("Help");
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            SahayataFragment.this.category = new UngalPojo(c.getString("Id"), c.getString("Title"), c.getString("ShortDesc"), c.getString("FullDesc"), c.getString("ImageUrl"), c.getString("ImageUrl2"), c.getString("VideoUrl"));
                            SahayataFragment.this.categoryList.add(SahayataFragment.this.category);
                            SahayataFragment.this.categoryAdapter = new SahayataAdpator(SahayataFragment.this.getActivity(), SahayataFragment.this.categoryList);
                            SahayataFragment.this.categoryAdapter.notifyDataSetChanged();
                            SahayataFragment.this.categoryRecyclerView.setAdapter(categoryAdapter);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    Toast.makeText(getActivity(), "Server not responding please try again latter", Toast.LENGTH_SHORT).show();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {

                if(null!=view_dilaog) {
                    view_dilaog.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "Server not responding please try again latter", Toast.LENGTH_SHORT).show();
                }
            }
        });
        jsonObjectRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
