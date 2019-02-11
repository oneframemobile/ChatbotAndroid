package com.kocsistem.chatbot.model;

import android.support.annotation.NonNull;

public final class MessageInfo {

  private String type;
  private String text;
  private FromInfo from;

  public MessageInfo(@NonNull String text) {
    this.type = "message";
    this.text = text;
    this.from = FromInfo.you();
  }
}
