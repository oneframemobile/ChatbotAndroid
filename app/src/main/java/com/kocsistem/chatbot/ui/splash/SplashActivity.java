package com.kocsistem.chatbot.ui.splash;

import android.support.v7.widget.AppCompatButton;

import com.kocsistem.chatbot.R;
import com.kocsistem.chatbot.ui.AbstractActivity;
import com.kocsistem.chatbot.ui.chat.ChatActivity;

public final class SplashActivity extends AbstractActivity {

  private AppCompatButton start;

  @Override
  protected int getLayoutResId() {
    return R.layout.layout_splash;
  }

  @Override
  public void onContentChanged() {
    super.onContentChanged();

    start = findViewById(R.id.start);
    start.setOnClickListener(v -> ChatActivity.start(SplashActivity.this));
  }

  @Override
  protected void onPause() {
    super.onPause();

    finish();
  }
}
