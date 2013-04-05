package org.vaadin.teemu.clara.inflater;

import com.vaadin.Application;
import com.vaadin.service.ApplicationContext;
import com.vaadin.ui.Component;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;

public class LayoutInflater {

    private ComponentManager componentManager = new DefaultComponentManager();

    public void setComponentManager(ComponentManager componentManager) {
        this.componentManager = componentManager;
    }

    /**
     * Inflates the XML layout file from {@code VAADIN/layouts} directory. Some
     * examples of valid {@code xmlFile} parameter values include
     * {@code mylayout.xml} or {@code mylayouts/mylayout.xml}. <br />
     * <br />
     * <b>This method has not been yet tested within portal environment.</b>
     * 
     * @param app
     * @param xmlFile
     * @return
     * @throws LayoutInflaterException
     */
    // TODO Test in portal environment.
    public Component inflate(Application app, String xmlFile)
            throws LayoutInflaterException {
        File layoutFile = getLayoutFile(app.getContext(), xmlFile);
        try {
            return inflate(new FileInputStream(layoutFile));
        } catch (FileNotFoundException e) {
            throw new LayoutInflaterException("Given file "
                    + layoutFile.getAbsolutePath() + " does not exist.");
        }
    }

    public Component inflate(InputStream xml) throws LayoutInflaterException {
        try {
            // initialize content handler
            LayoutInflaterContentHandler handler = new LayoutInflaterContentHandler(
                    componentManager);

            // parse the XML
            XMLReader parser = XMLReaderFactory.createXMLReader();
            parser.setContentHandler(handler);

            parser.parse(new InputSource(xml));

            // construct the result
            return handler.getRoot();
        } catch (SAXException e) {
            throw new LayoutInflaterException(e);
        } catch (IOException e) {
            throw new LayoutInflaterException(e);
        } catch (ComponentInstantiationException e) {
            throw new LayoutInflaterException(e.getMessage(), e);
        }
    }

    public void addAttributeFilter(AttributeFilter attributeFilter) {
        componentManager.addAttributeFilter(attributeFilter);
    }

    public void removeAttributeFilter(AttributeFilter attributeFilter) {
        componentManager.removeAttributeFilter(attributeFilter);
    }

    private static File getLayoutFile(ApplicationContext context,
            String filepath) {
        return new File(context.getBaseDirectory().getAbsoluteFile()
                + File.separator + "VAADIN" + File.separator + "layouts"
                + File.separator + filepath);
    }
}
