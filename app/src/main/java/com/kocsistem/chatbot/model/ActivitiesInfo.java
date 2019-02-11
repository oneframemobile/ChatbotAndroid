package com.kocsistem.chatbot.model;

import java.util.ArrayList;

public final class ActivitiesInfo {

  private ArrayList<ActivityInfo> activities;
  private String watermark;

  public ArrayList<ActivityInfo> getActivities() {
    return activities;
  }

  public String getWatermark() {
    return watermark;
  }
}
