package io.agora.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;

import io.agora.views.BlurDialogFragment;
import io.agora.rtmtutorial.R;
/**
 * Created by animsh on 9/2/2021.
 */
public class ChatProfileOptionMenuDialogFragment extends BlurDialogFragment {



    private RelativeLayout rootLayout;
    private TextView unmatchButton, reportButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public int onCreateView() {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return R.layout.fragment_dialog_chat_profile_option_menu;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rootLayout = view.findViewById(R.id.rootLayout);
        unmatchButton = view.findViewById(R.id.unmatchButton);
        reportButton = view.findViewById(R.id.reportProfile);
        Typeface tf = Typeface.createFromAsset(requireActivity().getAssets(), "font/sourcesanspro_semibold.ttf");
        reportButton.setTypeface(tf);

//        rootLayout.setOnClickListener(view1 -> {
//            dismiss();
//        });

        unmatchButton.setOnClickListener(view1 -> {
            UnmatchDialog dialogFragment = new UnmatchDialog();
            dialogFragment.show(getParentFragmentManager(), "unmatch");
            dismiss();
        });

        reportButton.setOnClickListener(view1 -> {
            ReportDialog dialogFragment = new ReportDialog();
            dialogFragment.show(getParentFragmentManager(), "report");
            dismiss();
        });
    }

    private FragmentManager getParentFragmentManager() {
        return null;
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
