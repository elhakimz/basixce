package org.vaadin.teemu.clara;

import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import org.vaadin.teemu.clara.binder.Binder;
import org.vaadin.teemu.clara.inflater.AttributeFilter;
import org.vaadin.teemu.clara.inflater.LayoutInflater;

import java.io.InputStream;
import java.util.Iterator;

public class Clara {

    /**
     * Returns a {@link com.vaadin.ui.Component} that is read from the XML representation
     * given as {@link java.io.InputStream}. If you would like to bind the resulting
     * {@link com.vaadin.ui.Component} to a controller object, you should use
     * {@link #create(java.io.InputStream, Object, AttributeFilter...)} method
     * instead.
     * 
     * @param xml
     *            XML representation.
     * @return a {@link com.vaadin.ui.Component} that is read from the XML representation.
     */
    public static Component create(InputStream xml) {
        return create(xml, null);
    }

    /**
     * Returns a {@link com.vaadin.ui.Component} that is read from the XML representation
     * given as {@link java.io.InputStream} and binds the resulting {@link com.vaadin.ui.Component} to
     * the given {@code controller} object.
     * 
     * <br />
     * <br />
     * Optionally you may also provide {@link AttributeFilter}s to do some
     * modifications (or example localized translations) to any attributes
     * present in the XML representation.
     * 
     * @param xml
     *            XML representation.
     * @param controller
     *            controller object to bind the resulting {@code Component} (
     *            {@code null} allowed).
     * @param attributeFilters
     *            optional {@link AttributeFilter}s to do attribute
     *            modifications.
     * @return a {@link com.vaadin.ui.Component} that is read from the XML representation and
     *         bound to the given {@code controller}.
     */
    private static Component create(InputStream xml, Object controller,
                                    AttributeFilter... attributeFilters) {
        // Inflate the XML to a component (tree).
        LayoutInflater inflater = new LayoutInflater();
        if (attributeFilters != null) {
            for (AttributeFilter filter : attributeFilters) {
                inflater.addAttributeFilter(filter);
            }
        }
        Component result = inflater.inflate(xml);

        // Bind to controller if one is given.
        if (controller != null) {
            Binder binder = new Binder();
            binder.bind(result, controller);
        }
        return result;
    }

    /**
     * Returns a {@link com.vaadin.ui.Component} that is read from an XML file in the
     * classpath and binds the resulting {@link com.vaadin.ui.Component} to the given
     * {@code controller} object.
     * 
     * <br />
     * <br />
     * The filename is given either as a path relative to the class of the
     * {@code controller} object or as an absolute path. For example if you have
     * a {@code MyController.java} and {@code MyController.xml} files in the
     * same package, you can call this method like
     * {@code Clara.create("MyController.xml", new MyController())}.
     * 
     * <br />
     * <br />
     * Optionally you may also provide {@link AttributeFilter}s to do some
     * modifications (or example localized translations) to any attributes
     * present in the XML representation.
     * 
     * @param xmlClassResourceFileName
     *            filename of the XML representation (within classpath, relative
     *            to {@code controller}'s class or absolute path).
     * @param controller
     *            controller object to bind the resulting {@code Component}
     *            (non-{@code null}).
     * @param attributeFilters
     *            optional {@link AttributeFilter}s to do attribute
     *            modifications.
     * @return a {@link com.vaadin.ui.Component} that is read from the XML representation and
     *         bound to the given {@code controller}.
     */
    public static Component create(String xmlClassResourceFileName,
            Object controller, AttributeFilter... attributeFilters) {
        InputStream xml = controller.getClass().getResourceAsStream(
                xmlClassResourceFileName);
        System.out.println("xmlResourceFileName="+xmlClassResourceFileName+", xml = " + xml);
        return create(xml, controller, attributeFilters);
    }

    /**
     * Searches the given component hierarchy {@code root} for the
     * {@link com.vaadin.ui.Component} with a given {@code componentId} as its debugId (see
     * {@link com.vaadin.ui.Component#setDebugId(String)}).
     * 
     * <br />
     * <br />
     * If the given {@code root} is a {@link com.vaadin.ui.ComponentContainer}, this method
     * will iterate the component hierarchy in search for the correct
     * {@link com.vaadin.ui.Component}. Otherwise if the given {@code root} is a single
     * {@link com.vaadin.ui.Component}, only it is checked for its debugId value.
     * 
     * @param root
     *            root of a component tree (non-{@code null}).
     * @param componentId
     *            debugId of a component to search for (non-{@code null}).
     * @return {@link com.vaadin.ui.Component} with a given {@code componentId} as its debugId
     *         or {@code null} if no such component is found.
     * @throws IllegalArgumentException
     *             if either of the given parameters is {@code null}.
     * @see com.vaadin.ui.Component#setDebugId(String)
     */
    public static Component findComponentById(Component root, String componentId) {
        // Check for null before doing anything.
        if (componentId == null) {
            throw new IllegalArgumentException("Component id must not be null.");
        }
        if (root == null) {
            throw new IllegalArgumentException(
                    "Root component must not be null.");
        }

        // Recursively traverse the whole component tree starting from the given
        // root component.
        if (componentId.equals(root.getDebugId())) {
            return root;
        } else if (root instanceof ComponentContainer) {
            // TODO change to HasComponents for Vaadin 7?
            for (Iterator<Component> i = ((ComponentContainer) root)
                    .getComponentIterator(); i.hasNext();) {
                Component c = findComponentById(i.next(), componentId);
                if (c != null) {
                    return c;
                }
            }
        }
        return null;
    }

}
