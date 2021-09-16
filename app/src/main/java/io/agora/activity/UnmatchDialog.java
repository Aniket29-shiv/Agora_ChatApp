package io.agora.activity;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.agora.views.BlurDialogFragment;
import io.agora.rtmtutorial.R;
import com.google.android.material.button.MaterialButton;



/**
 * Created by animsh on 9/2/2021.
 */
public class UnmatchDialog extends BlurDialogFragment {

    private RelativeLayout rootLayout;
    private MaterialButton unmatchButton;
    private TextView cancelButton, label1;

    @Override
    public int onCreateView() {
        return R.layout.fragment_dialog_unmatch;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootLayout = view.findViewById(R.id.rootLayout);
        unmatchButton = view.findViewById(R.id.unmatchButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        label1 = view.findViewById(R.id.label1);

        Typeface tf = Typeface.createFromAsset(requireActivity().getAssets(), "font/sourcesanspro_semibold.ttf");
        unmatchButton.setTypeface(tf);
        cancelButton.setTypeface(tf);
        label1.setTypeface(tf);

//        rootLayout.setOnClickListener(view1 -> {
//            dismiss();
//        });

        unmatchButton.setOnClickListener(view1 -> {
            dismiss();
        });

        cancelButton.setOnClickListener(view1 -> {
            dismiss();
        });
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
