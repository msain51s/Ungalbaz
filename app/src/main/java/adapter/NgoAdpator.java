package adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.unglibaaz.ungli.DetailsActivityNgo;
import com.unglibaaz.ungli.MainActivity;
import com.unglibaaz.ungli.R;

import net.NgoPojo;

import java.util.List;

import SessionManagement.SessionManager;


public class NgoAdpator extends Adapter<NgoAdpator.MyViewHolder> implements OnClickListener {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    SessionManager session;
    NgoPojo ungal;
    private List<NgoPojo> ungalList;

    public class MyViewHolder extends ViewHolder {
        public ImageView likeImage;
        public ImageView shareImage;
        public TextView shortDesc;
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title);
            this.shortDesc = (TextView) view.findViewById(R.id.shortDesc);
            this.likeImage = (ImageView) view.findViewById(R.id.like);
            this.shareImage = (ImageView) view.findViewById(R.id.share);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String str);
    }

    public NgoAdpator(Context context, List<NgoPojo> moviesList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.ungalList = moviesList;
        this.context = context;
        this.session = new SessionManager(context);
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, final int position) {
        this.ungal = (NgoPojo) this.ungalList.get(position);
        holder.title.setText(this.ungal.getLawerName());
        holder.shortDesc.setText(this.ungal.getAddress());
        holder.title.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                //Intent detailIntent = new Intent(view.getContext(), MainActivity.class);
                Intent detailIntent = new Intent(view.getContext(), DetailsActivityNgo.class);
                detailIntent.putExtra("Type", "Ngo");
                detailIntent.putExtra("ImageUrl", ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getId());
                detailIntent.putExtra("Name", ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getLawerName());
                detailIntent.putExtra("Address", ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getAddress());
                detailIntent.putExtra("Phone", ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getPhone());
                detailIntent.putExtra("Email", ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getEmail());
                detailIntent.putExtra("Rating", ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getRating());
                detailIntent.putExtra("About", ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getAboutUS());
                detailIntent.putExtra("Thumb", ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getThumb());
                NgoAdpator.this.context.startActivity(detailIntent);
            }
        });
        holder.shortDesc.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                //Intent detailIntent = new Intent(view.getContext(), MainActivity.class);
               Intent detailIntent = new Intent(view.getContext(), DetailsActivityNgo.class);
                detailIntent.putExtra("Type", "Ngo");
                detailIntent.putExtra("ImageUrl", ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getId());
                detailIntent.putExtra("Name", ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getLawerName());
                detailIntent.putExtra("Address", ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getAddress());
                detailIntent.putExtra("Phone", ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getPhone());
                detailIntent.putExtra("Email", ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getEmail());
                detailIntent.putExtra("Rating", ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getRating());
                detailIntent.putExtra("About", ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getAboutUS());
                detailIntent.putExtra("Thumb", ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getThumb());
                NgoAdpator.this.context.startActivity(detailIntent);
            }
        });
        holder.likeImage.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NgoAdpator.this.requestCategoryData(NgoAdpator.this.session.getEmail(), ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getId());
            }
        });
        holder.shareImage.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.SUBJECT", ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getLawerName());
                intent.putExtra("android.intent.extra.TEXT", "Address: " + ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getAddress() + "\n Phone: " + ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getPhone() + "\nEmail: " + ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getEmail() + "\n About: " + ((NgoPojo) NgoAdpator.this.ungalList.get(position)).getAboutUS());
                NgoAdpator.this.context.startActivity(Intent.createChooser(intent, "NGO"));
            }
        });
    }

    public int getItemCount() {
        return this.ungalList.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void onClick(View view) {
        if (this.mOnItemClickListener != null) {
            this.mOnItemClickListener.onItemClick(view, String.valueOf(view.getTag()));
        }
    }

    private void requestCategoryData(String email, String id) {
        final ProgressDialog pDialog = new ProgressDialog(this.context);
        pDialog.setMessage("Please wait...");
        pDialog.show();
        Volley.newRequestQueue(this.context).add(new StringRequest(1, "http://ungalbaaz.com/mobile-app/ngo_like.php?email=" + email + "&ngoID=" + id, new Listener<String>() {
            public void onResponse(String response) {
                Log.d("Response", response);
                if (response.contains("already liked")) {
                    pDialog.dismiss();
                    Toast.makeText(NgoAdpator.this.context, "Thanks. You already liked this NGO!", Toast.LENGTH_SHORT).show();
                } else if (response.contains("liked")) {
                    pDialog.dismiss();
                    Toast.makeText(NgoAdpator.this.context, "Thanks for like!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
            }
        }));
    }
}
