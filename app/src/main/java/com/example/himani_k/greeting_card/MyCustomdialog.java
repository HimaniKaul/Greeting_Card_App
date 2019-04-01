package com.example.himani_k.greeting_card;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.rtugeek.android.colorseekbar.ColorSeekBar;

public class MyCustomdialog extends DialogFragment {

    private static final String TAG = "MyCustomDialog";
    static String str;
    public interface OnInputListener{
            void sendInput(String input,int col);
        }
        public OnInputListener mOnInputListener;
        //widgets
        private EditText mInput; int colorr;
        private TextView mActionOk, mActionCancel;
        private RecyclerView.Adapter mAdapter;
        private ColorSeekBar colorSeekBar;

        //vars
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dialog_fragment, container, false);
            mActionCancel = view.findViewById(R.id.action_cancel);
            mActionOk = view.findViewById(R.id.action_ok);
            mInput = view.findViewById(R.id.input);
            colorSeekBar = view.findViewById(R.id.colorSlider);
            RecyclerView recyclerView=view.findViewById(R.id.idRecyclerViewHorizontalList);
            mActionCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: closing dialog");
                    getDialog().dismiss();
                }
            });

            mInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    str= mInput.getText().toString();
                    // TODO Auto-generated method stub
                }
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            colorSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
                    @Override
                    public void onColorChangeListener(int colorBarPosition, int alphaBarPosition, int color) {
                        mInput.setTextColor(color);
                        colorr=color;
                    }
                });


            mActionOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: capturing input");
                    String input = mInput.getText().toString();
                    mOnInputListener.sendInput(input,colorr);
                    getDialog().dismiss();
                }
            });

            // specify an adapter (see also next example)
            final Typeface[] myDataset={Typeface.createFromAsset(getActivity().getAssets(),"AlexBrush-Regular.ttf"),
                                        Typeface.createFromAsset(getActivity().getAssets(),"blackjack.otf"),
                                        Typeface.createFromAsset(getActivity().getAssets(),"Lobster_1.3.otf"),
                                        Typeface.createFromAsset(getActivity().getAssets(),"Aller_Rg.ttf"),
                                        Typeface.createFromAsset(getActivity().getAssets(),"FiraSans-Regular.otf"),
                                        Typeface.createFromAsset(getActivity().getAssets(),"KaushanScript-Regular.otf"),
                                        Typeface.createFromAsset(getActivity().getAssets(),"Rubik-Regular.ttf")};

            recyclerView.setLayoutManager(new LinearLayoutManager((getActivity()),(LinearLayoutManager.HORIZONTAL),false));
            mAdapter = new text_styles_adapter(myDataset,getActivity());
            recyclerView.setAdapter(mAdapter);
            return view;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            try{
                mOnInputListener = (OnInputListener) getActivity();
            }catch (ClassCastException e){
                Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class text_styles_adapter extends RecyclerView.Adapter<text_styles_adapter.MyViewHolder> {
    Context context;
    private Typeface[] typefaces;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public MyViewHolder(View v) {
            super(v);
            mTextView =v.findViewById(R.id.text_recycle_view);
        }
    }

    public text_styles_adapter(Typeface[] myData_set, Context context) {
        typefaces = myData_set;
        this.context=context;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public text_styles_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.textview_styles, parent, false);
        text_styles_adapter.MyViewHolder vh = new text_styles_adapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final text_styles_adapter.MyViewHolder holder, final int position) {
        holder.mTextView.setTypeface(typefaces[position]);
        holder.mTextView.setText(MyCustomdialog.str);

    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return typefaces.length;
    }
}


