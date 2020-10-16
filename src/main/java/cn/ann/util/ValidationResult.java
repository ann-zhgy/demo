package cn.ann.util;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;

/**
 * Description：校验结果对象
 * <p>
 * Date: 2020-10-16 12:55
 *
 * @author 88475
 * @version v1.0
 */
public class ValidationResult {
    /**
     * 是否有异常
     */
    private boolean hasErrors;

    /**
     * 异常消息记录
     */
    private Map<String, String> errorMsg;

    /**
     * 获取异常消息
     *
     * @return {@link String} 异常消息
     */
    public String getMessage() {
        if (errorMsg == null || errorMsg.isEmpty()) {
            return "";
        }
        StringBuilder message = new StringBuilder();
        errorMsg.forEach((key, value) ->
                message.append(MessageFormat.format("{0}:{1} \r\n", key, value)));
        return message.toString();
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsg() {
        return errorMsg;
    }

    public String getErrorMsgStr() {
        return getErrorMsgStr("");
    }

    public String getErrorMsgStr(String suffix) {
        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = errorMsg.entrySet().iterator();
        while (iterator.hasNext()){
            builder.append(iterator.next().getValue());
            if (iterator.hasNext()) {
                builder.append(suffix);
            }
        }
        return builder.toString();
    }

    public void setErrorMsg(Map<String, String> errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ValidationResult{" + "hasErrors=" + hasErrors + ", errorMsg=" + errorMsg + '}';
    }

}
