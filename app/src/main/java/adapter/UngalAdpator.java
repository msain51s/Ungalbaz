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
import com.facebook.share.internal.ShareConstants;
import com.unglibaaz.ungli.DetailsActivity;
import com.unglibaaz.ungli.MainActivity;
import com.unglibaaz.ungli.R;

import net.UngalPojo;

import java.util.List;

import SessionManagement.SessionManager;


public class UngalAdpator extends Adapter<UngalAdpator.MyViewHolder> implements OnClickListener {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    SessionManager session;
    UngalPojo ungal;
    private List<UngalPojo> ungalList;

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

    public UngalAdpator(Context context, List<UngalPojo> moviesList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.ungalList = moviesList;
        this.context = context;
        this.session = new SessionManager(context);
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, final int position) {
        this.ungal = (UngalPojo) this.ungalList.get(position);
        holder.title.setText(this.ungal.getTitle());
        holder.shortDesc.setText(this.ungal.getShor_description());
        holder.title.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent detailIntent = new Intent(view.getContext(), DetailsActivity.class);
                detailIntent.putExtra("Type", "Ungals");
                detailIntent.putExtra(ShareConstants.WEB_DIALOG_PARAM_ID, ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getId());
                detailIntent.putExtra(ShareConstants.WEB_DIALOG_PARAM_TITLE, ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getTitle());
                detailIntent.putExtra("shor_description", ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getShor_description());
                detailIntent.putExtra("description", ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getDescription());
                detailIntent.putExtra("image1", ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getMainImage());
                detailIntent.putExtra("image2", ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getMainImage2());
                detailIntent.putExtra("video", ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getVideo());
               context.startActivity(detailIntent);
            }
        });
        holder.shortDesc.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent detailIntent = new Intent(view.getContext(), DetailsActivity.class);
                detailIntent.putExtra("Type", "Ungals");
                detailIntent.putExtra(ShareConstants.WEB_DIALOG_PARAM_ID, ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getId());
                detailIntent.putExtra(ShareConstants.WEB_DIALOG_PARAM_TITLE, ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getTitle());
                detailIntent.putExtra("shor_description", ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getShor_description());
                detailIntent.putExtra("description", ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getDescription());
                detailIntent.putExtra("image1", ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getMainImage());
                detailIntent.putExtra("image2", ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getMainImage2());
                detailIntent.putExtra("video", ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getVideo());
               context.startActivity(detailIntent);
            }
        });
        holder.likeImage.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UngalAdpator.this.requestCategoryData(UngalAdpator.this.session.getEmail(), ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getId());
            }
        });
        holder.shareImage.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {

                try {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("text/plain");
                    intent.putExtra("android.intent.extra.SUBJECT", ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getTitle());
                    intent.putExtra("android.intent.extra.TEXT", "Ungal Title: " + ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getTitle() + "\n Description: " + ((UngalPojo) UngalAdpator.this.ungalList.get(position)).getDescription());
                    context.startActivity(Intent.createChooser(intent, "UNGAL"));
                }catch (Exception e)
                {

                }

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
        Volley.newRequestQueue(this.context).add(new StringRequest(1, "http://ungalbaaz.com/mobile-app/ungal_like.php?email=" + email + "&ungalID=" + id, new Listener<String>() {
            public void onResponse(String response) {
                Log.d("Response", response);
                if (response.contains("already liked")) {
                    pDialog.dismiss();
                    Toast.makeText(UngalAdpator.this.context, "Thanks. You already liked this UNGAL!", Toast.LENGTH_SHORT).show();
                } else if (response.contains("liked")) {
                    pDialog.dismiss();
                    Toast.makeText(UngalAdpator.this.context, "Thanks for like!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
            }
        }));
    }
}
