package com.skole.s304114mappe2ny.Fragmenter;

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
import com.skole.s304114mappe2ny.klasser.Bestilling;
import com.skole.s304114mappe2ny.klasser.Deltakelse;

import java.util.ArrayList;

public class SeBestillingsInfoFragment extends AppCompatActivity {

    Integer ID;
    DBhandler db;
    Bestilling bestilling;
    ArrayList<Deltakelse> deltakelser = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent receivedIntent = getIntent();
        db = new DBhandler(this);

        //Henter valgt ID fra se bestillinger
        ID = receivedIntent.getIntExtra("id",0);



        bestilling = db.finnBestilling(ID);
        deltakelser = db.finnAlleDeltakelser();

        //Load setting fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SeBestillingsInfo()).commit();

    }

    public Bestilling getBestiling() {
        return bestilling;
    }


    public String visDeltakelser() {
        String s = "";
        for(Deltakelse d : deltakelser) {
            if(d.getBestillingID() == bestilling.get_ID()) {
                s += d.getVennNavn()+".\n";
            }
        }
        return s;
    }

    public DBhandler getDB() {
        return db;
    }



    //----- start fragment----
    public static class SeBestillingsInfo extends Fragment {

        TextView resNavn, bDato, bTid, bVenner;


        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }




        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_se_bestillings_info, container, false);



            final Bestilling bestilling = ((SeBestillingsInfoFragment)getActivity()).getBestiling();
            String visVenner = ((SeBestillingsInfoFragment)getActivity()).visDeltakelser();

            resNavn = v.findViewById(R.id.resNavn);
            bDato = v.findViewById(R.id.bDato);
            bTid = v.findViewById(R.id.bTid);
            bVenner = v.findViewById(R.id.bVenner);

            resNavn.setText(bestilling.getResturantNavn());
            bDato.setText(bestilling.getDato());
            bTid.setText(bestilling.getTid());
            bVenner.setText(visVenner);


            Button btnOk = v.findViewById(R.id.btnOk);
            Button btnAvbestill = v.findViewById(R.id.btnAvbestill);

            //bare en ja knapp når spill er ferdig
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((SeBestillingsInfoFragment)getActivity()).onBackPressed();
                }
            });

            btnAvbestill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Sletter valgt bestilling fra db
                    ((SeBestillingsInfoFragment)getActivity()).getDB().slettBestilling(bestilling.get_ID());

                    //Sletter alle deltakelser til bestillingen fra db
                    ArrayList<Deltakelse> deltakelser = ((SeBestillingsInfoFragment)getActivity()).getDB().finnAlleDeltakelser();
                    for (Deltakelse d : deltakelser) {
                        if(d.getBestillingID() == bestilling.get_ID()) {
                            ((SeBestillingsInfoFragment)getActivity()).getDB().slettDeltakelse(d.getID());
                        }
                    }
                    ((SeBestillingsInfoFragment)getActivity()).onBackPressed();

                }
            });
            return v;
        }
    } //SLUTT FRAGMENT

    //-------TILBAKEKNAPP - OPPDATERER INTENT FOR Å OPPDATERE SPRÅKENDRING---------
    @Override
    public void onBackPressed() {
        Intent intent_tilbake = new Intent (SeBestillingsInfoFragment.this, SeBestillinger.class);
        startActivity(intent_tilbake);
        finish();
    }
}
