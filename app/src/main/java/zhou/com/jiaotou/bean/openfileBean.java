package zhou.com.jiaotou.bean;

/**
 * Created by zhou
 * on 2019/2/11.
 */

public class openfileBean {

    /**
     * type : openfile
     * path : http://oa.dgjtjt.com.cn:8081/DMS_Phone//QWMan/Download/567D4F424FC94874845E639C5309562F20190211171109027/附件：召开组织生活会及开展民主评议党员工作进展情况汇报表.xls
     */

    private String type;
    private String path;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "openfileBean{" +
                "type='" + type + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
