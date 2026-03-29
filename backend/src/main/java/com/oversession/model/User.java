package com.oversession.model;

import java.util.Objects;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

/**
 * Userエンティティ - frontend types.tsのUser interfaceに対応
 */
@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "users")
public class User {
    @Id
    private String userId;
    private String userName;
    private String primaryHeadOfficeName;
    private String secondaryHeadOfficeName;
    private String departmentName;
    private Integer officeId;
    private Integer floor;
    private String gender;
    private Integer affiliationYear;
    private String workingStatus; // "出社" | "不在"
    private String matchingUserId;
    private String pictureName;
    private boolean deleted;

    public User() {
        this.deleted = false;
        this.workingStatus = "不在";
    }

    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.deleted = false;
        this.workingStatus = "不在";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPrimaryHeadOfficeName() {
        return primaryHeadOfficeName;
    }

    public void setPrimaryHeadOfficeName(String primaryHeadOfficeName) {
        this.primaryHeadOfficeName = primaryHeadOfficeName;
    }

    public String getSecondaryHeadOfficeName() {
        return secondaryHeadOfficeName;
    }

    public void setSecondaryHeadOfficeName(String secondaryHeadOfficeName) {
        this.secondaryHeadOfficeName = secondaryHeadOfficeName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAffiliationYear() {
        return affiliationYear;
    }

    public void setAffiliationYear(Integer affiliationYear) {
        this.affiliationYear = affiliationYear;
    }

    public String getWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(String workingStatus) {
        this.workingStatus = workingStatus;
    }

    public String getMatchingUserId() {
        return matchingUserId;
    }

    public void setMatchingUserId(String matchingUserId) {
        this.matchingUserId = matchingUserId;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", workingStatus='" + workingStatus + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}