package com.dev.manan.adminappec2018;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText username, password;
    private String usr, pass;
    private Button login;
    private String url = "https://elementsculmyca2018.herokuapp.com/api/v1/admin/login/";
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.loginButton);

        prefs=getApplicationContext().getSharedPreferences("com.dev.manan.adminappec2018", Context.MODE_PRIVATE);

        //final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //progressDialog.show();
                usr = username.getText().toString();
                pass = password.getText().toString();

                if (usr.equals("brixx") && pass.equals("12345678")) {

                    prefs.edit().putString("token", "63617168").apply();
                    Intent i = new Intent(LoginActivity.this, BrixxActivity.class);
                    startActivity(i);
                }

                if (usr.equals("manan") && pass.equals("12345678")) {

                    prefs.edit().putString("token", "63617169").apply();
                    Intent i = new Intent(LoginActivity.this, BrixxActivity.class);
                    startActivity(i);
                }

                /*if(usr.equals("brixx") && pass.equals("123456")){
                    Intent brixx = new Intent(LoginActivity.this, BrixxActivity.class);
                    startActivity(brixx);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                }*/


                /*RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {

                                System.out.println(response);
                                progressDialog.dismiss();

                                prefs.edit().putString("token", response).apply();
                                Intent intent=new Intent(LoginActivity.this,BrixxActivity.class);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {

                    @Override
                    protected Map<String, String> getParams()
                    {

                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("username", usr);
                        params.put("password", pass);
                        return params;
                    }
                };
                requestQueue.add(postRequest);
            }*/
            }

        });
    }
}
