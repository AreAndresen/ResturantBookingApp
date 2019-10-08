package com.skole.s304114mappe2ny;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class SeBestillingsInfoDialog extends DialogFragment {
    private DialogClickListener callback;
    private String bestillingTeksten;

    /*TextView bestillingTekst;
    private String bestillingTeksten;*/

    public void init(String b) {
        bestillingTeksten = b;
    }

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bestillingsinfo_dialog, container, false);

        /*if (bestillingTeksten != null) {
            TextView bestillingTekst = (TextView) v.findViewById(R.id.bestillingTekst);
            bestillingTekst.setText(bestillingTeksten);
        }
        return v;*/

        /*bestillingTekst = (TextView) v.findViewById(R.id.bestillingTekst);
        Bundle b = getArguments();
        String bestilling = b.getString("Bestillingstekst");
        bestillingTekst.setText(bestilling);

        return v;
    }

    public void updateUrl(String s) {
        bestillingTeksten = s;
        TextView wv = (TextView) getView().findViewById(R.id.bestillingTekst);
        wv.setText(s);
    }*/


   public interface DialogClickListener{
        void seBestillingsinfoClick();
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


    //Egendefinert alertboks kalles her
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.bestillingsinfo_dialog); //setter egen layout her
        Button ok = dialog.findViewById(R.id.btnOk);

        //bare en ja knapp når spill er ferdig
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.seBestillingsinfoClick();
                dismiss();
            }
        });
        dialog.show();
        return dialog;
    }


}


