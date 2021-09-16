package io.agora.views;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

/**
 * Created by animsh on 9/2/2021.
 */
public abstract class BlurDialogFragment extends DialogFragment {
    private final int BLUR_ID = 514214;
    private float DEFAULT_BLUR_RADIUS = 20F;
    private boolean DEFAULT_BLUR_LIVE = false;

    public abstract int onCreateView();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return blurInjector(inflater.inflate(onCreateView(), container, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BlurView blurView = view.findViewById(BLUR_ID);
        blurView.setupWith(((ViewGroup) requireActivity().getWindow().getDecorView()))
                .setFrameClearDrawable(requireActivity().getWindow().getDecorView().getBackground())
                .setBlurAlgorithm(new RenderScriptBlur(requireContext()))
                .setHasFixedTransformationMatrix(!liveBackground())
                .setBlurRadius(blurRadius());
    }


    /**
     * generate blur background and include content into that
     *
     * @param container View?
     * @return ViewGroup?
     */
    private BlurView blurInjector(View container) {
        BlurView blurContainer = new BlurView(requireContext());
        blurContainer.setId(BLUR_ID);
        blurContainer.addView(container);
        return blurContainer;
    }


    /**
     * return blur radius to make your background blur as you can!
     * value must be  (0 < value < 25)
     *
     * @return Float
     */
    float blurRadius() {
        return DEFAULT_BLUR_RADIUS;
    }

    /**
     * if you have live background that means something that is moving while you are showing this Dialog Fragment
     * then you should return {@link Boolean#TRUE} otherwise {@link Boolean#FALSE}
     *
     * @return Boolean
     */
    private boolean liveBackground() {
        return DEFAULT_BLUR_LIVE;
    }
}
