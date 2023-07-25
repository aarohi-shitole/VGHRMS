package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Document;
import com.techvg.hrms.service.dto.DocumentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Document} and its DTO {@link DocumentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {}
