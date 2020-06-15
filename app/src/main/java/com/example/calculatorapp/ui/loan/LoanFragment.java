package com.example.calculatorapp.ui.loan;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.calculatorapp.R;

public class LoanFragment extends Fragment {

    private LoanViewModel loanViewModel;

    private View root;

    private EditText amount;

    private EditText months;

    private EditText interest;

    private TextView result;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loanViewModel =
                ViewModelProviders.of(this).get(LoanViewModel.class);
        root = inflater.inflate(R.layout.fragment_loan, container, false);

        amount = root.findViewById(R.id.input_amount);
        months = root.findViewById(R.id.input_months);
        interest = root.findViewById(R.id.input_interest);
        result = root.findViewById(R.id.text_result);

        Button btnCalculate = root.findViewById(R.id.button_calculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (amount.getText().toString().matches("\\d+(\\.\\d+)?") && months.getText().toString().matches("\\d+") && interest.getText().toString().matches("\\d+(\\.\\d+)?"))
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

        return root;
    }

    public void calculate()
    {
        double resultNum;
        double amountNum = Double.parseDouble(amount.getText().toString());
        int monthsNum = Integer.parseInt(months.getText().toString());
        double interestNum = Double.parseDouble(interest.getText().toString());

        resultNum = amountNum * (interestNum /100.0);
        resultNum = resultNum / 12;

        String display = String.format("Your monthly repayment will be £%.2f.\n\nThe total amount repayable will be £%.2f, therefore the loan will cost you £%.2f.", Math.round(resultNum*100.0)/100.0, Math.round((amountNum + (resultNum * monthsNum))*100.0)/100.0, Math.round((resultNum * monthsNum)*100.0)/100.0);

        result.setText(display);
    }
}