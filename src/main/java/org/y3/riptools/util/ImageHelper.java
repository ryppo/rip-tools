package org.y3.riptools.util;

import java.awt.Image;
import javax.swing.ImageIcon;

/** 
 * <p>Title: org.y3.riptools.util - ImageHelper</p>
 * <p>Description: Utils for images</p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 * @version $Id$
*/
public class ImageHelper {
    
    /**
     * Receive image icon object by image class path
     * @param imageClassPath path of the image
     * @return image icon
     */
    public static ImageIcon getIcon(String imageClassPath) {
        return new ImageIcon(ImageHelper.class.getClassLoader().getResource(imageClassPath));
    }
    
    /**
     * Receive image object by image class path
     * @param imageClassPath path of the image
     * @return image
     */
    public static Image getImage(String imageClassPath) {
        return getIcon(imageClassPath).getImage();
    }

}
