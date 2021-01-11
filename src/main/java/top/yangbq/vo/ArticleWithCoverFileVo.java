package top.yangbq.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import top.yangbq.pojo.Articles;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleWithCoverFileVo {
    ArticlesVo articlesVo;
    MultipartFile cover;
}
