package cn.dg.md5;

public interface DataHandle {
    /**
     * 设置数据处理进度，最大为100，最小为0
     * @param n
     */
    void setProgress(int n);

    void setException(String ex);
}
