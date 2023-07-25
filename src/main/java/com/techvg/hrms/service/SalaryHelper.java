package com.techvg.hrms.service;

import com.techvg.hrms.service.criteria.AttendanceCriteria;
import com.techvg.hrms.service.criteria.EmployeeExemptionCriteria;
import com.techvg.hrms.service.criteria.EmployeeSalaryComponentCriteria;
import com.techvg.hrms.service.criteria.EsiDetailsCriteria;
import com.techvg.hrms.service.criteria.HolidayCriteria;
import com.techvg.hrms.service.criteria.LeaveApplicationCriteria;
import com.techvg.hrms.service.criteria.LeaveTypeCriteria;
import com.techvg.hrms.service.criteria.MasterLookupCriteria;
import com.techvg.hrms.service.criteria.PfDetailsCriteria;
import com.techvg.hrms.service.criteria.RemunerationCriteria;
import com.techvg.hrms.service.criteria.TaxSlabCriteria;
import com.techvg.hrms.service.criteria.WorkDaysSettingCriteria;
import com.techvg.hrms.service.criteria.WorkingHoursCriteria;
import com.techvg.hrms.service.dto.AttendanceDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.EmployeeExemptionDTO;
import com.techvg.hrms.service.dto.EmployeeSalaryComponentDTO;
import com.techvg.hrms.service.dto.EsiDetailsDTO;
import com.techvg.hrms.service.dto.HolidayDTO;
import com.techvg.hrms.service.dto.LeaveApplicationDTO;
import com.techvg.hrms.service.dto.LeaveTypeDTO;
import com.techvg.hrms.service.dto.MasterLookupDTO;
import com.techvg.hrms.service.dto.PaySlipDTO;
import com.techvg.hrms.service.dto.PfDetailsDTO;
import com.techvg.hrms.service.dto.RemunerationDTO;
import com.techvg.hrms.service.dto.SalaryDTO;
import com.techvg.hrms.service.dto.TaxExempSectionDTO;
import com.techvg.hrms.service.dto.TaxSlabDTO;
import com.techvg.hrms.service.dto.WorkDaysSettingDTO;
import com.techvg.hrms.service.dto.WorkingHoursDTO;
import com.techvg.hrms.web.rest.errors.BusinessRuleException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Service
public class SalaryHelper {

    private static final Logger log = LoggerFactory.getLogger(SalaryHelper.class);

    private static final String ENTITY_NAME = "salaryHelper";

    @Autowired
    private HolidayQueryService holidayQueryService;

    @Autowired
    private TaxSlabQueryService taxSlabQueryService;

    @Autowired
    private TaxExempSectionService taxExempSectionService;

    @Autowired
    private WorkDaysSettingQueryService workDaysSettingQueryService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeSalaryComponentQueryService employeeSalaryComponentQueryService;

    @Autowired
    private RemunerationQueryService remunerationQueryService;

    @Autowired
    private EsiDetailsQueryService esiDetailsQueryService;

    @Autowired
    private PfDetailsQueryService pfDetailsQueryService;

    @Autowired
    private MasterLookupQueryService masterLookupQueryService;

    @Autowired
    private LeaveTypeQueryService leaveTypeQueryService;

    @Autowired
    private AttendanceQueryService attendanceQueryService;

    @Autowired
    private PaySlipService paySlipService;

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private LeaveApplicationQueryService leaveApplicationQueryService;

    @Autowired
    private EmployeeExemptionQueryService employeeExemptionQueryService;

    @Autowired
    private WorkingHoursQueryService workingHoursQueryService;

    ArrayList<LocalDate> workingDayDates = new ArrayList<>();
    ArrayList<LocalDate> allApprovedleaveDates = new ArrayList<>();
    // ArrayList<LocalDate> absentDaysDates = new ArrayList<>();
    double taxableIncomeAmount = 0;

    double noOfLWPLeaves = 0;

