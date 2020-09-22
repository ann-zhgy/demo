package cn.ann.valid.group;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * Description：组校验顺序
 * <p>
 * Date: 2020-9-22 22:50
 *
 * @author 88475
 * @version v1.0
 */
@GroupSequence({Update.class, Default.class})
public interface GroupOrder {
}
