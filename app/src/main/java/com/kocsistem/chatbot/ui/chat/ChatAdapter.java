package com.kocsistem.chatbot.ui.chat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kocsistem.chatbot.R;
import com.kocsistem.chatbot.model.ActivityInfo;
import com.kocsistem.chatbot.model.AttachmentInfo;
import com.kocsistem.chatbot.model.ButtonInfo;
import com.kocsistem.chatbot.model.ContentInfo;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Typeface.BOLD;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.AbstractChatHolder> {

  private final List<ActivityInfo> activities;
  private final OnUrlClickListener listener;

  ChatAdapter(@Nullable List<ActivityInfo> activities, OnUrlClickListener listener) {
    this.activities = activities == null ? new ArrayList<>() : activities;
    this.listener = listener;
  }

  @Override
  public int getItemViewType(int position) {
    ActivityInfo activity = activities.get(position);
    return TextUtils.equals("You", activity.getFrom().getId())
        ? RightChatHolder.TYPE
        : LeftChatHolder.TYPE;
  }

  @NonNull
  @Override
  public AbstractChatHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

    switch (viewType) {
      case LeftChatHolder.TYPE:
        return new LeftChatHolder(inflater.inflate(R.layout.cell_left_message, viewGroup, false));
      case RightChatHolder.TYPE:
        return new RightChatHolder(inflater.inflate(R.layout.cell_right_message, viewGroup, false));
      default:
        throw new IllegalArgumentException("Unknown view type");
    }
  }

  @Override
  public void onBindViewHolder(@NonNull AbstractChatHolder holder, int position) {
    ActivityInfo activity = activities.get(position);

    holder.from.setText(activity.getFrom().getName());

    ArrayList<AttachmentInfo> attachments = activity.getAttachments();
    if (attachments != null && !attachments.isEmpty()) {
      SpannableStringBuilder text = new SpannableStringBuilder();
      for (AttachmentInfo attachment : attachments) {
        ContentInfo content = attachment.getContent();

        SpannableString title = new SpannableString(content.getText());
        title.setSpan(new StyleSpan(BOLD), 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        text.append(title).append("\n\n");
        for (ButtonInfo button : content.getButtons()) {
          String action = button.getTitle();

          SpannableString click = new SpannableString(action);
          click.setSpan(
              new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                  if (listener != null) {
                    listener.onUrlClick(button.getValue());
                  }
                }
              },
              0,
              action.length(),
              Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
          text.append(click).append("\n");
        }
      }

      holder.content.setText(text);
    } else {
      holder.content.setText(activity.getText());
    }
  }

  @Override
  public int getItemCount() {
    return activities.size();
  }

  interface OnUrlClickListener {
    void onUrlClick(String url);
  }

  abstract class AbstractChatHolder extends RecyclerView.ViewHolder {

    private TextView from;
    private TextView content;

    AbstractChatHolder(@NonNull View itemView) {
      super(itemView);

      from = itemView.findViewById(R.id.from);
      content = itemView.findViewById(R.id.content);

      content.setMovementMethod(LinkMovementMethod.getInstance());
    }
  }

  class LeftChatHolder extends AbstractChatHolder {

    private static final int TYPE = 218;

    LeftChatHolder(@NonNull View itemView) {
      super(itemView);
    }
  }

  class RightChatHolder extends AbstractChatHolder {

    private static final int TYPE = 845;

    RightChatHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
