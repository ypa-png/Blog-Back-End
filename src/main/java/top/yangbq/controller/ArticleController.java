package top.yangbq.controller;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.yangbq.pojo.Articles;
import top.yangbq.pojo.ArticlesExample;
import top.yangbq.service.ArticleService;
import top.yangbq.service.UserService;
import top.yangbq.utils.IdWork;
import top.yangbq.utils.JWTUtils;
import top.yangbq.vo.ArticlesVo;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

@CrossOrigin
/**
 *@ClassName: ArticleController
 *@Description 处理文章请求
 *@Author yangbq
 *@Date 2020/11/10
 *@Time 20:44
 */
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Value ( "${locationTemp}" )
    private String locationTemp;

    /**
     * @MethodName: articleList
     * @Description 查询文章列表
     * @Author yangbq
     * @MethodReturnType java.util.Map<java.lang.String , java.lang.Object>
     * @ParameterNames []
     * @Date 2020/11/16
     * @Time 22:12
     */
    @GetMapping ( "/article/list" )
    public Map < String, Object > articleList () {
        Map < String, Object > map = new HashMap <> ( );
        try {
            List < Articles > articles = articleService.selectByExample ( new ArticlesExample ( ) );
            List < ArticlesVo > articleVoAsList = ArticlesVo.getArticleVoAsList ( articles );
            for (ArticlesVo vo : articleVoAsList) {
                vo.setUsername ( userService.selectByPrimaryKey ( vo.getArticle ( ).getUserId ( ) ).getUserName ( ) );
            }
            map.put ( "articleList" , articleVoAsList );
            map.put ( "state" , true );
        } catch (Exception e) {
            e.printStackTrace ( );
            map.put ( "msg" , e.getMessage ( ) );
            map.put ( "state" , false );
        }
        return map;
    }

    /**
     * @MethodName: test
     * @Description 创建文章，附带封面cover上传
     * @Author yangbq
     * @MethodReturnType java.util.Map<java.lang.String , java.lang.Object>
     * @ParameterNames [articles , file, request]
     * @Date 2020/11/13
     * @Time 16:47
     */
    @PutMapping ( "/article/add" )
    public Map < String, Object > test ( Articles articles , @RequestParam ( "cover" ) MultipartFile file , HttpServletRequest request ) {
//        String rootPath = request.getSession ( ).getServletContext ( ).getRealPath ( "/" );
        String rootPath = locationTemp;
        String OriginalFilename = file.getOriginalFilename ( );
        String ext = OriginalFilename.substring ( OriginalFilename.lastIndexOf ( "." ) );
        String articleId = IdWork.getId ( );
        String newFileName = "cover_" + articleId + System.currentTimeMillis ( ) + ext;
        File coverFile = new File ( rootPath , File.separator + "article" + File.separator + "cover" + File.separator + newFileName );
//        System.out.println ( coverFile.getAbsolutePath ( ) );
        File coverDir = new File ( rootPath , File.separator + "article" + File.separator + "cover" );

        Map < String, Object > map = new HashMap <> ( );
        map.put ( "msg" , "文章创建成功" );
        map.put ( "state" , true );
        try {

            if (!coverDir.exists ( )) {
                coverDir.mkdirs ( );
            }

            try {
                file.transferTo ( coverFile );
            } catch (Exception e) {
                e.printStackTrace ( );
            }
            String userId = JWTUtils.verify ( request.getHeader ( "token" ) ).getClaims ( ).get ( "id" ).asString ( );
            articles.setUserId ( userId );
            articles.setArticleId ( articleId );
            articles.setArticleCover ( File.separator + "article" + File.separator + "cover" + File.separator + newFileName );
            articleService.insertSelective ( articles );
        } catch (Exception e) {
            e.printStackTrace ( );
            map.put ( "msg" , e.getMessage ( ) );
            map.put ( "state" , false );
        }
        return map;
    }


    /**
     * @MethodName: updateCover
     * @Description 更新封面图片方法
     * @Author yangbq
     * @MethodReturnType java.lang.String
     * @ParameterNames [file , request, articles]
     * @Date 2020/11/18
     * @Time 21:23
     */
    public String updateCover ( MultipartFile file , HttpServletRequest request , Articles articles ) throws Exception {
        System.out.println ( articles );
//        String rootPath = request.getSession ( ).getServletContext ( ).getRealPath ( "/" );
        String rootPath = locationTemp;
        String OriginalFilename = file.getOriginalFilename ( );
        String ext = OriginalFilename.substring ( OriginalFilename.lastIndexOf ( "." ) );
        String oldFilename = articles.getArticleCover ( ).substring ( articles.getArticleCover ( ).lastIndexOf ( File.separator ) + 1 );
        String newFileName = "cover_" + articles.getArticleId ( ) + System.currentTimeMillis ( ) + ext;
        File coverDir = new File ( rootPath , File.separator + "article" + File.separator + "cover" );
        File[] files = coverDir.listFiles ( );

        if (files.length > 0) {
            for (File f : files) {
//                System.out.println ( f.getName ( ) + ": :" + oldFilename + ": :" + newFileName );
                if (f.getName ( ).equals ( oldFilename )) {
                    f.delete ( );
                }
            }
        }
        file.transferTo ( new File ( coverDir , newFileName ) );
        return newFileName;
    }

    /**
     * @MethodName: updateArticle
     * @Description 文章修改
     * @Author yangbq
     * @MethodReturnType java.util.Map<java.lang.String , java.lang.Object>
     * @ParameterNames [articles , cover, request]
     * @Date 2020/11/18
     * @Time 21:23
     */
    @PutMapping ( "article/update" )
    public Map < String, Object > updateArticle ( Articles articles , MultipartFile cover , HttpServletRequest request ) {
        String userIdFromToken = JWTUtils.verify ( request.getHeader ( "token" ) ).getClaims ( ).get ( "id" ).asString ( );
        String userIdFromModel = articleService.selectByPrimaryKey ( articles.getArticleId ( ) ).getUserId ( );
        Map < String, Object > map = new HashMap <> ( );
        String newFileName = null;
        if (!userIdFromModel.equals ( userIdFromToken ) && !"661271314718192".equals ( userIdFromToken )) {
            map.put ( "msg" , "只能修改当次登录用户文章" );
            map.put ( "state" , false );
        } else {
            ArticlesExample example = new ArticlesExample ( );
            example.createCriteria ( ).andArticleIdEqualTo ( articles.getArticleId ( ) );
            try {
                newFileName = updateCover ( cover , request , articles );
                articles.setArticleCover ( File.separator + "article" + File.separator + "cover" + File.separator + newFileName );
            } catch (Exception e) {
                e.printStackTrace ( );
            } finally {
                try {
                    articleService.updateByExampleSelective ( articles , example );
                    map.put ( "msg" , "文章修改成功" );
                    map.put ( "state" , true );
                } catch (Exception e) {
                    e.printStackTrace ( );
                    map.put ( "msg" , e.getMessage ( ) );
                    map.put ( "state" , false );
                }
            }
        }
        return map;
    }

    @DeleteMapping ( "/article/delete" )
    public Map < String, Object > deleteArticle ( @RequestBody List < String > ids , HttpServletRequest request ) {
        Map < String, Object > map = new HashMap <> ( );
//        String rootPath=request.getSession ().getServletContext ().getRealPath ( "/" );
        String rootPath = locationTemp;
        File file = new File ( rootPath , File.separator + "article" + File.separator + "cover" + File.separator );

        try {
            for (String id : ids) {
                try {
                    for (File f : file.listFiles ( )) {
                        String s = articleService.selectByPrimaryKey ( id ).getArticleCover ( );
//                        System.out.println ( f.getName ( ) + ">>>" + s.substring ( s.lastIndexOf ( File.separator ) + 1 ) );
                        if (f.getName ( ).equals ( s.substring ( s.lastIndexOf ( File.separator ) + 1 ) )) {
                            f.delete ( );
                        }
                    }
                } catch (Exception e) {
                } finally {
                    articleService.deleteByPrimaryKey ( id );
                }
            }
            map.put ( "msg" , "删除了"+ids.size ()+"条数据" );
            map.put ( "state" , true );
        } catch (Exception e) {
            e.printStackTrace ( );
            map.put ( "msg" , e.getMessage ( ) );
            map.put ( "state" , false );
        }
        return map;
    }

    @GetMapping ( "/article/{articleId}" )
    public Map < String, Object > getArticleById ( @PathVariable String articleId ) {
        Map < String, Object > map = new HashMap <> ( );
        try {
            Articles articles = articleService.selectByPrimaryKey ( articleId );
            String author = userService.selectByPrimaryKey ( articles.getUserId ( ) ).getUserName ( );
            map.put ( "article" , articles );
            map.put ( "author" , author );
            map.put ( "state" , true );
        } catch (Exception e) {
            e.printStackTrace ( );
            map.put ( "msg" , e.getMessage ( ) );
            map.put ( "state" , false );
        }
        return map;
    }

    @PostMapping ( "article/pageList" )
    public Map pageList ( @RequestParam ( value = "currentPage", defaultValue = "1" ) Integer currentPage ,
                                             @RequestParam ( value = "pageSize", defaultValue = "4  " ) Integer pageSize
                                             ,@RequestBody Articles article) {
        Map map = null;
        try {
            map = articleService.listArticle ( currentPage , pageSize , article );
            map.put ( "state" , true );
        } catch (Exception e) {
            e.printStackTrace ( );
            map.put ( "msg",e.getMessage () );
            map.put ( "state" , false );
        }
        return map;
    }



}
