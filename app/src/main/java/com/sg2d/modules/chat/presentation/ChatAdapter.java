package com.sg2d.modules.chat.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sg2d.modules.R;
import com.sg2d.modules.chat.entities.ChatEntry;

import java.util.ArrayList;

import static com.sg2d.app.utils.Constants.TYPE_FILE;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private Context mContext;
    ArrayList<ChatEntry> filteredMessageContents;
    ArrayList<ChatEntry> messageContents;
    int myId;
    onChatItemClickListener onChatItemClickListener;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filteredMessageContents = messageContents;
                } else {
                    ArrayList<ChatEntry> filteredList = new ArrayList<>();
                    for (ChatEntry row : messageContents) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getBody().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    filteredMessageContents = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredMessageContents;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredMessageContents = (ArrayList<ChatEntry>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class SendMsgViewHolder extends RecyclerView.ViewHolder {
        TextView chat_time;
        ImageView profilePhoto;
        TextView chat_message;
        ImageView status_indicator;

        public SendMsgViewHolder(View view) {
            super(view);
            chat_time = view.findViewById(R.id.chat_time);
            profilePhoto = view.findViewById(R.id.profile_photo);
            chat_message = view.findViewById(R.id.chat_message);
            status_indicator = view.findViewById(R.id.status_indicator);
        }

        void bind(int position) {
            chat_time.setText(filteredMessageContents.get(position).getTimestamp());
            chat_message.setText(filteredMessageContents.get(position).getBody());
            if (filteredMessageContents.get(position).isError()) {
                status_indicator.setImageResource(R.drawable.ic_error);
                status_indicator.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onChatItemClickListener.onChatItemClick(filteredMessageContents.get(position));
                    }
                });
            } else {
                if (filteredMessageContents.get(position).isSynced()) {
                    status_indicator.setImageResource(R.drawable.ic_green_tick);
                } else {
                    status_indicator.setImageResource(R.drawable.ic_gray_tick);
                }

            }
        }
    }

    public class SendFileViewHolder extends RecyclerView.ViewHolder {
        TextView chat_time;
        ImageView profilePhoto;
        ImageView chat_file;
        ImageView status_indicator;

        public SendFileViewHolder(View view) {
            super(view);
            chat_time = view.findViewById(R.id.chat_time);
            profilePhoto = view.findViewById(R.id.profile_photo);
            chat_file = view.findViewById(R.id.chat_file);
            status_indicator = view.findViewById(R.id.status_indicator);
        }

        void bind(int position) {
            chat_time.setText(filteredMessageContents.get(position).getTimestamp());
            Glide.with(mContext)
                    .load(filteredMessageContents.get(position).getFileBody().getPath())
                    .centerCrop()
                    .placeholder(R.drawable.logo_sg2d)
                    .into(chat_file);
            if (filteredMessageContents.get(position).isError()) {
                status_indicator.setImageResource(R.drawable.ic_error);
                status_indicator.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onChatItemClickListener.onChatItemClick(filteredMessageContents.get(position));
                    }
                });
            } else {
                if (filteredMessageContents.get(position).isSynced()) {
                    status_indicator.setImageResource(R.drawable.ic_green_tick);
                } else {
                    status_indicator.setImageResource(R.drawable.ic_gray_tick);
                }
            }
        }
    }

    public class RecieveFileViewHolder extends RecyclerView.ViewHolder {
        TextView chat_time;
        ImageView profilePhoto;
        ImageView chat_file;

        public RecieveFileViewHolder(View view) {
            super(view);
            chat_time = view.findViewById(R.id.chat_time);
            profilePhoto = view.findViewById(R.id.profile_photo);
            chat_file = view.findViewById(R.id.chat_file);
        }

        void bind(int position) {
            chat_time.setText(filteredMessageContents.get(position).getTimestamp());
            Glide.with(mContext)
                    .load(filteredMessageContents.get(position).getFileBody().getPath())
                    .centerCrop()
                    .placeholder(R.drawable.logo_sg2d)
                    .into(chat_file);
        }
    }

    public class RecieveMsgViewHolder extends RecyclerView.ViewHolder {
        TextView chat_time;
        ImageView profilePhoto;
        TextView chat_message;

        public RecieveMsgViewHolder(View view) {
            super(view);
            chat_time = view.findViewById(R.id.chat_time);
            profilePhoto = view.findViewById(R.id.profile_photo);
            chat_message = view.findViewById(R.id.chat_message);
        }

        void bind(int position) {
            chat_time.setText(filteredMessageContents.get(position).getTimestamp());
            chat_message.setText(filteredMessageContents.get(position).getBody());
        }
    }

    public ChatAdapter(Context mContext, int myId, ArrayList<ChatEntry> filteredMessageContents, onChatItemClickListener onChatItemClickListener) {
        this.mContext = mContext;
        this.filteredMessageContents = filteredMessageContents;
        this.messageContents = filteredMessageContents;
        this.onChatItemClickListener = onChatItemClickListener;
        this.myId = myId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 11) {
            return new SendFileViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_send_file, parent, false));

        } else if (viewType == 12) {
            return new SendMsgViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_send_message, parent, false));
        } else if (viewType == 21) {
            return new RecieveFileViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_receive_file, parent, false));
        }
        //22
        return new RecieveMsgViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_receive_message, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 11) {
            ((SendFileViewHolder) holder).bind(position);
        } else if (holder.getItemViewType() == 12) {
            ((SendMsgViewHolder) holder).bind(position);
        } else if (holder.getItemViewType() == 21) {
            ((RecieveFileViewHolder) holder).bind(position);
        } else {
            ((RecieveMsgViewHolder) holder).bind(position);
        }
    }


    @Override
    public int getItemCount() {
        return filteredMessageContents.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (filteredMessageContents.get(position).getUserid() == myId) {
            if (filteredMessageContents.get(position).getType().equalsIgnoreCase(TYPE_FILE)) {
                return 11;
            } else {
                return 12;
            }

        } else {
            if (filteredMessageContents.get(position).getType().equalsIgnoreCase(TYPE_FILE)) {
                return 21;
            } else {
                return 22;
            }
        }
    }

    public interface onChatItemClickListener {
        void onChatItemClick(ChatEntry chatEntry);
    }
}