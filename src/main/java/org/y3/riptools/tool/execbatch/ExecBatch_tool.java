package org.y3.riptools.tool.execbatch;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import net.java.dev.designgridlayout.DesignGridLayout;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.y3.riptools.ITool;
import org.y3.riptools.RipTools;

/** 
 * <p>Title: org.y3.riptools - ExecBatch_tool</p>
 * <p>Description: </p>
 * <p>Copyright: 2015</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
*/
public class ExecBatch_tool extends ITool {
    
    private JPanel jp_ui;
    private JTextField jtf_batchFile;
    private JButton jb_execute;
    
    private SwingWorker<?, ?> worker;
    
    private String defaultBatchFileName = "";
    private final String PROPERTY_KEY_DEFAULT_BATCH_FILE = "defaultBatchFile";
    
    
    private void loadProperties() {
        defaultBatchFileName = getUserProperties().getProperty(PROPERTY_KEY_DEFAULT_BATCH_FILE);
    }

    @Override
    public void storeUserPropertiesForShutdown() {
        defaultBatchFileName = jtf_batchFile.getText();
        getUserProperties().setProperty(PROPERTY_KEY_DEFAULT_BATCH_FILE, StringUtils.defaultString(defaultBatchFileName));
    }

    @Override
    public JPanel getUserInterface(JFrame root) {
        loadProperties();
        jtf_batchFile = new JTextField(defaultBatchFileName);
        jb_execute = new JButton(RipTools.RB().getString("EXECUTE"));
        jb_execute.addActionListener((ActionEvent e) -> {
            execute();
        });
        if (jp_ui == null) {
            jp_ui = new JPanel();
            DesignGridLayout layout = new DesignGridLayout(jp_ui);
            layout.row().grid(new JLabel(RipTools.RB().getString("BATCH_FILE"))).add(jtf_batchFile, 2);
            layout.row().grid().empty().add(jb_execute);
        }
        return jp_ui;
    }
    
    private void execute() {
        try {
            defaultBatchFileName = jtf_batchFile.getText();
            Desktop.getDesktop().open(new File(defaultBatchFileName));
        } catch (IOException ex) {
            Logger.getLogger(ExecBatch_tool.class.getName()).error(ex);
        }
    }

    @Override
    public String getName() {
        return RipTools.RB().getString("EXECUTE_BATCH_TOOL");
    }

}
