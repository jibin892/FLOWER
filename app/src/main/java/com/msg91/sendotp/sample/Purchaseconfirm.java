package com.msg91.sendotp.sample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Purchaseconfirm extends AppCompatActivity implements PaymentResultListener {
EditText name,ph,email,add,nos;
Button pay;
Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchaseconfirm);
         name=findViewById(R.id.pnm);
        ph=findViewById(R.id.pphn);
        email=findViewById(R.id.pem);
       add=findViewById(R.id.pad);
       pay=findViewById(R.id.pay);
        nos=findViewById(R.id.nos);
       i=getIntent();
       pay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


startPayment();


           }
       });
    }
    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Flower Shop");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", String.valueOf(Integer.parseInt(i.getStringExtra("amount"))*(Integer.parseInt(nos.getText().toString()))*100));

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "9876543210");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(final String s) {
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/Flower_maegument_system/product_payment.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//If we are getting success from server
                        Toast.makeText(Purchaseconfirm.this, response, Toast.LENGTH_LONG).show();
//
                        ph.getText().clear();
                        email.getText().clear();
                        name.getText().clear();
                        add.getText().clear();
                        nos.getText().clear();

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json_obj = jsonArray.getJSONObject(i);
//ba = json_obj.getString("balance");


                            }
//Toast.makeText(Recharge.this, "your new balnce is "+ba, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//Adding parameters to request
                params.put("tid", s);
                params.put("nm", name.getText().toString());
                params.put("ph", ph.getText().toString());
                params.put("tdance", email.getText().toString());
                params.put("tname", add.getText().toString());
                params.put("nos", nos.getText().toString());
                params.put("amt", i.getStringExtra("amount"));
                params.put("pnm", i.getStringExtra("pname"));
                params.put("pd", i.getStringExtra("pd"));
//                params.put("temail", email.getText().toString());
//                params.put("pa", email.getText().toString());
//                            params.put("texperiance", dblog.getText().toString());
// Toast.makeText(MainActivity.this,"submitted",Toast.LENGTH_LONG).show();

//returning parameter
                return params;
            }

        };

// m = Integer.parseInt(ba) - Integer.parseInt(result.getContents());
// balance.setText(m+"");


        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(Purchaseconfirm.this);
        requestQueue.add(stringRequest);
      /*  StringRequest stringRequest1;
        stringRequest1 = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/Flower_maegument_system/c_copy1.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//If we are getting success from server
                        Toast.makeText(Purchaseconfirm.this, response, Toast.LENGTH_LONG).show();
//





                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//Adding parameters to request
                params.put("tid", s);
                params.put("nm", name.getText().toString());
                params.put("ph", ph.getText().toString());
                params.put("tdance", email.getText().toString());
                params.put("tname", add.getText().toString());
                params.put("nos", nos.getText().toString());
                params.put("amt", i.getStringExtra("amount"));
                params.put("pnm", i.getStringExtra("pname"));
                params.put("pd", i.getStringExtra("pd"));
//                params.put("temail", email.getText().toString());
//                params.put("pa", email.getText().toString());
//                            params.put("texperiance", dblog.getText().toString());
// Toast.makeText(MainActivity.this,"submitted",Toast.LENGTH_LONG).show();

//returning parameter
                return params;
            }

        };

// m = Integer.parseInt(ba) - Integer.parseInt(result.getContents());
// balance.setText(m+"");


        //Adding the string request to the queue
        RequestQueue requestQueue1 = Volley.newRequestQueue(Purchaseconfirm.this);
        requestQueue1.add(stringRequest1);*/
    }

    @Override
    public void onPaymentError(int i, String s) {

    }


}
