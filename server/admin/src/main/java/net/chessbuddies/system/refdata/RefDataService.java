package net.chessbuddies.system.refdata;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.technovator.api.common.services.BaseService;

@ApplicationScoped
public class RefDataService extends BaseService{
	
	private RefDataRepository repo;

	@Inject
	public RefDataService(RefDataRepository repo) {
		this.repo=repo;
	}

	public List<RefData> findByReferenceType(String referenceType) {
		return repo.findByReferenceType(referenceType);
	}
	public List<RefData> findAll() {
		return repo.findAllReferenceTypes();
	}
}
