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


    @Override
    public void jaClick() {
        fullforAvbestilling();
    }

    @Override
    public void neiClick() {
        return;
    }

    Integer ID;
    DBhandler db;
    Bestilling bestilling;
    ArrayList<Deltakelse> deltakelser = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DBhandler(this);


        //ny variabel
        ID = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt("VISNINGSID", 0);


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

    public Resturant getResturant() {
        int resID = (int) bestilling.get_resturantID();

        Resturant resturanten = db.finnResturant(resID);

        return resturanten;
    }

    public DBhandler getDB() {
        return db;
    }

    //metode som sletter bestilling og deltakelser
    public void fullforAvbestilling() {
        //Sletter valgt bestilling fra db
        db.slettBestilling(bestilling.get_ID());

        //Sletter alle deltakelser til bestillingen fra db
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




    //----- start fragment----
    public static class SeBestillingsInfo extends Fragment {

        TextView resNavn, resTlf, bDato, bTid, bVenner;


        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_se_bestillings_info, container, false);



            final Bestilling bestilling = ((SeBestillingsInfoFragment)getActivity()).getBestiling();
            final Resturant resturanten = ((SeBestillingsInfoFragment)getActivity()).getResturant();
            String visVenner = ((SeBestillingsInfoFragment)getActivity()).visDeltakelser();

            resNavn = v.findViewById(R.id.resNavn);
            resTlf = v.findViewById(R.id.resTlf);
            bDato = v.findViewById(R.id.bDato);
            bTid = v.findViewById(R.id.bTid);
            bVenner = v.findViewById(R.id.bVenner);

            resNavn.setText(resturanten.getNavn());
            resTlf.setText(resturanten.getTelefon());
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

                    visAvbestillDialog();

                }
            });
            return v;
        }

        //-------VISER DIALOG VED AVBRYT---------
        private void visAvbestillDialog() {
            DialogFragment dialog = new AvbestillDialog();
            dialog.show(getFragmentManager(), "Avslutt");
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
