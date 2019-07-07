package com.vggbudge.educhat.utils;

import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Patterns;

import java.util.Locale;

public class TextUtils {

    private TextUtils() {
    }

    public static String asteriskPhoneNumber(String phoneNumber, int startIndex, int endIndex) {
        String firstPart = phoneNumber.substring(0, startIndex);
        String endPart = phoneNumber.substring(endIndex);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(firstPart);

        for (int i = 0; i < (endIndex - startIndex); i++) {
            stringBuilder.append("*");
        }
        stringBuilder.append(endPart);
        return stringBuilder.toString();
    }

    public static String addNairaToText(String text) {
        return ("₦" + text);
    }

    public static String formatTextToNaira(String text) {
        if (isEmpty(text)) {
            return "";
        }
        double amount;
        try {
            amount = Double.valueOf(text);
        } catch (NumberFormatException e) {
            return text;
        }
        return addNairaToText(String.format(Locale.getDefault(), "%,.2f", amount));
    }

    public static String formatTextToNaira(Double amount) {
        return addNairaToText(String.format(Locale.getDefault(), "%,.2f", amount));
    }

    public static String formatMoneyToText(String text) {
        double amount;
        try {
            amount = Double.valueOf(text);
        } catch (NumberFormatException e) {
            return text;
        }
        return formatMoneyToText(amount);
    }

    public static String formatMoneyToText(double amount) {
        final int MILLION = 1000000;
        final int THOUSAND = 1000;

        if (amount >= 1000000)
            return String.format(Locale.getDefault(), "₦%dM", (int) amount / MILLION);
        else if (amount >= 1000)
            return String.format(Locale.getDefault(), "₦%dK", (int) amount / THOUSAND);
        else
            return String.format(Locale.getDefault(), "₦%d", (int) amount);
    }

    public static Spanned getHtmlFormattedDescription(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(text);
        }
    }

    public static Spannable prepareMultiColorSpannable(String fullText, int textColor, String... textsToSpan) {
        Spannable spannable = new SpannableString(fullText);
        for (String textToSpan : textsToSpan) {
            int startOfTextToSpan = fullText.indexOf(textToSpan);
            int endOfOfTextToSpan = fullText.indexOf(textToSpan) + textToSpan.length();
            spannable.setSpan(new ForegroundColorSpan(textColor), startOfTextToSpan, endOfOfTextToSpan,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startOfTextToSpan, endOfOfTextToSpan,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    public static String getInitials(String firstName, String lastName) {
        String initials = "";
        if (!isEmpty(firstName)) {
            initials = initials.concat(String.valueOf(firstName.charAt(0)));
        }
        if (!isEmpty(lastName)) {
            initials = initials.concat(String.valueOf(lastName.charAt(0)));
        }
        return initials;
    }

    public static String getOrdinal(int number) {
        String[] suffixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch (number % 100) {
            case 11:
            case 12:
            case 13:
                return number + "th";
            default:
                return number + suffixes[number % 10];
        }
    }

    public static String appendYear(int yearNumber, String yearString) {
        if (yearNumber == 1) {
            return String.format("%d %s", yearNumber, yearString);
        }
        return String.format("%d %s", yearNumber, yearString.concat("s"));
    }

    public static String getStringBeforeCharacter(String word, char character) {
        int charIndex = word.indexOf(character);
        if (charIndex > -1) {
            return word.substring(0, charIndex);
        }
        return word;
    }

    public static String getInitials(String value) {
        String[] split = value.split(" ");
        return split[0].charAt(0) + "" + split[1].charAt(0);
    }

    public static String getValueInTitleCase(String value) {
        String[] valueArr = value.split(" ");
        StringBuilder valueInTitleCase = new StringBuilder();
        for (String word : valueArr) {
            valueInTitleCase.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
            valueInTitleCase.append(" ");
        }
        return valueInTitleCase.toString();
    }

    public static boolean validateEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target.toString()) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isEmpty(String value) {
        return ((value == null) || value.length() == 0);
    }
}
