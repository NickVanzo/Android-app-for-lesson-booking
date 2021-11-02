package com.example.bookinglessons.Controller;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomRequest extends JsonObjectRequest {
    private String session_id = "";

    public CustomRequest(int method, String url, JSONObject jsonRequest,
                         Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public CustomRequest(int method, String url, JSONObject jsonRequest, String session_id,
                         Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.session_id = session_id;
    }


    @Override
    public Map getHeaders() throws AuthFailureError {
        Map headers = new HashMap();
        Log.d("In getHeaders", " -> session_id = " + session_id);
        if(!(session_id.equals(""))) {
            headers.put("Cookie", this.session_id);
        }
        return headers;
    }

}