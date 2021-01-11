package top.yangbq.service;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import top.yangbq.pojo.ArticleComment;
import top.yangbq.pojo.ArticleCommentExample;

import java.util.List;

public interface ArticleCommentService {
    long countByExample( ArticleCommentExample example);

    int deleteByExample(ArticleCommentExample example);

    int deleteByPrimaryKey(Integer commentId);

    int insert( ArticleComment record);

    int insertSelective(ArticleComment record);

    List<ArticleComment> selectByExample(ArticleCommentExample example);

    ArticleComment selectByPrimaryKey(Integer commentId);

    int updateByExampleSelective(@Param("record") ArticleComment record, @Param("example") ArticleCommentExample example);

    int updateByExample(@Param("record") ArticleComment record, @Param("example") ArticleCommentExample example);

    int updateByPrimaryKeySelective(ArticleComment record);

    int updateByPrimaryKey(ArticleComment record);

    PageInfo < ArticleComment > commentPageList( Integer currentPage, Integer pageSize, @Param("example") ArticleCommentExample example);
}
