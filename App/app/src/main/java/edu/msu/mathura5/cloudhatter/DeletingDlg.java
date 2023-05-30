package edu.msu.mathura5.cloudhatter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import edu.msu.cse476.cloudhatter.R;
import edu.msu.mathura5.cloudhatter.Cloud.Cloud;
import edu.msu.mathura5.cloudhatter.Cloud.Models.Hat;

public class DeletingDlg extends DialogFragment {
    private AlertDialog dlg,dlg1;
    private volatile boolean cancel = false;
    private final static String ID = "id";
    private final static String NAME = "name";
    /**
     * Id for the image we are loading
     */
    private String catId;
    private String catName;

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }
    public void setCatName(String catName) {
        this.catName = catName;
    }
    /**
     * Save the instance state
     */
    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putString(ID, catId);
        bundle.putString(NAME, catName);
    }
    /**
     * Called when the view is destroyed.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancel = true;
    }
    private void delete(final String id) {
        // Get a reference to the view we are going to load into
        final HatterView view = (HatterView)getActivity().findViewById(R.id.hatterView);
        /*
         * Create a thread to load the hatting from the cloud
         */
        new Thread(new Runnable() {

            @Override
            public void run() {
                // Create a cloud object and get the XML
                Cloud cloud = new Cloud();
                boolean hat = cloud.deletefromCloud(catId);

                view.post(new Runnable() {

                    @Override
                    public void run() {
                        dlg1.dismiss();
                        if(hat) {
                            Toast.makeText(view.getContext(),
                                    R.string.deleting_success,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(view.getContext(),
                                    R.string.deleting_fail,
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                });
            }
        }).start();

    }
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        if(bundle != null) {
            catId = bundle.getString(ID);
            catName = bundle.getString(NAME);
        }
        cancel = false;


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.deleteing);
                LayoutInflater inflater = getActivity().getLayoutInflater();
                @SuppressLint("InflateParams")
                View view1 = inflater.inflate(R.layout.delete_dlg_options, null);
                builder.setView(view1);

                TextView textView = view1.findViewById(R.id.ask);

                String selectedFromList = String.valueOf(catName);
                String text = String.format(getString(R.string.delete_option), selectedFromList);
                textView.setText(text);


             builder.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        cancel = true;
                    }
                });
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        delete(catId);
                    }
                });

        dlg1 = builder.create();
        return dlg1;
    }


}