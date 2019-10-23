package com.skole.s304114mappe2ny.SlettDialoger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import com.skole.s304114mappe2ny.R;


public class AvbestillDialog extends DialogFragment {

    private DialogClickListener callback;

    public interface DialogClickListener{
        void jaClick();
        void neiClick();
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

    //--------DIALOGBOKS FOR AVBESTILLING--------
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity()).setTitle("Avbestilling").setMessage("Er du sikker på at du vil fullføre avbestillingen?").
                setPositiveButton(R.string.ja, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton){
                        callback.jaClick();
                    }
                })
                .setNegativeButton(R.string.nei,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton){
                        callback.neiClick();
                    }
                })
                .create();
    }
}