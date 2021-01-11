package top.yangbq.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.yangbq.pojo.ArticleComment;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    private ArticleComment articleComment;

    private CommentUserInfo commentUserInfo;
}
