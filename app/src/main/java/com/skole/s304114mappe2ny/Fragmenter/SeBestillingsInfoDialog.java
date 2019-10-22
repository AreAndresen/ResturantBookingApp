package com.skole.s304114mappe2ny.Fragmenter;


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.skole.s304114mappe2ny.DBhandler;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.klasser.Resturant;
import com.skole.s304114mappe2ny.klasser.Venn;

import java.util.ArrayList;


public class SeBestillingsInfoDialog extends DialogFragment {

    private DialogClickListener callback;

    private DBhandler db;

    TextView resNavn, resTlf, bDato, bTid, bVenner;

    private String dato;
    private String tid;
    private Resturant resturant;
    private ArrayList<Venn> venner = new ArrayList<>();


    public interface DialogClickListener{

        void bestillClick();
        void avbrytClick();
    }


    //METODE FOR Å OVERFØRE INFO TIL DIALOG
   public void hentInfo(String dato, String tid, Resturant valgtResturant, ArrayList<Venn> venner, DBhandler db) {
        this.dato = dato;
        this.tid = tid;
        this.resturant = valgtResturant;
        this.venner = venner;
        this.db = db;
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
        dialog.setContentView(R.layout.dialog_se_bestillings_info); //setter egen layout her


        resNavn = dialog.findViewById(R.id.resNavn);
        resTlf = dialog.findViewById(R.id.resTlf);
        bDato = dialog.findViewById(R.id.bDato);
        bTid = dialog.findViewById(R.id.bTid);
        bVenner = dialog.findViewById(R.id.bVenner);

        resNavn.setText(resturant.getNavn());
        resTlf.setText(resturant.getTelefon());
        bDato.setText(dato);
        bTid.setText(tid);

        String vennNavn = "";
        for (Venn i : venner) {
            vennNavn += i.getNavn()+"\n";
        }
        bVenner.setText(vennNavn);


        Button btnBestill = dialog.findViewById(R.id.btnOk);
        Button btnAvbryt = dialog.findViewById(R.id.btnAvbryt);

        //bare en ja knapp når spill er ferdig
        btnBestill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.bestillClick();

                dismiss();
            }
        });

        btnAvbryt.setOnClickListener(new View.OnClickListener() {
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