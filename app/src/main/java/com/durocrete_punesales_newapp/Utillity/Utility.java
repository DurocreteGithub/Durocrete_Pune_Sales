package com.durocrete_punesales_newapp.Utillity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;

import java.io.ByteArrayOutputStream;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by root on 4/5/17.
 */
public class Utility {

    public static Locale locale = Locale.US;
    private static DateFormatSymbols formatter;


    public static void showFragment(final Class<? extends Fragment> fragmentClass, final Activity activityClass,
                                    final int frameLayoutId) {

        try {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    FragmentActivity fragmentActivity = (FragmentActivity) activityClass;
                    FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(frameLayoutId, Fragment.instantiate(fragmentActivity, fragmentClass.getCanonicalName()));
                    fragmentTransaction.commit();
                }
            }).start();
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public static String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteFormat = stream.toByteArray();
            // get the base 64 string
            String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

            return imgString;
        }
        return null;
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            if (activity != null && activity.getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity
                        .getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity
                        .getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void errorDialog(String message, Activity activity) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }


    public static void selectDatePicker(Context context, int reduceYear, final CustomDatePickerListener customDatePickerListener) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dlg = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();

                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        customDatePickerListener.onDateSet(cal);

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dlg.setTitle("");
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.YEAR, -reduceYear);
        dlg.getDatePicker().setMaxDate(cal1.getTime().getTime());
        dlg.show();
    }

    public static String formatDateForDisplay(Date d, String format) {
        if (d == null) {
            return "";
        }
        return new SimpleDateFormat(format, getAppLocale()).format(d);
    }


    public static Locale getAppLocale() {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }

    public static void setAppLocale(Locale newLocale) {
        locale = newLocale;
        formatter = new DateFormatSymbols(locale);
    }



    public static String formatDateForDisplay(Context context, Date date) {
        if (date == null) {
            return "";
        }
        return android.text.format.DateFormat.getDateFormat(context).format(date);
    }


    public static void selectDatePicker1(Context context, int reduceYear, final CustomDatePickerListener customDatePickerListener) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dlg = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();

                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        customDatePickerListener.onDateSet(cal);

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dlg.setTitle("");
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.YEAR, -reduceYear);

    dlg.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);


        dlg.show();


    }



}
