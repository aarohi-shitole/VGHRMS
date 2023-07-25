package com.techvg.hrms.document.management;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.OKMWebservicesFactory;
import com.openkm.sdk4j.bean.Folder;
import com.openkm.sdk4j.exception.AccessDeniedException;
import com.openkm.sdk4j.exception.AutomationException;
import com.openkm.sdk4j.exception.DatabaseException;
import com.openkm.sdk4j.exception.ExtensionException;
import com.openkm.sdk4j.exception.ItemExistsException;
import com.openkm.sdk4j.exception.PathNotFoundException;
import com.openkm.sdk4j.exception.RepositoryException;
import com.openkm.sdk4j.exception.UnknowException;
import com.openkm.sdk4j.exception.WebserviceException;
import com.techvg.hrms.config.DocumentsConfiguration;

/**
 * Folder service for managing document folders in openKm server .
 */

@Component
public class FolderManagementService {
	
	
	
	@Autowired
	private DocumentsConfiguration documentsConfiguration;
	
	private final Logger log = LoggerFactory.getLogger(FolderManagementService.class);

	 /**
     * Create a Folder.
     *
     * @return the Folder object.
     */
	public Folder createFolder(Long empId) {
		OKMWebservices okm1 = OKMWebservicesFactory.newInstance(documentsConfiguration.authenticateUrl, documentsConfiguration.openKmUsername, documentsConfiguration.openKmPassword);

		Folder folder = new Folder();
		folder.setAuthor("vghrms");
		folder.setPath(documentsConfiguration.openKmRootFolder+empId);
		log.debug(empId.toString());

		log.debug("folder created : {}");
		folder.setSubscribed(true);
		
		try {
			folder= okm1.createFolder(folder);

		} catch (AccessDeniedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RepositoryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (PathNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ItemExistsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DatabaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExtensionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (AutomationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnknowException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (WebserviceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}return folder;
	}
	
	/**
     * Create a Folder with parameters.
     * @param employeeId and docCategory
     * @return the Folder object.
     */
	
public Folder createFolderWithdocCategory(Long employeeId, String docCategory) {
	OKMWebservices okm1 = OKMWebservicesFactory.newInstance(documentsConfiguration.authenticateUrl, documentsConfiguration.openKmUsername, documentsConfiguration.openKmPassword);

	Folder folder = new Folder();
	folder.setAuthor("vghrms");
	folder.setPath(documentsConfiguration.openKmRootFolder+employeeId+"/"+docCategory.toString());
	folder.setSubscribed(true);
	
	log.debug("Create newFolder : {}", folder);
	try {
		folder= okm1.createFolder(folder);
	} 
	catch (AccessDeniedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (RepositoryException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}  catch (PathNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (ItemExistsException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (DatabaseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (ExtensionException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (AutomationException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (UnknowException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (WebserviceException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}return folder;
}

	
	/**
     * Get all the Child folders of root folder.
     *
     * @return the list folder of entities.
     */
	public List<Folder> getFolders() {

		OKMWebservices okm = OKMWebservicesFactory.newInstance(documentsConfiguration.authenticateUrl, documentsConfiguration.openKmUsername, documentsConfiguration.openKmPassword);
		
		List<Folder> folderList = null;
		try {
			for (Folder folder : okm.getFolderChildren("/okm:root")) {
				System.out.println("Folder -> " + folder.getPath());

				 folderList = okm.getFolderChildren(documentsConfiguration.openKmRootFolder);
				log.debug("FolderList : {}", folderList);
			}
		} catch (PathNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WebserviceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return folderList;
   
	}
}
