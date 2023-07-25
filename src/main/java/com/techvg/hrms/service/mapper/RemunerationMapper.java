package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Remuneration;
import com.techvg.hrms.domain.TaxRegime;
import com.techvg.hrms.service.dto.RemunerationDTO;
import com.techvg.hrms.service.dto.TaxRegimeDTO;


import org.mapstruct.*;

/**
 * Mapper for the entity {@link Remuneration} and its DTO {@link RemunerationDTO}.
 */
@Mapper(componentModel = "spring")
public interface RemunerationMapper extends EntityMapper<RemunerationDTO, Remuneration> {
	
	 @Mapping(target = "taxRegime", source = "taxRegime", qualifiedByName = "taxRegimeId")
	    RemunerationDTO toDto(Remuneration remuneration);

	

	 
	    @Named("taxRegimeId")
	    @BeanMapping(ignoreByDefault = true)
	    @Mapping(target = "id", source = "id")
	    TaxRegimeDTO toDtoTaxRegime(TaxRegime taxRegime);
	 
	 
	 
//	 
//	 @Named("taxRegimeId")
//	    default TaxRegimeDTO mapTaxRegime(TaxRegime taxRegime) {
//
//	        TaxRegimeDTO taxRegimeDTO = new TaxRegimeDTO();
//	        // Map the properties from TaxRegime to TaxRegimeDTO
//	        taxRegimeDTO.setId(taxRegime.getId());
//	        taxRegimeDTO.setTaxRegimeName(taxRegime.getTaxRegimeName());
//	        taxRegimeDTO.setStatus(taxRegime.getStatus());
//	        taxRegimeDTO.setCompanyId(taxRegime.getCompanyId());
//	        taxRegimeDTO.setLastModified(taxRegime.getLastModified());
//	        taxRegimeDTO.setLastModifiedBy(taxRegime.getLastModifiedBy());
//
//	        return taxRegimeDTO;
//	    }
	 
	 
}
