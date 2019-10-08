package com.skole.s304114mappe2ny.LeggTilogEndre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.skole.s304114mappe2ny.DBhandler;
import com.skole.s304114mappe2ny.Fragmenter.DatoFragment;
import com.skole.s304114mappe2ny.Fragmenter.TidFragment;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.klasser.Resturant;
import com.skole.s304114mappe2ny.klasser.Venn;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class LeggTilBestilling extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Spinner spinnerResturanter, spinnerVenner;
    private Resturant valgtResturant;
    private Venn valgtVenn;
    private ArrayList<Venn> valgteVenner = new ArrayList<Venn>();


    DBhandler db;

    private TextView bestillingTekst, visDato, visTid;
    private Button btnTilbake, btnLeggTilVenn, btnSlettVenn, velgDato, velgTid;
    //private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legg_til_bestilling);

        //Knapper
        btnTilbake = (Button) findViewById(R.id.btnTilbake);
        btnLeggTilVenn = (Button) findViewById(R.id.btnLeggTilVenn);
        btnSlettVenn = (Button) findViewById(R.id.btnSlettVenn);
        velgDato = (Button) findViewById(R.id.velgDato);
        velgTid = (Button) findViewById(R.id.velgTid);

        //Tekst ++
        bestillingTekst = (TextView) findViewById(R.id.bestillingTekst);
        spinnerResturanter = (Spinner) findViewById(R.id.spinResturant);
        spinnerVenner = (Spinner) findViewById(R.id.spinVenn);
        visDato = (TextView) findViewById(R.id.visDato);
        visTid = (TextView) findViewById(R.id.visTid);

        db = new DBhandler(this);

        //Lager spinnere
        lagResturantSpinner();
        lagVennerSpinner();


        btnLeggTilVenn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                leggTilValgtVenn();

                bestillingTekst.setText(visBestillingsData());
            }
        });

        btnSlettVenn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                slettValgtVenn();
                bestillingTekst.setText(visBestillingsData());
            }
        });

        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

    }
    //UTENFOR CREATE


    //DATOFRAGMENT
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        //ny
        String nyFormat = dayOfMonth+"/"+month+"/"+year;
        visDato.setText(nyFormat);
    }

    //TIDFRAGMENT
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String tid = "Kl: " + hourOfDay + ":";
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

                //visResturantData(resturant);

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
                //valgteVenner.add(db.finnVenn(ID));
                //visResturantData(venn);
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
    }

    /*private void visResturantData(Resturant resturant) {

        String name = resturant.getNavn();
        String tlf = resturant.getTelefon();
        String type = resturant.getType();

        String userData = "Name: " + name + "\ntlf: " + tlf + "\ntype: " + type;

        //Toast.makeText(this, userData, Toast.LENGTH_LONG).show();
    }*/

    //meldiung må lagres i sharedpreferance
    private String visBestillingsData() {

        String venner = "Din bestillingsordre\n";
        //valgteVenner;
        venner += "Resturant:"+valgtResturant.getNavn()+"\nTlf: "+valgtResturant.getTelefon()+"\nType: "+valgtResturant.getType()+"\n\n";

        venner += "Venner:\n";
        for(Venn venn : valgteVenner) {
            venner += "Name: " + venn.getNavn() + ". Tlf: " + venn.getTelefon()+"\n";
        }


        //Toast.makeText(this, venner, Toast.LENGTH_LONG).show();
        return venner;
    }




    /*public void leggtil(String navn, String tlf, String type) {
        //Oppretter nytt objekt
        Resturant nyResturant = new Resturant(navn, tlf, type);
        //legger til i database
        db.leggTilResturant(nyResturant);

        //legger til venn
        Venn nyVenn = new Venn("Gunnar", "911");
        db.leggTilVenn(nyVenn);

        //Resetter inputs
        EnavnResturant.setText("");
        EtlfResturant.setText("");
        EtypeResturant.setText("");

        toastMessage("Resturant lagt til!");
        Log.d("Legg inn: ", "Resturant lagt til");


        Intent intent_tilbake = new Intent (LeggTilBestilling.this, SeResturanter.class);
        startActivity(intent_tilbake);
        finish();
    }*/

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

