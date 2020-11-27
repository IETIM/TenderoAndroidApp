package edu.eci.ieti.takeiteasysk.model.auth;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

public class Token {
    private String token;
    private JSONObject payload;

    public Token(String token) {
        this.token = token;
    }

    public Token() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void parseToken(){
        java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
        String[] parts = token.split("\\."); // split out the "parts" (header, payload and signature)
        String headerJson = new String(decoder.decode(parts[0]));
        String payloadJson = new String(decoder.decode(parts[1]));
        try {
            payload = new JSONObject(payloadJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("PAYLOAD"+ payloadJson);
        //String signatureJson = new String(decoder.decode(parts[2]));
    }

    public JSONObject getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                '}';
    }
}
