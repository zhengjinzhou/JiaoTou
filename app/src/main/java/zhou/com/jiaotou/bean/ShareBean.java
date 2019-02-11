package zhou.com.jiaotou.bean;

/**
 * Created by zhou
 * on 2019/2/11.
 */

public class ShareBean {

    /**
     * type : share
     * path : http://ys.wanve.com:8080/DMS_Phone/QWMan/Download/eb7f6a1d08f540f39028f812f6ec1bd020190124172954974/题目.xls
     * name : 题目.xls
     */

    private String type;
    private String path;
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ShareBean{" +
                "type='" + type + '\'' +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
