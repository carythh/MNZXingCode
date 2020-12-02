package com.maning.zxingcodedemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.client.android.model.MNScanConfig;
import com.google.zxing.client.android.other.OnScanCallback;
import com.google.zxing.client.android.view.ScanSurfaceView;

public class ScanActivity extends AppCompatActivity {

    private ScanSurfaceView mScanSurfaceView;
    private Handler UIHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        initView();
    }

    private void initView() {
        mScanSurfaceView = (ScanSurfaceView) findViewById(R.id.scan_surface_view);
        mScanSurfaceView.init(this);
        MNScanConfig scanConfig = new MNScanConfig.Builder()
                .setSupportZoom(true)
                .builder();
        mScanSurfaceView.setScanConfig(scanConfig);
        mScanSurfaceView.setOnScanCallback(new OnScanCallback() {
            @Override
            public void onScanSuccess(String resultTxt, Bitmap barcode) {
                Toast.makeText(ScanActivity.this, "成功：" + resultTxt, Toast.LENGTH_SHORT).show();
                UIHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //重新开启扫描
                        mScanSurfaceView.restartScan();
                    }
                }, 1000);

            }

            @Override
            public void onFail(String msg) {
                Toast.makeText(ScanActivity.this, "失败：" + msg, Toast.LENGTH_SHORT).show();
                //关闭页面
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScanSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScanSurfaceView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScanSurfaceView.onDestroy();
        mScanSurfaceView = null;
        UIHandler.removeCallbacksAndMessages(null);
    }

    public void restartScan(View view) {
        if (mScanSurfaceView != null) {
            mScanSurfaceView.restartScan();
        }
    }

    public void stopScan(View view) {
        if (mScanSurfaceView != null) {
            mScanSurfaceView.stopScan();
        }
    }
}
