package cn.edu.tuyt.sea2.seandisk.common.utils;

/**
 * 常量工具类
 * @author ：klenkiven
 * @date ：2021/7/12 8:57
 */
public final class Constant {

    /** 超级管理员ID */
    public static final int ADMIN = 1;

    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private final int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum DeviceStatus {
        /**
         * 占用
         */
        UNAVAILABLE(0),

        /**
         * 可用
         */
        AVAILABLE(1),

        /**
         * 未分配
         */
        UNALLOCATED(2),

        /**
         * 已分配
         */
        ALLOCATED(3);

        private final int value;

        DeviceStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
