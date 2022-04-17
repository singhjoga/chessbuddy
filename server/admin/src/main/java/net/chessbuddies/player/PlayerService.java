package net.chessbuddies.player;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import com.technovator.api.common.services.BaseCrudService;

@Singleton
public class PlayerService extends BaseCrudService<Player, String>{
	@Inject
	private QuestionAnswerService qaService;
	@Inject
	public PlayerService(PlayerRepo repo) {
		super(repo, Player.class, String.class);

	}
	
	@Override
	@Transactional
	public Player add(Player obj) {
		try {
			Player saved = super.add(obj);
			if (obj.getQuestionAnswers() != null) {
				for (QuestionAnswer a: obj.getQuestionAnswers()) {
					 a.setPlayerId(saved.getId());
				}
				qaService.addAll(obj.getQuestionAnswers());	
			}

			return saved;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public Player getById(String id, Boolean returnQuestionAnswers) {
		Player player = getById(id);
		if (Boolean.TRUE.equals(returnQuestionAnswers)) {
		 List<QuestionAnswer> answers =	qaService.findAll(id);
		}
	}
}
