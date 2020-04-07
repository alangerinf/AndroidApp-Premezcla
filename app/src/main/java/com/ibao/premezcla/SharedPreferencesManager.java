package com.ibao.premezcla;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.ibao.premezcla.models.User;

import java.util.Map;

public class SharedPreferencesManager {


    private static String TAG = SharedPreferencesManager.class.getSimpleName();
    private static String namePreferences_userdata = "data";

    //data user
    public static String user_id = "id";
    public static String user_user = "user";
    public static String user_name = "name";
    public static String user_password = "password";
    public static String user_modules = "modulelist";
    public static String user_current_module = "module";


    public static boolean changeMode(Context ctx, int mode){
        boolean flag = false;
        try{
            SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(user_current_module,mode);
            flag = editor.commit();
        }catch (Exception e){
            Log.d(TAG,"clearMode:"+e.toString());
            Toast.makeText(ctx,"clearMode:"+e.toString(), Toast.LENGTH_LONG).show();
        }
        return flag;
    }

    public static boolean clearMode(Context ctx){
        boolean flag = false;
        try{
        SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(user_current_module,0);
        flag = editor.commit();
        }catch (Exception e){
            Log.d(TAG,"clearMode:"+e.toString());
            Toast.makeText(ctx,"clearMode:"+e.toString(), Toast.LENGTH_LONG).show();
        }
        return flag;
    }

    public static boolean saveUser(Context ctx, User user){
        boolean flag = false;
        try{
            SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(user_id,user.getId());
            editor.putString(user_user,user.getUser());
            editor.putString(user_password,user.getPassword());
            editor.putString(user_modules,user.getModulesString());
            editor.putInt(user_current_module,user.getCurrentModule());
            editor.putString(user_name,user.getName());
            flag = editor.commit();
        }catch (Exception e){
            Log.d(TAG,"saveUser:"+e.toString());
            Toast.makeText(ctx,"saveUser:"+e.toString(), Toast.LENGTH_LONG).show();
        }
        return flag;
    }

    public static User getUser(Context ctx){
        User user = null;
            try {
                SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
                if(
                        preferences.contains(user_id)
                        &&
                        preferences.contains(user_user)
                        &&
                        preferences.contains(user_password)
                        &&
                        preferences.contains(user_current_module)
                        &&
                        preferences.contains(user_name)
                ){
                    //user.setId(preferences.getInt(user_id,0));
                    user = new User();
                    user.setId(preferences.getInt(user_id,0));
                    user.setUser(preferences.getString(user_user,""));
                    user.setPassword(preferences.getString(user_password,""));
                    user.setModulesString(preferences.getString(user_modules,""));
                    user.setCurrentModule(preferences.getInt(user_current_module,0));
                    user.setName(preferences.getString(user_name,""));
                }
            }catch (Exception e) {
                Log.d(TAG,"getUser:" + e.toString()) ;
                Toast.makeText(ctx, "getUser:" + e.toString(), Toast.LENGTH_LONG).show();
            }

        return user;
    }

    public static boolean deleteUser(Context ctx){
        boolean flag = false;
        try {
            SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            flag = editor.commit(); // commit changes
        }catch (Exception e) {
            Toast.makeText(ctx, "deleteUser:" + e.toString(), Toast.LENGTH_LONG).show();
        }
        return flag;
    }

    public static Map<String,?> getMapUser(Context ctx){
        try {
            SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
            Map<String,?> map = preferences.getAll();
            return  map;
        }catch (Exception e) {
            Toast.makeText(ctx, "getMapUser:" + e.toString(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

}
