package org.y3.riptools;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.y3.riptools.app.RipToolsMaster_view;
import org.y3.riptools.tool.nowinscr.NoWinScr_tool;
import org.y3.riptools.util.PropertiesHelper;

/** 
 * <p>Title: org.y3.riptools.rip_tools - RipTools</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-happens.de</p>
 * @author Christian.Rybotycky
 * @version $Id$
*/
public class RipTools {
    
    public static ResourceBundle RES_BUNDLE = ResourceBundle.getBundle("org/y3/riptools/riptools");
    
    private final String logFilenameAndLocation = "/org/y3/riptools/log4j.properties";
    public static Logger LOG = Logger.getLogger(RipTools.class.getName());
    
    private final String propertiesFilename = "riptools.config";
    private final String propertiesLocation = System.getProperty("user.home") + "/" + propertiesFilename;
    public static Properties USER_PROPERTIES = null;
    
    private RipToolsMaster_view view;
    
    public RipTools() {
        init();
    }
    
    public void run() {
        view.setVisible(true);
    }
    
    private void init() {
        try {
            //setup logging
            PropertyConfigurator.configure(RipTools.class.getResourceAsStream(logFilenameAndLocation));
            //setup user properties
            File userPropertiesFile = new File(propertiesLocation);
            if (!userPropertiesFile.exists()) {
                Logger.getLogger(getClass()).debug("User properties file does not exist at: " + propertiesLocation);
                File originalPropertiesFile = new File(propertiesFilename);
                USER_PROPERTIES = new Properties();
                PropertiesHelper.saveProperties(USER_PROPERTIES, propertiesLocation, true);
            }
            USER_PROPERTIES = PropertiesHelper.loadProperties(propertiesLocation, true);
            view = new RipToolsMaster_view();
        } catch (IOException ex) {
            LOG.error("Save new properties file to " + logFilenameAndLocation + " failed", ex);
        }
        view.addWindowListener(getShutDownListener());
        //register provided tools
        view.registerTool(new NoWinScr_tool());
    }
    
    /**
     * Receive shut down listener object which manages the application shut down operations
     * @return shut down listener object
     */
    public WindowListener getShutDownListener() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                view.saveProperties();
                try {
                    //save properties
                    PropertiesHelper.saveProperties(USER_PROPERTIES, propertiesLocation, true);
                } catch (IOException ex) {
                    LOG.debug(ex);
                }
                LOG.debug("System exit by window closing");
                System.exit(0);
            }
        };
    }
    
    public static void main(String args[]) {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // handle exception
        }
        RipTools riptools = new RipTools();
        riptools.run();
    }

}
