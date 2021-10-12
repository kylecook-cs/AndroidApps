package edu.sjsu.android.raidersdirectory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PlayerInfo extends MenuActivity
{
    Button callBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

      callBtn = findViewById(R.id.phone_button);
      callBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    Intent intentCall = new Intent(Intent.ACTION_DIAL);
                    intentCall.setData(Uri.parse("tel:8888888"));
                    startActivity(intentCall);
            }
        });
    }
}