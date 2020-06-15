package com.example.calculatorapp.ui.simple;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.calculatorapp.R;

import java.util.ArrayList;
import java.util.List;

public class SimpleFragment extends Fragment {

    private SimpleViewModel simpleViewModel;

    private View root;

    private EditText amount;

    private EditText period;

    private EditText interest;

    private Spinner periodChoice;

    private Spinner interestChoice;

    private TextView result;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        simpleViewModel =
                ViewModelProviders.of(this).get(SimpleViewModel.class);
        root = inflater.inflate(R.layout.fragment_simple, container, false);

        amount = root.findViewById(R.id.input_amount);
        period = root.findViewById(R.id.input_period);
        interest = root.findViewById(R.id.input_interest);
        periodChoice = root.findViewById(R.id.periodChoice);
        interestChoice = root.findViewById(R.id.interestChoice);
        result = root.findViewById(R.id.text_result);


        Button btnCalculate = root.findViewById(R.id.button_calculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount.getText().toString().matches("\\d+(\\.\\d+)?") && period.getText().toString().matches("\\d+") && interest.getText().toString().matches("\\d+(\\.\\d+)?"))
                {
                    result.setVisibility(View.VISIBLE);
                    calculate();
                }
                else
                {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getContext());
                    dlgAlert.setMessage("Some values have been entered incorrectly!");
                    dlgAlert.setTitle("Error");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
            }
        });


        List<String> periodArray = new ArrayList<>();
        periodArray.add("years");
        periodArray.add("months");

        ArrayAdapter<String> periodAdapter = new ArrayAdapter<String>(super.getContext(), android.R.layout.simple_spinner_item, periodArray);
        periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodChoice.setAdapter(periodAdapter);



        List<String> interestArray = new ArrayList<>();
        interestArray.add("yearly");
        interestArray.add("monthly");

        ArrayAdapter<String> interestAdapter = new ArrayAdapter<String>(super.getContext(), android.R.layout.simple_spinner_item, interestArray);
        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interestChoice.setAdapter(interestAdapter);


        return root;
    }

    public void calculate()
    {
        double totalBalance = 0.0;
        double amountNum = Double.parseDouble(amount.getText().toString());
        double periodNum = Double.parseDouble(period.getText().toString());
        double interestNum = Double.parseDouble(interest.getText().toString());

        String periodChoiceString = periodChoice.getSelectedItem().toString();
        String interestChoiceString = interestChoice.getSelectedItem().toString();

        if (periodChoiceString.equals("months"))
        {
            if (interestChoiceString.equals("yearly") && periodNum < 12.0)
            {
                return;
            }
            else
            {
                periodNum = periodNum / 12.0;
            }
        }
        else if (periodChoiceString.equals("years") && interestChoiceString.equals("monthly"))
        {
            periodNum = periodNum * 12.0;
        }

        totalBalance = amountNum + (amountNum * periodNum * (interestNum / 100.0));

        String display = String.format("Your total balance at the end of the interest period will be £%.2f.\n\nThe total interest earned will be £%.2f.", Math.round(totalBalance*100.0)/100.0, Math.round((totalBalance - amountNum)*100.0)/100.0);

        result.setText(display);
    }
}