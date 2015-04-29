package org.y3.riptools.tool.nowinscr;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import net.java.dev.designgridlayout.DesignGridLayout;
import org.apache.commons.lang3.math.NumberUtils;
import org.y3.riptools.ITool;
import org.y3.riptools.RipTools;

/** 
 * <p>Title: org.y3.riptools.rip_tools.nowinscr - NoWinScr_tool</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 */
public class NoWinScr_tool extends ITool {
    
    private JPanel jp_ui;
    private JCheckBox jcb_enable;
    private JTextField jtf_sleepDuration, jtf_mouseMovePixels;
    private JProgressBar jpb_sleepProgress;
    
    private SwingWorker<?, ?> worker;
    
    private int defaultSleepDuration = 1000;
    private int defaultMouseMovePixels = 5;
    private final String PROPERTY_KEY_DEFAULT_SLEEP_DURATION = "defaultSleepDuration";
    private final String PROPERTY_KEY_DEFAULT_MOUSE_MOVE_PIXELS = "defaultMouseMovePixels";
    
    public NoWinScr_tool() {
    }
    
    private void loadProperties() {
        String property_defaultSleepDuration = getUserProperties().getProperty(PROPERTY_KEY_DEFAULT_SLEEP_DURATION);
        defaultSleepDuration = NumberUtils.toInt(property_defaultSleepDuration, defaultSleepDuration);
        String property_defaultMouseMovePixels = getUserProperties().getProperty(PROPERTY_KEY_DEFAULT_MOUSE_MOVE_PIXELS);
        defaultMouseMovePixels = NumberUtils.toInt(property_defaultMouseMovePixels, defaultMouseMovePixels);
    }

    @Override
    public JPanel getUserInterface(JFrame root) {
        loadProperties();
        jcb_enable = new JCheckBox(RipTools.RB().getString("ENABLE"));
        jcb_enable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jcb_enable.isSelected()) {
                    run();
                } else {
                    stop();
                }
            }
        });
        jtf_sleepDuration = new JTextField();
        jtf_sleepDuration.setText(Integer.toString(defaultSleepDuration));
        jtf_mouseMovePixels = new JTextField();
        jtf_mouseMovePixels.setText(Integer.toString(defaultMouseMovePixels));
        jpb_sleepProgress = new JProgressBar(0, defaultSleepDuration);
        if (jp_ui == null) {
            jp_ui = new JPanel();
            DesignGridLayout layout = new DesignGridLayout(jp_ui);
            layout.row().grid().add(jcb_enable);
            layout.row().grid().add(new JLabel(getRES_BUNDLE().getString("DEFAULT_SLEEP_DURATION"))).add(jtf_sleepDuration);
            layout.row().grid().add(new JLabel(getRES_BUNDLE().getString("DEFAULT_MOUSE_MOVE_PIXELS"))).add(jtf_mouseMovePixels);
            layout.row().grid().add(jpb_sleepProgress);
        }
        return jp_ui;
    }

    @Override
    public String getName() {
        return RipTools.RB().getString("NO_WIN_SCR_TOOL");
    }
    
    public void run() {
        getLOG().info("Run win32ideltimer");
        String durationString = jtf_sleepDuration.getText();
        if (durationString != null && durationString.length() > 0) {
            defaultSleepDuration = Integer.parseInt(durationString);
            jpb_sleepProgress.setMaximum(defaultSleepDuration);
            jpb_sleepProgress.setValue(0);
            String mouseMoveString = jtf_mouseMovePixels.getText();
            defaultMouseMovePixels = Integer.parseInt(mouseMoveString);
        }
        worker = new SwingWorker<Void, Integer>() {
            
            private Win32IdleTime idler;
            
            @Override
            protected Void doInBackground() throws InterruptedException {
                try {
                    idler = new Win32IdleTime(getLOG(), defaultSleepDuration, jpb_sleepProgress, defaultMouseMovePixels);
                } catch (Exception ex) {
                    getLOG().error("Run win32idletimer failed", ex);
                    idler.stop();
                    done();
                }
                return null;
                }
            
            @Override
            public void done() {
                getLOG().info("Run win32idletimer stopped");
                idler = null;
            }
        };
        worker.execute();
    }
    
    public void stop() {
        getLOG().info("Shut down win32ideltimer");
        worker.cancel(true);
        worker = null;
        
    }

    @Override
    public void storeUserPropertiesForShutdown() {
        String durationString = jtf_sleepDuration.getText();
        if (durationString != null && durationString.length() > 0) {
            defaultSleepDuration = Integer.parseInt(durationString);
        }
        getUserProperties().setProperty(PROPERTY_KEY_DEFAULT_SLEEP_DURATION, Integer.toString(defaultSleepDuration));
        String mouseMoveString = jtf_mouseMovePixels.getText();
        if (mouseMoveString != null && mouseMoveString.length() > 0) {
            defaultMouseMovePixels = Integer.parseInt(mouseMoveString);
        }
        getUserProperties().setProperty(PROPERTY_KEY_DEFAULT_MOUSE_MOVE_PIXELS, Integer.toString(defaultMouseMovePixels));
    }

}
