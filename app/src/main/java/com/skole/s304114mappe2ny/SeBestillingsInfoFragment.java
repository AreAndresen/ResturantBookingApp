package com.skole.s304114mappe2ny;


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

public class SeBestillingsInfoFragment extends DialogFragment {

    private DialogClickListener callback;
    TextView bestillingsInfo;
    private String bTekst;


    public interface DialogClickListener{
        void seBestillingsinfoClick();
    }

    /*public SeBestillingsinfoClick() {
        // Required empty public constructor
    }*/


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

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }*/


    //@Override
    //public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Egendefinert alertboks kalles her
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fragment_se_bestillings_info); //setter egen layout her

        //View v = inflater.inflate(R.layout.fragment_se_bestillings_info, container, false);

        //if (bTekst != null) {
        bestillingsInfo = dialog.findViewById(R.id.bestillingTekst);
            //UNPACK OUR DATA FROM OUR BUNDLE

         //bTekst = this.getArguments().getString("BESTIILLING_INFO").toString();
        bestillingsInfo.setText(bTekst);
            //visInfo(v);

        //}
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



    /*public void visInfo(View v) {
        bTekst = this.getArguments().getString("BESTIILLING_INFO").toString();
        bestillingsInfo.setText(bTekst);
    }*/

    /*public void updateString(String s) {
        bTekst = s;
        TextView bestillingsInfo = (TextView) getView().findViewById(R.id.bestillingTekst);
        //WebView wv = (WebView) getView().findViewById(R.id.webside);
        bestillingsInfo.setText(s);
    }*/

}
