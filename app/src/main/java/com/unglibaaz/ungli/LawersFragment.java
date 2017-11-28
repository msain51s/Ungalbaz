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

import net.LawersPojo;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import adapter.LawAdpator;

public class LawersFragment extends Fragment {
    private static final String url = "http://ungalbaaz.com/mobile-app/ungalbaaz-lawers.php";
    private int currentId;
    private ArrayList<LawersPojo> itemList;
    private LayoutManager layoutManager;
    private LawAdpator mAdapter;
    private LawersPojo mPojo;
    private RecyclerView mRecyclerView;
private View view_dilaog;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lawers_fragment, container, false);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.mRecyclerView.setHasFixedSize(false);
        view_dilaog = getActivity().findViewById(R.id.loader);
        this.layoutManager = new LinearLayoutManager(getActivity());
        this.mRecyclerView.setLayoutManager(this.layoutManager);
        DividerItemDecoration verticalDecoration = new DividerItemDecoration(this.mRecyclerView.getContext(), 0);
        verticalDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.vertical_divider));
        this.mRecyclerView.addItemDecoration(verticalDecoration);
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(this.mRecyclerView.getContext(), 1);
        horizontalDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.horizontal_divider));
        this.mRecyclerView.addItemDecoration(horizontalDecoration);
        this.itemList = new ArrayList();
        requestCategoryData();
        return view;
    }

    private void requestCategoryData() {
        view_dilaog.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(0, url, null, new Listener<JSONObject>() {



            public void onResponse(JSONObject jsonObject) {
                view_dilaog.setVisibility(View.INVISIBLE);
                try {
                    if (LawersFragment.this.itemList.isEmpty()) {
                        JSONArray contacts = jsonObject.getJSONArray("Law");
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            LawersFragment.this.mPojo = new LawersPojo(c.getString("Id"), c.getString("LawerName"), c.getString("LawerType"), c.getString("Email"), c.getString("Phone"), c.getString("AboutUS"), c.getString("Rating"), c.getString("ThumbImage"));
                            LawersFragment.this.itemList.add(LawersFragment.this.mPojo);
                            LawersFragment.this.mAdapter = new LawAdpator(LawersFragment.this.getActivity(), LawersFragment.this.itemList);
                            LawersFragment.this.mAdapter.notifyDataSetChanged();
                            LawersFragment.this.mAdapter.setOnItemClickListener(new LawAdpator.OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, String str) {

                                }
                            });
                            LawersFragment.this.mRecyclerView.setAdapter(LawersFragment.this.mAdapter);
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
