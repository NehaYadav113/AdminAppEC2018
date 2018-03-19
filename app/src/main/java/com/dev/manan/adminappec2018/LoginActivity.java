package com.dev.manan.adminappec2018;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity {
    private EditText username, password;
    private String usr, pass;
    private Button login;
    private ImageView photoImageView;
    private String url = "https://elementsculmyca2018.herokuapp.com/api/v1/admin/login/";
    SharedPreferences prefs;
    private TextView devByTextView;
    private int unicode = 0x2764;
    String CLUB_NAME = "clubname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.loginButton);
        photoImageView = findViewById(R.id.iv_login_photo);
        devByTextView = findViewById(R.id.tv_developed_by_text);

        String s1 = "Developed with ";
        String s2 = " by ";
        String s3 = "<b> Manan! </b>";
//        SpannableStringBuilder s3 = new SpannableStringBuilder("Manan!");
//        s3.setSpan(new StyleSpan(Typeface.BOLD),0, s3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        devByTextView.setText(s1 + getEmojiByUnicode(unicode) + s2 + Html.fromHtml(s3));

        prefs = getApplicationContext().getSharedPreferences("com.dev.manan.adminappec2018", Context.MODE_PRIVATE);

        Picasso.get().load(R.drawable.background).fit().into(photoImageView);

        //final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());

        //Testing Purpose!
        username.setText("brixx");
        password.setText("12345678");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressDialog.show();
                usr = username.getText().toString();
                pass = password.getText().toString();

                if (usr.equals("brixx") && pass.equals("12345678")) {
                    prefs.edit().putString("token", "63617168").apply();
                    Intent i = new Intent(LoginActivity.this, BrixxActivity.class);
                    i.putExtra(CLUB_NAME, username.getText().toString() );
                    startActivity(i);
                }

                if (usr.equals("manan") && pass.equals("12345678")) {
                    prefs.edit().putString("token", "63617169").apply();
                    Intent i = new Intent(LoginActivity.this, BrixxActivity.class);
                    i.putExtra(CLUB_NAME, username.getText().toString() );
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

    public String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    public static Spanned getBoldString(String textToBold) {
        String resultant = null;
        resultant = "<b>" + textToBold + "</b>";
        return Html.fromHtml(resultant);
    }

}
