package org.basix.metadata.module;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: abilhakim
 * Date: 3/31/13
 * Time: 7:43 PM
 */
public class ModuleMetaProcessor implements Serializable {

    private final SAXParserFactory factory = SAXParserFactory.newInstance();
    private List<ModuleTag> moduleTags = new ArrayList<ModuleTag>();
    private String fileLocation;

    public ModuleMetaProcessor(String fileLocation){
       this.fileLocation = fileLocation;
       initialize();
    }

    private void initialize(){
        try {

            SAXParser saxParser = factory.newSAXParser();
            ModuleMetaHandler handler = new ModuleMetaHandler();
            InputStream inputStream = new FileInputStream(fileLocation);
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");

            saxParser.parse(is, handler);
            moduleTags = handler.getModuleTags();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ModuleTag> getModuleTags() {
        return moduleTags;
    }

    public class ModuleMetaHandler extends DefaultHandler{

        final List<ModuleTag> moduleTags=new ArrayList<ModuleTag>();

        ModuleTag currentModule;
        FunctionTag currentFunction;

        @Override
        public void startElement(String uri, String localName, String qname, Attributes attributes) throws SAXException {

            if(qname.equalsIgnoreCase("module")){
              currentModule = new ModuleTag(attributes.getValue("caption"),attributes.getValue("package"));
              currentModule.setRole(attributes.getValue("role"));
              moduleTags.add(currentModule);

            }else if(qname.equalsIgnoreCase("function")){

              currentFunction = new FunctionTag(attributes.getValue("caption"),attributes.getValue("controller"),false);
              currentModule.getFunctionTags().add(currentFunction);

            } else if(qname.equalsIgnoreCase("separator")){
                currentFunction = new FunctionTag(attributes.getValue("caption"),null,true);
                currentModule.getFunctionTags().add(currentFunction);
            }

        }

        public List<ModuleTag> getModuleTags() {
            return moduleTags;
        }
    }

}
