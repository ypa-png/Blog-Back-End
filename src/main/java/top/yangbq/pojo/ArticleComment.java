package top.yangbq.pojo;

import top.yangbq.vo.CommentUserInfo;

import java.util.Date;

public class ArticleComment {
    private Integer commentId;

    private Integer pid;

    private String content;

    private String articleId;

    private String fromUserId;

    private Boolean status;

    private Date publishTime;

    private CommentUserInfo commentUserInfo;

    public CommentUserInfo getCommentUserInfo () {
        return commentUserInfo;
    }

    public void setCommentUserInfo ( CommentUserInfo commentUserInfo ) {
        this.commentUserInfo = commentUserInfo;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public String toString () {
        return "ArticleComment{" +
                "commentId=" + commentId +
                ", pid=" + pid +
                ", content='" + content + '\'' +
                ", articleId='" + articleId + '\'' +
                ", fromUserId='" + fromUserId + '\'' +
                ", status=" + status +
                ", publishTime=" + publishTime +
                ", commentUserInfo=" + commentUserInfo +
                '}';
    }
}