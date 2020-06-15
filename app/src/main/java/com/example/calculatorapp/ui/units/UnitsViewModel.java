package com.example.calculatorapp.ui.units;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UnitsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UnitsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is units fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}