package com.techvg.hrms.web.rest;

import java.io.IOException;
import java.util.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * REST controller for managing {@link reports}.
 */
@RestController
@RequestMapping("/api/reports")
public class ReportResource {

    private final Logger log = LoggerFactory.getLogger(ReportResource.class);

    @Value("${custom.jasper.url}")
    private String reportServerUrl;

    @Value("${custom.jasper.username}")
    private String reportServerUseranme;

    @Value("${custom.jasper.password}")
    private String reportServerPassword;

    private final RestTemplate restTemplate;

    public ReportResource(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
        ((RestTemplate) this.restTemplate).setErrorHandler(
                new DefaultResponseErrorHandler() {
                    @Override
                    public void handleError(ClientHttpResponse response) throws IOException {
                        if (response.getRawStatusCode() != 400) {
                            super.handleError(response);
                        }
                    }
                }
            );
    }

    /**
     * {@code GET  /member/:id} : get the "id" employee.
     *
     * @param id the id of the employeeDTO to retrieve.
     * @return
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *          {@code 404 (Not Found)}.
     */
    @GetMapping("/downloadByName") //Pay_Slip_Report
    public ResponseEntity<byte[]> getByName(
        @RequestParam(name = "format", required = true) String format,
        @RequestParam(name = "name", required = true) String name,
        @RequestParam(name = "quote_id", required = false) Long quote_id,
        @RequestParam(name = "month", required = false) String month,
        @RequestParam(name = "Year", required = false) String Year
    ) {
        log.debug("REST request to get report : {}" + name);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.reportServerUrl + "/VGHRMS/Reports/" + name + "." + format);
        if (StringUtils.isNotEmpty(quote_id.toString())) builder.queryParam("quote_id", quote_id);
        if (StringUtils.isNotEmpty(quote_id.toString())) builder.queryParam("month", month);
        if (StringUtils.isNotEmpty(quote_id.toString())) builder.queryParam("Year", Year);
        String auth = this.reportServerUseranme + ":" + this.reportServerPassword;
        String base64Creds = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(base64Creds);
        HttpEntity request = new HttpEntity(httpHeaders);

        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, byte[].class);
    }

    /**
     * {@code GET  /member/:id} : get the "id" employee.
     *
     * @param id the id of the employeeDTO to retrieve.
     * @return
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/downloadByNameDate") //Holiday
    public ResponseEntity<byte[]> getByDateName(
        @RequestParam(name = "format", required = true) String format,
        @RequestParam(name = "name", required = true) String name,
        @RequestParam(name = "start_date", required = false) String start_date,
        @RequestParam(name = "end_date", required = false) String end_date
    ) {
        log.debug("REST request to get report : {}" + name);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.reportServerUrl + "/VGHRMS/Reports/" + name + "." + format);
        if (StringUtils.isNotEmpty(start_date.toString())) builder.queryParam("start_date", start_date);
        if (StringUtils.isNotEmpty(end_date.toString())) builder.queryParam("end_date", end_date);

        String auth = this.reportServerUseranme + ":" + this.reportServerPassword;
        String base64Creds = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(base64Creds);
        HttpEntity request = new HttpEntity(httpHeaders);

        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, byte[].class);
    }

    @GetMapping("/downloadByEmployee_List_Report") //EmployeeList
    public ResponseEntity<byte[]> getByName(
        @RequestParam(name = "format", required = true) String format,
        @RequestParam(name = "name", required = true) String name,
        @RequestParam(name = "branchId", required = false) Long branchId,
        @RequestParam(name = "start_date", required = false) String start_date,
        @RequestParam(name = "end_date", required = false) String end_date,
        @RequestParam(name = "designationId", required = false) Long designationId
    ) {
        log.debug("REST request to get report : {}" + name);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.reportServerUrl + "/VGHRMS/Reports/" + name + "." + format);
        if (StringUtils.isNotEmpty(branchId.toString())) builder.queryParam("branchId", branchId);
        if (StringUtils.isNotEmpty(start_date.toString())) builder.queryParam("start_date", start_date);
        if (StringUtils.isNotEmpty(end_date.toString())) builder.queryParam("end_date", end_date);
        if (StringUtils.isNotEmpty(designationId.toString())) builder.queryParam("designationId", designationId);
        String auth = this.reportServerUseranme + ":" + this.reportServerPassword;
        String base64Creds = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(base64Creds);
        HttpEntity request = new HttpEntity(httpHeaders);

        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, byte[].class);
    }

    @GetMapping("/downloadByPromotion_Due_ListMonthly")
    public ResponseEntity<byte[]> getByName(
        @RequestParam(name = "format", required = true) String format,
        @RequestParam(name = "name", required = true) String name,
        @RequestParam(name = "branchId", required = false) Long branchId
    ) {
        log.debug("REST request to get report : {}" + name);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.reportServerUrl + "/VGHRMS/Reports/" + name + "." + format);
        if (StringUtils.isNotEmpty(branchId.toString())) builder.queryParam("branchId", branchId);
        String auth = this.reportServerUseranme + ":" + this.reportServerPassword;
        String base64Creds = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(base64Creds);
        HttpEntity request = new HttpEntity(httpHeaders);

        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, byte[].class);
    }

    @GetMapping("/downloadByEmployee_Monthly_Attendance")
    public ResponseEntity<byte[]> getByName(
        @RequestParam(name = "format", required = true) String format,
        @RequestParam(name = "name", required = true) String name,
        @RequestParam(name = "month", required = false) String month,
        @RequestParam(name = "Year", required = false) String Year
    ) {
        log.debug("REST request to get report : {}" + name);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.reportServerUrl + "/VGHRMS/Reports/" + name + "." + format);
        if (StringUtils.isNotEmpty(month.toString())) builder.queryParam("month", month);
        if (StringUtils.isNotEmpty(Year.toString())) builder.queryParam("Year", Year);
        String auth = this.reportServerUseranme + ":" + this.reportServerPassword;
        String base64Creds = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(base64Creds);
        HttpEntity request = new HttpEntity(httpHeaders);

        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, byte[].class);
    }

    @GetMapping("/downloadByPayroll") //EmployeeMonthlyDeduction
    public ResponseEntity<byte[]> getByName(
        @RequestParam(name = "format", required = true) String format,
        @RequestParam(name = "name", required = true) String name,
        @RequestParam(name = "month", required = false) String month,
        @RequestParam(name = "Year", required = false) String Year,
        @RequestParam(name = "branchId", required = false) Long branchId
    ) {
        log.debug("REST request to get report : {}" + name);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.reportServerUrl + "/VGHRMS/Reports/" + name + "." + format);
        if (StringUtils.isNotEmpty(month.toString())) builder.queryParam("month", month);
        if (StringUtils.isNotEmpty(Year.toString())) builder.queryParam("Year", Year);
        if (StringUtils.isNotEmpty(branchId.toString())) builder.queryParam("branchId", branchId);
        String auth = this.reportServerUseranme + ":" + this.reportServerPassword;
        String base64Creds = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(base64Creds);
        HttpEntity request = new HttpEntity(httpHeaders);

        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, byte[].class);
    }

    @GetMapping("/downloadByEmployee_Training_monthlyList") //Retirement_Due_List_Monthly
    public ResponseEntity<byte[]> getByDateName(
        @RequestParam(name = "format", required = true) String format,
        @RequestParam(name = "name", required = true) String name,
        @RequestParam(name = "start_date", required = false) String start_date,
        @RequestParam(name = "end_date", required = false) String end_date,
        @RequestParam(name = "branchId", required = false) Long branchId
    ) {
        log.debug("REST request to get report : {}" + name);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.reportServerUrl + "/VGHRMS/Reports/" + name + "." + format);
        if (StringUtils.isNotEmpty(start_date.toString())) builder.queryParam("start_date", start_date);
        if (StringUtils.isNotEmpty(end_date.toString())) builder.queryParam("end_date", end_date);
        if (StringUtils.isNotEmpty(branchId.toString())) builder.queryParam("branchId", branchId);

        String auth = this.reportServerUseranme + ":" + this.reportServerPassword;
        String base64Creds = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(base64Creds);
        HttpEntity request = new HttpEntity(httpHeaders);

        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, byte[].class);
    }
}
