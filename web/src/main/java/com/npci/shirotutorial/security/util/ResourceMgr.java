package com.npci.shirotutorial.security.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceMgr {
    private static final String LABEL_BUNDLE = "com/npci/shirotutorial/security/web/i18n/labels";
    private static final String MESSAGE_BUNDLE = "com/npci/shirotutorial/security/web/i18n/messages";
    private static final String VALIDATION_BUNDLE = "com/npci/shirotutorial/security/web/i18n/validations";

    public static ResourceBundle getResourceBundle(String bundleName) {
        return ResourceBundle.getBundle(bundleName, new Locale("fa", "IR"));
    }

    public static ResourceBundle getMessageBundle() {
        return getResourceBundle(MESSAGE_BUNDLE);
    }

    public static ResourceBundle getLabelBundle() {
        return getResourceBundle(LABEL_BUNDLE);
    }

    public static ResourceBundle getValidationBundle() {
        return getResourceBundle(VALIDATION_BUNDLE);
    }
}
