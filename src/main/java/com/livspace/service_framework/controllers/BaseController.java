package com.livspace.service_framework.controllers;

import com.livspace.service_framework.codes.ErrorCodes;
import com.livspace.service_framework.codes.SuccessCodes;
import com.livspace.service_framework.models.entities.BaseEntity;
import com.livspace.service_framework.models.entries.BaseEntry;
import com.livspace.service_framework.models.responses.BaseResponse;
import com.livspace.service_framework.models.responses.StatusResponse;
import com.livspace.service_framework.services.BaseService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@Getter
public abstract class BaseController<R extends BaseResponse, M extends BaseEntity, E extends BaseEntry> {

    protected BaseService<M, E> service;

    protected abstract R createResponse(List<E> entryList);

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public R findById(@PathVariable Long id) {
        R response = createResponse(null);
        try {
            E entry = getService().find(id);
            response = createResponse(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(SuccessCodes.DATA_RETRIEVED_SUCCESSFULLY, 1));
            log.info("data retrieved successfully {}", response);
        } catch (Exception e) {
            log.error("Error Occurred: {}", e.getMessage());
            response.setStatus(new StatusResponse(ErrorCodes.GENERIC_ERROR_OCCURRED, 0));
        }
        return response;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public R save(@RequestBody E entry) {
        R response = createResponse(null);
        try {
            E newEntry = getService().save(entry);
            response = createResponse(Collections.singletonList(newEntry));
            response.setStatus(new StatusResponse(SuccessCodes.CREATED, 1));
        } catch (Exception e) {
            log.error("Error Occurred: {}", e.getMessage());
            response.setStatus(new StatusResponse(ErrorCodes.GENERIC_ERROR_OCCURRED, 0));
        }
        return response;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public R update(@RequestBody E entry, @PathVariable Long id) {
        R response = createResponse(null);
        try {
            E newEntry = getService().update(entry, id);
            response = createResponse(Collections.singletonList(newEntry));
            response.setStatus(new StatusResponse(SuccessCodes.CREATED, 1));
        } catch (Exception e) {
            log.error("Error Occurred: {}", e.getMessage());
            response.setStatus(new StatusResponse(ErrorCodes.GENERIC_ERROR_OCCURRED.getCode(), e.getMessage(), StatusResponse.Type.ERROR, 0));
        }
        return response;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public R customSearch(@RequestParam("filters") String filters,
                          @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                          @RequestParam(value = "fetchSize", defaultValue = "100", required = false) Integer fetchSize,
                          @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                          @RequestParam(value = "sortOrder", defaultValue = "ASC", required = false) String sortOrder) {
        R response = createResponse(null);
        try {
            List<E> result = service.search(filters, page, fetchSize, sortBy, sortOrder);
            response = createResponse(result);
            response.setStatus(new StatusResponse(SuccessCodes.DATA_RETRIEVED_SUCCESSFULLY, result.size()));
        } catch (Exception e) {
            log.error("Error Occurred {}", e.getMessage());
            response.setStatus(new StatusResponse(ErrorCodes.GENERIC_ERROR_OCCURRED.getCode(), e.getMessage(), StatusResponse.Type.ERROR, 0));
        }
        return response;
    }
}
