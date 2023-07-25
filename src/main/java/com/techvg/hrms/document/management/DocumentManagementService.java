package com.techvg.hrms.document.management;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.OKMWebservicesFactory;
import com.openkm.sdk4j.bean.Document;
import com.openkm.sdk4j.bean.Version;
import com.techvg.hrms.config.DocumentsConfiguration;
import com.techvg.hrms.service.dto.DocumentDTO;

/**
 * Document service for managing document in folders of openKm server .
 */
@Component
public class DocumentManagementService {

	@Value("${folder.path}")
	private String folderPath;

	@Autowired
	private DocumentsConfiguration documentsConfiguration;

	private final Logger log = LoggerFactory.getLogger(DocumentManagementService.class);

	/**
	 * Create a Document.
	 * 
	 * @param DocumentDTO and file InputStream the pagination information.
	 * @return the Document object of openkm.sdk4j.bean.
	 */

	public Document createDocument(InputStream fileStream, DocumentDTO documentDTO, String folderpath) {
		OKMWebservices okm = OKMWebservicesFactory.newInstance(documentsConfiguration.authenticateUrl,
				documentsConfiguration.openKmUsername, documentsConfiguration.openKmPassword);
		try {
			String docName = documentDTO.getRefTable() + "_" + documentDTO.getRefTableId() + "_"
					+ documentDTO.getDocType() + "_" + System.currentTimeMillis() + "." + documentDTO.getContentType();

			log.debug("Docment name  {}" + docName);

			Document documentObj = new Document();

			documentObj.setPath(folderpath + "/" + docName);

			Document resultDocument = okm.createDocument(documentObj, fileStream);
			log.debug("Created Document: {}", resultDocument);

			return resultDocument;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fileStream);
		}
		return null;
	}

	/**
	 * Update Existing Document.
	 * 
	 * @param uuid and file InputStream .
	 * @return the Version object of openkm.sdk4j.bean.
	 */

	public Version updateDocument(InputStream fileStream, String uuid) {
		OKMWebservices okm = OKMWebservicesFactory.newInstance(documentsConfiguration.authenticateUrl,
				documentsConfiguration.openKmUsername, documentsConfiguration.openKmPassword);

		Version updatedDocumentVersion = null;
		try {
			String comment = "Change document version "; // TODO here needs to change and add dynamic comment while
															// updating
			// Locks a document first and return an object with the Lock information.
			okm.lock(uuid);

			// Updates a document with new version and return an object with new Version
			// values.
			updatedDocumentVersion = okm.checkin(uuid, fileStream, comment);
			log.debug("Updated Document Version: {}", updatedDocumentVersion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updatedDocumentVersion;
	}

	/**
	 * Get path of Document.
	 * 
	 * @param String of OpenKm Uuid.
	 * @return path of document.
	 */
	public String getDocumentPath(String fileUid) {
		OKMWebservices okm = OKMWebservicesFactory.newInstance(documentsConfiguration.authenticateUrl,
				documentsConfiguration.openKmUsername, documentsConfiguration.openKmPassword);
		String path = null;
		try {
			path = okm.getDocumentPath(fileUid);
			log.debug("Folder Path : {}", path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * Get File content of Document.
	 * 
	 * @param DocumentDTO for document parameters .
	 */
	// TODO here needs to add logic of get document
	public InputStreamResource getFileContent(DocumentDTO document) {
		OKMWebservices okm = OKMWebservicesFactory.newInstance(documentsConfiguration.authenticateUrl,
				documentsConfiguration.openKmUsername, documentsConfiguration.openKmPassword);
		InputStreamResource resource = null;
		try {

			OutputStream fos = new FileOutputStream(
					folderPath + document.getFileName() + "." + document.getContentType());

			String path = this.getDocumentPath(document.getFileUuid());
			InputStream file = okm.getContent(path);
			resource = new InputStreamResource(file);
			log.debug("InputStream File : {}", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resource;
	}

}
