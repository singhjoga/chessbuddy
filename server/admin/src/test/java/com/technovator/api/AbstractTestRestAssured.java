package com.technovator.api;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.beanutils.locale.LocaleBeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.openapi.models.PathItem.HttpMethod;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.technovator.api.common.auditlog.AuditLog;
import com.technovator.api.common.constants.Constants;
import com.technovator.api.common.utils.DateUtils;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class AbstractTestRestAssured extends AbstractTest {
	public enum AuthType {
		WITH_TOKEN, WITHOUT_TOKEN, EXTERNAL_PARTNER_HEADER
	}

	private static final Logger LOG = LoggerFactory.getLogger(AbstractTestRestAssured.class);

	public String createResourceSuccess(String uri, Object obj) throws Exception {
		return createResourceSuccess(uri, (obj == null ? null : asJsonString(obj)));
	}

	public String createResourceSuccess(String uri, String json) throws Exception {
		Response resp = createResource(uri, json, HttpStatus.SC_CREATED);
		String s = resp.asPrettyString();
		resp.then().header("Location", response -> endsWith("/" + response.path("id")));
		String locationUrl = resp.header("Location");
		String id = locationUrl.substring(locationUrl.lastIndexOf("/") + 1);
		LOG.info("ID=" + id);
		return id;
	}

	public Response getResourceSuccess(String uri) throws Exception {
		return getResource(uri, HttpStatus.SC_OK);
	}

	public Response getResourceFailure(String uri, int expectedStatusCode) throws Exception {
		return getResource(uri, expectedStatusCode);
	}

	public Response getResource(String uri, int expectedStatusCode) throws Exception {
		String json = null;
		return call(uri, json, expectedStatusCode, HttpMethod.GET);
	}

	public Response createResourceFailure(String uri, Object obj, int expectedStatusCode) throws Exception {
		return createResource(uri, obj, expectedStatusCode);
	}

	public Response createResourceFailure(String uri, String json, int expectedStatusCode) throws Exception {
		return createResource(uri, json, expectedStatusCode);
	}

	public Response createResource(String uri, Object obj, int expectedStatusCode) throws Exception {
		LOG.info("Adding resource to " + uri);
		return call(uri, obj, expectedStatusCode, HttpMethod.POST);
	}

	public Response createResource(String uri, String json, int expectedStatusCode) throws Exception {
		LOG.info("Adding resource to " + uri);
		return call(uri, json, expectedStatusCode, HttpMethod.POST);
	}

	public Response patchResourceSuccess(String uri, String json) throws Exception {
		return patchResource(uri, json, HttpStatus.SC_OK);
	}

	public Response patchResourceSuccess(String uri, Object obj) throws Exception {
		return patchResource(uri, obj, HttpStatus.SC_OK);
	}

	public Response patchResourceFailure(String uri, String json, int expectedStatusCode) throws Exception {
		return patchResource(uri, json, expectedStatusCode);
	}

	public Response patchResourceFailure(String uri, Object obj, int expectedStatusCode) throws Exception {
		return patchResource(uri, obj, expectedStatusCode);
	}

	public Response patchResource(String uri, Object obj, int expectedStatusCode) throws Exception {
		LOG.info("Patching resource to " + uri);
		return call(uri, obj, expectedStatusCode, HttpMethod.PATCH);
	}

	public Response patchResource(String uri, String json, int expectedStatusCode) throws Exception {
		LOG.info("Patching resource to " + uri);
		return call(uri, json, expectedStatusCode, HttpMethod.PATCH);
	}

	public Response overwriteResourceSuccess(String uri, String json) throws Exception {
		return overwriteResource(uri, json, HttpStatus.SC_OK);
	}

	public Response overwriteResourceSuccess(String uri, Object obj) throws Exception {
		return overwriteResource(uri, obj, HttpStatus.SC_OK);
	}

	public Response overwriteResourceFailure(String uri, String json, int expectedStatusCode) throws Exception {
		return overwriteResource(uri, json, expectedStatusCode);
	}

	public Response overwriteResourceFailure(String uri, Object obj, int expectedStatusCode) throws Exception {
		return overwriteResource(uri, obj, expectedStatusCode);
	}

	public Response overwriteResource(String uri, Object obj, int expectedStatusCode) throws Exception {
		LOG.info("Overwriting resource to " + uri);
		return call(uri, obj, expectedStatusCode, HttpMethod.PUT);
	}

	public Response overwriteResource(String uri, String json, int expectedStatusCode) throws Exception {
		LOG.info("Overwriting resource to " + uri);
		return call(uri, json, expectedStatusCode, HttpMethod.PUT);
	}

	public Response bulkPatchFailure(String uri, String json, int expectedStatusCode) throws Exception {
		return call(uri, json, expectedStatusCode, HttpMethod.PATCH);
	}

	public Response bulkPatchFailure(String uri, List<Object> objList, int expectedStatusCode) throws Exception {
		return call(uri, objList, expectedStatusCode, HttpMethod.PATCH);
	}

	public List<String> bulkPatchSuccess(String uri, String json) throws Exception {
		return bulkOperation(uri, json, HttpStatus.SC_OK, HttpMethod.PATCH);
	}

	public List<String> bulkPatchSuccess(String uri, List<Object> objList) throws Exception {
		return bulkOperation(uri, objList, HttpStatus.SC_OK, HttpMethod.PATCH);
	}

	public Response bulkOverwriteFailure(String uri, String json, int expectedStatusCode) throws Exception {
		return call(uri, json, expectedStatusCode, HttpMethod.PUT);
	}

	public Response bulkOverwriteFailure(String uri, List<Object> objList, int expectedStatusCode) throws Exception {
		return call(uri, objList, expectedStatusCode, HttpMethod.PUT);
	}

	public List<String> bulkOverwriteSuccess(String uri, String json) throws Exception {
		return bulkOperation(uri, json, HttpStatus.SC_OK, HttpMethod.PUT);
	}

	public List<String> bulkOverwriteSuccess(String uri, List<Object> objList) throws Exception {
		return bulkOperation(uri, objList, HttpStatus.SC_OK, HttpMethod.PUT);
	}

	public List<String> bulkOperation(String uri, List<Object> objList, int expectedStatusCode, HttpMethod method) throws Exception {
		return bulkOperation(uri, asJsonString(objList), expectedStatusCode, method);
	}

	public List<String> bulkOperation(String uri, String json, int expectedStatusCode, HttpMethod method) throws Exception {
		Response resp = call(uri, json, expectedStatusCode, method);
		// bulk operation always return the IDs of the added/updated resources
		resp.then().body("ids", isA(List.class));
		return resp.jsonPath().getList("ids");
	}

	public Response call(String uri, List<Object> objList, int expectedStatusCode, HttpMethod method) throws Exception {
		return call(uri, (objList == null ? null : asJsonString(objList)), expectedStatusCode, method);
	}

	public Response call(String uri, Object obj, int expectedStatusCode, HttpMethod method) throws Exception {
		return call(uri, (obj == null ? null : asJsonString(obj)), expectedStatusCode, method);
	}

	public Response getResource(String uri) throws Exception {
		LOG.info("Getting resource to " + uri);
		return call(uri, "", HttpStatus.SC_OK, HttpMethod.GET);
	}

	public <T> T getResource(String uri, Class<T> returnType) throws Exception {
		LOG.info("Getting resource to " + uri);
		Response resp = call(uri, "", HttpStatus.SC_OK, HttpMethod.GET);
		return asObject(resp.asString(), returnType);
	}

	public Response getResourceFailureWithAuthType(String uri, int expectedStatus, AuthType authType) throws Exception {
		LOG.info("Getting resource to " + uri);
		return call(uri, "", expectedStatus, HttpMethod.GET, authType);
	}

	public Response getResourceWithoutAuthorizationToken(String uri) throws Exception {
		LOG.info("Getting resource without token to " + uri);
		return call(uri, "", HttpStatus.SC_OK, HttpMethod.GET, AuthType.WITHOUT_TOKEN);
	}

	public Response getResourceWithExternalPartnerHeader(String uri) throws Exception {
		LOG.info("Getting resource with 'X-External-Partner' header to " + uri);
		return call(uri, "", HttpStatus.SC_OK, HttpMethod.GET, AuthType.EXTERNAL_PARTNER_HEADER);
	}

	public Response call(String uri, String json, int expectedStatusCode, HttpMethod method) throws Exception {
		return call(uri, json, expectedStatusCode, method, AuthType.WITHOUT_TOKEN);
	}
	public Response call(String uri, String json, int expectedStatusCode, HttpMethod method, AuthType authType) throws Exception {
		Map<String, String> headers = new TreeMap<>();
		if (authType != null) {
			addAuthHeader(headers, authType);
		}
		return call(uri, json, expectedStatusCode, method, headers);
	}
	private void addAuthHeader(Map<String, String> headers, AuthType authType) {
		switch (authType) {
		case WITH_TOKEN:
			//headers.put("Authorization", "Bearer " + getAccessToken().getValue());
			break;
		case EXTERNAL_PARTNER_HEADER:
			headers.put("X-External-Partner", "true");
			break;
		case WITHOUT_TOKEN:
			break;
		}
	}
	public <T> T getForLocale(String uri, Locale locale, Class<T> returnType) throws Exception {
		Map<String, String> headers = new TreeMap<>();
		String langHeader = locale.toLanguageTag();
		headers.put(Constants.HEADER_ACCEPT_LANGUAGE, langHeader);
		return asObject(getWithHeaders(uri, headers).asString(), returnType);
	}
	public <T> T getForContentLanguage(String uri, Locale locale, Class<T> returnType) throws Exception {
		Map<String, String> headers = new TreeMap<>();
		String langHeader = locale.getLanguage();
		headers.put(Constants.HEADER_CONTENT_LANGUAGE, langHeader);
		return asObject(getWithHeaders(uri, headers).asString(), returnType);
	}
	public Response getWithHeaders(String uri, Map<String, String> headers) throws Exception {
		return call(uri, null, HttpStatus.SC_OK, HttpMethod.GET, headers);
	}
	public Response call(String uri, String json, int expectedStatusCode, HttpMethod method, Map<String, String> headers) throws Exception {
		RequestSpecification spec = given();
		spec.contentType(ContentType.JSON);
		if (headers != null) {
			spec.headers(headers);
		}
		if (json != null) {
			LOG.info(json);
		}
		Response resp;
		if (method == HttpMethod.POST) {
			resp = spec.body(json).post(uri).andReturn();
		} else if (method == HttpMethod.PUT) {
			resp = spec.body(json).put(uri).andReturn();
		} else if (method == HttpMethod.PATCH) {
			resp = spec.body(json).patch(uri).andReturn();
		} else if (method == HttpMethod.DELETE) {
			resp = spec.body(json).delete(uri).andReturn();
		} else if (method == HttpMethod.GET) {
			resp = spec.get(uri).andReturn();
		} else {
			throw new IllegalArgumentException("Method " + method.name() + " not supported");
		}
		int stausCode = resp.statusCode();

		LOG.info(resp.asPrettyString());

		if (stausCode == expectedStatusCode) {
			// status code is the same as expected. Return the response for the caller to do
			// further validations
			return resp;
		} else {
			String strResp = resp.asPrettyString();
			String msg = resp.statusLine();
			throw new RestException(msg + System.lineSeparator() + strResp);
		}
	}
/*
	public RequestSpecification build(AuthType authType) throws Exception {
		RequestSpecification spec = given();
		spec.contentType(ContentType.JSON);

		switch (authType) {
		case WITH_TOKEN:
			// spec.header("Authorization", "Bearer " + getAccessToken().getValue());
			break;
		case EXTERNAL_PARTNER_HEADER:
			spec.header("X-External-Partner", "true");
			break;
		case WITHOUT_TOKEN:
			break;
		}

		return spec;
	}
*/
	protected void assertEqualsString(JsonPath request, JsonPath response, String... paths) {
		for (String path : paths) {
			Assertions.assertEquals(request.getString(path), response.getString(path));
		}
	}

	protected void assertEqualsStringList(JsonPath request, JsonPath response, String path) {
		List<String> listReq = request.getList(path);
		List<String> listResp = response.getList(path);
		Assertions.assertArrayEquals(listReq.toArray(new String[] {}), listResp.toArray(new String[] {}));
	}

	protected void assertEqualsDate(JsonPath request, JsonPath response, String... paths) {
		for (String path : paths) {
			assertThat(DateUtils.toZonedDateTime(request.getString(path))).isEqualTo(DateUtils.toZonedDateTime(response.getString(path)));
		}
	}

	protected void assertEqualsBoolean(JsonPath request, JsonPath response, String... paths) {
		for (String path : paths) {
			assertThat(request.getBoolean(path)).isEqualTo(response.getBoolean(path));
		}
	}

	protected void assertEqualsInt(JsonPath request, JsonPath response, String... paths) {
		for (String path : paths) {
			assertThat(request.getInt(path)).isEqualTo(response.getInt(path));
		}
	}

	protected void assertNull(JsonPath jsonPath, String... paths) {
		for (String path : paths) {
			assertThat(jsonPath.getString(path)).isNull();
		}
	}

	protected void assertEmpty(JsonPath jsonPath, String... paths) {
		for (String path : paths) {
			assertThat(jsonPath.getString(path)).isEmpty();
			;
		}
	}

	protected void assertNotNull(JsonPath jsonPath, String... paths) {
		for (String path : paths) {
			assertThat(jsonPath.getString(path)).isNotNull();
		}
	}

	protected void assertNotEmpty(JsonPath jsonPath, String... paths) {
		for (String path : paths) {
			assertThat(jsonPath.getString(path)).isNotEmpty();
		}
	}

	protected void assertErrors(Response resp, String errorCode, String... fields) {
		for (String field : fields) {
			assertError(resp, errorCode, field);
		}
	}

	protected void assertAddHistory(Response resp) {
		AuditLog[] history = resp.as(AuditLog[].class);
		AuditLog addEntry = findLatestHistoryLogEntry(history, "Add");
		assertThat(addEntry).isNotNull();
	}

	protected void assertOverwriteHistory(Response resp, String... expectedFields) {
		assertActionHistory(resp, "Overwrite", expectedFields);
	}

	protected void assertUpdateHistory(Response resp, String... expectedFields) {
		assertActionHistory(resp, "Update", expectedFields);
	}

	protected void assertActionHistory(Response resp, String action, String... expectedFields) {
		AuditLog[] history = resp.as(AuditLog[].class);
		AuditLog entry = findLatestHistoryLogEntry(history, action);
		assertThat(entry).isNotNull();

		// get the changed fields from changes
		// get the string betweeen []
		String fieldString = StringUtils.substringBetween(entry.getDetails(), "[", "]");
		String[] actualFields = StringUtils.split(fieldString, ",");
		// fields have values separated by '='.
		for (int i = 0; i < actualFields.length; i++) {
			actualFields[i] = StringUtils.substringBefore(actualFields[i], "=");
		}
		Set<String> expectedFieldsList = new HashSet<>(Arrays.asList(expectedFields));
		Set<String> actualFieldsList = new HashSet<>(Arrays.asList(actualFields));
		assertThat(actualFieldsList).isEqualTo(expectedFieldsList);
	}

	protected void assertUpdateActionHistory(Response resp, String changeDetails) {
		AuditLog[] history = resp.as(AuditLog[].class);
		AuditLog entry = findLatestHistoryLogEntry(history, "Update");
		assertThat(entry).isNotNull();

		assertThat(entry.getDetails()).isEqualTo(changeDetails);
	}

	protected AuditLog findLatestHistoryLogEntry(AuditLog[] history, String action) {
		AuditLog result = null;
		for (AuditLog log : findLatestHistoryLogEntries(history, action, null)) {
			if (result == null) {
				result = log;
			} else if (log.getDate().after(result.getDate())) {
				result = log;
			}
		}

		return result;
	}

	protected List<AuditLog> findLatestHistoryLogEntries(AuditLog[] history, String action, String filterValue) {
		List<AuditLog> result = new ArrayList<>();
		for (AuditLog log : history) {
			if ((action != null ? action.equals(log.getAction()) : true) && (filterValue != null ? filterValue.equals(log.getFilterValue()) : true)) {
				result.add(log);
			}
		}

		return result;
	}

	private void assertError(Response resp, String errorCode, String field) {
		resp.then().assertThat().body("errors", hasItem( //
				allOf( //
						hasEntry("field", field), //
						hasEntry("code", errorCode) //
				)));
	}

	protected void assertNotErrors(Response resp, String errorCode, String... fields) {
		for (String field : fields) {
			assertNotError(resp, errorCode, field);
		}
	}

	private void assertNotError(Response resp, String errorCode, String field) {
		resp.then().assertThat().body("errors", not(hasItem( //
				allOf( //
						hasEntry("field", field), //
						hasEntry("code", errorCode) //
				))));
	}

}
