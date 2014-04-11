package com.thoughtworks.twist2;

import java.lang.reflect.Method;
import java.util.Set;

public class HooksRegistry {
    private static Set<Method> beforeSpecHooks;
    private static Set<Method> afterSpecHooks;

    public static Set<Method> getBeforeSpecHooks() {
        return beforeSpecHooks;
    }

    public static void setBeforeSpecHooks(Set<Method> beforeSpecHooks) {
        HooksRegistry.beforeSpecHooks = beforeSpecHooks;
    }

    public static Set<Method> getAfterSpecHooks() {
        return afterSpecHooks;
    }

    public static void setAfterSpecHooks(Set<Method> afterSpecHooks) {
        HooksRegistry.afterSpecHooks = afterSpecHooks;
    }
}
