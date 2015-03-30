package com.kevincherian.cz2006;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by hp on 3/22/2015.
 */
public class MedicalRecordEditActivity extends Activity{

    public MedicalRecordEditActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record_edit);

        Spinner dropdown = (Spinner)findViewById(R.id.MedrecSpinner);
        String[]items = new String[]{"Marital Status","Single","Married","Divorced","Widowed"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items);
        dropdown.setAdapter(adapter);
    }

}
