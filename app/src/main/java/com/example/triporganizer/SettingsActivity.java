package com.example.triporganizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity  extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String language = sharedPreferences.getString("lang","en");
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
    }


    public void onLanguageRadioButtonClick(View view){

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()){
            case R.id.rbEnglish:
                if (checked) {
                    editor.putString("lang", "en");
                    editor.commit();
                }
                    break;
            case R.id.rbFrench:
                if (checked){
                    editor.putString("lang", "fr");
                    editor.commit();
                }

                    break;
        }
    }
}
