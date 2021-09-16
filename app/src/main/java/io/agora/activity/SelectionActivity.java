package io.agora.activity;

import static android.view.View.GONE;
import static io.agora.adapter.UserAdapter.setOnViewHolderClickedListener;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.agora.adapter.MessageAdapter;
import io.agora.adapter.UserAdapter;
import io.agora.model.ItemChatProfileModel;
import io.agora.model.MessageBean;
import io.agora.model.MyListData;
import io.agora.rtm.RtmClient;
import io.agora.rtmtutorial.AGApplication;
import io.agora.rtmtutorial.ChatManager;
import io.agora.rtmtutorial.R;
import io.agora.utils.MessageUtil;

public class SelectionActivity extends Activity implements UserAdapter.OnCardInfoListener {
    private static final int CHAT_REQUEST_CODE = 1;

    private TextView mTitleTextView;
    private TextView mChatButton;
    private EditText mNameEditText;

    private boolean mIsPeerToPeerMode = true; // whether peer to peer mode or channel mode\
    private String mTargetName;
    private String mUserId;

    private ChatManager mChatManager;
    private RecyclerView uRecyclerView;
    private UserAdapter uUserAdapter;
    private List userList = new ArrayList<>();
    private MessageAdapter mMessageAdapter;
    private RtmClient mRtmClient;
    private ImageView mBigImage;
    private List<MessageBean> mMessageBeanList = new ArrayList<>();

    TextView chatTabButton, matchesTabButton;
    RecyclerView recyclerView;
    UserAdapter adapter;

    FloatingActionButton floatingActionButton;
    AppCompatCheckBox mOfflineMsgCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        mChatManager = AGApplication.the().getChatManager();
        mNameEditText = findViewById(R.id.selection_name);
        mChatButton = findViewById(R.id.selection_chat_btn);
        AppCompatCheckBox mOfflineMsgCheck = findViewById(R.id.offline_msg_check);
        mOfflineMsgCheck.setChecked(mChatManager.isOfflineMessageEnabled());
        mOfflineMsgCheck.setOnCheckedChangeListener((buttonView, isChecked) -> mChatManager.enableOfflineMessage(isChecked));

        mNameEditText.setVisibility(View.GONE);
        mChatButton.setVisibility(View.GONE);
        mOfflineMsgCheck.setVisibility(View.GONE);

//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
//                new IntentFilter("custom-message"));
//        MyListData[] myListData = new MyListData[] {};
        initUIAndData();
    }

//    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // Get extra data included in the Intent
//            String pName = intent.getStringExtra("profileName");
//            Toast.makeText(SelectionActivity.this,pName + "In select" ,Toast.LENGTH_SHORT).show();
//        }
//    };

    public void initUIAndData() {
        Intent intent = getIntent();
        mUserId = intent.getStringExtra(MessageUtil.INTENT_EXTRA_USER_ID);
        mTitleTextView = findViewById(R.id.selection_title);

        floatingActionButton = findViewById(R.id.add_user);
        RadioGroup modeGroup = findViewById(R.id.mode_radio_group);
        modeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.peer_radio_button:
                    mIsPeerToPeerMode = true;
                    mTitleTextView.setText(getString(R.string.title_peer_msg));
                    mChatButton.setText(getString(R.string.btn_chat));
                    mNameEditText.setHint(getString(R.string.hint_friend));
                    break;
                case R.id.selection_tab_channel:
                    mIsPeerToPeerMode = false;
                    mTitleTextView.setText(getString(R.string.title_channel_message));
                    mChatButton.setText(getString(R.string.btn_join));
                    mNameEditText.setHint(getString(R.string.hint_channel));
                    break;
            }
        });
        RadioButton peerMode = findViewById(R.id.peer_radio_button);
        peerMode.setChecked(true);




/*        mOfflineMsgCheck.setOnCheckedChangeListener((buttonView, isChecked) -> mChatManager.enableHistoricalMessaging(isChecked));*/

        // New User list
        chatTabButton = findViewById(R.id.chatsTabButton);
        matchesTabButton = findViewById(R.id.matchesTabButton);
        recyclerView = findViewById(R.id.chatListRecyclerView);

        final Typeface tf = ResourcesCompat.getFont(this, R.font.sourcesanspro_semibold);
        chatTabButton.setTypeface(tf);
        matchesTabButton.setTypeface(tf);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.ic_divider_recyclerview)));
        recyclerView.addItemDecoration(itemDecorator);
        List<ItemChatProfileModel> list = new ArrayList<>();
        list.add(new ItemChatProfileModel("Ani", "Insta pe follow kiya??", false, "1", "7:49 am", ""));
        list.add(new ItemChatProfileModel("Ayesha", "Hii...", true, "0", "7:49 am", ""));
        list.add(new ItemChatProfileModel("Kajal", "Kaise hue??", true, "0", "7:49 am", ""));
        list.add(new ItemChatProfileModel("Avini", "Let’s meet tom...", true, "0", "7:49 am", ""));
        list.add(new ItemChatProfileModel("Natasha", "Have you heard this song?", true, "0", "7:49 am", ""));

        adapter = new UserAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNameEditText.setVisibility(View.VISIBLE);
                mChatButton.setVisibility(View.VISIBLE);
                //mOfflineMsgCheck.setVisibility(View.VISIBLE);
            }


        });
