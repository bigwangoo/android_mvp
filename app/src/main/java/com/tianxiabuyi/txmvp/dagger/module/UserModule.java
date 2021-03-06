package com.tianxiabuyi.txmvp.dagger.module;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tianxiabuyi.mvp.dagger.scope.ActivityScope;
import com.tianxiabuyi.txmvp.mvp.contract.UserContract;
import com.tianxiabuyi.txmvp.mvp.model.UserModel;
import com.tianxiabuyi.txmvp.mvp.model.bean.UserBean;
import com.tianxiabuyi.txmvp.ui.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * 参考 dagger2 @module用法
 * <p>
 * <p>
 * Created in 2017/9/21 19:52.
 *
 * @author Wang YaoDong.
 */
@Module
public class UserModule {

    private UserContract.View view;

    /**
     * 构建 UserModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
     */
    public UserModule(UserContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UserContract.View provideUserView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    UserContract.Model provideUserModel(UserModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new GridLayoutManager(view.getActivity(), 2);
    }

    @ActivityScope
    @Provides
    List<UserBean> provideUserList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    RecyclerView.Adapter provideUserAdapter(List<UserBean> list) {
        return new HomeAdapter(list);
    }
}
