package top.yangbq.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import top.yangbq.mapper.ArticlesMapper;
import top.yangbq.mapper.UsersMapper;
import top.yangbq.pojo.Articles;
import top.yangbq.pojo.ArticlesExample;
import top.yangbq.vo.ArticlesVo;
import top.yangbq.vo.resp.HotArticleVo;

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

    @Autowired
    StringRedisTemplate stringRedisTemplate;

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
        ArticlesExample.Criteria criteria = example.createCriteria ( );

        if (searchEntity != null) {
            if (!StringUtils.isEmpty ( searchEntity.getArticleTitle ( ) )) {
                criteria.andArticleTitleLike ( "%" + searchEntity.getArticleTitle ( ) + "%" );
            }
            if (!StringUtils.isEmpty ( searchEntity.getArticleTag ( ) )) {
                criteria.andArticleTagLike ( "%" + searchEntity.getArticleTag ( ) + "%" );
            }
        }

        PageHelper.startPage ( currentPage , pageSize );
        List < Articles > articles = articlesMapper.selectByExample ( example );
        PageInfo < Articles > pageInfo = new PageInfo <> ( articles );

        List < Articles > list = pageInfo.getList ( );

        Map entries = redisTemplate.boundHashOps ( "articleScanCount" ).entries ( );
        Map comentCountHash = stringRedisTemplate.boundHashOps ( "comentCountHash" ).entries ( );
        list.forEach ( article -> {
            if (StringUtils.isEmpty ( entries.get ( article.getArticleId ( ) ) )) {
                article.setArticleScan ( 0 );
            } else {
                article.setArticleScan ( (Integer) entries.get ( article.getArticleId ( ) ) );
            }
            if (StringUtils.isEmpty ( comentCountHash.get ( article.getArticleId ( ) ) )) {
                article.setArticleReply ( 0 );
            } else {
                article.setArticleReply ( Integer.parseInt ( (String) comentCountHash.get ( article.getArticleId ( ) ) ) );
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
     * @MethodName: cron
     * @Description 定时将redis中刘浏览量 , 评论量更新到数据库！每天凌晨四点（04:00）执行
     * @Author yangbq
     * @MethodReturnType void
     * @ParameterNames []
     * @Date 2021/1/12
     * @Time 11:43
     */
    //每天四点
//    @Scheduled ( cron = "0 0 4 * * ?" )
    //每一分钟
    @Scheduled ( cron = "0 0/1 * * * ?" )
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

        BoundHashOperations comentCountHash = stringRedisTemplate.boundHashOps ( "comentCountHash" );
        Set keys1 = comentCountHash.keys ( );
        List list1 = comentCountHash.multiGet ( keys1 );
        if (keys1 != null && list1 != null) {
            for (int i = 0; i < list1.size ( ); i++) {
                Articles articles = new Articles ( );
                List < String > keysList2 = new ArrayList <> ( keys1 );
                articles.setArticleId ( keysList2.get ( i ) );
                articles.setArticleReply ( Integer.valueOf ( (String) list1.get ( i ) ) );
                articlesMapper.updateByPrimaryKeySelective ( articles );
            }
        }

    }

//    @Override
//    public List < Map < String, String > > getAllTags () {
//
//        Set < Map < String, String > > tagMapList = new HashSet <> ( );
//        ArticlesExample example = new ArticlesExample ( );
//        List < Articles > articles = articlesMapper.selectByExample ( example );
//
//        for (Articles article : articles) {
////            System.out.println ( article.getArticleTag () );
//            String articleTag = article.getArticleTag ( );
//            List < String > stringList = Arrays.asList ( articleTag.replaceAll ( " " , "" ).split ( "," ) );
////            System.out.println ( stringList );
//            for (String s : stringList) {
//                Map < String, String > map = new HashMap <> ( 30 );
//                System.out.println ( s );
//                map.put ( "name" , s );
//                example.createCriteria ( ).andArticleTagLike ( "%" + s + "%" );
//                Long count = articlesMapper.countByExample ( example );
//                map.put ( "count" , String.valueOf ( count ) );
//                example.clear ( );
//                tagMapList.add ( map );
//            }
//
//        }
//        return new ArrayList <> ( tagMapList );
//    }

    @Override
    public List < Map < String, String > > getAllTags () {
        List < Map < String, String > > returnList = new ArrayList <> ( 20 );
        List < Articles > articles = articlesMapper.selectByExample ( new ArticlesExample ( ) );
        List < String > tagList = new ArrayList <> ( 20 );
        for (Articles article : articles) {
            String articleTag = article.getArticleTag ( );
            List < String > stringList = Arrays.asList ( articleTag.split ( "," ) );
            tagList.addAll ( stringList );
        }

        Map < String, String > tagCountMap = new HashMap <> ( 2 );
        for (String tag : tagList) {
            if (tagCountMap.get ( tag ) == null) {
                tagCountMap.put ( tag , String.valueOf ( 1 ) );
            } else {
                tagCountMap.put ( tag , String.valueOf ( Integer.parseInt ( tagCountMap.get ( tag ) ) + 1 ) );
            }
        }

        Set < Map.Entry < String, String > > entrieSet = tagCountMap.entrySet ( );
        entrieSet.forEach ( entry -> {
            Map < String, String > map = new HashMap <> ( 2 );
            map.put ( "name" , entry.getKey ( ) );
            map.put ( "count" , entry.getValue ( ) );
            returnList.add ( map );
        } );

        return returnList;
    }

    @Override
    public List < HotArticleVo > getHotArticle () {
        ArticlesExample example = new ArticlesExample ( );
        example.setOrderByClause ( "article_scan desc" );
        List < Articles > articles = articlesMapper.selectByExample ( example ).subList ( 0 , 5 );
        List < HotArticleVo > hotArticleVoList = new ArrayList <> ( 5 );

        for (Articles article : articles) {
            HotArticleVo hotArticleVo = new HotArticleVo ( );
            hotArticleVo.setArticleId ( article.getArticleId ( ) );
            hotArticleVo.setArticleCover ( article.getArticleCover ( ) );
            hotArticleVo.setArticleDate ( article.getArticleDate ( ) );
            hotArticleVo.setArticleScan ( article.getArticleScan ( ) );
            hotArticleVo.setArticleTitle ( article.getArticleTitle ( ) );
            hotArticleVo.setUserHeadPic ( userMapper.selectByPrimaryKey ( article.getUserId ( ) ).getUserHeadpic ( ) );
            hotArticleVoList.add ( hotArticleVo );
        }
//        System.out.println ( hotArticleVoList );
        return hotArticleVoList;
    }


}
