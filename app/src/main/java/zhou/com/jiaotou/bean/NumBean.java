package zhou.com.jiaotou.bean;

/**
 * Created by zhou
 * on 2018/12/4.
 */

public class NumBean {

    /**
     * HandleCount : 0
     * IsSuccess : false
     * ErrorMessage : 请求时间已过期!
     * ErrorCode : 000
     */

    private int HandleCount;
    private boolean IsSuccess;
    private String ErrorMessage;
    private String ErrorCode;

    public int getHandleCount() {
        return HandleCount;
    }

    public void setHandleCount(int HandleCount) {
        this.HandleCount = HandleCount;
    }

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    @Override
    public String toString() {
        return "NumBean{" +
                "HandleCount=" + HandleCount +
                ", IsSuccess=" + IsSuccess +
                ", ErrorMessage='" + ErrorMessage + '\'' +
                ", ErrorCode='" + ErrorCode + '\'' +
                '}';
    }
}
