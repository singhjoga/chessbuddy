package net.chessbuddies.player;

import java.util.List;

import javax.inject.Singleton;

import com.technovator.api.common.repos.ChildEntityRepository;

@Singleton
public class QuestionAnswerRepo implements ChildEntityRepository<QuestionAnswer, String, String>{

	@Override
	public List<QuestionAnswer> findAll(String parentId) {
		return list("playerId", parentId);
	}
}
