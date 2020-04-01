package com.watchoverme.wom.Services;

import com.watchoverme.wom.Models.Contact;
import com.watchoverme.wom.Models.Log;
import com.watchoverme.wom.Models.LoginResponse;
import com.watchoverme.wom.Models.PhoneMeRequest;
import com.watchoverme.wom.Models.ServiceId;
import com.watchoverme.wom.Models.User;
import com.watchoverme.wom.Models.Watcher;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Headers;


public interface APIService {

    /*@Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })*/

    @FormUrlEncoded
    @POST("app_login.php")
    Call<LoginResponse> processLogin(@Field("user_name") String userName,
                                     @Field("user_pw") String userPassword);

    @FormUrlEncoded
    @POST("watcher_retrieval.php")
    Call<List<Watcher>> processWatcher(@Field("service_id") String serviceId);

    @POST("phoneMeRequest")
    Call<String> processPhoneMeRequest(@Body PhoneMeRequest phoneMeRequest);

    @POST("contactsprocessing")
    Call<List<Contact>> processContacts(@Body ServiceId serviceId);

    @POST("helpmecheck")
    Call<String> processHelpMe(@Body Log logBody);

    @POST("interactionlogprocessing")
    Call<String> processInteractionLog(@Body ArrayList<Log> logArrayList);

    @POST("hourlylogsprocessing")
    Call<String> processHourlyLog(@Body Log logBody);

    @GET("connectionCheck")
    Call<String> connectionCheck();
}
