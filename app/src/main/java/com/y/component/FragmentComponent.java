
package com.y.component;


import com.y.mvp.component.AppComponent;
import com.y.mvp.scope.CustomizeScope;
import com.y.mvp.fragment.ChatFragment;
import com.y.mvp.fragment.GameFragment;
import com.y.mvp.fragment.ToolFragment;

import dagger.Component;

@CustomizeScope
@Component(dependencies = AppComponent.class)
public interface FragmentComponent {
    void inject(ChatFragment chat);
    void inject(GameFragment game);
    void inject(ToolFragment tool);
}
