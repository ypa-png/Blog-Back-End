package top.yangbq.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleVo {

    private String articleCover;

    private String articleTitle;

    private Date articleDate;

    private Integer articleScan;

    private String userHeadPic;

    private String articleId;

}
