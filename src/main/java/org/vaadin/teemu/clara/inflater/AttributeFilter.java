package org.vaadin.teemu.clara.inflater;

import com.vaadin.ui.Component;

public interface AttributeFilter {

    /**
     * This method is called before an attribute value is set to a
     * {@link com.vaadin.ui.Component} allowing manipulation of the value by calling
     * {@link org.vaadin.teemu.clara.inflater.AttributeContext#setValue(Object)}.
     * 
     * <br />
     * <br />
     * Call the {@link org.vaadin.teemu.clara.inflater.AttributeContext#proceed()} to proceed to the next
     * {@link org.vaadin.teemu.clara.inflater.AttributeFilter} and to finally set the value if no other
     * {@link org.vaadin.teemu.clara.inflater.AttributeFilter}s exist. If you do not call the
     * {@link org.vaadin.teemu.clara.inflater.AttributeContext#proceed()} method, the value will never be set
     * for the {@link com.vaadin.ui.Component}.
     * 
     * @param attributeContext
     */
    void filter(AttributeContext attributeContext);

}
