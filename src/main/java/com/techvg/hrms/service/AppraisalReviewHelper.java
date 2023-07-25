package com.techvg.hrms.service;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.techvg.hrms.service.criteria.AdminUserCriteria;
import com.techvg.hrms.service.criteria.AppraisalReviewCriteria;
import com.techvg.hrms.service.criteria.ApprovalCriteria;
import com.techvg.hrms.service.criteria.ApprovalLevelCriteria;
import com.techvg.hrms.service.criteria.ApprovalSettingCriteria;
import com.techvg.hrms.service.criteria.EmployeeCriteria;
import com.techvg.hrms.service.criteria.PerformanceReviewCriteria;
import com.techvg.hrms.service.dto.AdminUserDTO;
import com.techvg.hrms.service.dto.AppraisalReviewDTO;
import com.techvg.hrms.service.dto.ApprovalDTO;
import com.techvg.hrms.service.dto.ApprovalLevelDTO;
import com.techvg.hrms.service.dto.ApprovalSettingDTO;
import com.techvg.hrms.service.dto.DepartmentDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.PerformanceReviewDTO;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;

import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Service
public class AppraisalReviewHelper {

	private static final Logger log = LoggerFactory.getLogger(ResignationHelper.class);

	private static final String ENTITY_NAME = "appraisalReviewHelper";

	// For findApprovarList method
	@Autowired
	public EmployeeService employeeService;

	@Autowired
	public DepartmentService departmentService;

	@Autowired
	public AppraisalReviewService appraisalReviewService;

	@Autowired
	public ApprovalSettingQueryService approvalSettingQueryService;

	@Autowired
	public ApprovalLevelQueryService approvalLevelQueryService;

	@Autowired
	public ApprovalService approvalService;

	@Autowired
	public EmployeeQueryService employeeQueryService;

	@Autowired
	public UserQueryService userQueryService;

	@Autowired
	public ApprovalQueryService approvalQueryService;

	@Autowired
	private AppraisalReviewQueryService appraisalReviewQueryService;

	public HashMap<Long, Long> findApprovarList(Long employeId, Long approvarEmpId) {
		HashMap<Long, Long> approvarListMap = null;

		// Find the approvar department
		Optional<EmployeeDTO> employee = employeeService.findOne(approvarEmpId);
		if (!employee.isPresent()) {
			throw new BadRequestAlertException("given approvar employee_id should not found", ENTITY_NAME,
					"employeeNotExists");
		}
		long approvarDeptId = employee.get().getDepartment().getId();

		Optional<DepartmentDTO> departmentDTO = departmentService.findOne(approvarDeptId);
		long approvarCompId = departmentDTO.get().getCompanyId();

		// Checking approvarEmpId is real approvar or not
		// HashMap<Integer,Integer> approvarListMap=new HashMap<Integer,Integer>();

		// To Find the approvalsetting id
		long approvalSettingId = 0;
		ApprovalSettingCriteria approvalSettingCriteria = new ApprovalSettingCriteria();

		LongFilter companyId = new LongFilter();
		companyId.setEquals(approvarCompId);
		approvalSettingCriteria.setCompanyId(companyId);

		StringFilter type = new StringFilter();
		type.setEquals("Appraisal Approval");
		approvalSettingCriteria.setType(type);

		List<ApprovalSettingDTO> approvalSettingList = approvalSettingQueryService
				.findByCriteria(approvalSettingCriteria);
		if (approvalSettingList != null) {
			for (ApprovalSettingDTO approvalSetting : approvalSettingList) {
				approvalSettingId = approvalSetting.getId();

				approvarListMap = getApprovarsList(approvalSettingId, approvarCompId, employeId);
				log.debug("!!!!request to get approvarListMap1: " + approvarListMap.toString());
				// approvarSortedMap = getSortedHashMap(approvarListMap);
				// log.debug("!!!!request to get after sort approvarSortedMap: " +
				// approvarSortedMap.toString());
			}
		}
		return approvarListMap;
	}

