package com.bearm.owlbotdictionary.DAL;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bearm.owlbotdictionary.R;
import com.bearm.owlbotdictionary.Interfaces.IVolleyCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WordDAO {

    private Context context;
    private RequestQueue queue;
    private JSONObject myResponse;

    public WordDAO(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    // Read
    public void requestData(String url, final IVolleyCallback callback) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("RESPONSE_OK", String.valueOf(response));

                myResponse = response;
                callback.getResponse(myResponse);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RESPONSE_NOT_OK", "Error: " + error);
                Toast.makeText(context, "There was an error. Please, try again later.", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", context.getString(R.string.credentials));
                return headers;
            }
        };

        queue.add(jsObjRequest);

    }


}
