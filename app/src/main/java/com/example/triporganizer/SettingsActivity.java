package com.example.triporganizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SettingsActivity  extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);

        String language = sharedPreferences.getString("lang","en");
        String reminder = sharedPreferences.getString("reminder","1w");
        setContentView(R.layout.activity_settings);

        switch(language){

            case "en":
                RadioButton rbEnglish = findViewById(R.id.rbEnglish);
                rbEnglish.setChecked(true);

                break;

            case "fr":
                RadioButton rbFrench = findViewById(R.id.rbFrench);
                rbFrench.setChecked(true);
                break;
        }

        switch (reminder){

            case "1w":
                RadioButton rbOneWeek = findViewById(R.id.rbOneWeek);
                rbOneWeek.setChecked(true);
                break;

            case "2w":

                RadioButton rbTwoWeek = findViewById(R.id.rbTwoWeek);
                rbTwoWeek.setChecked(true);
                break;

            case "3w":

                RadioButton rbThreeWeek = findViewById(R.id.rbThreeWeek);
                rbThreeWeek.setChecked(true);
                break;

        }




    }


    public void onLanguageRadioButtonClick(View view){

        boolean checked = ((RadioButton) view).isChecked();
        editor = sharedPreferences.edit();
        String language = "en";
        switch(view.getId()){
            case R.id.rbEnglish:
                if (checked) {
                    language = "en";
                    editor.putString("lang", "en");
                    editor.apply();
                }
                    break;
            case R.id.rbFrench:
                language = "fr";
                if (checked){
                    editor.putString("lang", "fr");
                    editor.apply();
                }

                    break;

        }

        Locale locale = new Locale(language);
        Configuration conf = getResources().getConfiguration();

        conf.locale = locale;
        getBaseContext().getResources().updateConfiguration(conf,
                getBaseContext().getResources().getDisplayMetrics());

        //finish();
        //startActivity(getIntent());
    }



    public void onReminderRadioButtonClick(View view){
        boolean checked = ((RadioButton) view).isChecked();
        editor = sharedPreferences.edit();


        switch (view.getId()){

            case R.id.rbOneWeek:
                if (checked){
                    editor.putString("reminder","1w");
                    editor.apply();
                }

            case R.id.rbTwoWeek:
                if (checked){
                    editor.putString("reminder","2w");
                    editor.apply();
                }

            case R.id.rbThreeWeek:
                if (checked){
                    editor.putString("reminder","3w");
                    editor.apply();
                }
        }
    }
}
