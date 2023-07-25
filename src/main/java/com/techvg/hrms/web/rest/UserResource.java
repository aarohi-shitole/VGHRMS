package com.techvg.hrms.web.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.hrms.service.UserManagment;
import com.techvg.hrms.service.UserQueryService;
import com.techvg.hrms.service.UserService;
import com.techvg.hrms.service.criteria.AdminUserCriteria;
import com.techvg.hrms.service.criteria.TimeSheetCriteria;
import com.techvg.hrms.service.dto.AdminUserDTO;
import com.techvg.hrms.service.dto.TimeSheetDTO;
import com.techvg.hrms.service.dto.UserDTO;

import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.StringFilter;

@RestController
@RequestMapping("/api")
public class UserResource {

	@Autowired
	private UserManagment userManagement;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserQueryService userQueryService;
	
	@PostMapping("/user/add")
	public ResponseEntity<HttpStatus> addUser(@RequestBody AdminUserDTO userDTO, HttpServletRequest request) {
		HttpStatus status = userManagement.addUser(userDTO);
		return new ResponseEntity<>(status);
	}

	@GetMapping("/user/{username}")
	public ResponseEntity<List<UserRepresentation>> getUser(@PathVariable String username) {
		List<UserRepresentation> users = userManagement.getUser(username);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PutMapping("/user/update/{userId}")
	public ResponseEntity<UserRepresentation> updateUser(@PathVariable String userId, @RequestBody AdminUserDTO userDTO) {
		UserRepresentation user = userManagement.updateUser(userId, userDTO);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@DeleteMapping("/user/delete/{userId}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable String userId) {
		HttpStatus status = userManagement.deleteUser(userId);
		Pageable pageable = PageRequest.of(0, 5);

		StringFilter stringFilter = new StringFilter();
		AdminUserCriteria criteria = new AdminUserCriteria();
		stringFilter.setEquals(userId);
		criteria.setId(stringFilter);
		Page<AdminUserDTO> page = userQueryService.findByCriteria(criteria, pageable);
		List<AdminUserDTO> list = page.getContent();
		System.out.println("list : "+list);
		if (!list.isEmpty()) {
			for (AdminUserDTO adminUserDTO : list) {
				adminUserDTO.setActivated(false);
				System.out.println("adminUserDTO : "+adminUserDTO);
				  ResponseEntity<AdminUserDTO> user = userManagement.inActiveUser(userId, adminUserDTO);
			}
		}
//		UserRepresentation user = userManagement.updateUser(userId, userDTO);
//		userService.delete(userId);
		return new ResponseEntity<>(status);
	}

	@GetMapping("/user/verification-link/{userId}")
	public ResponseEntity<HttpStatus> sendVerificationLink(@PathVariable String userId) {
		HttpStatus status = userManagement.sendVerificationLink(userId);
		return new ResponseEntity<>(status);
	}

	@GetMapping("/user/reset-password/{userId}")
	public ResponseEntity<HttpStatus> sendResetPassword(@PathVariable String userId) {
		HttpStatus status = userManagement.sendResetPassword(userId);
		return new ResponseEntity<>(status);
	}
}
