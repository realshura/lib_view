package com.feeling.view.popupmenu;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.feeling.view.R;

public class PopupMenu extends PopupWindow {

    private Activity activity;
    private View popView;

    private RecyclerView mRecyclerView;
    private MenuAdapter mMenuAdapter;
    private String[] tabs;

    private OnMenuClickListener mListener;

    public PopupMenu(Activity activity, String[] data) {
        super(activity);
        this.activity = activity;
        this.tabs = data;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popView = inflater.inflate(R.layout.popup_menu, null);
        this.setContentView(popView);

        mRecyclerView = popView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mMenuAdapter = new MenuAdapter();
        mRecyclerView.setAdapter(mMenuAdapter);

        this.setWidth(dip2px(activity, 150));
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setTouchable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable());

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1);
            }
        });
    }


    /**
     * 设置显示的位置
     *
     * @param resourId 这里的x,y值自己调整可以
     */
    public void showLocation(int resourId) {
        setBackgroundAlpha(0.8f);
        View view = activity.findViewById(resourId);
        int xOff = getWidth() - view.getMeasuredWidth();
        int left = -xOff - dip2px(activity, 10);
        showAsDropDown(view, left, dip2px(activity, 0));
    }

    // dip转换为px
    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    private class MenuAdapter extends RecyclerView.Adapter<ItemHolder> {

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_menu_item, parent, false);
            return new ItemHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            holder.mLabelText.setText(tabs[position]);
            holder.mLineView.setVisibility(position == tabs.length - 1 ? View.GONE : View.VISIBLE);
            if (position == 0) {
                holder.itemView.setBackgroundResource(R.drawable.popup_menu_item_top_bg);
            } else if (position == tabs.length - 1) {
                holder.itemView.setBackgroundResource(R.drawable.popup_menu_item_bottom_bg);
            } else {
                holder.itemView.setBackgroundResource(R.drawable.popup_menu_item_center_bg);
            }
        }

        @Override
        public int getItemCount() {
            return tabs != null ? tabs.length : 0;
        }
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        TextView mLabelText;
        View mLineView;

        public ItemHolder(View itemView) {
            super(itemView);
            mLabelText = itemView.findViewById(R.id.textView_label);
            mLineView = itemView.findViewById(R.id.lineView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (mListener != null) {
                        mListener.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

    public interface OnMenuClickListener {
        void onItemClick(int index);
    }

    public void setOnMenuClickListener(OnMenuClickListener listener) {
        mListener = listener;
    }
}
