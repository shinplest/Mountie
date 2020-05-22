package com.shinplest.mobiletermproject.map.interfaces;

import com.shinplest.mobiletermproject.map.models.PathResponse;

public interface MapFragmentView {
    void getPathdataSuccess(PathResponse pathResponse);
    void getPathdataFailure();
}
