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

/**
 * Created by animsh on 9/3/2021.
 */
public class ReportDialog extends BlurDialogFragment {

    RelativeLayout rootLayout;
    TextView reportButton, cancelButton;

    @Override
    public int onCreateView() {
        return R.layout.fragment_dialog_report;
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
        reportButton = view.findViewById(R.id.reportButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        Typeface tf = Typeface.createFromAsset(requireActivity().getAssets(), "font/sourcesanspro_semibold.ttf");
        reportButton.setTypeface(tf);
        cancelButton.setTypeface(tf);

//        rootLayout.setOnClickListener(view1 -> {
//            dismiss();
//        });

        reportButton.setOnClickListener(view1 -> {
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