    public void saveSalaryComponentsInSalary(Long empId) {
        try {
            Instant date = Instant.now();
            LocalDate todayDate = convertToLocalDate(date);

            LocalDate startDate = todayDate.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate endDate = todayDate.with(TemporalAdjusters.lastDayOfMonth());

            System.out.println("startDate: " + startDate);
            System.out.println("endDate: " + endDate);

            int daysInMonth = endDate.getDayOfMonth();
            Month month = todayDate.getMonth();
            String monthName = month.getDisplayName(TextStyle.FULL, Locale.US);
            String yearName = Integer.toString(todayDate.getYear());

            log.debug("----------daysInMonth: " + daysInMonth);
            log.debug("----------monthName: " + monthName);

            // if (todayDate.equals(endDate)) {
            // System.out.println("------------------------------------todayDate is equals
            // endDate");

            Optional<EmployeeDTO> employee = employeeService.findOne(empId);
            long companyId = employee.get().getCompanyId();

            // Create reusable SalaryDTO object
            SalaryDTO salary = new SalaryDTO();
            salary.setEmployeeId(empId);
            salary.setStatus("A");
            salary.setMonth(monthName);
            salary.setYear(yearName);
            salary.setCompanyId(companyId);

            // Set employeeId,companyId,status to EmployeeSalaryComponentCriteria
            EmployeeSalaryComponentCriteria componentCriteria = new EmployeeSalaryComponentCriteria();
            LongFilter employeeId = new LongFilter();
            employeeId.setEquals(empId);
            componentCriteria.setEmployeeId(employeeId);

            StringFilter status = new StringFilter();
            status.setEquals("A");
            componentCriteria.setStatus(status);

            LongFilter companyId2 = new LongFilter();
            companyId2.setEquals(companyId);
            componentCriteria.setCompanyId(companyId2);

            double basicAmt = 0; // MonthlySalary is set to basic
            double da = 0;
            double hra = 0;
            double medical = 0;
            double conveyance = 0;
            double specialAllowance = 0;
            double cca = 0;
            double ghin = 0;
            double glin = 0;

            double monthlySalary = 0;
            double perDaySalary = 0;
            double hourlySalary = 0;
            double pf = 0;
            double esicAmt = 0;
            double incomeTaxAmountYearly;
            double incomeTaxAmountMonthly;
            double lWPAmount;
            double absentAmount;
            double perDayWorkingHours = getPerDayWorkingHours(companyId2, empId, status);

            double monthlyGrossPay;
            double monthlyNetSalary;
            double totalDeduction;
            double lossOfPay;

            // Get all salary components and save
            List<EmployeeSalaryComponentDTO> list = employeeSalaryComponentQueryService.findByCriteria(componentCriteria);
            if (list != null) {
                for (EmployeeSalaryComponentDTO employeeSalaryComponent : list) {
                    // If Basic SalaryComponent
                    if (employeeSalaryComponent.getName().equalsIgnoreCase("Basic") && employeeSalaryComponent.getValue() != null) {
                        basicAmt = employeeSalaryComponent.getValue();
                        // Check remuneration for salary type is monthly/yearly/hourly
                        RemunerationCriteria remunerationCriteria = new RemunerationCriteria();
                        remunerationCriteria.setEmployeeId(employeeId);
                        remunerationCriteria.setStatus(status);
                        remunerationCriteria.setCompanyId(companyId2);

                        List<RemunerationDTO> remunerationlist = remunerationQueryService.findByCriteria(remunerationCriteria);
                        if (remunerationlist != null) {
                            for (RemunerationDTO remuneration : remunerationlist) {
                                // Calculate Basic amount from SalaryComponent
                                if (remuneration.getSalaryType() != null && remuneration.getSalaryType().equalsIgnoreCase("Monthly")) {
                                    log.debug("--------------------------------------Monthly Salary");
                                    perDaySalary = basicAmt / daysInMonth;
                                    hourlySalary = perDaySalary / perDayWorkingHours;
                                }
                                if (remuneration.getSalaryType() != null && remuneration.getSalaryType().equalsIgnoreCase("Yearly")) {
                                    log.debug("--------------------------------------Yearly Salary");
                                    monthlySalary = basicAmt / 12;
                                    basicAmt = monthlySalary;
                                    perDaySalary = monthlySalary / daysInMonth;
                                    hourlySalary = perDaySalary / perDayWorkingHours;
                                }
                                if (remuneration.getSalaryType() != null && remuneration.getSalaryType().equalsIgnoreCase("Weekly")) {
                                    log.debug("--------------------------------------Weekly Salary");
                                    perDaySalary = basicAmt / 7;
                                    hourlySalary = perDaySalary / perDayWorkingHours;
                                    monthlySalary = perDaySalary * daysInMonth;
                                    basicAmt = monthlySalary;
                                }
                                if (remuneration.getSalaryType() != null && remuneration.getSalaryType().equalsIgnoreCase("Daily")) {
                                    log.debug("--------------------------------------Daily Salary");
                                    perDaySalary = basicAmt;
                                    hourlySalary = perDaySalary / perDayWorkingHours;
                                    monthlySalary = perDaySalary * daysInMonth;
                                    basicAmt = monthlySalary;
                                }
                                if (remuneration.getSalaryType() != null && remuneration.getSalaryType().equalsIgnoreCase("Hourly")) {
                                    log.debug("--------------------------------------Hourly Salary");
                                    hourlySalary = basicAmt;
                                    perDaySalary = hourlySalary * perDayWorkingHours;
                                    monthlySalary = perDaySalary * daysInMonth;
                                    basicAmt = monthlySalary;
                                }
                            }
                            System.out.println("--------------------------------------Per Day Salary:" + perDaySalary);
                        }
                    }
                    // Save DA SalaryComponent
                    if (employeeSalaryComponent.getName().equalsIgnoreCase("DA") && employeeSalaryComponent.getValue() != null) {
                        if (employeeSalaryComponent.getType().equalsIgnoreCase("Percentage")) {
                            da = basicAmt * da / 100;
                        } else da = employeeSalaryComponent.getValue();
                        log.debug("--------------------------------------da:" + da);
                    }
                    // Save HRA SalaryComponent
                    if (employeeSalaryComponent.getName().equalsIgnoreCase("HRA") && employeeSalaryComponent.getValue() != null) {
                        if (employeeSalaryComponent.getType().equalsIgnoreCase("Percentage")) {
                            hra = basicAmt * hra / 100;
                        } else hra = employeeSalaryComponent.getValue();
                        log.debug("--------------------------------------hra:" + hra);
                    }
                    if (employeeSalaryComponent.getName().equalsIgnoreCase("Medical") && employeeSalaryComponent.getValue() != null) {
                        if (employeeSalaryComponent.getType().equalsIgnoreCase("Percentage")) {
                            medical = basicAmt * medical / 100;
                        } else medical = employeeSalaryComponent.getValue();
                        log.debug("--------------------------------------medical:" + medical);
                    }
                    if (employeeSalaryComponent.getName().equalsIgnoreCase("Conveyance") && employeeSalaryComponent.getValue() != null) {
                        if (employeeSalaryComponent.getType().equalsIgnoreCase("Percentage")) {
                            conveyance = basicAmt * conveyance / 100;
                        } else conveyance = employeeSalaryComponent.getValue();
                        log.debug("--------------------------------------conveyance:" + conveyance);
                    }
                    if (
                        employeeSalaryComponent.getName().equalsIgnoreCase("SpecialAllowance") && employeeSalaryComponent.getValue() != null
                    ) {
                        if (employeeSalaryComponent.getType().equalsIgnoreCase("Percentage")) {
                            specialAllowance = basicAmt * specialAllowance / 100;
                        } else specialAllowance = employeeSalaryComponent.getValue();
                        log.debug("--------------------------------------specialAllowance:" + specialAllowance);
                    }

                    if (employeeSalaryComponent.getName().equalsIgnoreCase("CCA") && employeeSalaryComponent.getValue() != null) {
                        if (employeeSalaryComponent.getType().equalsIgnoreCase("Percentage")) {
                            cca = basicAmt * cca / 100;
                        } else cca = employeeSalaryComponent.getValue();
                        log.debug("--------------------------------------CCA:" + cca);
                    }

                    if (employeeSalaryComponent.getName().equalsIgnoreCase("GHIN") && employeeSalaryComponent.getValue() != null) {
                        if (employeeSalaryComponent.getType().equalsIgnoreCase("Percentage")) {
                            ghin = basicAmt * ghin / 100;
                        } else ghin = employeeSalaryComponent.getValue();
                        log.debug("--------------------------------------GHIN:" + ghin);
                    }

                    if (employeeSalaryComponent.getName().equalsIgnoreCase("GLIN") && employeeSalaryComponent.getValue() != null) {
                        if (employeeSalaryComponent.getType().equalsIgnoreCase("Percentage")) {
                            glin = basicAmt * glin / 100;
                        } else glin = employeeSalaryComponent.getValue();
                        log.debug("--------------------------------------GHIN:" + glin);
                    }
                }
            }

            System.out.println("--------------------------------------basic amt:" + basicAmt);

            // basicAmt >0 do deduction factors calculation
            if (basicAmt > 0) {
                long companyWorkingDays = findTotalWorkingDays(startDate, endDate);
                double leaveDays = findLeaveDaysInMonth(companyId2, employeeId, status, monthName);
                log.debug("--------------------------------------Total leave Days:" + leaveDays);
                log.debug("-----------------------------allApprovedleaveDates: " + allApprovedleaveDates.toString());
                log.debug("--------------------------------------Total noOfLWPLeaves:" + noOfLWPLeaves);

                double empActualWorkDays = companyWorkingDays - leaveDays;
                log.debug("-----------------------------empActualWorkDays:" + empActualWorkDays);

                // log.debug("-----------------------------workingDayDates: " +
                // workingDayDates.toString());
                // workingDayDates.removeAll(allApprovedleaveDates);
                // log.debug("----------After removing leave days empActualWorkDays:" +
                // workingDayDates.toString());
                // //Find absent days & dates
                // long absentDays = 0;
                // //attendanceDays=empActualWorkDays, if not it is absent day
                // // if (!(attendanceDays == empActualWorkDays)) {
                // log.debug("----------------attendanceDays:" + attendanceDays + "&
                // empActualWorkDays:" + empActualWorkDays + "Not same");
                // for (LocalDate dates : workingDayDates) {
                // if (!attendanceDates.contains(dates)) {
                // absentDays++;
                // absentDaysDates.add(dates);
                // }
                // }
                // // }
                // log.debug("-----------------------absentDaysDates:" + absentDaysDates);

                long attendanceDays = attendanceDays(empId);
                log.debug("-----------------------attendanceDays:" + attendanceDays);
                log.debug("-----------------------attendance Day Dates:" + attendanceDates);

                double absentDays = empActualWorkDays - attendanceDays;
                log.debug("-----------------------absentDays:" + absentDays);

                double paidDays = 0;

                // If not any attendance mark,Save all the salary calculation with zero's
                if (attendanceDays == 0) {
                    basicAmt = 0.0;
                    da = 0.0;
                    hra = 0.0;
                    medical = 0.0;
                    conveyance = 0.0;
                    specialAllowance = 0.0;

                    cca = 0.0;
                    ghin = 0.0;
                    glin = 0.0;

                    // Additions
                    saveDA(salary, da, da);
                    saveHRA(salary, hra, hra);
                    saveMedicalAmount(salary, medical, medical);
                    saveConveyanceAmount(salary, conveyance, conveyance);
                    saveSpecialAllowance(salary, specialAllowance, specialAllowance);
                    saveBasic(salary, basicAmt, specialAllowance);
                    saveCCA(salary, cca, cca);

                    // Deductions
                    saveGHIN(salary, ghin);
                    saveGLIN(salary, glin);

                    incomeTaxAmountMonthly = 0.0;
                    lWPAmount = 0;
                    absentAmount = 0.0;
                    pf = 0.0;
                    esicAmt = 0.0;

                    salary.setType("PF");
                    salary.setAmount(pf);
                    salary.setIsdeduction(true);
                    salaryService.save(salary);

                    salary.setType("ESIC");
                    salary.setAmount(esicAmt);
                    salary.setIsdeduction(true);
                    salaryService.save(salary);

                    saveAbsent(salary, absentAmount);
                    // saveLWPLeave(salary, lWPAmount);
                    saveIncomeTaxAmountMonthly(salary, incomeTaxAmountMonthly);

                    monthlyNetSalary = 0;
                    monthlyGrossPay = 0;
                    totalDeduction = 0;
                    lossOfPay = 0;
                    savePaySlip(
                        companyId,
                        empId,
                        monthName,
                        yearName,
                        monthlyNetSalary,
                        monthlyGrossPay,
                        totalDeduction,
                        lossOfPay,
                        paidDays,
                        monthlyGrossPay
                    );
                } else {
                    paidDays = daysInMonth - (absentDays + noOfLWPLeaves);

                    // save Actual and earning Additions in salary as addition
                    double earningDA = (int) Math.round((da / daysInMonth) * paidDays);
                    double earningHRA = (int) Math.round((hra / daysInMonth) * paidDays);
                    double earningMedical = (int) Math.round((medical / daysInMonth) * paidDays);
                    double earningConveyance = (int) Math.round((conveyance / daysInMonth) * paidDays);
                    double earningSpecialAllowance = (int) Math.round((specialAllowance / daysInMonth) * paidDays);
                    double earningBasic = (int) Math.round((basicAmt / daysInMonth) * paidDays);
                    double earningCca = (int) Math.round((cca / daysInMonth) * paidDays);

                    double totalEarning =
                        earningDA + earningHRA + earningMedical + earningConveyance + earningSpecialAllowance + earningBasic + earningCca;

                    // Additions
                    saveDA(salary, da, earningDA);
                    saveHRA(salary, hra, earningHRA);
                    saveMedicalAmount(salary, medical, earningMedical);
                    saveConveyanceAmount(salary, conveyance, earningConveyance);
                    saveSpecialAllowance(salary, specialAllowance, earningSpecialAllowance);
                    saveBasic(salary, basicAmt, earningBasic);
                    saveCCA(salary, cca, earningCca);

                    // Deductions
                    saveGHIN(salary, ghin);
                    saveGLIN(salary, glin);

                    // monthly Gross salary
                    monthlyGrossPay = basicAmt + da + hra + medical + conveyance + specialAllowance + cca;

                    log.debug("----------------Monthly GrossPay:" + monthlyGrossPay);
                    double CTC = monthlyGrossPay * 12;
                    log.debug("----------------CTC: " + CTC);

                    // ESI amount
                    esicAmt = findESIAmount(employeeId, empId, companyId2, status, monthlyGrossPay, salary);

                    // PF amount
                    pf = findPFAmount(employeeId, empId, companyId2, status, earningBasic, earningDA, salary);

                    // Income Tax calculation on CTC
                    incomeTaxAmountYearly = getIncomeTaxAmount(employeeId, status, companyId2, CTC);
                    log.debug("----------------incomeTaxAmountYearly before cess: " + incomeTaxAmountYearly);

                    // cess calculation on incomeTaxAmountYearly
                    double cess = getCessAmount(incomeTaxAmountYearly, status);
                    log.debug("----------------cess:" + cess);
                    incomeTaxAmountYearly = incomeTaxAmountYearly + cess;
                    log.debug("----------------incomeTaxAmountYearly after cess: " + incomeTaxAmountYearly);

                    // Surcharge calculation
                    log.debug("Taxable Income before Surcharge calculation:" + taxableIncomeAmount);
                    double surcharge = getSurchargeAmount(incomeTaxAmountYearly, taxableIncomeAmount, status);
                    log.debug("----------------surcharge:" + surcharge);

                    // Income tax payable=incomeTaxAmountYearly + surcharge
                    incomeTaxAmountYearly = incomeTaxAmountYearly + surcharge;

                    incomeTaxAmountMonthly = incomeTaxAmountYearly / 12;
                    incomeTaxAmountMonthly = (int) Math.round(incomeTaxAmountMonthly);

                    // save absent amount in salary as deduction
                    // double perDayGross = monthlyGrossPay / daysInMonth;
                    // absentAmount = monthlyGrossPay-;
                    // absentAmount = (int) Math.round(absentAmount);
                    // log.debug("----------------absentAmount:" + absentAmount);
                    // saveAbsent(salary, absentAmount);

                    // save LWP leave amount in salary as deduction
                    // lWPAmount = perDayGross * noOfLWPLeaves;
                    // saveLWPLeave(salary, lWPAmount);
                    // System.out.println("THE LWP AMOUNT :: " + lWPAmount);

                    // save absent amount in salary as deduction
                    absentAmount = monthlyGrossPay - totalEarning;
                    absentAmount = (int) Math.round(absentAmount);
                    log.debug("----------------absentAmount:" + absentAmount);
                    saveAbsent(salary, absentAmount);

                    // save professionalTax in salary as deduction
                    double professionalTax = getProfessionalTax(monthlyGrossPay, empId, status, monthName);
                    saveProfessionalTax(salary, professionalTax);

                    // save incomeTaxAmount in salary as deduction
                    saveIncomeTaxAmountMonthly(salary, incomeTaxAmountMonthly);

                    // TotalDeduction
                    totalDeduction = incomeTaxAmountMonthly + pf + esicAmt + absentAmount + professionalTax + ghin + glin;
                    // + lWPAmount +

                    // Monthly Net salary
                    monthlyNetSalary = monthlyGrossPay - totalDeduction;
                    monthlyNetSalary = (int) Math.round(monthlyNetSalary);
                    log.debug("----------------monthly Net Salary:" + monthlyNetSalary);

                    lossOfPay = (double) absentDays + noOfLWPLeaves;

                    // save salary in payslip
                    savePaySlip(
                        companyId,
                        empId,
                        monthName,
                        yearName,
                        monthlyNetSalary,
                        monthlyGrossPay,
                        totalDeduction,
                        lossOfPay,
                        paidDays,
                        totalEarning
                    );
                }
            } else {
                throw new BusinessRuleException(
                    "#Invalid input value provided",
                    "Basic salary amount is not assigned for employee having Id: " + empId
                );
            }
        } catch (NullPointerException e) {
            throw new BusinessRuleException(
                "Invalid input value provided for employee salary component basic",
                "!!!Nullvalue is assigned to: " + e.getMessage()
            );
        }
    }

