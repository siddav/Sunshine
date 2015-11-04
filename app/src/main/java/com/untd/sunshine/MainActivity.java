package com.untd.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                Toast toast = Toast.makeText(getApplicationContext(), "clicked on settings", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
            } else if (id == R.id.action_map) {
                openPreferredLocationInMap();
                return true;
            }

        return super.onOptionsItemSelected(item);
    }
    private void openPreferredLocationInMap() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String zipcode = pref.getString(getString(R.string.pref_location_key),getString(R.string.pref_location_default));
        Log.i("ZIPCODE", zipcode + " fetched zip code");
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                                .appendQueryParameter("q", zipcode)
                               .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
        } else {
          Log.d("ZIPCODE", "Couldn't call " + zipcode + ", no receiving apps installed!");
          }
    }
}
