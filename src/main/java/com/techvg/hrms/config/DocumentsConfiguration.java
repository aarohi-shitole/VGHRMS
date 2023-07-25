package com.techvg.hrms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.techvg.hrms.domain.Document;

/**
 * Configuration Implementation for OpenKm server authentication {@link Document}.
 */
@Configuration
public class DocumentsConfiguration {
	
	@Value("${openKM.authenticateURL:http://91.107.193.58:8939/OpenKM/}")
	public String authenticateUrl;

	@Value("${openKM.username:vghrms}")
	public  String openKmUsername;

	@Value("${openKM.password:vgtech@2100}")
	public  String openKmPassword;
	
	@Value("${openKM.rootFolder:/okm:root/vghrms/qa/}")
	public  String openKmRootFolder;
	
}
