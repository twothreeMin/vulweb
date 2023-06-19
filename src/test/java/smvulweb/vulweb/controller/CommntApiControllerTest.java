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
import smvulweb.vulweb.domain.Comment;
import smvulweb.vulweb.dto.AddCommentRequest;
import smvulweb.vulweb.repository.BoardRepository;
import smvulweb.vulweb.repository.CommentRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class CommntApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CommentRepository commentRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        commentRepository.deleteAll();
    }

    @DisplayName("addComment : 댓글 추가 성공")
    @Test
    public void addComment() throws Exception {
        //given
        //게시글 작성
        //게시글 하나 등록
        final String articleUrl = "/api/articles";
        final String articleTitle = "title";
        final String articleContent = "content";
        final String articleAuthor = "author";

        Article savedArticle = boardRepository.save(Article.builder()
                .title(articleTitle)
                .content(articleContent)
                .author(articleAuthor)
                .build());

        //댓글 작성
        final String url = "/api/articles/{id}/comments";
        final String nickname = "nickname";
        final String comment = "comment";
        final AddCommentRequest userRequest = new AddCommentRequest(nickname, comment);

        final String requestBody = objectMapper.writeValueAsString(userRequest);

        //when
        ResultActions result = mockMvc.perform(post(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(status().isOk());

        List<Comment> comments = commentRepository.findAll();
        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getNickname()).isEqualTo(nickname);
        assertThat(comments.get(0).getComment()).isEqualTo(comment);

    }
}