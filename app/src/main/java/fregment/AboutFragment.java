package fregment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unglibaaz.ungli.R;

public class AboutFragment extends Fragment {
    private final String htmlAboutText = "<body ><h1>Ungalbaaz hai kaun?</h1><p style=\"text-align:justify\"> Ungalbaaz is one who Raises his voice when he sees wrong, he Raises his voice against injustice and inequality.</p><p class=\"justify\" > Somewhere, Sometimes, we all have been an Ungalbaaz in our lives. We have had raised our voice against injustice and wrong doings.But somehow now, it seems we have lost our inner conscience to stand against injustice, somehow, we have lost our souls to stand up and raise our voice against wrong things. somehow, we have lost being an Ungalbaaz.</p><p style=\"text-align:justify\"> Today when we see a wrong happening, a weak being bullied and we still choose to ignore or turn the other way.  We do it because of our fear or because of our circumstances or it’s just because we care little about it…</p><p style=\"text-align:justify\"> Ungalbaaz is a platform which takes your choice to fight back to its logical conclusion. It’s unlike the NEWS Channels of today, which only highlight a problem, but does not woe to resolve the issue.If we are to bring about a change in our society, we need to work collectively, we need to raise our voice to wrong and injustice, we need to be an Ungalbaaz.</p><p style=\"text-align:justify\">Here you have a mate to provoke the Ungalbaaz in YOU again, but for a good cause!</p><p> Be the change…\n    Be Ungalbaaz !!</p> </body>";
    TextView txtAbout;
    Typeface typeface;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.typeface = Typeface.createFromAsset(getActivity().getAssets(), "Verdana.ttf");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.about_fragment, container, false);
        this.txtAbout = (TextView) mView.findViewById(R.id.txtAbout);
        this.txtAbout.setTypeface(this.typeface);
        this.txtAbout.setText(Html.fromHtml("<body ><h1>Ungalbaaz hai kaun?</h1><p style=\"text-align:justify\"> Ungalbaaz is one who Raises his voice when he sees wrong, he Raises his voice against injustice and inequality.</p><p class=\"justify\" > Somewhere, Sometimes, we all have been an Ungalbaaz in our lives. We have had raised our voice against injustice and wrong doings.But somehow now, it seems we have lost our inner conscience to stand against injustice, somehow, we have lost our souls to stand up and raise our voice against wrong things. somehow, we have lost being an Ungalbaaz.</p><p style=\"text-align:justify\"> Today when we see a wrong happening, a weak being bullied and we still choose to ignore or turn the other way.  We do it because of our fear or because of our circumstances or it’s just because we care little about it…</p><p style=\"text-align:justify\"> Ungalbaaz is a platform which takes your choice to fight back to its logical conclusion. It’s unlike the NEWS Channels of today, which only highlight a problem, but does not woe to resolve the issue.If we are to bring about a change in our society, we need to work collectively, we need to raise our voice to wrong and injustice, we need to be an Ungalbaaz.</p><p style=\"text-align:justify\">Here you have a mate to provoke the Ungalbaaz in YOU again, but for a good cause!</p><p> Be the change…\n    Be Ungalbaaz !!</p> </body>"));
        return mView;
    }
}
