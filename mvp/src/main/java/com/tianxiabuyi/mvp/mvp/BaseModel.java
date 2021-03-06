package com.tianxiabuyi.mvp.mvp;

import com.tianxiabuyi.mvp.manager.RepositoryManager;

/**
 * Created in 2017/9/20 14:05.
 *
 * @author Wang YaoDong.
 */
public class BaseModel implements IModel {

    /**
     * 用于管理网络请求层,以及数据缓存层
     */
    protected RepositoryManager mRepositoryManager;

    public BaseModel(RepositoryManager repositoryManager) {
        this.mRepositoryManager = repositoryManager;
    }

    /**
     * 框架中{@link BasePresenter#onDestroy()}会默认调用
     */
    @Override
    public void onDestroy() {
        mRepositoryManager = null;
    }
}
