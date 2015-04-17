package org.y3.riptools;

import org.y3.commons.application.IApplication;
import org.y3.riptools.app.RipToolsMaster_view;
import org.y3.riptools.tool.execbatch.ExecBatch_tool;
import org.y3.riptools.tool.nowinscr.NoWinScr_tool;
import org.y3.riptools.tool.text.Text_tool;

/** 
 * <p>Title: org.y3.riptools.rip_tools - RipTools</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-happens.de</p>
 * @author Christian.Rybotycky
 */
public class RipTools extends IApplication {
    
    private RipToolsMaster_view view;
    private ITool[] tools;
    
    public RipTools() {
        super();
    }
    
    @Override
    public void run() {
        view.setVisible(true);
    }
    
    public static void main(String args[]) {
        RipTools riptools = new RipTools();
        riptools.run();
    }

    @Override
    public String getUserPropertiesLocation() {
        return System.getProperty("user.home") + "/riptools.config";
    }

    @Override
    public String getLoggerPropertiesLocation() {
        return "/org/y3/riptools/log4j.properties";
    }

    @Override
    public String getResourceBundleLocation() {
        return "org/y3/riptools/riptools";
    }
    
    @Override
    public String getApplicationName() {
        return "RipTools";
    }

    @Override
    public void prepare() {
        //setup the tools
        tools = new ITool[]{
            new NoWinScr_tool(),
            new ExecBatch_tool(),
            new Text_tool()
        };
        //prepare the view
        view = new RipToolsMaster_view();
        view.addWindowListener(getShutDownListener());
        //register provided tools
        for(ITool tool : tools) {
            view.registerTool(tool);
        }
    }

    @Override
    public void beforeShutDown() {
        System.out.println("beforeshutdown");
        view.saveProperties();
        for(ITool tool : tools) {
            tool.storeUserPropertiesForShutdown();
        }
    }

}
