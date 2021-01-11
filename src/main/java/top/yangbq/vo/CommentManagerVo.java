package top.yangbq.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.yangbq.pojo.ArticleComment;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentManagerVo {

    private String ArticleTitle;

    private ArticleComment articleComment;

    private String replyFromUsername;

    private String replyToUsername;
}
