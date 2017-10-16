package com.example.manpreet.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity{

    @InjectView(R.id.name)
    EditText eTxtName;


    @InjectView(R.id.button)
    Button btnRegister;

    User user;

    // Volley Library
    RequestQueue requestQueue;
    StringRequest stringRequest;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    boolean updateMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);


        requestQueue = Volley.newRequestQueue(this);

        user = new User();


        preferences = getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);
        editor = preferences.edit();


        Intent rcv = getIntent();
        updateMode = rcv.hasExtra(Util.KEY_USER);

       /* if(updateMode){
            user = (User)rcv.getSerializableExtra(Util.KEY_USER);
            eTxtName.setText(user.getName());
            eTxtEmail.setText(user.getEmail());
            eTxtPassword.setText(user.getPassword());
            btnRegister.setText("Update User");
        }*/

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.setName(eTxtName.getText().toString().trim());


                registerUserOnServer();
            }
        });
    }

    void registerUserOnServer(){

        String url = Util.REGISTER_ENDPOINT;



        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();

                    /*if(success == 1){

                        if(!updateMode) {
                            editor.putBoolean(Util.KEY_LOGREG, true);
                            editor.commit();


                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            finish();
                        }
                    }*/

                }catch (Exception e){
                    Toast.makeText(MainActivity.this,"Exception: "+e,Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Error: "+error,Toast.LENGTH_LONG).show();
            }
        })

        /*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();

                if(updateMode){
                    map.put("id",String.valueOf(user.getUid()));
                }

                map.put("name",user.getName());


                return map;
            }
        }*/
        ;


        // Execute Request
        requestQueue.add(stringRequest);

    }
}

