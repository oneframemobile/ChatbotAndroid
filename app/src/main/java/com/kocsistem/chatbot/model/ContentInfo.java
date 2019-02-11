package com.kocsistem.chatbot.model;

import java.util.ArrayList;

public final class ContentInfo {

  private String text;
  private String title;
  private ArrayList<ButtonInfo> buttons;

  public String getText() {
    return text;
  }

  public String getTitle() {
    return title;
  }

  public ArrayList<ButtonInfo> getButtons() {
    return buttons;
  }
}
