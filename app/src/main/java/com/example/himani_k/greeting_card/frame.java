package com.example.himani_k.greeting_card;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Objects;

public class frame extends BottomSheetDialogFragment {

    public frame() {
        // Required empty public constructor
    }

    private FrameListener mFrameListener;

    public void setmFrameListener(FrameListener frameListener) {
        mFrameListener = frameListener;
    }

    public interface FrameListener {
        void onFrameClick(Bitmap bitmap);
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            } }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.frames,null);

        ImageView back=contentView.findViewById(R.id.back_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        dialog.setContentView(contentView);
        if(dialog != null) {
            contentView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        ((View) contentView.getParent()).setFitsSystemWindows(true);
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
        contentView.measure(0,0);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        bottomSheetBehavior.setPeekHeight(screenHeight);
        if (params.getBehavior() instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior)params.getBehavior()).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        params.height = screenHeight;
        ((View) contentView.getParent()).setLayoutParams(params);
        RecyclerView rvEmoji = contentView.findViewById(R.id.rvEmoji);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        rvEmoji.setLayoutManager(gridLayoutManager);
        FrameAdapter frameAdapter = new FrameAdapter();
        rvEmoji.setAdapter(frameAdapter);
    }


    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.ViewHolder> {
        int[] frameList = new int[]{R.drawable.frame_one, R.drawable.frame_two,
                R.drawable.frame_three,
                R.drawable.frame_one, R.drawable.frame_two,
                R.drawable.frame_three,
                R.drawable.frame_one,   R.drawable.frame_two,
                R.drawable.frame_three,
                R.drawable.frame_one, R.drawable.frame_two,
                R.drawable.frame_three,
                R.drawable.frame_one, R.drawable.frame_two,
                R.drawable.frame_three};

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_image, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.imgframe.setImageResource(frameList[position]);
        }

        @Override
        public int getItemCount() {
            return frameList.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imgframe;

            ViewHolder(View itemView) {
                super(itemView);
                imgframe = itemView.findViewById(R.id.frame_Sticker);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mFrameListener != null) {
                            mFrameListener.onFrameClick(
                                    BitmapFactory.decodeResource(getResources(),
                                            frameList[getLayoutPosition()]));
                        }
                        dismiss();
                    }
                });
            }
        }}}

