package org.vaadin.teemu.clara.inflater;

import java.util.Map;


import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;

public interface ComponentManager {

    /**
     * Returns a new {@link com.vaadin.ui.Component} instance of given {@code namespace} and
     * {@code name} with fields populated from the {@code attributes} map. If
     * the component cannot be instantiated properly a
     * {@link org.vaadin.teemu.clara.inflater.ComponentInstantiationException} is thrown.
     * 
     * @param namespace
     * @param name
     * @param attributes
     * @return a new {@link com.vaadin.ui.Component} instance.
     * @throws org.vaadin.teemu.clara.inflater.ComponentInstantiationException
     */
    Component createComponent(String namespace, String name,
                              Map<String, String> attributes)
            throws ComponentInstantiationException;

    /**
     * Applies the layout related attributes (for example alignment and expand
     * ratio) of the given {@link com.vaadin.ui.Component} in the given
     * {@link com.vaadin.ui.ComponentContainer}.
     * 
     * @param layout
     * @param component
     * @param attributes
     * @throws IllegalStateException
     *             if the given {@code container} isn't the parent of the given
     *             {@code component}.
     */
    void applyLayoutAttributes(ComponentContainer container,
                               Component component, Map<String, String> attributes);

    void addAttributeFilter(AttributeFilter attributeFilter);

    void removeAttributeFilter(AttributeFilter attributeFilter);

}
