package com.example.learnmoto;

import android.annotation.SuppressLint;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.EditText;

public class ShowPassword {
    @SuppressLint({"NotConstructor", "ClickableViewAccessibility"})
    public void ShowPassword(EditText editText){
        editText.setOnTouchListener((v, event) -> {
            final int DrawableRight = 2;
            if (event.getAction() == MotionEvent.ACTION_UP){
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()
                        [DrawableRight].getBounds().width())){
                    editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }else{
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            return false;
        });
    }
}
