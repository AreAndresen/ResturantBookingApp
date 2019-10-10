package com.skole.s304114mappe2ny.LeggTilogEndre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
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
import com.skole.s304114mappe2ny.ListViews.SeVenner;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.Fragmenter.SeBestillingsInfoFragment;
import com.skole.s304114mappe2ny.klasser.Bestilling;
import com.skole.s304114mappe2ny.klasser.Resturant;
import com.skole.s304114mappe2ny.klasser.Venn;
import java.util.ArrayList;


public class RegistrerBestilling extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, SeBestillingsInfoFragment.DialogClickListener { //, SeBestillingsInfoDialog.DialogClickListener

    //--------DIALOG KNAPPER TIL FULLFORTSPILLDIALOGFRAGMENT--------
    //--------DIALOG KNAPPER TIL AVBRYTDIALOGFRAGMENT--------
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

    private ListView mListView;


    private String dato, tid, bestillingsinfo;

    DBhandler db;

    private TextView visDato, visTid;
    private Button btnTilbake, btnLeggTilVenn, btnSlettVenn, velgDato, velgTid, btnSeBestillingsinfo;
    //private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrer_bestilling);


        mListView = (ListView) findViewById(R.id.list);

        //Knapper
        btnTilbake = (Button) findViewById(R.id.btnTilbake);
        btnLeggTilVenn = (Button) findViewById(R.id.btnLeggTilVenn);
        btnSlettVenn = (Button) findViewById(R.id.btnSlettVenn);
        velgDato = (Button) findViewById(R.id.velgDato);
        velgTid = (Button) findViewById(R.id.velgTid);
        btnSeBestillingsinfo = (Button) findViewById(R.id.btnSeBestillingsinfo);

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

                visBestillingsinfo();
            }
        });


        populateListView();

    }
    //UTENFOR CREATE


    //OVERFØRER BESTILLINGSINFO TIL SEBESTILLINGSFRAGMENT
    private void visBestillingsinfo()  {

        //Spanned info = visBestillingsData();


        SeBestillingsInfoFragment bFragment = new SeBestillingsInfoFragment();
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

        //set an onItemClickListener to the ListView
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




    @Override
    public void onBackPressed() {
        finish();
    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


    //-------METODE FOR Å LAGRE RESTULTAT---------
    private void lagreBestilling() {

        //ny lagring til disk
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putString("BESTILLINGSINFO", bestillingsinfo).apply();
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
        outState.putString("BESTILLINGSINFO", bestillingsinfo);


        super.onSaveInstanceState(outState);
    }


    //-------HENTING AV LAGRET DATA---------
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        //strenger
        bestillingsinfo = (savedInstanceState.getString("BESTILLINGSINFO"));


        super.onRestoreInstanceState(savedInstanceState);
    }


}

