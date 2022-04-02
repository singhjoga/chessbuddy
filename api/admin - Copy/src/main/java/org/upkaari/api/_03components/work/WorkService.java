package org.upkaari.api._03components.work;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.upkaari.api.common.services.BaseCrudService;

@Singleton
public class WorkService extends BaseCrudService<Work, String>{
	@Inject
	public WorkService(WorkRepository repo) {
		super(repo, Work.class, String.class);

	}

}
