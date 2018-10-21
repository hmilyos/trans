package com.hmily.transaxon.springaxonsaga.customer.query;

import org.axonframework.common.Assert;

public class CustomerId {

    private final String identifier;
    private final int hashCode;

    public CustomerId(String identifier) {
        Assert.notNull(identifier, () -> "Identifier may not be null");
        this.identifier = identifier;
        this.hashCode = identifier.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerId customerId = (CustomerId) o;
        return identifier.equals(customerId.identifier);

    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return identifier;
    }

}
