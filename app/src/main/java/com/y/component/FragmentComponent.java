
package com.y.component;


import com.y.mvp.component.AppComponent;
import com.y.mvp.fragment.ChatFragment;
import com.y.mvp.fragment.GameFragment;
import com.y.mvp.fragment.NewsFragment;
import com.y.mvp.fragment.MoreFragment;
import com.y.mvp.fragment.VideoFragment;
import com.y.mvp.scope.CustomizeScope;

import dagger.Component;

@CustomizeScope
@Component(dependencies = AppComponent.class)
public interface FragmentComponent {
    void inject(NewsFragment tool);
    void inject(ChatFragment chat);
    void inject(GameFragment game);
    void inject(VideoFragment game);
    void inject(MoreFragment tool);
}
