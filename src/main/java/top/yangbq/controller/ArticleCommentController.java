package top.yangbq.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.yangbq.pojo.ArticleComment;
import top.yangbq.pojo.ArticleCommentExample;
import top.yangbq.pojo.Users;
import top.yangbq.pojo.UsersExample;
import top.yangbq.service.ArticleCommentService;
import top.yangbq.service.ArticleService;
import top.yangbq.service.UserService;
import top.yangbq.vo.CommentManagerVo;
import top.yangbq.vo.CommentUserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ArticleCommentController {

    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    /**
     * @MethodName: addComment
     * @Description 发布评论或回复其他评论
     * @Author yangbq
     * @MethodReturnType java.util.Map<java.lang.String , java.lang.Object>
     * @ParameterNames [articleId , pid, commentContent, userId]
     * @Date 2020/12/9
     * @Time 18:30
     */
    @PostMapping ( "/comment/add" )
    public Map < String, Object > addComment ( @RequestParam ( "articleId" ) String articleId ,
                                               @RequestParam ( "pid" ) Integer pid ,
                                               @RequestParam ( "commentContent" ) String commentContent ,
                                               @RequestParam ( "userId" ) String userId ) {
        Map < String, Object > map = new HashMap <> ( );
        ArticleComment comment = new ArticleComment ( );

        comment.setArticleId ( articleId );
        comment.setPid ( pid );
        comment.setContent ( commentContent );
        comment.setFromUserId ( userId );

        comment.setStatus ( true );

        try {
            articleCommentService.insertSelective ( comment );
            map.put ( "msg" , "评论已提交管理员审核" );
            map.put ( "state" , true );
        } catch (Exception e) {
            map.put ( "msg" , "出问题了QAQ，快去联系管理员吧" );
            map.put ( "state" , false );
        }
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
        List < ArticleComment > comments = articleCommentService.selectByExample ( example );
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

    /**
     * @MethodName: commentLogin
     * @Description 评论区登录验证
     * @Author yangbq
     * @MethodReturnType java.util.Map<java.lang.String , java.lang.Object>
     * @ParameterNames [username , password]
     * @Date 2020/12/9
     * @Time 18:16
     */
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
            map.put ( "msg","账号或密码错误" );
            map.put ( "state" , false );
        }
        return map;
    }


    /**
     * @MethodName: commentPageList
     * @Description 带条件分页查询
     * @Author yangbq
     * @MethodReturnType java.util.Map<java.lang.String , java.lang.Object>
     * @ParameterNames [currentPage , pageSize, searchEntity（查询条件）]
     * @Date 2020/12/9
     * @Time 18:15
     */
    @GetMapping ( "/comment/pageList" )
    public Map < String, Object > commentPageList ( @RequestParam ( value = "currentPage", defaultValue = "1" ) Integer currentPage ,
                                                    @RequestParam ( value = "pageSize", defaultValue = "10" ) Integer pageSize ,
                                                    @RequestParam ( value = "searchEntity", defaultValue = "" ) String searchEntity ) {
        System.out.println ( searchEntity );
        Map < String, Object > map = new HashMap <> ( );
        ArticleCommentExample example = new ArticleCommentExample ( );
        if (!StringUtils.isEmpty ( searchEntity ) || !"".equals ( searchEntity )) {
            example.createCriteria ( ).andContentLike ( "%" + searchEntity + "%" );
        }

        List < CommentManagerVo > commentManagerVos = new ArrayList <> ( );
        try {
            PageInfo < ArticleComment > articleCommentPageInfo = articleCommentService.commentPageList ( currentPage , pageSize , example );

            for (ArticleComment comment : articleCommentPageInfo.getList ( )) {

                CommentManagerVo commentManagerVo = new CommentManagerVo ( );
                commentManagerVo.setArticleTitle ( articleService.selectByPrimaryKey ( comment.getArticleId ( ) ).getArticleTitle ( ) );
                commentManagerVo.setArticleComment ( comment );
                commentManagerVo.setReplyFromUsername ( userService.selectByPrimaryKey ( comment.getFromUserId ( ) ).getUserName ( ) );
                if (comment.getPid ( ) != 0) {
                    example.clear ( );
                    example.createCriteria ( ).andCommentIdEqualTo ( comment.getPid ( ) );
                    commentManagerVo.setReplyToUsername ( userService.selectByPrimaryKey ( articleCommentService.selectByExample ( example ).get ( 0 ).getFromUserId ( ) ).getUserName ( ) );
                } else {
                    commentManagerVo.setReplyToUsername ( "文章" );
                }
                commentManagerVos.add ( commentManagerVo );
            }
            articleCommentPageInfo.setList ( null );
            map.put ( "commentTableData" , commentManagerVos );
            map.put ( "pageInfo" , articleCommentPageInfo );
            map.put ( "state" , true );
        } catch (Exception e) {
            e.printStackTrace ( );
            map.put ( "msg" , e.getMessage ( ) );
            map.put ( "state" , false );
        }
        return map;
    }

    /**
     * @MethodName: commentUpdate
     * @Description 修改评论，禁用评论同时也会将该评论的子评论一并禁用
     * @Author yangbq
     * @MethodReturnType java.util.Map<java.lang.String , java.lang.Object>
     * @ParameterNames [comment]
     * @Date 2020/12/9
     * @Time 17:59
     */
    @PutMapping ( "/comment/update" )
    public Map < String, Object > commentUpdate ( ArticleComment comment ) {
        Map < String, Object > map = new HashMap <> ( );
        System.out.println ( comment );
        try {
            articleCommentService.updateByPrimaryKeySelective ( comment );

            ArticleCommentExample example = new ArticleCommentExample ( );
            ArticleCommentExample.Criteria criteria = example.createCriteria ( );
            criteria.andPidEqualTo ( comment.getCommentId ( ) );
            List < ArticleComment > comments = articleCommentService.selectByExample ( example );
            for (ArticleComment articleComment : comments) {
                articleComment.setStatus ( comment.getStatus ( ) );
                articleCommentService.updateByPrimaryKeySelective ( articleComment );
            }
            map.put ( "state" , true );
        } catch (Exception e) {
            e.printStackTrace ( );
            map.put ( "state" , false );
        }
        return map;
    }
}