    private void savePaySlip(
        long companyId,
        Long empId,
        String monthName,
        String yearName,
        double monthlyNetSalary,
        double monthlyGrossPay,
        double totalDeduction,
        double lossOfPay,
        double paidDays,
        double totalEarning
    ) {
        PaySlipDTO paySlip = new PaySlipDTO();
        paySlip.setCompanyId(companyId);
        paySlip.setEmployeeId(empId);
        paySlip.setStatus("A");
        paySlip.setMonth(monthName);
        paySlip.setYear(yearName);
        paySlip.setLastModified(Instant.now());
        paySlip.setSalary(monthlyNetSalary);
        paySlip.setSalaryStatus("Generated");
        paySlip.setSalaryDate(Instant.now());
        paySlip.setTotalEarning(totalEarning);
        paySlip.setActualGrossPay(monthlyGrossPay);
        paySlip.setTotalDeduction(totalDeduction);
        paySlip.setLossOfPay(lossOfPay);
        paySlip.setPresentDays(paidDays);
        paySlipService.save(paySlip);
    }

    private void saveSpecialAllowance(SalaryDTO salary, double specialAllowance, double specialAllowance2) {
        salary.setType("SpecialAllowance");
        salary.setIsdeduction(false);
        salary.setAmount(specialAllowance);
        salary.setEarningAmount(specialAllowance2);
        salaryService.save(salary);
    }

