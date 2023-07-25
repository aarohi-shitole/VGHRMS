package com.techvg.hrms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.techvg.hrms.domain.TechvgRole;
import com.techvg.hrms.domain.User;
import com.techvg.hrms.service.criteria.EmployeeCriteria;
import com.techvg.hrms.service.dto.AdminUserDTO;
import com.techvg.hrms.service.dto.AttendanceDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.UserDTO;

import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.web.util.HeaderUtil;

@Service
public class UserManagment {

	@Value("${user.base-url}")
	private String baseUrl;
	
	@Value("${jhipster.clientApp.name}")
	private String applicationName;
	
	private static final String ENTITY_NAME = "AdminUser";

	@Autowired
	private EmployeeQueryService employeeQueryService;

	@Autowired
	private LoginService loginservice;

	@Autowired
	private UserService userservice;

	private final RestTemplate restTemplate;

	public UserManagment() {
		this.restTemplate = new RestTemplate();
	}

	// user create method using employee details.
	public HttpStatus addUser(AdminUserDTO userDTO) {
		EmployeeDTO empObj = this.checkEmployee(userDTO);
		userDTO.setFirstName(empObj.getFirstName());
		userDTO.setLastName(empObj.getLastName());
		userDTO.setEmail(empObj.getEmailId());
		userDTO.setPassword("welcome1");
		userDTO.setCompanyId(empObj.getCompanyId());
		HashMap<String, List<String>> attributes = new HashMap<>();
		List<String> companyId = new ArrayList<String>(Arrays.asList(userDTO.getCompanyId().toString()));
		List<String> employeeId = new ArrayList<String>(Arrays.asList(userDTO.getEmployeeId().toString()));
		attributes.put("companyId", companyId);
		attributes.put("employeeId", employeeId);
		userDTO.setAttributes(attributes);
		String url = baseUrl + "/add";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AdminUserDTO> requestEntity = new HttpEntity<>(userDTO, headers);
		ResponseEntity<HttpStatus> responseEntity = restTemplate.postForEntity(url, requestEntity, HttpStatus.class);

		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			List<UserRepresentation> userObj = this.getUser(requestEntity.getBody().getEmail());
			if (!userObj.isEmpty()) {
				User user = new User();
				for (UserRepresentation userRepresentation : userObj) {
					Set<TechvgRole> roles = userDTO.getRoles();
					user.setTechvgRoles(roles);
					user.setEmail(userRepresentation.getEmail());
					user.setId(userRepresentation.getId());
					user.setFirstName(userRepresentation.getFirstName());
					user.setLastName(userRepresentation.getLastName());
					user.setLogin(userRepresentation.getEmail());
					user.setCreatedBy(userRepresentation.getEmail());
					user.setActivated(true);
					Map<String, List<String>> attr = userRepresentation.getAttributes();
					Long companyId2 = 0l;
					Long employeeId2 = 0l;
					for (Map.Entry<String, List<String>> entry : attr.entrySet()) {
						String key = entry.getKey();
						List<String> values = entry.getValue();
						System.out.println("Attribute key: " + key);
						for (String value : values) {
							if (key == "companyId") {
								companyId2 = Long.parseLong(value);
							}
							if (key == "employeeId") {
								employeeId2 = Long.parseLong(value);
							}

							System.out.println("Attribute value: " + value);

						}
					}

					user.setEmployeeId(employeeId2);
					user.setCompanyId(companyId2);
					user.setCreatedBy(userRepresentation.getUsername());

				}

				userservice.syncUser(user);
			}
		}
		return responseEntity.getStatusCode();
	}

// check employee details for user creation.
	public EmployeeDTO checkEmployee(AdminUserDTO userDTO) {
		EmployeeDTO empObj = new EmployeeDTO();
		EmployeeCriteria criteria = new EmployeeCriteria();
		LongFilter filter = new LongFilter();
		filter.setEquals(userDTO.getEmployeeId());
		criteria.setId(filter);
		List<EmployeeDTO> filterEmployee = this.employeeQueryService.findByCriteria(criteria);
		if (!filterEmployee.isEmpty()) {
			for (EmployeeDTO employeeDTO : filterEmployee) {
				if (employeeDTO.getFirstName() == null) {
					throw new IllegalArgumentException("Employee first name is missing");
				}
				if (employeeDTO.getLastName() == null) {
					throw new IllegalArgumentException("Employee last name is missing");
				}
				if (employeeDTO.getCompanyId() == null) {
					throw new IllegalArgumentException("Employee company id is missing");
				}
				empObj = employeeDTO;
			}
		} else {
			throw new IllegalArgumentException("Employee is not present");
		}
		return empObj;
	}

	public List<UserRepresentation> getUser(String username) {
		String url = baseUrl + "/" + username;
		ResponseEntity<List<UserRepresentation>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<UserRepresentation>>() {
				});
		return responseEntity.getBody();
	}

	public UserRepresentation updateUser(String userId, AdminUserDTO userDTO) {
		String url = baseUrl + "/update/" + userId;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AdminUserDTO> requestEntity = new HttpEntity<>(userDTO, headers);
		ResponseEntity<UserRepresentation> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity,
				UserRepresentation.class);
		return responseEntity.getBody();
	}

	public HttpStatus deleteUser(String userId) {
		String url = baseUrl + "/delete/" + userId;
		ResponseEntity<HttpStatus> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null,
				HttpStatus.class);
		return responseEntity.getStatusCode();
	}

	public HttpStatus sendVerificationLink(String userId) {
		String url = baseUrl + "/verification-link/" + userId;
		ResponseEntity<HttpStatus> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, HttpStatus.class);
		return responseEntity.getStatusCode();
	}

	public HttpStatus sendResetPassword(String userId) {
		String url = baseUrl + "/reset-password/" + userId;
		ResponseEntity<HttpStatus> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, HttpStatus.class);
		return responseEntity.getStatusCode();
	}

	public ResponseEntity<AdminUserDTO> inActiveUser(String userId, AdminUserDTO adminUserDTO) {
		AdminUserDTO result1 = userservice.save(adminUserDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminUserDTO.getId().toString()))
				.body(result1);
	}

}