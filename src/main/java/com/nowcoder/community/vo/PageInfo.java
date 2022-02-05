package com.nowcoder.community.vo;

/**
 * 封装分页信息
 * @author Alex
 * @version 1.0
 * @date 2022/2/1 16:32
 */
public class PageInfo {

    /**
     * 当前页码
     */
    private int current=1;
    /**
     * 每页帖子条数
     */
    private int limit=10;

    /**
     * 总记录数(用于计算总页数)
     */
    private int rows;
    /**
     * 查询路径(用于服用分页链接)
     */
    private String path;
    private int total;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if(current>=1){
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit>=1&&limit<=100){
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if(rows>0){
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取当前页起始行
     * @return
     */
    public int getOffset(){
        return (current - 1) * limit;
    }

    /**
     * 获取总页数
     * @return
     */
    public int getTotal(){
        if(rows%limit==0){
            return rows/limit;
        }else {
            return rows/limit + 1;
        }
    }

    /**
     * 获取起始页码
     * @return
     */
    public int getFrom(){
        int from = current - 2;
        return from<1?1:from;
    }

    /**
     * 获取结束页码
     * @return
     */
    public int getTo(){
        int to = current+2;
        int total = getTotal();
        return to>total?total:to;
    }

}
