package top.yangbq.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.yangbq.pojo.ArticleImage;
import top.yangbq.pojo.ArticleImageExample;
@Mapper
@Repository
public interface ArticleImageMapper {
    long countByExample(ArticleImageExample example);

    int deleteByExample(ArticleImageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleImage record);

    int insertSelective(ArticleImage record);

    List<ArticleImage> selectByExample(ArticleImageExample example);

    ArticleImage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ArticleImage record, @Param("example") ArticleImageExample example);

    int updateByExample(@Param("record") ArticleImage record, @Param("example") ArticleImageExample example);

    int updateByPrimaryKeySelective(ArticleImage record);

    int updateByPrimaryKey(ArticleImage record);
}