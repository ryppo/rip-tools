package org.y3.riptools;

import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

/** 
 * <p>Title: org.y3.riptools.rip_tools - ITool</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 */
public abstract class ITool {
    
    private Properties userProperties;
    private Logger LOG;
    private ResourceBundle RES_BUNDLE;

    public ResourceBundle getRES_BUNDLE() {
        return RES_BUNDLE;
    }

    public void setRES_BUNDLE(ResourceBundle RES_BUNDLE) {
        this.RES_BUNDLE = RES_BUNDLE;
    }

    public Logger getLOG() {
        return LOG;
    }

    public void setLOG(Logger LOG) {
        this.LOG = LOG;
    }

    public Properties getUserProperties() {
        return userProperties;
    }

    public void setUserProperties(Properties userProperties) {
        this.userProperties = userProperties;
    }
    
    public abstract void storeUserPropertiesForShutdown();
    
    public abstract JPanel getUserInterface(JFrame root);
    
    public abstract String getName();

}