//        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mNameEditText.setVisibility(View.GONE);
//                mChatButton.setVisibility(View.GONE);
//                floatingActionButton.setVisibility(View.VISIBLE);
//            }
//
//
//        });



        // Reply My Msg
        setOnViewHolderClickedListener(new UserAdapter.OnViewHolderClickedListener() {
            @Override
            public void onViewHolderClicked() {
                System.out.println("LongClick in MainActivity");

            }
        });

        chatTabButton.setOnClickListener(view -> {
            animateTabs(matchesTabButton, chatTabButton);
        });

        matchesTabButton.setOnClickListener(view -> {
            animateTabs(chatTabButton, matchesTabButton);
        });

        //

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        mMessageAdapter = new MessageAdapter(this, mMessageBeanList, message -> {
//            if (message.getMessage().getMessageType() == RtmMessageType.IMAGE) {
//                if (!TextUtils.isEmpty(message.getCacheFile())) {
//                    Glide.with(this).load(message.getCacheFile()).into(mBigImage);
//                    mBigImage.setVisibility(View.VISIBLE);
//                } else {
//                    ImageUtil.cacheImage(this, mRtmClient, (RtmImageMessage) message.getMessage(), new ResultCallback<String>() {
//                        @Override
//                        public void onSuccess(String file) {
//                            message.setCacheFile(file);
//                            runOnUiThread(() -> {
//                                Glide.with(SelectionActivity.this).load(file).into(mBigImage);
//                                mBigImage.setVisibility(View.VISIBLE);
//                            });
//                        }
//
//                        @Override
//                        public void onFailure(ErrorInfo errorInfo) {
//
//                        }
//                    });
//                }
//            }
//        });

//        SharedPreferences sharedPref = SelectionActivity.this.getPreferences(Context.MODE_PRIVATE);
//        String lasttarget = sharedPref.getString("targetName", "");
//        for (int x=0; x<userList.size(); x++)
////            String usernm = userList.get(x);
//            System.out.println("This is userlist item+++ "+userList.get(x));
//
//        MyListData[] myListData = new MyListData[] {
//
//                new MyListData(lasttarget, android.R.drawable.ic_dialog_dialer),
//                new MyListData("Email", android.R.drawable.ic_dialog_email),
//                new MyListData("Info", android.R.drawable.ic_dialog_info),
//                new MyListData("Delete", android.R.drawable.ic_delete),
//                new MyListData("Dialer", android.R.drawable.ic_dialog_dialer),
//                new MyListData("Alert", android.R.drawable.ic_dialog_alert),
//                new MyListData("Map", android.R.drawable.ic_dialog_map),
//                new MyListData("Email", android.R.drawable.ic_dialog_email),
//                new MyListData("Info", android.R.drawable.ic_dialog_info),
//                new MyListData("Delete", android.R.drawable.ic_delete),
//                new MyListData("Dialer", android.R.drawable.ic_dialog_dialer),
//                new MyListData("Alert", android.R.drawable.ic_dialog_alert),
//                new MyListData("Map", android.R.drawable.ic_dialog_map),
//        };
//        uRecyclerView = findViewById(R.id.user_list);
//        UserAdapter adapter = new UserAdapter(myListData);
//        uRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        uRecyclerView.setAdapter(adapter);
    }

    public void animateTabs(TextView from, TextView to) {
        from.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        to.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_tab_highlighter);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(from, "textSize", 30, 18);
        animator1.setDuration(500);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(to, "textSize", 18, 30);
        animator2.setDuration(500);
        animator1.start();
        animator2.start();
        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                from.setTextColor(ContextCompat.getColor(SelectionActivity.this, R.color.tabUnSelected));
            }
        });
        animator2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                to.setTextColor(ContextCompat.getColor(SelectionActivity.this, R.color.tabSelected));
            }
        });
    }

    public void onClickChat(View v) {
//        MyListData[] myListData = new MyListData[]{};
        mTargetName = mNameEditText.getText().toString();
        if (mTargetName.equals("")) {
            showToast(getString(mIsPeerToPeerMode ? R.string.account_empty : R.string.channel_name_empty));
        } else if (mTargetName.length() >= MessageUtil.MAX_INPUT_NAME_LENGTH) {
            showToast(getString(mIsPeerToPeerMode ? R.string.account_too_long : R.string.channel_name_too_long));
        } else if (mTargetName.startsWith(" ")) {
            showToast(getString(mIsPeerToPeerMode ? R.string.account_starts_with_space : R.string.channel_name_starts_with_space));
        } else if (mTargetName.equals("null")) {
            showToast(getString(mIsPeerToPeerMode ? R.string.account_literal_null : R.string.channel_name_literal_null));
        } else if (mIsPeerToPeerMode && mTargetName.equals(mUserId)) {
            showToast(getString(R.string.account_cannot_be_yourself));
        } else {
            mChatButton.setEnabled(false);
//            SharedPreferences sharedPref = SelectionActivity.this.getPreferences(Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPref.edit();
//            editor.putString("targetName", mTargetName);
//            userList.add(new MyListData(mTargetName, android.R.drawable.ic_dialog_dialer));
//            System.out.println("******UserList>>> "+userList);
//            editor.commit();
            jumpToMessageActivity();
        }
    }

    private void jumpToMessageActivity() {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra(MessageUtil.INTENT_EXTRA_IS_PEER_MODE, mIsPeerToPeerMode);
        intent.putExtra(MessageUtil.INTENT_EXTRA_TARGET_NAME, mTargetName);
        intent.putExtra(MessageUtil.INTENT_EXTRA_USER_ID, mUserId);
        startActivityForResult(intent, CHAT_REQUEST_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mChatButton.setEnabled(true);
    }

    public void onClickFinish(View v) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHAT_REQUEST_CODE) {
            if (resultCode == MessageUtil.ACTIVITY_RESULT_CONN_ABORTED) {
                finish();
            }
        }
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    

    @Override
    public void OnCardInfoListener(Intent intent) {
        Log.e("Data >>>>", intent.getStringExtra("targetname"));
        mTargetName = intent.getStringExtra("targetname");
        jumpToMessageActivity();
    }


}
