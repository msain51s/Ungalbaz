package adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.unglibaaz.ungli.R;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String[] countryNames;
    LayoutInflater inflter;
    Typeface typeface;

    public CustomAdapter(Context applicationContext, String[] countryNames) {
        this.context = applicationContext;
        this.countryNames = countryNames;
        this.inflter = LayoutInflater.from(applicationContext);
        this.typeface = Typeface.createFromAsset(applicationContext.getAssets(), "Verdana.ttf");
    }

    public int getCount() {
        return this.countryNames.length;
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        view = this.inflter.inflate(R.layout.custom_spinner, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setTypeface(this.typeface);
        names.setText(this.countryNames[i].toUpperCase());
        names.setGravity(Gravity.CENTER);
        return view;
    }
}
