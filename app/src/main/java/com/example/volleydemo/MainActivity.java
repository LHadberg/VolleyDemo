package com.example.volleydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PersonDataService dataService = new PersonDataService(MainActivity.this);

        dataService.getAllPersons(new DataServiceListener() {
            @Override
            public void onSuccess(JSONArray response) {
                Log.d("MyData", "getAllPersons success: " + response.toString());
            }

            @Override
            public void onSuccess(JSONObject jsonObject) { }

            @Override
            public void onError(String error) {
                Log.d("MyData", "getAllPersons error: " + error);
            }
        });

        dataService.getPersonById(3, new DataServiceListener() {
            @Override
            public void onSuccess(JSONObject response) {
                Gson gson = new Gson();
                Person p = gson.fromJson(response.toString(), Person.class);

                Log.d("MyData", "getPersonById success: " + p.toString());
            }

            @Override
            public void onError(String error) {
                Log.d("MyData", "getPersonById error: " + error);
            }

            @Override
            public void onSuccess(JSONArray jsonArray) { }
        });

        JSONObject j = new JSONObject();
        try {
            j.put("name", "Lazzz");
            j.put("email", "yaskii@hotmail.com");
            j.put("note", "henlo");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        dataService.postPerson(j, new DataServiceListener() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                Log.d("MyData", "postPerson success: " + jsonArray.toString());
            }

            @Override
            public void onSuccess(JSONObject jsonObject) {
                Log.d("MyData", "postPerson success: " + jsonObject.toString());
            }

            @Override
            public void onError(String error) {
                Log.d("MyData", "postPerson error: " + error);
            }
        });

        dataService.deletePersonById(28, new DataServiceListener() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                Log.d("MyData", "deletePerson success" + jsonArray.toString());
            }

            @Override
            public void onSuccess(JSONObject jsonObject) {
                Log.d("MyData", "deletePerson success" + jsonObject.toString());
            }

            @Override
            public void onError(String error) {
                Log.d("MyData", "deletePerson error:" + error);
            }
        });

        Person person = new Person();
        try {
            person.setId(3);
            person.setName("Lazoooor");
            person.setEmail("yaskicide@hotmail.com");
            person.setNote("henlo mi amigo");

        } catch (Exception e) {
            e.printStackTrace();
        }

        dataService.putPersonById(person, new DataServiceListener() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                Log.d("MyData", "putPerson success: " + jsonArray.toString());
            }

            @Override
            public void onSuccess(JSONObject jsonObject) {
                Log.d("MyData", "putPerson success: " + jsonObject.toString());
            }

            @Override
            public void onError(String error) {
                Log.d("MyData", "putPerson error: " + error);
            }
        });
    }
}