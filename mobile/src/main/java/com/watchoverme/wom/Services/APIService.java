package com.watchoverme.wom.Services;

import com.watchoverme.wom.Models.Contact;
import com.watchoverme.wom.Models.Log;
import com.watchoverme.wom.Models.ServiceId;
import com.watchoverme.wom.Models.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Headers;


public interface APIService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })

    @POST("userloginprocessing")
    Call<String> processLogin(@Body User body);

    @POST("contactsprocessing")
    Call<List<Contact>> processContacts(@Body ServiceId serviceId);

    @POST("logsprocessing")
    Call<Log> processLogs(@Body Log logBody);

    @POST("interactionlogprocessing")
    Call<String> processInteractionLog(@Body ArrayList<Log> logArrayList);

    @POST("hourlylogsprocessing")
    Call<String> processHourlyLog(@Body Log logBody);
}
