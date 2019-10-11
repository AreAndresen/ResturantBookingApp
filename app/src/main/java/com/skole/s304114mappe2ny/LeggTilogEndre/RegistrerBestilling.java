package com.skole.s304114mappe2ny.LeggTilogEndre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
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
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.Fragmenter.SeBestillingsInfoDialog;
import com.skole.s304114mappe2ny.klasser.Bestilling;
import com.skole.s304114mappe2ny.klasser.Resturant;
import com.skole.s304114mappe2ny.klasser.Venn;
import java.util.ArrayList;


public class RegistrerBestilling extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, SeBestillingsInfoDialog.DialogClickListener { //, SeBestillingsInfoDialog.DialogClickListener

    @Override
    public void bestillClick() {

        //lagreBestilling(); //lagrer ved klikk fullført
        //registrerBestilling();

        Toast.makeText(getApplicationContext(),"Bestilling utført",Toast.LENGTH_LONG).show();
        return;
    }

    @Override
    public void avbrytClick() {
        Toast.makeText(getApplicationContext(),"Avbrutt bestilling",Toast.LENGTH_LONG).show();
        return;
    }


    private Spinner spinnerResturanter, spinnerVenner;
    private Resturant valgtResturant;
    private Venn valgtVenn;
    private Venn valgtVennSlett;
    private ArrayList<Venn> valgteVenner = new ArrayList<Venn>();

    //til lagring av IDer
    ArrayList<Integer> IDer = new ArrayList<Integer>();

    ListView mListView;

    private String dato, tid;

    DBhandler db;

    private TextView visDato, visTid;
    private Button btnTilbake, btnLeggTilVenn, btnSlettVenn, velgDato, velgTid, btnSeBestillingsinfo;
    //private EditText editText;


    //--------LAGRINGSKODER--------
    private static final String NOKKEL_VENNERARRAY = "vennerArray_nokkel";
    private static final String NOKKEL_DATO = "dato_nokkel";
    private static final String NOKKEL_TID = "tid_nokkel";
    private static final String NOKKEL_RESTURANT = "resturant_nokkel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrer_bestilling);

        populerValgteVenner();


        //Knapper
        btnTilbake = (Button) findViewById(R.id.btnTilbake);
        btnLeggTilVenn = (Button) findViewById(R.id.btnLeggTilVenn);
        //btnSlettVenn = (Button) findViewById(R.id.btnSlettVenn);
        velgDato = (Button) findViewById(R.id.velgDato);
        velgTid = (Button) findViewById(R.id.velgTid);
        btnSeBestillingsinfo = (Button) findViewById(R.id.btnSeBestillingsinfo);

        //Tekst ++
        //bestillingTekst = (TextView) findViewById(R.id.bestillingTekst);
        spinnerResturanter = (Spinner) findViewById(R.id.spinResturant);
        spinnerVenner = (Spinner) findViewById(R.id.spinVenn);
        visDato = (TextView) findViewById(R.id.visDato);
        visTid = (TextView) findViewById(R.id.visTid);
        mListView = (ListView) findViewById(R.id.list);


        db = new DBhandler(this);

        //Lager spinnere
        lagResturantSpinner();
        lagVennerSpinner();



        String s = "";
        for(int i : IDer) {
            s += i+" ";
        }
        toastMessage(s);


