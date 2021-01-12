package top.yangbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.yangbq.pojo.*;
import top.yangbq.service.ArticleCommentService;
import top.yangbq.service.ArticleService;
import top.yangbq.service.UserService;
import top.yangbq.utils.IpUtils;
import top.yangbq.vo.CommentUserInfo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping ( "/portal" )
/**
 *@ClassName: PortalController
 *@Description
 *@Author yangbq
 *@Date 2021/1/11
 *@Time 14:19
 */
public class PortalController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleCommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @GetMapping ( value = "/contentList/{count}" )
    public Map getContentList ( @PathVariable ( "count" ) Integer count ) {

        Map map = new HashMap ( );
        try {
            List < String > picUrls = articleService.getArticlePicList ( count );
            map.put ( "pics" , picUrls );
            map.put ( "state" , true );
        } catch (Exception e) {
            e.printStackTrace ( );
            map.put ( "msg" , e.getMessage ( ) );
            map.put ( "state" , false );
        }
        return map;
    }

    @PostMapping ( "/articleList" )
    public Map pageList ( @RequestParam ( value = "currentPage", defaultValue = "1" ) Integer currentPage ,
                          @RequestParam ( value = "pageSize", defaultValue = "4  " ) Integer pageSize
            , @RequestBody Articles article ) {
        Map map = null;
        try {
            map = articleService.listArticle ( currentPage , pageSize , article );
            map.put ( "state" , true );
        } catch (Exception e) {
            e.printStackTrace ( );
            map.put ( "msg" , e.getMessage ( ) );
            map.put ( "state" , false );
        }
        return map;
    }


    @GetMapping ( "/article/{articleId}" )
    public Map < String, Object > getArticleById ( @PathVariable String articleId , HttpServletRequest request , HttpServletResponse response ) {
        Map < String, Object > map = new HashMap <> ( );
        try {
            Articles articles = articleService.selectByPrimaryKey ( articleId );
            Users users = userService.selectByPrimaryKey ( articles.getUserId ( ) );
            String author = users.getUserName ( );
            String userHeadpic = users.getUserHeadpic ( );

            Integer count = this.articleReadCount ( articleId , request );
            articles.setArticleScan ( count );

            map.put ( "article" , articles );
            map.put ( "userHeadpic" , userHeadpic );
            map.put ( "author" , author );
            map.put ( "state" , true );
        } catch (Exception e) {
            e.printStackTrace ( );
            map.put ( "msg" , e.getMessage ( ) );
            map.put ( "state" , false );
        }
        return map;
    }

    /**
     * @MethodName: articleReadCount
     * @Description 给文章访问量赋值，并设置同一篇文章半小时内重复访问时，访问量只增加一次
     * @Author yangbq
     * @MethodReturnType java.lang.Integer
     * @ParameterNames [articleId , request]
     * @Date 2021/1/6
     * @Time 21:50
     */
    //获取IP地址
