package top.yangbq.pojo;

import java.util.Date;

public class Articles {
    private String articleId;

    private String userId;

    private String articleTitle;

    private String articleContent;

    private Date articleDate;

    private String articleCover;

    private Integer articleGood;

    private Integer articleReply;

    private Integer articleScan;

    private String articleTag;

    private String articleCategoryId;

    private String articleGeneral;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public Date getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(Date articleDate) {
        this.articleDate = articleDate;
    }

    public String getArticleCover() {
        return articleCover;
    }

    public void setArticleCover(String articleCover) {
        this.articleCover = articleCover;
    }

    public Integer getArticleGood() {
        return articleGood;
    }

    public void setArticleGood(Integer articleGood) {
        this.articleGood = articleGood;
    }

    public Integer getArticleReply() {
        return articleReply;
    }

    public void setArticleReply(Integer articleReply) {
        this.articleReply = articleReply;
    }

    public Integer getArticleScan() {
        return articleScan;
    }

    public void setArticleScan(Integer articleScan) {
        this.articleScan = articleScan;
    }

    public String getArticleTag() {
        return articleTag;
    }

    public void setArticleTag(String articleTag) {
        this.articleTag = articleTag;
    }

    public String getArticleCategoryId() {
        return articleCategoryId;
    }

    public void setArticleCategoryId(String articleCategoryId) {
        this.articleCategoryId = articleCategoryId;
    }

    public String getArticleGeneral() {
        return articleGeneral;
    }

    public void setArticleGeneral(String articleGeneral) {
        this.articleGeneral = articleGeneral;
    }

    @Override
    public String toString () {
        return "Articles{" +
                "articleId='" + articleId + '\'' +
                ", userId='" + userId + '\'' +
                ", articleTitle='" + articleTitle + '\'' +
                ", articleContent='" + articleContent + '\'' +
                ", articleDate=" + articleDate +
                ", articleCover='" + articleCover + '\'' +
                ", articleGood=" + articleGood +
                ", articleReply=" + articleReply +
                ", articleScan=" + articleScan +
                ", articleTag='" + articleTag + '\'' +
                ", articleCategoryId='" + articleCategoryId + '\'' +
                ", articleGeneral='" + articleGeneral + '\'' +
                '}';
    }
}