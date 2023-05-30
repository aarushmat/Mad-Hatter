package edu.msu.mathura5.cloudhatter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;

import edu.msu.cse476.cloudhatter.R;
import edu.msu.mathura5.cloudhatter.Cloud.Cloud;
import edu.msu.mathura5.cloudhatter.Cloud.HatterService;
import edu.msu.mathura5.cloudhatter.Cloud.Models.SaveResult;
import retrofit2.Response;

public class DeleteDlg extends DialogFragment {
    private AlertDialog dlg;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the title
        builder.setTitle(R.string.load_delete_title);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Pass null as the parent view because its going in the dialog layout
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.delete_dlg, null);
        builder.setView(view);
        // Add a cancel button
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // Cancel just closes the dialog box
                dlg.dismiss();
            }
        });
        dlg = builder.create();
        // Find the list view
        ListView list = view.findViewById(R.id.listHattings);

        // Create an adapter
        final Cloud.CatalogAdapter adapter = new Cloud.CatalogAdapter(list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Get the id of the one we want to load
                String catId = adapter.getId(position);
                String catName = adapter.getItem(position).getName();
                // Dismiss the dialog box
                dlg.dismiss();

                DeletingDlg loadDlg = new DeletingDlg();
                loadDlg.setCatId(catId);
                loadDlg.setCatName(catName);
                loadDlg.show(getActivity().getSupportFragmentManager(), "delete");
            }

        });


        return dlg;
    }
}
