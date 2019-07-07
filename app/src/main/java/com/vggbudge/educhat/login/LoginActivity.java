package com.vggbudge.educhat.login;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cottacush.android.libraries.utils.ViewUtils;
import com.vggbudge.educhat.R;
import com.vggbudge.educhat.base.BaseActivity;
import com.vggbudge.educhat.login.register.RegisterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vggbudge.educhat.utils.ViewUtils.isShowing;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar_root)
    ConstraintLayout progressBarRoot;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.progress_message)
    TextView progressMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //TODO FIX: Butterknife is not binding the views
        setUpUnbinder(ButterKnife.bind(this));
        setSupportActionBar(toolbar);
        hideToolbar();
        setUpToolBarUpNavigation();
        showRegisterScreen();
    }

    @Override
    public void showLoading() {
        showLoading(R.string.please_wait);
    }

    @Override
    public void showLoading(int resId) {
        showLoading(getString(resId));
    }

    @Override
    public void showLoading(String message) {
        ViewUtils.show(progressBarRoot, progressBar, progressMessage);
        progressMessage.setText(message);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void dismissLoading() {
        ViewUtils.hide(progressBarRoot);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onBackPressed() {
        if (!isShowing(progressBarRoot)) {
            super.onBackPressed();
        }
    }

    @Override
    public void setDrawerIconToBack() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setDrawerIconToHome() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public void hideToolbar() {
        getSupportActionBar().hide();
    }

    public void showToolbar() {
        getSupportActionBar().show();
    }

    public void adjustView() {
        FrameLayout frameLayout = findViewById(R.id.view_container);
        frameLayout.setFitsSystemWindows(true);
    }

    public void setUpToolBarUpNavigation() {
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void showRegisterScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.view_container, RegisterFragment.newInstance())
                .commit();
    }
}
