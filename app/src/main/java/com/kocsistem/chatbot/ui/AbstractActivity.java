package com.kocsistem.chatbot.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class AbstractActivity extends AppCompatActivity {

  private boolean visible = true;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(getLayoutResId());
  }

  protected abstract @LayoutRes int getLayoutResId();

  @Override
  protected void onResume() {
    super.onResume();

    visible = true;
  }

  @Override
  protected void onPause() {
    super.onPause();

    visible = false;
  }

  protected boolean isVisible() {
    return visible;
  }
}
