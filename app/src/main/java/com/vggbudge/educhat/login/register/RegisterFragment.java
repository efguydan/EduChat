package com.vggbudge.educhat.login.register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vggbudge.educhat.R;
import com.vggbudge.educhat.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterFragment extends BaseFragment {

    @BindView(R.id.surname_layout)
    TextInputLayout surnameTextInputLayout;
    @BindView(R.id.surname)
    TextInputEditText surnameEditText;
    @BindView(R.id.first_name_layout)
    TextInputLayout firstNameTextInputLayout;
    @BindView(R.id.first_name)
    TextInputEditText firstNameEditText;
    @BindView(R.id.middle_name_layout)
    TextInputLayout middleNameTextInputLayout;
    @BindView(R.id.middle_name)
    TextInputEditText middleNameEditText;
    @BindView(R.id.email_address_layout)
    TextInputLayout emailAddressTextInputLayout;
    @BindView(R.id.email_address)
    TextInputEditText emailEditText;
    @BindView(R.id.password_layout)
    TextInputLayout passwordTextInputLayout;
    @BindView(R.id.password)
    TextInputEditText passwordEditText;
    @BindView(R.id.confirm_password_layout)
    TextInputLayout confirmPasswordTextInputLayout;
    @BindView(R.id.confirm_password)
    TextInputEditText confirmPasswordEditText;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        setUpUnbinder(ButterKnife.bind(this, view));
        setToolbarTitle(getString(R.string.register));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
