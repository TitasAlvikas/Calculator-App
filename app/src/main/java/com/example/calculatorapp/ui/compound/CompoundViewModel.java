package com.example.calculatorapp.ui.compound;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CompoundViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CompoundViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is compound fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}