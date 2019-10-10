package com.skole.s304114mappe2ny;


import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.skole.s304114mappe2ny.Fragmenter.SeBestillingsInfoFragment;
import com.skole.s304114mappe2ny.klasser.Bestilling;
import com.skole.s304114mappe2ny.klasser.Resturant;
import com.skole.s304114mappe2ny.klasser.Venn;

import java.util.ArrayList;


public class EndreBestillingFragment extends Fragment {
    /*private SeBestillingsInfoFragment.DialogClickListener callback;
    //TextView bestillingsInfo;
    //private Spanned bTekst;

    private DBhandler db;

    TextView resNavn, resTlf, bDato, bTid, bVenner;

    //private long _ID;
    private String dato;
    private String tid;
    //private String resturantNavn;
    //private String resturantTlf;
    private Resturant resturant;
    private ArrayList<Venn> venner = new ArrayList<>();



    public interface DialogClickListener{

        void bestillClick();
        void avbrytClick();
    }



    public void hentInfo(String dato, String tid, Resturant valgtResturant, ArrayList<Venn> venner, DBhandler db) {
        this.dato = dato;
        this.tid = tid;
        //this.resturantNavn = resturantNavn;
        //this.resturantTlf = resturantTlf;
        this.resturant = valgtResturant;
        this.venner = venner;
        this.db = db;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            callback = (SeBestillingsInfoFragment.DialogClickListener)getActivity();
        }
        catch(ClassCastException e) {
            throw new ClassCastException("Feil ved kalling av interface!");
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_endre_bestilling, container, false);

        ListView lv = (ListView) v.findViewById(R.id.liste);

        String[] values = new String[]{"minfil.txt","feil.html"};
        final ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String data = adapter.getItem(i);
                listener.linkEndret(data);
            }
        });

        return v;
    }

    @Override
    public Dialog onCreateFragment(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fragment_se_bestillings_info); //setter egen layout her


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
            vennNavn += i.getNavn()+" "+i.getTelefon()+".\n";
        }
        bVenner.setText(vennNavn);


        Button btnBestill = dialog.findViewById(R.id.btnOk);
        Button btnAvbryt = dialog.findViewById(R.id.btnAvbryt);

        //bare en ja knapp når spill er ferdig
        btnBestill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.bestillClick();

                String vennene = "";
                for(Venn i : venner) {
                    vennene += "Navn: "+i.getNavn()+". ";
                }

                Bestilling bestilling = new Bestilling(dato, tid, vennene, resturant.getNavn(), resturant.get_ID());
                db.leggTilBestilling(bestilling);

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
*/


}

