package com.example.eiffelyk.runtimepermissionstest;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Eiffelyk on 2016/3/21.
 * 封装的运行时权限调用的工具类（Activity）
 */
public class SARunTimePermissionsTool {
     interface CancelCallBack {
        void cancel();
    }
    /**
     * 回调常量
     */
    public static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;

    /**
     * 拒绝后第二次弹出后展示的权限使用说明的自定义对话框
     *
     * @param context    依赖
     * @param message    需要展示的文字
     * @param okListener 确认键的回调ClickListener
     */
    private static void showMessageDialog(Context context, String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.act_persimssion_dialg_title_str))
                .setMessage(message)
                .setPositiveButton(context.getResources().getString(R.string.act_persimssion_message_dialg_sure_str), okListener)
                .setNegativeButton(context.getResources().getString(R.string.act_persimssion_message_dialg_cancel_str), cancelListener)
                .setCancelable(false)
                .create()
                .show();
    }

    /**
     * 申请权限的方法
     *
     * @param act                        需要调用权限方法的页面
     * @param runTimePermissionArrayList 需要请求的 权限数组
     * @return true已经全部授权，false没有授权或者部分授权
     */
    public static boolean requestByActivity(final Activity act, ArrayList<SARunTimePermission> runTimePermissionArrayList, final CancelCallBack cancelCallBack) {
        List<String> permissionsNeeded = new ArrayList<>();
        final List<String> permissionsList = new ArrayList<>();
        for (SARunTimePermission run : runTimePermissionArrayList) {
            if (!addPermission(act, permissionsList, run.getPermission())) {
                permissionsNeeded.add(run.getName());
            }
        }
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                String message = act.getResources().getString(R.string.act_persimssion_message_dialg_message_str) + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + "、" + permissionsNeeded.get(i);
                showMessageDialog(act, message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(act, permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cancelCallBack.cancel();
                    }
                });
                return false;
            }
            ActivityCompat.requestPermissions(act, permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    /**
     * 将单个权限加入到权限申请的数组中
     *
     * @param activity        需要请求权限的页面
     * @param permissionsList 权限数组
     * @param permission      单个权限
     * @return 此权限是否已经授权
     */
    private static boolean addPermission(Activity activity, List<String> permissionsList, String permission) {
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission))
                return false;
        }
        return true;
    }

    /**
     * 申请权限的方法(Fragment中使用)
     *
     * @param fragment                   当前fragment
     * @param runTimePermissionArrayList 权限数组
     * @return 是否已经授权
     */
    public static boolean requestByFragment(final Fragment fragment, ArrayList<SARunTimePermission> runTimePermissionArrayList, final CancelCallBack cancelCallBack) {
        List<String> permissionsNeeded = new ArrayList<>();
        final List<String> permissionsList = new ArrayList<>();
        for (SARunTimePermission run : runTimePermissionArrayList) {
            if (!addPermission(fragment.getActivity(), permissionsList, run.getPermission())) {
                permissionsNeeded.add(run.getName());
            }
        }
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                String message = fragment.getActivity().getResources().getString(R.string.act_persimssion_message_dialg_message_str) + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + "、" + permissionsNeeded.get(i);
                showMessageDialog(fragment.getActivity(), message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(fragment.getActivity(), permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cancelCallBack.cancel();
                    }
                });
                return false;
            }
            fragment.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public static boolean requestPermissionsResult(final Activity act, String[] permissions, int[] grantResults, ArrayList<SARunTimePermission> runTimePermissionArrayList, final CancelCallBack cancelCallBack) {
        Set<String> groupNames = new HashSet<>();
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                for (SARunTimePermission saRunTimePermission : runTimePermissionArrayList) {
                    if (permissions[i].equals(saRunTimePermission.getPermission())) {
                        groupNames.add(saRunTimePermission.getGroupName());
                        break;
                    }
                }
            }
        }
        if (groupNames.size() > 0) {
            String string = "";
            for (Object str : groupNames) {
                string += "、" + str;
            }
            string = string.substring(1);
            new AlertDialog.Builder(act)
                    .setTitle(act.getResources().getString(R.string.act_persimssion_dialg_title_str))
                    .setMessage(act.getResources().getString(R.string.act_welcome_persimssion_dialg_message_str, string))
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.fromParts("package", act.getPackageName(), null));
                            act.startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            cancelCallBack.cancel();
                        }
                    })
                    //.setNeutralButton("重新提示", new DialogInterface.OnClickListener() {
                    //    @Override
                    //    public void onClick(DialogInterface dialogInterface, int i) {
                    //        if (SARunTimePermissionsTool.requestByActivity(MainActivity.this, runTimePermissionArrayList)) {
                    //
                    //        }
                    //    }
                    //})
                    .setCancelable(false)
                    .create()
                    .show();
            return false;
        }
        return true;
    }
}
