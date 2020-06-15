package com.example.calculatorapp.ui.temperature;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.calculatorapp.R;

import org.json.JSONException;

import java.math.BigDecimal;

public class TemperatureFragment extends Fragment {

    private TemperatureViewModel temperatureViewModel;

    private View root;

    private EditText inputCelcius;

    private EditText inputFareinheit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        temperatureViewModel =
                ViewModelProviders.of(this).get(TemperatureViewModel.class);
        root = inflater.inflate(R.layout.fragment_temperature, container, false);

        inputCelcius = root.findViewById(R.id.inputCelcius);
        inputFareinheit = root.findViewById(R.id.inputFarenheit);

        inputCelcius.addTextChangedListener(new TextWatcher()
        {
            boolean skipOnChange = false;

            public void afterTextChanged(Editable s)
            {
                if (skipOnChange)
                    return;

                skipOnChange = true;

                if (inputCelcius.getText().toString().matches("\\d+"))
                {
                    double result = ((Double.parseDouble(inputCelcius.getText().toString()) / 5.0) * 9.0) + 32.0;

                    BigDecimal ans = BigDecimal.valueOf(result);
                    ans = ans.setScale(0, BigDecimal.ROUND_HALF_UP);

                    int pos = inputFareinheit.getSelectionEnd();

                    inputFareinheit.setText(ans.toString());
                    inputFareinheit.setSelection(pos);
                }

                skipOnChange = false;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int count, int after) { }
        });

        inputFareinheit.addTextChangedListener(new TextWatcher()
        {
            boolean skipOnChange = false;

            public void afterTextChanged(Editable s)
            {
                if (skipOnChange)
                    return;

                skipOnChange = true;

                if (inputFareinheit.getText().toString().matches("\\d+"))
                {
                    double result = ((Double.parseDouble(inputFareinheit.getText().toString()) - 32.0) * 5.0) / 9.0;

                    BigDecimal ans = BigDecimal.valueOf(result);
                    ans = ans.setScale(0, BigDecimal.ROUND_HALF_UP);

                    int pos = inputCelcius.getSelectionEnd();

                    inputCelcius.setText(ans.toString());
                    inputCelcius.setSelection(pos);
                }

                skipOnChange = false;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int count, int after) { }
        });

        return root;
    }
}