    private void saveConveyanceAmount(SalaryDTO salary, double conveyance, double conveyance2) {
        salary.setType("Conveyance");
        salary.setAmount(conveyance);
        salary.setEarningAmount(conveyance2);
        salary.setIsdeduction(false);
        salaryService.save(salary);
    }

    private void saveMedicalAmount(SalaryDTO salary, double medical, double medical2) {
        salary.setType("Medical");
        salary.setAmount(medical);
        salary.setEarningAmount(medical2);
        salary.setIsdeduction(false);
        salaryService.save(salary);
    }

    private void saveHRA(SalaryDTO salary, double hra, double earningHRA) {
        salary.setType("HRA");
        salary.setAmount(hra);
        salary.setEarningAmount(earningHRA);
        salary.setIsdeduction(false);
        salaryService.save(salary);
    }

    private void saveDA(SalaryDTO salary, double da, double earningDA) {
        salary.setType("DA");
        salary.setAmount(da);
        salary.setEarningAmount(earningDA);
        salary.setIsdeduction(false);
        salaryService.save(salary);
    }

    private void saveBasic(SalaryDTO salary, double basicAmt, double earningBasic) {
        salary.setType("Basic");
        salary.setAmount(basicAmt);
        salary.setEarningAmount(earningBasic);
        salary.setIsdeduction(false);
        salaryService.save(salary);
    }

