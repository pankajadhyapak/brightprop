package com.w3effects.brightprop;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.squareup.picasso.Picasso;
import com.w3effects.brightprop.Api.PropertyApi;
import com.w3effects.brightprop.Models.Media;
import com.w3effects.brightprop.Models.Property;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class PropertyDetails extends AppCompatActivity {
    Dialog mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        Integer mediaId = intent.getIntExtra("mediaId", 0);

        TextView contentView = (TextView)findViewById(R.id.content);
        TextView Propertytitle = (TextView)findViewById(R.id.Propertytitle);
        contentView.setText(Html.fromHtml(content).toString());

        Propertytitle.setText(title);

        mProgressBar = ProgressDialog.show(this, "", "Fetching Property Details...", true);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.interceptors().add(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://brightprop.com/wp-json/wp/v2/")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        PropertyApi brightProp = retrofit.create(PropertyApi.class);
        Call<Media> call = brightProp.loadMedia(mediaId);
        call.enqueue(new Callback<Media>() {
            @Override
            public void onResponse(Response<Media> response, Retrofit retrofit) {
                ImageView propertyImg = (ImageView)findViewById(R.id.propertyImg);
                Picasso.with(PropertyDetails.this).load(response.body().getSource_url()).into(propertyImg);
                mProgressBar.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });



        Log.e("Property Detail", title +"  "+content);
        getSupportActionBar().setTitle(title);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This will send Contact to Agent ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
