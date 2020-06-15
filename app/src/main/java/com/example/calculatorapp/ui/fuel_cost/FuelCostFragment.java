package com.example.calculatorapp.ui.fuel_cost;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FuelCostFragment extends Fragment {

    private FuelCostViewModel fuelCostViewModel;

    private View root;

    private EditText cost;

    private EditText distance;

    private EditText efficiency;

    private Spinner costChoice;

    private Spinner distanceChoice;

    private TextView units;

    private TextView result;

    String costChoiceString = "L";
    String distanceChoiceString = "km";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fuelCostViewModel =
                ViewModelProviders.of(this).get(FuelCostViewModel.class);
        root = inflater.inflate(R.layout.fragment_fuel_cost, container, false);

        cost = root.findViewById(R.id.input_fuel_cost);
        distance = root.findViewById(R.id.input_distance);
        efficiency = root.findViewById(R.id.input_efficiency);
        costChoice = root.findViewById(R.id.costChoice);
        distanceChoice = root.findViewById(R.id.distanceChoice);
        units = root.findViewById(R.id.text_units);
        result = root.findViewById(R.id.text_result);

        Button btnCalculate = root.findViewById(R.id.button_calculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cost.getText().toString().matches("\\d+(\\.\\d+)?") && distance.getText().toString().matches("\\d+") && efficiency.getText().toString().matches("\\d+(\\.\\d+)?"))
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

        List<String> costArray = new ArrayList<>();
        costArray.add("per litre");
        costArray.add("per gallon");

        ArrayAdapter<String> adapterOne = new ArrayAdapter<String>(super.getContext(), android.R.layout.simple_spinner_item, costArray);
        adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        costChoice.setAdapter(adapterOne);



        List<String> distanceArray = new ArrayList<>();
        distanceArray.add("kilometres");
        distanceArray.add("miles");

        ArrayAdapter<String> adapterTwo = new ArrayAdapter<String>(super.getContext(), android.R.layout.simple_spinner_item, distanceArray);
        adapterTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceChoice.setAdapter(adapterTwo);



        costChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (costChoice.getSelectedItem().toString().equals("per litre"))
                {
                    costChoiceString = "L";
                }
                else
                {
                    costChoiceString = "gl";
                }

                units.setText(distanceChoiceString + "/" + costChoiceString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        distanceChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (distanceChoice.getSelectedItem().toString().equals("kilometres"))
                {
                    distanceChoiceString = "km";
                }
                else
                {
                    distanceChoiceString = "m";
                }

                units.setText(distanceChoiceString + "/" + costChoiceString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        return root;
    }

    public void calculate()
    {
        double totalCost;
        double distanceNum = Double.parseDouble(distance.getText().toString());
        double efficiencyNum = Integer.parseInt(efficiency.getText().toString());
        double costNum = Double.parseDouble(cost.getText().toString());

        totalCost = (distanceNum / efficiencyNum) * costNum;

        String display = String.format("Total cost will be £%.2f.", Math.round(totalCost*100.0)/100.0);

        result.setText(display);
    }
}