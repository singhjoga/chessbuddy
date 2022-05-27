package net.chessbuddies.system.refdata;

import java.util.List;

import javax.inject.Singleton;

import com.technovator.api.common.repos.EntityRepository;

@Singleton
public class RefDataRepository implements EntityRepository<RefData, String>{
	public List<RefData> findByReferenceType(String referenceType) {
		return find("referenceType",referenceType).list();
	}
	public List<RefData> findAllReferenceTypes() {
		return find("referenceType IS NULL AND isDisabled=false").list();
	}
}
