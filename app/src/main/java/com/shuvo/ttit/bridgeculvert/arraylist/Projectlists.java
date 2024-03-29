package com.shuvo.ttit.bridgeculvert.arraylist;

import java.util.ArrayList;

public class Projectlists {

    private String pcmId;
    private String pcmEntryDate;
    private String pcmInternalNo;
    private String pcmProjectCode;
    private String pcmUser;
    private String pcmProjectName;
    private String pcmProjectNo;
    private String pcmProjectDate;
    private String pcmPicChairmanName;
    private String pcmPicChairmanDetails;
    private String pcmEstimateProjectValue;
    private String fyFinancialYearName;
    private String fsmFundName;
    private String projectTypeName;
    private String projectSubTypeName;
    private String PscSanctionCatName;
    private String pcmCategoryName;
    private String dduId;
    private String ddId;
    private String projEvaluationRem;
    private String pcmgdTypeFlag;
    private String projectDetails;
    private String projectStartDate;
    private String projectEndDate;
    private String sanctionType;
    private String length;
    private String width;
    private boolean mapData;
    private boolean imageData;
    private String rowNumber;
    private String count;
    private ArrayList<LocationLists> locationLists;

    public Projectlists(String pcmId, String pcmEntryDate, String pcmInternalNo, String pcmProjectCode, String pcmUser, String pcmProjectName, String pcmProjectNo, String pcmProjectDate, String pcmPicChairmanName, String pcmPicChairmanDetails, String pcmEstimateProjectValue, String fyFinancialYearName, String fsmFundName, String projectTypeName, String projectSubTypeName, String pscSanctionCatName, String pcmCategoryName, String dduId, String ddId, String projEvaluationRem, String pcmgdTypeFlag, String projectDetails, String projectStartDate, String projectEndDate,String sanctionType, String length, String width, boolean mapData, boolean imageData, String rowNumber, String count, ArrayList<LocationLists> locationLists) {
        this.pcmId = pcmId;
        this.pcmEntryDate = pcmEntryDate;
        this.pcmInternalNo = pcmInternalNo;
        this.pcmProjectCode = pcmProjectCode;
        this.pcmUser = pcmUser;
        this.pcmProjectName = pcmProjectName;
        this.pcmProjectNo = pcmProjectNo;
        this.pcmProjectDate = pcmProjectDate;
        this.pcmPicChairmanName = pcmPicChairmanName;
        this.pcmPicChairmanDetails = pcmPicChairmanDetails;
        this.pcmEstimateProjectValue = pcmEstimateProjectValue;
        this.fyFinancialYearName = fyFinancialYearName;
        this.fsmFundName = fsmFundName;
        this.projectTypeName = projectTypeName;
        this.projectSubTypeName = projectSubTypeName;
        PscSanctionCatName = pscSanctionCatName;
        this.pcmCategoryName = pcmCategoryName;
        this.dduId = dduId;
        this.ddId = ddId;
        this.projEvaluationRem = projEvaluationRem;
        this.pcmgdTypeFlag = pcmgdTypeFlag;
        this.projectDetails = projectDetails;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.sanctionType = sanctionType;
        this.length = length;
        this.width = width;
        this.mapData = mapData;
        this.imageData = imageData;
        this.rowNumber = rowNumber;
        this.count = count;
        this.locationLists = locationLists;
    }

    public String getPcmId() {
        return pcmId;
    }

    public void setPcmId(String pcmId) {
        this.pcmId = pcmId;
    }

    public String getPcmEntryDate() {
        return pcmEntryDate;
    }

    public void setPcmEntryDate(String pcmEntryDate) {
        this.pcmEntryDate = pcmEntryDate;
    }

    public String getPcmInternalNo() {
        return pcmInternalNo;
    }

    public void setPcmInternalNo(String pcmInternalNo) {
        this.pcmInternalNo = pcmInternalNo;
    }

    public String getPcmProjectCode() {
        return pcmProjectCode;
    }

    public void setPcmProjectCode(String pcmProjectCode) {
        this.pcmProjectCode = pcmProjectCode;
    }

