package io.agora.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.agora.model.ItemChatProfileModel;
import io.agora.rtmtutorial.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private List<ItemChatProfileModel> listdata;



    public ImageView imageView;
    public TextView textView;
    public RelativeLayout relativeLayout;
    public OnCardInfoListener onCardInfoListener;
    // RecyclerView recyclerView;
    public UserAdapter(List<ItemChatProfileModel> listdata, Context context) {
        this.listdata = listdata;

        try{
            this.onCardInfoListener = ((OnCardInfoListener)context);
        }
        catch (ClassCastException e){
            throw new ClassCastException(e.getMessage());
        }
    }

    public interface OnViewHolderClickedListener {
        void onViewHolderClicked();
    }

    private static OnViewHolderClickedListener mViewHolderClickListener;

    public static void setOnViewHolderClickedListener(OnViewHolderClickedListener l) {
        mViewHolderClickListener = l;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ItemChatProfileModel myListData = listdata.get(position);
        holder.textView.setText(myListData.getProfileName());
        holder.profileMessage.setText(myListData.getProfileMessage());
        holder.lastMessageTime.setText(myListData.getLastMessageTime());

        if (myListData.isLastMessageSeen()) {
            //myListData.getProfileMessage().setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.messageSeenColor));
            holder.unseenCount.setVisibility(View.INVISIBLE);
            holder.unseenCount.setText(myListData.getUnseenMessageCount());
        } else {
            //message.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.messageUnSeenColor));
            holder.unseenCount.setText(myListData.getUnseenMessageCount());
            holder.unseenCount.setVisibility(View.VISIBLE);
        }
        //holder.imageView.setImageResource(listdata.get(position).getProfileImage());
        holder.LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("targetname", myListData.getProfileName());
                onCardInfoListener.OnCardInfoListener(intent);
//                Toast.makeText(view.getContext(),"click on item: "+myListData.getProfileName(),Toast.LENGTH_LONG).show();
//                if (mViewHolderClickListener != null) {
//                    mViewHolderClickListener.onViewHolderClicked();
//                }
//                Intent intent = new Intent("custom-message");
                //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
//                intent.putExtra("profilename",myListData.getProfileName());
//                View.OnClickListener context = this;
//                LocalBroadcastManager.getInstance((Context) context).sendBroadcast(intent);
            }
        });
    }

    public interface OnCardInfoListener{
        public void OnCardInfoListener(Intent intent);
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public TextView profileMessage, lastMessageTime, unseenCount;
        public ImageView imageView;
        public View LinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.profileMessage = (TextView) itemView.findViewById(R.id.profileMessage);
            this.lastMessageTime = itemView.findViewById(R.id.profileLastMessageTime);
            this.unseenCount = itemView.findViewById(R.id.profileUnseenMessageCount);
            LinearLayout = (LinearLayout)itemView.findViewById(R.id.rootLayout);
        }
    }
}
