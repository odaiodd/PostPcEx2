package com.example.odai.selfChat;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.widget.TextView;


import java.util.List;

public class msgAdapter extends RecyclerView.Adapter<msgAdapter.MessageViewHolder> {

    final OnClickListener listen;
    final List<Message> data;

    public msgAdapter(OnClickListener listener, List<Message> input) {
        this.listen = listener;
        this.data = input;
    }

    public void addMessage(Message msg) {
        data.add(msg);
        notifyItemInserted(data.size() - 1);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, parent, false);
        MessageViewHolder holder = new MessageViewHolder(view);
        holder.setOnClickListener(listen);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message msg = data.get(position);
        Context var1 = holder.itemView.getContext();
        Long var2 = msg.getTimestamp();
        holder.Timestamp.setText(DateUtils.getRelativeTimeSpanString(var1, var2));
        holder.dataContent.setText(msg.getContent());
        holder.name.setText(msg.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeItem(Message msg) {
        for(int i = 0, size = data.size(); i < size; i++) {
            if (msg.equals(data.get(i))) {
                data.remove(i);
                notifyItemRemoved(i);
                return;
            }
        }
        throw new IllegalArgumentException("item is not in dataset");
    }


    class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView name , dataContent , Timestamp;
        OnClickListener listener;

        MessageViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            dataContent = itemView.findViewById(R.id.textViewMessageContent);
            Timestamp = itemView.findViewById(R.id.timestampText);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        listener.onClick(data.get(getAdapterPosition()));
                        return true;
                    }
                    return false;
                }
            });
        }

        void setOnClickListener(OnClickListener onClickListener) {
            this.listener = onClickListener;
        }
    }

    interface OnClickListener {
        void onClick(Message message);
    }
}

