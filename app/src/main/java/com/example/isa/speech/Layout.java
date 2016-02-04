package com.example.isa.speech;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by isa on 2016/2/2.
 */
public class Layout extends LinearLayout {
    public EditText editText;
    public Button button;
    public Button button2;

    public Layout(Context context) {
        super(context);
        setOrientation(VERTICAL);

        editText = editText();
        button = button();
        button2 = button2();

        addView(editText);
        addView(button);
        addView(button2);

    }

    private EditText editText() {
        LinearLayout.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        EditText v = new EditText(getContext());
        v.setText("123");
        v.setLayoutParams(params);
        return v;
    }

    private Button button() {
        LinearLayout.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        Button v = new Button(getContext());
        v.setText("Button");
        v.setLayoutParams(params);
        return v;

    }

    private Button button2() {
        LinearLayout.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        Button v = new Button(getContext());
        v.setText("Button");
        v.setLayoutParams(params);
        return v;
    }
}
