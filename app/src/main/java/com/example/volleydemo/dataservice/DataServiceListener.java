package com.example.volleydemo.dataservice;

import org.json.JSONArray;
import org.json.JSONObject;

public interface DataServiceListener {
    public void onSuccess(JSONArray jsonArray);

    public void onSuccess(JSONObject jsonObject);

    public void onError(String error);

}