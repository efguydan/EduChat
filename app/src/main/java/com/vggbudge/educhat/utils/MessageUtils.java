package com.vggbudge.educhat.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.vggbudge.educhat.R;


public class MessageUtils {
    private MessageUtils() {
    }

    public static void showMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showMessageIndefinitely(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).show();
    }

    public static Snackbar showMessage(View view, String message, String action) {

        Context context = view.getContext();
        final Snackbar snackbar = Snackbar.make(view, message
                , Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        }).setActionTextColor(Color.WHITE);

        View snackbarView = snackbar.getView();
        TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(3);
        snackbarView.setBackgroundColor(
                context.getResources().getColor(R.color.colorPrimary));
        snackbar.show();
        return snackbar;
    }
}