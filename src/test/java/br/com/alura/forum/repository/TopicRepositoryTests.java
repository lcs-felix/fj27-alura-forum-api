package br.com.alura.forum.repository;

import br.com.alura.forum.model.Category;
import br.com.alura.forum.model.Course;
import br.com.alura.forum.model.OpenTopicsByCategory;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic_domain.Topic;
import br.com.alura.forum.repository.setup.TopicRepositoryTestsSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class TopicRepositoryTests {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TopicRepository topicRepository;

    @Test
    public void shouldSaveATopic() {
        Topic topic = new Topic("Descrição do tópico", "Conteúdo do tópico", null, null);

        Topic persistedTopic = this.topicRepository.save(topic);
        Topic foundTopic = this.testEntityManager.find(Topic.class, persistedTopic.getId());

        assertThat(foundTopic).isNotNull();

        assertThat(foundTopic.getShortDescription())
                .isEqualTo(persistedTopic.getShortDescription());
    }

    @Test
    public void shouldReturnAllExistentTopics() {

        Topic topic = new Topic("Descrição do tópico", "Conteúdo do tópico", null, null);

        Topic persistedTopic = this.testEntityManager.persist(topic);
        List<Topic> allTopics = this.topicRepository.list();

        assertThat(allTopics).isNotEmpty();
        assertThat(allTopics).hasSize(1);
        assertThat(allTopics).containsExactly(persistedTopic);
    }

    @Test
    public void shouldReturnOpenTopicsByCategory() {

        TopicRepositoryTestsSetup testSetup = new TopicRepositoryTestsSetup(testEntityManager);
        testSetup.openTopicsByCategorySetup();

        List<OpenTopicsByCategory> openTopics = this.topicRepository.findOpenTopicsByCategory();

        assertThat(openTopics).isNotEmpty();
        assertThat(openTopics).hasSize(2);

        assertThat(openTopics.get(0).getCategoryName())
                .isEqualTo("Programação");
        assertThat(openTopics.get(0).getTopicCount()).isEqualTo(2);

        assertThat(openTopics.get(1).getCategoryName())
                .isEqualTo("Front-end");
        assertThat(openTopics.get(1).getTopicCount()).isEqualTo(1);
    }
}
