package com.skole.s304114mappe2ny.LeggTilogEndre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.skole.s304114mappe2ny.DBhandler;
import com.skole.s304114mappe2ny.Fragmenter.DatoFragment;
import com.skole.s304114mappe2ny.Fragmenter.TidFragment;
import com.skole.s304114mappe2ny.ListViews.SeBestillinger;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.Fragmenter.SeBestillingsInfoDialog;
import com.skole.s304114mappe2ny.klasser.Bestilling;
import com.skole.s304114mappe2ny.klasser.Deltakelse;
import com.skole.s304114mappe2ny.klasser.Resturant;
import com.skole.s304114mappe2ny.klasser.Venn;
import java.util.ArrayList;



public class RegistrerBestilling extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, SeBestillingsInfoDialog.DialogClickListener {


    //--------DIALOG KNAPPER TIL SLETTRESTURANTDIALOG--------
    @Override
    public void bestillClick() {
        fullforBestilling();
    }

    @Override
    public void avbrytClick() {
        Toast.makeText(getApplicationContext(),"Avbrutt bestilling",Toast.LENGTH_LONG).show();
        return;
    }


    //--------KNAPPER--------
    private Button btnTilbake, btnLeggTilVenn, velgDato, velgTid, btnSeBestillingsinfo;

    //--------TEKST--------
    private TextView visDato, visTid;

    //--------SPINNERE--------
    private Spinner spinnerResturanter, spinnerVenner;

    //--------VERDIER--------
    private String dato, tid;

    //--------OBJEKTER--------
    private Venn valgtVenn, valgtVennSlett;
    private Resturant valgtResturant;

    //--------ARRAYS--------
    private ArrayList<Venn> valgteVenner = new ArrayList<Venn>();

    //--------LISTVIEW--------
    ListView vennerListView;