//        String ipAddr = IpUtils.getIpAddr ( request );
    private Integer articleReadCount ( String articleId , HttpServletRequest request ) throws Exception {
        BoundHashOperations countHash = redisTemplate.boundHashOps ( "articleScanCount" );
        BoundHashOperations repeatScanHash = stringRedisTemplate.boundHashOps ( "repeatScanHash" );

        if (!repeatScanHash.hasKey ( articleId )) {
            if (countHash.hasKey ( articleId )) {
                Integer count = (Integer) countHash.get ( articleId );
                countHash.put ( articleId , count + 1 );
            } else {
                countHash.put ( articleId , 1 );
            }
            repeatScanHash.put ( articleId , "0" );
            repeatScanHash.expire ( 30 , TimeUnit.MINUTES );
        }

        return (Integer) countHash.get ( articleId );
    }

    @GetMapping ( "/testRedis" )
    public Map test ( String articleId ) {
        Map map = new HashMap ( 50 );

        stringRedisTemplate.boundHashOps ( "repeatScanHash" ).put ( articleId , "0" );
        stringRedisTemplate.boundHashOps ( "repeatScanHash" ).expire ( 30 , TimeUnit.SECONDS );
        map.put ( articleId , stringRedisTemplate.boundHashOps ( "repeatScanHash" ).get ( articleId ) );
        return map;
    }


    /**
     * @MethodName: commentList
     * @Description 根据文章id查询评论，用于前台文章下显示
     * @Author yangbq
     * @MethodReturnType java.util.Map<java.lang.String , java.lang.Object>
     * @ParameterNames [articleId]
     * @Date 2020/12/9
     * @Time 18:31
     */
    @GetMapping ( value = "/comment/{articleId}" )
    public Map < String, Object > commentList ( @PathVariable ( "articleId" ) String articleId ) {
        Map < String, Object > map = new HashMap <> ( );
        ArticleCommentExample example = new ArticleCommentExample ( );
        ArticleCommentExample.Criteria criteria = example.createCriteria ( );
        criteria.andArticleIdEqualTo ( articleId );
        criteria.andStatusEqualTo ( true );
        List < ArticleComment > comments = commentService.selectByExample ( example );
        for (ArticleComment comment : comments) {
            CommentUserInfo commentUserInfo = new CommentUserInfo ( );
            Users users = userService.selectByPrimaryKey ( comment.getFromUserId ( ) );
            commentUserInfo.setUsername ( users.getUserName ( ) );
            commentUserInfo.setUserHeadPic ( users.getUserHeadpic ( ) );
            commentUserInfo.setUserId ( users.getUserId ( ) );

            comment.setCommentUserInfo ( commentUserInfo );
        }
        map.put ( "comments" , comments );
        map.put ( "total" , comments.size ( ) );
        return map;
    }


    @PostMapping ( "/comment/add" )
    public Map < String, Object > addComment ( @RequestParam ( "articleId" ) String articleId ,
                                               @RequestParam ( "pid" ) Integer pid ,
                                               @RequestParam ( "commentContent" ) String commentContent ,
                                               @RequestParam ( "userId" ) String userId ) {
        Map < String, Object > map = new HashMap <> ( );
        ArticleComment comment = new ArticleComment ( );

        comment.setArticleId ( articleId );
        comment.setPid ( pid );
        commentContent = commentContent
                .replaceAll ( "傻" , " * " )
                .replaceAll ( "逼" , " * " )
                .replaceAll ( "操" , " * " )
                .replaceAll ( "妈" , " * " )
                .replaceAll ( "娘" , " * " )
                .replaceAll ( "狗逼" , " * * " )
                .replaceAll ( "狗币" , " * * " )
                .replaceAll ( "日" , " * " )
                .replaceAll ( "干你妈" , " * * * " )
                .replaceAll ( "草尼玛" , " * * * " )
                .replaceAll ( "干你娘" , " * * * " )
                .replaceAll ( "干尼玛" , " * * * " )
                .replaceAll ( "狗 逼" , " * * * " );
        comment.setContent ( commentContent );
        comment.setFromUserId ( userId );

        comment.setStatus ( true );

        try {
            commentService.insertSelective ( comment );
            map.put ( "msg" , "评论已提交" );
            map.put ( "state" , true );
        } catch (Exception e) {
            map.put ( "msg" , "出问题了QAQ" );
            map.put ( "state" , false );
        }
        return map;
    }

    @PostMapping ( "/comment/login" )
    public Map < String, Object > commentLogin ( @RequestParam ( "username" ) String username , @RequestParam ( "password" ) String password ) {
        Map < String, Object > map = new HashMap <> ( );
        UsersExample usersExample = new UsersExample ( );
//        System.out.println ( username + "/////" + password );
        usersExample.createCriteria ( ).
                andUserNameEqualTo ( username )
                .andUserPwdEqualTo ( password );
        List < Users > users = userService.selectByExample ( usersExample );
        if (users.size ( ) > 0) {
            map.put ( "state" , true );
            map.put ( "username" , users.get ( 0 ).getUserName ( ) );
            map.put ( "userHeadPic" , users.get ( 0 ).getUserHeadpic ( ) );
            map.put ( "userId" , users.get ( 0 ).getUserId ( ) );
        } else {
            map.put ( "msg" , "账号或密码错误" );
            map.put ( "state" , false );
        }
        return map;
    }


}
