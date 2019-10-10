package com.skole.s304114mappe2ny.Fragmenter;


import android.app.FragmentManager;
import android.os.Bundle;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.klasser.Bestilling;


public class SeBestillingsInfoDialog extends DialogFragment {

    TextView resNavn, bDato, bTid, bVenner;
    private Bestilling bestilling;

    private DialogClickListener callback;


    public interface DialogClickListener{
        void bestillClick();
        void avbrytClick();
    }


    public void hentBestilling(Bestilling bestilling) {
        this.bestilling = bestilling;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            callback = (DialogClickListener)getActivity();
        }
        catch(ClassCastException e) {
            throw new ClassCastException("Feil ved kalling av interface!");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.activity_se_bestillings_info); //setter egen layout her


        resNavn = dialog.findViewById(R.id.resNavn);
        bDato = dialog.findViewById(R.id.bDato);
        bTid = dialog.findViewById(R.id.bTid);
        bVenner = dialog.findViewById(R.id.bVenner);


        resNavn.setText(bestilling.getResturantNavn());
        bDato.setText(bestilling.getDato());
        bTid.setText(bestilling.getTid());
        bVenner.setText(bestilling.getVenner());


        Button btnOk = dialog.findViewById(R.id.btnOk);
        Button btnTilbake = dialog.findViewById(R.id.btnTilbake);

        //bare en ja knapp når spill er ferdig
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.bestillClick();

                dismiss();
            }
        });

        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.avbrytClick();
                dismiss();
            }
        });
        dialog.show();
        return dialog;

    }
}

