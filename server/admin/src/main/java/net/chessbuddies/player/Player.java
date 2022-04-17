package net.chessbuddies.player;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.technovator.api.common.auditlog.AuditableMain;
import com.technovator.api.common.constants.Constants;
import com.technovator.api.common.domain.AbstractResource;

@Entity
@Table(name = "PLAYER")
public class Player extends AbstractResource<String> implements AuditableMain<String>{
	@Column(name="USERNAME")
	private String username;
	@Column(name="FIRST_NAME")
	private String firstName;
	@Column(name="LAST_NAME")
	private String lastName;
	@Column(name="EMAIL")
	private String email;
	@Column(name="LANG_CODE")
	private String langCode;
	@Column(name="BIRTH_DATE")
	@JsonFormat(pattern = Constants.JSON_DATE_FORMAT)
//	@JsonbDateFormat(value = Constants.JSON_DATE_FORMAT)
	private LocalDate birthDate;

	@Transient
	private List<QuestionAnswer> questionAnswers;
	
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public List<QuestionAnswer> getQuestionAnswers() {
		return questionAnswers;
	}

	public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}

	@JsonIgnore
	@Override
	public String getAppObjectType() {
		return this.getClass().getSimpleName();
	}

	@Override
	@JsonIgnore
	public String getName() {
		return username;
	}
}