    private void saveCCA(SalaryDTO salary, double cca, double earningCca) {
        salary.setType("CCA");
        salary.setAmount(cca);
        salary.setEarningAmount(earningCca);
        salary.setIsdeduction(false);
        salaryService.save(salary);
    }

    private void saveGLIN(SalaryDTO salary, double glin) {
        salary.setType("GLIN");
        salary.setAmount(glin);
        salary.setEarningAmount(null);
        salary.setIsdeduction(true);
        salaryService.save(salary);
    }

    private void saveGHIN(SalaryDTO salary, double ghin) {
        salary.setType("GHIN");
        salary.setAmount(ghin);
        salary.setEarningAmount(null);
        salary.setIsdeduction(true);
        salaryService.save(salary);
    }

    private void saveIncomeTaxAmountMonthly(SalaryDTO salary, double incomeTaxAmountMonthly) {
        salary.setType("Income Tax");
        salary.setAmount(incomeTaxAmountMonthly);
        salary.setEarningAmount(null);
        salary.setIsdeduction(true);
        salaryService.save(salary);
    }

    // private void saveLWPLeave(SalaryDTO salary, double lWPamount) {
    // salary.setType("LWPLeave");
    // salary.setAmount(lWPamount);
    // salary.setIsdeduction(true);
    // salaryService.save(salary);
    // }

    private void saveAbsent(SalaryDTO salary, double absentAmount) {
        salary.setType("Absent");
        salary.setAmount(absentAmount);
        salary.setEarningAmount(null);
        salary.setIsdeduction(true);
        salaryService.save(salary);
    }

    private void saveProfessionalTax(SalaryDTO salary, double professionalTax) {
        salary.setType("ProfessionalTax");
        salary.setAmount(professionalTax);
        salary.setEarningAmount(null);
        salary.setIsdeduction(true);
        salaryService.save(salary);
    }

    // Get ProfessionalTax
    private double getProfessionalTax(double monthlyGrossPay, Long empId, StringFilter status, String monthName) {
        Optional<EmployeeDTO> employee = employeeService.findOne(empId);
        String gender = employee.get().getGender();

        MasterLookupCriteria lookupCriteria = new MasterLookupCriteria();
        StringFilter type2 = new StringFilter();
        type2.setEquals("Gender");
        lookupCriteria.setType(type2);
        lookupCriteria.setStatus(status);
        double professionalTaxAmt = 0;

        List<MasterLookupDTO> lookupList = masterLookupQueryService.findByCriteria(lookupCriteria);
        if (lookupList != null) {
            for (MasterLookupDTO masterLookup : lookupList) {
                log.debug("-----------masterLookup DTO:" + masterLookup.getValue());
                if (masterLookup.getValue().equalsIgnoreCase(gender)) {
                    if (
                        masterLookup.getValue().equalsIgnoreCase("Male") && monthlyGrossPay > 7500 && monthlyGrossPay < 10000
                    ) professionalTaxAmt = 175; else if (
                        masterLookup.getValue().equalsIgnoreCase("Female") && (monthlyGrossPay > 7500 && monthlyGrossPay < 10000)
                    ) professionalTaxAmt = 0; else if (
                        (masterLookup.getValue().equalsIgnoreCase("Male") || masterLookup.getValue().equalsIgnoreCase("Female")) &&
                        monthlyGrossPay > 10000 &&
                        !monthName.equalsIgnoreCase("Feb")
                    ) professionalTaxAmt = 200; else if (
                        (masterLookup.getValue().equalsIgnoreCase("Male") || masterLookup.getValue().equalsIgnoreCase("Female")) &&
                        monthlyGrossPay > 10000 &&
                        monthName.equalsIgnoreCase("Feb")
                    ) professionalTaxAmt = 300;
                }
            }
        }

        return professionalTaxAmt;
    }

    // Get SurchargeAmount
    private double getSurchargeAmount(double incomeTaxAmountYearly, double taxableIncomeAmount, StringFilter status) {
        MasterLookupCriteria lookupCriteria = new MasterLookupCriteria();
        StringFilter type2 = new StringFilter();
        type2.setEquals("Surcharge Rate");
        lookupCriteria.setType(type2);
        lookupCriteria.setStatus(status);

        List<MasterLookupDTO> lookupList = masterLookupQueryService.findByCriteria(lookupCriteria);
        double surcharge = 0;
        if (lookupList != null) {
            for (MasterLookupDTO masterLookup : lookupList) {
                log.debug("-----------masterLookup DTO:" + masterLookup.toString());
                if (taxableIncomeAmount > (Double.parseDouble(masterLookup.getName()))) {
                    surcharge = incomeTaxAmountYearly * (Double.parseDouble(masterLookup.getValue()) / 100);
                    log.debug(
                        "-----------Double.parseDouble(masterLookup.getValue())/100:" + Double.parseDouble(masterLookup.getValue()) / 100
                    );
                }
            }
        }
        return surcharge;
    }

    // Get CessAmount
    private double getCessAmount(double incomeTaxAmountYearly, StringFilter status) {
        MasterLookupCriteria masterLookupCriteria = new MasterLookupCriteria();
        StringFilter type = new StringFilter();
        type.setEquals("Income Tax cess %");
        masterLookupCriteria.setType(type);
        masterLookupCriteria.setStatus(status);

        List<MasterLookupDTO> list3 = masterLookupQueryService.findByCriteria(masterLookupCriteria);
        double cess = 0;
        if (list3 != null) {
            for (MasterLookupDTO masterLookup : list3) {
                cess = incomeTaxAmountYearly * (Double.parseDouble(masterLookup.getValue()) / 100);
            }
        }
        return cess;
    }

