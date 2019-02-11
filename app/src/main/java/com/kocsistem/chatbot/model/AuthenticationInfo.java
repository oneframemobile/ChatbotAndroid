package com.kocsistem.chatbot.model;

import android.support.annotation.NonNull;

public final class AuthenticationInfo {

  private String token;

  public AuthenticationInfo(@NonNull String token) {
    this.token = token;
  }

  public String bearer() {
    return "Bearer " + token;
  }
}
