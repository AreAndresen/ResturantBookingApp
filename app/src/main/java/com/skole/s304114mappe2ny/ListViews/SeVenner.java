package com.skole.s304114mappe2ny.ListViews;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.skole.s304114mappe2ny.DBhandler;
import com.skole.s304114mappe2ny.LeggTilogEndre.EndreVenn;
import com.skole.s304114mappe2ny.LeggTilogEndre.LeggTilVenn;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.klasser.Venn;
import java.util.ArrayList;


public class SeVenner extends AppCompatActivity {

    //--------KNAPPER--------
    private Button btnTilbake, btnLeggTilVenner;

    //--------LISTVIEW--------
    private ListView vennerListView;

    //--------OBJEKT--------
    private Venn valgtVenn;

    //--------DB HANDLER--------
    DBhandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_venner);

        //--------KNAPPER--------
        btnTilbake = (Button) findViewById(R.id.btnTilbake);
        btnLeggTilVenner = (Button) findViewById(R.id.leggTilVenn);

        //--------LISTVIEW--------
        vennerListView = (ListView) findViewById(R.id.list);

        //--------DB HANDLER--------
        db = new DBhandler(this);

        //--------POPULERER RESTURANTER LISTVIEWET--------
        populerListView();

        //--------LISTENERS--------
        //KLIKK PÅ TILBAKE
        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //KLIKK PÅ REGISTRER BESTILLING NY RESTURANT
        btnLeggTilVenner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (SeVenner.this, LeggTilVenn.class);
                startActivity(intent_startspill);
                finish();
            }
        });
        //--------SLUTT LISTENERS--------

    }//-------CREATE SLUTTER---------


    //--------POPULERER RESTURANTER-LISTVIEWET--------
    private void populerListView() {

        //HENTER ALLE RESTURANTER FRA DB OG LEGGER OVER I ARRAY
        final ArrayList<Venn> venner = db.finnAlleVenner();

        //GENERERER ARRAYADAPTER TIL LISTVIEWET
        ArrayAdapter<Venn> adapter = new ArrayAdapter<Venn>(this, android.R.layout.simple_list_item_1, venner);
        vennerListView.setAdapter(adapter);

        //VED KLIKK PÅ VENN I LISTVIEWET
        vennerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //GIR VALGTVENN VERDIEN TIL VALGT OBJEKT FRA LISTVIEWET
                valgtVenn = (Venn) vennerListView.getItemAtPosition(i);

                //HENTER OG PARSER ID FRA RESTURANTEN
                Integer ID = (int) valgtVenn.getID();

                //INTENT TIL ENDREVENN
                Intent intentet = new Intent(SeVenner.this, EndreVenn.class);
                //TAR MED VALGT ID TIL OVERFØRING
                intentet.putExtra("id",ID);
                startActivity(intentet);
                finish();
            }
        });
    }

    //-------TILBAKE KNAPP - FORHINDRER STACK---------
    @Override
    public void onBackPressed() {
        finish();
    }

}