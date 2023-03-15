package com.example.project_trpp;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class WeightFragment extends DialogFragment {

    private EditText weightEditText;
    private Button saveButton;

    public interface WeightDialogListener {
        void onWeightEntered(int weight);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weight, container, false);
        weightEditText = view.findViewById(R.id.weight_edit_text);
        saveButton = view.findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weightString = weightEditText.getText().toString();
                if (!weightString.isEmpty()) {
                    int weight = Integer.parseInt(weightString);
                    WeightDialogListener listener = (WeightDialogListener) getActivity();
                    listener.onWeightEntered(weight);
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Вы не ввели ваш вес!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Установить ширину и высоту окна фрагмента
        int width = getResources().getDimensionPixelSize(R.dimen.dialog_weight_width);
        int height = getResources().getDimensionPixelSize(R.dimen.dialog_weight_height);
        getDialog().getWindow().setLayout(width, height);
    }
}
