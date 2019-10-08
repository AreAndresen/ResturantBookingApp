package com.skole.s304114mappe2ny.Fragmenter;


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.skole.s304114mappe2ny.R;

public class SeBestillingsInfoFragment extends DialogFragment {

    private DialogClickListener callback;
    TextView bestillingsInfo;
    private String bTekst;


    public interface DialogClickListener{
        void seBestillingsinfoClick();
    }

   public void init(String s) {
        bTekst = s;
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
        dialog.setContentView(R.layout.fragment_se_bestillings_info); //setter egen layout her

        bestillingsInfo = dialog.findViewById(R.id.bestillingTekst);
        bestillingsInfo.setText(bTekst);

        Button dialogButton = dialog.findViewById(R.id.btnOk);

        //bare en ja knapp når spill er ferdig
        dialogButton.setOnClickListener(new View.OnClickListener() {
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
