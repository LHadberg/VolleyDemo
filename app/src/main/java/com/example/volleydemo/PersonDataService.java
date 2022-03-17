package com.example.volleydemo;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class PersonDataService {
    public static final String PERSON_API = "http://10.0.2.2:8080/demo1/api/person";
    private Context ctx;

    public PersonDataService(Context ctx) {
        this.ctx = ctx;
    }

    public void getAllPersons(DataServiceListener serviceListener) {
        String url = PERSON_API;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        serviceListener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        serviceListener.onError(error.getMessage());
                    }
                }
        );

        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(ctx).addToRequestQueue(jsonArrayRequest);
    }

    public void getPersonById(int personId, DataServiceListener serviceListener) {
        String url = PERSON_API + "/" + personId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        serviceListener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        serviceListener.onError(error.getMessage());
                    }
                }
        );
        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public int postPerson(JSONObject person, DataServiceListener dataServiceListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                PERSON_API,
                person,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dataServiceListener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dataServiceListener.onError(error.getMessage());
                    }
                }
        );
        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
        return 1;
    }

    public void deletePersonById(int personId, DataServiceListener serviceListener) {
        String url = PERSON_API + "/" + personId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        serviceListener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        serviceListener.onError(error.getMessage());
                    }
                }
        );
        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }



    public void putPersonById(Person person, DataServiceListener serviceListener) {
        String url = PERSON_API;

        Gson gson = new Gson();
        String jsonString = gson.toJson(person, Person.class);
        JSONObject json = new JSONObject();
        try {
            json = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        serviceListener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        serviceListener.onError(error.getMessage());
                    }
                }
        );
        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }
}
