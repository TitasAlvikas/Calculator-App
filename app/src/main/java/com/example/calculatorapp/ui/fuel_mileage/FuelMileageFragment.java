package com.example.calculatorapp.ui.fuel_mileage;

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

public class FuelMileageFragment extends Fragment {

    private FuelMileageViewModel fuelMileageViewModel;

    private View root;

    private EditText distance;

    private EditText efficiency;

    private Spinner unitsChoice;

    private Spinner efficiencyChoice;

    private Spinner distanceChoice;

    private TextView result;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fuelMileageViewModel =
                ViewModelProviders.of(this).get(FuelMileageViewModel.class);
        root = inflater.inflate(R.layout.fragment_fuel_mileage, container, false);

        distance = root.findViewById(R.id.input_distance);
        efficiency = root.findViewById(R.id.input_efficiency);
        unitsChoice = root.findViewById(R.id.unitsChoice);
        efficiencyChoice = root.findViewById(R.id.efficiencyChoice);
        distanceChoice = root.findViewById(R.id.distanceChoice);
        result = root.findViewById(R.id.text_result);

        List<String> unitsArray = new ArrayList<>();
        unitsArray.add("Litres");
        unitsArray.add("Gallons");

        ArrayAdapter<String> unitsAdapter = new ArrayAdapter<String>(super.getContext(), android.R.layout.simple_spinner_item, unitsArray);
        unitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitsChoice.setAdapter(unitsAdapter);



        List<String> distanceArray = new ArrayList<>();
        distanceArray.add("kilometres");
        distanceArray.add("miles");

        ArrayAdapter<String> distanceAdapter = new ArrayAdapter<String>(super.getContext(), android.R.layout.simple_spinner_item, distanceArray);
        distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceChoice.setAdapter(distanceAdapter);
        efficiencyChoice.setAdapter(distanceAdapter);

        Button btnCalculate = root.findViewById(R.id.button_calculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (distance.getText().toString().matches("\\d+(\\.\\d+)?") && efficiency.getText().toString().matches("\\d+(\\.\\d+)?"))
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
        double fuelConsumed;
        double distanceNum = Double.parseDouble(distance.getText().toString());
        double efficiencyNum = Integer.parseInt(efficiency.getText().toString());

        String distanceUnits = distanceChoice.getSelectedItem().toString();
        String efficiencyUnits = efficiencyChoice.getSelectedItem().toString();

        if (!distanceUnits.equals(efficiencyUnits))
        {
            if (distanceUnits.equals("miles"))
            {
                distanceNum = (distanceNum / 8.0) * 5.0;
            }
            else
            {
                efficiencyNum = (efficiencyNum / 5.0) * 8.0;
            }
        }

        String fuelUnits = unitsChoice.getSelectedItem().toString().equals("Litres") ? "L" : "gl";

        fuelConsumed = (distanceNum / efficiencyNum);

        String display = String.format("Total fuel consumed will be %.1f %s.", Math.round(fuelConsumed*100.0)/100.0, fuelUnits);

        result.setText(display);
    }
}