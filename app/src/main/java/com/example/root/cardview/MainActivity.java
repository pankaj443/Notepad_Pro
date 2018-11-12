package com.example.root.cardview;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    static MyCustomAdapter adapter;
    ArrayList<String> data = new ArrayList<String>() ;
    ArrayList<String> timed = new ArrayList<String>();
    Button button;
    static ArrayAdapter arrayAdapter;
    public void add(View view){

        Intent intent = new Intent(getApplicationContext(),Main2Activity.class);

        startActivity(intent);

    }

   /* public void setbg(int color ){

        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);

    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView = ( android.support.v7.widget.SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<String> newList = new ArrayList<>();
                for (String channel: data){
                    String channelName = channel.toLowerCase();
                    if (channelName.contains(newText)){
                        newList.add(channel);
                    }
                }
                adapter.setFilter(newList);
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete){

           adapter.clearall();
           arrayAdapter.notifyDataSetChanged();

            return true;

        }
        if (item.getItemId() == R.id.rateus){

            Uri uri = Uri.parse("https://www.google.com/");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);


        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setTitle("All Notes");


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.root.cardview", Context.MODE_PRIVATE);


        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        HashSet<String> time = (HashSet<String>) sharedPreferences.getStringSet("time", null);




        if (set == null) {

            adapter.data.add("Example Note");

        } else {

           data = new ArrayList(set);
        }

       if (time == null){
            adapter.timed.add("Ef");
        }else {

            timed = new ArrayList(time);

        }



        button  = (Button) findViewById(R.id.button);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);


        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        adapter = new MyCustomAdapter(this, data,timed);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }
}
