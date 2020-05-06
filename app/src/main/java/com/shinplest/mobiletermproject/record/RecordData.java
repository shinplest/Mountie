package com.shinplest.mobiletermproject.record;

public class RecordData {
    // Card1
    private int mainRecord;
    private String recentRecord;
    //Text and Button
    private String lastRecord;
    private int moreRecord;
    //Card2
    private int subRecord1;

    public RecordData(int mainRecord, String recentRecord, String lastRecord, int moreRecord, int subRecord1) {
        this.mainRecord = mainRecord;
        this.recentRecord = recentRecord;
        this.lastRecord = lastRecord;
        this.moreRecord = moreRecord;
        this.subRecord1 = subRecord1;
    }

    public int getMainRecord() {
        return mainRecord;
    }

    public void setMainRecord(int mainRecord) {
        this.mainRecord = mainRecord;
    }

    public String getRecentRecord() {
        return recentRecord;
    }

    public void setRecentRecord(String recentRecord) {
        this.recentRecord = recentRecord;
    }

    public String getLastRecord() {
        return lastRecord;
    }

    public void setLastRecord(String lastRecord) {
        this.lastRecord = lastRecord;
    }

    public int getMoreRecord() {
        return moreRecord;
    }

    public void setMoreRecord(int moreRecord) {
        this.moreRecord = moreRecord;
    }

    public int getSubRecord1() {
        return subRecord1;
    }

    public void setSubRecord1(int subRecord1) {
        this.subRecord1 = subRecord1;
    }
}
