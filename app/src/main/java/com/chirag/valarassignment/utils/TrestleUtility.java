package com.chirag.valarassignment.utils;

import android.graphics.Typeface;

import com.etiennelawlor.trestle.library.Span;
import com.etiennelawlor.trestle.library.Trestle;

public class TrestleUtility {

    public static CharSequence getFormattedText(String text, Typeface font) {
        return Trestle.getFormattedText(
                new Span.Builder(text)
                        .typeface(font)
                        .build());
    }

    public static CharSequence getFormattedText(String text, Typeface font, int size) {
        return Trestle.getFormattedText(
                new Span.Builder(text)
                        .typeface(font)
                        .absoluteSize(size)
                        .build());
    }
}
