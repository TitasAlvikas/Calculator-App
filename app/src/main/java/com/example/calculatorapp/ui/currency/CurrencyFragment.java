package com.example.calculatorapp.ui.currency;

import android.app.AlertDialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.calculatorapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CurrencyFragment extends Fragment {

    private CurrencyViewModel currencyViewModel;

    private static final String FILE_NAME = "latest.json";

    private View root;

    private EditText firstInput;

    private EditText secondInput;

    private Spinner firstDropDown;

    private Spinner secondDropDown;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        currencyViewModel =
                ViewModelProviders.of(this).get(CurrencyViewModel.class);
        root = inflater.inflate(R.layout.fragment_currency, container, false);

        //Currency rates obtained from: https://api.exchangeratesapi.io/latest?base=GBP

        firstInput = root.findViewById(R.id.firstInput);

        secondInput = root.findViewById(R.id.secondInput);

        firstDropDown = root.findViewById(R.id.first);

        secondDropDown = root.findViewById(R.id.second);

        firstInput.requestFocus();


        InputStream inputStream = null;
        StringBuilder result = null;
        JSONObject input = null;
        final JSONObject rates;


        try
        {
            AssetManager assetManager = this.getContext().getAssets();
            inputStream = assetManager.open(FILE_NAME);

            result = new StringBuilder();

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = br.readLine()) != null)
            {
                result.append(line);
                result.append('\n');
            }
            br.close();

            input = new JSONObject(result.toString());
            rates = input.getJSONObject("rates");


            List<String> arraySpinner = new ArrayList<>();


            Iterator<String> iterator = rates.keys();

            while (iterator.hasNext())
            {
                arraySpinner.add(iterator.next());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(super.getContext(), android.R.layout.simple_spinner_item, arraySpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            firstDropDown.setAdapter(adapter);
            secondDropDown.setAdapter(adapter);


            firstDropDown.setSelection(arraySpinner.indexOf("GBP"));

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
                            double result = (Double.parseDouble(firstInput.getText().toString()) / Double.parseDouble(rates.getString(firstDropDown.getSelectedItem().toString()))) * Double.parseDouble(rates.getString(secondDropDown.getSelectedItem().toString()));

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
                            double result = (Double.parseDouble(secondInput.getText().toString()) / Double.parseDouble(rates.getString(secondDropDown.getSelectedItem().toString()))) * Double.parseDouble(rates.getString(firstDropDown.getSelectedItem().toString()));

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

            firstDropDown.setOnItemSelectedListener(new OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                {
                    if (secondInput.getText().toString().matches("\\d+(\\.\\d+)?"))
                    {
                        try
                        {
                            double result = (Double.parseDouble(secondInput.getText().toString()) / Double.parseDouble(rates.getString(secondDropDown.getSelectedItem().toString()))) * Double.parseDouble(rates.getString(firstDropDown.getSelectedItem().toString()));

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

            secondDropDown.setOnItemSelectedListener(new OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                {
                    if (firstInput.getText().toString().matches("\\d+(\\.\\d+)?"))
                    {
                        try
                        {
                            double result = (Double.parseDouble(firstInput.getText().toString()) / Double.parseDouble(rates.getString(firstDropDown.getSelectedItem().toString()))) * Double.parseDouble(rates.getString(secondDropDown.getSelectedItem().toString()));

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
        }
        catch (IOException e)
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getContext());
            dlgAlert.setMessage("Fatal error!");
            dlgAlert.setTitle("Error");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
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