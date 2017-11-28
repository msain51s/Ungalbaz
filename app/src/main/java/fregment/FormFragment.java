package fregment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog.Builder;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.share.internal.ShareConstants;
import com.unglibaaz.ungli.AppController;
import com.unglibaaz.ungli.R;

import net.DataPart;
import net.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import SessionManagement.SessionManager;
import adapter.CustomAdapter;

public class FormFragment extends Fragment {
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static int RESULT_LOAD_FILE = 1;
    Drawable btnPostDrawable;
    Drawable btnProofDrawable;
    String[] country = new String[]{"Ungal", "Suggestion", "Sahayata"};
    Date curDate;
    String dateToStr;
    EditText editTextDetail;
    EditText editTextTitle;
    String fileName = "";
    String fileNameTimeStamp = "";
    String filePath = "";
    private Uri fileUri;
    String finalUploadUrl;
    String[] helpType = new String[]{"Lawer", "NGO", "Administration", "Other"};
    Bitmap mBitmap;
    ImageView proofBtn;
    LinearLayout relSahataType;
    String sahaytaType = AppEventsConstants.EVENT_PARAM_VALUE_YES;
    String selectedContentType = AppEventsConstants.EVENT_PARAM_VALUE_YES;
    String selectedFormType = "Ungal";
    SessionManager session;
    ImageView submitBtn;
    String txtDetail;
    TextView txtPostType;
    TextView txtSahatyaType;
    String txtTitle;
    TextView txtvTitle;
    TextView txvtDetail;
    Typeface typeface;
    String ungalbaazEmail;
    TextView heading1, heading2, heading3;
    int width;
    public void performUpload(View view) {

        txtTitle = editTextTitle.getText().toString();
        txtDetail = editTextDetail.getText().toString();
        ungalbaazEmail = session.getEmail();
        curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        dateToStr = format.format(curDate);
        if (txtTitle.equals("") || txtDetail.equals("")) {
            Toast.makeText(getActivity(), getString(R.string.empty_message), Toast.LENGTH_SHORT).show();
        } else if (selectedFormType.equals("Ungal")) {
            if (mBitmap == null) {
                Toast.makeText(getActivity(), getString(R.string.not_attach_message), Toast.LENGTH_SHORT).show();
                return;
            }
            selectedContentType = AppEventsConstants.EVENT_PARAM_VALUE_YES;
            finalUploadUrl = "http://ungalbaaz.com/mobile-app/ungalbaaz-post.php?";
            fileNameTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            requestUploadUngalData(finalUploadUrl, fileNameTimeStamp);
            fileUpload(mBitmap, fileNameTimeStamp);
        } else if (selectedFormType.equals("Suggestion")) {
            if (mBitmap == null) {
                Toast.makeText(getActivity(), getString(R.string.not_attach_message), Toast.LENGTH_SHORT).show();
                return;
            }
            selectedContentType = "2";
            finalUploadUrl = "http://ungalbaaz.com/mobile-app/suggestion-post.php?";
            fileNameTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            requestUploadSuggetionData(finalUploadUrl, fileNameTimeStamp);
            fileUpload(mBitmap, fileNameTimeStamp);
        } else if (!selectedFormType.equals("Sahayata")) {
        } else {
            if (mBitmap == null) {
                Toast.makeText(getActivity(), getString(R.string.not_attach_message), Toast.LENGTH_SHORT).show();
                return;
            }
            selectedContentType = "3";
            finalUploadUrl = "http://ungalbaaz.com/mobile-app/sahayata-post.php?";
            fileNameTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            requestUploadSahayataData(finalUploadUrl, fileNameTimeStamp);
            fileUpload(mBitmap, fileNameTimeStamp);
        }

    }



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.typeface = Typeface.createFromAsset(getActivity().getAssets(), "Verdana.ttf");
        this.session = new SessionManager(getActivity());
        this.ungalbaazEmail = this.session.getEmail();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.forms_fragment, container, false);
        try {
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getSize(size);
                 width = size.x;
                //int height = size.y;
            }
        }catch (Exception e)
        {

        }
        txtvTitle = (TextView) mView.findViewById(R.id.txtTitle);
        txvtDetail = (TextView) mView.findViewById(R.id.txtDetail);
        txtPostType = (TextView) mView.findViewById(R.id.txtPostType);
        editTextTitle = (EditText) mView.findViewById(R.id.editTitle);
        editTextDetail = (EditText) mView.findViewById(R.id.editTextDetails);

        heading1 = (TextView) mView.findViewById(R.id.heading1);
        heading2 = (TextView) mView.findViewById(R.id.heading2);
        heading3 = (TextView) mView.findViewById(R.id.heading3);

   //     heading1.setTypeface(this.typeface);
        heading3.setTypeface(this.typeface);
        heading2.setTypeface(this.typeface);
        txtvTitle.setTypeface(this.typeface);

        txvtDetail.setTypeface(this.typeface);
        txtPostType.setTypeface(this.typeface);
        proofBtn = (ImageView) mView.findViewById(R.id.btnProof);
        submitBtn = (ImageView) mView.findViewById(R.id.uploadPost);

        this.relSahataType = (LinearLayout) mView.findViewById(R.id.subMainLayoutSahayataType);
        Spinner spin = (Spinner) mView.findViewById(R.id.spinnerFormSelectiona);
        spin.setAdapter(new CustomAdapter(getActivity(), this.country));
        // spin.setOnItemSelectedListener(new C06953());
        Spinner spinHelp = (Spinner) mView.findViewById(R.id.spinnerSahayata);
        spinHelp.setAdapter(new CustomAdapter(getActivity(), this.helpType));

        spinHelp.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (FormFragment.this.helpType[i].equals("Lawyer")) {
                    FormFragment.this.sahaytaType = AppEventsConstants.EVENT_PARAM_VALUE_YES;
                } else if (FormFragment.this.helpType[i].equals("NGO")) {
                    FormFragment.this.sahaytaType = "2";
                } else if (FormFragment.this.helpType[i].equals("Administration")) {
                    FormFragment.this.sahaytaType = "3";
                } else if (FormFragment.this.helpType[i].equals("Other")) {
                    FormFragment.this.sahaytaType = "4";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ungalimageSet();

        spin.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (country[i].equals("Ungal")) {
                    selectedFormType = "Ungal";
                    ungalimageSet();
                    editTextTitle.setText("");
                    editTextDetail.setText("");
                    relSahataType.setVisibility(View.INVISIBLE);
                    txtvTitle.setText(getActivity().getResources().getString(R.string.ungal_Tit));
                    txvtDetail.setText(getActivity().getResources().getString(R.string.ungal_details));
                } else if (country[i].equals("Suggestion")) {
                    selectedFormType = "Suggestion";
                    sussetionimageSet();
                    editTextTitle.setText("");
                    editTextDetail.setText("");
                    relSahataType.setVisibility(View.INVISIBLE);
                    txtvTitle.setText(getActivity().getResources().getString(R.string.suggestion_Title));
                    txvtDetail.setText(getActivity().getResources().getString(R.string.suggestion_Detail));
                } else if (country[i].equals("Sahayata")) {
                    selectedFormType = "Sahayata";

                    shayataimageSet();

                    editTextTitle.setText("");
                    editTextDetail.setText("");
                    relSahataType.setVisibility(View.VISIBLE);
                    txtvTitle.setText(getActivity().getResources().getString(R.string.sahayata_Title));
                    txvtDetail.setText(getActivity().getResources().getString(R.string.sahayata_Detail));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        proofBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FormFragment.this.selectedFormType.equals("Ungal")) {
                    FormFragment.this.RefereceAlertDialog("Ungal", FormFragment.this.getContext());
                } else if (FormFragment.this.selectedFormType.equals("Suggestion")) {
                    FormFragment.this.RefereceAlertDialog("Suggestion", FormFragment.this.getContext());
                } else if (FormFragment.this.selectedFormType.equals("Sahayata")) {
                    FormFragment.this.RefereceAlertDialog("Sahayata", FormFragment.this.getContext());
                }
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performUpload(null);
            }
        });


        editTextDetail.setImeOptions(EditorInfo.IME_ACTION_DONE);
        return mView;
    }


    private void RefereceAlertDialog(String title, Context mContext) {
        Builder alert = new Builder(mContext);
        alert.setTitle((CharSequence) title);
        alert.setMessage((CharSequence) "Select proof from:");
        alert.setPositiveButton((CharSequence) "Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra("output", fileUri);
                FormFragment.this.startActivityForResult(intent, 100);
            }
        });
        alert.setNegativeButton((CharSequence) "Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent pickIntent = new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/* video/*");
                FormFragment.this.startActivityForResult(pickIntent, FormFragment.RESULT_LOAD_FILE);
            }
        });

        alert.create().show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == -1 && data != null) {
                try {

                    //Uri uri=data.getExtras().get("data");
                    //this.mBitmap = Media.getBitmap(getActivity().getContentResolver(),uri );
                    if (data.getData() == null) {
                        mBitmap = (Bitmap) data.getExtras().get("data");
                    } else {
                        mBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == 0) {
                Toast.makeText(getActivity(), "User cancelled image capture", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Sorry! Failed to capture image", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 200 && resultCode != -1) {
            if (resultCode == 0) {
                Toast.makeText(getActivity(), "User cancelled video recording", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Sorry! Failed to record video", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == RESULT_LOAD_FILE && resultCode == -1 && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = new String[]{"_data"};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            this.filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            cursor.close();
            try {
                this.mBitmap = Media.getBitmap(getActivity().getContentResolver(), selectedImage);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void requestUploadUngalData(String basicUrl, String fileNameTimeStampStr) {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Uploading data & proof or reference...");
        final String str = fileNameTimeStampStr;
        pDialog.show();
        pDialog.setCancelable(false);

        Volley.newRequestQueue(getActivity()).add(new StringRequest(1, basicUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                pDialog.dismiss();
               // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(getActivity(), "Data upload fail ", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap();
                params.put(ShareConstants.WEB_DIALOG_PARAM_TITLE, txtTitle);
                params.put("short_desc", txtDetail);
                params.put("full_desc", txtDetail);
                params.put("email", ungalbaazEmail);
                params.put("date", dateToStr);
                params.put("main_image", str + ".jpg");
                return params;
            }
        });

        this.editTextTitle.setText("");
        this.editTextDetail.setText("");
    }

    private void requestUploadSahayataData(String basicUrl, String fileNameTimeStampStr) {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Uploading data & proof or reference...");
        final String str = fileNameTimeStampStr;
        pDialog.show();

        Volley.newRequestQueue(getActivity()).add(new StringRequest(1, basicUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                pDialog.dismiss();
               // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(getActivity(), "Data upload fail ", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap();
                params.put(ShareConstants.WEB_DIALOG_PARAM_TITLE, txtTitle);
                params.put("short_desc", txtDetail);
                params.put("full_desc", txtDetail);
                params.put("help_type", sahaytaType);
                params.put("date", dateToStr);
                params.put("email", ungalbaazEmail);
                params.put("main_image", str + ".jpg");
                return params;
            }
        });
        this.editTextTitle.setText("");
        this.editTextDetail.setText("");
    }

    private void requestUploadSuggetionData(String basicUrl, String fileNameTimeStampStr) {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Uploading data & proof or reference...");
        final String str = fileNameTimeStampStr;
        pDialog.show();

        Volley.newRequestQueue(getActivity()).add(new StringRequest(1, basicUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                pDialog.dismiss();
                //Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(getActivity(), "Data upload fail ", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap();
                params.put(ShareConstants.WEB_DIALOG_PARAM_TITLE, txtTitle);
                params.put("short_desc", txtDetail);
                params.put("full_desc", txtDetail);
                params.put("date", dateToStr);
                params.put("email", ungalbaazEmail);
                params.put("main_image", str + ".jpg");
                return params;
            }
        });
        this.editTextTitle.setText("");
        this.editTextDetail.setText("");



    }

    private void fileUpload(Bitmap bitmap, String fileNameTimeStampStr) {
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Uploading data & proof or reference...");
        pDialog.show();
        final String str = fileNameTimeStampStr;
        final Bitmap bitmap2 = bitmap;
        Volley.newRequestQueue(getActivity()).add(new VolleyMultipartRequest(1, "http://ungalbaaz.com/services/file_upload_service", new Listener<NetworkResponse>() {
            public void onResponse(NetworkResponse response) {
                try {

                    String ss=  new String(response.data);
                    Toast.makeText(getActivity(), "Data upload successfully ", Toast.LENGTH_SHORT).show();
                    //JSONObject obj = new JSONObject();
                    pDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    pDialog.dismiss();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        }) {
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap();
                params.put("userfile", new DataPart(str + ".jpg", getFileDataFromDrawable(bitmap2)));
                return params;
            }
        });
        this.mBitmap = null;
    }

    private String getPath(Uri contentUri) {
        String result = "";
        try {
            Cursor cursor = new CursorLoader(getActivity(), contentUri, new String[]{"_data"}, null, null, null).loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow("_data");
            cursor.moveToFirst();
            result = cursor.getString(column_index);
            cursor.close();
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    public Uri getOutputMediaFileUri1(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);
        if (mediaStorageDir.exists() || mediaStorageDir.mkdirs()) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            if (type == 1) {
                return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
            }
            if (type == 2) {
                return new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
            }
            return null;
        }
        Log.d(VolleyLog.TAG, "Oops! Failed create Android File Upload directory");
        return null;
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void sussetionimageSet()
    {
        if(width>500 && width<720) {
            btnProofDrawable = getActivity().getResources().getDrawable(R.drawable.selector_suggest_reference_five);
            btnPostDrawable = getActivity().getResources().getDrawable(R.drawable.selector_suggest_five);

        }else
        {
            btnProofDrawable = getActivity().getResources().getDrawable(R.drawable.selector_suggest_reference);
            btnPostDrawable = getActivity().getResources().getDrawable(R.drawable.selector_suggest);
        }
        finalImageset();

    }

    public void shayataimageSet()
    {

        if(width>500 && width<720) {
            btnProofDrawable = getActivity().getResources().getDrawable(R.drawable.selector_sahayata_reference_five);
            btnPostDrawable = getActivity().getResources().getDrawable(R.drawable.selector_sahayata_five);

        }else
        {
            btnProofDrawable = getActivity().getResources().getDrawable(R.drawable.selector_sahayata_reference);
            btnPostDrawable = getActivity().getResources().getDrawable(R.drawable.selector_sahayata);
        }

        finalImageset();
    }
    public void ungalimageSet()
    {
        if(width>500 && width<720) {
            btnProofDrawable = getActivity().getResources().getDrawable(R.drawable.selector_proof_ungal_five);
            btnPostDrawable = getActivity().getResources().getDrawable(R.drawable.selector_just_ungle_five);

        }else
        {
            btnProofDrawable = getActivity().getResources().getDrawable(R.drawable.selector_proof_ungal);
            btnPostDrawable = getActivity().getResources().getDrawable(R.drawable.selector_just_ungle);
        }

        finalImageset();
    }

    public void finalImageset()
    {
        btnProofDrawable.mutate();
        btnPostDrawable.mutate();
        proofBtn.setBackgroundDrawable(btnProofDrawable);
        submitBtn.setBackgroundDrawable(btnPostDrawable);
    }
}
