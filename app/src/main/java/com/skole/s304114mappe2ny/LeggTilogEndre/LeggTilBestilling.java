package com.skole.s304114mappe2ny.LeggTilogEndre;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.skole.s304114mappe2ny.DBhandler;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.klasser.Resturant;
import com.skole.s304114mappe2ny.klasser.Venn;
import java.util.ArrayList;


public class LeggTilBestilling extends AppCompatActivity {

    private Spinner spinnerResturanter, spinnerVenner;
    private Resturant valgtResturant;
    private Venn valgtVenn;
    private ArrayList<Venn> valgteVenner = new ArrayList<Venn>();


    DBhandler db;

    private TextView bestillingTekst;
    private Button btnTilbake, btnLeggTilVenn, btnSlettVenn;
    //private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legg_til_bestilling);

        //Knapper
        btnTilbake = (Button) findViewById(R.id.btnTilbake);
        btnLeggTilVenn = (Button) findViewById(R.id.btnLeggTilVenn);
        btnSlettVenn = (Button) findViewById(R.id.btnSlettVenn);


        bestillingTekst = (TextView) findViewById(R.id.bestillingTekst);
        spinnerResturanter = (Spinner) findViewById(R.id.spinResturant);
        spinnerVenner = (Spinner) findViewById(R.id.spinVenn);

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

