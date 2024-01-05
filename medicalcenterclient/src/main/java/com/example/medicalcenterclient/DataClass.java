package com.example.medicalcenterclient;

public class DataClass {
    private String dataTimedate;
    private String dataEpf;
    private String dataName;
    private String dataDepartment;
    private String dataDiagnose;
    private String dataMedicine;
    private String dataNote;
    private String dataReported;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataTimedate() {
        return dataTimedate;
    }

    public String getDataEpf() {
        return dataEpf;
    }

    public String getDataName() {
        return dataName;
    }

    public String getDataDepartment() {
        return dataDepartment;
    }

    public String getDataDiagnose() {
        return dataDiagnose;
    }

    public String getDataMedicine() {
        return dataMedicine;
    }

    public String getDataNote() {
        return dataNote;
    }

    public String getDataReported() {
        return dataReported;
    }

    public DataClass(String dataTimedate, String dataEpf, String dataName, String dataDepartment, String dataDiagnose, String dataMedicine, String dataNote, String dataReported) {
        this.dataTimedate = dataTimedate;
        this.dataEpf = dataEpf;
        this.dataName = dataName;
        this.dataDepartment = dataDepartment;
        this.dataDiagnose = dataDiagnose;
        this.dataMedicine = dataMedicine;
        this.dataNote = dataNote;
        this.dataReported = dataReported;
    }

    public DataClass(){

    }
}