    public String getPcmUser() {
        return pcmUser;
    }

    public void setPcmUser(String pcmUser) {
        this.pcmUser = pcmUser;
    }

    public String getPcmProjectName() {
        return pcmProjectName;
    }

    public void setPcmProjectName(String pcmProjectName) {
        this.pcmProjectName = pcmProjectName;
    }

    public String getPcmProjectNo() {
        return pcmProjectNo;
    }

    public void setPcmProjectNo(String pcmProjectNo) {
        this.pcmProjectNo = pcmProjectNo;
    }

    public String getPcmProjectDate() {
        return pcmProjectDate;
    }

    public void setPcmProjectDate(String pcmProjectDate) {
        this.pcmProjectDate = pcmProjectDate;
    }

    public String getPcmPicChairmanName() {
        return pcmPicChairmanName;
    }

    public void setPcmPicChairmanName(String pcmPicChairmanName) {
        this.pcmPicChairmanName = pcmPicChairmanName;
    }

    public String getPcmPicChairmanDetails() {
        return pcmPicChairmanDetails;
    }

    public void setPcmPicChairmanDetails(String pcmPicChairmanDetails) {
        this.pcmPicChairmanDetails = pcmPicChairmanDetails;
    }

    public String getPcmEstimateProjectValue() {
        return pcmEstimateProjectValue;
    }

    public void setPcmEstimateProjectValue(String pcmEstimateProjectValue) {
        this.pcmEstimateProjectValue = pcmEstimateProjectValue;
    }

    public String getFyFinancialYearName() {
        return fyFinancialYearName;
    }

    public void setFyFinancialYearName(String fyFinancialYearName) {
        this.fyFinancialYearName = fyFinancialYearName;
    }

    public String getFsmFundName() {
        return fsmFundName;
    }

    public void setFsmFundName(String fsmFundName) {
        this.fsmFundName = fsmFundName;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    public String getProjectSubTypeName() {
        return projectSubTypeName;
    }

    public void setProjectSubTypeName(String projectSubTypeName) {
        this.projectSubTypeName = projectSubTypeName;
    }

    public String getPscSanctionCatName() {
        return PscSanctionCatName;
    }

    public void setPscSanctionCatName(String pscSanctionCatName) {
        PscSanctionCatName = pscSanctionCatName;
    }

    public String getPcmCategoryName() {
        return pcmCategoryName;
    }

    public void setPcmCategoryName(String pcmCategoryName) {
        this.pcmCategoryName = pcmCategoryName;
    }

    public String getDduId() {
        return dduId;
    }

    public void setDduId(String dduId) {
        this.dduId = dduId;
    }

    public String getDdId() {
        return ddId;
    }

    public void setDdId(String ddId) {
        this.ddId = ddId;
    }

    public String getProjEvaluationRem() {
        return projEvaluationRem;
    }

    public void setProjEvaluationRem(String projEvaluationRem) {
        this.projEvaluationRem = projEvaluationRem;
    }

    public String getPcmgdTypeFlag() {
        return pcmgdTypeFlag;
    }

    public void setPcmgdTypeFlag(String pcmgdTypeFlag) {
        this.pcmgdTypeFlag = pcmgdTypeFlag;
    }

    public String getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(String projectDetails) {
        this.projectDetails = projectDetails;
    }

    public String getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(String projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public String getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(String projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    public String getSanctionType() {
        return sanctionType;
    }

    public void setSanctionType(String sanctionType) {
        this.sanctionType = sanctionType;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public ArrayList<LocationLists> getLocationLists() {
        return locationLists;
    }

    public void setLocationLists(ArrayList<LocationLists> locationLists) {
        this.locationLists = locationLists;
    }

    public boolean isMapData() {
        return mapData;
    }

    public void setMapData(boolean mapData) {
        this.mapData = mapData;
    }

    public boolean isImageData() {
        return imageData;
    }

    public void setImageData(boolean imageData) {
        this.imageData = imageData;
    }
}
