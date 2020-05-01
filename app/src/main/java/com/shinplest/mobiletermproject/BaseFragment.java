package com.shinplest.mobiletermproject;

import android.app.ProgressDialog;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    public ProgressDialog mProgressDialog;

    protected void showCustomToast(final String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
