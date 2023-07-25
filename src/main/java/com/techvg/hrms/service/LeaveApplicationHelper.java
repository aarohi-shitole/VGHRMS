package com.techvg.hrms.service;

import com.techvg.hrms.domain.ApprovalLevel;
import com.techvg.hrms.repository.ApprovalLevelRepository;
import com.techvg.hrms.repository.DesignationRepository;
import com.techvg.hrms.service.criteria.AdminUserCriteria;
import com.techvg.hrms.service.criteria.ApprovalCriteria;
import com.techvg.hrms.service.criteria.ApprovalLevelCriteria;
import com.techvg.hrms.service.criteria.ApprovalSettingCriteria;
import com.techvg.hrms.service.criteria.DesignationCriteria;
import com.techvg.hrms.service.criteria.EmployeeCriteria;
import com.techvg.hrms.service.criteria.EmployeeLeaveAccountCriteria;
import com.techvg.hrms.service.criteria.HolidayCriteria;
import com.techvg.hrms.service.criteria.LeaveApplicationCriteria;
import com.techvg.hrms.service.criteria.LeaveTypeCriteria;
import com.techvg.hrms.service.criteria.WorkDaysSettingCriteria;
import com.techvg.hrms.service.dto.AdminUserDTO;
import com.techvg.hrms.service.dto.ApprovalDTO;
import com.techvg.hrms.service.dto.ApprovalLevelDTO;
import com.techvg.hrms.service.dto.ApprovalSettingDTO;
import com.techvg.hrms.service.dto.DepartmentDTO;
import com.techvg.hrms.service.dto.DesignationDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.EmployeeLeaveAccountDTO;
import com.techvg.hrms.service.dto.HolidayDTO;
import com.techvg.hrms.service.dto.LeaveApplicationDTO;
import com.techvg.hrms.service.dto.LeaveTypeDTO;
import com.techvg.hrms.service.dto.WorkDaysSettingDTO;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Service
public class LeaveApplicationHelper {

    private static final Logger log = LoggerFactory.getLogger(LeaveApplicationHelper.class);

    private static final String ENTITY_NAME = "leaveApplicationHelper";

    @Autowired
    private HolidayQueryService holidayQueryService;

    @Autowired
    private WorkDaysSettingQueryService workDaysSettingQueryService;

    @Autowired
    private LeaveTypeQueryService leaveTypeQueryService;

    @Autowired
    public EmployeeLeaveAccountQueryService employeeLeaveAccountQueryService;

    // For findApprovarList method
    @Autowired
    public EmployeeService employeeService;

    @Autowired
    public DepartmentService departmentService;

    @Autowired
    public ApprovalSettingQueryService approvalSettingQueryService;

    @Autowired
    public ApprovalLevelQueryService approvalLevelQueryService;

    @Autowired
    public DesignationQueryService designationQueryService;

    @Autowired
    public EmployeeQueryService employeeQueryService;

    @Autowired
    public ApprovalService approvalService;

    @Autowired
    public LeaveApplicationService leaveApplicationService;

    // For cancel Leave
    @Autowired
    public EmployeeLeaveAccountService employeeLeaveAccountService;

    @Autowired
    public ApprovalQueryService approvalQueryService;

    @Autowired
    public UserQueryService userQueryService;

    // For getTodayApprovedLeaveEmpCount
    @Autowired
    public LeaveApplicationQueryService leaveApplicationQueryService;

    public long calculateLeaveDays(LeaveApplicationDTO leaveApplicationDTO) {
        Instant fromInstantDate = leaveApplicationDTO.getFormDate();
        Instant toInstantDate = leaveApplicationDTO.getToDate();

        LocalDate fromDate = convetToLocalDate(fromInstantDate);
        LocalDate toDate = convetToLocalDate(toInstantDate);

        ArrayList<LocalDate> enteredLeaveDates = new ArrayList<>();
        LocalDate date = fromDate;
        while (!date.isAfter(toDate)) {
            enteredLeaveDates.add(date);
            date = date.plusDays(1);
        }
        log.debug("-------All entered applied leave dates FromDate to ToDate :" + enteredLeaveDates);

        long days = calculateLeaveDays(fromDate, toDate);

        return days;
    }

