package com.technovator.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@TestMethodOrder(OrderAnnotation.class)

public abstract class AbstractTest {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractTest.class);
	public static final String REST_DATE_FORMAT = "yyyy-MM-dd";
	private static final SimpleDateFormat restDateFormat = new SimpleDateFormat(REST_DATE_FORMAT);
	private static final DateFormat jsonDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	protected ObjectMapper mapper;
	protected ObjectWriter ow;


	private String resourceUri;

	public AbstractTest() {
		mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setDateFormat(jsonDateTime);
		mapper.registerModule(new JavaTimeModule());
		ow = mapper.writer().withDefaultPrettyPrinter();
	}

	public String getResourceUri() {
		return resourceUri;
	}

	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	public String getResourceUri(String id) {
		return resourceUri + "/" + id;
	}
	public String getHistoryUri(String id) {
		return resourceUri + "/" + id+"/history";
	}

	protected String asJsonString(Object obj) {
		if (obj == null)
			return "";
		String str;
		try {
			str = ow.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
		return str;
	}

	protected <T> T asObject(String json, Class<T> returnType) {
		try {
			return mapper.readValue(json, returnType);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}

	}


	public String buildQueryString(String paramNames, Object... values) {
		String[] params = StringUtils.split(paramNames, ",");
		;
		if (params.length != values.length) {
			throw new IllegalStateException("No. of params does not match with the no. of values");
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (values[i] == null) {
				continue;
			}
			if (sb.length() > 0) {
				sb.append("&");
			}
			String value = null;
			if (values[i] instanceof Date) {
				value = restDateFormat.format((Date) values[i]);
			} else {
				value = values[i].toString();
			}
			sb.append(params[i].trim()).append("=").append(value);
		}

		return sb.toString();
	}

	public static String uniqueName(String prefix) {
		return prefix + "-" + System.currentTimeMillis();
	}
}
