package SessionManagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.unglibaaz.ungli.LoginActivity;

import java.util.HashMap;

public class SessionManager {
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAME = "name";
    private static final String PREF_NAME = "UngalbaazPref";
    int PRIVATE_MODE = 0;
    Context _context;
    Editor editor;
    SharedPreferences pref;

    public SessionManager(Context context) {
        this._context = context;
        this.pref = this._context.getSharedPreferences(PREF_NAME, this.PRIVATE_MODE);
        this.editor = this.pref.edit();
    }

    public void createLoginSession(String name, String email) {
        this.editor.putBoolean(IS_LOGIN, true);
        this.editor.putString("name", name);
        this.editor.putString("email", email);
        this.editor.commit();
    }

    public void checkLogin() {
        if (!isLoggedIn()) {
            Intent i = new Intent(this._context, LoginActivity.class);
            i.addFlags(67108864);
            i.setFlags(268435456);
            this._context.startActivity(i);
        }
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap();
        user.put("name", this.pref.getString("name", null));
        user.put("email", this.pref.getString("email", null));
        return user;
    }

    public void logoutUser() {
        this.editor.clear();
        this.editor.commit();
        Intent i = new Intent(this._context, LoginActivity.class);
        i.addFlags(67108864);
        i.setFlags(268435456);
        this._context.startActivity(i);
    }

    public boolean isLoggedIn() {
        return this.pref.getBoolean(IS_LOGIN, false);
    }

    public String getEmail() {
        return this.pref.getString("email", "email");
    }
}
