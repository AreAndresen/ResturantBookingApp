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
import com.skole.s304114mappe2ny.Hovedmenyer.Bestillinger;
import com.skole.s304114mappe2ny.LeggTilogEndre.RegistrerBestilling;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.Fragmenter.SeBestillingsInfoFragment;
import com.skole.s304114mappe2ny.klasser.Bestilling;
import java.util.ArrayList;


public class SeBestillinger extends AppCompatActivity{

    //--------KNAPPER--------
    private Button btnTilbake, btnRegistrerBestilling;

    //--------LISTVIEW--------
    private ListView bestillingerListView;

    //--------OBJEKT--------
    private Bestilling valgtBestilling;

    //--------DB HANDLER--------
    DBhandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_bestillinger);

        //--------KNAPPER--------
        btnTilbake = (Button) findViewById(R.id.btnTilbake);
        btnRegistrerBestilling = (Button) findViewById(R.id.registrerNyBestilling);

        //--------LISTVIEW--------
        bestillingerListView = (ListView) findViewById(R.id.list);

        //--------DB HANDLER--------
        db = new DBhandler(this);

        //--------POPULERER BESTILLINGER LISTVIEWET--------
        populerListView();


        //--------LISTENERS--------
        //KLIKK PÅ TILBAKE
        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (SeBestillinger.this, Bestillinger.class);
                startActivity(intent);
                finish();
            }
        });

        //KLIKK PÅ REGISTRER BESTILLING
        btnRegistrerBestilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (SeBestillinger.this, RegistrerBestilling.class);
                startActivity(intent);
                finish();
            }
        });
        //--------SLUTT LISTENERS--------

    }//-------CREATE SLUTTER---------


    //--------POPULERER BESTILLINGER-LISTVIEWET--------
    private void populerListView() {

        //HENTER ALLE BESTILLINGER FRA DB OG LEGGER OVER I ARRAY
        final ArrayList<Bestilling> bestillinger = db.finnAlleBestillinger();

        //GENERERER ARRAYADAPTER TIL LISTVIEWET
        ArrayAdapter<Bestilling> adapter = new ArrayAdapter<Bestilling>(this, android.R.layout.simple_list_item_1, bestillinger);
        bestillingerListView.setAdapter(adapter);


        //VED KLIKK PÅ BESTILLING I LISTVIEWET
        bestillingerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //GIR VALGTBESTILLING VERDIEN TIL VALGT OBJEKT FRA LISTVIEWET
                valgtBestilling = (Bestilling) bestillingerListView.getItemAtPosition(i);

                //HENTER OG PARSER ID FRA BESTILLINGEN
                Integer ID = (int) valgtBestilling.get_ID();

                //LAGRER ID I MINNET - BENYTTES TIL I SEBESTILLINGSINFODIALOGFRAGMENT OG I MINSERVICE/NOTIFIKASJON FOR VISNING
                getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt("VISNINGSID", ID).apply();

                //INTENT TIL SEBESTILLINGSINFOFRAGMENT
                Intent intentet = new Intent(SeBestillinger.this, SeBestillingsInfoFragment.class);
                startActivity(intentet);
                finish();
            }
        });
    }


    //-------TILBAKE KNAPP - FORHINDRER STACK---------
    @Override
    public void onBackPressed() {
        Intent intent = new Intent (SeBestillinger.this, Bestillinger.class);
        startActivity(intent);
        finish();
    }
}