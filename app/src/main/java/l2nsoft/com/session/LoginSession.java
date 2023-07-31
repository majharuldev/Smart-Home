package l2nsoft.com.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import l2nsoft.com.activity.Login;
import l2nsoft.com.activity.MainActivity;

public class LoginSession {


    private static SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "user_credential";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_TOKEN = "uid";

    public LoginSession(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.commit();

        return;

    }

    public void CreateUserSession( String uid) {

      //  editor.putString(KEY_ID, id);
        editor.putString(KEY_TOKEN, uid);
        editor.commit();


    }

    public boolean checkLogin() {

        if (!this.isUserLoggedIn()) {

            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);

            return true;
        }
        return false;
    }

    public  boolean isUserLoggedIn() {
        return !getKeyToken().isEmpty();
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, Login.class);
        context.startActivity(intent);
    }


    public  String getKeyToken() {
        return sharedPreferences.getString(KEY_TOKEN, "");
    }


    public String getKeyId() {
        return sharedPreferences.getString(KEY_ID, "");
    }

    public String getKeyName() {
        return sharedPreferences.getString(KEY_NAME, "");
    }

    public String getKeyEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }


}
