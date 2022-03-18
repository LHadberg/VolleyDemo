package com.example.volleydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.volleydemo.dataservice.DataServiceListener;
import com.example.volleydemo.dataservice.PersonDataService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddPersonActivity extends AppCompatActivity {
    private EditText etAddFullName, etAddEmail, etAddNote;
    private Button btnAddPerson, btnAddDiscard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        //GET COMPONENTS BY ID
        etAddFullName = findViewById(R.id.etAddFullName);
        etAddEmail = findViewById(R.id.etAddEmail);
        etAddNote = findViewById(R.id.etAddNote);
        btnAddPerson = findViewById(R.id.btnAddPerson);
        btnAddDiscard = findViewById(R.id.btnAddDiscard);

        //DATA SERVICE
        PersonDataService dataService = new PersonDataService(AddPersonActivity.this);

        //etAddFullName onClickHandler
        btnAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostPerson(dataService);
                Intent returnToMain = new Intent(AddPersonActivity.this, MainActivity.class);
                startActivity(returnToMain);
            }
        });
        //etAddFullName onClickHandler
        btnAddDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnToMain = new Intent(AddPersonActivity.this, MainActivity.class);
                startActivity(returnToMain);
                finish();
            }
        });
    }

    private void PostPerson(PersonDataService dataService){
        //POST PERSON
        JSONObject j = new JSONObject();
        try {
            j.put("name", etAddFullName.getText().toString());
            j.put("email", etAddEmail.getText().toString());
            j.put("note", etAddNote.getText().toString());

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
    };
}