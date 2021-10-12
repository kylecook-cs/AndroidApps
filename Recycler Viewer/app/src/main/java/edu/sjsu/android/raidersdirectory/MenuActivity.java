package edu.sjsu.android.raidersdirectory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    //This function deals with creating action bar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.raiders_info:
                //Pass from main activity to zoo info activity
                Intent intent = new Intent(MenuActivity.this, PlayerInfo.class);
                startActivity(intent);
                Toast.makeText(this, "Raiders Info selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.uninstall:
                //Make intent to uninstall the service here
                Uri packageUri = Uri.parse("package:edu.sjsu.android.raidersdirectory");
                Intent intentDelete = new Intent(Intent.ACTION_DELETE, packageUri);
                startActivity(intentDelete);
                Toast.makeText(this, "Uninstall selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}