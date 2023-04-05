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

/**

 A dialog fragment used to prompt the user to input their weight.
 */
public class WeightFragment extends DialogFragment {

    /**

     The EditText used to input the user's weight.
     */
    private EditText weightEditText;
    /**

     The button used to save the user's weight.
     */
    private Button saveButton;
    /**

     Interface definition for a callback to be invoked when the user enters their weight.
     */
    public interface WeightDialogListener {

        /**

         Called when the user has entered their weight.
         @param weight The weight entered by the user.
         */
        void onWeightEntered(int weight);
    }
    /**

     Called to have the fragment instantiate its user interface view. This method is

     called between onCreate(Bundle) and onActivityCreated(Bundle).

     @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.

     @param container If non-null, this is the parent view that the fragment's UI should be attached to.

     @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.

     @return Return the View for the fragment's UI, or null.
     */
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

    /**

     This method will be called when the fragment is visible and in focus.
     */
    @Override
    public void onResume() {

        int width = getResources().getDimensionPixelSize(R.dimen.dialog_weight_width);
        int height = getResources().getDimensionPixelSize(R.dimen.dialog_weight_height);
        getDialog().getWindow().setLayout(width, height);
    }
}