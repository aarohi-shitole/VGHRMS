package com.techvg.hrms.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.techvg.hrms.repository.DocumentRepository;
import com.techvg.hrms.service.DocumentQueryService;
import com.techvg.hrms.service.DocumentRulesService;
import com.techvg.hrms.service.DocumentService;
import com.techvg.hrms.service.criteria.DocumentCriteria;
import com.techvg.hrms.service.dto.DocumentDTO;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;

import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.hrms.domain.Document}.
 */
@RestController
@RequestMapping("/api")
public class DocumentResource {

	private final Logger log = LoggerFactory.getLogger(DocumentResource.class);

	private static final String ENTITY_NAME = "document";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final DocumentService documentService;

	private final DocumentRepository documentRepository;

	private final DocumentQueryService documentQueryService;

	@Autowired
	private DocumentRulesService documentRulesService;

	public DocumentResource(DocumentService documentService, DocumentRepository documentRepository,
			DocumentQueryService documentQueryService) {
		this.documentService = documentService;
		this.documentRepository = documentRepository;
		this.documentQueryService = documentQueryService;
	}

	/**
	 * {@code POST  /documents} : Create a new document.
	 *
	 * @param documentDTO the documentDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new documentDTO, or with status {@code 400 (Bad Request)} if
	 *         the document has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/documents")
	public ResponseEntity<DocumentDTO> createDocument(@RequestBody DocumentDTO documentDTO) throws URISyntaxException {
		log.debug("REST request to save Document : {}", documentDTO);
		if (documentDTO.getId() != null) {
			throw new BadRequestAlertException("A new document cannot already have an ID", ENTITY_NAME, "idexists");
		}
		DocumentDTO result = documentService.save(documentDTO);
		return ResponseEntity
				.created(new URI("/api/documents/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /documents/:id} : Updates an existing document.
	 *
	 * @param id          the id of the documentDTO to save.
	 * @param documentDTO the documentDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated documentDTO, or with status {@code 400 (Bad Request)} if
	 *         the documentDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the documentDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/documents/{id}")
	public ResponseEntity<DocumentDTO> updateDocument(@PathVariable(value = "id", required = false) final Long id,
			@RequestBody DocumentDTO documentDTO) throws URISyntaxException {
		log.debug("REST request to update Document : {}, {}", id, documentDTO);
		if (documentDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, documentDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!documentRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		DocumentDTO result = documentService.update(documentDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PATCH  /documents/:id} : Partial updates given fields of an existing
	 * document, field will ignore if it is null
	 *
	 * @param id          the id of the documentDTO to save.
	 * @param documentDTO the documentDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated documentDTO, or with status {@code 400 (Bad Request)} if
	 *         the documentDTO is not valid, or with status {@code 404 (Not Found)}
	 *         if the documentDTO is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the documentDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/documents/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<DocumentDTO> partialUpdateDocument(
			@PathVariable(value = "id", required = false) final Long id, @RequestBody DocumentDTO documentDTO)
			throws URISyntaxException {
		log.debug("REST request to partial update Document partially : {}, {}", id, documentDTO);
		if (documentDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, documentDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!documentRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Optional<DocumentDTO> result = documentService.partialUpdate(documentDTO);

		return ResponseUtil.wrapOrNotFound(result,
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentDTO.getId().toString()));
	}

	/**
	 * {@code GET  /documents} : get all the documents.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of documents in body.
	 */
	@GetMapping("/documents")
	public ResponseEntity<List<DocumentDTO>> getAllDocuments(DocumentCriteria criteria,
			@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
		log.debug("REST request to get Documents by criteria: {}", criteria);
		Page<DocumentDTO> page = documentQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /documents/count} : count all the documents.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/documents/count")
	public ResponseEntity<Long> countDocuments(DocumentCriteria criteria) {
		log.debug("REST request to count Documents by criteria: {}", criteria);
		return ResponseEntity.ok().body(documentQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /documents/:id} : get the "id" document.
	 *
	 * @param id the id of the documentDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the documentDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/documents/{id}")
	public ResponseEntity<DocumentDTO> getDocument(@PathVariable Long id) {
		log.debug("REST request to get Document : {}", id);
		Optional<DocumentDTO> documentDTO = documentService.findOne(id);
		return ResponseUtil.wrapOrNotFound(documentDTO);
	}

	/**
	 * {@code DELETE  /documents/:id} : delete the "id" document.
	 *
	 * @param id the id of the documentDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/documents/{id}")
	public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
		log.debug("REST request to delete Document : {}", id);
		documentService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	// TODO Here for upload document
	@PostMapping(value = "/documents-upload")
	public ResponseEntity<DocumentDTO> uploadDocument(@RequestParam("file") MultipartFile file,
			@RequestParam("fileMetadata") String fileMetadata) throws URISyntaxException {
		// Create document in openKM server
		DocumentDTO documentDTO = documentRulesService.createDocument(file, fileMetadata);
		// Save unique id of document in hrms server
		DocumentDTO result = documentService.save(documentDTO);
		return ResponseEntity
				.created(new URI("/api/documents/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);

	}

	/**
	 * {@code GET  /documents-download} : get the documents.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
	 *         InputStreamResource in body.
	 */
	@GetMapping("/documents-download")
	public ResponseEntity<InputStreamResource> getDocuments(DocumentCriteria criteria) {
		log.debug("REST request to get Documents by criteria: {}", criteria);
		List<DocumentDTO> docList = null;
		if (criteria.getDocType() == null && criteria.getRefTableId() == null && criteria.getRefTable() == null) {
			throw new BadRequestAlertException("Please specify document type and owner of document", ENTITY_NAME,
					"idnotfound");
		}
		docList = documentQueryService.findByCriteria(criteria);

		// Get document from OpenKm server
		InputStreamResource resource = documentRulesService.getDocument(docList);
		return ResponseEntity.ok()
				// .headers(headers)
				// .contentLength(resource.getFile().length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);

	}

}
