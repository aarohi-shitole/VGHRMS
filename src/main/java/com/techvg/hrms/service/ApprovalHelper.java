package com.techvg.hrms.service;

import com.techvg.hrms.service.criteria.AdminUserCriteria;
import com.techvg.hrms.service.criteria.AppraisalReviewCriteria;
import com.techvg.hrms.service.criteria.ApprovalCriteria;
import com.techvg.hrms.service.criteria.EmployeeLeaveAccountCriteria;
import com.techvg.hrms.service.criteria.LeaveApplicationCriteria;
import com.techvg.hrms.service.criteria.LeaveTypeCriteria;
import com.techvg.hrms.service.criteria.ResignationCriteria;
import com.techvg.hrms.service.dto.AdminUserDTO;
import com.techvg.hrms.service.dto.AppraisalReviewDTO;
import com.techvg.hrms.service.dto.ApprovalDTO;
import com.techvg.hrms.service.dto.EmployeeLeaveAccountDTO;
import com.techvg.hrms.service.dto.LeaveApplicationDTO;
import com.techvg.hrms.service.dto.LeaveTypeDTO;
import com.techvg.hrms.service.dto.ResignationDTO;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Service
public class ApprovalHelper {

    private final Logger log = LoggerFactory.getLogger(ApprovalHelper.class);

    private static final String ENTITY_NAME = "approval";

    @Autowired
    public UserQueryService userQueryService;

    @Autowired
    public LeaveApplicationService leaveApplicationService;

    @Autowired
    public ResignationService resignationService;
    
    @Autowired
    public AppraisalReviewService appraisalReviewService;

    @Autowired
    public LeaveApplicationQueryService leaveApplicationQueryService;

    @Autowired
    public ResignationQueryService resignationQueryService;
    
    @Autowired
    public AppraisalReviewQueryService appraisalReviewQueryService;

    @Autowired
    private LeaveTypeQueryService leaveTypeQueryService;

    @Autowired
    public EmployeeLeaveAccountQueryService employeeLeaveAccountQueryService;

    @Autowired
    public EmployeeLeaveAccountService employeeLeaveAccountService;

    @Autowired
    public ApprovalService approvalService;

    @Autowired
    public ApprovalQueryService approvalQueryService;

