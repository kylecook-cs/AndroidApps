package edu.sjsu.android.raidersdirectory;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends MenuActivity {

    //Add recyclerview object
    RecyclerView recyclerView;
    Button  buttonCall;
    //string1 is title string2 is descriptions
    String s1[], s2[], s3[];
    int images[ ] = {R.drawable.dc, R.drawable.jj, R.drawable.dw, R.drawable.ja,R.drawable.mc};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get ID for recyclerview
        recyclerView = findViewById(R.id.recyclerView);
        s1 = getResources().getStringArray(R.array.players);
        s2 = getResources().getStringArray(R.array.positions);
        s3 = getResources().getStringArray(R.array.details);
        //create adapter
        AdapterActivity myAdapter = new AdapterActivity(this, s1, s2, s3, images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}