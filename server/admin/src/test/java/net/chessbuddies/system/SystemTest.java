package net.chessbuddies.system;

import java.net.URL;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.technovator.api.AbstractTestRestAssured;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import net.chessbuddies.system.country.Country;
import net.chessbuddies.system.language.Language;
import net.chessbuddies.system.refdata.RefData;
@QuarkusTest
public class SystemTest extends AbstractTestRestAssured{
	private static final String GERMAN = Locale.GERMAN.toLanguageTag();
	private static final String ENGLISH = Locale.UK.toLanguageTag();
	@TestHTTPEndpoint(SystemController.class)
	@TestHTTPResource
    URL url;
	
	@BeforeEach
	public void init() {
		super.setResourceUri(url.toString());
	}
    @Test
    public void testLanguages() throws Exception {
    	String uri = getResourceUri()+"/languages";
    	Language[] objs= getResource(uri, Language[].class);
    	Assertions.assertTrue(objs.length > 0);
    }
    @Test
    public void testCountries() throws Exception {
    	String uri = getResourceUri()+"/countries";
    	Country[] objs= getResource(uri, Country[].class);
    	Assertions.assertTrue(objs.length > 0);
    }
    @ParameterizedTest
    @EnumSource(CountryLocale.class)
    public void testRefData(CountryLocale countryLocale) throws Exception {
    	String uri = getResourceUri()+"/refdata";
    	RefData[] objs= verifyLocaleRefData(uri, countryLocale.locale);
    	Assertions.assertTrue(objs.length > 0);
    	for (RefData refDataType: objs) {
        	uri = getResourceUri()+"/refdata/"+refDataType.getCode();
        	objs= verifyLocaleRefData(uri, countryLocale.locale);
        	Assertions.assertTrue(objs.length > 0);
    	}

    }
    public RefData[] verifyLocaleRefData(String uri, Locale locale) throws Exception {
    	getForLocale(uri, locale, RefData[].class);
    	return getForContentLanguage(uri, locale, RefData[].class);
    }
    
    private static enum CountryLocale {
    	GERMANY(Locale.GERMANY), //
    	UK(Locale.UK);
    	
    	private Locale locale;

		private CountryLocale(Locale locale) {
			this.locale = locale;
		}
    	
    }
}