package fregment;

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
import com.unglibaaz.ungli.AppController;

import com.unglibaaz.ungli.R;

import net.NgoPojo;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import adapter.NgoAdpator;

public class NgoFragment extends Fragment {
    private static final String url = "http://ungalbaaz.com/mobile-app/ungalbaaz-ngo.php";
    private NgoPojo category;
    private NgoAdpator categoryAdapter;
    private ArrayList<NgoPojo> categoryList;
    private RecyclerView categoryRecyclerView;
    private int currentId;
    private LayoutManager layoutManager;
private View view_dilaog;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.suggestion_fragment, container, false);
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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(0, url, null, new Listener<JSONObject>() {

            public void onResponse(JSONObject jsonObject) {
                view_dilaog.setVisibility(View.INVISIBLE);
                try {
                    if (NgoFragment.this.categoryList.isEmpty()) {
                        JSONArray contacts = jsonObject.getJSONArray("Ngo");
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            NgoFragment.this.category = new NgoPojo(c.getString("Id"), c.getString("NgoName"), c.getString("NgoAdd"), c.getString("NgoEmail"), c.getString("NgoPhone"), c.getString("NgoAboutUS"), c.getString("NgoRating"), c.getString("ThumbImage"));
                            NgoFragment.this.categoryList.add(NgoFragment.this.category);
                        }

                        NgoFragment.this.categoryAdapter = new NgoAdpator(NgoFragment.this.getActivity(), NgoFragment.this.categoryList);
                        NgoFragment.this.categoryAdapter.notifyDataSetChanged();
                        NgoFragment.this.categoryAdapter.setOnItemClickListener(new NgoAdpator.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, String str) {

                            }
                        });
                        NgoFragment.this.categoryRecyclerView.setAdapter(categoryAdapter);
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
