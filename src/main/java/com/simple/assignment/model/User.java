package com.simple.assignment.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
@NamedEntityGraph(name = "user-history-graph", attributeNodes = @NamedAttributeNode(value = "userLoginHistories"))
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long userId;

	@NotNull
	@Size(max = 65)
	@Column(name = "first_name")
	private String firstName;

	@NotNull
	@Size(max = 65)
	@Column(name = "last_name")
	private String lastName;

	@OneToMany(fetch = FetchType.EAGER, mappedBy="user", cascade = CascadeType.ALL)
	private Set<UserEmail> emails;

	@JsonIgnore
	@NotNull
	@Size(max = 128)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private Status status;

	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private Role role;

	@Size(max = 100)
	private String city;

	@Column(name = "phone_number")
	@Size(max = 15)
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private Gender gender;

	@Size(max = 100)
	private String address1;

	@Size(max = 100)
	private String address2;

	// @JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UserLoginHistory> userLoginHistories = new ArrayList<UserLoginHistory>();

	private boolean isDeleted;
	private Date createDate;
	private Date updateDate;
	private Date deleteDate;

	public User() {

	}

	public User(@NotNull @Size(max = 65) String firstName, @NotNull @Size(max = 65) String lastName,
			Set<UserEmail> emails, @NotNull @Size(max = 128) String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emails = emails;
		this.password = password;
	}

	public String getLastLogin() {
		Collections.sort(this.userLoginHistories, new UserLoginHistoryComparator());
		UserLoginHistory userLoginHistory = (this.userLoginHistories.size() > 0)
				? userLoginHistories.get(userLoginHistories.size() - 1)
				: null;
		return (userLoginHistory != null)
				? userLoginHistory.getLoginDateTime() + " from IP" + userLoginHistory.getLoginIP()
				: "";
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public Set<UserEmail> getEmails() {
		return emails;
	}

	public void setEmails(Set<UserEmail> emails) {
		this.emails = emails;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<UserLoginHistory> getUserLoginHistories() {
		return userLoginHistories;
	}

	public void setUserLoginHistories(List<UserLoginHistory> userLoginHistories) {
		this.userLoginHistories = userLoginHistories;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

}
