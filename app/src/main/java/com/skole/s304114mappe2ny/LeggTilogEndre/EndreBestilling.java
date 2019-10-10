package com.skole.s304114mappe2ny.LeggTilogEndre;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import android.content.Intent;

import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.skole.s304114mappe2ny.DBhandler;
import com.skole.s304114mappe2ny.Fragmenter.DatoFragment;
import com.skole.s304114mappe2ny.Fragmenter.TidFragment;
import com.skole.s304114mappe2ny.ListViews.SeBestillinger;
import com.skole.s304114mappe2ny.ListViews.SeVenner;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.ListViews.SeResturanter;
import com.skole.s304114mappe2ny.klasser.Bestilling;
import com.skole.s304114mappe2ny.klasser.Resturant;
import com.skole.s304114mappe2ny.klasser.Venn;

import java.util.ArrayList;

/**
 * Created by User on 2/28/2017.
 */

/*public class EndreBestilling extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    /*private static final String TAG = "EditDataActivity";


    private int valgtID;
    private Bestilling valgtBestilling;

    private Spinner spinnerResturanter, spinnerVenner;
    private Resturant valgtResturant;
    private Venn valgtVenn;
    private Venn valgtVennSlett;
    private ArrayList<Venn> valgteVenner = new ArrayList<Venn>();

    private ListView mListView;


    private String dato, tid;

    DBhandler db;

    private TextView visDato, visTid;
    private Button btnSlett, btnLeggTilVenn, btnSlettVenn, velgDato, velgTid, btnEndreBestilling;
    //private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endre_bestilling);

        mListView = (ListView) findViewById(R.id.list);

        //Knapper
        btnSlett = (Button) findViewById(R.id.btnSlett);
        btnLeggTilVenn = (Button) findViewById(R.id.btnLeggTilVenn);
        btnSlettVenn = (Button) findViewById(R.id.btnSlettVenn);
        velgDato = (Button) findViewById(R.id.velgDato);
        velgTid = (Button) findViewById(R.id.velgTid);
        btnEndreBestilling = (Button) findViewById(R.id.btnEndreBestilling);

        //Tekst ++
        //bestillingTekst = (TextView) findViewById(R.id.bestillingTekst);
        spinnerResturanter = (Spinner) findViewById(R.id.spinResturant);
        spinnerVenner = (Spinner) findViewById(R.id.spinVenn);
        visDato = (TextView) findViewById(R.id.visDato);
        visTid = (TextView) findViewById(R.id.visTid);

        db = new DBhandler(this);

        //Lager spinnere
        lagResturantSpinner();
        lagVennerSpinner();

        //get the intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        valgtID = receivedIntent.getIntExtra("id",0); //NOTE: -1 is just the default value

        valgtBestilling = db.finnBestilling(valgtID);





        btnEndreBestilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String item = editable_item.getText().toString();

                String navn = EnavnResturant.getText().toString();
                String tlf = EtlfResturant.getText().toString();
                String type = EtypeResturant.getText().toString();


                //Bestilling bestilling = db.finnBestilling(valgtID); //manuell her
                valgtBestilling.setResturantNavn();
                resturant.setNavn(navn);
                resturant.setTelefon(tlf);
                resturant.setType(type);
                //Resturant oppdatertResturant = new Resturant(navn,tlf, type);;


                if(!navn.equals("") && !tlf.equals("") && !type.equals("")){
                    //mDatabaseHelper.updateName(item,selectedID,selectedName);

                    db.oppdaterBestilling(bestilling);

                    //gjørs så viewet oppdaterer fortløpende
                    Intent intent_tilbake = new Intent (EndreBestilling.this, SeBestillinger.class);
                    startActivity(intent_tilbake);
                    finish();

                }else{
                    toastMessage("You must enter a name");
                }
            }
        });

        btnSlett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Resturant slettResturant = db.finnResturant(valgtID);

                db.slettResturant(slettResturant.get_ID());


                //gjørs så viewet oppdaterer fortløpende
                Intent intent_tilbake = new Intent (EndreBestilling.this, SeBestillinger.class);
                startActivity(intent_tilbake);
                finish();

                toastMessage("removed from database");
            }
        });

        btnLeggTilVenn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                leggTilValgtVenn();
                //bestillingTekst.setText(visBestillingsData());
            }
        });

        btnSlettVenn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                slettValgtVenn();
                //bestillingTekst.setText(visBestillingsData());
            }
        });

        /*btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
/*
        velgDato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datoValg = new DatoFragment();
                datoValg.show(getSupportFragmentManager(), "dato valg");
            }
        });

        velgTid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TidFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });



        populateListView();

    }

    //DATOFRAGMENT
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        //ny
        dato = dayOfMonth+"/"+month+"/"+year;
        visDato.setText(dato);
    }

    //TIDFRAGMENT
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        tid = "Kl: " + hourOfDay + ":";
        //sørger for at det står f.eks 10:05 isteden for 10:5
        if(minute < 10) {
            tid += "0";
        }
        tid += minute;

        visTid.setText(tid);
    }




    private void lagResturantSpinner() {
        //leggr alle resturanter i array
        ArrayList<Resturant> resturanter = db.finnAlleResturanter();

        //create the list adapter and set the adapter
        ArrayAdapter<Resturant> adapter = new ArrayAdapter<Resturant>(this, android.R.layout.simple_list_item_1, resturanter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerResturanter.setAdapter(adapter);

        spinnerResturanter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Resturant resturant = (Resturant) parent.getSelectedItem();

                Integer ID = (int) resturant.get_ID();
                valgtResturant = db.finnResturant(ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void lagVennerSpinner() {
        //leggr alle resturanter i array
        ArrayList<Venn> venner = db.finnAlleVenner();

        //create the list adapter and set the adapter
        ArrayAdapter<Venn> adapter2 = new ArrayAdapter<Venn>(this, android.R.layout.simple_list_item_1, venner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerVenner.setAdapter(adapter2);

        spinnerVenner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Venn venn = (Venn) parent.getSelectedItem();


                Integer ID = (int) venn.getID();
                valgtVenn = db.finnVenn(ID);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    //MÅ GJØRES SÅ DETTE GJØRES MOT DATABASEN OG TABLE BESTILLINGER
    public void leggTilValgtVenn() {
        //Venn venn = (Venn)  spinnerVenner.getSelectedItem();
        //visVennData(venn);
        valgteVenner.add(valgtVenn);
        Toast.makeText(this, valgtVenn.getNavn()+" lagt til i bestilling.", Toast.LENGTH_LONG).show();

        populateListView();

    }


    //må gjøre så siste venn også kan bli slettet
    public void slettValgtVenn() {
        //Venn venn = (Venn)  spinnerVenner.getSelectedItem();
        //visVennData(venn);

        if(valgteVenner.size() == 1) {
            valgteVenner.clear();
        }
        else {
            valgteVenner.remove(valgtVenn);
        }
        valgteVenner.remove(valgtVenn);
        Toast.makeText(this, valgtVenn.getNavn()+" fjernet fra bestilling.", Toast.LENGTH_LONG).show();

        populateListView();
    }



    private void populateListView() {
        //final ArrayList<Venn> opps = valgteVenner;

        //create the list adapter and set the adapter
        final ArrayAdapter<Venn> adapter = new ArrayAdapter<Venn>(this, android.R.layout.simple_list_item_1, valgteVenner);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String name = adapterView.getItemAtPosition(i).toString();
                toastMessage(name);

                valgtVennSlett = (Venn) mListView.getItemAtPosition(i);

                valgteVenner.remove(valgtVennSlett);
                adapter.notifyDataSetChanged();

            }
        });
    }


/*
    @Override
    public void onBackPressed() {
        finish();
    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }*/


//}

