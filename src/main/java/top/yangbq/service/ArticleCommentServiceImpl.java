package top.yangbq.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.yangbq.mapper.ArticleCommentMapper;
import top.yangbq.mapper.ArticlesMapper;
import top.yangbq.pojo.ArticleComment;
import top.yangbq.pojo.ArticleCommentExample;
import top.yangbq.vo.req.CommentVo;

import java.util.List;

@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

    @Autowired
    private ArticleCommentMapper articleCommentMapper;

    @Autowired
    private ArticlesMapper articlesMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public long countByExample ( ArticleCommentExample example ) {
        return articleCommentMapper.countByExample ( example );
    }

    @Override
    public int deleteByExample ( ArticleCommentExample example ) {
        return articleCommentMapper.deleteByExample ( example );
    }

    @Override
    public int deleteByPrimaryKey ( Integer commentId ) {
        return 0;
    }

    @Override
    public int insert ( ArticleComment record ) {
        return 0;
    }

    @Override
    public int insertSelective ( ArticleComment record ) {
        return articleCommentMapper.insertSelective ( record );
    }

    @Override
    public List < ArticleComment > selectByExample ( ArticleCommentExample example ) {
        return articleCommentMapper.selectByExample ( example );
    }

    @Override
    public ArticleComment selectByPrimaryKey ( Integer commentId ) {
        return articleCommentMapper.selectByPrimaryKey ( commentId );
    }

    @Override
    public int updateByExampleSelective ( ArticleComment record , ArticleCommentExample example ) {
        return articleCommentMapper.updateByExampleSelective ( record , example );
    }

    @Override
    public int updateByExample ( ArticleComment record , ArticleCommentExample example ) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective ( ArticleComment record ) {
        return articleCommentMapper.updateByPrimaryKeySelective ( record );
    }

    @Override
    public int updateByPrimaryKey ( ArticleComment record ) {
        return 0;
    }

    @Override
    public PageInfo < ArticleComment > commentPageList ( Integer currentPage , Integer pageSize , ArticleCommentExample example ) {
        PageHelper.startPage ( currentPage , pageSize );
        List < ArticleComment > articleComments = articleCommentMapper.selectByExample ( example );
        PageInfo < ArticleComment > pageInfo = new PageInfo <> ( articleComments );
        return pageInfo;
    }

    /**
     * @MethodName: addComment
     * @Description 用户发布评论
     * @Author yangbq
     * @MethodReturnType void
     * @ParameterNames [commentVo]
     * @Date 2021/1/13
     * @Time 14:38
     */
    @Override
    public void addComment ( CommentVo commentVo ) {
        if (commentVo == null) {
            throw new RuntimeException ( "参数异常" );
        }

        ArticleComment comment = new ArticleComment ( );

        comment.setArticleId ( commentVo.getArticleId ( ) );
        comment.setPid ( commentVo.getPid ( ) );
        String commentContent = commentVo.getCommentContent ( );
        //评论脏话过滤
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
        comment.setFromUserId ( commentVo.getUserId ( ) );
        comment.setStatus ( true );

        articleCommentMapper.insertSelective ( comment );

        /*-----------------------------------------统计评论数量-----------------------------------------*/

        BoundHashOperations comentCountHash = stringRedisTemplate.boundHashOps ( "comentCountHash" );
        ArticleCommentExample example = new ArticleCommentExample ( );
        example.createCriteria ().andArticleIdEqualTo ( commentVo.getArticleId () );
        Long commentCount = articleCommentMapper.countByExample ( example );

        comentCountHash.put ( commentVo.getArticleId (),String.valueOf ( commentCount ) );

    }


}
