package io.agora.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import java.util.List;

import io.agora.model.MessageBean;
import io.agora.rtm.RtmImageMessage;
import io.agora.rtm.RtmMessage;
import io.agora.rtm.RtmMessageType;
import io.agora.rtmtutorial.R;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private List<MessageBean> messageBeanList;
    private LayoutInflater inflater;
    private OnItemClickListener listener;

    public MessageAdapter(Context context, List<MessageBean> messageBeanList, @NonNull OnItemClickListener listener) {
        this.inflater = ((Activity) context).getLayoutInflater();
        this.messageBeanList = messageBeanList;
        this.listener = listener;
    }

    //For right chat
    public interface OnViewHolderClickedListener {
        void onViewHolderClicked();
    }

    private static OnViewHolderClickedListener mViewHolderClickListener;

    public static void setOnViewHolderClickedListener(OnViewHolderClickedListener l) {
        mViewHolderClickListener = l;
    }

    //For left chat
    public interface OnViewHolderClickedListener2 {
        void onViewHolderClicked2();
    }

    private static OnViewHolderClickedListener2 mViewHolderClickListener2;

    public static void setOnViewHolderClickedListener2(OnViewHolderClickedListener2 l) {
        mViewHolderClickListener2 = l;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.msg_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        setupView(holder, position);
    }

    @Override
    public int getItemCount() {
        return messageBeanList.size();
    }

    private void setupView(MyViewHolder holder, int position) {
        MessageBean bean = messageBeanList.get(position);
        if (bean.isBeSelf()) {
            holder.textViewSelfName.setText(bean.getAccount());
        } else {
            holder.textViewOtherName.setText(bean.getAccount());
            if (bean.getBackground() != 0) {
                holder.textViewOtherName.setBackgroundResource(bean.getBackground());
            }
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(bean);
        });

        RtmMessage rtmMessage = bean.getMessage();
        switch (rtmMessage.getMessageType()) {
            case RtmMessageType.TEXT:
                if (bean.isBeSelf()) {
                    holder.textViewSelfMsg.setVisibility(View.VISIBLE);
                    holder.textViewSelfMsg.setText(rtmMessage.getText());
                } else {
                    holder.textViewOtherMsg.setVisibility(View.VISIBLE);
                    holder.textViewOtherMsg.setText(rtmMessage.getText());
                }

                holder.imageViewSelfImg.setVisibility(View.GONE);
                holder.imageViewOtherImg.setVisibility(View.GONE);
                break;
            case RtmMessageType.IMAGE:
                RtmImageMessage rtmImageMessage = (RtmImageMessage) rtmMessage;
                RequestBuilder<Drawable> builder = Glide.with(holder.itemView)
                        .load(rtmImageMessage.getThumbnail())
                        .override(rtmImageMessage.getThumbnailWidth(), rtmImageMessage.getThumbnailHeight());
                if (bean.isBeSelf()) {
                    holder.imageViewSelfImg.setVisibility(View.VISIBLE);
                    builder.into(holder.imageViewSelfImg);
                } else {
                    holder.imageViewOtherImg.setVisibility(View.VISIBLE);
                    builder.into(holder.imageViewOtherImg);
                }

                holder.textViewSelfMsg.setVisibility(View.GONE);
                holder.textViewOtherMsg.setVisibility(View.GONE);
                break;
        }

        holder.layoutRight.setVisibility(bean.isBeSelf() ? View.VISIBLE : View.GONE);
        holder.layoutLeft.setVisibility(bean.isBeSelf() ? View.GONE : View.VISIBLE);

        // For long click
        holder.textViewOtherMsg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                int p=getLayoutPosition();
                //((ChatActivity)context).updateData("yourvalue");
                if (mViewHolderClickListener != null) {
                    mViewHolderClickListener.onViewHolderClicked();
                }

//                System.out.println("RightLongClick: "+message);
                return true;// returning true instead of false, works for me
            }
        });
        // For long click
        holder.textViewSelfMsg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                int p=getLayoutPosition();
                //((ChatActivity)context).updateData("yourvalue");
                if (mViewHolderClickListener2 != null) {
                    mViewHolderClickListener2.onViewHolderClicked2();
                }

//                System.out.println("RightLongClick: "+message);
                return true;// returning true instead of false, works for me
            }
        });


    }

    public interface OnItemClickListener {
        void onItemClick(MessageBean message);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewOtherName;
        private TextView textViewOtherMsg;
        private ImageView imageViewOtherImg;
        private TextView textViewSelfName;
        private TextView textViewSelfMsg;
        private ImageView imageViewSelfImg;
        private RelativeLayout layoutLeft;
        private RelativeLayout layoutRight;

        MyViewHolder(View itemView) {
            super(itemView);

            textViewOtherName = itemView.findViewById(R.id.item_name_l);
            textViewOtherMsg = itemView.findViewById(R.id.item_msg_l);
            imageViewOtherImg = itemView.findViewById(R.id.item_img_l);
            textViewSelfName = itemView.findViewById(R.id.item_name_r);
            textViewSelfMsg = itemView.findViewById(R.id.item_msg_r);
            imageViewSelfImg = itemView.findViewById(R.id.item_img_r);
            layoutLeft = itemView.findViewById(R.id.item_layout_l);
            layoutRight = itemView.findViewById(R.id.item_layout_r);
        }
    }
}
