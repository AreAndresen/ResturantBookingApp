package com.skole.s304114mappe2ny;



import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.skole.s304114mappe2ny.klasser.Resturant;
import com.skole.s304114mappe2ny.klasser.Venn;

import java.util.ArrayList;

/**
 * Created by User on 2/28/2017.
 */

public class SeVenner extends AppCompatActivity {

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
                Intent intent_tilbake = new Intent (SeVenner.this, MainActivity.class);
                startActivity(intent_tilbake);
                finish();
            }
        });
    }


    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //leggr alle resturanter i array
        final ArrayList<Venn> venner = db.finnAlleVenner();


        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, venner);
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


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}

