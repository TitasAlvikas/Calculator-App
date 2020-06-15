package com.example.calculatorapp.ui.fuel_mileage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FuelMileageViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FuelMileageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is fuel mileage fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}