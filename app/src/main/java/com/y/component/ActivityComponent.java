package com.y.component;

import com.y.mvp.component.AppComponent;
import com.y.mvp.scope.CustomizeScope;
import com.y.mvp.activity.MainActivity;

import dagger.Component;

@CustomizeScope
@Component(dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(MainActivity main);
}
