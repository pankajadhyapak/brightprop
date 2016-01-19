package com.w3effects.brightprop.Fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.w3effects.brightprop.Api.PropertyApi;
import com.w3effects.brightprop.Models.Property;
import com.w3effects.brightprop.PropertyDetails;
import com.w3effects.brightprop.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment{

    Dialog mProgressBar;
    ListView listview;
    List<Property> properties = new ArrayList<>();
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        listview = (ListView)getActivity().findViewById(R.id.listView);
        mProgressBar = ProgressDialog.show(getContext(), "", "Fetching Properties", true);

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
        PropertyApi stackOverflowAPI = retrofit.create(PropertyApi.class);

        Call<List<Property>> call = stackOverflowAPI.loadQuestions(20);
        //asynchronous call
        call.enqueue(new Callback<List<Property>>() {
            @Override
            public void onResponse(Response<List<Property>> response, Retrofit retrofit) {

                Log.d("Home Fragment", ""+response.body().size());
                properties.addAll(response.body());

                String propertyName[] = new String[response.body().size()];

                for (int i=0;i <response.body().size(); i++){
                    propertyName[i] = (response.body().get(i).getTitle().getRendered());
                }
                mProgressBar.dismiss();

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, propertyName);
                listview.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), PropertyDetails.class);
                intent.putExtra("title", properties.get(position).getTitle().getRendered());
                intent.putExtra("content", properties.get(position).getContent().getRendered());
                intent.putExtra("mediaId", properties.get(position).getFeatured_image());
                Log.e("Property Detail", properties.get(position).getTitle().getRendered() + "  " + properties.get(position).getContent().getRendered());

                startActivity(intent);
            }
        });
    }




}
