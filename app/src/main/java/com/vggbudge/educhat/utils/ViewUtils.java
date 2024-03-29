package com.vggbudge.educhat.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vggbudge.educhat.R;

public final class ViewUtils {

    private ViewUtils() {
    }

    public static void show(@NonNull View... views) {
        for (View view : views) {
            show(view);
        }
    }

    public static void hide(@NonNull View... views) {
        for (View view : views) {
            hide(view);
        }
    }

    public static void invis(@NonNull View... views) {
        for (View view : views) {
            invis(view);
        }
    }

    public static boolean isShowing(@NonNull View view) {
        return view != null && view.getVisibility() == View.VISIBLE;
    }

    public static void show(@NonNull View view) {
        view.setVisibility(View.VISIBLE);
    }

    public static void hide(@NonNull View view) {
        view.setVisibility(View.GONE);
    }

    public static void invis(@NonNull View view) {
        view.setVisibility(View.INVISIBLE);
    }

    public static int convertDpToPixels(int dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    public static void adjustGridViewSizeBasedOnData(GridView gridView, int size) {
        adjustAdapterViewSizeBasedOnData(gridView, size, 2);
    }

    public static void adjustAdapterViewSizeBasedOnData(AdapterView adapterView, int size, int noOfColumn) {
        int rowHeight = (size % noOfColumn == 0) ? size / noOfColumn : (size + 1) / noOfColumn;
        int height = ViewUtils.convertDpToPixels(rowHeight * 42);
        ViewGroup.LayoutParams layoutParams = adapterView.getLayoutParams();
        layoutParams.height = height;
        adapterView.setLayoutParams(layoutParams);
        adapterView.requestLayout();
    }

    public static boolean validateEditTexts(EditText... editTexts) {
        for (EditText newEditText : editTexts) {
            if (newEditText.getText().toString().trim().length() < 1) {
                newEditText.setError("This Field is required");
                return false;
            }
        }
        return true;
    }

    public static boolean validateRadioGroup(RadioGroup radioGroup, String message, Context context) {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateEditTextInput(String inputString, int minValue, int maxValue,
                                                int seekbarIncrement) {
        int inputValue;
        try {
            inputValue = Integer.valueOf(inputString);
        } catch (NumberFormatException e) {
            return false;
        }
        return inputValue <= maxValue && inputValue >= minValue && inputValue % seekbarIncrement == 0;
    }

    public static boolean validateSpinners(Context context, Spinner... spinners) {
        for (Spinner spinner : spinners) {
            if (spinner.getSelectedItemPosition() < 1) {
                if (spinner.getSelectedView() != null && spinner.getSelectedView() instanceof TextView) {
                    TextView textView = (TextView) spinner.getSelectedView();
//                    textView.setError(context.getString(R.string.field_required));
                }
                return false;
            }
        }
        return true;
    }

    public static boolean validateTextView(TextView textView, Context context) {
        if (TextUtils.isEmpty(textView.getText())) {
            textView.setError(context.getString(R.string.field_required));
            return false;
        }
        return true;
    }
}
