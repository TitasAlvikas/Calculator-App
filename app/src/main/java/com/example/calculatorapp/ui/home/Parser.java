package com.example.calculatorapp.ui.home;
//Parser code obtained from:
//  https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form

public class Parser
{
    static boolean isError = false;

    public static String eval(final String str)
    {
        return new Object()
        {
            int pos = -1, ch;

            void nextChar()
            {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat)
            {
                while (ch == ' ') nextChar();
                if (ch == charToEat)
                {
                    nextChar();
                    return true;
                }
                return false;
            }

            String parse()
            {
                isError = false;
                nextChar();
                double x = parseExpression();
                if (isError) return "Error";
                if (pos < str.length()) return "Error";
                return String.valueOf(x);
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression()
            {
                double x = parseTerm();
                for (;;)
                {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm()
            {
                double x = Double.parseDouble(parseFactor());
                for (;;)
                {
                    if      (eat('x')) x *= Double.parseDouble(parseFactor()); // multiplication
                    else if (eat('÷')) x /= Double.parseDouble(parseFactor()); // division
                    else return x;
                }
            }

            String parseFactor()
            {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return String.valueOf(-Double.parseDouble(parseFactor())); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('('))
                { // parentheses
                    x = parseExpression();
                    eat(')');
                }
                else if ((ch >= '0' && ch <= '9') || ch == '.')
                { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                }
                else
                {
                    isError = true;
                    return "0.0";
                }

                if (eat('^')) x = Math.pow(x, Double.parseDouble(parseFactor())); // exponentiation

                return String.valueOf(x);
            }
        }.parse();
    }
}
