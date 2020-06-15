package com.example.calculatorapp.ui.units;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.calculatorapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UnitsFragment extends Fragment {

    private UnitsViewModel unitsViewModel;

    private View root;

    private EditText firstInput;

    private EditText secondInput;

    private Spinner spinnerType;

    private Spinner firstDropDown;

    private Spinner secondDropDown;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        unitsViewModel =
                ViewModelProviders.of(this).get(UnitsViewModel.class);
        root = inflater.inflate(R.layout.fragment_units, container, false);

        firstInput = root.findViewById(R.id.firstInput);
        secondInput = root.findViewById(R.id.secondInput);
        spinnerType = root.findViewById(R.id.spinner_type);;
        firstDropDown = root.findViewById(R.id.first);
        secondDropDown = root.findViewById(R.id.second);

        firstInput.requestFocus();

        List<String> conversionsArray = new ArrayList<>();
        conversionsArray.add("Length");
        conversionsArray.add("Area");
        conversionsArray.add("Weight");
        conversionsArray.add("Volume");

        ArrayAdapter<String> conversionsAdapter = new ArrayAdapter<String>(super.getContext(), android.R.layout.simple_spinner_item, conversionsArray);
        conversionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(conversionsAdapter);



        List<String> lengthArray = new ArrayList<>();
        lengthArray.add("Metre");
        lengthArray.add("Kilometre");
        lengthArray.add("Centimetre");
        lengthArray.add("Millimetre");
        lengthArray.add("Mile");
        lengthArray.add("Yard");
        lengthArray.add("Foot");
        lengthArray.add("Inch");

        final ArrayAdapter<String> lengthAdapter = new ArrayAdapter<String>(super.getContext(), android.R.layout.simple_spinner_item, lengthArray);
        lengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        List<String> areaArray = new ArrayList<>();
        areaArray.add("Square Meter");
        areaArray.add("Square Kilometer");
        areaArray.add("Square Centimeter");
        areaArray.add("Square Mile");
        areaArray.add("Hectare");
        areaArray.add("Acre");

        final ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(super.getContext(), android.R.layout.simple_spinner_item, areaArray);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        List<String> weightArray = new ArrayList<>();
        weightArray.add("Kilogram");
        weightArray.add("Gram");
        weightArray.add("Milligram");
        weightArray.add("Ton");
        weightArray.add("Pound");
        weightArray.add("Ounce");

        final ArrayAdapter<String> weightAdapter = new ArrayAdapter<String>(super.getContext(), android.R.layout.simple_spinner_item, weightArray);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        List<String> volumeArray = new ArrayList<>();
        volumeArray.add("Cubic Metre");
        volumeArray.add("Litre");
        volumeArray.add("Millilitre");
        volumeArray.add("Gallon");
        volumeArray.add("Pint");

        final ArrayAdapter<String> volumeAdapter = new ArrayAdapter<String>(super.getContext(), android.R.layout.simple_spinner_item, volumeArray);
        volumeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        final JSONObject conversions = new JSONObject();
        try
        {
            conversions.put("Metre", "1.0");
            conversions.put("Kilometre", "0.001");
            conversions.put("Millimetre", "1000");
            conversions.put("Mile", "0.0006213689");
            conversions.put("Yard", "1.0936132983");
            conversions.put("Foot", "3.280839895");
            conversions.put("Inch", "39.37007874");
            conversions.put("Square Meter", "1.0");
            conversions.put("Square Kilometer", "0.000001");
            conversions.put("Square Centimeter", "10000");
            conversions.put("Square Mile", "0.0000003861018768");
            conversions.put("Hectare", "0.0001");
            conversions.put("Acre", "0.0002471054");
            conversions.put("Kilogram", "1.0");
            conversions.put("Gram", "1000.0");
            conversions.put("Milligram", "1000000.0");
            conversions.put("Ton", "0.001");
            conversions.put("Pound", "2.2046244202");
            conversions.put("Ounce", "35.273990723");
            conversions.put("Cubic Metre", "1.0");
            conversions.put("Litre", "1000.0");
            conversions.put("Millilitre", "1000000.0");
            conversions.put("Gallon", "219.9692483");
            conversions.put("Pint", "1759.7539864");
        }
        catch (JSONException e)
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getContext());
            dlgAlert.setMessage("Fatal error!");
            dlgAlert.setTitle("Error");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }


        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                firstDropDown.setAdapter(null);
                secondDropDown.setAdapter(null);

                switch (spinnerType.getSelectedItem().toString())
                {
                    case "Length":
                        firstDropDown.setAdapter(lengthAdapter);
                        secondDropDown.setAdapter(lengthAdapter);
                        break;
                    case "Area":
                        firstDropDown.setAdapter(areaAdapter);
                        secondDropDown.setAdapter(areaAdapter);
                        break;
                    case "Weight":
                        firstDropDown.setAdapter(weightAdapter);
                        secondDropDown.setAdapter(weightAdapter);
                        break;
                    case "Volume":
                        firstDropDown.setAdapter(volumeAdapter);
                        secondDropDown.setAdapter(volumeAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });


        firstInput.addTextChangedListener(new TextWatcher()
        {
            boolean skipOnChange = false;

            public void afterTextChanged(Editable s)
            {
                if (skipOnChange)
                    return;

                skipOnChange = true;

                if (firstInput.getText().toString().matches("\\d+(\\.\\d+)?"))
                {
                    try
                    {
                        double result = (Double.parseDouble(firstInput.getText().toString()) / Double.parseDouble(conversions.getString(firstDropDown.getSelectedItem().toString()))) * Double.parseDouble(conversions.getString(secondDropDown.getSelectedItem().toString()));

                        BigDecimal ans = BigDecimal.valueOf(result);
                        ans = ans.setScale(2, BigDecimal.ROUND_HALF_UP);

                        int pos = secondInput.getSelectionEnd();

                        if (result == Math.floor(result))
                        {
                            ans = ans.setScale(0, BigDecimal.ROUND_HALF_UP);
                        }

                        secondInput.setText(ans.toString());
                        secondInput.setSelection(pos);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                skipOnChange = false;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int count, int after) { }
        });

        secondInput.addTextChangedListener(new TextWatcher()
        {
            boolean skipOnChange = false;

            public void afterTextChanged(Editable s)
            {
                if (skipOnChange)
                    return;

                skipOnChange = true;

                if (secondInput.getText().toString().matches("\\d+(\\.\\d+)?"))
                {
                    try
                    {
                        double result = (Double.parseDouble(secondInput.getText().toString()) / Double.parseDouble(conversions.getString(secondDropDown.getSelectedItem().toString()))) * Double.parseDouble(conversions.getString(firstDropDown.getSelectedItem().toString()));

                        BigDecimal ans = BigDecimal.valueOf(result);
                        ans = ans.setScale(2, BigDecimal.ROUND_HALF_UP);

                        int pos = firstInput.getSelectionEnd();

                        if (result == Math.floor(result))
                        {
                            ans = ans.setScale(0, BigDecimal.ROUND_HALF_UP);
                        }

                        firstInput.setText(ans.toString());
                        firstInput.setSelection(pos);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                skipOnChange = false;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int count, int after) { }
        });

        firstDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (secondInput.getText().toString().matches("\\d+(\\.\\d+)?"))
                {
                    try
                    {
                        double result = (Double.parseDouble(secondInput.getText().toString()) / Double.parseDouble(conversions.getString(secondDropDown.getSelectedItem().toString()))) * Double.parseDouble(conversions.getString(firstDropDown.getSelectedItem().toString()));

                        BigDecimal ans = new BigDecimal(String.valueOf(result));
                        ans = ans.setScale(2, BigDecimal.ROUND_HALF_UP);

                        int pos = firstInput.getSelectionEnd();

                        if (result == Math.floor(result))
                        {
                            ans = ans.setScale(0, BigDecimal.ROUND_HALF_UP);
                        }

                        firstInput.setText(ans.toString());
                        firstInput.setSelection(pos);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        secondDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (firstInput.getText().toString().matches("\\d+(\\.\\d+)?"))
                {
                    try
                    {
                        double result = (Double.parseDouble(firstInput.getText().toString()) / Double.parseDouble(conversions.getString(firstDropDown.getSelectedItem().toString()))) * Double.parseDouble(conversions.getString(secondDropDown.getSelectedItem().toString()));

                        BigDecimal ans = new BigDecimal(String.valueOf(result));
                        ans = ans.setScale(2, BigDecimal.ROUND_HALF_UP);

                        int pos = secondInput.getSelectionEnd();

                        if (result == Math.floor(result))
                        {
                            ans = ans.setScale(0, BigDecimal.ROUND_HALF_UP);
                        }

                        secondInput.setText(ans.toString());
                        secondInput.setSelection(pos);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        ImageView imgView = root.findViewById(R.id.imageView);

        imgView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                firstInput.setText("");
                secondInput.setText("");

                int tempDropDown = firstDropDown.getSelectedItemPosition();
                firstDropDown.setSelection(secondDropDown.getSelectedItemPosition());
                secondDropDown.setSelection(tempDropDown);
            }
        });

        return root;
    }
}