package com.kocsistem.chatbot.model;

public final class ConversationsInfo {

  private String conversationId;
  private String token;
  private long expires_in;
  private String streamUrl;
  private String referenceGrammarId;

  public String getConversationId() {
    return conversationId;
  }

  public String getToken() {
    return token;
  }

  public long getExpires_in() {
    return expires_in;
  }

  public String getStreamUrl() {
    return streamUrl;
  }

  public String getReferenceGrammarId() {
    return referenceGrammarId;
  }

  public String bearer() {
    return "Bearer " + token;
  }
}
