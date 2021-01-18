package top.yangbq.service;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import top.yangbq.pojo.Articles;
import top.yangbq.pojo.ArticlesExample;
import top.yangbq.vo.ArticlesVo;
import top.yangbq.vo.resp.HotArticleVo;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    long countByExample( ArticlesExample example);

    int deleteByExample(ArticlesExample example);

    int deleteByPrimaryKey(String articleId);

    int insert( Articles record);

    int insertSelective(Articles record);

    List <Articles> selectByExample( ArticlesExample example);

    Articles selectByPrimaryKey(String articleId);

    int updateByExampleSelective( @Param ("record") Articles record, @Param("example") ArticlesExample example);

    int updateByExample(@Param("record") Articles record, @Param("example") ArticlesExample example);

    int updateByPrimaryKeySelective(Articles record);

    int updateByPrimaryKey(Articles record);

    Map listArticle( Integer currentPage, Integer pageSize, Articles searchEntity);

    List< String> getArticlePicList ( Integer count );


    List< Map< String, String>> getAllTags ();

    List< HotArticleVo> getHotArticle ();
}
