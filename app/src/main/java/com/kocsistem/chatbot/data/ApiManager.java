package com.kocsistem.chatbot.data;

import android.support.annotation.NonNull;

import com.kocsistem.chatbot.model.ActivitiesInfo;
import com.kocsistem.chatbot.model.AuthenticationInfo;
import com.kocsistem.chatbot.model.ConversationInfo;
import com.kocsistem.chatbot.model.ConversationsInfo;
import com.kocsistem.chatbot.model.MessageInfo;
import com.kocsistem.chatbot.model.TokenInfo;
import com.oneframe.android.networking.NetworkConfig;
import com.oneframe.android.networking.NetworkManager;
import com.oneframe.android.networking.NetworkingFactory;
import com.oneframe.android.networking.listener.NetworkResponseListener;

@SuppressWarnings("unchecked")
public final class ApiManager {

  private static final String ENDPOINT = "https://directline.botframework.com/v3/directline/";

  private NetworkManager mManager;
  private NetworkConfig mConfig;

  // FIXME Read URL from Gradle
  public ApiManager() {
    mManager = NetworkingFactory.create();
    mManager.setJsonKey("ResultData");

    mConfig = NetworkConfig.getInstance();
    mConfig.addHeader("Cache-Control", "no-cache").setURL(ENDPOINT);
    mConfig.enableLog(true);
  }

  /**
   * Creates token
   *
   * @param secretKey This model should be created with secret-key-1 from Manifest file
   * @param listener Callback for the result
   */
  public void generate(
      @NonNull String secretKey, @NonNull NetworkResponseListener<TokenInfo, String> listener) {
    mConfig.deleteAllHeaders();
    mConfig.addHeader("Authorization", new AuthenticationInfo(secretKey).bearer());

    mManager.post("tokens/generate", null, listener);
  }

  /**
   * Creates a conversation
   *
   * @param token Token to authenticate
   * @param listener Callback for result
   */
  public void conversations(
      @NonNull TokenInfo token,
      @NonNull NetworkResponseListener<ConversationsInfo, String> listener) {
    mConfig.deleteAllHeaders();
    mConfig.addHeader("Authorization", token.bearer());

    mManager.post("conversations", null, listener);
  }

  /**
   * This method sbould be called in periods. Its used for listening answers
   *
   * @param conversations Conversations to be listening
   * @param listener Callback for results
   */
  public void activities(
      @NonNull ConversationsInfo conversations,
      @NonNull NetworkResponseListener<ActivitiesInfo, String> listener) {
    mConfig.deleteAllHeaders();
    mConfig.addHeader("Authorization", conversations.bearer());

    mManager.get("conversations/" + conversations.getConversationId() + "/activities", listener);
  }

  /**
   * Sends message to given conversation info.
   *
   * @param conversations Conversation to send
   * @param message Message object to send
   * @param listener Callback for the result
   */
  public void send(
      @NonNull ConversationsInfo conversations,
      @NonNull MessageInfo message,
      @NonNull NetworkResponseListener<ConversationInfo, String> listener) {
    mConfig.deleteAllHeaders();
    mConfig.addHeader("Authorization", conversations.bearer());

    mManager.post(
        "conversations/" + conversations.getConversationId() + "/activities", message, listener);
  }
}
