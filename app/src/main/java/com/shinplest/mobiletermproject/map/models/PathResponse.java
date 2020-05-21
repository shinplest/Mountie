package com.shinplest.mobiletermproject.map.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PathResponse {
    @SerializedName("response")
    Response ResponseObject;

    // Getter Methods
    public Response getResponse() {
        return ResponseObject;
    }

    // Setter Methods
    public void setResponse(Response responseObject) {
        this.ResponseObject = responseObject;
    }

    public class Response {
        @SerializedName("service")
        Service ServiceObject;
        @SerializedName("status")
        String status;
        @SerializedName("record")
        Record RecordObject;
        @SerializedName("page")
        Page PageObject;
        @SerializedName("result")
        Result ResultObject;

        // Getter Methods
        public Service getService() {
            return ServiceObject;
        }

        public String getStatus() {
            return status;
        }

        public Record getRecord() {
            return RecordObject;
        }

        public Page getPage() {
            return PageObject;
        }

        public Result getResult() {
            return ResultObject;
        }

        // Setter Methods
        public void setService(Service serviceObject) {
            this.ServiceObject = serviceObject;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setRecord(Record recordObject) {
            this.RecordObject = recordObject;
        }

        public void setPage(Page pageObject) {
            this.PageObject = pageObject;
        }

        public void setResult(Result resultObject) {
            this.ResultObject = resultObject;
        }
    }

    public class Result {
        @SerializedName("FeatureCollection")
        FeatureCollection FeatureCollectionObject;

        public FeatureCollection getFeatureCollection() {
            return FeatureCollectionObject;
        }

        public void setFeatureCollection(FeatureCollection featureCollectionObject) {
            this.FeatureCollectionObject = featureCollectionObject;
        }
    }

    public class FeatureCollection {
        @SerializedName("type")
        String type;
        @SerializedName("features")
        ArrayList<Object> features = new ArrayList<Object>();

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ArrayList<Object> getFeatures() {
            return features;
        }
    }

    public class Page {
        @SerializedName("type")

        private String total;
        @SerializedName("type")

        private String current;
        @SerializedName("type")

        private String size;
        // Getter Methods

        public String getTotal() {
            return total;
        }

        public String getCurrent() {
            return current;
        }

        public String getSize() {
            return size;
        }

        // Setter Methods

        public void setTotal(String total) {
            this.total = total;
        }

        public void setCurrent(String current) {
            this.current = current;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }

    public class Record {
        @SerializedName("type")

        private String total;
        @SerializedName("type")

        private String current;


        // Getter Methods

        public String getTotal() {
            return total;
        }

        public String getCurrent() {
            return current;
        }

        // Setter Methods

        public void setTotal(String total) {
            this.total = total;
        }

        public void setCurrent(String current) {
            this.current = current;
        }
    }

    public class Service {
        @SerializedName("type")

        private String name;
        @SerializedName("type")

        private String version;
        @SerializedName("type")

        private String operation;
        @SerializedName("type")

        private String time;


        // Getter Methods

        public String getName() {
            return name;
        }

        public String getVersion() {
            return version;
        }

        public String getOperation() {
            return operation;
        }

        public String getTime() {
            return time;
        }

        // Setter Methods

        public void setName(String name) {
            this.name = name;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public void setTime(String time) {
            this.time = time;
        }

    }
}