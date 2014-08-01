package in.co.pbs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_session_info")
public class UserSessionInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(nullable = false)
	private int id;

	@Column(nullable = false)
	private String sessionId;

	@Column(nullable = false)
	private String domain;

	@Column(nullable = false)
	private String asin;

	@Column(nullable = false)
	private boolean vote;

	public UserSessionInfo() {
		super();
	}

	public UserSessionInfo(String sessionId, String domain, String asin, boolean vote) {
		super();
		this.sessionId = sessionId;
		this.domain = domain;
		this.asin = asin;
		this.vote = vote;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public boolean isVote() {
		return vote;
	}

	public void setVote(boolean vote) {
		this.vote = vote;
	}

}
