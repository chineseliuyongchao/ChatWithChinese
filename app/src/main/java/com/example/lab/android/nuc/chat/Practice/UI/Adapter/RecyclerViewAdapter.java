package com.example.lab.android.nuc.chat.Practice.UI.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab.android.nuc.chat.Practice.Test.Ise_Demo;
import com.example.lab.android.nuc.chat.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private List<Ise_Demo.DataBean> dataBeanList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Ise_Demo.DataBean> list) {
        this.context = context;
        this.dataBeanList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.card_view1, parent, false);
        //将创建的View注册点击事件
        itemView.setOnClickListener(RecyclerViewAdapter.this);

        return new ItemHolder(itemView);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position,List payloads) {
        holder.setIsRecyclable(false);
        ItemHolder itemHolder = (ItemHolder) holder;
        if (payloads.isEmpty()){
            itemHolder.itemView.setTag(position);

            itemHolder.test.setTag(position);
            itemHolder.textView1.setTag(position);
            itemHolder.textView2.setTag(position);
            itemHolder.imageView.setTag(position);
            itemHolder.text_more.setTag(position);
            itemHolder.text_result.setTag(position);
            itemHolder.imageView_more.setTag(position);
            Ise_Demo.DataBean dataBean = dataBeanList.get(position);
            itemHolder.textView1.setText(dataBean.getText());
            itemHolder.textView2.setText(dataBean.getContent());
            itemHolder.imageView_more.setImageResource(dataBeanList.get(position).getImageView());
        } else{
            int type =(int)payloads.get(0);
            switch (type){
                case 0 :
                    itemHolder.imageView_more.setImageResource(dataBeanList.get(position).getImageView());
                    break;
            }
        }


        itemHolder.textView1.setOnClickListener(RecyclerViewAdapter.this);
        itemHolder.textView2.setOnClickListener(RecyclerViewAdapter.this);
        itemHolder.imageView.setOnClickListener(RecyclerViewAdapter.this);

       itemHolder.test.setOnClickListener(RecyclerViewAdapter.this);
       itemHolder.text_more.setOnClickListener(RecyclerViewAdapter.this);
    }

    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

     //  public Button mIseStartButton, btn2, btn3;
       public Button test;
       public TextView textView1, textView2;
       public ImageView imageView,imageView_more;
       public TextView text_more;
       public TextView text_result;
    //   public EditText mResTv;

     //  public EditText editText;

        public ItemHolder(View itemView) {
            super(itemView);

            test = itemView.findViewById(R.id.btn_test);
            textView1 = itemView.findViewById(R.id.tv1);
            textView2 = itemView.findViewById(R.id.tv2);
            imageView = itemView.findViewById(R.id.iv_shoucang);
            text_more = itemView.findViewById(R.id.tv_more);
            imageView_more = itemView.findViewById(R.id.iv_more);
            text_result = itemView.findViewById(R.id.ise_result_text);

        }
    }


    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**
     * item里面有多个控件可以点击
     */


    public interface OnRecyclerViewItemClickListener {
        void onClick(View view, int position);
    }


    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (mOnItemClickListener != null) {
            switch (v.getId()) {

                case R.id.btn_test:
                    mOnItemClickListener.onClick(v, position);
                    break;
                case R.id.iv_shoucang:
                    mOnItemClickListener.onClick(v, position);
                    break;
                case R.id.tv_more:
                    mOnItemClickListener.onClick(v, position);
                    break;
                default:
              //      mOnItemClickListener.onClick(v, position);
                    break;
            }
        }
    }

}
