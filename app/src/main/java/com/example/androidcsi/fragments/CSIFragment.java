package com.example.androidcsi.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.androidcsi.R;

import java.util.prefs.Preferences;

public class CSIFragment extends Fragment {
    View view;
    NumberPicker pickerHighRating;
    NumberPicker pickerLowRating;
    TextView textResult;
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        preferences = this.getActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        view = inflater.inflate(R.layout.fragment_csi, container, false);
        textResult = view.findViewById(R.id.textCSI);

        pickerHighRating = view.findViewById(R.id.picker_high_rating);
        pickerHighRating.setMinValue(0);
        pickerHighRating.setMaxValue(Integer.parseInt(preferences.getString("ratingCount", "100")));
        pickerLowRating = view.findViewById(R.id.picker_low_rating);
        pickerLowRating.setMinValue(0);
        pickerLowRating.setMaxValue(Integer.parseInt(preferences.getString("ratingCount", "100")));

        pickerHighRating.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                textResult.setText(Integer.toString(getResult(pickerHighRating.getValue(), pickerLowRating.getValue())));
            }
        });

        pickerLowRating.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                textResult.setText(Integer.toString(getResult(pickerHighRating.getValue(), pickerLowRating.getValue())));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        pickerHighRating.setMaxValue(Integer.parseInt(preferences.getString("ratingCount", "100")));
        pickerLowRating.setMaxValue(Integer.parseInt(preferences.getString("ratingCount", "100")));
        super.onResume();
    }

    private int getResult(double HighRating, double LowRating) {
        if ((HighRating + LowRating) != 0) {
            double result = 100 * (HighRating - LowRating) / (HighRating + LowRating);
            return (int) Math.round(result);
        } else return 0;
    }
}