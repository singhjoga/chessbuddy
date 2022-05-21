package net.chessbuddies.player;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.technovator.api.common.services.BaseChildEntityService;

@Singleton
public class QuestionAnswerService extends BaseChildEntityService<QuestionAnswer, String, String>{
	private QuestionAnswerRepo repo;
	@Inject
	public QuestionAnswerService(QuestionAnswerRepo repo) {
		super(repo, QuestionAnswer.class, String.class);
		this.repo=repo;
	}
/*
	@Override
	public List<QuestionAnswer> findAll(String playerId) {
		return repo.findAll(playerId);
	}
	*/
}
