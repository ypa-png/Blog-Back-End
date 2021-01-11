package top.yangbq.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.yangbq.mapper.ArticleCommentMapper;
import top.yangbq.mapper.UsersMapper;
import top.yangbq.pojo.ArticleCommentExample;
import top.yangbq.pojo.Users;
import top.yangbq.pojo.UsersExample;
import top.yangbq.utils.IdWork;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper userMapper;

    @Autowired
    private ArticleCommentMapper commentMapper;


    @Override
    public long countByExample ( UsersExample example ) {
        return 0;
    }

    @Override
    public int deleteByExample ( UsersExample example ) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey ( String userId ) {
        return 0;
    }

    @Override
    public int insert ( Users record ) {
        return 0;
    }

    @Override
    public int insertSelective ( Users record ) {
        record.setUserId ( IdWork.getId ( ) );
        int i = userMapper.insertSelective ( record );
        if (i > 0) {
            return i;
        }
        throw new RuntimeException ( "注册失败" );
    }

    @Override
    public List < Users > selectByExample ( UsersExample example ) {
        return userMapper.selectByExample ( example );
    }

    @Override
    public Users selectByPrimaryKey ( String userId ) {
        return userMapper.selectByPrimaryKey ( userId );
    }

    @Override
    public int updateByExampleSelective ( Users record , UsersExample example ) {
        return 0;
    }

    @Override
    public int updateByExample ( Users record , UsersExample example ) {
        return userMapper.updateByExample ( record , example );
    }

    @Override
    public int updateByPrimaryKeySelective ( Users record ) {
        return userMapper.updateByPrimaryKeySelective ( record );
    }

    @Override
    public int updateByPrimaryKey ( Users record ) {
        return userMapper.updateByPrimaryKey ( record );
    }


    public Users login ( Users user ) {
        Users users = userMapper.login ( user );
        if (users != null) {
            return users;
        }
        throw new RuntimeException ( "登陆失败" );
    }

    @Override
    public Users validateUsername ( String username ) {
        Users users = userMapper.validateUsername ( username );
        if (users != null) {
            throw new RuntimeException ( "用户名不可用" );
        }
        return users;
    }

    @Override
    public PageInfo pageList ( Integer currentPage , Integer pageSize , Users searchEntity ) {
        UsersExample example = new UsersExample ( );
        UsersExample.Criteria criteria = example.createCriteria ( );
        if(searchEntity!=null){
            if(!StringUtils.isEmpty ( searchEntity.getUserName () )){
                criteria.andUserNameLike ( "%"+searchEntity.getUserName ()+"%" );
            }
            if(!StringUtils.isEmpty ( searchEntity.getUserNickname () )){
                criteria.andUserNicknameLike ( "%"+searchEntity.getUserNickname ()+"%" );
            }
        }
        PageHelper.startPage ( currentPage,pageSize );
        List < Users > usersList = userMapper.selectByExample ( example );
        PageInfo < Users > pageInfo = new PageInfo <> ( usersList );
        return pageInfo;
    }

    @Override
    @Transactional
    public void deleteByIds ( String[] ids ) {
        if(ids.length==0){
            throw new RuntimeException ( "参数异常" );
        }
        UsersExample example = new UsersExample ( );
        example.createCriteria ().andUserIdIn ( Arrays.asList ( ids) );
        ArticleCommentExample commentExample = new ArticleCommentExample ( );
        //删除用户同时删除相关评论
        commentExample.createCriteria ().andFromUserIdIn ( Arrays.asList ( ids ) );
        commentMapper.deleteByExample ( commentExample );
        userMapper.deleteByExample ( example );
    }
}
