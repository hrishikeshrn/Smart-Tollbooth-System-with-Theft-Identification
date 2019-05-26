package com.tollbooth.CommonClasses;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tollbooth.R;
import org.w3c.dom.Text;

public class Validation {

    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static Pattern pattern;
    private static Matcher matcher;

    public Validation() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    public static void Message(Context context, String msg) {
            Toast toast =Toast.makeText(context,msg,Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
    }
    public static boolean isEmpty(Context context, String value, String error) {

        if (value.toString().equals("")) {
            Toast toast =Toast.makeText(context,error,Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        else {
            return true;
        }
    }
    public static boolean ClearControl(MaterialEditText editText) {
        try {
            editText.setText("");
            return true;
        }catch (Exception ex){
            return false;
        }
    }
    public static boolean ClearControl(EditText editText) {
        try {
            editText.setText("");
            return true;
        }catch (Exception ex){
            return false;
        }
    }
    public static boolean isValidate(MaterialEditText editText, String error) {

        if (editText.getText().toString().equals("")) {
            editText.setError(error);
            editText.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }
    public static boolean isValidate(TextView editText, String error) {

        if (editText.getText().toString().equals("")) {
            editText.setError(error);
            editText.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isValidateConfirm(MaterialEditText editText, MaterialEditText editText1, String error) {
        if (editText.getText().toString().trim().equals(editText1.getText().toString())) {
            return true;
        }
        editText1.setError(error);
        editText1.requestFocus();
        return false;
    }

    public static boolean isNotEmpty(EditText editText, String error) {
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        editText.setError(error);
        editText.requestFocus();
        return false;
    }

    public static boolean isNotEmptyTextview(TextView textView, String error) {
        if (textView.getText().toString().trim().length() > 0) {
            return true;
        }
        textView.setError(error);
        textView.requestFocus();
        return false;
    }

    public static boolean isValidContact(MaterialEditText editText, String error) {
        if (editText.getText().length() == 10) {
            return true;
        }
        editText.setError(error);
        editText.requestFocus();
        return false;
    }

    public static boolean isPassword(MaterialEditText editText, String error) {
        if (editText.getText().length() >= 6 && editText.getText().length() <= 12) {
            return true;
        }
        editText.setError(error);
        editText.requestFocus();
        return false;
    }

    public static boolean isValidateEmailAddress(MaterialEditText editText, String error) {
        Matcher matcherObj = Pattern.compile(EMAIL_PATTERN).matcher(editText.getText());

        if (matcherObj.matches()) {
            return true;
        }
        editText.setError(error);
        editText.requestFocus();
        return false;
    }

    public static String setCapitalizeletter(String s)
    {
        return s.substring(0,1).toUpperCase()+s.substring(1,s.length()).toLowerCase();
    }

    public static String spliString(String s)
    {
        String a[] = s.split(" ");
        return a[0];
    }

    public static void openSnackBar(View view, String message, Context context)
    {
        Snackbar snak = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View snackBarView = snak.getView();
        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextSize(14.0f);
        snackBarView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        snak.show();
    }
}
