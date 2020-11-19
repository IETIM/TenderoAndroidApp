package edu.eci.ieti.takeiteasysk.services;

import edu.eci.ieti.takeiteasysk.model.auth.LoginWrapper;
import edu.eci.ieti.takeiteasysk.model.auth.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    String API_ROUTE = "login";
    @POST(API_ROUTE)
    public Call<Token> login(@Body LoginWrapper lw);
}
