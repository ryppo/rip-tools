package org.y3.riptools.tool.text;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import net.java.dev.designgridlayout.DesignGridLayout;
import org.apache.commons.lang3.StringUtils;
import org.y3.riptools.ITool;
import org.y3.riptools.RipTools;

/** 
 * <p>Title: org.y3.riptools.tool.text - Text_tool</p>
 * <p>Description: </p>
 * <p>Copyright: 2015</p>
 * <p>Company: SE Bordnetze GmbH</p>
 * <p>Department: CIT SC GSD</p>
 * @author Christian.Rybotycky
 * @version $Id$
*/
public class Text_tool extends ITool {
    
    private JPanel jp_ui;
    private JTextField jtf_text1, jtf_text2, jtf_text3;
    
    private SwingWorker<?, ?> worker;
    
    private final String PROPERTY_TEXT1 = "text_1";
    private String text_1;
    private final String PROPERTY_TEXT2 = "text_2";
    private String text_2;
    private final String PROPERTY_TEXT3 = "text_3";
    private String text_3;

    private void loadProperties() {
        text_1 = getUserProperties().getProperty(PROPERTY_TEXT1);
        text_2 = getUserProperties().getProperty(PROPERTY_TEXT2);
        text_3 = getUserProperties().getProperty(PROPERTY_TEXT3);
    }
    
    @Override
    public void storeUserPropertiesForShutdown() {
        text_1 = jtf_text1.getText();
        getUserProperties().setProperty(PROPERTY_TEXT1, StringUtils.defaultString(text_1));
        text_2 = jtf_text2.getText();
        getUserProperties().setProperty(PROPERTY_TEXT2, StringUtils.defaultString(text_2));
        text_3 = jtf_text3.getText();
        getUserProperties().setProperty(PROPERTY_TEXT3, StringUtils.defaultString(text_3));
    }
    
    @Override
    public JPanel getUserInterface(JFrame root) {
        if (jp_ui == null) {
            loadProperties();
            jtf_text1 = new JTextField(text_1);
            jtf_text2 = new JTextField(text_2);
            jtf_text3 = new JTextField(text_3);
            jp_ui = new JPanel();
            DesignGridLayout layout = new DesignGridLayout(jp_ui);
            layout.row().grid(new JLabel(RipTools.RB().getString("TEXT") + "_1")).add(jtf_text1).add(createCopyButton(jtf_text1));
            layout.row().grid(new JLabel(RipTools.RB().getString("TEXT") + "_2")).add(jtf_text2).add(createCopyButton(jtf_text2));
            layout.row().grid(new JLabel(RipTools.RB().getString("TEXT") + "_3")).add(jtf_text3).add(createCopyButton(jtf_text3));
        }
        return jp_ui;
    }
    
    private JButton createCopyButton(JTextField jtf) {
        JButton jb = new JButton(RipTools.RB().getString("COPY"));
        jb.addActionListener((ActionEvent e) -> {
            Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            systemClipboard.setContents(new StringSelection(jtf.getText()), null);
        });
        return jb;
    }
    
    @Override
    public String getName() {
        return RipTools.RB().getString("TEXT_TOOLS");
    }
}
