package top.yangbq.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: CommentVo
 * @Description 添加评论请求VO
 * @Author yangbq
 * @Date 2021/1/13
 * @Time 14:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    private String articleId;
    private Integer pid;
    private String commentContent;
    private String userId;
}
