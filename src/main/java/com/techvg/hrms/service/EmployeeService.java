package com.techvg.hrms.service;

import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.repository.EmployeeRepository;
import com.techvg.hrms.service.dto.AddressDTO;
import com.techvg.hrms.service.dto.ContactsDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.mapper.EmployeeMapper;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
@Transactional
public class EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final AddressService addressService;

    private final ContactsService contactsService;

    private static final String ENTITY_NAME = "employee";

    @Autowired
    private ValidationService validationService;

    public EmployeeService(
        EmployeeRepository employeeRepository,
        EmployeeMapper employeeMapper,
        AddressService addressService,
        ContactsService contactsService
    ) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.addressService = addressService;
        this.contactsService = contactsService;
    }

    /**
     * Save a employee.
     *
     * @param employeeDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        log.debug("Request to save Employee : {}", employeeDTO);

        Employee employee = employeeMapper.toEntity(employeeDTO);
        validationService.validateMethod(employee);

        employee = employeeRepository.save(employee);
        
     // ----------------Save/update address of employee-------------
        
        List<AddressDTO> addressList = new ArrayList<>();
        if (employeeDTO.getAddressList() != null) {
        	addressList = addressService.saveAddress(employeeDTO.getAddressList(), employee);
        }
        
        // ----------------Save/update contact of employee-------------
        List<ContactsDTO> contactList = new ArrayList<>();
        if (employeeDTO.getContactList() != null) {
            contactList = contactsService.saveContacts(employeeDTO.getContactList(), employee);
        }
        // -----------------------------------------------------------------------
        employeeDTO = employeeMapper.toDto(employee);
        employeeDTO.setContactList(contactList);
        employeeDTO.setAddressList(addressList);
        return employeeDTO;
    }

    /**
     * Update a employee.
     *
     * @param employeeDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeDTO update(EmployeeDTO employeeDTO) {
        log.debug("Request to update Employee : {}", employeeDTO);

        Field[] fields = employeeDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(employeeDTO);

                validationService.validateField("EMPLOYEE", field.getName(), value);
            } catch (ValidationException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        Employee employee = employeeMapper.toEntity(employeeDTO);
        // ----------------Save/ update address of employee-------------
        List<AddressDTO> addressList = new ArrayList<>();
        if (employeeDTO.getAddressList() != null) {
            addressList = addressService.saveAddress(employeeDTO.getAddressList(), employee);
        }
        // -----------------------------------------------------------------------

        // ----------------Save/update contact of employee-------------
        List<ContactsDTO> contactList = new ArrayList<>();
        if (employeeDTO.getContactList() != null) {
            contactList = contactsService.saveContacts(employeeDTO.getContactList(), employee);
        }
        // -----------------------------------------------------------------------
        employee = employeeRepository.save(employee);
        employeeDTO = employeeMapper.toDto(employee);
        employeeDTO.setAddressList(addressList);
        employeeDTO.setContactList(contactList);
        return employeeDTO;
    }

    /**
     * Partially update a employee.
     *
     * @param employeeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeDTO> partialUpdate(EmployeeDTO employeeDTO) {
        log.debug("Request to partially update Employee : {}", employeeDTO);

        return employeeRepository
            .findById(employeeDTO.getId())
            .map(existingEmployee -> {
                employeeMapper.partialUpdate(existingEmployee, employeeDTO);

                return existingEmployee;
            })
            .map(employeeRepository::save)
            .map(employeeMapper::toDto);
    }

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable).map(employeeMapper::toDto);
    }

    /**
     * Get one employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id).map(employeeMapper::toDto);
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }
}
