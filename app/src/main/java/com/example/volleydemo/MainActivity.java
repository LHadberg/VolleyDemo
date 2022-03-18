package com.example.volleydemo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.volleydemo.dataservice.DataServiceListener;
import com.example.volleydemo.dataservice.PersonDataService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private LinearLayout llPersonListContainer;
    private FloatingActionButton fabAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MyData", "onCreate");

        //GET COMPONENTS BY IDS
        llPersonListContainer = findViewById(R.id.llPersonListContainer);
        fabAddButton = findViewById(R.id.fabBtn);

        //DATA SERVICE
        PersonDataService dataService = new PersonDataService(MainActivity.this);

        //SET INITIAL PERSONS
        getAllPersons(dataService);


        //fabAddButton Event Handlers
        ActivityResultLauncher<Intent> nameLauncher;
        nameLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        getAllPersons(dataService);
                        Toast.makeText(MainActivity.this, "From Add Person Activity", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        fabAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPersonActivity.class);
                nameLauncher.launch(intent);
            }
        });
    }

    private Button generatePersonButton(int i) {
        Button btn = new Button(MainActivity.this);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        btnParams.weight = 1;
        btn.setLayoutParams(btnParams);
        return btn;
    }

    private void getAllPersons(PersonDataService dataService) {
        Log.d("MyData", "getAllPersons called");

        LinearLayout llPersonList = new LinearLayout(this);
        llPersonList.setOrientation(LinearLayout.VERTICAL);

        //LAYOUT PARAMS
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        llPersonList.setLayoutParams(layoutParams);

        //GET ALL
        dataService.getAllPersonsAsObjects(new DataServiceListener() {
            @Override
            public void onSuccess(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = (JSONObject) response.get(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Button btn = generatePersonButton(i);
                    int finalI = i;
                    JSONObject finalJsonObject = jsonObject;
                    btn.setOnClickListener(view -> {
                        Intent intent = new Intent(MainActivity.this, EditPersonActivity.class);
                        try {
                            intent.putExtra("personId", finalJsonObject.getInt("id"));
                            Log.d("MyData", "PersonId before intent: " + response.getJSONObject(finalI).getInt("personId"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(intent);
                    });
                    try {
                        btn.setText(jsonObject.getString("name"));
                        Log.d("MyData", jsonObject.getString("name") + " added to llPersonList");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    llPersonList.addView(btn);
                }
                llPersonListContainer.removeAllViews();
                llPersonListContainer.addView(llPersonList);
            }

            @Override
            public void onSuccess(JSONObject jsonObject) {
            }

            @Override
            public void onError(String error) {
                Log.d("MyData", "getAllPersons error: " + error);
            }
        });
    }
}