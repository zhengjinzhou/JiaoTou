package zhou.com.jiaotou.bean;

/**
 * Created by zhou
 * on 2018/12/12.
 */

public class SNIDList {


    /**
     * SNID : 0006
     * SortName : 深圳万维
     * FullName : 广东万维博通（深圳）信息技术有限公司
     */

    private String SNID;
    private String SortName;
    private String FullName;

    public String getSNID() {
        return SNID;
    }

    public void setSNID(String SNID) {
        this.SNID = SNID;
    }

    public String getSortName() {
        return SortName;
    }

    public void setSortName(String SortName) {
        this.SortName = SortName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    @Override
    public String toString() {
        return "SNIDList{" +
                "SNID='" + SNID + '\'' +
                ", SortName='" + SortName + '\'' +
                ", FullName='" + FullName + '\'' +
                '}';
    }
}
