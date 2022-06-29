package net.chessbuddies.system;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

import com.fasterxml.jackson.annotation.JsonView;
import com.technovator.api.common.constants.Views;
import com.technovator.api.common.controllers.BaseController;
import com.technovator.api.common.controllers.RestResponseBuilder;

import net.chessbuddies.system.country.Country;
import net.chessbuddies.system.country.CountryService;
import net.chessbuddies.system.language.Language;
import net.chessbuddies.system.language.LanguageService;
import net.chessbuddies.system.refdata.RefData;
import net.chessbuddies.system.refdata.RefDataService;

@Path("/api/v1/system")
@Singleton
@Tag(name = "System")
@Produces(MediaType.APPLICATION_JSON)
public class SystemController extends BaseController {
	@Inject
	private LanguageService langService;
	@Inject
	private CountryService countryService;
	@Inject
	private RefDataService refDataService;

	public SystemController() {
		super();
	}

	@GET
	@Path("/languages")
	@JsonView(value = Views.List.class)
	@Operation(description = "Returns all supported languages")
	public RestResponse<List<Language>> getLanguages() {
		List<Language> body = langService.findAllByIsDisabled(false);
		return RestResponseBuilder.ok(body);
	}

	@GET
	@Path("/countries")
	@JsonView(value = Views.List.class)
	@Operation(description = "Returns list of countries")
	public RestResponse<List<Country>> getCountries() {
		List<Country> body = countryService.findAll();
		return RestResponseBuilder.ok(body);
	}

	@GET
	@Path("/countries/{id}")
	@JsonView(value = Views.List.class)
	@Operation(description = "Returns country details")
	public RestResponse<Country> getCountry(@RestPath String id) {
		Country body = countryService.findById(id);
		return RestResponseBuilder.ok(body);
	}
	@GET
	@Path("/refdata")
	@JsonView(value=Views.List.class)
	@Operation(description = "Returns list of reference types")
	public RestResponse<List<RefData>>findAllReferenceTypes() {
		List<RefData> body = refDataService.findAll();
		return RestResponseBuilder.ok(body);
	}
	@GET
	@Path("/refdata/{referenceType}")
	@JsonView(value=Views.List.class)
	@Operation(description = "Returns refrence types for the given types")
	public RestResponse<List<RefData>>findReferenceTypes(@RestPath String referenceType) {
		List<RefData> body = refDataService.findByReferenceType(referenceType);
		return RestResponseBuilder.ok(body);
	}
}
