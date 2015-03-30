package com.kevincherian.cz2006;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by use on 3/12/2015.
 */
public class MedicalRecordFragment extends Fragment {


    //parse declaration
    ParseUser UserInfo = new ParseUser();
    String temp;
    TextView name;

    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //button to go to edit screen
        View root =inflater.inflate(R.layout.fragment_medical_record, container, false);

        return root;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {

        super.onActivityCreated(savedInstanceState);
        ListView record = (ListView)getView().findViewById(R.id.recordlist);
        ArrayList<String> item = new ArrayList<String>();
        addItem(item);
        Button editButton = new Button(getActivity());
        //set button
        editButton.setText("Edit Medical Record");
        editButton.setBackgroundColor(0xFF329593);
        record.scrollTo(0,record.getHeight());
        record.addFooterView(editButton);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,item);
        record.setAdapter(adapter);
        intent = new Intent(getActivity(), MedicalRecordEditActivity.class);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }

    public void addItem (ArrayList item)
    {
        String MaritalStatus="NULL", DOB="NULL",BloodType="NULL",Allergy="NULL",MedHistory="NULL",FamHistory="NULL",SocialHistory="NULL",Height="NULL",Weight="NULL",Others="NULL";


        item.add("         Marital Status :"+MaritalStatus);
        item.add("           Date of Birth : "+DOB);
        item.add("                Blood Type :"+BloodType);
        item.add("                      Allergy : "+Allergy);
        item.add("Past Medical History :"+MedHistory);
        item.add("          Family History : "+FamHistory);
        item.add("            Social History : "+SocialHistory);
        item.add("");
        item.add("                Height(cm) :"+Height);
        item.add("                Weight(kg) :"+Weight);
        item.add("         Other Remarks :"+Others);

    }

}