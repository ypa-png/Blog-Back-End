package top.yangbq.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yangbq.mapper.ArticleCommentMapper;
import top.yangbq.mapper.ArticlesMapper;
import top.yangbq.pojo.ArticleComment;
import top.yangbq.pojo.ArticleCommentExample;

import java.util.List;

@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

    @Autowired
    private ArticleCommentMapper articleCommentMapper;
    @Autowired
    private ArticlesMapper articlesMapper;


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
        return articleCommentMapper.updateByExampleSelective ( record ,example );
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
        PageHelper.startPage ( currentPage,pageSize );
        List < ArticleComment > articleComments = articleCommentMapper.selectByExample ( example );
        PageInfo < ArticleComment > pageInfo = new PageInfo <> ( articleComments );
        return pageInfo;
    }


}
