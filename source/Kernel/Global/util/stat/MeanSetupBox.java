/**
 * Description: Calculate the mean value of the results by MAOS.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 19, 2002 
 * Xiaofeng Xie    Mar 01, 2003
 * Xiaofeng Xie    Nov 05, 2003    With mean evaluation times
 *
 * @version 1.0
 * @Since MAOS1.0
 */

package Global.util.stat;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Global.methods.*;
import javax.swing.border.*;

public class MeanSetupBox extends JDialog implements ActionListener{
//  StatusBar

  JButton calcVthButton = new JButton("Calc");
  JButton statButton = new JButton("Stat.");
  JButton clearButton = new JButton("Clear");
  JButton cancelButton = new JButton("Exit");

  JLabel workDirLabel = new JLabel("Work dir:");
  JTextField workDirInput = new JTextField(20);
  JCheckBox subDirBox = new JCheckBox("Subdirectories?", true);

  JLabel thValueLabel = new JLabel("Threshold value:");
  JTextField thValueInput = new JTextField("0", 10);
  JLabel thIndexLabel = new JLabel("Index:");
  JTextField thIndexInput = new JTextField("3", 3);
  JLabel ratioLabel = new JLabel("Allowed ratio:");
  JTextField ratioInput = new JTextField("1.0", 3);

  JLabel optimLabel = new JLabel("Optim value:");
  JTextField optimInput = new JTextField(15);

  JLabel errorsLabel = new JLabel("Error ratio:");
  JTextField errorsInput = new JTextField("0.03", 5);

  JLabel scaleLabel = new JLabel("Scale:");
  JTextField scaleInput = new JTextField("1", 3);

  JComboBox statBox = new JComboBox(new String[] {"Mean", "EvalNum"});

