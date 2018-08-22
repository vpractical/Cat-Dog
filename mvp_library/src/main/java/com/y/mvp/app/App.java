package com.y.mvp.app;

import com.y.mvp.component.AppComponent;

import io.reactivex.annotations.NonNull;

public interface App {
    @NonNull
    AppComponent getAppComponent();
}
