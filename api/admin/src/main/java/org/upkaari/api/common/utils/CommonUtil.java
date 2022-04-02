package org.upkaari.api.common.utils;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class CommonUtil {
	public static String genUUID() {
		return StringUtils.replace(UUID.randomUUID().toString(),"-","");
	}
	public static String getTempDir() {
		return System.getProperty("java.io.tmpdir");
	}
}
