package com.example.triporganizer;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }


    public void onLanguageRadioButtonClick(View view){

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()){
            case R.id.rbEnglish:
                if (checked)

                    break;
            case R.id.rbFrench:
                if (checked)

                    break;
        }
    }
}
