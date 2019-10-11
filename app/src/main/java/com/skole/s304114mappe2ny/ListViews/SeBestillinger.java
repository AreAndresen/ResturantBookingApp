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
import com.skole.s304114mappe2ny.LeggTilogEndre.RegistrerBestilling;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.Fragmenter.SeBestillingsInfoFragment;
import com.skole.s304114mappe2ny.klasser.Bestilling;

import java.util.ArrayList;


public class SeBestillinger extends AppCompatActivity{





    private static final String TAG = "ListDataActivity";

    private Button btnTilbake, btnRegistrerBestilling;

    DBhandler db;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_bestillinger);

        btnTilbake = (Button) findViewById(R.id.btnTilbake);
        btnRegistrerBestilling = (Button) findViewById(R.id.registrerNyBestilling);

        mListView = (ListView) findViewById(R.id.list);

        db = new DBhandler(this);

        populateListView();


        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnRegistrerBestilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (SeBestillinger.this, RegistrerBestilling.class);
                startActivity(intent_startspill);
                finish();
            }
        });
    }


    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //leggr alle resturanter i array
        final ArrayList<Bestilling> bestillinger = db.finnAlleBestillinger();

        //create the list adapter and set the adapter
        ArrayAdapter<Bestilling> adapter = new ArrayAdapter<Bestilling>(this, android.R.layout.simple_list_item_1, bestillinger);
        mListView.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String name = adapterView.getItemAtPosition(i).toString();
                toastMessage(name);

                Bestilling bestilling = (Bestilling) mListView.getItemAtPosition(i);
                toastMessage(""+bestilling.get_ID());
                //ny lagring til disk
                Integer ID = (int) bestilling.get_ID();

                //Intent editScreenIntent = new Intent(SeBestillinger.this, SeBestillingsInfo.class);
                //editScreenIntent.putExtra("id",ID);


                //--------LOADER FRAGMENTT--------
                //SeBestillingsInfo besFragment = new SeBestillingsInfo();
                //besFragment.hentBestilling(db.finnBestilling(ID));

                 //DENNE
                 //SeBestillingsInfoDialog besDialog = new SeBestillingsInfoDialog();
                 //besDialog.hentBestilling(db.finnBestilling(ID));
                 //besDialog.show(getSupportFragmentManager(), "Bestillingsinfo");


                 //IKKE DENNE
                //getFragmentManager().beginTransaction().replace(android.R.id.content, new SeBestillingsInfo()).commit();
                //getSupportFragmentManager().beginTransaction().add(android.R.id.content,besDialog).commit();


                Intent editScreenIntent = new Intent(SeBestillinger.this, SeBestillingsInfoFragment.class);
                editScreenIntent.putExtra("id",ID);
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



    //-------LAGRING AV DATA VED ROTASJON---------
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //strenger
        //outState.putListView("listview", mListView);
        //mListView
        populateListView();

        super.onSaveInstanceState(outState);
    }


    //-------HENTING AV LAGRET DATA---------
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        //strenger
        //tellerSpr.setText(savedInstanceState.getString(NOKKEL_ANTTELLER));


        super.onRestoreInstanceState(savedInstanceState);
    }

}


