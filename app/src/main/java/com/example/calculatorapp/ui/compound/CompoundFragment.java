package com.example.calculatorapp.ui.compound;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.calculatorapp.R;

import java.util.ArrayList;
import java.util.List;
//Code used for popup window from website:
//https://tekeye.uk/android/examples/ui/android-popup-window

public class CompoundFragment extends Fragment {

    private CompoundViewModel compoundViewModel;

    private View root;

    private EditText amount;

    private EditText period;

    private EditText interest;

    private Spinner periodChoice;

    private Spinner interestChoice;

    private TextView result;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        compoundViewModel =
                ViewModelProviders.of(this).get(CompoundViewModel.class);
        root = inflater.inflate(R.layout.fragment_compound, container, false);

        final View layout = inflater.inflate(R.layout.relative_layout, null);
        final float density = this.getResources().getDisplayMetrics().density;

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

        Button button = root.findViewById(R.id.button_info);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ((TextView) layout.findViewById(R.id.text_info)).setText("Compound interest is interest calculated on the initial amount and also on the accumulated interest from previous periods and will make a sum grow at a faster rate than simple interest.");

                final PopupWindow popup = new PopupWindow(layout, (int) density * 240, (int) density * 285, true);

                ((Button) layout.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        popup.dismiss();
                    }
                });

                popup.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                popup.setTouchInterceptor(new View.OnTouchListener()
                {
                    public boolean onTouch(View v, MotionEvent event)
                    {
                        if(event.getAction() == MotionEvent.ACTION_OUTSIDE)
                        {
                            popup.dismiss();
                            return true;
                        }
                        return false;
                    }
                });
                popup.setOutsideTouchable(true);

                popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });

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

        totalBalance = amountNum * Math.pow((1.0 +(interestNum / 100.0)), periodNum);

        String display = String.format("Your total balance at the end of the interest period will be £%.2f.\n\nThe total interest earned will be £%.2f.", Math.round(totalBalance*100.0)/100.0, Math.round((totalBalance - amountNum)*100.0)/100.0);

        result.setText(display);
    }
}