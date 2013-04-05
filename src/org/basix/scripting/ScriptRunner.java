package org.basix.scripting;

import com.AppFacade;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.basix.util.AppConstant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * User: abilhakim
 * Date: 3/31/13
 * Time: 3:33 AM
 */
public class ScriptRunner extends AbstractScriptRunner {

    private Properties properties;

    private Binding binding;
    private GroovyScriptEngine scriptEngine;
    private Object output;
    private Map parameters=new HashMap();
    private String scriptFile;
    private String scriptDirectory;

    private static ScriptRunner __instance;



    public static ScriptRunner getInstance(){
     if(__instance==null) __instance = new ScriptRunner();
     return __instance;
    }


    private  ScriptRunner(){

        if(AppConstant.PLATFORM.equals("unix")){
            scriptDirectory = AppFacade.getInstance().getBaseDirectory()+"/scripts/";
        }else{
            scriptDirectory = AppFacade.getInstance().getBaseDirectory()+"\\scripts\\";
        }

    }

    /**
     * run script
     * @return
     */
    public void run(){



        String[] roots = new String[] { scriptDirectory };

        output=null;
        binding = new Binding();

        try {
            binding.setVariable("parameters",parameters);
            scriptEngine = new GroovyScriptEngine(roots);
            System.out.println("Running script:"+scriptFile);


            System.out.println("scriptFile = " + scriptFile);

            int n = scriptFile.lastIndexOf("modules");
            System.out.println("n = " + n);
            String cut = scriptFile.substring(n);
            System.out.println("cut = " + cut);

            scriptEngine.run(cut, binding);
            output=binding.getVariable("output");

        } catch (ResourceException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public
    ScriptRunner addParameter(String name, Object value){
        parameters.put(name,value);
        return this;
    }

    public String getScriptFile() {
        return scriptFile;
    }


    public void setScriptFile(String scriptFile) {
        this.scriptFile = scriptFile;
    }

    public Object getOutput() {
        return output;
    }

    public void setOutput(Object output) {
        this.output = output;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }




}
