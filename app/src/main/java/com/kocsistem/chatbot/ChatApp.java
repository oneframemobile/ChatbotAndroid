package com.kocsistem.chatbot;

import android.app.Application;

import com.oneframe.android.networking.NetworkingFactory;

public class ChatApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    NetworkingFactory.init(this);
  }
}
