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
import com.skole.s304114mappe2ny.LeggTilogEndre.EndreResturant;
import com.skole.s304114mappe2ny.LeggTilogEndre.LeggTilResturant;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.klasser.Resturant;
import java.util.ArrayList;


public class SeResturanter extends AppCompatActivity {

    //--------KNAPPER--------
    private Button btnTilbake, btnLeggTilResturant;

    //--------LISTVIEW--------
    private ListView resturanterListView;

    //--------OBJEKT--------
    private Resturant valgtResturant;

    //--------DB HANDLER--------
    DBhandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_resturanter);

        //--------KNAPPER--------
        btnTilbake = (Button) findViewById(R.id.btnTilbake);
        btnLeggTilResturant = (Button) findViewById(R.id.leggTilResturant);

        //--------LISTVIEW--------
        resturanterListView = (ListView) findViewById(R.id.list);

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
        btnLeggTilResturant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (SeResturanter.this, LeggTilResturant.class);
                startActivity(intent_startspill);
                finish();
            }
        });
        //--------SLUTT LISTENERS--------

    }//-------CREATE SLUTTER---------


    //--------POPULERER RESTURANTER-LISTVIEWET--------
    private void populerListView() {

        //HENTER ALLE RESTURANTER FRA DB OG LEGGER OVER I ARRAY
        final ArrayList<Resturant> resturanter = db.finnAlleResturanter();

        //GENERERER ARRAYADAPTER TIL LISTVIEWET
        ArrayAdapter<Resturant> adapter = new ArrayAdapter<Resturant>(this, android.R.layout.simple_list_item_1, resturanter);
        resturanterListView.setAdapter(adapter);

        //VED KLIKK PÅ RESTURANT I LISTVIEWET
        resturanterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //GIR VALGTRESTURANT VERDIEN TIL VALGT OBJEKT FRA LISTVIEWET
                valgtResturant = (Resturant) resturanterListView.getItemAtPosition(i);

                //HENTER OG PARSER ID FRA RESTURANTEN
                Integer ID = (int) valgtResturant.get_ID();

                //INTENT TIL ENDRERESTURANT
                Intent intentet = new Intent(SeResturanter.this, EndreResturant.class);
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