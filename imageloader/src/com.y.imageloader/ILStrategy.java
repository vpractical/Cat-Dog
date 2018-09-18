package com.y.imageloader;

public interface ILStrategy {
    void clearMemoryCache();
    void clearDiskCache();
    void build(ILBuilder builder);
    void pause(String tag);
    void cancel(String tag);
}