    public boolean isDayOff(LocalDate date) {
        WorkDaysSettingCriteria workDaysCriteria = new WorkDaysSettingCriteria();

        BooleanFilter dayOff = new BooleanFilter();
        dayOff.setEquals(true);
        workDaysCriteria.setDayOff(dayOff);

        List<WorkDaysSettingDTO> workDaysList = workDaysSettingQueryService.findByCriteria(workDaysCriteria);
        int flag = 0;
        if (workDaysList != null) {
            for (WorkDaysSettingDTO workDay : workDaysList) {
                log.debug("--------------Finding the off days-----------------");
                DayOfWeek day = date.getDayOfWeek();

                String offDay = workDay.getDay();
                String inputUppercase = offDay.toUpperCase(); // MONDAY
                DayOfWeek dow = DayOfWeek.valueOf(inputUppercase);

                if (day == dow) {
                    log.debug("---------day matched-------:" + day + "--at date--:" + date);
                    flag = 1;
                    break;
                }
            }
        }

        if (flag == 1) {
            return true;
        } else return false;
    }

    public long calculateLeaveDays(LocalDate fromDate, LocalDate toDate) {
        long totalLeaveDays = 0;
        LocalDate date = fromDate;

        while (date.isBefore(toDate) || date.isEqual(toDate)) {
            if (!isDayOff(date) && !isHoliday(date)) {
                totalLeaveDays++;
                log.debug("-------totalLeaveDays: " + totalLeaveDays + " After date:" + fromDate);
            }
            date = date.plusDays(1);
        }

        return totalLeaveDays;
    }

    public boolean isHoliday(LocalDate date) {
        HolidayCriteria holidayCriteria = new HolidayCriteria();
        List<HolidayDTO> holidayList = holidayQueryService.findByCriteria(holidayCriteria);
        ArrayList<LocalDate> holidayDates = new ArrayList<>();
        if (holidayList != null) {
            for (HolidayDTO holiday : holidayList) {
                LocalDate holidayLocalDate = convetToLocalDate(holiday.getHolidayDate());
                holidayDates.add(holidayLocalDate);
            }
        }
        return holidayDates.contains(date);
    }

    private static LocalDate convetToLocalDate(Instant instantDate) {
        ZoneId zoneId = ZoneId.of("Asia/Kolkata");
        ZonedDateTime zonedDateTimeFrom1 = instantDate.atZone(zoneId); // convert the Instant to a ZonedDateTime in the
        // default time zone
        LocalDate localDate = zonedDateTimeFrom1.toLocalDate();
        return localDate;
    }

