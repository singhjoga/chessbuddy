package net.chessbuddies.system.country;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.technovator.api.common.services.BaseService;

@ApplicationScoped
public class CountryService extends BaseService{
	private static Logger LOG = LoggerFactory.getLogger(CountryService.class);
	@Inject
	private CountryRepo repo;
	public CountryService() {

	}
	public Country findById(String id) {
		return repo.findById(id);
	}
	public List<Country> findAll() {
		return repo.findAll().list();
	}
}
