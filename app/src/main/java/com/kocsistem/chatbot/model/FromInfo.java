package com.kocsistem.chatbot.model;

public final class FromInfo {

  private String id;
  private String name;

  public static FromInfo you() {
    FromInfo from = new FromInfo();
    from.id = "You";
    from.name = "You";

    return from;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
