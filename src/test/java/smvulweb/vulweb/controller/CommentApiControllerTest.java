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
import smvulweb.vulweb.dto.comment.AddCommentRequest;
import smvulweb.vulweb.dto.comment.UpdateCommentRequest;
import smvulweb.vulweb.repository.BoardRepository;
import smvulweb.vulweb.repository.CommentRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class CommentApiControllerTest {
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

    private Article savedArticle;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        commentRepository.deleteAll();

        final String articleTitle = "title";
        final String articleContent = "content";
        final String articleAuthor = "author";

        savedArticle = boardRepository.save(Article.builder()
                .title(articleTitle)
                .content(articleContent)
                .author(articleAuthor)
                .build());
    }

    @DisplayName("addComment : 댓글 추가 성공")
    @Test
    public void 요청_댓글생성() throws Exception {
        //given
        //댓글 작성
        final String url = "/api/article/{id}/comments";
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

    @DisplayName("deleteComment : 댓글 삭제 성공")
    @Test
    public void 요청_댓글삭제() throws Exception {
        //given
        final String nickname = "nickname";
        final String commentContent = "comment";

        Comment savedComment = commentRepository.save(Comment.builder()
                .nickname(nickname)
                .comment(commentContent)
                .article(savedArticle)
                .build());

        //when
        final String deleteUrl = "/api/delete/comment/{id}";
        ResultActions result = mockMvc.perform(get(deleteUrl, savedComment.getId()));

        //then
        result.andExpect(status().isOk());

        List<Comment> comments = commentRepository.findAll();

        assertThat(comments.size()).isEqualTo(0);

    }

    @DisplayName("updateComment: 댓글 수정 성공")
    @Test
    public void 요청_댓글수정() throws Exception {
        //given
        final String nickname = "nickname";
        final String originalComment = "comment";

        Comment savedComment = commentRepository.save(Comment.builder()
                .nickname(nickname)
                .comment(originalComment)
                .article(savedArticle)
                .build());

        final String updateUrl = "/api/update/comment/{id}";
        final String newComment = "new Comment";

        UpdateCommentRequest request = new UpdateCommentRequest(newComment);

        //when
        ResultActions result = mockMvc.perform(get(updateUrl, savedComment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        //then
        result.andExpect(status().isOk());

        Comment comment = commentRepository.findById(savedComment.getId()).get();

        assertThat(comment.getComment()).isEqualTo(newComment);
    }
}