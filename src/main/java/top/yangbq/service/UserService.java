package top.yangbq.service;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import top.yangbq.pojo.Users;
import top.yangbq.pojo.UsersExample;

import java.util.List;

public interface UserService {
    long countByExample( UsersExample example);

    int deleteByExample(UsersExample example);

    int deleteByPrimaryKey(String userId);

    int insert( Users record);

    int insertSelective(Users record);

    List<Users> selectByExample(UsersExample example);

    Users selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") Users record, @Param("example") UsersExample example);

    int updateByExample(@Param("record") Users record, @Param("example") UsersExample example);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    Users login ( Users user );

    Users validateUsername ( String username );

    PageInfo pageList ( Integer currentPage , Integer pageSize , Users searchEntity );

    void deleteByIds ( String[] ids );
}
