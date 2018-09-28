package br.com.alura.forum.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Topic {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String shortDescription;
	
	@Lob
	private String content;
	
	private Instant creationInstant = Instant.now();
	private Instant lastUpdate = Instant.now();
	
	@Enumerated(EnumType.STRING)
	private TopicStatus status = TopicStatus.NOT_ANSWERED;
	
	@ManyToOne
	private User owner;
	
	@ManyToOne
	private Course course;
	
	@OneToMany(mappedBy = "topic")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<Answer> answers = new ArrayList<>();
	
	/**
	 * @deprecated
	 */
	public Topic() {}
	
	public Topic(String shortDescription, String content, User owner, Course course) {
		super();
		this.shortDescription = shortDescription;
		this.content = content;
		this.owner = owner;
		this.course = course;
	}

	public Long getId() {
		return id;
	}

	public String getShortDescription() {
		return shortDescription;
	}
	
	public String getContent() {
		return content;
	}
	
	public Instant getCreationInstant() {
		return creationInstant;
	}

	public Instant getLastUpdate() {
		return lastUpdate;
	}

	public User getOwner() {
		return owner;
	}

	public Course getCourse() {
		return course;
	}
	
	public Integer getNumberOfAnswers() {
		return this.answers.size();
	}
	
	public TopicStatus getStatus() {
		return status;
	}

}
