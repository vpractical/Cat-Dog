package com.y.mvp.module;

/**
 *
 */
public interface ConfigModule {
    /**
     * 配置框架的参数
     * @param builder
     */
    void applyOptions(GlobalConfigBuild.Builder builder);
}