    public ApprovalDTO updateApproval(ApprovalDTO approvalDTO) {
        ApprovalDTO approvalObj = null;
        long sequence = approvalDTO.getSequence();
        long approverEmployeeId = approvalDTO.getApproverEmployeeId();

        // Finding current logged in UserId
        String id = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // securityUtils
        if (authentication != null && authentication.isAuthenticated()) {
            id = authentication.getName();
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

        Optional<ApprovalDTO> approval = approvalService.findOne(approvalDTO.getId());
        String existingApprovalStatus = approval.get().getApprovalStatus();

        LeaveApplicationCriteria criteria = new LeaveApplicationCriteria();
        LongFilter leaveApplicationId = new LongFilter();
        leaveApplicationId.setEquals(approvalDTO.getRefTableId());
        criteria.setId(leaveApplicationId);

        StringFilter status = new StringFilter();
        status.setEquals("A");
        criteria.setStatus(status);

        List<LeaveApplicationDTO> leaveApplication = leaveApplicationQueryService.findByCriteria(criteria);

        List<ApprovalDTO> currentApprovalList = null;
        if (approverEmployeeId == currentUserId) {
            log.debug("!!!!!!!!!!!!Current logged in UserId and approverEmployeeId from DTO are same: {}", currentUserId);
            if (sequence == 1 && existingApprovalStatus.equalsIgnoreCase("Applied")) {
                approvalObj = approvalService.save(approvalDTO);
                log.debug("After save approval for sequence " + sequence + " and approvalObject=" + approvalObj);

                if (approvalObj.getApprovalStatus().equalsIgnoreCase("Rejected")) {
                    for (LeaveApplicationDTO leaveApplicationDTO : leaveApplication) {
                        leaveApplicationDTO.setLeaveStatus("Rejected");
                        // save leaveApplicationDTO
                        leaveApplicationService.save(leaveApplicationDTO);
                    }
                }

                if (leaveApplication != null) {
                    currentApprovalList = findApprovalList(leaveApplication);
                    log.debug("!!!!!!!!!!!!!!!!currentApprovalList : ", currentApprovalList.toString());
                }
                Boolean simultaneousType = isSimultaneousApprove(currentApprovalList);

                if (simultaneousType) {
                    if (approvalObj.getApprovalStatus().equalsIgnoreCase("Approved")) {
                        for (LeaveApplicationDTO leaveApplicationDTO : leaveApplication) {
                            leaveApplicationDTO.setLeaveStatus("Approved");
                            // save leaveApplicationDTO
                            leaveApplicationService.save(leaveApplicationDTO);
                            String leaveType = leaveApplicationDTO.getLeaveType();
                            long companyId = leaveApplicationDTO.getCompanyId();
                            long noOfDays = leaveApplicationDTO.getNoOfDays();
                            long employeeId = leaveApplicationDTO.getEmployeId();
                            this.reduceEmployeeLeaveAccountBalance(companyId, leaveType, employeeId, noOfDays);
                        }
                    }
                } else {
                    if (approvalObj.getApprovalStatus().equalsIgnoreCase("Approved")) {
                        for (LeaveApplicationDTO leaveApplicationDTO : leaveApplication) {
                            leaveApplicationDTO.setLeaveStatus("Pending");
                            // save leaveApplicationDTO
                            leaveApplicationService.save(leaveApplicationDTO);
                        }
                    }
                }
            }

            // If sequence greater than 1 checked approvalStatus of previous sequence
            if (sequence > 1) {
                if (leaveApplication != null) {
                    currentApprovalList = findApprovalList(leaveApplication);
                    log.debug("!!!!!!!!!!!!!!!!currentApprovalList : ", currentApprovalList.toString());
                }

                int isApproved = findPrevSequenceIsApproved(sequence, currentApprovalList);
                log.debug("!!!!!!!!!!!!!!!!returned isApproved from method: " + isApproved);
                // }
                if (isApproved == 1 && existingApprovalStatus.equalsIgnoreCase("Applied")) {
                    approvalObj = approvalService.save(approvalDTO);
                } else {
                    throw new BadRequestAlertException(
                        "Please approved first from previous sequence",
                        ENTITY_NAME,
                        "NotApprovedFromPreviousSequence"
                    );
                }

                if (approvalObj.getApprovalStatus().equalsIgnoreCase("Rejected")) {
                    for (LeaveApplicationDTO leaveApplicationDTO : leaveApplication) {
                        leaveApplicationDTO.setLeaveStatus("Rejected");
                        // save leaveApplicationDTO
                        leaveApplicationService.save(leaveApplicationDTO);
                    }
                }

                if (leaveApplication != null) {
                    currentApprovalList = findApprovalList(leaveApplication);
                    log.debug("!!!!!!!!!!!!!!!!currentApprovalList : ", currentApprovalList.toString());
                }

                // Find isLastApproval if !isLastApproval set
                // leaveStatus to pending else set to approved
                Boolean isLastApproval = isLastApproval(currentApprovalList);

                if (isLastApproval) {
                    if (approvalObj.getApprovalStatus().equalsIgnoreCase("Approved")) {
                        for (LeaveApplicationDTO leaveApplicationDTO : leaveApplication) {
                            leaveApplicationDTO.setLeaveStatus("Approved");
                            // save leaveApplicationDTO
                            leaveApplicationService.save(leaveApplicationDTO);

                            String leaveType = leaveApplicationDTO.getLeaveType();
                            long companyId = leaveApplicationDTO.getCompanyId();
                            long noOfDays = leaveApplicationDTO.getNoOfDays();
                            long employeeId = leaveApplicationDTO.getEmployeId();
                            this.reduceEmployeeLeaveAccountBalance(companyId, leaveType, employeeId, noOfDays);
                        }
                    }
                } else {
                    if (approvalObj.getApprovalStatus().equalsIgnoreCase("Approved")) {
                        for (LeaveApplicationDTO leaveApplicationDTO : leaveApplication) {
                            leaveApplicationDTO.setLeaveStatus("Pending");
                            // save leaveApplicationDTO
                            leaveApplicationService.save(leaveApplicationDTO);
                        }
                    }
                }
            }
        } else {
            throw new BadRequestAlertException(
                "current logged in user not matched with approvalDTO approverEmployeeId ",
                ENTITY_NAME,
                "Notmatched"
            );
        }
        return approvalObj;
    }

    public ApprovalDTO updateResignationApproval(ApprovalDTO approvalDTO) {
        ApprovalDTO approvalObj = null;
        long sequence = approvalDTO.getSequence();
        long approverEmployeeId = approvalDTO.getApproverEmployeeId();

        // Finding current logged in UserId
        String id = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // securityUtils
        if (authentication != null && authentication.isAuthenticated()) {
            id = authentication.getName();
        }

        AdminUserCriteria userCriteria = new AdminUserCriteria();
        StringFilter userId = new StringFilter();
        userId.setEquals(id);
        userCriteria.setId(userId);

        long currentUserId = 0;
        List<AdminUserDTO> list = userQueryService.findByCriteria(userCriteria);
        if (list != null && !list.isEmpty()) {
            for (AdminUserDTO adminUserDTO : list) {
                currentUserId = adminUserDTO.getEmployeeId();
                log.debug("!!!!!!!!!!!!!!!!REST request to get current logged in UserId : {}", currentUserId);
            }
        }

        Optional<ApprovalDTO> approval = approvalService.findOne(approvalDTO.getId());
        String existingApprovalStatus = approval.isPresent() ? approval.get().getApprovalStatus() : null;

        ResignationCriteria criteria = new ResignationCriteria();
        LongFilter resignationApplicationId = new LongFilter();
        resignationApplicationId.setEquals(approvalDTO.getRefTableId());
        criteria.setId(resignationApplicationId);

        StringFilter status = new StringFilter();
        status.setEquals("A");
        criteria.setStatus(status);

        List<ResignationDTO> resignation = resignationQueryService.findByCriteria(criteria);

        List<ApprovalDTO> currentApprovalList = null;
        if (approverEmployeeId == currentUserId) {
            log.debug("!!!!!!!!!!!!Current logged in UserId and approverEmployeeId from DTO are same: {}", currentUserId);
            if (sequence == 1 && existingApprovalStatus != null && existingApprovalStatus.equalsIgnoreCase("Applied")) {
                approvalObj = approvalService.save(approvalDTO);
                log.debug("After save approval for sequence " + sequence + " and approvalObject=" + approvalObj);

                if (approvalObj.getApprovalStatus().equalsIgnoreCase("Rejected")) {
                    if (resignation != null) {
                        for (ResignationDTO resginationDTO : resignation) {
                            resginationDTO.setResignStatus("Rejected");
                            // save leaveApplicationDTO
                            resignationService.save(resginationDTO);
                        }
                    }
                }

                if (resignation != null) {
                    currentApprovalList = findApprovalResignationList(resignation);
                    log.debug("!!!!!!!!!!!!!!!!currentApprovalList : ", currentApprovalList.toString());
                }
                Boolean simultaneousType = isSimultaneousApprove(currentApprovalList);

                if (simultaneousType) {
                    if (approvalObj.getApprovalStatus().equalsIgnoreCase("Approved")) {
                        if (resignation != null) {
                            for (ResignationDTO resginationDTO : resignation) {
                                resginationDTO.setResignStatus("Approved");
                                // save leaveApplicationDTO
                                resignationService.save(resginationDTO);
                            }
                        }
                    }
                } else {
                    if (approvalObj.getApprovalStatus().equalsIgnoreCase("Approved")) {
                        if (resignation != null) {
                            for (ResignationDTO resginationDTO : resignation) {
                                resginationDTO.setResignStatus("Pending");
                                // save leaveApplicationDTO
                                resignationService.save(resginationDTO);
                            }
                        }
                    }
                }
            }

            // If sequence greater than 1 checked approvalStatus of previous sequence
            if (sequence > 1) {
                if (resignation != null) {
                    currentApprovalList = findApprovalResignationList(resignation);
                    log.debug("!!!!!!!!!!!!!!!!currentApprovalList : ", currentApprovalList.toString());
                }

                int isApproved = findPrevSequenceIsApproved(sequence, currentApprovalList);
                log.debug("!!!!!!!!!!!!!!!!returned isApproved from method: " + isApproved);
                // }
                if (isApproved == 1 && existingApprovalStatus != null && existingApprovalStatus.equalsIgnoreCase("Applied")) {
                    approvalObj = approvalService.save(approvalDTO);
                } else {
                    throw new BadRequestAlertException(
                        "Please approve first from the previous sequence",
                        ENTITY_NAME,
                        "NotApprovedFromPreviousSequence"
                    );
                }

                if (approvalObj.getApprovalStatus().equalsIgnoreCase("Rejected")) {
                    if (resignation != null) {
                        for (ResignationDTO resginationDTO : resignation) {
                            resginationDTO.setResignStatus("Rejected");
                            // save leaveApplicationDTO
                            resignationService.save(resginationDTO);
                        }
                    }
                }

                if (resignation != null) {
                    currentApprovalList = findApprovalResignationList(resignation);
                    log.debug("!!!!!!!!!!!!!!!!currentApprovalList : ", currentApprovalList.toString());
                }

                // Find isLastApproval if !isLastApproval set
                // leaveStatus to pending else set to approved
                Boolean isLastApproval = isLastApproval(currentApprovalList);

                if (isLastApproval) {
                    if (approvalObj.getApprovalStatus().equalsIgnoreCase("Approved")) {
                        if (resignation != null) {
                            for (ResignationDTO resginationDTO : resignation) {
                                resginationDTO.setResignStatus("Approved");
                                // save leaveApplicationDTO
                                resignationService.save(resginationDTO);
                            }
                        }
                    }
                } else {
                    if (approvalObj.getApprovalStatus().equalsIgnoreCase("Approved")) {
                        if (resignation != null) {
                            for (ResignationDTO resginationDTO : resignation) {
                                resginationDTO.setResignStatus("Pending");
                                // save leaveApplicationDTO
                                resignationService.save(resginationDTO);
                            }
                        }
                    }
                }
            }
        } else {
            throw new BadRequestAlertException(
                "The currently logged-in user does not match the approvalDTO approverEmployeeId",
                ENTITY_NAME,
                "NotMatched"
            );
        }
        return approvalObj;
    }

    //

    private void reduceEmployeeLeaveAccountBalance(long companyId, String leaveType, long empId, long noOfDays) {
        // find leaveTypeId
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!In method reduceEmployeeLeaveAccountBalance");
        LeaveTypeCriteria leaveTypeCriteria = new LeaveTypeCriteria();
        StringFilter leaveType1 = new StringFilter();
        leaveType1.setEquals(leaveType);
        leaveTypeCriteria.setLeaveType(leaveType1);

        long leaveTypeId = 0;
        List<LeaveTypeDTO> dto = leaveTypeQueryService.findByCriteria(leaveTypeCriteria);
        if (dto != null) {
            leaveTypeId = dto.get(0).getId();
        }
        EmployeeLeaveAccountCriteria criteria = new EmployeeLeaveAccountCriteria();
        LongFilter companyId1 = new LongFilter();
        companyId1.setEquals(companyId);
        criteria.setCompanyId(companyId1);

        LongFilter leaveTypeId1 = new LongFilter();
        leaveTypeId1.setEquals(leaveTypeId);
        criteria.setLeaveTypeId(leaveTypeId1);

        LongFilter empId1 = new LongFilter();
        empId1.setEquals(empId);
        criteria.setEmployeeId(empId1);

        List<EmployeeLeaveAccountDTO> employeeLeaveAccount = employeeLeaveAccountQueryService.findByCriteria(criteria);
        log.debug("EmployeeLeaveAccount before add balance result: " + employeeLeaveAccount.get(0));

        if (employeeLeaveAccount != null) {
            long bal = employeeLeaveAccount.get(0).getBalance() - noOfDays;
            employeeLeaveAccount.get(0).setBalance(bal);
            employeeLeaveAccountService.save(employeeLeaveAccount.get(0));
        }
    }

    private int findPrevSequenceIsApproved(long sequence, List<ApprovalDTO> currentApprovalList) {
        boolean flag = false;
        //sequence = sequence - 1;

        //Code added to check seq till 0
        if (sequence != 0) {
            sequence = sequence - 1;
        }
        if (sequence == 0) return 1;

        int val = 0;
        log.debug("!!!!!!!!!!!!!!!!sequence = sequence - 1: " + sequence);
        if (currentApprovalList != null) {
            for (ApprovalDTO approval : currentApprovalList) {
                if (approval.getSequence().equals(sequence)) {
                    flag = true;
                    if (approval.getApprovalStatus().equalsIgnoreCase("Approved")) {
                        log.debug("!!!!!!!!!!!!!!!Inside if approval.getApprovalStatus(): " + approval.getApprovalStatus());
                        val = 1;
                        log.debug("!!!!!!!!!!!!!!!!!val: " + val);
                        // return 1;
                        break;
                    }
                }
            }
        }
        log.debug("!!!!!!!!!!!!!!!flag: " + flag);
        if (flag == false) {
            return findPrevSequenceIsApproved(sequence, currentApprovalList);
        }
        // return false correct but its always returning false even Approved for prevSeq
        if (val == 1) return 1; else return 0;
    }

    private Boolean isLastApproval(List<ApprovalDTO> currentApprovalList) {
        Boolean flag = null;
        int cnt = 0;
        if (currentApprovalList != null) {
            for (ApprovalDTO approval : currentApprovalList) {
                if (approval.getApprovalStatus().equalsIgnoreCase("Applied")) {
                    cnt++;
                }
            }
            if (cnt < 1) {
                flag = true;
            } else {
                flag = false;
            }
        }

        log.debug("!!!!!!!!!!!!!!!isLastApproval flag set to: " + flag);
        return flag;
    }

    private List<ApprovalDTO> findApprovalList(List<LeaveApplicationDTO> leaveApplication) {
        long id = 0;
        long companyId = 0;
        for (LeaveApplicationDTO leaveApplicationDTO : leaveApplication) {
            id = leaveApplicationDTO.getId();
            companyId = leaveApplicationDTO.getCompanyId();
        }

        ApprovalCriteria approvalCriteria = new ApprovalCriteria();

        LongFilter refId = new LongFilter();
        refId.setEquals(id);
        approvalCriteria.setRefTableId(refId);

        StringFilter refTable = new StringFilter();
        refTable.setEquals("LeaveApplication");
        approvalCriteria.setRefTable(refTable);

        LongFilter companyId1 = new LongFilter();
        companyId1.setEquals(companyId);
        approvalCriteria.setCompanyId(companyId1);

        StringFilter status = new StringFilter();
        status.setEquals("A");
        approvalCriteria.setStatus(status);

        List<ApprovalDTO> approvalList = approvalQueryService.findByCriteria(approvalCriteria);
        return approvalList;
    }

    // for resignation approval list
    private List<ApprovalDTO> findApprovalResignationList(List<ResignationDTO> resignation) {
        long id = 0;
        long companyId = 0;
        for (ResignationDTO resignationDTO : resignation) {
            id = resignationDTO.getId();
            companyId = resignationDTO.getCompanyId();
        }

        ApprovalCriteria approvalCriteria = new ApprovalCriteria();

        LongFilter refId = new LongFilter();
        refId.setEquals(id);
        approvalCriteria.setRefTableId(refId);

        StringFilter refTable = new StringFilter();
        refTable.setEquals("Resignation");
        approvalCriteria.setRefTable(refTable);

        LongFilter companyId1 = new LongFilter();
        companyId1.setEquals(companyId);
        approvalCriteria.setCompanyId(companyId1);

        StringFilter status = new StringFilter();
        status.setEquals("A");
        approvalCriteria.setStatus(status);

        List<ApprovalDTO> approvalList = approvalQueryService.findByCriteria(approvalCriteria);
        return approvalList;
    }

    private Boolean isSimultaneousApprove(List<ApprovalDTO> approvalList) {
        Boolean flag = null;
        if (approvalList != null) {
            log.debug("!!!!!!!!!!!!!!!approvalList is not null flag: " + flag);
            for (ApprovalDTO approval : approvalList) {
                if (approval.getSequence() != 1) {
                    flag = false;
                    break;
                } else {
                    flag = true;
                }
            }
        }
        log.debug("!!!!!!!!!!!!!!!flag set to: " + flag);
        return flag;
    }
    
    
    //for appraisal review 
    public ApprovalDTO updateReviewApproval(ApprovalDTO approvalDTO) {
        ApprovalDTO approvalObj = null;
        long sequence = approvalDTO.getSequence();
        long approverEmployeeId = approvalDTO.getApproverEmployeeId();

        // Finding current logged in UserId
        String id = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // securityUtils
        if (authentication != null && authentication.isAuthenticated()) {
            id = authentication.getName();
        }

        AdminUserCriteria userCriteria = new AdminUserCriteria();
        StringFilter userId = new StringFilter();
        userId.setEquals(id);
        userCriteria.setId(userId);

        long currentUserId = 0;
        List<AdminUserDTO> list = userQueryService.findByCriteria(userCriteria);
        if (list != null && !list.isEmpty()) {
            for (AdminUserDTO adminUserDTO : list) {
                currentUserId = adminUserDTO.getEmployeeId();
                log.debug("!!!!!!!!!!!!!!!!REST request to get current logged in UserId : {}", currentUserId);
            }
        }

        Optional<ApprovalDTO> approval = approvalService.findOne(approvalDTO.getId());
        String existingApprovalStatus = approval.isPresent() ? approval.get().getApprovalStatus() : null;

        
        AppraisalReviewCriteria criteria = new AppraisalReviewCriteria();
        LongFilter reviewId = new LongFilter();
        reviewId.setEquals(approvalDTO.getRefTableId());
        criteria.setId(reviewId);

        StringFilter status = new StringFilter();
        status.setEquals("A");
        criteria.setStatus(status);

        List<AppraisalReviewDTO> review = appraisalReviewQueryService.findByCriteria(criteria);

        List<ApprovalDTO> currentApprovalList = null;
        if (approverEmployeeId == currentUserId) {
            log.debug("!!!!!!!!!!!!Current logged in UserId and approverEmployeeId from DTO are same: {}", currentUserId);
            if (sequence == 1 && existingApprovalStatus != null && existingApprovalStatus.equalsIgnoreCase("Applied")) {
                approvalObj = approvalService.save(approvalDTO);
                log.debug("After save approval for sequence " + sequence + " and approvalObject=" + approvalObj);

                if (approvalObj.getApprovalStatus().equalsIgnoreCase("Rejected")) {
                    if (review != null) {
                        for (AppraisalReviewDTO appraisalReviewDTO : review) {
                        	appraisalReviewDTO.setAppraisalStatus("Rejected");
                            // save leaveApplicationDTO
                        	appraisalReviewService.save(appraisalReviewDTO);
                        }
                    }
                }

                if (review != null) {
                    currentApprovalList = findApprovalReviewList(review);
                    log.debug("!!!!!!!!!!!!!!!!currentApprovalList : ", currentApprovalList.toString());
                }
                Boolean simultaneousType = isSimultaneousApprove(currentApprovalList);

                if (simultaneousType) {
                    if (approvalObj.getApprovalStatus().equalsIgnoreCase("Approved")) {
                        if (review != null) {
                            for (AppraisalReviewDTO appraisalReviewDTO : review) {
                            	appraisalReviewDTO.setAppraisalStatus("Approved");
                               
                            	appraisalReviewService.save(appraisalReviewDTO);
                            }
                        }
                    }
                } else {
                    if (approvalObj.getApprovalStatus().equalsIgnoreCase("Approved")) {
                        if (review != null) {
                            for (AppraisalReviewDTO appraisalReviewDTO : review) {
                            	appraisalReviewDTO.setAppraisalStatus("Pending");
                          
                            	appraisalReviewService.save(appraisalReviewDTO);
                            }
                        }
                    }
                }
            }

            // If sequence greater than 1 checked approvalStatus of previous sequence
            if (sequence > 1) {
                if (review != null) {
                    currentApprovalList = findApprovalReviewList(review);
                    log.debug("!!!!!!!!!!!!!!!!currentApprovalList : ", currentApprovalList.toString());
                }

                int isApproved = findPrevSequenceIsApproved(sequence, currentApprovalList);
                log.debug("!!!!!!!!!!!!!!!!returned isApproved from method: " + isApproved);
                // }
                if (isApproved == 1 && existingApprovalStatus != null && existingApprovalStatus.equalsIgnoreCase("Applied")) {
                    approvalObj = approvalService.save(approvalDTO);
                } else {
                    throw new BadRequestAlertException(
                        "Please approve first from the previous sequence",
                        ENTITY_NAME,
                        "NotApprovedFromPreviousSequence"
                    );
                }

                if (approvalObj.getApprovalStatus().equalsIgnoreCase("Rejected")) {
                    if (review != null) {
                        for (AppraisalReviewDTO appraisalReviewDTO : review) {
                        	appraisalReviewDTO.setAppraisalStatus("Rejected");
                          
                        	appraisalReviewService.save(appraisalReviewDTO);
                        }
                    }
                }

                if (review != null) {
                    currentApprovalList = findApprovalReviewList(review);
                    log.debug("!!!!!!!!!!!!!!!!currentApprovalList : ", currentApprovalList.toString());
                }

                // Find isLastApproval if !isLastApproval set
                // leaveStatus to pending else set to approved
                Boolean isLastApproval = isLastApproval(currentApprovalList);

                if (isLastApproval) {
                    if (approvalObj.getApprovalStatus().equalsIgnoreCase("Approved")) {
                        if (review != null) {
                            for (AppraisalReviewDTO appraisalReviewDTO : review) {
                            	appraisalReviewDTO.setAppraisalStatus("Approved");
                               
                            	appraisalReviewService.save(appraisalReviewDTO);
                            }
                        }
                    }
                } else {
                    if (approvalObj.getApprovalStatus().equalsIgnoreCase("Approved")) {
                        if (review != null) {
                            for (AppraisalReviewDTO appraisalReviewDTO : review) {
                            	appraisalReviewDTO.setAppraisalStatus("Pending");
                              
                            	appraisalReviewService.save(appraisalReviewDTO);
                            }
                        }
                    }
                }
            }
        } else {
            throw new BadRequestAlertException(
                "The currently logged-in user does not match the approvalDTO approverEmployeeId",
                ENTITY_NAME,
                "NotMatched"
            );
        }
        return approvalObj;
    }
    
    
    private List<ApprovalDTO> findApprovalReviewList(List<AppraisalReviewDTO> review) {
        long id = 0;
        long companyId = 0;
        for (AppraisalReviewDTO appraisalReviewDTO : review) {
            id = appraisalReviewDTO.getId();
            companyId = appraisalReviewDTO.getCompanyId();
        }

        ApprovalCriteria approvalCriteria = new ApprovalCriteria();

        LongFilter refId = new LongFilter();
        refId.setEquals(id);
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
        return approvalList;
    }
}
