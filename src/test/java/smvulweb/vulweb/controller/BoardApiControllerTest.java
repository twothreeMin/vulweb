package smvulweb.vulweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import smvulweb.vulweb.domain.Article;
import smvulweb.vulweb.dto.article.AddArticleRequest;
import smvulweb.vulweb.dto.article.UpdateArticleRequest;
import smvulweb.vulweb.repository.BoardRepository;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BoardApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        boardRepository.deleteAll();
    }

    @DisplayName("addArticle: 글 추가 성공")
    @Test
    public void 요청_글생성() throws Exception {
        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final String author = "author";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content, author);

        final String requestBody = objectMapper.writeValueAsString(userRequest);

        //when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(status().isCreated());

        List<Article> articles = boardRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllArticles: 글 목록 조회 성공")
    @Test
    public void 요청_글목록조회() throws Exception {
        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final String author = "author";

        boardRepository.save(Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));

    }

    @DisplayName("findArticle: 글 조회 성공")
    @Test
    public void 요청_글조회() throws Exception {
        //given
        final String url = "/api/article/{id}";
        final String title = "title";
        final String content = "content";
        final String author = "author";

        Article savedArticle = boardRepository.save(Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));
    }

    @DisplayName("deleteArticle: 글 삭제 성공")
    @Test
    public void 요청_글삭제() throws Exception {
        //given
        final String url = "/api/article/delete/{id}";
        final String title = "title";
        final String content = "content";
        final String author = "author";

        Article savedArticle = boardRepository.save(Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        //then
        resultActions.andExpect(status().isOk());

        List<Article> articles = boardRepository.findAll();
        assertThat(articles).isEmpty();
    }

    @DisplayName("updateArticle: 글 수정 성공")
    @Test
    public void 요청_글수정() throws Exception {
        //given
        final String url = "/api/article/update/{id}";
        final String title = "title";
        final String content = "content";
        final String author = "author";

        Article savedArticle = boardRepository.save(Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        final String newTitle = "new Title";
        final String newContent = "new Content";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        //when
        ResultActions result = mockMvc.perform(get(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        //then
        result.andExpect(status().isOk());

        Article article = boardRepository.findById(savedArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
    }

}