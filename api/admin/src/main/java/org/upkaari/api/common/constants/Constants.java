package org.upkaari.api.common.constants;

import java.nio.charset.Charset;

public interface Constants {
	public static final String UTF8="UTF-8";
	public static final Charset UTF8_CHARSET=Charset.forName(UTF8);
	public static final String BASE_PACKAGE="org.upkaari";
	public static final String SYSTEM_USER="system";
	public static final long MS_ONE_SECOND = 1000l;
	public static final long MS_ONE_MINUTE = MS_ONE_SECOND * 60;
	public static final long PROPSET_ID_HOST=5;
	String RESOURCE_ID="sm-api";
	String OAUTH_CLIENT_ID="komrade-server";
}
