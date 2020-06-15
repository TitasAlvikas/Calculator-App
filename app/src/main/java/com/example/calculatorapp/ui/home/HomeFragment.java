package com.example.calculatorapp.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.calculatorapp.R;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeFragment extends Fragment
{
    private HomeViewModel homeViewModel;

    View root;

    EditText calculatorScreen;

    private boolean isOpPressed = false;

    private boolean isExpression = false;

    private char currentOp;

    private boolean isDot = false;

    private double firstNumber = 0;

    private int secondNumberIndex = 0;

    Button equals;
    Button add;
    Button subtract;
    Button multiply;
    Button divide;
    Button dot;
    Button n00;
    Button n0;
    Button n1;
    Button n2;
    Button n3;
    Button n4;
    Button n5;
    Button n6;
    Button n7;
    Button n8;
    Button n9;
    Button squared;
    Button powerY;
    Button rootX;
    Button yRootX;
    Button overX;
    ImageButton backspace;
    Button open;
    Button close;
    Button percent;
    Button pi;
    Button clear;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        calculatorScreen = root.findViewById(R.id.editText);
        equals = root.findViewById(R.id.btnEquals);
        add = root.findViewById(R.id.btnPlus);
        subtract = root.findViewById(R.id.btnMinus);
        multiply = root.findViewById(R.id.btnMultiply);
        divide = root.findViewById(R.id.btnDivide);
        dot = root.findViewById(R.id.btnDot);
        n00 = root.findViewById(R.id.btnDoubleZero);
        n0 = root.findViewById(R.id.btnZero);
        n1 = root.findViewById(R.id.btnOne);
        n2 = root.findViewById(R.id.btnTwo);
        n3 = root.findViewById(R.id.btnThree);
        n4 = root.findViewById(R.id.btnFour);
        n5 = root.findViewById(R.id.btnFive);
        n6 = root.findViewById(R.id.btnSix);
        n7 = root.findViewById(R.id.btnSeven);
        n8 = root.findViewById(R.id.btnEight);
        n9 = root.findViewById(R.id.btnNine);
        squared = root.findViewById(R.id.btnSquared);
        powerY = root.findViewById(R.id.btnPowerY);
        rootX = root.findViewById(R.id.btnRoot);
        yRootX = root.findViewById(R.id.btnYRootX);
        overX = root.findViewById(R.id.btn1OverX);
        backspace = root.findViewById(R.id.btnBackSpace);
        open = root.findViewById(R.id.btnOpenBracket);
        close = root.findViewById(R.id.btnCloseBracket);
        percent = root.findViewById(R.id.btnPercent);
        pi = root.findViewById(R.id.btnPi);
        clear = root.findViewById(R.id.btnClear);


        squared.setText(Html.fromHtml("x<sup>2</sup>"));
        powerY.setText(Html.fromHtml("x<sup>y</sup>"));
        pi.setText(Html.fromHtml("&#960;"));
        overX.setText(Html.fromHtml("<sup>1</sup>&frasl;<sub>x</sub>"));

        calculatorScreen.requestFocus();

        final View.OnClickListener clickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final int id = v.getId();

                String content = calculatorScreen.getText().toString();

                double num = 0;
                double result = 0;
                String resultString = "";
                Matcher m = Pattern.compile("[A-Za-z!£$%&*_{}\\[\\]@~#:;?/,<>|¬]+").matcher(calculatorScreen.getText().toString());

                switch (id)
                {
                    case R.id.btnEquals:
                        if (!calculatorScreen.getText().toString().equals(""))
                        {
                            if(isExpression)
                            {
                                Parser p = new Parser();
                                resultString = String.valueOf(p.eval(calculatorScreen.getText().toString()));

                                if (resultString.endsWith(".0"))
                                {
                                    resultString = resultString.substring(0, resultString.length() - 2);
                                }

                                if (!resultString.equals("Error"))
                                {
                                    BigDecimal ans = new BigDecimal(resultString);

                                    ans = ans.round(new MathContext(7, RoundingMode.HALF_UP));


                                    calculatorScreen.setText(ans.toString());
                                }
                                else
                                {
                                    calculatorScreen.setText("Error");
                                }
                            }
                            else if (isOpPressed)
                            {
                                char lastCharacter = content.charAt(content.length() - 1);
                                if (lastCharacter == '+' | lastCharacter == '-' | lastCharacter == 'x' | lastCharacter == '÷')
                                {
                                    return;
                                }

                                isOpPressed = false;

                                double secondNumber = Double.parseDouble(calculatorScreen.getText().subSequence(secondNumberIndex, calculatorScreen.getText().length()).toString());

                                switch (currentOp)
                                {
                                    case '+':
                                        result = firstNumber + secondNumber;
                                        break;
                                    case '-':
                                        result = firstNumber - secondNumber;
                                        break;
                                    case 'x':
                                        result = firstNumber * secondNumber;
                                        break;
                                    case '÷':
                                        result = firstNumber / secondNumber;
                                        break;
                                    case '^':
                                        result = Math.pow(firstNumber, secondNumber);
                                        break;
                                    case '√':
                                        result = Math.pow(secondNumber, 1.0 / firstNumber);
                                        break;
                                }


                                resultString = String.valueOf(result);

                                if (resultString.endsWith(".0"))
                                {
                                    resultString = resultString.substring(0, resultString.length() - 2);
                                }


                                BigDecimal ans = new BigDecimal(resultString);

                                ans = ans.round(new MathContext(7, RoundingMode.HALF_UP));


                                calculatorScreen.setText(ans.toString());
                            }
                        }
                        calculatorScreen.setSelection(calculatorScreen.getText().toString().length());
                        isDot = false;

                        break;
                    case R.id.btnPlus:
                        opPressed('+');
                        break;
                    case R.id.btnMinus:
                        opPressed('-');
                        break;
                    case R.id.btnMultiply:
                        opPressed('x');
                        break;
                    case R.id.btnDivide:
                        opPressed('÷');
                        break;
                    case R.id.btnDot:
                        if (!isDot)
                        {
                            appendSymbol(new char[]{'.'}, calculatorScreen.getSelectionStart());
                            isDot = true;
                        }
                        break;
                    case R.id.btnDoubleZero:
                        appendSymbol(new char[]{'0','0'}, calculatorScreen.getSelectionStart());
                        calculatorScreen.setSelection(calculatorScreen.getText().length());
                        break;
                    case R.id.btnZero:
                        appendSymbol(new char[]{'0'}, calculatorScreen.getSelectionStart());
                        break;
                    case R.id.btnOne:
                        appendSymbol(new char[]{'1'}, calculatorScreen.getSelectionStart());
                        break;
                    case R.id.btnTwo:
                        appendSymbol(new char[]{'2'}, calculatorScreen.getSelectionStart());
                        break;
                    case R.id.btnThree:
                        appendSymbol(new char[]{'3'}, calculatorScreen.getSelectionStart());
                        break;
                    case R.id.btnFour:
                        appendSymbol(new char[]{'4'}, calculatorScreen.getSelectionStart());
                        break;
                    case R.id.btnFive:
                        appendSymbol(new char[]{'5'}, calculatorScreen.getSelectionStart());
                        break;
                    case R.id.btnSix:
                        appendSymbol(new char[]{'6'}, calculatorScreen.getSelectionStart());
                        break;
                    case R.id.btnSeven:
                        appendSymbol(new char[]{'7'}, calculatorScreen.getSelectionStart());
                        break;
                    case R.id.btnEight:
                        appendSymbol(new char[]{'8'}, calculatorScreen.getSelectionStart());
                        break;
                    case R.id.btnNine:
                        appendSymbol(new char[]{'9'}, calculatorScreen.getSelectionStart());
                        break;
                    case R.id.btnSquared:
                        if (!calculatorScreen.getText().toString().equals("") && !m.matches())
                        {
                            num = Double.parseDouble(calculatorScreen.getText().toString());
                            result = num * num;
                            resultString = String.valueOf(result);

                            if (resultString.endsWith(".0"))
                            {
                                resultString = resultString.substring(0, resultString.length() - 2);
                            }

                            BigDecimal ans = new BigDecimal(resultString);

                            ans = ans.round(new MathContext(7, RoundingMode.HALF_UP));


                            calculatorScreen.setText(ans.toString());
                            calculatorScreen.setSelection(calculatorScreen.getText().length());
                        }
                        break;
                    case R.id.btnPowerY:
                        opPressed('^');
                        break;
                    case R.id.btnRoot:
                        if (!calculatorScreen.getText().toString().equals("") && !m.matches())
                        {
                            num = Double.parseDouble(calculatorScreen.getText().toString());
                            result = Math.sqrt(num);
                            resultString = String.valueOf(result);

                            if (resultString.endsWith(".0"))
                            {
                                resultString = resultString.substring(0, resultString.length() - 2);
                            }

                            BigDecimal ans = new BigDecimal(resultString);

                            ans = ans.round(new MathContext(7, RoundingMode.HALF_UP));


                            calculatorScreen.setText(ans.toString());
                            calculatorScreen.setSelection(calculatorScreen.getText().length());
                        }

                        break;
                    case R.id.btnYRootX:
                        opPressed('√');
                        break;
                    case R.id.btn1OverX:
                        if (!calculatorScreen.getText().toString().equals("") && !m.matches())
                        {
                            resultString = String.valueOf(1.0 / Double.parseDouble(calculatorScreen.getText().toString()));

                            if (resultString.endsWith(".0"))
                            {
                                resultString = resultString.substring(0, resultString.length() - 2);
                            }

                            BigDecimal ans = new BigDecimal(resultString);

                            ans = ans.round(new MathContext(7, RoundingMode.HALF_UP));


                            calculatorScreen.setText(ans.toString());
                            calculatorScreen.setSelection(calculatorScreen.getText().length());
                        }
                        break;
                    case R.id.btnBackSpace:
                        int length = calculatorScreen.getText().length();

                        if (length != 0)
                        {
                            int i = calculatorScreen.getSelectionStart();

                            if (i != 0)
                            {
                                calculatorScreen.setText(calculatorScreen.getText().toString().substring(0, i - 1) + calculatorScreen.getText().toString().substring(i));
                                calculatorScreen.setSelection(i - 1);
                            }
                        }
                        break;
                    case R.id.btnOpenBracket:
                        appendSymbol(new char[]{'('}, calculatorScreen.getSelectionStart());
                        isExpression = true;
                        break;
                    case R.id.btnCloseBracket:
                        appendSymbol(new char[]{')'}, calculatorScreen.getSelectionStart());
                        isExpression = true;
                        break;
                    case R.id.btnPercent:
                        if (!calculatorScreen.getText().toString().equals("") && !m.matches())
                        {
                            resultString = String.valueOf(Double.parseDouble(calculatorScreen.getText().toString()) / 100.0);

                            if (resultString.endsWith(".0"))
                            {
                                resultString = resultString.substring(0, resultString.length() - 2);
                            }

                            BigDecimal ans = new BigDecimal(resultString);

                            ans = ans.round(new MathContext(7, RoundingMode.HALF_UP));


                            calculatorScreen.setText(ans.toString());
                            calculatorScreen.setSelection(calculatorScreen.getText().length());
                        }
                        break;
                    case R.id.btnPi:
                        appendSymbol(new char[]{'3','.','1','4','1','5','9','2','6','5','3','5','8','9','7'}, calculatorScreen.getSelectionStart());
                        break;
                    case R.id.btnClear:
                        calculatorScreen.setText("");
                        isDot = false;
                        break;
                }
            }
        };

        equals.setOnClickListener(clickListener);
        add.setOnClickListener(clickListener);
        subtract.setOnClickListener(clickListener);
        multiply.setOnClickListener(clickListener);
        divide.setOnClickListener(clickListener);
        dot.setOnClickListener(clickListener);
        n00.setOnClickListener(clickListener);
        n0.setOnClickListener(clickListener);
        n1.setOnClickListener(clickListener);
        n2.setOnClickListener(clickListener);
        n3.setOnClickListener(clickListener);
        n4.setOnClickListener(clickListener);
        n5.setOnClickListener(clickListener);
        n6.setOnClickListener(clickListener);
        n7.setOnClickListener(clickListener);
        n8.setOnClickListener(clickListener);
        n9.setOnClickListener(clickListener);
        squared.setOnClickListener(clickListener);
        powerY.setOnClickListener(clickListener);
        rootX.setOnClickListener(clickListener);
        yRootX.setOnClickListener(clickListener);
        overX.setOnClickListener(clickListener);
        backspace.setOnClickListener(clickListener);
        open.setOnClickListener(clickListener);
        close.setOnClickListener(clickListener);
        percent.setOnClickListener(clickListener);
        pi.setOnClickListener(clickListener);
        clear.setOnClickListener(clickListener);

        return root;
    }

    private void opPressed(char operation)
    {
        String content = calculatorScreen.getText().toString();

        if (!content.contains("(") && !content.contains(")"))
        {
            if (content.length() != 0 && content.matches("-?\\d+(\\.\\d+)?")) {
                firstNumber = Double.parseDouble(content);
                appendSymbol(new char[]{operation}, calculatorScreen.getSelectionStart());
                secondNumberIndex = content.length() + 1;
                currentOp = operation;
                isOpPressed = true;
            }
            isDot = false;
        }
        else
        {
            appendSymbol(new char[]{operation}, calculatorScreen.getSelectionStart());
        }
    }

    private void appendSymbol(char[] symbol, int cursor)
    {
        calculatorScreen.setText(calculatorScreen.getText().toString().substring(0, cursor) + String.valueOf(symbol) + calculatorScreen.getText().toString().substring(cursor));
        calculatorScreen.setSelection(cursor + 1);
    }
}