package com.example.project_trpp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class WaterAddingFragment extends DialogFragment {

    private EditText mlEditText;

    private Button saveButton;


    public interface WaterAddingDialogListener
    {
        void onMlEntered(int ml);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ml, container, false);
        mlEditText = view.findViewById(R.id.ml_edit_text);
        saveButton = view.findViewById(R.id.hour);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mlString = mlEditText.getText().toString();
                if (!mlString.isEmpty()) {
                    int ml = Integer.parseInt(mlString);
                    WaterAddingFragment.WaterAddingDialogListener listener = (WaterAddingFragment.WaterAddingDialogListener) getActivity();
                    listener.onMlEntered(ml);
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Вы не ввели мл воды!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    /**

     This method will be called when the fragment is visible and in focus.
     */
    @Override
    public void onResume() {

        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.dialog_weight_width);
        int height = getResources().getDimensionPixelSize(R.dimen.dialog_weight_height);
        getDialog().getWindow().setLayout(width, height);
    }
}
