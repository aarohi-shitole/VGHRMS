package com.techvg.hrms.service.criteria;

import java.io.Serializable;

import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Address} entity. This
 * class is used in {@link com.techvg.hrms.web.rest.AddressResource} to receive
 * all the possible filtering options from the Http GET request parameters. For
 * example the following could be a valid request:
 * {@code /addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific
 * {@link Filter} class are used, we need to use fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdminUserCriteria implements Serializable, Criteria {

	private static final long serialVersionUID = 1L;

	private StringFilter id;

	private StringFilter login;

	private StringFilter firstName;

	private StringFilter lastName;

	private StringFilter email;

	private StringFilter password;

	private StringFilter imageUrl;

	private StringFilter langKey;

	private BooleanFilter activated;

	private StringFilter createdBy;

	private LongFilter employeeId;

	private LongFilter companyId;

	private StringFilter status;

	private InstantFilter createdDate;

	private InstantFilter lastModified;

	private StringFilter lastModifiedBy;

	private Boolean distinct;

	public AdminUserCriteria() {
	}

	public AdminUserCriteria(AdminUserCriteria other) {
		this.id = other.id == null ? null : other.id.copy();
		this.login = other.login == null ? null : other.login.copy();
		this.firstName = other.firstName == null ? null : other.firstName.copy();
		this.lastName = other.lastName == null ? null : other.lastName.copy();
		this.email = other.email == null ? null : other.email.copy();
		this.password = other.password == null ? null : other.password.copy();
		this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
		this.langKey = other.langKey == null ? null : other.langKey.copy();
		this.activated = other.activated == null ? null : other.activated.copy();
		this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
		this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
		this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
		this.companyId = other.companyId == null ? null : other.companyId.copy();
		this.status = other.status == null ? null : other.status.copy();
		this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
		this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
		this.distinct = other.distinct;
	}

	@Override
	public AdminUserCriteria copy() {
		return new AdminUserCriteria(this);
	}

	public StringFilter getId() {
		return id;
	}

	public StringFilter id() {
		if (id == null) {
			id = new StringFilter();
		}
		return id;
	}

	public void setId(StringFilter id) {
		this.id = id;
	}

	public StringFilter getLogin() {
		return login;
	}

	public StringFilter login() {
		if (login == null) {
			login = new StringFilter();
		}
		return login;
	}

	public void setLogin(StringFilter login) {
		this.login = login;
	}

	public StringFilter getFirstName() {
		return firstName;
	}

	public StringFilter firstName() {
		if (firstName == null) {
			firstName = new StringFilter();
		}
		return firstName;
	}

	public void setFirstName(StringFilter firstName) {
		this.firstName = firstName;
	}

	public StringFilter getLastName() {
		return lastName;
	}

	public StringFilter lastName() {
		if (lastName == null) {
			lastName = new StringFilter();
		}
		return lastName;
	}

	public void setLastName(StringFilter lastName) {
		this.lastName = lastName;
	}

	public StringFilter getEmail() {
		return email;
	}

	public StringFilter email() {
		if (email == null) {
			email = new StringFilter();
		}
		return email;
	}

	public void setEmail(StringFilter email) {
		this.email = email;
	}

	public StringFilter getPassword() {
		return password;
	}

	public StringFilter password() {
		if (password == null) {
			password = new StringFilter();
		}
		return password;
	}

	public void setPassword(StringFilter password) {
		this.password = password;
	}

	public StringFilter getImageUrl() {
		return imageUrl;
	}

	public StringFilter imageUrl() {
		if (imageUrl == null) {
			imageUrl = new StringFilter();
		}
		return imageUrl;
	}

	public void setImageUrl(StringFilter imageUrl) {
		this.imageUrl = imageUrl;
	}

	public StringFilter getLangKey() {
		return langKey;
	}

	public StringFilter langKey() {
		if (langKey == null) {
			langKey = new StringFilter();
		}
		return langKey;
	}

	public void setLangKey(StringFilter langKey) {
		this.langKey = langKey;
	}

	public BooleanFilter getActivated() {
		return activated;
	}

	public BooleanFilter activated() {
		if (activated == null) {
			activated = new BooleanFilter();
		}
		return activated;
	}

	public void setActivatedd(BooleanFilter activated) {
		this.activated = activated;
	}

	public StringFilter getCreatedBy() {
		return createdBy;
	}

	public StringFilter createdBy() {
		if (createdBy == null) {
			createdBy = new StringFilter();
		}
		return createdBy;
	}

	public void setCreatedBy(StringFilter createdBy) {
		this.createdBy = createdBy;
	}

	public LongFilter getEmployeeId() {
		return employeeId;
	}

	public LongFilter employeeId() {
		if (employeeId == null) {
			employeeId = new LongFilter();
		}
		return employeeId;
	}

	public void setEmployeeId(LongFilter employeeId) {
		this.employeeId = employeeId;
	}

	public LongFilter getCompanyId() {
		return companyId;
	}

	public LongFilter companyId() {
		if (companyId == null) {
			companyId = new LongFilter();
		}
		return companyId;
	}

	public void setCompanyId(LongFilter companyId) {
		this.companyId = companyId;
	}

	public StringFilter getStatus() {
		return status;
	}

	public StringFilter status() {
		if (status == null) {
			status = new StringFilter();
		}
		return status;
	}

	public void setStatus(StringFilter status) {
		this.status = status;
	}

	public InstantFilter getLastModified() {
		return lastModified;
	}

	public InstantFilter lastModified() {
		if (lastModified == null) {
			lastModified = new InstantFilter();
		}
		return lastModified;
	}

	public void setLastModified(InstantFilter lastModified) {
		this.lastModified = lastModified;
	}

	public StringFilter getLastModifiedBy() {
		return lastModifiedBy;
	}

	public StringFilter lastModifiedBy() {
		if (lastModifiedBy == null) {
			lastModifiedBy = new StringFilter();
		}
		return lastModifiedBy;
	}

	public void setLastModifiedBy(StringFilter lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Boolean getDistinct() {
		return distinct;
	}

	public void setDistinct(Boolean distinct) {
		this.distinct = distinct;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final AdminUserCriteria that = (AdminUserCriteria) o;
		return (Objects.equals(id, that.id) && Objects.equals(login, that.login)
				&& Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName)
				&& Objects.equals(email, that.email) && Objects.equals(password, that.password)
				&& Objects.equals(imageUrl, that.imageUrl) && Objects.equals(langKey, that.langKey)
				&& Objects.equals(activated, that.activated) && Objects.equals(createdBy, that.createdBy)
				&& Objects.equals(employeeId, that.employeeId) && Objects.equals(createdDate, that.createdDate)
				&& Objects.equals(companyId, that.companyId) && Objects.equals(status, that.status)
				&& Objects.equals(lastModified, that.lastModified)
				&& Objects.equals(lastModifiedBy, that.lastModifiedBy) && Objects.equals(distinct, that.distinct));
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, firstName, lastName, email, password, imageUrl, langKey, activated, createdBy,
				employeeId, createdDate, companyId, status, lastModified, lastModifiedBy, distinct);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "AdminUserCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (login != null ? "login=" + login + ", " : "")
				+ (firstName != null ? "firstName=" + firstName + ", " : "")
				+ (lastName != null ? "lastName=" + lastName + ", " : "")
				+ (email != null ? "email=" + email + ", " : "")
				+ (password != null ? "password=" + password + ", " : "")
				+ (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "")
				+ (langKey != null ? "langKey=" + langKey + ", " : "")
				+ (activated != null ? "activated=" + activated + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (employeeId != null ? "employeeId=" + employeeId + ", " : "")
				+ (createdDate != null ? "createdDate=" + createdDate + ", " : "")
				+ (companyId != null ? "companyId=" + companyId + ", " : "")
				+ (status != null ? "status=" + status + ", " : "")
				+ (lastModified != null ? "lastModified=" + lastModified + ", " : "")
				+ (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "")
				+ (distinct != null ? "distinct=" + distinct + ", " : "") + "}";
	}
}
