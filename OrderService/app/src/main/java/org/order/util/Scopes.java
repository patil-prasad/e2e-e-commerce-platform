package org.order.util;

import lombok.Getter;

@Getter
public enum Scopes {
    READ_ORDER("read:order"),
    WRITE_ORDER("write:order");

    private final String scope;

    Scopes(String scope) {
        this.scope = scope;
    }

}
