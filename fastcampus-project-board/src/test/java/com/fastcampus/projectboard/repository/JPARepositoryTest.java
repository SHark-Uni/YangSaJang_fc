package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.config.JpaConfig;
import com.fastcampus.projectboard.domain.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Import(JpaConfig.class)
@DisplayName("JPA 연결 테스트")
@DataJpaTest
class JPARepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JPARepositoryTest(@Autowired ArticleRepository articleRepository,
                             @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @Test
    @DisplayName("Select 테스트")
    void givenTestData_whenSelecting_thenWorksFine(){
        //given

        //when
        List<Article> articles = articleRepository.findAll();
        //then

        assertThat(articles)
                .isNotNull()
                .hasSize(123);

    }

    @Test
    @DisplayName("Insert 테스트")
    void givenTestData_whenInsert_thenPlusOne(){
        //given
        long previousCount = articleRepository.count();
        Article article = Article.of("new Article","new Content","#Spring");
        //when
        Article savedArticle = articleRepository.save(article);
        //then
        long afterCount = articleRepository.count();
        assertThat(afterCount).isEqualTo(previousCount+1);

    }

    @Test
    @DisplayName("update 테스트")
    void givenTestData_whenUpdating_thenWorksFine(){
        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashTag = "#SpringBootss";
        article.setHashtag(updatedHashTag);
        //when
        Article savedArticle = articleRepository.saveAndFlush(article);
        //then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag",updatedHashTag);

    }

    @Test
    @DisplayName("delete 테스트")
    void givenTestData_whenDeleting_thenWorksFine(){
        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        long prevArticleCount = articleRepository.count();
        long prevArticleComment = articleCommentRepository.count();

        int deletedCommentSize = article.getArticleComments().size();
        //when
        articleRepository.delete(article);
        //then
        assertThat(articleRepository.count()).isEqualTo(prevArticleCount-1);
        assertThat(articleCommentRepository.count()).isEqualTo(prevArticleComment-deletedCommentSize);

    }

}