    // PF calculation
    private double findPFAmount(
        LongFilter employeeId,
        Long empId,
        LongFilter companyId2,
        StringFilter status,
        double earningBasicAmt,
        double earningDA,
        SalaryDTO salary
    ) {
        // Added criteria for employee id, status and comapny id
        PfDetailsCriteria pfDetailsCriteria = new PfDetailsCriteria();
        pfDetailsCriteria.setEmployeeId(employeeId);
        pfDetailsCriteria.setStatus(status);
        pfDetailsCriteria.setCompanyId(companyId2);

        List<PfDetailsDTO> list1 = pfDetailsQueryService.findByCriteria(pfDetailsCriteria);
        double pf = 0;
        if (list1 != null) {
            for (PfDetailsDTO pfDetail : list1) {
                if (pfDetail.getEmployeeId() != null && pfDetail.getEmployeeId() == empId && pfDetail.getTotalPfRate() != null) {
                    pf = (pfDetail.getTotalPfRate() / 100) * (earningBasicAmt + earningDA);

                    salary.setType("PF");
                    salary.setAmount(pf);
                    salary.setEarningAmount(null);
                    salary.setIsdeduction(true);
                    salaryService.save(salary);
                }
            }
        }
        return pf;
    }

    // ESIC calculation
    private double findESIAmount(
        LongFilter employeeId,
        Long empId,
        LongFilter companyId2,
        StringFilter status,
        double monthlyGrossPay,
        SalaryDTO salary
    ) {
        // master lookup criteria for get minimum esic amount value
        MasterLookupCriteria masterLookupCriteria = new MasterLookupCriteria();

        StringFilter type = new StringFilter();
        type.setEquals("esi details");
        masterLookupCriteria.setType(type);
        masterLookupCriteria.setStatus(status);

        List<MasterLookupDTO> list3 = masterLookupQueryService.findByCriteria(masterLookupCriteria);
        double esicAmt = 0;
        if (list3 != null) {
            for (MasterLookupDTO masterLookup : list3) {
                if (monthlyGrossPay < Double.parseDouble(masterLookup.getValue())) {
                    EsiDetailsCriteria esiDetailsCriteria = new EsiDetailsCriteria();

                    // Added criteria for employee id, status and comapny id
                    esiDetailsCriteria.setEmployeeId(employeeId);
                    esiDetailsCriteria.setStatus(status);
                    esiDetailsCriteria.setCompanyId(companyId2);

                    List<EsiDetailsDTO> list2 = esiDetailsQueryService.findByCriteria(esiDetailsCriteria);
                    if (list2 != null) {
                        for (EsiDetailsDTO esiDetail : list2) {
                            if (
                                esiDetail.getEmployeeId() != null &&
                                esiDetail.getEmployeeId() == empId &&
                                esiDetail.getTotalEsiRate() != null
                            ) {
                                esicAmt = (monthlyGrossPay * (esiDetail.getTotalEsiRate() / 100));

                                log.debug("--------------------------------------esicAmt:" + esicAmt);
                                salary.setType("ESIC");
                                salary.setAmount(esicAmt);
                                salary.setIsdeduction(true);
                                salary.setEarningAmount(null);
                                salaryService.save(salary);
                            }
                        }
                    }
                }
            }
        }

        return esicAmt;
    }

    private double getIncomeTaxAmount(LongFilter employeeId, StringFilter status, LongFilter companyId2, double cTC) {
        double totalTaxAmount = 0;
        double taxableIncome = 0;
        // long regimeId;

        // Check the CTC amount comes in tax brackets
        TaxSlabCriteria taxSlabCriteria = new TaxSlabCriteria();
        taxSlabCriteria.setCompanyId(companyId2);
        taxSlabCriteria.setStatus(status);

        boolean amountIsTaxable = false;
        List<TaxSlabDTO> taxSlabList = taxSlabQueryService.findByCriteria(taxSlabCriteria);
        if (taxSlabList != null) {
            for (TaxSlabDTO taxSlab : taxSlabList) {
                if (
                    (taxSlab.getAmtFrom() != null && taxSlab.getAmtTo() != null) &&
                    taxSlab.getTaxPercentage() != null &&
                    (taxSlab.getAmtFrom() < cTC && cTC > taxSlab.getAmtTo()) &&
                    taxSlab.getTaxPercentage() > 0
                ) {
                    amountIsTaxable = true;
                    // regimeId=taxSlab.getTaxRegimeId();
                }
            }
        }

        // To find Taxable Income amount by deducting employee exemptions
        if (amountIsTaxable) {
            double totalExemAmount = 0;
            EmployeeExemptionCriteria employeeExemptionCriteria = new EmployeeExemptionCriteria();
            employeeExemptionCriteria.setCompanyId(companyId2);
            employeeExemptionCriteria.setStatus(status);
            employeeExemptionCriteria.setEmployeeId(employeeId);

            StringFilter exemptionStatus = new StringFilter();
            exemptionStatus.setEquals("Approved");
            employeeExemptionCriteria.setExemptionStatus(exemptionStatus);

            List<EmployeeExemptionDTO> exemptionList = employeeExemptionQueryService.findByCriteria(employeeExemptionCriteria);
            if (exemptionList != null) {
                log.debug("----------After find by criteria Exemption List:" + exemptionList);
                for (EmployeeExemptionDTO exemption : exemptionList) {
                    double exempMaxLimit = 0;
                    double exempAmount = 0;
                    long taxExempSectionId = 0;
                    if (exemption.getTaxExempSectionId() != null) {
                        taxExempSectionId = exemption.getTaxExempSectionId();
                    }

                    Optional<TaxExempSectionDTO> taxExempSection = taxExempSectionService.findOne(taxExempSectionId);
                    if (taxExempSection.isPresent() && taxExempSection.get().getTaxExempSectionId() != null) {
                        long taxExempSectionId2 = taxExempSection.get().getTaxExempSectionId();

                        Optional<TaxExempSectionDTO> taxExempSection2 = taxExempSectionService.findOne(taxExempSectionId2);
                        if (taxExempSection2.get().getMaxlimit() != null && taxExempSection2.isPresent()) {
                            exempMaxLimit = taxExempSection2.get().getMaxlimit();
                        }
                    }

                    for (EmployeeExemptionDTO empExemption : exemptionList) {
                        if (
                            empExemption.getAmount() != null &&
                            empExemption.getTaxExempSectionId() == taxExempSectionId &&
                            !(empExemption.getStatus().equalsIgnoreCase("D"))
                        ) {
                            log.debug("exemption.getAmount(): " + empExemption.getAmount() + " for id" + empExemption.getId());
                            exempAmount += empExemption.getAmount();
                            log.debug("----------!exempAmount += exemption.getAmount():" + exempAmount);
                            empExemption.setStatus("D");
                            if (exempAmount > exempMaxLimit) {
                                exempAmount = exempMaxLimit;
                            }
                        }
                    }
                    log.debug("----------After inner for each Exemp Amount:" + exempAmount);
                    log.debug("----------Inner Exemption List:" + exemptionList);

                    totalExemAmount = totalExemAmount + exempAmount;
                    log.debug("----------totalExemAmount:" + totalExemAmount + " for tax exemption section id" + taxExempSectionId);
                }
                log.debug("----------Outer final Exemption List:" + exemptionList);
                log.debug("----------Final totalExemAmount:" + totalExemAmount);
            }
            taxableIncome = cTC - totalExemAmount;
            taxableIncomeAmount = taxableIncome;
            log.debug("--------------Taxable Income(CTC-totalExemAmount):" + taxableIncome);

            // Calculate the tax amount from applicable tax slabs on taxable Income
            totalTaxAmount = getTotalTaxAmount(taxSlabList, taxableIncome);
            log.debug("----------TotalTaxAmount after method getTotalTaxAmount:" + totalTaxAmount);
        } else {
            totalTaxAmount = 0;
        }
        return totalTaxAmount;
    }

