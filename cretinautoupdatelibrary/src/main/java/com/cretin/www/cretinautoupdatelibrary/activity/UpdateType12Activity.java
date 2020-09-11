package com.cretin.www.cretinautoupdatelibrary.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cretin.www.cretinautoupdatelibrary.R;
import com.cretin.www.cretinautoupdatelibrary.interfaces.AppDownloadListener;
import com.cretin.www.cretinautoupdatelibrary.model.DownloadInfo;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUtils;
import com.cretin.www.cretinautoupdatelibrary.utils.LogUtils;
import com.cretin.www.cretinautoupdatelibrary.utils.ResUtils;
import com.cretin.www.cretinautoupdatelibrary.utils.RootActivity;

public class UpdateType12Activity extends RootActivity {

    private TextView tvMsg;
    private TextView tvBtn2;
    private ImageView ivClose;
    private TextView tvVersion;
    private TextView tvBtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_type12);

        findView();

        setDataAndListener();
    }

    private void setDataAndListener() {
        String log = mDefaultLocale ? downloadInfo.getUpdateLog() : downloadInfo.getUpdateLogEn();
        tvMsg.setText(log);
        tvMsg.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvVersion.setText("v" + downloadInfo.getProdVersionName());

        if (downloadInfo.isForceUpdateFlag()) {
            ivClose.setVisibility(View.GONE);
            tvBtn1.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvBtn2.getLayoutParams();
            layoutParams.setMargins(AppUtils.dp2px(45), AppUtils.dp2px(15), AppUtils.dp2px(45), AppUtils.dp2px(40));
            tvBtn2.setLayoutParams(layoutParams);
        } else {
            ivClose.setVisibility(View.VISIBLE);
        }

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tvBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //左边的按钮
                cancelTask();
                finish();
            }
        });

        tvBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //右边的按钮
                download();
            }
        });
    }

    private void findView() {
        tvMsg = findViewById(R.id.tv_content);
        tvBtn2 = findViewById(R.id.tv_update);
        ivClose = findViewById(R.id.iv_close);
        tvVersion = findViewById(R.id.tv_version);
        tvBtn1 = findViewById(R.id.tv_btn1);
        LinearLayout linearLayout = findViewById(R.id.bg_view);
        int bgRes = mDefaultLocale ? R.mipmap.dialog_bg_type_12 : R.mipmap.dialog_bg_type_12_en;
        linearLayout.setBackgroundResource(bgRes);
    }

    @Override
    public AppDownloadListener obtainDownloadListener() {
        return new AppDownloadListener() {
            @Override
            public void downloading(int progress) {
                tvBtn2.setText(ResUtils.getString(R.string.downloading) + progress + "%");
            }

            @Override
            public void downloadFail(String msg) {
                tvBtn2.setText(ResUtils.getString(R.string.btn_update_now));
                Toast.makeText(UpdateType12Activity.this, ResUtils.getString(R.string.apk_file_download_fail), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void downloadComplete(String path) {
                tvBtn2.setText(ResUtils.getString(R.string.btn_update_now));
            }

            @Override
            public void downloadStart() {
                tvBtn2.setText(ResUtils.getString(R.string.downloading));
            }

            @Override
            public void reDownload() {
                LogUtils.log("下载失败后点击重试");
            }

            @Override
            public void pause() {

            }
        };
    }

    /**
     * 启动Activity
     *
     * @param context
     * @param info
     * @param defaultLocale
     */
    public static void launch(Context context, DownloadInfo info, boolean defaultLocale) {
        launchActivity(context, info, UpdateType12Activity.class, defaultLocale);
    }

}
