package com.example.calculatorapp.ui.fuel_cost;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FuelCostViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FuelCostViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is fuel cost fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}