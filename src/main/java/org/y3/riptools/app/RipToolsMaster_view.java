package org.y3.riptools.app;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import net.java.dev.designgridlayout.DesignGridLayout;
import org.y3.riptools.ITool;
import org.y3.riptools.RipTools;
import org.y3.riptools.util.PropertiesHelper;

/** 
 * <p>Title: org.y3.riptools.app - RipToolsMaster_view</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 * @version $Id$
*/
public class RipToolsMaster_view extends JFrame {
    
    int defaultWindowHeight = 500;
    int defaultWindowWidth = 1000;
    int defaultWindowPosX = 100;
    int defaultWindowPosY = 100;
    int defaultWindowExtendedState = JFrame.NORMAL;

    private ArrayList<ITool> tools;
    private JTabbedPane toolsTabs;
    
    public RipToolsMaster_view() {
        loadProperties();
        buildUi();
    }
    
    public void registerTool(ITool tool) {
        if (tools == null) {
            tools = new ArrayList<>(1);
        }
        if (!tools.contains(tool)) {
            tools.add(tool);
            tool.setUserProperties(RipTools.USER_PROPERTIES);
            tool.setLOG(RipTools.LOG);
            tool.setRES_BUNDLE(RipTools.RES_BUNDLE);
        }
        toolsTabs.add(tool.getName(), tool.getUserInterface(this));
    }
    
    public void unregisterTool(ITool tool) {
        if (tools != null && tools.contains(tool)) {
            tools.remove(tool);
        }
        for (int tabNo = 0; tabNo < toolsTabs.getTabCount(); tabNo++) {
            if (toolsTabs.getTitleAt(tabNo).equals(tool.getName())) {
                toolsTabs.remove(tabNo);
                break;
            }
        }
    }

    private void buildUi() {
        setIconImage(IconDictionary.APP_ICON);
        setTitle(RipTools.RES_BUNDLE.getString("APPLICATION_IDENTITY") +
                " " + RipTools.RES_BUNDLE.getString("VERSION_VALUE"));
        
        toolsTabs = new JTabbedPane();
        if (tools != null && tools.size() > 0) {
            for (ITool tool: tools) {
                toolsTabs.add(tool.getName(), tool.getUserInterface(this));
            }
        }
        DesignGridLayout layout = new DesignGridLayout(this);
        layout.row().grid().add(new JScrollPane(toolsTabs));
        setSize(defaultWindowWidth,defaultWindowHeight);
        setLocation(defaultWindowPosX, defaultWindowPosY);
        setExtendedState(defaultWindowExtendedState);
    }
    
    public void loadProperties() {
        RipTools.LOG.debug("Load user properties");
        defaultWindowHeight = PropertiesHelper.getIntegerFromProperties(
                RipTools.USER_PROPERTIES, "defaultWindowHeight", defaultWindowHeight);
        defaultWindowWidth = PropertiesHelper.getIntegerFromProperties(
                RipTools.USER_PROPERTIES, "defaultWindowWidth", defaultWindowWidth);
        defaultWindowPosX = PropertiesHelper.getIntegerFromProperties(
                RipTools.USER_PROPERTIES, "defaultWindowPosX", defaultWindowPosX);
        defaultWindowPosY = PropertiesHelper.getIntegerFromProperties(
                RipTools.USER_PROPERTIES, "defaultWindowPosY", defaultWindowPosY);
        defaultWindowExtendedState = PropertiesHelper.getIntegerFromProperties(
                RipTools.USER_PROPERTIES, "defaultWindowExtendedState");
    }
    
    public void saveProperties() {
        RipTools.LOG.debug("Save user properties");
        RipTools.USER_PROPERTIES.setProperty("defaultWindowHeight", Integer.toString(getSize().height));
        RipTools.USER_PROPERTIES.setProperty("defaultWindowWidth", Integer.toString(getSize().width));
        RipTools.USER_PROPERTIES.setProperty("defaultWindowPosX", Integer.toString(getLocation().x));
        RipTools.USER_PROPERTIES.setProperty("defaultWindowPosY", Integer.toString(getLocation().y));
        RipTools.USER_PROPERTIES.setProperty("defaultWindowExtendedState", Integer.toString(getExtendedState()));
        if (tools != null) {
            for (Iterator<ITool> iterator = tools.iterator(); iterator.hasNext();) {
                ITool next = iterator.next();
                next.storeUserPropertiesForShutdown();
            }
        }
    }
    
}
