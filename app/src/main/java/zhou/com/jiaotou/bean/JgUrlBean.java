package zhou.com.jiaotou.bean;

/**
 * Created by zhou
 * on 2019/3/4.
 */

public class JgUrlBean {

    /**
     * url : http://www.baidu.com
     */

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "JgUrlBean{" +
                "url='" + url + '\'' +
                '}';
    }
}
