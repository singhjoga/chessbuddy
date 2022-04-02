package org.upkaari.api.common.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestResponse;
import org.upkaari.api.common.context.ClientContext;
import org.upkaari.api.common.controllers.CommonResponse.AddResponse;
import org.upkaari.api.common.controllers.CommonResponse.BulkAddUpdateResponse;
import org.upkaari.api.common.controllers.CommonResponse.BulkOperationResponse;
import org.upkaari.api.common.exception.BadRequestException;
import org.upkaari.api.common.utils.DateUtils;

@Produces(MediaType.APPLICATION_JSON)
public abstract class BaseController {
	public BaseController() {
		super();
	}

	protected String getCurrentURI() {
		return ClientContext.getInstance().getRequestUri();
	}

	protected String getCurrentURI(Serializable id) {
		return getCurrentURI()+"/"+id;
	}
	protected Date toDateParam(String dateStr, String paramName) {
		if (dateStr==null) return null;
		//first convert using json format
		Date dt = DateUtils.toDateFromJsonFormat(dateStr);

		if (dt == null) {
			String msg = String.format("Parameter '%s' value '%s' is not in '%s' format", paramName,dateStr,DateUtils.JSON_DATE);
			throw new BadRequestException(msg);
		}
		
		return dt;
	}
	protected Date toDateTimeParam(String dateStr, String paramName) {
		if (dateStr==null) return null;
		//first convert using json format
		Date dt = DateUtils.toDateTimeFromJsonFormat(dateStr);

		if (dt == null) {
			String msg = String.format("Parameter '%s' value '%s' is not in '%s' format", paramName,dateStr,DateUtils.JSON_DATE_TIME);
			throw new BadRequestException(msg);
		}
		
		return dt;
	}

	protected RestResponse<AddResponse> addResponse(Long id) {
		return addResponse(id.toString());
	}
	protected RestResponse<AddResponse> addResponse(String id) {
		String resUrl = getCurrentURI(id);
		return RestResponseBuilder.created(resUrl, id);
	}
	protected RestResponse<Object> okResponse() {
		return RestResponseBuilder.ok();
	}
	protected RestResponse<BulkOperationResponse> bulkOperationResponse(int affectedItems) {
		return RestResponseBuilder.ok(new BulkOperationResponse(affectedItems));
	}
	protected RestResponse<BulkAddUpdateResponse>  bulkAddUpdateResponse(List<?> ids) {
		List<String> strIds = new ArrayList<>();
		ids.forEach(e-> strIds.add(e.toString()));
		return RestResponseBuilder.ok(new CommonResponse.BulkAddUpdateResponse(strIds));
	}
}