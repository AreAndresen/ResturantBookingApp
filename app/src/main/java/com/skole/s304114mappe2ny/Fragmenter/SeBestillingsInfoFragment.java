package com.skole.s304114mappe2ny.Fragmenter;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.skole.s304114mappe2ny.DBhandler;
import com.skole.s304114mappe2ny.ListViews.SeBestillinger;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.SlettDialoger.AvbestillDialog;
import com.skole.s304114mappe2ny.klasser.Bestilling;
import com.skole.s304114mappe2ny.klasser.Deltakelse;
import com.skole.s304114mappe2ny.klasser.Resturant;
import java.util.ArrayList;


public class SeBestillingsInfoFragment extends AppCompatActivity implements AvbestillDialog.DialogClickListener{


    //--------DIALOG KNAPPER TIL AVBESTILLDIALOG--------
    @Override
    public void jaClick() {
        fullforAvbestilling();
    }

    @Override
    public void neiClick() {
        return;
    }

    //--------VERDIER--------
    Integer ID;
    //--------OBJEKT--------
    Bestilling bestilling;
    Resturant resturant;
    //--------ARRAY--------
    ArrayList<Deltakelse> deltakelser = new ArrayList<>();
    //--------DB HANDLER--------
    DBhandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //--------DB HANDLER--------
        db = new DBhandler(this);


        ////--------HENTER ID TIL BESTILLINGEN SOM SKAL VISES FRA MINNE - DEFINERT I SEBESTILLINGER OG I NOTIFIKASJON/SERVICE--------
        ID = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt("VISNINGSID", 0);

        //--------HENTER BESTILLING MED IDen FRA DB--------
        bestilling = db.finnBestilling(ID);

        //--------HENTER ALLE DELTAKELSER FRA DB--------
        deltakelser = db.finnAlleDeltakelser();


        //--------KJØRER SEBESTILLINGSINFO FRAGMENTET--------
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SeBestillingsInfo()).commit();

    }
    //-------CREATE SLUTTER---------


    //-----------------------METODER TIL HOVEDKLASSE-----------------------------

    //-------HENTER BESTILLINGEN FRA HOVEDKLASSEN - BENYTTES I FRAGMENTET---------
    public Bestilling getBestiling() {
        return bestilling;
    }


    //-------HENTER RESTURANTEN FRA HOVEDKLASSEN - BENYTTES I FRAGMENTET---------
    public Resturant getResturant() {
        //HENTEER RESTURANT ID FRA BESTILLINGEN
        int resID = (int) bestilling.get_resturantID();

        //HENTER RESTURANTEN FRA DB MED ID
        resturant = db.finnResturant(resID);

        return resturant;
    }


    //-------HENTER ALLE DELTAKELSER I BESTILLINGEN - BENYTTES I FRAGMENTET---------
    public String visDeltakelser() {
        String s = "";

        for(Deltakelse d : deltakelser) {
            if(d.getBestillingID() == bestilling.get_ID()) {
                //GENERERER STRENG MED NAVN TIL ALLE VENNER
                s += d.getVennNavn()+".\n";
            }
        }
        return s;
    }


    //-------FULLFØRER AVBESTILLING - BENYTTES INNAD I jaClick() TIL visAvbestillDialog()---------
    public void fullforAvbestilling() {
        //SLETTER VALGT BESTILLING FRA DB
        db.slettBestilling(bestilling.get_ID());

        //SLETTER SAMTIDIG ALLE DELTAKELSER I DB TIL BESTILLINGEN
        ArrayList<Deltakelse> deltakelser = db.finnAlleDeltakelser();
        for (Deltakelse d : deltakelser) {
            if(d.getBestillingID() == bestilling.get_ID()) {
                db.slettDeltakelse(d.getID());
            }
        }
        Intent intent_tilbake = new Intent (SeBestillingsInfoFragment.this, SeBestillinger.class);
        startActivity(intent_tilbake);
        finish();
    }
    //-----------------------METODER TIL HOVEDKLASSE-----------------------------




    //--------FRAGMENT STARTER--------
    public static class SeBestillingsInfo extends Fragment {

        //--------TEKST--------
        TextView resNavn, resTlf, bDato, bTid, bVenner;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_se_bestillings_info, container, false);


            //BENYTTER METODENE FRA HOVEDKLASSEN TIL Å HENTE BESTILLING, RETURANT OG STRENG MED DELTAKELSER
            final Bestilling bestilling = ((SeBestillingsInfoFragment)getActivity()).getBestiling();
            final Resturant resturanten = ((SeBestillingsInfoFragment)getActivity()).getResturant();
            String visVenner = ((SeBestillingsInfoFragment)getActivity()).visDeltakelser();


            //--------TEKST--------
            resNavn = v.findViewById(R.id.resNavn);
            resTlf = v.findViewById(R.id.resTlf);
            bDato = v.findViewById(R.id.bDato);
            bTid = v.findViewById(R.id.bTid);
            bVenner = v.findViewById(R.id.bVenner);

            //--------SETTER TEKST MED HENTET RESTURANT, BESTILLING OG DELTAKELSR--------
            resNavn.setText(resturanten.getNavn());
            resTlf.setText(resturanten.getTelefon());
            bDato.setText(bestilling.getDato());
            bTid.setText(bestilling.getTid());
            bVenner.setText(visVenner);


            //--------KNAPPER--------
            Button btnOk = v.findViewById(R.id.btnOk);
            Button btnAvbestill = v.findViewById(R.id.btnAvbestill);


            //--------LISTENERS--------
            //KLIKK PÅ OK
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //BENYTTER SAMME METODE SOM TILBAKE KNAPP - VISNING AV BESTILLINGER OPPDATERES DERMED FORTLØPENDE
                    ((SeBestillingsInfoFragment)getActivity()).onBackPressed();
                }
            });

            //KLIKK PÅ AVBESTILL
            btnAvbestill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //VISER DIALOG VED AVBESTILLING - SPØR OM BRUKER ER SIKKER PÅ SLETTING/AVBESTILLING
                    visAvbestillDialog();

                }
            });
            return v;
        }


        //-------VISER DIALOG VED AVBESTILL KNAPP-----
        private void visAvbestillDialog() {
            DialogFragment dialog = new AvbestillDialog();
            dialog.show(getFragmentManager(), "Avslutt");
        }

    } //SLUTT FRAGMENT


    //-------TILBAKEKNAPP - OPPDATERER INTENT FOR Å OPPDATERE EVENTUELL SLETTING/AVBESTILLING---------
    @Override
    public void onBackPressed() {
        Intent intent_tilbake = new Intent (SeBestillingsInfoFragment.this, SeBestillinger.class);
        startActivity(intent_tilbake);
        finish();
    }
}