    // Calculate the tax amount from applicable tax slabs for taxable Income
    private double getTotalTaxAmount(List<TaxSlabDTO> taxSlabList, double taxableIncome) {
        double totalTaxAmount = 0;
        if (taxSlabList != null) {
            for (TaxSlabDTO taxSlab : taxSlabList) {
                int isPrevSlabTaxCalculate = 0;
                double from;
                if (taxSlab.getAmtTo() != null && taxSlab.getAmtTo() != null && taxSlab.getTaxPercentage() != null) {
                    log.debug("Tax slab is: " + taxSlab.getAmtFrom() + "-" + taxSlab.getAmtTo());
                }

                if (
                    taxSlab.getAmtTo() != null &&
                    taxSlab.getAmtTo() != null &&
                    taxSlab.getTaxPercentage() != null &&
                    taxableIncome < taxSlab.getAmtTo() &&
                    taxableIncome > taxSlab.getAmtFrom()
                ) {
                    log.debug("--------------TaxableIncome<= " + taxSlab.getAmtTo() + " TaxableIncome >" + taxSlab.getAmtFrom());
                    double taxAmount = taxableIncome * (taxSlab.getTaxPercentage() / 100);
                    totalTaxAmount = totalTaxAmount + taxAmount;
                    taxableIncome -= taxableIncome;

                    isPrevSlabTaxCalculate = 1;
                    log.debug("--------------TaxAmount:" + taxAmount);
                    log.debug("--------------totalTaxAmount:" + totalTaxAmount);
                    log.debug("--------------taxableIncome(taxableIncome-=taxableIncome):" + taxableIncome);
                } else if (taxSlab.getAmtTo() != null && taxSlab.getTaxPercentage() != null && taxableIncome > taxSlab.getAmtTo()) {
                    log.debug("--------------taxableIncome> " + taxSlab.getAmtTo());
                    double taxAmount = (taxSlab.getAmtTo() - taxSlab.getAmtFrom()) * (taxSlab.getTaxPercentage() / 100);
                    totalTaxAmount = totalTaxAmount + taxAmount;

                    isPrevSlabTaxCalculate = 1;
                    log.debug("--------------TaxAmount:" + taxAmount);
                    log.debug("--------------totalTaxAmount:" + totalTaxAmount);
                    log.debug("taxableIncome-taxSlabAmtTo:" + taxableIncome + "-" + taxSlab.getAmtTo());
                    taxableIncome = taxableIncome - (taxSlab.getAmtTo() - taxSlab.getAmtFrom());
                    log.debug("taxableIncome:" + taxableIncome);
                } else if (taxSlab.getAmtFrom() != null && taxSlab.getTaxPercentage() != null && taxableIncome < taxSlab.getAmtFrom()) {
                    log.debug("--------------taxableIncome< " + taxSlab.getAmtFrom() + " isPrevSlabTaxCalculate==1");
                    double taxAmount = taxableIncome * (taxSlab.getTaxPercentage() / 100);
                    totalTaxAmount = totalTaxAmount + taxAmount;
                    taxableIncome -= taxableIncome;

                    log.debug("--------------TaxAmount:" + taxAmount);
                    log.debug("--------------totalTaxAmount:" + totalTaxAmount);
                    log.debug("--------------taxableIncome(taxableIncome-=taxableIncome):" + taxableIncome);
                }
            }
        }

        return totalTaxAmount;
    }

    private double getPerDayWorkingHours(LongFilter companyId2, Long empId, StringFilter status) {
        // Get employment type
        long employmentType1 = 0;
        Optional<EmployeeDTO> employee = employeeService.findOne(empId);
        if (employee.isPresent() && employee.get().getEmploymentTypeId() != null) {
            employmentType1 = employee.get().getEmploymentTypeId();
        }

        WorkingHoursCriteria criteria = new WorkingHoursCriteria();
        LongFilter employmentType = new LongFilter();
        employmentType.setEquals(employmentType1);

        StringFilter refTable = new StringFilter();
        refTable.setEquals("Company");

        criteria.setCompanyId(companyId2);
        criteria.setStatus(status);
        criteria.setEmploymentTypeId(employmentType);
        criteria.setRefTable(refTable);
        criteria.setRefTableId(companyId2);
        double noOforkingHours = 0;
        List<WorkingHoursDTO> workingHoursList = workingHoursQueryService.findByCriteria(criteria);
        if (workingHoursList != null) {
            for (WorkingHoursDTO WorkingHours : workingHoursList) {
                if (WorkingHours.getNoOfHours() != null) {
                    noOforkingHours = WorkingHours.getNoOfHours();
                }
            }
        }

        return noOforkingHours;
    }

