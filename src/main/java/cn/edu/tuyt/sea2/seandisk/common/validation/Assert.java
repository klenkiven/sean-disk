package cn.edu.tuyt.sea2.seandisk.common.validation;

import org.apache.commons.lang.StringUtils;
import cn.edu.tuyt.sea2.seandisk.common.exception.GeneralException;

/**
 * @author ：klenkiven
 * @date ：2021/7/13 17:09
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new GeneralException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new GeneralException(message);
        }
    }

}
