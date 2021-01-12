package top.yangbq.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.yangbq.mapper.ArticlesMapper;
import top.yangbq.mapper.UsersMapper;
import top.yangbq.pojo.Articles;
import top.yangbq.pojo.ArticlesExample;
import top.yangbq.vo.ArticlesVo;

import java.util.*;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticlesMapper articlesMapper;

    @Autowired
    private UsersMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public long countByExample ( ArticlesExample example ) {
        return 0;
    }

    @Override
    public int deleteByExample ( ArticlesExample example ) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey ( String articleId ) {
        return articlesMapper.deleteByPrimaryKey ( articleId );
    }

    @Override
    public int insert ( Articles record ) {
        return 0;
    }

    @Override
    public int insertSelective ( Articles record ) {
        record.setArticleReply ( 0 );
        record.setArticleScan ( 0 );
        record.setArticleGood ( 0 );
        int i = articlesMapper.insertSelective ( record );
        if (i > 0) {
            return i;
        }
        throw new RuntimeException ( "添加失败" );
    }

    @Override
    public List < Articles > selectByExample ( ArticlesExample example ) {
        List < Articles > articles = articlesMapper.selectByExample ( example );
        if (articles.size ( ) > 0) {
            return articles;
        }
        throw new RuntimeException ( "没有文章" );
    }

    @Override
    public Articles selectByPrimaryKey ( String articleId ) {
        Articles articles = articlesMapper.selectByPrimaryKey ( articleId );
        if (articles != null) {
            return articles;
        }
        throw new RuntimeException ( "文章不存在" );
    }

    @Override
    public int updateByExampleSelective ( Articles record , ArticlesExample example ) {
        int i = articlesMapper.updateByExampleSelective ( record , example );
        return i;
    }

    @Override
    public int updateByExample ( Articles record , ArticlesExample example ) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective ( Articles record ) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey ( Articles record ) {
        return 0;
    }

//    @Override
//    public PageInfo < Articles > listArticle ( Integer currentPage, Integer pageSize, ArticlesExample example ) {
//        PageHelper.startPage ( currentPage,pageSize );
//        List < Articles > articles = articlesMapper.selectByExample ( example );
//        PageInfo < Articles > pageInfo = new PageInfo <> ( articles );
//        return pageInfo;
//    }

    @Override
    public Map listArticle ( Integer currentPage , Integer pageSize , Articles searchEntity ) {
        Map < String, Object > map = new HashMap <> ( );
        ArticlesExample example = new ArticlesExample ( );
        if (searchEntity != null) {
            if (!StringUtils.isEmpty ( searchEntity.getArticleTitle ( ) )) {
                example.createCriteria ( ).andArticleTitleLike ( "%" + searchEntity.getArticleTitle ( ) + "%" );
            }
        }
        PageHelper.startPage ( currentPage , pageSize );
        List < Articles > articles = articlesMapper.selectByExample ( example );
        PageInfo < Articles > pageInfo = new PageInfo <> ( articles );

        List < Articles > list = pageInfo.getList ( );


        Map entries = redisTemplate.boundHashOps ( "articleScanCount" ).entries ( );
        list.forEach ( article -> {
            if (StringUtils.isEmpty ( entries.get ( article.getArticleId ( ) ) )) {
                article.setArticleScan ( 0 );
            } else {
                article.setArticleScan ( (Integer) entries.get ( article.getArticleId ( ) ) );
            }
//            System.out.println ((Integer) entries.get ( article.getArticleId () ) );
        } );

        List < ArticlesVo > articleVoAsList = ArticlesVo.getArticleVoAsList ( list );
        for (ArticlesVo vo : articleVoAsList) {
            vo.setUsername ( userMapper.selectByPrimaryKey ( vo.getArticle ( ).getUserId ( ) ).getUserName ( ) );
        }

        map.put ( "articleVoAsList" , articleVoAsList );
        map.put ( "pageInfo" , pageInfo );
        return map;
    }

    @Override
    public List < String > getArticlePicList ( Integer count ) {
        if (count == null || count < 2) {
            throw new RuntimeException ( "参数异常" );
        }
        List < Articles > articles = articlesMapper.selectByExample ( new ArticlesExample ( ) );
        List < String > picUrls = new ArrayList <> ( );
        for (Articles article : articles) {
            if (picUrls.size ( ) <= count) {
                picUrls.add ( article.getArticleCover ( ) );
            }
        }
        return picUrls;
    }

    /**
    *@MethodName: cron
    *@Description 定时将redis中刘浏览量数据更新到数据库完成！
    *@Author yangbq
    *@MethodReturnType void
    *@ParameterNames []
    *@Date 2021/1/12
    *@Time 11:43
    */
    @Scheduled ( cron = "0/10 * * * * *" )
    public void cron () {
        BoundHashOperations articleScanCount = redisTemplate.boundHashOps ( "articleScanCount" );
        Set keys = articleScanCount.keys ( );
        List list = articleScanCount.multiGet ( keys );
        if (keys != null && list != null) {
            for (int i = 0; i < list.size ( ); i++) {
                Articles article = new Articles ( );
                List < String > keysList = new ArrayList < String > ( keys );
                article.setArticleId ( keysList.get ( i ) );
                article.setArticleScan ( (Integer) list.get ( i ) );
                articlesMapper.updateByPrimaryKeySelective ( article );
            }
        }
        log.info ( "定时将redis中刘浏览量数据更新到数据库完成！" );
    }

}