    // Get the count of current month Standard working days of company
    private long findTotalWorkingDays(LocalDate startDate, LocalDate endDate) {
        long totalWorkingDays = 0;
        LocalDate date = startDate;

        while (date.isBefore(endDate) || date.isEqual(endDate)) {
            log.debug("--------Find TotalWorkingDays Checking for date: " + date);
            if (!isDayOff(date) && !isHoliday(date)) {
                totalWorkingDays++;
                workingDayDates.add(date);
                log.debug("------------Added date to workingDayDates: " + date);
                log.debug("------------WorkingDayDates array: " + workingDayDates.toString());
            }
            date = date.plusDays(1);
        }
        log.debug(
            "-----------------Total Working Days: " + totalWorkingDays + " in between start date:" + startDate + " end date: " + endDate
        );
        log.debug(
            "-----------------Total Working DATES: " + workingDayDates + " in between start date:" + startDate + " end date: " + endDate
        );
        return totalWorkingDays;
    }

    // Get the count of current month LeaveDays of employee
    private double findLeaveDaysInMonth(LongFilter companyId2, LongFilter employeeId, StringFilter status, String monthName) {
        LeaveApplicationCriteria criteria = new LeaveApplicationCriteria();
        criteria.setCompanyId(companyId2);
        criteria.setEmployeId(employeeId);
        criteria.setStatus(status);

        StringFilter leaveStatus = new StringFilter();
        leaveStatus.setEquals("Approved");
        criteria.setLeaveStatus(leaveStatus);

        double noOfLeaves = 0;
        List<LeaveApplicationDTO> leaveApplicationList = leaveApplicationQueryService.findByCriteria(criteria);
        if (leaveApplicationList != null) {
            for (LeaveApplicationDTO leaveApplication : leaveApplicationList) {
                Instant fromdate = leaveApplication.getFormDate();
                Instant toDate1 = leaveApplication.getToDate();
                log.debug("------------------LeaveApplication fromdate:" + fromdate + "and toDate:" + toDate1);

                LocalDate fromdate2 = convertToLocalDate(fromdate);
                LocalDate toDate2 = convertToLocalDate(toDate1);

                LocalDate date = fromdate2;

                while (date.isBefore(toDate2) || date.isEqual(toDate2)) {
                    log.debug("------------------------------In while checking for date:" + date);
                    Month month = date.getMonth();
                    String nameOfMonth = month.getDisplayName(TextStyle.FULL, Locale.US);

                    if (nameOfMonth.equalsIgnoreCase(monthName)) {
                        LeaveTypeCriteria leaveTypeCriteria = new LeaveTypeCriteria();

                        String leaveType = leaveApplication.getLeaveType();
                        StringFilter leaveType1 = new StringFilter();
                        leaveType1.setEquals(leaveType);
                        leaveTypeCriteria.setLeaveType(leaveType1);

                        boolean hasEarned = false;
                        String halfDayType = null;
                        List<LeaveTypeDTO> leaveTypeList = leaveTypeQueryService.findByCriteria(leaveTypeCriteria);
                        if (leaveTypeList != null) {
                            for (LeaveTypeDTO DTO : leaveTypeList) {
                                hasEarned = DTO.getHasEarned();
                                halfDayType = DTO.getLeaveType();
                            }
                        }

                        if (!isDayOff(date) && !isHoliday(date) && workingDayDates.contains(date)) {
                            //
                            if (halfDayType.equalsIgnoreCase("Half Day")) noOfLeaves = noOfLeaves + (0.5); else noOfLeaves++;

                            allApprovedleaveDates.add(date);
                            // Get the count of current month LWP LeaveDays of employee
                            if (!hasEarned) {
                                if (halfDayType.equalsIgnoreCase("Half Day")) noOfLWPLeaves = noOfLWPLeaves + (0.5); else noOfLWPLeaves++;
                            }
                        }
                        log.debug("--------------nameOfMonth.equalsIgnoreCase(monthName) noOfLeaves: " + noOfLeaves);
                    }
                    date = date.plusDays(1);
                }
            } // for
        }

        return noOfLeaves;
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

    public boolean isHoliday(LocalDate date) {
        HolidayCriteria holidayCriteria = new HolidayCriteria();
        List<HolidayDTO> holidayList = holidayQueryService.findByCriteria(holidayCriteria);
        ArrayList<LocalDate> holidayDates = new ArrayList<>();
        if (holidayList != null) {
            for (HolidayDTO holiday : holidayList) {
                LocalDate holidayLocalDate = convertToLocalDate(holiday.getHolidayDate());
                holidayDates.add(holidayLocalDate);
            }
        }
        return holidayDates.contains(date);
    }

    private static LocalDate convertToLocalDate(Instant instantDate) {
        ZoneId zoneId = ZoneId.of("Asia/Kolkata");
        ZonedDateTime zonedDateTimeFrom1 = instantDate.atZone(zoneId); // convert the Instant to a ZonedDateTime in the
        // default time zone
        LocalDate localDate = zonedDateTimeFrom1.toLocalDate();
        return localDate;
    }

    // Create an ArrayList object to hold the dates
    List<LocalDate> attendanceDates = new ArrayList<>();

    // Get the count of current month attendance of employee
    @Transactional(readOnly = true)
    public long attendanceDays(long empId) {
        Pageable pageable = PageRequest.of(0, 10);
        log.debug("------------    In attendanceDays method");
        // Get the current month's start and end dates
        LocalDate startDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

        // Set the start and end dates in the criteria to filter by the current month
        Instant startInstant = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endInstant = endDate.atStartOfDay(ZoneId.systemDefault()).plusHours(23).plusMinutes(59).plusSeconds(59).toInstant();

        AttendanceCriteria attendanceCriteria = new AttendanceCriteria();

        InstantFilter dateFilter = new InstantFilter();
        dateFilter.setGreaterThanOrEqual(startInstant);
        dateFilter.setLessThanOrEqual(endInstant);
        attendanceCriteria.setDate(dateFilter);

        LongFilter empId1 = new LongFilter();
        empId1.setEquals(empId);
        attendanceCriteria.setEmployeeId(empId1);

        log.debug("CURRENT COUNT by criteria: {}", attendanceCriteria);
        Page<AttendanceDTO> page = attendanceQueryService.findByCriteria(attendanceCriteria, pageable);
        List<AttendanceDTO> list = page.getContent();

        // Add the attendanceDate to the ArrayList
        for (AttendanceDTO attendance : list) {
            Instant date = attendance.getDate();
            LocalDate attendanceDate = convertToLocalDate(date);
            attendanceDates.add(attendanceDate);
        }

        // Return the count
        return page.getTotalElements();
    }
}
