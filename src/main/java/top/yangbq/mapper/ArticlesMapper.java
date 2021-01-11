package top.yangbq.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.yangbq.pojo.Articles;
import top.yangbq.pojo.ArticlesExample;

import java.util.List;

@Repository
@Mapper
public interface ArticlesMapper {
    long countByExample ( ArticlesExample example );

    int deleteByExample ( ArticlesExample example );

    int deleteByPrimaryKey ( String articleId );

    int insert ( Articles record );

    int insertSelective ( Articles record );

    List<Articles> selectByExample ( ArticlesExample example );

    Articles selectByPrimaryKey ( String articleId );

    int updateByExampleSelective ( @Param ( "record" ) Articles record , @Param ( "example" ) ArticlesExample example );

    int updateByExample ( @Param ( "record" ) Articles record , @Param ( "example" ) ArticlesExample example );

    int updateByPrimaryKeySelective ( Articles record );

    int updateByPrimaryKey ( Articles record );
}