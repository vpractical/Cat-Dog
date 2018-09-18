package com.y.mvp.activity.presenter;


import com.y.api.GameApi;
import com.y.bean.Role;
import com.y.config.Const;
import com.y.mvp.base.RxPresenter;
import com.y.mvp.download.DownloadObserver;
import com.y.util.AppUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class RolePresenter extends RxPresenter<RoleContract.View> implements RoleContract.Presenter {

    GameApi mGameApi;
    //缓存目录
    private File roleDir;
    private String zipName = "role.rar";
    private File zipFile;
    //记录下载进度
    private int curProgress;

    @Inject
    public RolePresenter(GameApi gameApi) {
        this.mGameApi = gameApi;
        String path = AppUtil.context().getExternalCacheDir().getAbsolutePath() + Const.PATH_GAME_ROLE_DIR;
        roleDir = new File(path);
        zipFile = new File(path + zipName);
    }

    @Override
    public void checkCache() {
        if (roleDir.exists() && roleDir.list().length > 1) {
            read();
        } else {
            try {
                if(!roleDir.exists()){
                    roleDir.mkdirs();
                }
                zipFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mView.emptyCache();
        }
    }

    @Override
    public void download() {
        mGameApi.downloadRole(new DownloadObserver(Const.PATH_GAME_ROLE_DIR + zipName) {
            @Override
            public void onSuccess(long bytesRead, long contentLength, float progress, boolean done, String filePath) {
                int pro = (int) progress;
                if (done) {
                    zipParse();
                    mView.downLoading(100, contentLength + "字节");
                } else if (curProgress < pro) {
                    curProgress = pro;
                    mView.downLoading(curProgress, contentLength + "字节");
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    @Override
    public void zipParse() {
//        String zip = roleDir.getAbsolutePath() + File.separator + zipName;
//        try {
//            ZipUtil.unZip(zip, roleDir.getAbsolutePath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void save() {

    }

    @Override
    public void read() {
        String[] imgs = roleDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("png") || name.endsWith("gif") || name.endsWith("jpg");
            }
        });

        if(imgs == null) return;

        int size = imgs.length;
        List<Role> roles = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Role role = new Role();
            role.path = roleDir + "/" + imgs[i];
            roles.add(role);
        }

        mView.show(roles);
    }
}