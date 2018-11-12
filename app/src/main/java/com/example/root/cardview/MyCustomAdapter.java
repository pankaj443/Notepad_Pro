package com.example.root.cardview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import static com.example.root.cardview.MainActivity.arrayAdapter;


public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyViewHolder> {



    Context context;

    static ArrayList<String> data = new ArrayList<String>();
    static ArrayList<String> timed = new ArrayList<String>();
   Date currentTime = Calendar.getInstance().getTime();


    LayoutInflater inflater;

    int previousPosition = 0;

    public MyCustomAdapter(Context context, ArrayList<String> data,ArrayList<String> timed){

        this.context = context;

        this.data = data;
        this.timed = timed;
        inflater = LayoutInflater.from(context);

    }


    public void setFilter(ArrayList<String> newList){
        data = new ArrayList<>();
        data.addAll(newList);
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        View view = inflater.inflate(R.layout.list_item_row, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {

        myViewHolder.textview.setText(data.get(position));

         myViewHolder.lastupdated.setText("Last Updated\n"+ timed.get(position).substring(0, 10));



        previousPosition = position;

        int currentPosition = position;
        final String infoData = data.get(position);


        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                Log.i("position", String.valueOf(position));
                Intent intent = new Intent(context.getApplicationContext(), Main2Activity.class);
                intent.putExtra("noteID", position );
                context.startActivity(intent);
            }

        });


        myViewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {



                new AlertDialog.Builder(context)
                        .setIcon(R.drawable.delete)
                        .setTitle("Are You Sure?")
                        .setMessage("Do you want to delete this Note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {

                                        data.remove(position);
                                        timed.remove(position);
                                       notifyItemRemoved(position);
                                       MainActivity.arrayAdapter.notifyDataSetChanged();

                                        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("com.example.root.cardview", Context.MODE_PRIVATE);

                                        HashSet<String> set = new HashSet(MainActivity.adapter.data);

                                        sharedPreferences.edit().putStringSet("notes", set).apply();

                                        HashSet<String> time = new HashSet(MainActivity.adapter.timed);

                                       sharedPreferences.edit().putStringSet("time", time).apply();





                                    }
                                }
                        )
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });


    }

    public void clearall(){


        new AlertDialog.Builder(context)
                .setIcon(R.drawable.delete)
                .setTitle("Are You Sure?")
                .setMessage("Do you want to Clear all your Notes?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {

                                data.clear();
                               // timed.clear();
                                notifyDataSetChanged();
                                MainActivity.arrayAdapter.notifyDataSetChanged();

                                SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("com.example.root.cardview", Context.MODE_PRIVATE);

                                HashSet<String> set = new HashSet(MainActivity.adapter.data);
                                sharedPreferences.edit().putStringSet("notes", set).apply();

                               HashSet<String> time = new HashSet(MainActivity.adapter.timed);
                               sharedPreferences.edit().putStringSet("notes", time).apply();


                            }
                        }
                )
                .setNegativeButton("No", null)
                .show();


    }



    @Override
    public int getItemCount() {
        return data.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{

       protected View cardView;
        TextView textview;
        TextView lastupdated;

        public MyViewHolder(View itemView) {
            super(itemView);

            textview = (TextView) itemView.findViewById(R.id.txv_row);
            cardView = itemView.findViewById(R.id.cardView);
            lastupdated = (TextView) itemView.findViewById(R.id.lastupdated);

        }

    }




    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(String infoData) {

        int currPosition = data.indexOf(infoData);
        data.remove(currPosition);
        notifyItemRemoved(currPosition);

    }


    // This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    public void addItem(int position, String infoData) {

        timed.add(position,currentTime.toString());

        data.add(position, String.valueOf(infoData));
        notifyItemInserted(position);

    }
    public void setx(int position, String infoData) {


        data.remove( position);
        notifyItemRemoved( position);

        data.add(position, String.valueOf(infoData));
        notifyItemInserted(position);

    }
    public void sety(int position, String infoData) {

        timed.remove( position);
        notifyItemRemoved( position);

        timed.add(position, String.valueOf(infoData));
        notifyItemInserted(position);

    }


}
