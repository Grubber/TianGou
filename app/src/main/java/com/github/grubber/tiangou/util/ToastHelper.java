package com.github.grubber.tiangou.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by grubber on 2017/1/6.
 */
public class ToastHelper {
    private Context _context;

    public ToastHelper(Context context) {
        _context = context;
    }

    public void show(String message) {
        Toast.makeText(_context, message, Toast.LENGTH_SHORT).show();
    }
}
