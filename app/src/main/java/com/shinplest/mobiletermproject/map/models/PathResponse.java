
package com.shinplest.mobiletermproject.map.models;

import com.google.gson.annotations.SerializedName;
import com.shinplest.mobiletermproject.map.models.data.Page;
import com.shinplest.mobiletermproject.map.models.data.Record;
import com.shinplest.mobiletermproject.map.models.data.Result;
import com.shinplest.mobiletermproject.map.models.data.Service;

@SuppressWarnings("unused")
public class PathResponse {

    @SerializedName("page")
    private Page mPage;
    @SerializedName("record")
    private Record mRecord;
    @SerializedName("response")
    private PathResponse mResponse;
    @SerializedName("result")
    private Result mResult;
    @SerializedName("service")
    private Service mService;
    @SerializedName("status")
    private String mStatus;

    public Page getPage() {
        return mPage;
    }

    public void setPage(Page page) {
        mPage = page;
    }

    public Record getRecord() {
        return mRecord;
    }

    public void setRecord(Record record) {
        mRecord = record;
    }

    public PathResponse getResponse() {
        return mResponse;
    }

    public void setResponse(PathResponse response) {
        mResponse = response;
    }

    public Result getResult() {
        return mResult;
    }

    public void setResult(Result result) {
        mResult = result;
    }

    public Service getService() {
        return mService;
    }

    public void setService(Service service) {
        mService = service;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
