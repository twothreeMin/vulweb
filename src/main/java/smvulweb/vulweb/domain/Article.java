package smvulweb.vulweb.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity // 엔티티로 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
        @Id // id 필드를 기본키로 지정
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", updatable = false)
        private Long id;

        @Column(name = "title", nullable = false) // 'title'이라는 not null 컬럼과 매핑
        private String title;

        @Column(name = "content", nullable = false) // 'content'이라는 not null 컬럼과 매핑
        private String content;

        @Column(name = "author", nullable = false)
        private String author;

        @OneToMany(mappedBy = "article", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
        @OrderBy("createdDate desc")
        private List<Comment> comments;

    @Builder
    public Article(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }



    // @NoArgsConstructor 의해 필요 없음
//    protected Article() {}
//
//    @Getter에 의해 필요 없음
//    public Long getId() {
//        return id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getContent() {
//        return content;
//    }
}
