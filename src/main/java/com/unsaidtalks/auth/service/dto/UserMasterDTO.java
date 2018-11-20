package com.unsaidtalks.auth.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the UserMaster entity.
 */
public class UserMasterDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String userEmail;

    @NotNull
    private String countryCode;

    @NotNull
    private ZonedDateTime dob;

    @NotNull
    private String gender;

    private Integer status;

    @NotNull
    private ZonedDateTime createDate;

    @NotNull
    private ZonedDateTime updateDate;

    @NotNull
    private String userPhone;

    private Boolean emailAuth;

    private Boolean phoneAuth;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public ZonedDateTime getDob() {
        return dob;
    }

    public void setDob(ZonedDateTime dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Boolean isEmailAuth() {
        return emailAuth;
    }

    public void setEmailAuth(Boolean emailAuth) {
        this.emailAuth = emailAuth;
    }

    public Boolean isPhoneAuth() {
        return phoneAuth;
    }

    public void setPhoneAuth(Boolean phoneAuth) {
        this.phoneAuth = phoneAuth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserMasterDTO userMasterDTO = (UserMasterDTO) o;
        if (userMasterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userMasterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserMasterDTO{" +
            "id=" + getId() +
            ", password='" + getPassword() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", userEmail='" + getUserEmail() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", dob='" + getDob() + "'" +
            ", gender='" + getGender() + "'" +
            ", status=" + getStatus() +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", userPhone='" + getUserPhone() + "'" +
            ", emailAuth='" + isEmailAuth() + "'" +
            ", phoneAuth='" + isPhoneAuth() + "'" +
            "}";
    }
}
