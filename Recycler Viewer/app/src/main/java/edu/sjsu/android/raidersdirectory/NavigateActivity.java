package edu.sjsu.android.raidersdirectory;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NavigateActivity extends MenuActivity {

    ImageView mainIV;
    TextView title, description;

    String d1, d2;
    int image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        mainIV = findViewById(R.id.imageView);
        title = findViewById(R.id.title_navigation);
        description = findViewById(R.id.description_navigation);

        getData();
        setData();
    }

    private void getData()
    {
        //only if these data values exist we can execute
        if(getIntent().hasExtra("myImage") && getIntent().hasExtra("data1")
                && getIntent().hasExtra("data2"))
        {
            d1 = getIntent().getStringExtra("data1");
            d2 = getIntent().getStringExtra("data3");
            image = getIntent().getIntExtra("myImage", 1);
        }
        else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    private void setData(){
        title.setText(d1);
        description.setText(d2);
        mainIV.setImageResource(image);
    }
}