package com.example.membersmanagement.helpers;

import java.text.DecimalFormat;

public class CurrencyFormatter {
    public static String formatCurrency(double amount) {
        DecimalFormat df = new DecimalFormat("###,###,###,### đ");
        return df.format(amount);
    }
}
