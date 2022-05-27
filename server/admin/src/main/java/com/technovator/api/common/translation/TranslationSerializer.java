package com.technovator.api.common.translation;

import java.io.IOException;

import javax.enterprise.inject.spi.CDI;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.technovator.api.common.context.ClientContext;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;

public class TranslationSerializer extends StdSerializer<String>{
	private TranslationService service;
    private static final long serialVersionUID = 1L;
    
	public TranslationSerializer() {
        this(null);
    }
  
    public TranslationSerializer(Class<String> t) {
        super(t);
    }
	@Override
	public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		if (service == null) {
			service = CDI.current().select(TranslationService.class).get();
		}
		if (StringUtils.isBlank(value) || !value.trim().startsWith("$")) {
			jgen.writeString(value);
		}else {
			String lang = ClientContext.getInstance().getUserLanguage();
			String key = value.trim();
			// value can be in the form of ${key} or just $key format
			if (key.startsWith("${")) {
				key = StringUtils.substringBetween(key, "{", "}");
			}else {
				key = StringUtils.substringAfter(key, "$");
			}
			if (StringUtils.isBlank(key)) {
				throw new IllegalStateException("Not a valid translation key definition: "+value);
			}
		    jgen.writeString(service.findTranslation(key, lang));
		}

	}

}