	public LinkedHashMap<Long, Long> getSortedMap(HashMap<Long, Long> approvarListMap) {
		List<Map.Entry<Long, Long>> list = new ArrayList<>(approvarListMap.entrySet());

		// Sort the list by values using a Comparator
		Collections.sort(list, new Comparator<Map.Entry<Long, Long>>() {
			public int compare(Map.Entry<Long, Long> o1, Map.Entry<Long, Long> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});

		// Create a new LinkedHashMap to preserve the order of insertion
		LinkedHashMap<Long, Long> sortedMap = new LinkedHashMap<>();
		for (Map.Entry<Long, Long> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	public boolean checkApprovarPresent(Long approvarEmpId, HashMap<Long, Long> approvarListMap1) {
		boolean approvarPresent = checkPassedApprovarEmpIdPresentInMap(approvarEmpId, approvarListMap1);
		log.debug("approvarPresent or not:" + approvarPresent);
		return approvarPresent;
	}

	private boolean checkPassedApprovarEmpIdPresentInMap(Long approvarEmpId, HashMap<Long, Long> approvarListMap1) {
		if (approvarListMap1.containsKey(approvarEmpId))
			return true;
		else
			return false;
	}

	private void getApprovarsList1(long approvalSettingId, long companyId, long applierEmpId,
			long appraisalReviewId) {
		// To Find approval levels for the approval setting
		ApprovalLevelCriteria approvalLevelCriteria = new ApprovalLevelCriteria();

		LongFilter approvalSettingId1 = new LongFilter();
		approvalSettingId1.setEquals(approvalSettingId);
		approvalLevelCriteria.setApprovalSettingId(approvalSettingId1);

		LongFilter compId = new LongFilter();
		compId.setEquals(companyId);
		approvalLevelCriteria.setCompanyId(compId);

		StringFilter status = new StringFilter();
		status.setEquals("A");
		approvalLevelCriteria.setStatus(status);

		List<ApprovalLevelDTO> approvalLevelList = approvalLevelQueryService.findByCriteria(approvalLevelCriteria);
		if (approvalLevelList != null) {
			// To add the approvalLevel employeeId and sequence in approvarListMap
			Map<Long, Long> approvarListMap = new HashMap<Long, Long>();

			for (ApprovalLevelDTO approvalLevel : approvalLevelList) {
				log.debug("Rest request to get approvalLevel:" + approvalLevel.toString());
				// HashMap<Long, Long> approvarListMap = new HashMap<Long, Long>();

				long sequence = approvalLevel.getSquence();
				long designationId = approvalLevel.getDesignationId();

				// Find the employee Id's for designationId,companyId which can be approvar's
				EmployeeCriteria employeeCriteria = new EmployeeCriteria();
				LongFilter designationId1 = new LongFilter();
				designationId1.setEquals(designationId);
				employeeCriteria.setDesignationId(designationId1);
				employeeCriteria.setCompanyId(compId);
				employeeCriteria.setStatus(status);

				// long seq;
				List<EmployeeDTO> employeeList = employeeQueryService.findByCriteria(employeeCriteria);
				if (employeeList != null) {
					for (EmployeeDTO employee : employeeList) {
						long empId = employee.getId();
//						approvarListMap.put(empId, sequence);

						// approvarListMap.put(empId, sequence);

						// If is applierEmpId occured in approvals,Code is added to skip that emp having
						// same applierEmpId designation
						Optional<EmployeeDTO> emp = employeeService.findOne(applierEmpId);
						if (!(applierEmpId == empId)
								&& !(emp.get().getDesignation().getId() == employee.getDesignation().getId())) {
							approvarListMap.put(empId, sequence);
						}
						// Code added to remove all the emp having less sequence than applierEmpId
						long seq = sequence;
						if (applierEmpId == empId && sequence >= 1) {
							while (seq != 0) {
								Map<Long, Long> approvarChildMap = new HashMap<Long, Long>();
								for (Map.Entry<Long, Long> entry : approvarListMap.entrySet()) {
									log.debug("In for ChildMap entry: " + entry);
									if (!entry.getValue().equals(seq)) {
										log.debug("Childmap and approvarListMap value not matched for value: "
												+ entry.getValue());
										approvarChildMap.put(entry.getKey(), entry.getValue());
									}
								}

								approvarListMap = approvarChildMap;
								seq = seq - 1;
							}
						}

						//
					}
					log.debug("Approvar Map with Emp id and sequence:" + approvarListMap.toString());
				}
				// log.debug("!!!!!!!!!!passed applierEmpId:" + applierEmpId);
				// long empId = applierEmpId;
				// applierEmpId = findNextReportingEmpId(applierEmpId);
				// log.debug("!!!!!!!!!!after findNextReportingEmpId applierEmpId(It should be
				// next):" + applierEmpId);
				//
				// if (approvarListMap.containsKey(empId)) {
				// seq = approvarListMap.get(empId);
				// log.debug("!!!!!!sequence from approvalLevel sequence:" + sequence +
				// "!!!!!!sequence from map key(empId) seq:" + seq);
				// LeaveApplicationApprovalDTO leaveApplicationApproval = new
				// LeaveApplicationApprovalDTO();
				// leaveApplicationApproval.setCompanyId(companyId);
				// leaveApplicationApproval.setApproverEmpId(applierEmpId);
				// leaveApplicationApproval.setLeaveApplicationId(leaveApplicationId);
				// leaveApplicationApproval.setSequence(seq);
				// leaveApplicationApproval.setStatus("A");
				// leaveApplicationApprovalService.save(leaveApplicationApproval);
				// }

			}

			while (!approvarListMap.isEmpty()) {
				log.debug("!!!!!!!!!!passed previous applierEmpId:" + applierEmpId);
				long empId = applierEmpId;
				applierEmpId = findNextReportingEmpId(applierEmpId);
				if (applierEmpId == 0) {
					approvarListMap.clear();
				}

				log.debug("!!!!!!!!!!after findNextReportingEmpId applierEmpId(designation should present in level):"
						+ applierEmpId);

				long seq = 0;
				if (approvarListMap.containsKey(applierEmpId)) {
					seq = approvarListMap.get(applierEmpId);
					log.debug("!!!!!!sequence from map key(empId) seq:" + applierEmpId);

					ApprovalDTO approval = new ApprovalDTO();
					approval.setCompanyId(companyId);
					approval.setApproverEmployeeId(applierEmpId);
					approval.setRefTable("AppraisalReview");
					approval.setRefTableId(appraisalReviewId);
					approval.setSequence(seq);
					Optional<AppraisalReviewDTO> appraisalReviewDTO = appraisalReviewService.findOne(appraisalReviewId);
					approval.setStatus(appraisalReviewDTO.get().getStatus());
					approval.setApprovalStatus(appraisalReviewDTO.get().getAppraisalStatus());
					approvalService.save(approval);
				}

				Map<Long, Long> approvarChildMap = new HashMap<Long, Long>();
				for (Map.Entry<Long, Long> entry : approvarListMap.entrySet()) {
					log.debug("In for ChildMap entry: " + entry);
					if (!entry.getValue().equals(seq)) {
						log.debug("Childmap and approvarListMap value not matched for value: " + entry.getValue());
						approvarChildMap.put(entry.getKey(), entry.getValue());
					}
				}
				log.debug("Childmap: " + approvarChildMap);
				approvarListMap = approvarChildMap;
				log.debug("After assign Childmap to approvarListMap: " + approvarListMap);
			}
		}
	}

	private long findNextReportingEmpId(long applierEmpId) {
		Optional<EmployeeDTO> employee = employeeService.findOne(applierEmpId);
		long reprotingEmpId = 0;
		if (employee != null && !employee.isEmpty()) {
			if (employee.get().getReportingEmpId() == null) {
				// approvarListMap=null;
				// throw new BadRequestAlertException(
				// "Reporting employee id found null for employee" +
				// employee.get().getFirstName() + employee.get().getLastName(),
				// ENTITY_NAME,
				// "ReprotingEmpIdnotfound"
				return reprotingEmpId;
				// );
			}
			reprotingEmpId = employee.get().getReportingEmpId();
		}
		return reprotingEmpId;
	}

	public void createAppraisalReviewApproval(AppraisalReviewDTO appraisalReview) {
		// HashMap<Long, Long> approvarListMap = null;
		long companyId1 = appraisalReview.getCompanyId();
		long applierEmpId = appraisalReview.getEmployeId();
		long appraisalReviewId = appraisalReview.getId();

		// To Find the approvalsetting id
		long approvalSettingId = 0;
		ApprovalSettingCriteria approvalSettingCriteria = new ApprovalSettingCriteria();

		LongFilter companyId = new LongFilter();
		companyId.setEquals(companyId1);
		approvalSettingCriteria.setCompanyId(companyId);

		StringFilter type = new StringFilter();
		type.setEquals("Appraisal Approval");
		approvalSettingCriteria.setType(type);

		StringFilter status = new StringFilter();
		status.setEquals("A");
		approvalSettingCriteria.setStatus(status);

		List<ApprovalSettingDTO> approvalSettingList = approvalSettingQueryService
				.findByCriteria(approvalSettingCriteria);
		if (approvalSettingList != null) {
			for (ApprovalSettingDTO approvalSetting : approvalSettingList) {
				approvalSettingId = approvalSetting.getId();
				getApprovarsList1(approvalSettingId, companyId1, applierEmpId, appraisalReviewId);
			}
		}
	}

	//
	public AppraisalReviewDTO cancelAppraisalReviewForm(AppraisalReviewDTO appraisalReviewDTO) {
	
		String id = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // securityUtils
		if (authentication != null && authentication.isAuthenticated()) {
			id = authentication.getName();
			System.out.println("!!!!!!!!!!!!!authentication.getName(): " + id);
		}

		AdminUserCriteria userCriteria = new AdminUserCriteria();
		StringFilter userId = new StringFilter();
		userId.setEquals(id);
		userCriteria.setId(userId);

		long currentUserId = 0;
		List<AdminUserDTO> list = userQueryService.findByCriteria(userCriteria);
		if (list != null) {
			for (AdminUserDTO adminUserDTO : list) {
				currentUserId = adminUserDTO.getEmployeeId();
				log.debug("!!!!!!!!!!!!!!!!REST request to get current logged in UserId : {}", currentUserId);
			}
		}

		long reviewId = appraisalReviewDTO.getId();
		Optional<AppraisalReviewDTO> appraisalReviewDTO1 = appraisalReviewService.findOne(reviewId);

		if (currentUserId == appraisalReviewDTO1.get().getEmployeId()) {
			String reviewType;
			long empId;
			long companyId = 0;
			
			if (appraisalReviewDTO1.get().getId() != null
					&& appraisalReviewDTO1.get().getStatus().equalsIgnoreCase("A")) {
				// If Approved update the leave balance in employee leave account
				if (appraisalReviewDTO1.get().getAppraisalStatus().equalsIgnoreCase("Approved")) {
					reviewType = appraisalReviewDTO1.get().getAppraisalStatus();
					empId = appraisalReviewDTO1.get().getEmployeId();
					companyId = appraisalReviewDTO1.get().getCompanyId();
				

				}
			}

			// Set all approval status I(inactive)
			ApprovalCriteria approvalCriteria = new ApprovalCriteria();

			LongFilter refId = new LongFilter();
			refId.setEquals(reviewId);
			approvalCriteria.setRefTableId(refId);

			StringFilter refTable = new StringFilter();
			refTable.setEquals("AppraisalReview");
			approvalCriteria.setRefTable(refTable);

			LongFilter companyId1 = new LongFilter();
			companyId1.setEquals(companyId);
			approvalCriteria.setCompanyId(companyId1);

			StringFilter status = new StringFilter();
			status.setEquals("A");
			approvalCriteria.setStatus(status);

			List<ApprovalDTO> approvalList = approvalQueryService.findByCriteria(approvalCriteria);
			List<ApprovalDTO> approvalList1 = new ArrayList<ApprovalDTO>();
			if (approvalList != null) {
				for (ApprovalDTO approval : approvalList) {
					approval.setStatus("I");
					approvalList1.add(approval);
					approvalService.save(approval);
				}
			}

			appraisalReviewDTO.setApprovals(approvalList1);
		} else {
			throw new BadRequestAlertException("Logged in user and applicant should be same to cancel leave",
					ENTITY_NAME, "LoggedInUserNotMatched");
		}
		return appraisalReviewDTO;
	}

	//

	private HashMap<Long, Long> getApprovarsList(long approvalSettingId, long companyId, long applierEmpId) {
		HashMap<Long, Long> approvarListMap = new HashMap<Long, Long>();

		// To Find approval levels for the approval setting
		ApprovalLevelCriteria approvalLevelCriteria = new ApprovalLevelCriteria();

		LongFilter approvalSettingId1 = new LongFilter();
		approvalSettingId1.setEquals(approvalSettingId);
		approvalLevelCriteria.setApprovalSettingId(approvalSettingId1);

		LongFilter compId = new LongFilter();
		compId.setEquals(companyId);
		approvalLevelCriteria.setCompanyId(compId);

		StringFilter status = new StringFilter();
		status.setEquals("A");
		approvalLevelCriteria.setStatus(status);

		List<ApprovalLevelDTO> approvalLevelList = approvalLevelQueryService.findByCriteria(approvalLevelCriteria);
		if (approvalLevelList != null) {
			// To sort ApprovalLevelList
			List<ApprovalLevelDTO> sortedList = sortApprovalLevelList(approvalLevelList);
			log.debug("!!!!!Request to get approvalLevelList after sort:" + sortedList);

			// find approvalLevel designation to add the approvalLevel sequence and
			// employeeId in map
			for (ApprovalLevelDTO approvalLevel : approvalLevelList) {
				long sequence = approvalLevel.getSquence();
				long designationId = approvalLevel.getDesignationId();

				// Find the employee Id's for designationId,companyId which can be approvar's
				EmployeeCriteria employeeCriteria = new EmployeeCriteria();

				LongFilter designationId1 = new LongFilter();
				designationId1.setEquals(designationId);
				employeeCriteria.setDesignationId(designationId1);
				employeeCriteria.setCompanyId(compId);

				List<EmployeeDTO> employeeList = employeeQueryService.findByCriteria(employeeCriteria);
				if (employeeList != null) {
					for (EmployeeDTO employee : employeeList) {
						long empId = employee.getId();
						approvarListMap.put(empId, sequence);
					}
				}
			}
		}
		return approvarListMap;
	}

	private List<ApprovalLevelDTO> sortApprovalLevelList(List<ApprovalLevelDTO> approvalLevelList) {
		List<ApprovalLevelDTO> sortedList = approvalLevelList.stream()
				.sorted(Comparator.comparing(ApprovalLevelDTO::getSquence)).collect(Collectors.toList());
		return sortedList;
	}

	/**
	 * Check review is already applied or not.
	 *
	 * @param ResignationDTO.
	 * 
	 * @return Boolean
	 */
	public Boolean checkReviewApplied(AppraisalReviewDTO appraisalReviewDTO) {

		AppraisalReviewCriteria appraisalReviewCriteria = new AppraisalReviewCriteria();
		LongFilter idFilter = new LongFilter();
		StringFilter statusFilter = new StringFilter();
		List<String> statusList = new ArrayList();
		statusList.add("Cancelled");
		statusList.add("Reject");
		if (appraisalReviewDTO.getEmployeId() != null && appraisalReviewDTO.getStatus() != null) {
			idFilter.setEquals(appraisalReviewDTO.getEmployeId());
			statusFilter.setNotIn(statusList);
			appraisalReviewCriteria.setEmployeId(idFilter);
			appraisalReviewCriteria.setAppraisalStatus(statusFilter);
		}
		List<AppraisalReviewDTO> reviewList = appraisalReviewQueryService.findByCriteria(appraisalReviewCriteria);
		if (!reviewList.isEmpty()) {
			return true;
		}
		return false;
	}
}
