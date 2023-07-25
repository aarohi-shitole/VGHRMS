package com.techvg.hrms.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openkm.sdk4j.bean.Document;
import com.openkm.sdk4j.bean.Folder;
import com.techvg.hrms.document.management.DocumentManagementService;
import com.techvg.hrms.document.management.FolderManagementService;
import com.techvg.hrms.service.criteria.DocumentCriteria;
import com.techvg.hrms.service.dto.DocumentDTO;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;

import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Service
public class DocumentRulesService {

	private final Logger log = LoggerFactory.getLogger(DocumentRulesService.class);

	@Autowired
	private FolderManagementService folderManagementService;

	@Autowired
	private DocumentManagementService documentManagementService;

	@Autowired
	private DocumentQueryService documentQueryService;
	
	@Autowired
	private DocumentService documentService;

	/**
	 * Create a Document.
	 * 
	 * @param Multipart file and fileMetadata .
	 * @return the DocumentDTO .
	 */

	public DocumentDTO createDocument(MultipartFile file, String fileMetadata) {
		log.debug("fileMetadata : {}", fileMetadata);
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		DocumentDTO documentDTO = gson.fromJson(fileMetadata, DocumentDTO.class);
		log.debug("REST request to save Documents : {}", documentDTO);
		// Check if document is already exist then update previous document
		DocumentDTO existingfDoc = this.checkDocAlreadyExists(documentDTO);

		// Create Folder with employee details and document details
		Folder folder = folderManagementService.createFolder(documentDTO.getRefTableId());
		Folder createdFolder = folderManagementService.createFolderWithdocCategory(documentDTO.getRefTableId(),
				documentDTO.getDocCategory());

		if (documentDTO.getDocType() != null) {
			try {
				InputStream fileStream = file.getInputStream();

				if (existingfDoc != null && existingfDoc.getFileUuid() != null) {
					// If document already exist then update previous document in OpenKM
					documentManagementService.updateDocument(fileStream, existingfDoc.getFileUuid());
					existingfDoc.setFileName(documentDTO.getFileName());
					documentDTO = existingfDoc;
				} else {
					// If document is not exist then create document name and upload document in
					// openKM
					Document docResult = documentManagementService.createDocument(fileStream, documentDTO,
							createdFolder.getPath());
					documentDTO.setFileUuid(docResult.getUuid());
				}
				documentDTO.setFolderUuid(createdFolder.getUuid());
				return documentDTO;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new BadRequestAlertException("Document type not found..!Could not store the file.", "Documents",
					"idnotfound");
		}
		return documentDTO;
	}

	/**
	 * Get a Document.
	 * 
	 * @param List<DocumentDTO> list of DocumentDTO.
	 * @return InputStreamResource of file.
	 */
	public InputStreamResource getDocument(List<DocumentDTO> docList) {

		InputStreamResource resource = null;
		if (!docList.isEmpty()) {
			for (DocumentDTO documentObj : docList) {
				resource = documentManagementService.getFileContent(documentObj);
			}
		} else {
			throw new BadRequestAlertException("Document not found", "Documents", "idnotfound");
		}
		return resource;

	}

	/**
	 * Check Document is already exist in database.
	 * 
	 * @param DocumentDTO.
	 * @return String of File uniqueId(uuid) of openKM.
	 */

	private DocumentDTO checkDocAlreadyExists(DocumentDTO documentDTO) {

		DocumentCriteria criteria = new DocumentCriteria();
	//	String fileuuid = null;
		DocumentDTO documentDto =null;

		if (documentDTO.getDocCategory() != null && documentDTO.getDocType() != null
				&& documentDTO.getRefTable() != null && documentDTO.getRefTableId() != null) {

			StringFilter stringFilter = new StringFilter();
			stringFilter.setEquals(documentDTO.getDocCategory());
			criteria.setDocCategory(stringFilter);

			stringFilter = new StringFilter();
			stringFilter.setEquals(documentDTO.getDocType());
			criteria.setDocType(stringFilter);

			stringFilter = new StringFilter();
			stringFilter.setEquals(documentDTO.getRefTable());
			criteria.setRefTable(stringFilter);

			LongFilter longFilter = new LongFilter();
			longFilter.setEquals(documentDTO.getRefTableId());
			criteria.setRefTableId(longFilter);

			List<DocumentDTO> docList = documentQueryService.findByCriteria(criteria);
			log.debug("Document  list : {}", docList.toString());

			if (!docList.isEmpty()) {
				documentDto = docList.get(0);
			}
		} else {
			throw new BadRequestAlertException(
					"Document save request must be contain document type, catagory and refrence!Could not store the file",
					"Documents", "idnotfound");
		}
		return documentDto;

	}
}
