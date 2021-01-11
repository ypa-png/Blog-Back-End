package top.yangbq.vo;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yangbq.pojo.Articles;
import top.yangbq.pojo.Users;
import top.yangbq.service.ArticleService;
import top.yangbq.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlesVo {



    private Articles article;
    private List <String> articleTags;
    private String username;
    private PageInfo pageInfo;



    public static String getArticleTagAsString (List<String> articleTag){
        return articleTag
                .toString ()
                .replace ( "[" ,"")
                .replaceAll ( " ","" )
                .replace ( "]","" ) ;
    }

    public static List<String> getArticleTagAsList (String articleTag){
        return Arrays.asList ( articleTag
                .replace ( "[" ,"")
                .replaceAll ( " ","" )
                .replace ( "]","" )
                .split ( "," ) ) ;
    }

    public static List<ArticlesVo> getArticleVoAsList(List<Articles> articles){
        List<ArticlesVo> avs=new ArrayList <> (  );
        ArticlesVo articlesVo=null;
        for (Articles a : articles) {
            articlesVo=new ArticlesVo (  );
            articlesVo.setArticle ( a );
            articlesVo.setArticleTags ( ArticlesVo.getArticleTagAsList ( a.getArticleTag () ) );
            avs.add ( articlesVo );
        }
//        for (ArticlesVo av : avs) {
//            log.info ( "avs:[{}]",av );
//        }
        return avs;
    }

}