package com.skole.s304114mappe2ny.ListViews;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.skole.s304114mappe2ny.DBhandler;
import com.skole.s304114mappe2ny.LeggTilogEndre.EndreVenn;
import com.skole.s304114mappe2ny.LeggTilogEndre.LeggTilVenn;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.klasser.Venn;

import java.util.ArrayList;


public class SeVenner extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    private Button btnTilbake, btnLeggTilVenner;

    DBhandler db;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_venner);

        btnTilbake = (Button) findViewById(R.id.btnTilbake);
        btnLeggTilVenner = (Button) findViewById(R.id.leggTilVenn);

        mListView = (ListView) findViewById(R.id.list);

        db = new DBhandler(this);

        populateListView();


        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnLeggTilVenner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (SeVenner.this, LeggTilVenn.class);
                startActivity(intent_startspill);
                finish();
            }
        });
    }


    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //leggr alle resturanter i array
        final ArrayList<Venn> venner = db.finnAlleVenner();


        //create the list adapter and set the adapter
        ArrayAdapter<Venn> adapter = new ArrayAdapter<Venn>(this, android.R.layout.simple_list_item_1, venner);
        mListView.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String name = adapterView.getItemAtPosition(i).toString();
                toastMessage(name);

                Venn venn = (Venn) mListView.getItemAtPosition(i);
                toastMessage(""+venn.getID());
                //ny lagring til disk
                Integer ID = (int) venn.getID();

                Intent editScreenIntent = new Intent(SeVenner.this, EndreVenn.class);
                editScreenIntent.putExtra("id",ID);
                editScreenIntent.putExtra("name",venn.getNavn());
                editScreenIntent.putExtra("tlf",venn.getTelefon());

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

