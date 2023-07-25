package com.techvg.hrms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.hrms.config.Constants;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A user.
 */
@Entity
@Table(name = "techvg_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@SQLDelete(sql = "UPDATE techvg_user SET status='D' WHERE id=?")
//@Where(clause = "status != 'D'")
public class User extends AbstractAuditingEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    private String id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;


    @Column(name = "company_id")
    private Long companyId;


    @Column(name = "employee_id")
    private Long employeeId;
    
    
    @ManyToMany
    @JoinTable(
        name = "rel_techvg_user__techvg_role",
        joinColumns = @JoinColumn(name = "techvg_user_id"),
        inverseJoinColumns = @JoinColumn(name = "techvg_role_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "techvgPermissions", "users" }, allowSetters = true)
    private Set<TechvgRole> techvgRoles = new HashSet<>();
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    // Lowercase the login before saving it in database
    public void setLogin(String login) {
        this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	 
	    public Set<TechvgRole> getTechvgRoles() {
	        return this.techvgRoles;
	    }

	    public void setTechvgRoles(Set<TechvgRole> techvgRoles) {
	        this.techvgRoles = techvgRoles;
	    }

	    public User techvgRoles(Set<TechvgRole> techvgRoles) {
	        this.setTechvgRoles(techvgRoles);
	        return this;
	    }

	    public User addTechvgRole(TechvgRole techvgRole) {
	        this.techvgRoles.add(techvgRole);
	        techvgRole.getUsers().add(this);
	        return this;
	    }

	    public User removeTechvgRole(TechvgRole techvgRole) {
	        this.techvgRoles.remove(techvgRole);
	        techvgRole.getUsers().remove(this);
	        return this;
	    }

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
	public String toString() {
		return "User {\"id\":\"" + id + "\", \"login\":\"" + login + "\", \"firstName\":\"" + firstName
				+ "\", \"lastName\":\"" + lastName + "\", \"email\":\"" + email + "\", \"activated\":\"" + activated
				+ "\", \"langKey\":\"" + langKey + "\", \"imageUrl\":\"" + imageUrl + "\", \"companyId\":\"" + companyId
				+ "\", \"employeeId\":\"" + "}";
	}
}
