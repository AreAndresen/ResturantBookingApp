package com.skole.s304114mappe2ny.ListViews;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.skole.s304114mappe2ny.DBhandler;
import com.skole.s304114mappe2ny.LeggTilogEndre.EndreResturant;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.klasser.Resturant;

import java.util.ArrayList;

/**
 * Created by User on 2/28/2017.
 */

public class SeResturanter extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    private Button btnTilbake;

    DBhandler db;

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_resturanter);

        btnTilbake = (Button) findViewById(R.id.btnTilbake);

        mListView = (ListView) findViewById(R.id.list);

        db = new DBhandler(this);

        populateListView();


        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //leggr alle resturanter i array
        final ArrayList<Resturant> resturanter = db.finnAlleResturanter();


        //create the list adapter and set the adapter
        ArrayAdapter<Resturant> adapter = new ArrayAdapter<Resturant>(this, android.R.layout.simple_list_item_1, resturanter);
        mListView.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String name = adapterView.getItemAtPosition(i).toString();
                toastMessage(name);


                Resturant resturant = (Resturant) mListView.getItemAtPosition(i);
                toastMessage(""+resturant.get_ID());
                //ny lagring til disk
                Integer ID = (int) resturant.get_ID();

                //Resturant resturant2 = (Resturant) mListView.getItem(i);
                //String value= selItem.getTheValue(); //getter method

                //Resturant resturant = db.finnResturant(1); //manuell her

                Intent editScreenIntent = new Intent(SeResturanter.this, EndreResturant.class);
                editScreenIntent.putExtra("id",ID);
                editScreenIntent.putExtra("name",resturant.getNavn());
                editScreenIntent.putExtra("tlf",resturant.getTelefon());
                editScreenIntent.putExtra("type",resturant.getType());
                //editScreenIntent.putParcelableArrayListExtra("Resturant",resturant);

                //legger denne her i tilfelle jeg får bruk for den senere
                //mListView.notifyDataSetChanged();

                startActivity(editScreenIntent);
                finish(); //unngår å legge på stack
            }
        });
    }


    //-------VISER DIALOG VED TILBAKEKNAPP---------
    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}

