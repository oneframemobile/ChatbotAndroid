package com.kocsistem.chatbot.model;

import java.util.ArrayList;

public final class ActivityInfo {

  private String type;
  private String id;
  private String timestamp;
  private String channelId;
  private FromInfo from;
  private ConversationInfo conversation;
  private String text;
  private String replyToId;
  private ArrayList<AttachmentInfo> attachments;

  public String getType() {
    return type;
  }

  public String getId() {
    return id;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getChannelId() {
    return channelId;
  }

  public FromInfo getFrom() {
    return from;
  }

  public ConversationInfo getConversation() {
    return conversation;
  }

  public String getText() {
    return text;
  }

  public String getReplyToId() {
    return replyToId;
  }

  public ArrayList<AttachmentInfo> getAttachments() {
    return attachments;
  }
}
