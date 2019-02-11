package com.kocsistem.chatbot.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kocsistem.chatbot.R;
import com.kocsistem.chatbot.data.ApiManager;
import com.kocsistem.chatbot.model.ActivitiesInfo;
import com.kocsistem.chatbot.model.ConversationInfo;
import com.kocsistem.chatbot.model.ConversationsInfo;
import com.kocsistem.chatbot.model.MessageInfo;
import com.kocsistem.chatbot.model.TokenInfo;
import com.kocsistem.chatbot.ui.AbstractActivity;
import com.kocsistem.utils.KeyboardUtils;
import com.kocsistem.utils.ManifestUtils;
import com.oneframe.android.networking.listener.NetworkResponseListener;
import com.oneframe.android.networking.model.ErrorModel;
import com.oneframe.android.networking.model.ResultModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

public class ChatActivity extends AbstractActivity
    implements View.OnClickListener, ChatAdapter.OnUrlClickListener {

  private CompositeDisposable disposables = new CompositeDisposable();
  private ApiManager api = new ApiManager();

  private ConversationsInfo conversations;
  private ChatAdapter adapter;

  private RecyclerView recycler;
  private AppCompatEditText text;
  private AppCompatImageButton send;
  private SwipeRefreshLayout loading;

  public static void start(Context context) {
    Intent starter = new Intent(context, ChatActivity.class);
    context.startActivity(starter);
  }

  @Override
  protected int getLayoutResId() {
    return R.layout.layout_chat;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    String secret_key = ManifestUtils.getMetaData(this, "SECRET_KEY");
    if (TextUtils.isEmpty(secret_key)) {
      throw new AssertionError("Secret key is empty");
    }

    generate(secret_key);
    activities();
  }

  @Override
  public void onContentChanged() {
    super.onContentChanged();

    recycler = findViewById(R.id.recycler);
    send = findViewById(R.id.send);
    text = findViewById(R.id.text);
    loading = findViewById(R.id.loading);

    loading.setOnRefreshListener(() -> loading.setRefreshing(false));
    send.setOnClickListener(this);
    text.setHint("Write something here...");
  }

  public void generate(@NonNull String secret_key) {
    loading.setRefreshing(true);

    api.generate(
        secret_key,
        new NetworkResponseListener<TokenInfo, String>() {
          @Override
          public void onSuccess(ResultModel<TokenInfo> result) {
            loading.setRefreshing(false);

            TokenInfo token = new Gson().fromJson(result.getJson(), TokenInfo.class);
            conversations(token);
          }

          @Override
          public void onError(ErrorModel<String> error) {
            loading.setRefreshing(false);

            Toast.makeText(ChatActivity.this, error.getData(), Toast.LENGTH_SHORT).show();
          }
        });
  }

  public void conversations(@NonNull TokenInfo token) {
    loading.setRefreshing(true);

    api.conversations(
        token,
        new NetworkResponseListener<ConversationsInfo, String>() {
          @Override
          public void onSuccess(ResultModel<ConversationsInfo> result) {
            loading.setRefreshing(false);

            conversations = new Gson().fromJson(result.getJson(), ConversationsInfo.class);
          }

          @Override
          public void onError(ErrorModel<String> error) {
            loading.setRefreshing(false);

            Toast.makeText(ChatActivity.this, error.getData(), Toast.LENGTH_SHORT).show();
          }
        });
  }

  public void activities() {
    disposables.add(
        Flowable.interval(1, TimeUnit.SECONDS)
            .takeWhile(periods -> conversations != null)
            .subscribe(
                secs ->
                    api.activities(
                        conversations,
                        new NetworkResponseListener<ActivitiesInfo, String>() {
                          @Override
                          public void onSuccess(ResultModel<ActivitiesInfo> result) {
                            ActivitiesInfo activities =
                                new Gson().fromJson(result.getJson(), ActivitiesInfo.class);

                            new Handler(Looper.getMainLooper())
                                .post(
                                    () -> {
                                      adapter =
                                          new ChatAdapter(
                                              activities.getActivities(), ChatActivity.this);
                                      recycler.setAdapter(adapter);

                                      int last = adapter.getItemCount() - 1;
                                      recycler.scrollToPosition(last);
                                    });
                          }

                          @Override
                          public void onError(ErrorModel<String> error) {
                            Toast.makeText(ChatActivity.this, error.getData(), Toast.LENGTH_SHORT)
                                .show();
                          }
                        })));
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    disposables.dispose();
  }

  public void send(@NonNull MessageInfo message) {
    text.setText(null);
    text.setHint("...");

    loading.setRefreshing(true);

    api.send(
        conversations,
        message,
        new NetworkResponseListener<ConversationInfo, String>() {
          @Override
          public void onSuccess(ResultModel<ConversationInfo> result) {
            loading.setRefreshing(false);

            text.setHint("Write something here...");
          }

          @Override
          public void onError(ErrorModel<String> error) {
            loading.setRefreshing(false);

            text.setHint("Write something here...");
            Toast.makeText(ChatActivity.this, error.getData(), Toast.LENGTH_SHORT).show();
          }
        });
  }

  @Override
  public void onClick(View view) {
    Editable editable = this.text.getText();
    if (editable != null) {
      String content = editable.toString().trim();
      if (TextUtils.isEmpty(content)) {
        return;
      }

      KeyboardUtils.hide(this, view);

      MessageInfo message = new MessageInfo(content);
      send(message);
    }
  }

  @Override
  public void onUrlClick(String url) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse("http://www.google.com"));
    startActivity(intent);
  }
}
