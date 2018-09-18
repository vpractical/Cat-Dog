package com.y.component;

import com.y.mvp.activity.ASCIIImageActivity;
import com.y.mvp.activity.MainActivity;
import com.y.mvp.activity.RoleActivity;
import com.y.mvp.activity.VideoActivity;
import com.y.mvp.component.AppComponent;
import com.y.mvp.login.LoginActivity;
import com.y.mvp.scope.CustomizeScope;

import dagger.Component;

@CustomizeScope
@Component(dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(MainActivity main);
    void inject(LoginActivity login);
//    void inject(RegisterFaceActivity registerFaceActivity);
    void inject(RoleActivity role);
    void inject(VideoActivity video);
    void inject(ASCIIImageActivity ascii);
}