  public MeanSetupBox(Frame parent) {
    super(parent,"Result statastics...",true);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try  {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    pack();
  }

  private void jbInit() throws Exception  {
    ratioLabel.setForeground(Color.blue);
    workDirLabel.setForeground(Color.blue);

    JPanel panel11 = new JPanel();
    panel11.setLayout(new BorderLayout());
    panel11.add(workDirLabel, BorderLayout.WEST);
    panel11.add(workDirInput, BorderLayout.CENTER);
    panel11.add(clearButton,BorderLayout.EAST);

    JPanel panel12 = new JPanel();
    panel12.add(subDirBox,BorderLayout.EAST);

    JPanel panel1 = new JPanel();
    panel1.setLayout(new BorderLayout());
    panel1.add(panel11, BorderLayout.WEST);
    panel1.add(panel12,BorderLayout.EAST);

    getContentPane().add(panel1, BorderLayout.NORTH);

    JPanel panel211 = new JPanel();
    panel211.setLayout(new BorderLayout());
    panel211.add(thValueLabel, BorderLayout.WEST);
    panel211.add(thValueInput, BorderLayout.EAST);

    JPanel panel212 = new JPanel();
    panel212.setLayout(new BorderLayout());
    panel212.add(thIndexLabel, BorderLayout.WEST);
    panel212.add(thIndexInput, BorderLayout.EAST);

    JPanel panel21 = new JPanel();
    panel21.setLayout(new BorderLayout());
    panel21.add(panel211, BorderLayout.WEST);
    panel21.add(panel212, BorderLayout.EAST);

    JPanel panel22 = new JPanel();
    panel22.setLayout(new BorderLayout());
    panel22.add(ratioLabel, BorderLayout.WEST);
    panel22.add(ratioInput, BorderLayout.EAST);

    JPanel panel23 = new JPanel();
    panel23.setLayout(new BorderLayout());
    panel23.add(scaleLabel, BorderLayout.WEST);
    panel23.add(scaleInput, BorderLayout.EAST);
    scaleLabel.setEnabled(statBox.getSelectedIndex()==1);
    scaleInput.setEnabled(statBox.getSelectedIndex()==1);

    JPanel panel2 = new JPanel();
    panel2.setLayout(new BorderLayout());
    panel2.add(panel21, BorderLayout.WEST);
    panel2.add(panel22, BorderLayout.CENTER);
    panel2.add(panel23, BorderLayout.EAST);
    getContentPane().add(panel2, BorderLayout.CENTER);

    JPanel panel31 = new JPanel();
    panel31.setLayout(new BorderLayout());
    panel31.add(optimLabel, BorderLayout.WEST);
    panel31.add(optimInput, BorderLayout.EAST);

    JPanel panel32 = new JPanel();
    panel32.setLayout(new BorderLayout());
    panel32.add(errorsLabel, BorderLayout.WEST);
    panel32.add(errorsInput, BorderLayout.EAST);

    EtchedBorder etchedBorder = new EtchedBorder(EtchedBorder.RAISED,Color.white,new java.awt.Color(148, 145, 140));
    JPanel panel3 = new JPanel(new BorderLayout());
    panel3.setBorder(new TitledBorder(etchedBorder,"Calculate threshold value"));
    panel3.add(panel31, BorderLayout.WEST);
    panel3.add(panel32, BorderLayout.CENTER);
    panel3.add(calcVthButton, BorderLayout.EAST);

    JPanel panelCenter = new JPanel();
    panelCenter.setLayout(new BorderLayout());
    panelCenter.setLayout(new BorderLayout());
    panelCenter.add(panel2, BorderLayout.NORTH);
    panelCenter.add(panel3, BorderLayout.SOUTH);

    getContentPane().add(panelCenter, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    JPanel buttonLeftPanel = new JPanel();
    JPanel buttonRightPanel = new JPanel();
    buttonLeftPanel.add(statBox,BorderLayout.WEST);
    buttonLeftPanel.add(statButton,BorderLayout.EAST);
    buttonRightPanel.add(cancelButton, BorderLayout.EAST);

    buttonPanel.add(buttonLeftPanel,BorderLayout.WEST);
    buttonPanel.add(buttonRightPanel,BorderLayout.EAST);

    calcVthButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          double optimV = TypeConverter.toDouble(optimInput.getText());
          double newTHValue = new Double(errorsInput.getText()).doubleValue();
          if(optimV!=0) {
            newTHValue = optimV+Math.abs(optimV)*newTHValue;
          }
          thValueInput.setText(new Double(newTHValue).toString());
        } catch (Exception ex) {
        }
      }
    });
    statBox.addActionListener(this);
    statButton.addActionListener(this);
    clearButton.addActionListener(this);
    cancelButton.addActionListener(this);
    getContentPane().add(buttonPanel, BorderLayout.SOUTH);
  }

  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
    super.processWindowEvent(e);
  }
  private void execAll(String workDir, double thValue, int thIndex, double ratioValue) throws Exception {
    File fileDir = new File(workDir);
    if (!fileDir.isDirectory()) return;
    try {
      exec(workDir, thValue, thIndex, ratioValue);
    } catch (Exception e) {}
    File[] files = fileDir.listFiles();
    for (int i=0; i<files.length; i++) {
      execAll(files[i].getAbsolutePath(), thValue, thIndex, ratioValue);
    }
  }

  private void exec(String workDir, double thValue, int thIndex, double ratioValue) throws Exception {
    if(statBox.getSelectedIndex()==0) {
      BasicResultStat.execAllMean(workDir, thValue, thIndex, ratioValue);
    } else {
      BasicResultStat.execAllEvalNum(workDir, thValue, thIndex, ratioValue, TypeConverter.toDouble(scaleInput.getText()));
    }
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == statBox) {
      try {
        scaleLabel.setEnabled(statBox.getSelectedIndex()==1);
        scaleInput.setEnabled(statBox.getSelectedIndex()==1);
      } catch(Exception ex) {}
    } else if(e.getSource()==statButton) {
      try {
        int thIndex = Integer.MAX_VALUE;
        try {
          if (thIndexInput.isEnabled()) {
            thIndex = TypeConverter.toInteger(thIndexInput.getText());
          }
        }catch (Exception ex) {
        }
        double thValue = Double.MAX_VALUE;
        try {
          thValue = TypeConverter.toDouble(thValueInput.getText());
        }catch (Exception ex) {
        }
        double ratioValue = 1;
        try {
          ratioValue = TypeConverter.toDouble(ratioInput.getText());
        }catch (Exception ex) {
        }
        if (subDirBox.isSelected()) {
          execAll(workDirInput.getText(), thValue, thIndex, ratioValue);
        } else {
          exec(workDirInput.getText(), thValue, thIndex, ratioValue);
         }
      } catch(Exception ex) {}
    } else if(e.getSource()==cancelButton) {
      this.dispose();
    } else if(e.getSource()==clearButton) {
      workDirInput.setText("");
    }
  }
}