    //--------DB HANDLER--------
    DBhandler db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrer_bestilling);


        //--------KNAPPER--------
        btnTilbake = (Button) findViewById(R.id.btnTilbake);
        btnLeggTilVenn = (Button) findViewById(R.id.btnLeggTilVenn);
        velgDato = (Button) findViewById(R.id.velgDato);
        velgTid = (Button) findViewById(R.id.velgTid);
        btnSeBestillingsinfo = (Button) findViewById(R.id.btnSeBestillingsinfo);


        //--------INPUTS--------
        visDato = (TextView) findViewById(R.id.visDato);
        visTid = (TextView) findViewById(R.id.visTid);
        vennerListView = (ListView) findViewById(R.id.list);


        //--------SPINNERE--------
        spinnerResturanter = (Spinner) findViewById(R.id.spinResturant);
        spinnerVenner = (Spinner) findViewById(R.id.spinVenn);


        //--------DB HANDLER--------
        db = new DBhandler(this);


        //--------OPPRETTER OG POPULERER SPINNERE--------
        lagResturantSpinner();
        lagVennerSpinner();


        //--------LISTENERS--------
        //KLIKK PÅ LEGG TIL
        btnLeggTilVenn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LEGGER TIL VALGT VENN I valgteVenner ARRAYET
                leggTilValgtVenn();
            }
        });

        //KLIKK PÅ VELG DATO
        velgDato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OPPRETTER DATOFRAGMENTET FOR SETTING AV DATO
                DialogFragment datoValg = new DatoFragment();
                datoValg.show(getSupportFragmentManager(), "dato valg");
            }
        });

        //KLIKK PÅ VELG TID
        velgTid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OPPRETTER TIDFRAGMENTET FOR SETTING AV TID
                DialogFragment tidValg = new TidFragment();
                tidValg.show(getSupportFragmentManager(), "tid valg");
            }
        });

        //KLIKK PÅ UTFØR BESTILLING
        btnSeBestillingsinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //KONTROLLERER AT ALLE FELTER SOM ER OBLIGATORISKE ER BENYTTET
                if (!visDato.getText().toString().equals("") || !visTid.getText().toString().equals("")) {

                    //OPPRETTER SEBESTILLINGSINFODIALOG OG VISER VALGT INFO
                    visBestillingsinfo();
                }
                else{
                    //INFOMELDING UT - FEIL INPUT
                    Toast.makeText(RegistrerBestilling.this, "Tid og dato for bestilling må være fylt ut.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //KLIKK PÅ TILBAKE
        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //--------SLUTT LISTENERS--------

    }//-------CREATE SLUTTER---------




    //--------GENERERER OG POPULERER VENNERLISTVIEWET - MULIGHET FOR LESTTING DIREKTE--------
    private void populateListView() {

        //GENERERER ARRAYADAPTER TIL LISTVIEWET
        final ArrayAdapter<Venn> adapter = new ArrayAdapter<Venn>(this, android.R.layout.simple_list_item_1, valgteVenner);
        vennerListView.setAdapter(adapter);

        //VED KLIKK PÅ VENN FRA LISTVIEWET
        vennerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //GIR VALGTVENNSLETT VERDIEN TIL VALGT OBJEKT FRA LISTVIEWET
                valgtVennSlett = (Venn) vennerListView.getItemAtPosition(i);

                //sletter valgt venn direkte fra listviewet ved trykk
                adapter.remove(valgtVennSlett);

                //OPPDATERER ADAPTERARRAYET FORTLØPENDE
                adapter.notifyDataSetChanged();
            }
        });
    }


    //--------OPPRETTER SEBESTILLINGSINFODIALOG--------
    private void visBestillingsinfo()  {

        //OPPRETTER NYTT DIALOGFRAGMENT
        SeBestillingsInfoDialog bFragment = new SeBestillingsInfoDialog();

        //OVERFØRER BESTILLINGSINFO TIL FRAGMENTET MED METODE FRA FRAGMENTET
        bFragment.hentInfo(dato, tid, valgtResturant, valgteVenner, db);

        //VISER DIALOGVINDUET
        bFragment.show(getFragmentManager(), "Bestillingsinfo");
    }


    //--------INNEBYGD METODE FOR SETTING AV DATO--------
    @Override
    public void onDateSet(DatePicker view, int aar, int mnd, int dag) {

        //MÅ LEGGE INN DENNE ETTERSOM MÅNEDSTALLET VISER 9 FOR OKTOBER OSV.
        mnd++;

        //GENERERER STRING PÅ 22/10/2019 FORMAT
        dato = dag+"/"+mnd+"/"+aar;
        visDato.setText(dato);
    }


    //--------INNEBYGD METODE FOR SETTING AV TIDSPUNKT--------
    @Override
    public void onTimeSet(TimePicker view, int time, int min) {

        tid = "Kl: " + time + ":";
        //SØRGER FOR AT DET STÅR f.eks 10:05 ISTEDEN FOR 10:5
        if(min < 10) {
            tid += "0";
        }

        //GENERERER STRING PÅ KL: 16:35 FORMAT
        tid += min;

        visTid.setText(tid);
    }



    //--------GENERERER SPINNER MED ALLE RESTURATENE SOM ER LAGT TIL I DB--------
    private void lagResturantSpinner() {

        //LEGGER ALLE RESTURANTER I RESTURANT-ARRAY - HENTET FRA DB
        ArrayList<Resturant> resturanter = db.finnAlleResturanter();

        //GENERERER ARRAYADAPTER TIL SPINNER
        final ArrayAdapter<Resturant> adapterRes = new ArrayAdapter<Resturant>(this, android.R.layout.simple_list_item_1, resturanter);
        adapterRes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerResturanter.setAdapter(adapterRes);

        //VED VALG/KLIKK AV RESTURANT I SPINNEREN
        spinnerResturanter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //GIR VALGTRESTURANT VERDIEN TIL VALGT OBJEKT FRA SPINNER
                valgtResturant = (Resturant) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    //--------GENERERER SPINNER MED ALLE VENNER SOM ER LAGT TIL I DB--------
    private void lagVennerSpinner() {

        //LEGGER ALLE VENNER I VENNER-ARRAY - HENTET FRA DB
        ArrayList<Venn> venner = db.finnAlleVenner();

        //GENERERER ARRAYADAPTER TIL SPINNER
        final ArrayAdapter<Venn> adapterVenner = new ArrayAdapter<Venn>(this, android.R.layout.simple_list_item_1, venner);
        adapterVenner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerVenner.setAdapter(adapterVenner);

        //VED VALG/KLIKK AV RESTURANT I SPINNEREN
        spinnerVenner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //GIR VALGTRESTURANT VERDIEN TIL VALGT OBJEKT FRA SPINNER
                valgtVenn = (Venn) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    //-------SJEKK OM INDEKS/VENN ALLEREDE ER LAGT TIL I VALGTEVENNER-ARRAY---------
    private boolean sjekkVenn(Venn venn) {
        boolean sjekk = false;

        //GÅR GJENNOM ARRAY OG KONTROLLERER ETTER VENNER
        for (Venn v : valgteVenner) {
            if (v == venn) {
                //FINNES I ARRAYET
                sjekk = true;
                break;
            }
        }
        return sjekk;
    }


    //-------BENYTTER KONTROLLEN OG LEGGER TIL VALGT VENN I VALGTEVENNER-ARRAY---------
    public void leggTilValgtVenn() {
        //KONTROLL AV VENN
        if(!sjekkVenn(valgtVenn)) {

            //HVIS KONTROLL GODKJENT - LEGGER TIL VENN
            valgteVenner.add(valgtVenn);

            //MELDING UT OM AT VENN ER LAGT TIL
            Toast.makeText(this, valgtVenn.getNavn() + " lagt til i bestilling.", Toast.LENGTH_LONG).show();


            //POPULERER FORTLØPENDE LISTVIEWET SOM VISER VALGTE VENNER SOM ER LAGT TIL
            populateListView();
        }
        else {
            Toast.makeText(this, valgtVenn.getNavn()+" er allerede lagt til i bestilling.", Toast.LENGTH_LONG).show();
        }
    }


    //-------GENERERER NØKLER FOR MELDINGER TIL MINNET OG FULLFØRER BESTILLINGEN---------
    private void fullforBestilling() {
        //GENERERER NØKKEL SOM BRUKES SOM BESTILLINGSID - HENTER FRA MINNET OG PLUSSER FOR HVER GANG
        int indeksen = 1 + getSharedPreferences("APP_INFO", MODE_PRIVATE).getInt("LOPENUMMERBESTILLING", 0);

        //LAGRER NØKKEL MED NØKKEL
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt("LOPENUMMERBESTILLING", indeksen).apply();


        //LEGGER TIL BESTILLING I DB
        leggTilBestilling(indeksen);

        //LEGGER TIL DELTAKELSER FOR HVER VENN SOM ER MED I BESTILLINGEN
        leggTilDeltakelser(indeksen);


        //OPPRETTER NY MELDING TIL PÅMINNELSER
        String meldingUt = "Husk bestilling i dag hos "+valgtResturant.getNavn() + ". Dato: " + dato + ". " + tid;


        //OPPRETTER NY LØPENDE NØKKEL
        String NOKKEL = indeksen + "";
        //LAGRER NØKKELEN I MINNET
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt(NOKKEL, indeksen).apply();


        //OPPRETTER NY LØPENDE NØKKEL TIL MELDING (BENYTTER INDEKSEN)
        String nokkel_MELDING = "melding" + indeksen;
        //LAGRER MELDING MED LØPENDE NØKKEL I MINNET
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putString(nokkel_MELDING, meldingUt).apply();


        //VIEW OPPDATERES FORTLØPENDE - FORHINDRER STACK
        Intent intent_preferanser = new Intent (RegistrerBestilling.this, SeBestillinger.class);
        startActivity(intent_preferanser);
        finish();
    }


    //-------LEGGER TIL DELTAKELSER I DB---------
    private void leggTilDeltakelser(int indeksen) {
        //GENERERER EN DELTAKELSE FOR HVER VENN SOM ER MED I BESTILLINGEN
        for(Venn i : valgteVenner) {
            //OPPRETTER NY DELTAKELSE MED DET GENERERTE LØPENUMMERET FRA MINNET
            Deltakelse deltakelse = new Deltakelse(indeksen, i.getID(), i.getNavn());
            //LEGGER TIL DELTAKELSEN I DB
            db.leggTilDeltakelse(deltakelse);
        }
    }


    //-------LEGGER TIL BESTILLING I DB---------
    private void leggTilBestilling(int indeksen) {
        //OPPRETTER NY BESTILLING
        Bestilling bestilling = new Bestilling(dato, tid, valgtResturant.getNavn(), valgtResturant.get_ID());

        //LEGGER TIL BESTILLINGEN I DB MED GENERERT LØPENUMMER FRA MINNET
        db.leggTilBestilling(bestilling, indeksen);
    }



    //-------TILBAKE KNAPP - FORHINDRER STACK---------
    @Override
    public void onBackPressed() {
        finish();
    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}

