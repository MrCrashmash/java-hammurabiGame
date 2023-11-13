package main.java.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static main.java.variables.GameVariables.*;
import static main.java.constants.GameConstants.*;
import main.java.logic.Logic;

import javax.swing.*;
public class GUI implements ActionListener{

    // ================= Trade Acres
    private static JLabel trade_Label;
    private static JTextField trade_Text;
    private static JLabel trade_subLabel;

    // ================= Feed People
    private static JLabel feed_Label;
    private static JTextField feed_Text;
    private static JLabel feed_subLabel;

    // ================= Plant seed
    private static JLabel plant_Label;
    private static JTextField plant_Text;
    private static JLabel plant_subLabel;

    // ================= Submit Button
    private static JButton submit_Button;
    private static JLabel status;

    // ================== Text
    private static JLabel reportText;

    /**
     * This method creates the UI for the game.
     */
    public void startGUI(){

        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        frame.setSize(1000,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        // ================= Title =========================
        JLabel title = new JLabel("Hammurabi", SwingConstants.CENTER);
        title.setBounds(0,10, 1000, 30);
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(title);

        JLabel subTitle = new JLabel("The classic game of strategy and resource allocation", SwingConstants.CENTER);
        subTitle.setBounds(0,40, 1000, 25);
        subTitle.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(subTitle);

        // ================= Status Text =========================
        reportText = new JLabel("");
        reportText.setBounds(20,65, 300, 160);
        panel.add(reportText);
        setReportText();

        // ================= Trade Acres =========================
        trade_Label = new JLabel("How many acres do you wish to buy or sell?");
        trade_Label.setBounds(20,235,400,15);
        trade_Label.setFont(new Font("Arial", Font.PLAIN, 15));
        panel.add(trade_Label);

        trade_Text = new JTextField();
        trade_Text.setBounds(410,235,100,15);
        panel.add(trade_Text);

        trade_subLabel = new JLabel("negative amount for selling");
        trade_subLabel.setBounds(410,250,300,15);
        trade_subLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(trade_subLabel);

        // ================= Feed People =========================
        feed_Label = new JLabel("How many bushels do you wish to feed your people?");
        feed_Label.setBounds(20, 285, 400, 15);
        feed_Label.setFont(new Font("Arial", Font.PLAIN, 15));
        panel.add(feed_Label);

        feed_Text = new JTextField();
        feed_Text.setBounds(410, 285, 100, 15);
        panel.add(feed_Text);

        feed_subLabel = new JLabel("each citizen needs at least "+MIN_GRAIN_TO_SURVIVE+" bushels a year");
        feed_subLabel.setBounds(410,300,300,15);
        feed_subLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(feed_subLabel);

        // ================= Plant Seeds =========================
        plant_Label = new JLabel("How many bushels do you wish to use for planting acres?");
        plant_Label.setBounds(20, 335, 400, 15);
        plant_Label.setFont(new Font("Arial", Font.PLAIN, 15));
        panel.add(plant_Label);

        plant_Text = new JTextField();
        plant_Text.setBounds(410, 335, 100, 15);
        panel.add(plant_Text);

        plant_subLabel = new JLabel("each acre takes "+SEED_REQUIRED_PER_ACRE+" seed");
        plant_subLabel.setBounds(410,350,300,15);
        plant_subLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(plant_subLabel);

        // ================= Submit Button =========================
        submit_Button = new JButton("Submit");
        submit_Button.setBounds(410,385,80,25);
        submit_Button.addActionListener(new GUI());
        panel.add(submit_Button);

        status = new JLabel("");
        status.setBounds(20,400,600,25);
        panel.add(status);

        frame.setVisible(true);
    }


    /**
     * This method performed the action for the event that occurs.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String trade = trade_Text.getText();
        String feed = feed_Text.getText();
        String plant = plant_Text.getText();

        trade = trade.isEmpty() ? "0" : trade;
        feed = feed.isEmpty() ? "0" : feed;
        plant = plant.isEmpty() ? "0" : plant;

        int tradeInt = Integer.parseInt(trade);
        int feedInt = Integer.parseInt(feed);
        int plantInt = Integer.parseInt(plant);

        Logic.submitter(tradeInt, feedInt, plantInt);
    }

    /**
     * This method sets the Report text for the ruler, so he knows the current state of his kingdom
     */
    public static void setReportText() {
        reportText.setText("<html>Hammurabi: I beg to report to you,<br>"
                                +" In Year "+k_year+", "+k_starved+" people starved.<br>"
                                +" "+k_immigrated+" people came to the city.<br>"
                                + (isPlague ? plagueText : "")
                                +" The city population is now "+k_peasants+".<br>"
                                +" The city now owns "+k_area+" acres.<br>"
                                +" You harvested "+k_harvest+" bushels.<br>"
                                +" Rats ate "+ratsAte+" bushels.<br>"
                                +" You now have "+k_grain+" bushels in store.<br>"
                                +" Land is trading at "+k_acre_price+" bushels per acre.</html>");
    }

    /**
     * This method resets the input text-fields
     */
    public static void textFieldRest(){
        trade_Text.setText("");
        feed_Text.setText("");
        plant_Text.setText("");
        status.setText("");
    }

    /**
     * This method sets the Status, for example the Input the ruler gave is not valid.
     * @param Text The status text for the ruler
     */
    public static void setStatus(String Text){
        status.setText(Text);
    }
}