    public boolean checkSuffiecientBalance(LeaveApplicationDTO leaveApplicationDTO) {
        log.debug("Request to get checkSuffiecientBalance method ");
        String leaveType = leaveApplicationDTO.getLeaveType();
        LeaveTypeCriteria leaveTypeCriteria = new LeaveTypeCriteria();

        StringFilter leaveType1 = new StringFilter();
        leaveType1.setEquals(leaveType);
        leaveTypeCriteria.setLeaveType(leaveType1);

        StringFilter status = new StringFilter();
        status.setEquals("A");
        leaveTypeCriteria.setStatus(status);

        long leaveTypeId = 0;
        List<LeaveTypeDTO> list = leaveTypeQueryService.findByCriteria(leaveTypeCriteria);
        if (list != null && !list.isEmpty()) {
            leaveTypeId = list.get(0).getId();
        }

        LongFilter empId = new LongFilter();
        empId.setEquals(leaveApplicationDTO.getEmployeId());

        LongFilter compId = new LongFilter();
        compId.setEquals(leaveApplicationDTO.getCompanyId());

        LongFilter leaveTypeId1 = new LongFilter();
        leaveTypeId1.setEquals(leaveTypeId);

        StringFilter status1 = new StringFilter();
        status1.setEquals("A");

        EmployeeLeaveAccountCriteria employeeLeaveAccountCriteria = new EmployeeLeaveAccountCriteria();

        employeeLeaveAccountCriteria.setEmployeeId(empId);
        employeeLeaveAccountCriteria.setCompanyId(compId);
        employeeLeaveAccountCriteria.setLeaveTypeId(leaveTypeId1);
        employeeLeaveAccountCriteria.setStatus(status1);

        boolean flag = true;
        List<EmployeeLeaveAccountDTO> employeeLeaveAccountList = employeeLeaveAccountQueryService.findByCriteria(
            employeeLeaveAccountCriteria
        );
        if (employeeLeaveAccountList == null || employeeLeaveAccountList.isEmpty()) {
            flag = false;
            throw new BadRequestAlertException(
                "In EmployeeLeaveAccount Not sufficient leave balance!!",
                ENTITY_NAME,
                "EmployeeLeaveAccountNotExists"
            );
        } else {
            for (EmployeeLeaveAccountDTO employeeLeaveAccount : employeeLeaveAccountList) {
                log.debug(
                    "!!!Request to find employeeLeaveAccount for employee id:" +
                    empId +
                    " and company id: " +
                    compId +
                    " and leave type id: " +
                    leaveTypeId1
                );
                if (employeeLeaveAccount.getBalance() == null) {
                    flag = false;
                    throw new BadRequestAlertException(
                        "Balance is null in EmployeeLeaveAccount for you!!",
                        ENTITY_NAME,
                        "BalanceNotExists"
                    );
                } else if (employeeLeaveAccount.getBalance() < leaveApplicationDTO.getNoOfDays()) {
                    flag = false;
                    throw new BadRequestAlertException(
                        "Not sufficient leave balance for you to take leave!!",
                        ENTITY_NAME,
                        "InsufficientBalance"
                    );
                }
            }
        }
        return flag;
    }

