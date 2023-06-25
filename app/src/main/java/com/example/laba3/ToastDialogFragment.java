package com.example.laba3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

public class ToastDialogFragment extends DialogFragment
{
    private FragmentActivity _activity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        _activity = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(_activity);

        builder.setMessage(R.string.dialog_message);
        builder.setPositiveButton(R.string.yes, (dialog, id) -> _activity.finish());
        builder.setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel());

        return builder.create();
    }
}
