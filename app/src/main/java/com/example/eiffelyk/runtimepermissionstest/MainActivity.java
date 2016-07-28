package com.example.eiffelyk.runtimepermissionstest;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //这些权限需要同步添加在Manifest中进行权限注册
        //group:android.permission - group.CONTACTS 通讯录
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.WRITE_CONTACTS));
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.GET_ACCOUNTS));
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.READ_CONTACTS));
        //group:android.permission - group.PHONE 电话  /*拨打电话和管理通话*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.READ_CALL_LOG));
        }
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.READ_PHONE_STATE));
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.CALL_PHONE));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.WRITE_CALL_LOG));
        }
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.USE_SIP));
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.PROCESS_OUTGOING_CALLS));
        //group:android.permission - group.CALENDAR 日历
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.READ_CALENDAR));
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.WRITE_CALENDAR));
        //group:android.permission-group.CAMERA 相机 /*拍摄照片和录制视频*/
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.CAMERA));
        //group:android.permission-group.SENSORS 传感器 /*生命体征相关的传感器数据*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.BODY_SENSORS));
        }
        //group:android.permission-group.LOCATION 位置信息
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.ACCESS_FINE_LOCATION));
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
        //group:android.permission-group.STORAGE 存储空间  /*照片、媒体内容和文件*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.READ_EXTERNAL_STORAGE));
        }
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));
        //group:android.permission-group.MICROPHONE 录音 /*录制音频*/
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.RECORD_AUDIO));
        //group:android.permission-group.SMS 短信 /*发送和查看短信*/
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.READ_SMS));
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.RECEIVE_WAP_PUSH));
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.RECEIVE_MMS));
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.RECEIVE_SMS));
        runTimePermissionArrayList.add(SARunTimePermission.addPermission(Manifest.permission.SEND_SMS));
        if (SARunTimePermissionsTool.requestByActivity(MainActivity.this, runTimePermissionArrayList, new SARunTimePermissionsTool.CancelCallBack() {
            @Override
            public void cancel() {
                // TODO: 2016/7/28 弹出自定义权限提示框 时 直接拒绝操作
            }
        })) {
            // TODO: 2016/7/28  之前已经授权允许执行
        }
    }

    private ArrayList<SARunTimePermission> runTimePermissionArrayList = new ArrayList<>();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SARunTimePermissionsTool.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                if (SARunTimePermissionsTool.requestPermissionsResult(this, permissions, grantResults, runTimePermissionArrayList, new SARunTimePermissionsTool.CancelCallBack() {
                    @Override
                    public void cancel() {
                        // TODO: 2016/7/28 弹出去前往设置页面修改权限框，直接取消
                    }
                })) {
                    // TODO: 2016/7/28  系统权限提示框出现后授权允许执行
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