    public HashMap<Long, Long> findApprovarList(Long employeId, Long approvarEmpId) {
        HashMap<Long, Long> approvarListMap = null;

        // Find the approvar department
        Optional<EmployeeDTO> employee = employeeService.findOne(approvarEmpId);
        if (!employee.isPresent()) {
            throw new BadRequestAlertException("given approvar employee_id should not found", ENTITY_NAME, "employeeNotExists");
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
        type.setEquals("Leave Approval");
        approvalSettingCriteria.setType(type);

        List<ApprovalSettingDTO> approvalSettingList = approvalSettingQueryService.findByCriteria(approvalSettingCriteria);
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
        Collections.sort(
            list,
            new Comparator<Map.Entry<Long, Long>>() {
                public int compare(Map.Entry<Long, Long> o1, Map.Entry<Long, Long> o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            }
        );

        // Create a new LinkedHashMap to preserve the order of insertion
        LinkedHashMap<Long, Long> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Long, Long> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

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
        List<ApprovalLevelDTO> sortedList = approvalLevelList
            .stream()
            .sorted(Comparator.comparing(ApprovalLevelDTO::getSquence))
            .collect(Collectors.toList());
        return sortedList;
    }

    public boolean checkApprovarPresent(Long approvarEmpId, HashMap<Long, Long> approvarListMap1) {
        boolean approvarPresent = checkPassedApprovarEmpIdPresentInMap(approvarEmpId, approvarListMap1);
        log.debug("approvarPresent or not:" + approvarPresent);
        return approvarPresent;
    }

    private boolean checkPassedApprovarEmpIdPresentInMap(Long approvarEmpId, HashMap<Long, Long> approvarListMap1) {
        if (approvarListMap1.containsKey(approvarEmpId)) return true; else return false;
    }

    private void getApprovarsList1(long approvalSettingId, long companyId, long applierEmpId, long leaveApplicationId) {
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
                        // approvarListMap.put(empId, sequence);

                        // If is applierEmpId occured in approvals,Code is added to skip that emp having
                        // same applierEmpId designation
                        Optional<EmployeeDTO> emp = employeeService.findOne(applierEmpId);
                        if (!(applierEmpId == empId) && !(emp.get().getDesignation().getId() == employee.getDesignation().getId())) {
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
                                        log.debug("Childmap and approvarListMap value not matched for value: " + entry.getValue());
                                        approvarChildMap.put(entry.getKey(), entry.getValue());
                                    }
                                }

                                approvarListMap = approvarChildMap;
                                seq = seq - 1;
                            }
                        }
                    }
                    log.debug("Approvar Map with Emp id and sequence:" + approvarListMap.toString());
                }
            }

            while (!approvarListMap.isEmpty()) {
                log.debug("!!!!!!!!!!passed previous applierEmpId:" + applierEmpId);
                long empId = applierEmpId;
                applierEmpId = findNextReportingEmpId(applierEmpId);
                if (applierEmpId == 0) {
                    approvarListMap.clear();
                }

                log.debug("!!!!!!!!!!after findNextReportingEmpId applierEmpId(designation should present in level):" + applierEmpId);

                long seq = 0;
                if (approvarListMap.containsKey(applierEmpId)) {
                    seq = approvarListMap.get(applierEmpId);
                    log.debug("!!!!!!sequence from map key(empId) seq:" + applierEmpId);

                    ApprovalDTO approval = new ApprovalDTO();
                    approval.setCompanyId(companyId);
                    approval.setApproverEmployeeId(applierEmpId);
                    approval.setRefTable("LeaveApplication");
                    approval.setRefTableId(leaveApplicationId);
                    approval.setSequence(seq);
                    Optional<LeaveApplicationDTO> leaveApplication = leaveApplicationService.findOne(leaveApplicationId);
                    approval.setStatus(leaveApplication.get().getStatus());
                    approval.setApprovalStatus(leaveApplication.get().getLeaveStatus());
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

    public void createLeaveApplicationApproval(LeaveApplicationDTO leaveApplication) {
        // HashMap<Long, Long> approvarListMap = null;
        long companyId1 = leaveApplication.getCompanyId();
        long applierEmpId = leaveApplication.getEmployeId();
        long leaveApplicationId = leaveApplication.getId();

        // To Find the approvalsetting id
        long approvalSettingId = 0;
        ApprovalSettingCriteria approvalSettingCriteria = new ApprovalSettingCriteria();

        LongFilter companyId = new LongFilter();
        companyId.setEquals(companyId1);
        approvalSettingCriteria.setCompanyId(companyId);

        StringFilter type = new StringFilter();
        type.setEquals("Leave Approval");
        approvalSettingCriteria.setType(type);

        StringFilter status = new StringFilter();
        status.setEquals("A");
        approvalSettingCriteria.setStatus(status);

        List<ApprovalSettingDTO> approvalSettingList = approvalSettingQueryService.findByCriteria(approvalSettingCriteria);
        if (approvalSettingList != null) {
            for (ApprovalSettingDTO approvalSetting : approvalSettingList) {
                approvalSettingId = approvalSetting.getId();
                getApprovarsList1(approvalSettingId, companyId1, applierEmpId, leaveApplicationId);
            }
        }
    }

    public LeaveApplicationDTO cancelLeave(LeaveApplicationDTO leaveApplicationDTO) {
        // Check logged in empId==leaveApplicationDTO.getEmployeId() else throw error
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

        long leaveId = leaveApplicationDTO.getId();
        Optional<LeaveApplicationDTO> leaveApplicationDTO1 = leaveApplicationService.findOne(leaveId);

        if (currentUserId == leaveApplicationDTO1.get().getEmployeId()) {
            String leaveType;
            long empId;
            long companyId = 0;
            long noOfDays = 0;
            if (leaveApplicationDTO1.get().getId() != null && leaveApplicationDTO1.get().getStatus().equalsIgnoreCase("A")) {
                // If Approved update the leave balance in employee leave account
                if (leaveApplicationDTO1.get().getLeaveStatus().equalsIgnoreCase("Approved")) {
                    leaveType = leaveApplicationDTO1.get().getLeaveType();
                    empId = leaveApplicationDTO1.get().getEmployeId();
                    companyId = leaveApplicationDTO1.get().getCompanyId();
                    noOfDays = leaveApplicationDTO1.get().getNoOfDays();

                    this.addEmployeeLeaveAccountBalance(companyId, leaveType, empId, noOfDays);
                } // Check cancelled
            }

            // Set all approval status I(inactive)
            ApprovalCriteria approvalCriteria = new ApprovalCriteria();

            LongFilter refId = new LongFilter();
            refId.setEquals(leaveId);
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
            List<ApprovalDTO> approvalList1 = new ArrayList<ApprovalDTO>();
            if (approvalList != null) {
                for (ApprovalDTO approval : approvalList) {
                    approval.setStatus("I");
                    approvalList1.add(approval);
                    approvalService.save(approval);
                }
            }

            leaveApplicationDTO.setApprovals(approvalList1);
        } else {
            throw new BadRequestAlertException(
                "Logged in user and applicant should be same to cancel leave",
                ENTITY_NAME,
                "LoggedInUserNotMatched"
            );
        }
        return leaveApplicationDTO;
    }

    // Add balance to employeeLeaveAccount
    private void addEmployeeLeaveAccountBalance(long companyId, String leaveType, long empId, long noOfDays) {
        // find leaveTypeId
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!In method addEmployeeLeaveAccountBalance");
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
            long bal = employeeLeaveAccount.get(0).getBalance() + noOfDays;
            employeeLeaveAccount.get(0).setBalance(bal);
            employeeLeaveAccountService.save(employeeLeaveAccount.get(0));
        }
    }

    public long getTodayApprovedLeaveEmpCount(Long companyId) {
        long empCount = 0;
        LeaveApplicationCriteria criteria = new LeaveApplicationCriteria();

        StringFilter leaveStatus = new StringFilter();
        leaveStatus.setEquals("Approved");
        criteria.setLeaveStatus(leaveStatus);

        StringFilter status = new StringFilter();
        status.setEquals("A");
        criteria.setStatus(status);

        LongFilter compId = new LongFilter();
        compId.setEquals(companyId);
        criteria.setCompanyId(compId);

        LocalDate todayDate = LocalDate.now();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!todayDate: " + todayDate);

        List<LeaveApplicationDTO> list = leaveApplicationQueryService.findByCriteria(criteria);
        if (list == null || list.isEmpty()) {
            return 0;
        } else {
            for (LeaveApplicationDTO leaveApplicationDTO : list) {
                Instant fromInstantDate = leaveApplicationDTO.getFormDate();
                Instant toInstantDate = leaveApplicationDTO.getToDate();

                LocalDate fromDate = convetToLocalDate(fromInstantDate);
                LocalDate toDate = convetToLocalDate(toInstantDate);

                ArrayList<LocalDate> approvedLeaveDates = new ArrayList<>();
                LocalDate date = fromDate;
                while (!date.isAfter(toDate)) {
                    approvedLeaveDates.add(date);
                    date = date.plusDays(1);
                }
                log.debug(
                    "-------Approved leave dates FromDate to ToDate :" +
                    approvedLeaveDates +
                    " for leave application " +
                    leaveApplicationDTO.getId()
                );

                if (approvedLeaveDates.contains(todayDate)) {
                    empCount++;
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!empCount: " + empCount + " leaveApplicationDTO " + leaveApplicationDTO);
                }
            }
        }

        return empCount;
    }
}
