package com.kocsistem.chatbot.model;

public final class TokenInfo {

  private String conversationId;
  private String token;
  private long expires_in;

  public String getConversationId() {
    return conversationId;
  }

  public String getToken() {
    return token;
  }

  public long getExpires_in() {
    return expires_in;
  }

  public String bearer() {
    return "Bearer " + token;
  }
}
