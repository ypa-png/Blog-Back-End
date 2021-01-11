package top.yangbq.pojo;

import java.util.Date;

public class Users {
    private String userId;

    private String userIp;

    private String userName;

    private String userNickname;

    private String userPwd;

    private String userEmail;

    private String userHeadpic;

    private Date userRegistTime;

    private Date userBir;

    private Integer userAge;

    private String userPhone;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserHeadpic() {
        return userHeadpic;
    }

    public void setUserHeadpic(String userHeadpic) {
        this.userHeadpic = userHeadpic;
    }

    public Date getUserRegistTime() {
        return userRegistTime;
    }

    public void setUserRegistTime(Date userRegistTime) {
        this.userRegistTime = userRegistTime;
    }

    public Date getUserBir() {
        return userBir;
    }

    public void setUserBir(Date userBir) {
        this.userBir = userBir;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public String toString () {
        return "Users{" +
                "userId='" + userId + '\'' +
                ", userIp='" + userIp + '\'' +
                ", userName='" + userName + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userHeadpic='" + userHeadpic + '\'' +
                ", userRegistTime=" + userRegistTime +
                ", userBir=" + userBir +
                ", userAge=" + userAge +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }
}