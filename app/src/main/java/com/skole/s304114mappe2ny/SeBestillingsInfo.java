package com.skole.s304114mappe2ny;


import android.os.Bundle;

import android.app.Dialog;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.skole.s304114mappe2ny.klasser.Bestilling;
import com.skole.s304114mappe2ny.klasser.Resturant;
import com.skole.s304114mappe2ny.klasser.Venn;

import java.util.ArrayList;

public class SeBestillingsInfo extends Fragment {

    TextView resNavn, bDato, bTid, bVenner;
    private Bestilling bestilling;


    public void hentBestilling(Bestilling bestilling) {
        this.bestilling = bestilling;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public SeBestillingsInfo() {

    }


    //@Override
    //public Dialog onCreateDialog(Bundle savedInstanceState) {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_se_bestillings_info, container, false);
        //final Dialog dialog = new Dialog(getActivity());
        //dialog.setContentView(R.layout.activity_se_bestillings_info); //setter egen layout her


        /*resNavn = dialog.findViewById(R.id.resNavn);
        bDato = dialog.findViewById(R.id.bDato);
        bTid = dialog.findViewById(R.id.bTid);
        bVenner = dialog.findViewById(R.id.bVenner);*/

        resNavn = v.findViewById(R.id.resNavn);
        bDato = v.findViewById(R.id.bDato);
        bTid = v.findViewById(R.id.bTid);
        bVenner = v.findViewById(R.id.bVenner);

        resNavn.setText(bestilling.getResturantNavn());
        bDato.setText(bestilling.getDato());
        bTid.setText(bestilling.getTid());
        bVenner.setText(bestilling.getVenner());


        Button btnOk = v.findViewById(R.id.btnOk);
        Button btnTilbake = v.findViewById(R.id.btnTilbake);

        //bare en ja knapp når spill er ferdig
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callback.bestillClick();

                //dismiss();
            }
        });

        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callback.avbrytClick();
                //dismiss();
            }
        });
        //dialog.show();
        //return dialog;

        return v;
    }


}

