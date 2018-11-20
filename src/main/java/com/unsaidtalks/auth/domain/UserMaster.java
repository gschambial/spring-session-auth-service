package com.unsaidtalks.auth.domain;


import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A UserMaster.
 */
@Entity
@Table(name = "user_master")
public class UserMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
  
    @NotNull
    @Column(name = "jhi_password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @NotNull
    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @NotNull
    @Column(name = "dob", nullable = false)
    private ZonedDateTime dob;

    @NotNull
    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "status")
    private Integer status;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

    @NotNull
    @Column(name = "update_date", nullable = false)
    private ZonedDateTime updateDate;

    @NotNull
    @Column(name = "user_phone", nullable = false)
    private String userPhone;

    @Column(name = "email_auth")
    private Boolean emailAuth;

    @Column(name = "phone_auth")
    private Boolean phoneAuth;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public UserMaster password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserMaster firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserMaster lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public UserMaster userEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public UserMaster countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public ZonedDateTime getDob() {
        return dob;
    }

    public UserMaster dob(ZonedDateTime dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(ZonedDateTime dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public UserMaster gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getStatus() {
        return status;
    }

    public UserMaster status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public UserMaster createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public UserMaster updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public UserMaster userPhone(String userPhone) {
        this.userPhone = userPhone;
        return this;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Boolean isEmailAuth() {
        return emailAuth;
    }

    public UserMaster emailAuth(Boolean emailAuth) {
        this.emailAuth = emailAuth;
        return this;
    }

    public void setEmailAuth(Boolean emailAuth) {
        this.emailAuth = emailAuth;
    }

    public Boolean isPhoneAuth() {
        return phoneAuth;
    }

    public UserMaster phoneAuth(Boolean phoneAuth) {
        this.phoneAuth = phoneAuth;
        return this;
    }

    public void setPhoneAuth(Boolean phoneAuth) {
        this.phoneAuth = phoneAuth;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserMaster userMaster = (UserMaster) o;
        if (userMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserMaster{" +
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
