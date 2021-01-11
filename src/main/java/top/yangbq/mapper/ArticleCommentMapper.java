package top.yangbq.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.yangbq.pojo.ArticleComment;
import top.yangbq.pojo.ArticleCommentExample;

@Repository
@Mapper
public interface ArticleCommentMapper {
    long countByExample(ArticleCommentExample example);

    int deleteByExample(ArticleCommentExample example);

    int deleteByPrimaryKey(Integer commentId);

    int insert(ArticleComment record);

    int insertSelective(ArticleComment record);

    List<ArticleComment> selectByExample(ArticleCommentExample example);

    ArticleComment selectByPrimaryKey(Integer commentId);

    int updateByExampleSelective(@Param("record") ArticleComment record, @Param("example") ArticleCommentExample example);

    int updateByExample(@Param("record") ArticleComment record, @Param("example") ArticleCommentExample example);

    int updateByPrimaryKeySelective(ArticleComment record);

    int updateByPrimaryKey(ArticleComment record);
}