package com.skole.s304114mappe2ny.Fragmenter;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.skole.s304114mappe2ny.DBhandler;
import com.skole.s304114mappe2ny.LeggTilogEndre.EndreResturant;
import com.skole.s304114mappe2ny.ListViews.SeBestillinger;
import com.skole.s304114mappe2ny.ListViews.SeResturanter;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.klasser.Bestilling;

public class SeBestillingsInfoFragment extends AppCompatActivity {

    Integer ID;
    DBhandler db;
    Bestilling bestilling;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent receivedIntent = getIntent();
        db = new DBhandler(this);

        ID = receivedIntent.getIntExtra("id",0); //NOTE: -1 is just the default value

        bestilling = db.finnBestilling(ID);

        //Load setting fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SeBestillingsInfo()).commit();

    }

    public Bestilling getBestiling() {
        return bestilling;
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

            resNavn = v.findViewById(R.id.resNavn);
            bDato = v.findViewById(R.id.bDato);
            bTid = v.findViewById(R.id.bTid);
            bVenner = v.findViewById(R.id.bVenner);

            resNavn.setText(bestilling.getResturantNavn());
            bDato.setText(bestilling.getDato());
            bTid.setText(bestilling.getTid());
            bVenner.setText(bestilling.getVenner());


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

                    ((SeBestillingsInfoFragment)getActivity()).getDB().slettBestilling(bestilling.get_ID());
                    ((SeBestillingsInfoFragment)getActivity()).onBackPressed();

                    //Toast.makeText(this, bestilling.get_ID() + " lagt til i bestilling.", Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(),"Bestilling utført",Toast.LENGTH_LONG).show();

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
