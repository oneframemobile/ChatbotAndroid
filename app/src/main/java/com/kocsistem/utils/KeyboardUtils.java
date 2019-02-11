package com.kocsistem.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class KeyboardUtils {

  public static void hide(@NonNull final Activity activity, @Nullable final View view) {
    activity.runOnUiThread(
        new Runnable() {
          public void run() {
            if (view != null) {
              InputMethodManager input =
                  (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

              if (input != null) {
                input.hideSoftInputFromWindow(view.getWindowToken(), 0);
              }
            }
          }
        });
  }
}
