package net.chessbuddies.player;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.technovator.api.CrudTest;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import net.chessbuddies.api._01controllers.PlayerController;
import net.chessbuddies.player.Player;
import net.chessbuddies.player.QuestionAnswer;
@QuarkusTest
public class PlayerTest extends CrudTest<Player, String>{
	@TestHTTPEndpoint(PlayerController.class)
	@TestHTTPResource
    URL url;
	
	public PlayerTest() {
		super(Player.class, Player[].class);
	}
	@BeforeEach
	public void init() {
		super.setResourceUri(url.toString());
	}
    @Test
    public void testAddWithQuestions() throws Exception {
    	String id = super.testAdd();
    	String uri = getResourceUri(id)+"?returnQuestionAnswers=true";
    	Player player = getResource(uri, Player.class);
    	Assertions.assertEquals(3, player.getQuestionAnswers().size());
    	
    }
    protected Player create(String username, String password, String firstName, String lastName, LocalDate birthDate) {
    	Player obj = new Player();
    	obj.setUsername(username);
    	obj.setFirstName(firstName);
    	obj.setLastName(lastName);
    	obj.setBirthDate(birthDate);

    	return obj;	
    }
    protected Player create() {
    	Player player = create("p1", "p1","p1 first", "p1 last", LocalDate.now());
    	List<QuestionAnswer> answers = new ArrayList<>();
    	answers.add(createAnswer("fav_color", "red", null));
    	answers.add(createAnswer("fav_pet", "jhota", null));
    	answers.add(createAnswer("fav_food", "saag", null));
    	
    	player.setQuestionAnswers(answers);
    	
    	return player;
    }
    
    private QuestionAnswer createAnswer(String questionCode, String answer, String playerId) {
    	QuestionAnswer ans = new QuestionAnswer();
    	ans.setQuestionCode(questionCode);
    	ans.setAnswer(answer);
    	ans.setPlayerId(playerId);
    	
    	return ans;
    }
    
}