package com.thoughtworks.twist2;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class HooksRegistry {
    private static Set<Method> beforeSpecHooks = new HashSet<Method>();
    private static Set<Method> afterSpecHooks = new HashSet<Method>();
    private static Set<Method> beforeScenarioHooks = new HashSet<Method>();
    private static Set<Method> afterScenarioHooks = new HashSet<Method>();

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

    public static Set<Method> getBeforeScenarioHooks() {
        return beforeScenarioHooks;
    }

    public static void setBeforeScenarioHooks(Set<Method> beforeScenarioHooks) {
        HooksRegistry.beforeScenarioHooks = beforeScenarioHooks;
    }

    public static Set<Method> getAfterScenarioHooks() {
        return afterScenarioHooks;
    }

    public static void setAfterScenarioHooks(Set<Method> afterScenarioHooks) {
        HooksRegistry.afterScenarioHooks = afterScenarioHooks;
    }
}
