package com.example.eiffelyk.runtimepermissionstest;

import android.Manifest;

/**
 * Created by Eiffelyk on 2016/3/21.
 * 权限封装，包括权限（系统请求使用）和说明（自定义的拒绝后的弹出对话框中的文字），
 */
public class SARunTimePermission {
    /**
     * 权限
     */
    String permission;
    /**
     * 说明
     */
    String name;
    /**
     * 权限组名称
     */
    String groupName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public SARunTimePermission(String permission, String name, String groupName) {
        this.permission = permission;
        this.name = name;
        this.groupName = groupName;
    }

    public SARunTimePermission() {
    }

    @Override
    public String toString() {
        return "SARunTimePermission{" +
                "permission='" + permission + '\'' +
                ", name='" + name + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }

    public static SARunTimePermission addPermission(String permission) {
        SARunTimePermission saRunTimePermission = new SARunTimePermission();
        saRunTimePermission.setPermission(permission);
        String[] strings = checkPermission(permission);
        saRunTimePermission.setName(strings[0]);
        saRunTimePermission.setGroupName(strings[1]);
        return saRunTimePermission;
    }

    private static String[] checkPermission(String permission) {
        String[] arr = new String[2];
        switch (permission) {
            //通讯录
            case Manifest.permission.WRITE_CONTACTS:
                arr[0] = "写入通讯录";
                arr[1] = "通讯录";
                break;
            case Manifest.permission.GET_ACCOUNTS:
                arr[0] = "访问账户";
                arr[1] = "通讯录";
                break;
            case Manifest.permission.READ_CONTACTS:
                arr[0] = "读取通讯录";
                arr[1] = "通讯录";
                break;
            //电话
            case Manifest.permission.READ_CALL_LOG:
                arr[0] = "读取通话记录";
                arr[1] = "电话";
                break;
            case Manifest.permission.READ_PHONE_STATE:
                arr[0] = "获取通话状态";
                arr[1] = "电话";
                break;
            case Manifest.permission.CALL_PHONE:
                arr[0] = "拨打电话";
                arr[1] = "电话";
                break;
            case Manifest.permission.WRITE_CALL_LOG:
                arr[0] = "写入通话记录";
                arr[1] = "电话";
                break;
            case Manifest.permission.USE_SIP:
                arr[0] = "SIP视频通话";
                arr[1] = "电话";
                break;
            case Manifest.permission.PROCESS_OUTGOING_CALLS:
                arr[0] = "监听或修改通话";
                arr[1] = "电话";
                break;
            //日历
            case Manifest.permission.READ_CALENDAR:
                arr[0] = "读取日程";
                arr[1] = "日历";
                break;
            case Manifest.permission.WRITE_CALENDAR:
                arr[0] = "写入日程";
                arr[1] = "日历";
                break;
            //相机
            case Manifest.permission.CAMERA:
                arr[0] = "拍照";
                arr[1] = "相机";
                break;
            //传感器
            case Manifest.permission.BODY_SENSORS:
                arr[0] = "使用传感器";
                arr[1] = "传感器";
                break;
            //位置信息
            case Manifest.permission.ACCESS_FINE_LOCATION:
                arr[0] = "通过GPS定位";
                arr[1] = "位置信息";
                break;
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                arr[0] = "通过WiFi定位";
                arr[1] = "位置信息";
                break;
            //存储空间
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                arr[0] = "读取文件";
                arr[1] = "存储空间";
                break;
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                arr[0] = "写入文件";
                arr[1] = "存储空间";
                break;
            //麦克风
            case Manifest.permission.RECORD_AUDIO:
                arr[0] = "录音";
                arr[1] = "麦克风";
                break;
            //短信
            case Manifest.permission.READ_SMS:
                arr[0] = "读取短信";
                arr[1] = "短信";
                break;
            case Manifest.permission.RECEIVE_WAP_PUSH:
                arr[0] = "接收WAP推送信息";
                arr[1] = "短信";
                break;
            case Manifest.permission.RECEIVE_MMS:
                arr[0] = "接收彩信";
                arr[1] = "短信";
                break;
            case Manifest.permission.RECEIVE_SMS:
                arr[0] = "接收短信";
                arr[1] = "短信";
                break;
            case Manifest.permission.SEND_SMS:
                arr[0] = "发送短信";
                arr[1] = "短信";
                break;
            default:
                arr[0] = "未知";
                arr[1] = "未知";
                break;
        }
        return arr;
    }
}