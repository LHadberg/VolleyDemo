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
import com.example.volleydemo.models.Person;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EditPersonActivity extends AppCompatActivity {
    private EditText etEditFullName, etEditEmail, etEditNote;
    private Button btnEditUpdate, btnEditRemove, btnEditDiscard;
    private Person selectedPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);

        //GET COMPONENTS BY IDS
        etEditFullName = findViewById(R.id.etEditFullName);
        etEditEmail = findViewById(R.id.etEditEmail);
        etEditNote = findViewById(R.id.etEditNote);
        btnEditUpdate = findViewById(R.id.btnEditUpdate);
        btnEditRemove = findViewById(R.id.btnEditRemove);
        btnEditDiscard = findViewById(R.id.btnEditDiscard);

        //DATA SERVICE
        PersonDataService dataService = new PersonDataService(EditPersonActivity.this);

        //RETRIEVE PROPERTIES
        Intent intent = getIntent();
        int personId = intent.getIntExtra("personId", 0);
        GetPersonById(personId, dataService);

        //btnEditUpdate
        btnEditUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PutPerson(selectedPerson, dataService);
            }
        });

        //btnEditRemove onClickHandler
        btnEditRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeletePerson(dataService);
            }
        });

        //btnEditDiscard onClickHandler
        btnEditDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnToMain = new Intent(EditPersonActivity.this, MainActivity.class);
                startActivity(returnToMain);
                finish();
            }
        });
    }

    private void DeletePerson(PersonDataService dataService) {
        //DELETE PERSON
        dataService.deletePersonById(selectedPerson.getId(), new DataServiceListener() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                Log.d("MyData", "deletePerson success" + jsonArray.toString());
                Intent returnToMain = new Intent(EditPersonActivity.this, MainActivity.class);
                startActivity(returnToMain);
                finish();
            }

            @Override
            public void onSuccess(JSONObject jsonObject) {
                Log.d("MyData", "deletePerson success" + jsonObject.toString());
                Intent returnToMain = new Intent(EditPersonActivity.this, MainActivity.class);
                startActivity(returnToMain);
                finish();
            }

            @Override
            public void onError(String error) {
                Log.d("MyData", "deletePerson error:" + error);
                Intent returnToMain = new Intent(EditPersonActivity.this, MainActivity.class);
                startActivity(returnToMain);
                finish();
            }
        });

    }

    private void PutPerson(Person p, PersonDataService dataService) {
        Person updatedPerson = new Person();
        updatedPerson.setId(p.getId());
        updatedPerson.setName(etEditFullName.getText().toString());
        updatedPerson.setEmail(etEditEmail.getText().toString());
        updatedPerson.setNote(etEditNote.getText().toString());

        //PUT PERSON
        dataService.putPersonById(updatedPerson, new DataServiceListener() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                Log.d("MyData", "putPerson success: " + jsonArray.toString());
                Intent returnToMain = new Intent(EditPersonActivity.this, MainActivity.class);
                startActivity(returnToMain);
                finish();
            }

            @Override
            public void onSuccess(JSONObject jsonObject) {
                Log.d("MyData", "putPerson success: " + jsonObject.toString());
                Intent returnToMain = new Intent(EditPersonActivity.this, MainActivity.class);
                startActivity(returnToMain);
                finish();
            }

            @Override
            public void onError(String error) {
                Log.d("MyData", "putPerson error: " + error);
                Intent returnToMain = new Intent(EditPersonActivity.this, MainActivity.class);
                startActivity(returnToMain);
                finish();
            }
        });
    }

    private void GetPersonById(int personId, PersonDataService dataService) {
        //GET BY ID
        dataService.getPersonById(personId, new DataServiceListener() {
            @Override
            public void onSuccess(JSONObject response) {
                Gson gson = new Gson();
                Person p = gson.fromJson(response.toString(), Person.class);
                selectedPerson = p;
                try {
                    etEditFullName.setText(response.getString("name"));
                    etEditEmail.setText(response.getString("email"));
                    etEditNote.setText(response.getString("note"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("MyData", "getPersonById success: " + p.toString());
            }

            @Override
            public void onError(String error) {
                Log.d("MyData", "getPersonById error: " + error);
            }

            @Override
            public void onSuccess(JSONArray jsonArray) {
            }
        });
    }
}