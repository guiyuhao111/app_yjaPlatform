package cn.net.gxht.app.yjdPlatform.common.entity;

/**
 * Created by Administrator on 2017/9/11.
 */
public class Page {
    /**
     * 当前页默认为1
     */
    private Integer currentPage=1;
    /**
     * 每夜默认为10条
     */
    private Integer pageSize=10;
    /**
     * 总条目数
     */
    private Integer amount;

    /**
     * 开始条目数
     * @return
     */
    private Integer startIndex;
    /**
     * 结束条目数
     */
    private Integer endIndex;

    /**
     * 总页数
     * @return
     */
    private Integer allPage;

    public void setAllPage(Integer allPage) {
        this.allPage = allPage;
    }

    public Integer getStartIndex(){
        startIndex=(currentPage-1)*pageSize;
    return startIndex;
    }

//    public Integer getStartIndex() {
//        return startIndex;
//    }

//    public Integer getEndIndex() {
//        return endIndex;
//    }
    public Integer getEndIndex(){
        endIndex= currentPage*pageSize<amount?currentPage*pageSize-1:amount-1;
        return endIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAllPage() {
        return allPage;
    }

    public void setAllPage() {
        allPage=amount%pageSize>0?amount/pageSize+1:amount/pageSize;
    }

    @Override
    public String toString() {
        return "Page{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", amount=" + amount +
                ", startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", allPage=" + allPage +
                '}';
    }
}
