package com.dawn.matissedawn;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHOOSE = 23;

    private UriAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter = new UriAdapter());
    }
    public void matisseSelect(View view){
        Matisse.from(this)
                .choose(MimeType.allOf())//选择类型，分为JPEG,PNG,GIF
                .maxSelectable(9)//最多选择张数
//                .capture(true)
//                .captureStrategy(new CaptureStrategy(true, ""))
                .countable(true)//选择图片的是否显示数字
                .imageEngine(new GlideEngine())//显示引擎，可以是PicassoEngine或者GlideEngine但是对应的需要引用对应的包，并且这个方法必须调用
//                .thumbnailScale(0.85f)
//                .theme(R.style.Matisse_Dracula)//设置模式，默认是Matisse_Zhihu,可以设置成Matisse_Dracula
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))//设置显示时图片大小
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)//限制方向,grid显示
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))//添加过滤
                .forResult(REQUEST_CODE_CHOOSE);//requestCode参数
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mAdapter.setData(Matisse.obtainResult(data));
        }
    }
    private static class UriAdapter extends RecyclerView.Adapter<UriAdapter.UriViewHolder> {

        private List<Uri> mUris;

        void setData(List<Uri> uris) {
            mUris = uris;
            notifyDataSetChanged();
        }

        @Override
        public UriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new UriViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.uri_item, parent, false));
        }

        @Override
        public void onBindViewHolder(UriViewHolder holder, int position) {
            holder.mUri.setText(mUris.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return mUris == null ? 0 : mUris.size();
        }

        static class UriViewHolder extends RecyclerView.ViewHolder {

            private TextView mUri;

            UriViewHolder(View contentView) {
                super(contentView);
                mUri = (TextView) contentView.findViewById(R.id.uri);
            }
        }
    }
}
