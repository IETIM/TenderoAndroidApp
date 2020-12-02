package edu.eci.ieti.takeiteasysk.storage;

import android.content.Context;
import android.content.SharedPreferences;

import edu.eci.ieti.takeiteasysk.R;
import edu.eci.ieti.takeiteasysk.model.auth.Token;


/**
 * @author Santiago Carrillo
 * 4/23/19.
 */
public class Storage
{

    private final String TOKEN_KEY = "TOKEN_KEY";
    private final String SHOP_ID="SHOP_ID";
    private final String SHOP_NAME = "SHOP_NAME";

    private final SharedPreferences sharedPreferences;

    public Storage( Context context )
    {
        this.sharedPreferences =
                context.getSharedPreferences( context.getString( R.string.preferece_file_key ), Context.MODE_PRIVATE );

    }

    public String getToken()
    {
        return sharedPreferences.getString( TOKEN_KEY, null );
    }

    public String getShopId(){
        return sharedPreferences.getString(SHOP_ID,null);
    }

    public String getShopName(){return sharedPreferences.getString(SHOP_NAME,null);};
    public void setShopId(String id){
        sharedPreferences.edit().putString(SHOP_ID,id).apply();
    }

    public void saveToken( Token token )
    {
        sharedPreferences.edit().putString( TOKEN_KEY, token.getToken() ).apply();
    }

    public boolean containsToken()
    {
        return sharedPreferences.contains( TOKEN_KEY );
    }

    public void clear()
    {
        sharedPreferences.edit().remove( TOKEN_KEY ).apply();
        sharedPreferences.edit().remove(SHOP_ID).apply();
    }

}

