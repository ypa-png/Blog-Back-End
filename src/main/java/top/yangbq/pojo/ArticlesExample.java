package top.yangbq.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticlesExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ArticlesExample () {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andArticleIdIsNull() {
            addCriterion("article_id is null");
            return (Criteria) this;
        }

        public Criteria andArticleIdIsNotNull() {
            addCriterion("article_id is not null");
            return (Criteria) this;
        }

        public Criteria andArticleIdEqualTo(String value) {
            addCriterion("article_id =", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdNotEqualTo(String value) {
            addCriterion("article_id <>", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdGreaterThan(String value) {
            addCriterion("article_id >", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdGreaterThanOrEqualTo(String value) {
            addCriterion("article_id >=", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdLessThan(String value) {
            addCriterion("article_id <", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdLessThanOrEqualTo(String value) {
            addCriterion("article_id <=", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdLike(String value) {
            addCriterion("article_id like", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdNotLike(String value) {
            addCriterion("article_id not like", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdIn(List<String> values) {
            addCriterion("article_id in", values, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdNotIn(List<String> values) {
            addCriterion("article_id not in", values, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdBetween(String value1, String value2) {
            addCriterion("article_id between", value1, value2, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdNotBetween(String value1, String value2) {
            addCriterion("article_id not between", value1, value2, "articleId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andArticleTitleIsNull() {
            addCriterion("article_title is null");
            return (Criteria) this;
        }

        public Criteria andArticleTitleIsNotNull() {
            addCriterion("article_title is not null");
            return (Criteria) this;
        }

        public Criteria andArticleTitleEqualTo(String value) {
            addCriterion("article_title =", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleNotEqualTo(String value) {
            addCriterion("article_title <>", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleGreaterThan(String value) {
            addCriterion("article_title >", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleGreaterThanOrEqualTo(String value) {
            addCriterion("article_title >=", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleLessThan(String value) {
            addCriterion("article_title <", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleLessThanOrEqualTo(String value) {
            addCriterion("article_title <=", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleLike(String value) {
            addCriterion("article_title like", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleNotLike(String value) {
            addCriterion("article_title not like", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleIn(List<String> values) {
            addCriterion("article_title in", values, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleNotIn(List<String> values) {
            addCriterion("article_title not in", values, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleBetween(String value1, String value2) {
            addCriterion("article_title between", value1, value2, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleNotBetween(String value1, String value2) {
            addCriterion("article_title not between", value1, value2, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleContentIsNull() {
            addCriterion("article_content is null");
            return (Criteria) this;
        }

        public Criteria andArticleContentIsNotNull() {
            addCriterion("article_content is not null");
            return (Criteria) this;
        }

        public Criteria andArticleContentEqualTo(String value) {
            addCriterion("article_content =", value, "articleContent");
            return (Criteria) this;
        }

        public Criteria andArticleContentNotEqualTo(String value) {
            addCriterion("article_content <>", value, "articleContent");
            return (Criteria) this;
        }

        public Criteria andArticleContentGreaterThan(String value) {
            addCriterion("article_content >", value, "articleContent");
            return (Criteria) this;
        }

        public Criteria andArticleContentGreaterThanOrEqualTo(String value) {
            addCriterion("article_content >=", value, "articleContent");
            return (Criteria) this;
        }

        public Criteria andArticleContentLessThan(String value) {
            addCriterion("article_content <", value, "articleContent");
            return (Criteria) this;
        }

        public Criteria andArticleContentLessThanOrEqualTo(String value) {
            addCriterion("article_content <=", value, "articleContent");
            return (Criteria) this;
        }

        public Criteria andArticleContentLike(String value) {
            addCriterion("article_content like", value, "articleContent");
            return (Criteria) this;
        }

        public Criteria andArticleContentNotLike(String value) {
            addCriterion("article_content not like", value, "articleContent");
            return (Criteria) this;
        }

        public Criteria andArticleContentIn(List<String> values) {
            addCriterion("article_content in", values, "articleContent");
            return (Criteria) this;
        }

        public Criteria andArticleContentNotIn(List<String> values) {
            addCriterion("article_content not in", values, "articleContent");
            return (Criteria) this;
        }

        public Criteria andArticleContentBetween(String value1, String value2) {
            addCriterion("article_content between", value1, value2, "articleContent");
            return (Criteria) this;
        }

        public Criteria andArticleContentNotBetween(String value1, String value2) {
            addCriterion("article_content not between", value1, value2, "articleContent");
            return (Criteria) this;
        }

        public Criteria andArticleDateIsNull() {
            addCriterion("article_date is null");
            return (Criteria) this;
        }

        public Criteria andArticleDateIsNotNull() {
            addCriterion("article_date is not null");
            return (Criteria) this;
        }

        public Criteria andArticleDateEqualTo(Date value) {
            addCriterion("article_date =", value, "articleDate");
            return (Criteria) this;
        }

        public Criteria andArticleDateNotEqualTo(Date value) {
            addCriterion("article_date <>", value, "articleDate");
            return (Criteria) this;
        }

        public Criteria andArticleDateGreaterThan(Date value) {
            addCriterion("article_date >", value, "articleDate");
            return (Criteria) this;
        }

        public Criteria andArticleDateGreaterThanOrEqualTo(Date value) {
            addCriterion("article_date >=", value, "articleDate");
            return (Criteria) this;
        }

        public Criteria andArticleDateLessThan(Date value) {
            addCriterion("article_date <", value, "articleDate");
            return (Criteria) this;
        }

        public Criteria andArticleDateLessThanOrEqualTo(Date value) {
            addCriterion("article_date <=", value, "articleDate");
            return (Criteria) this;
        }

        public Criteria andArticleDateIn(List<Date> values) {
            addCriterion("article_date in", values, "articleDate");
            return (Criteria) this;
        }

        public Criteria andArticleDateNotIn(List<Date> values) {
            addCriterion("article_date not in", values, "articleDate");
            return (Criteria) this;
        }

        public Criteria andArticleDateBetween(Date value1, Date value2) {
            addCriterion("article_date between", value1, value2, "articleDate");
            return (Criteria) this;
        }

        public Criteria andArticleDateNotBetween(Date value1, Date value2) {
            addCriterion("article_date not between", value1, value2, "articleDate");
            return (Criteria) this;
        }

        public Criteria andArticleCoverIsNull() {
            addCriterion("article_cover is null");
            return (Criteria) this;
        }

        public Criteria andArticleCoverIsNotNull() {
            addCriterion("article_cover is not null");
            return (Criteria) this;
        }

        public Criteria andArticleCoverEqualTo(String value) {
            addCriterion("article_cover =", value, "articleCover");
            return (Criteria) this;
        }

        public Criteria andArticleCoverNotEqualTo(String value) {
            addCriterion("article_cover <>", value, "articleCover");
            return (Criteria) this;
        }

        public Criteria andArticleCoverGreaterThan(String value) {
            addCriterion("article_cover >", value, "articleCover");
            return (Criteria) this;
        }

        public Criteria andArticleCoverGreaterThanOrEqualTo(String value) {
            addCriterion("article_cover >=", value, "articleCover");
            return (Criteria) this;
        }

        public Criteria andArticleCoverLessThan(String value) {
            addCriterion("article_cover <", value, "articleCover");
            return (Criteria) this;
        }

        public Criteria andArticleCoverLessThanOrEqualTo(String value) {
            addCriterion("article_cover <=", value, "articleCover");
            return (Criteria) this;
        }

        public Criteria andArticleCoverLike(String value) {
            addCriterion("article_cover like", value, "articleCover");
            return (Criteria) this;
        }

        public Criteria andArticleCoverNotLike(String value) {
            addCriterion("article_cover not like", value, "articleCover");
            return (Criteria) this;
        }

        public Criteria andArticleCoverIn(List<String> values) {
            addCriterion("article_cover in", values, "articleCover");
            return (Criteria) this;
        }

        public Criteria andArticleCoverNotIn(List<String> values) {
            addCriterion("article_cover not in", values, "articleCover");
            return (Criteria) this;
        }

        public Criteria andArticleCoverBetween(String value1, String value2) {
            addCriterion("article_cover between", value1, value2, "articleCover");
            return (Criteria) this;
        }

        public Criteria andArticleCoverNotBetween(String value1, String value2) {
            addCriterion("article_cover not between", value1, value2, "articleCover");
            return (Criteria) this;
        }

        public Criteria andArticleGoodIsNull() {
            addCriterion("article_good is null");
            return (Criteria) this;
        }

        public Criteria andArticleGoodIsNotNull() {
            addCriterion("article_good is not null");
            return (Criteria) this;
        }

        public Criteria andArticleGoodEqualTo(Integer value) {
            addCriterion("article_good =", value, "articleGood");
            return (Criteria) this;
        }

        public Criteria andArticleGoodNotEqualTo(Integer value) {
            addCriterion("article_good <>", value, "articleGood");
            return (Criteria) this;
        }

        public Criteria andArticleGoodGreaterThan(Integer value) {
            addCriterion("article_good >", value, "articleGood");
            return (Criteria) this;
        }

        public Criteria andArticleGoodGreaterThanOrEqualTo(Integer value) {
            addCriterion("article_good >=", value, "articleGood");
            return (Criteria) this;
        }

        public Criteria andArticleGoodLessThan(Integer value) {
            addCriterion("article_good <", value, "articleGood");
            return (Criteria) this;
        }

        public Criteria andArticleGoodLessThanOrEqualTo(Integer value) {
            addCriterion("article_good <=", value, "articleGood");
            return (Criteria) this;
        }

        public Criteria andArticleGoodIn(List<Integer> values) {
            addCriterion("article_good in", values, "articleGood");
            return (Criteria) this;
        }

        public Criteria andArticleGoodNotIn(List<Integer> values) {
            addCriterion("article_good not in", values, "articleGood");
            return (Criteria) this;
        }

        public Criteria andArticleGoodBetween(Integer value1, Integer value2) {
            addCriterion("article_good between", value1, value2, "articleGood");
            return (Criteria) this;
        }

        public Criteria andArticleGoodNotBetween(Integer value1, Integer value2) {
            addCriterion("article_good not between", value1, value2, "articleGood");
            return (Criteria) this;
        }

        public Criteria andArticleReplyIsNull() {
            addCriterion("article_reply is null");
            return (Criteria) this;
        }

        public Criteria andArticleReplyIsNotNull() {
            addCriterion("article_reply is not null");
            return (Criteria) this;
        }

        public Criteria andArticleReplyEqualTo(Integer value) {
            addCriterion("article_reply =", value, "articleReply");
            return (Criteria) this;
        }

        public Criteria andArticleReplyNotEqualTo(Integer value) {
            addCriterion("article_reply <>", value, "articleReply");
            return (Criteria) this;
        }

        public Criteria andArticleReplyGreaterThan(Integer value) {
            addCriterion("article_reply >", value, "articleReply");
            return (Criteria) this;
        }

        public Criteria andArticleReplyGreaterThanOrEqualTo(Integer value) {
            addCriterion("article_reply >=", value, "articleReply");
            return (Criteria) this;
        }

        public Criteria andArticleReplyLessThan(Integer value) {
            addCriterion("article_reply <", value, "articleReply");
            return (Criteria) this;
        }

        public Criteria andArticleReplyLessThanOrEqualTo(Integer value) {
            addCriterion("article_reply <=", value, "articleReply");
            return (Criteria) this;
        }

        public Criteria andArticleReplyIn(List<Integer> values) {
            addCriterion("article_reply in", values, "articleReply");
            return (Criteria) this;
        }

        public Criteria andArticleReplyNotIn(List<Integer> values) {
            addCriterion("article_reply not in", values, "articleReply");
            return (Criteria) this;
        }

        public Criteria andArticleReplyBetween(Integer value1, Integer value2) {
            addCriterion("article_reply between", value1, value2, "articleReply");
            return (Criteria) this;
        }

        public Criteria andArticleReplyNotBetween(Integer value1, Integer value2) {
            addCriterion("article_reply not between", value1, value2, "articleReply");
            return (Criteria) this;
        }

        public Criteria andArticleScanIsNull() {
            addCriterion("article_scan is null");
            return (Criteria) this;
        }

        public Criteria andArticleScanIsNotNull() {
            addCriterion("article_scan is not null");
            return (Criteria) this;
        }

        public Criteria andArticleScanEqualTo(Integer value) {
            addCriterion("article_scan =", value, "articleScan");
            return (Criteria) this;
        }

        public Criteria andArticleScanNotEqualTo(Integer value) {
            addCriterion("article_scan <>", value, "articleScan");
            return (Criteria) this;
        }

        public Criteria andArticleScanGreaterThan(Integer value) {
            addCriterion("article_scan >", value, "articleScan");
            return (Criteria) this;
        }

        public Criteria andArticleScanGreaterThanOrEqualTo(Integer value) {
            addCriterion("article_scan >=", value, "articleScan");
            return (Criteria) this;
        }

        public Criteria andArticleScanLessThan(Integer value) {
            addCriterion("article_scan <", value, "articleScan");
            return (Criteria) this;
        }

        public Criteria andArticleScanLessThanOrEqualTo(Integer value) {
            addCriterion("article_scan <=", value, "articleScan");
            return (Criteria) this;
        }

        public Criteria andArticleScanIn(List<Integer> values) {
            addCriterion("article_scan in", values, "articleScan");
            return (Criteria) this;
        }

        public Criteria andArticleScanNotIn(List<Integer> values) {
            addCriterion("article_scan not in", values, "articleScan");
            return (Criteria) this;
        }

        public Criteria andArticleScanBetween(Integer value1, Integer value2) {
            addCriterion("article_scan between", value1, value2, "articleScan");
            return (Criteria) this;
        }

        public Criteria andArticleScanNotBetween(Integer value1, Integer value2) {
            addCriterion("article_scan not between", value1, value2, "articleScan");
            return (Criteria) this;
        }

        public Criteria andArticleTagIsNull() {
            addCriterion("article_tag is null");
            return (Criteria) this;
        }

        public Criteria andArticleTagIsNotNull() {
            addCriterion("article_tag is not null");
            return (Criteria) this;
        }

        public Criteria andArticleTagEqualTo(String value) {
            addCriterion("article_tag =", value, "articleTag");
            return (Criteria) this;
        }

        public Criteria andArticleTagNotEqualTo(String value) {
            addCriterion("article_tag <>", value, "articleTag");
            return (Criteria) this;
        }

        public Criteria andArticleTagGreaterThan(String value) {
            addCriterion("article_tag >", value, "articleTag");
            return (Criteria) this;
        }

        public Criteria andArticleTagGreaterThanOrEqualTo(String value) {
            addCriterion("article_tag >=", value, "articleTag");
            return (Criteria) this;
        }

        public Criteria andArticleTagLessThan(String value) {
            addCriterion("article_tag <", value, "articleTag");
            return (Criteria) this;
        }

        public Criteria andArticleTagLessThanOrEqualTo(String value) {
            addCriterion("article_tag <=", value, "articleTag");
            return (Criteria) this;
        }

        public Criteria andArticleTagLike(String value) {
            addCriterion("article_tag like", value, "articleTag");
            return (Criteria) this;
        }

        public Criteria andArticleTagNotLike(String value) {
            addCriterion("article_tag not like", value, "articleTag");
            return (Criteria) this;
        }

        public Criteria andArticleTagIn(List<String> values) {
            addCriterion("article_tag in", values, "articleTag");
            return (Criteria) this;
        }

        public Criteria andArticleTagNotIn(List<String> values) {
            addCriterion("article_tag not in", values, "articleTag");
            return (Criteria) this;
        }

        public Criteria andArticleTagBetween(String value1, String value2) {
            addCriterion("article_tag between", value1, value2, "articleTag");
            return (Criteria) this;
        }

        public Criteria andArticleTagNotBetween(String value1, String value2) {
            addCriterion("article_tag not between", value1, value2, "articleTag");
            return (Criteria) this;
        }

        public Criteria andArticleCategoryIdIsNull() {
            addCriterion("article_category_id is null");
            return (Criteria) this;
        }

        public Criteria andArticleCategoryIdIsNotNull() {
            addCriterion("article_category_id is not null");
            return (Criteria) this;
        }

        public Criteria andArticleCategoryIdEqualTo(String value) {
            addCriterion("article_category_id =", value, "articleCategoryId");
            return (Criteria) this;
        }

        public Criteria andArticleCategoryIdNotEqualTo(String value) {
            addCriterion("article_category_id <>", value, "articleCategoryId");
            return (Criteria) this;
        }

        public Criteria andArticleCategoryIdGreaterThan(String value) {
            addCriterion("article_category_id >", value, "articleCategoryId");
            return (Criteria) this;
        }

        public Criteria andArticleCategoryIdGreaterThanOrEqualTo(String value) {
            addCriterion("article_category_id >=", value, "articleCategoryId");
            return (Criteria) this;
        }

        public Criteria andArticleCategoryIdLessThan(String value) {
            addCriterion("article_category_id <", value, "articleCategoryId");
            return (Criteria) this;
        }

        public Criteria andArticleCategoryIdLessThanOrEqualTo(String value) {
            addCriterion("article_category_id <=", value, "articleCategoryId");
            return (Criteria) this;
        }

        public Criteria andArticleCategoryIdLike(String value) {
            addCriterion("article_category_id like", value, "articleCategoryId");
            return (Criteria) this;
        }

        public Criteria andArticleCategoryIdNotLike(String value) {
            addCriterion("article_category_id not like", value, "articleCategoryId");
            return (Criteria) this;
        }

        public Criteria andArticleCategoryIdIn(List<String> values) {
            addCriterion("article_category_id in", values, "articleCategoryId");
            return (Criteria) this;
        }

        public Criteria andArticleCategoryIdNotIn(List<String> values) {
            addCriterion("article_category_id not in", values, "articleCategoryId");
            return (Criteria) this;
        }

        public Criteria andArticleCategoryIdBetween(String value1, String value2) {
            addCriterion("article_category_id between", value1, value2, "articleCategoryId");
            return (Criteria) this;
        }

        public Criteria andArticleCategoryIdNotBetween(String value1, String value2) {
            addCriterion("article_category_id not between", value1, value2, "articleCategoryId");
            return (Criteria) this;
        }

        public Criteria andArticleGeneralIsNull() {
            addCriterion("article_general is null");
            return (Criteria) this;
        }

        public Criteria andArticleGeneralIsNotNull() {
            addCriterion("article_general is not null");
            return (Criteria) this;
        }

        public Criteria andArticleGeneralEqualTo(String value) {
            addCriterion("article_general =", value, "articleGeneral");
            return (Criteria) this;
        }

        public Criteria andArticleGeneralNotEqualTo(String value) {
            addCriterion("article_general <>", value, "articleGeneral");
            return (Criteria) this;
        }

        public Criteria andArticleGeneralGreaterThan(String value) {
            addCriterion("article_general >", value, "articleGeneral");
            return (Criteria) this;
        }

        public Criteria andArticleGeneralGreaterThanOrEqualTo(String value) {
            addCriterion("article_general >=", value, "articleGeneral");
            return (Criteria) this;
        }

        public Criteria andArticleGeneralLessThan(String value) {
            addCriterion("article_general <", value, "articleGeneral");
            return (Criteria) this;
        }

        public Criteria andArticleGeneralLessThanOrEqualTo(String value) {
            addCriterion("article_general <=", value, "articleGeneral");
            return (Criteria) this;
        }

        public Criteria andArticleGeneralLike(String value) {
            addCriterion("article_general like", value, "articleGeneral");
            return (Criteria) this;
        }

        public Criteria andArticleGeneralNotLike(String value) {
            addCriterion("article_general not like", value, "articleGeneral");
            return (Criteria) this;
        }

        public Criteria andArticleGeneralIn(List<String> values) {
            addCriterion("article_general in", values, "articleGeneral");
            return (Criteria) this;
        }

        public Criteria andArticleGeneralNotIn(List<String> values) {
            addCriterion("article_general not in", values, "articleGeneral");
            return (Criteria) this;
        }

        public Criteria andArticleGeneralBetween(String value1, String value2) {
            addCriterion("article_general between", value1, value2, "articleGeneral");
            return (Criteria) this;
        }

        public Criteria andArticleGeneralNotBetween(String value1, String value2) {
            addCriterion("article_general not between", value1, value2, "articleGeneral");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}