package com.techvg.hrms.service.dto;


import com.techvg.hrms.config.Constants;
import com.techvg.hrms.domain.TechvgRole;
import com.techvg.hrms.domain.User;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.*;

/**
 * A DTO representing a user, with his authorities.
 */
public class AdminUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
 
    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(min = 1, max = 50)
    private String password;

    private Map<String, List<String>> attributes;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    @Size(min = 2, max = 10)
    private String langKey;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<String> authorities;

    
    private Set<TechvgRole> roles;
    
    private Long companyId;

    private Long employeeId;

    public AdminUserDTO() {
        // Empty constructor needed for Jackson.
    }
   

	public AdminUserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.activated = user.isActivated();
        this.imageUrl = user.getImageUrl();
        this.langKey = user.getLangKey();
        this.createdBy = user.getCreatedBy();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedBy = user.getLastModifiedBy();
        this.lastModifiedDate = user.getLastModifiedDate();
        this.authorities = user.getTechvgRoles().stream().map(TechvgRole::getName).collect(Collectors.toSet());
        this.roles= user.getTechvgRoles();
//        this.roles=user.getTechvgRoles().stream().map(TechvgRole::getName).collect(Collectors.toSet());
        this.companyId = user.getCompanyId();
        this.employeeId = user.getEmployeeId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
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

	//
	 @Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
	        result = prime * result + ((email == null) ? 0 : email.hashCode());
	        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
	        result = prime * result + ((id == null) ? 0 : id.hashCode());
	        result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
	        result = prime * result + ((langKey == null) ? 0 : langKey.hashCode());
	        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
	        result = prime * result + ((login == null) ? 0 : login.hashCode());
	        result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
	        result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
	        result = prime * result + ((roles == null) ? 0 : roles.hashCode());
	        result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
	        result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
	        return result;
	    }
	
//
	
	@Override
	public String toString() {
		return "AdminUserDTO {\"id\":\"" + id + "\", \"login\":\"" + login + "\", \"firstName\":\"" + firstName
				+ "\", \"lastName\":\"" + lastName + "\", \"email\":\"" + email +
				 "\", \"password\":\"" + password +
				 "\", \"attributes\":\"" + attributes +
				"\", \"imageUrl\":\"" + imageUrl
				+ "\", \"activated\":\"" + activated + "\", \"langKey\":\"" + langKey + "\", \"createdBy\":\""
				+ createdBy + "\", \"createdDate\":\"" + createdDate + "\", \"lastModifiedBy\":\"" + lastModifiedBy
				+ "\", \"lastModifiedDate\":\"" + lastModifiedDate + "\", \"authorities\":\"" + authorities
				+", roles=" +
	            roles +
	            "\", \"companyId\":\"" + companyId + "\", \"employeeId\":\"" + employeeId + "}";
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, List<String>> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, List<String>> attributes) {
		this.attributes = attributes;
	}

	public Set<TechvgRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<TechvgRole> roles) {
		this.roles = roles;
	}

}
