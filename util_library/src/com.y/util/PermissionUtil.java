package com.y.util;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Size;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class PermissionUtil {

    public interface PermissionCallback {
        /**
         * 赋予了哪些权限
         * @param perms
         */
        void onGranted(List<String> perms);
        /**
         * 拒绝了哪些权限
         * @param perms
         */
        void onDenied(List<String> perms);
        /**
         * 从设置页中返回时间，并不一定赋予权限了
         */
        void onSettingGranted();
    }

    private PermissionCallback mPermissionListener;

    public PermissionUtil callback(PermissionCallback listener) {
        mPermissionListener = listener;
        return this;
    }

    private static PermissionUtil instance = new PermissionUtil();

    public static PermissionUtil getInstance() {
        return instance;
    }

    private PermissionUtil(){}

    /**
     * 是否有权限
     * @param activity
     * @param perm
     * @return
     */
    public boolean has(Activity activity, String... perm) {
        return EasyPermissions.hasPermissions(activity, perm);
    }

    /**
     * 申请权限
     * @param activity
     * @param tip
     * @param perm
     */
    public void request(Activity activity, String tip, @NonNull @Size(min = 1) String... perm) {
        EasyPermissions.requestPermissions(activity, tip, 201, perm);
    }

    /**
     * 如果有禁止询问的权限，弹窗提示跳转设置页
     * @param activity
     * @param tip
     * @param perms
     */
    public void neverAsk(Activity activity, String tip, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(activity, perms)) {
            new AppSettingsDialog.Builder(activity).setTitle("权限申请")
                    .setRationale(tip).build().show();
        }
    }

    /**
     * 回调中调用
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, new EasyPermissions.PermissionCallbacks() {
            @Override
            public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
                if (mPermissionListener != null) {
                    mPermissionListener.onGranted(perms);
                }
            }

            @Override
            public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
                if (mPermissionListener != null) {
                    mPermissionListener.onDenied(perms);
                }
            }

            @Override
            public void onRequestPermissionsResult(int i, @NonNull String[] strings, @NonNull int[] ints) {

            }
        });
    }

    /**
     * 回调中调用
     * @param requestCode
     */
    public void onActivityResult(int requestCode) {
        //弹窗的确认和取消都会回调这里
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (mPermissionListener != null) {
                mPermissionListener.onSettingGranted();
            }
        }
    }

}
