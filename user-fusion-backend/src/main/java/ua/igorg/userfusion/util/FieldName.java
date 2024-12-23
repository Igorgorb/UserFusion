package ua.igorg.userfusion.util;

import lombok.Getter;

/**
 * Created by igorg on 19.12.2024
 */
@Getter
public enum FieldName {
    USER_NAME("user_name", " AND t.%s = ?"),
    NAME("name", " AND t.%s = ?"),
    SURNAME("surname", " AND t.%s = ?");

    private final String paramName;
    private final String criteria;

    FieldName(final String paramName, final String criteria) {
        this.paramName = paramName;
        this.criteria = criteria;
    }
}
