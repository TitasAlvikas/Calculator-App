package com.example.calculatorapp.ui.simple;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SimpleViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SimpleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is simple fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}