        btnLeggTilVenn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                leggTilValgtVenn();
                //bestillingTekst.setText(visBestillingsData());
            }
        });

        /*btnSlettVenn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                slettValgtVenn();
                //bestillingTekst.setText(visBestillingsData());
            }
        });*/

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


        btnSeBestillingsinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (visDato.getText().toString().equals("") || visTid.getText().toString().equals("")) { //kontrollerer at svar ikke er tom
                    Toast.makeText(RegistrerBestilling.this, "Tid og dato for bestilling må være fylt ut.", Toast.LENGTH_SHORT).show();
                }
                else{
                    visBestillingsinfo();
                }
            }
        });


        for(int i : IDer) {
            valgteVenner.add(db.finnVenn(i));
        }

        populateListView();

    }
    //UTENFOR CREATE


    //OVERFØRER BESTILLINGSINFO TIL SEBESTILLINGSFRAGMENT
    private void visBestillingsinfo()  {

        //Spanned info = visBestillingsData();
        SeBestillingsInfoDialog bFragment = new SeBestillingsInfoDialog();
        //bFragment.init(info);

        bFragment.hentInfo(dato, tid, valgtResturant, valgteVenner, db);

        bFragment.show(getFragmentManager(), "Bestillingsinfo");
    }


    //DATOFRAGMENT
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        /*Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());*/

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
                //Venn venn = (Venn) parent.getSelectedItem();
                valgtVenn = (Venn) parent.getSelectedItem();

                //Integer ID = (int) venn.getID();
                //valgtVenn = db.finnVenn(ID);
                //visResturantData(venn);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    //-------SJEKK OM TALL/INDEKS ER BRUKT TIDLIGERE (SPØRSMÅL)---------
    private boolean sjekkVenn(Venn venn) {
        boolean sjekk = false;
        //går gjennom arrayet som er fylt med brukte indekser

        for (Venn v : valgteVenner) {
            if (v == venn) {
                sjekk = true; //finnes i arrayet
                break;
            }
        }
        return sjekk;
    }


    //MÅ GJØRES SÅ DETTE GJØRES MOT DATABASEN OG TABLE BESTILLINGER
    public void leggTilValgtVenn() {
        if(!sjekkVenn(valgtVenn)) {
            valgteVenner.add(valgtVenn);
            //brukes til lagring av ID til venner
            Integer ID = (int) valgtVenn.getID();
            IDer.add(ID);

            Toast.makeText(this, valgtVenn.getNavn() + " lagt til i bestilling.", Toast.LENGTH_LONG).show();
            populateListView();
        }
        else {
            Toast.makeText(this, valgtVenn.getNavn()+" er allerede lagt til i bestilling.", Toast.LENGTH_LONG).show();
        }
    }


    //må gjøre så siste venn også kan bli slettet
    public void slettValgtVenn() {
        if(sjekkVenn(valgtVenn)) {
            valgteVenner.remove(valgtVenn);

            //brukes til lagring av ID til venner
            Integer ID = (int) valgtVenn.getID();
            IDer.remove(ID);

            Toast.makeText(this, valgtVenn.getNavn()+" fjernet fra bestilling.", Toast.LENGTH_LONG).show();
            populateListView();
        }
        else {
            Toast.makeText(this, valgtVenn.getNavn()+" finnes ikke i bestillingen.", Toast.LENGTH_LONG).show();
        }
    }


    private void populerValgteVenner() {
        Venn v = null;
        String s = "";

        for(int i : IDer) {
            v =  db.finnVenn(i);
            valgteVenner.add(v);
            s += v.getNavn();
        }

        toastMessage(s);
    }


    private void populateListView() {

        //final ArrayList<Venn> valgteVenner = new ArrayList<Venn>();

        //create the list adapter and set the adapter
        final ArrayAdapter<Venn> adapter = new ArrayAdapter<Venn>(this, android.R.layout.simple_list_item_1, valgteVenner);
        mListView.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                valgtVenn = (Venn) mListView.getItemAtPosition(i);
                adapter.remove(valgtVenn);

                //Integer ID = (int) venn.getID();
                //valgtVenn = db.finnVenn(ID);

                //slettValgtVenn();
                //valgteVenner.remove(valgtVennSlett);

                //brukes til lagring av ID til venner
                //Integer ID = (int) valgtVennSlett.getID();
                //IDer.remove(ID);

                //populerValgteVenner();


                adapter.notifyDataSetChanged();

            }
        });
    }




    @Override
    public void onBackPressed() {
        finish();
    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }



    //REGISTRERER BESTILLING I DATABASEN
    private void registrerBestilling() {
        //String dato, String tid, long resturantID, String venner
        String venner = "";
        for(Venn i : valgteVenner) {
            venner += "Navn: "+i.getNavn()+". ";
        }

        Bestilling bestilling = new Bestilling(dato, tid, venner, valgtResturant.getNavn(), valgtResturant.get_ID());
        db.leggTilBestilling(bestilling);
    }



    //-------LAGRING AV DATA VED ROTASJON---------
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //strenger

        outState.putString(NOKKEL_DATO, visDato.getText().toString());
        outState.putString(NOKKEL_TID , visTid.getText().toString());
        //outState.putString(NOKKEL_RESTURANT, antallFeil.getText().toString());
        outState.putIntegerArrayList(NOKKEL_VENNERARRAY, IDer);


        super.onSaveInstanceState(outState);
    }


    //-------HENTING AV LAGRET DATA---------
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        //strenger

        visDato.setText(savedInstanceState.getString(NOKKEL_DATO));
        visTid.setText(savedInstanceState.getString(NOKKEL_TID));
        //antallFeil.setText(savedInstanceState.getString(NOKKEL_ANTFEIL));
        IDer = savedInstanceState.getIntegerArrayList(NOKKEL_VENNERARRAY);

        super.onRestoreInstanceState(savedInstanceState);
    }




   /* @Override
    protected void onPause(){
        super.onPause();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().(NOKKEL_VENNERARRAY, IDer).apply();

    }

    @Override
    protected void onResume(){
        super.onResume();

        IDer = getSharedPreferences("APP_INFO",MODE_PRIVATE).getIntegerArrayList(NOKKEL_VENNERARRAY,"");
    }*/

}

