package org.vaadin.teemu.clara.inflater;

import java.lang.reflect.Method;


abstract class AttributeContext {

    private Object value;
    private final Method setter;

    public AttributeContext(Method setter, Object value) {
        this.value = value;
        this.setter = setter;
    }

    /**
     * Continue processing of the attribute by passing on to the next
     * {@link AttributeFilter} or finally to the setter method if there is
     * no more {@link AttributeFilter}s. If you do not call this method,
     * the value is never set to the component.
     * 
     * @throws Exception
     */
    public abstract void proceed() throws Exception;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Method getSetter() {
        return setter;
    }
}
