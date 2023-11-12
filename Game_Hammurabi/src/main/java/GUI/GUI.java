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
    private static JLabel  statusText;

    public void startGUI(){

        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        frame.setSize(1000,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        // ================= Trade Acres =========================
        trade_Label = new JLabel("How many acres do you wish to buy or sell?");
        trade_Label.setBounds(10,20,400,15);
        trade_Label.setFont(new Font("Arial", Font.PLAIN, 15));
        panel.add(trade_Label);

        trade_Text = new JTextField();
        trade_Text.setBounds(400,20,100,15);
        panel.add(trade_Text);

        trade_subLabel = new JLabel("negative amount for selling");
        trade_subLabel.setBounds(400,35,300,15);
        trade_subLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(trade_subLabel);

        // ================= Feed People =========================
        feed_Label = new JLabel("How many bushels do you wish to feed your people?");
        feed_Label.setBounds(10, 60, 400, 15);
        feed_Label.setFont(new Font("Arial", Font.PLAIN, 15));
        panel.add(feed_Label);

        feed_Text = new JTextField();
        feed_Text.setBounds(400, 60, 100, 15);
        panel.add(feed_Text);

        feed_subLabel = new JLabel("each citizen needs at least "+MIN_GRAIN_TO_SURVIVE+" bushels a year");
        feed_subLabel.setBounds(400,75,300,15);
        feed_subLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(feed_subLabel);

        // ================= Plant Seeds =========================
        plant_Label = new JLabel("How many bushels do you wish to feed your people?");
        plant_Label.setBounds(10, 100, 400, 15);
        plant_Label.setFont(new Font("Arial", Font.PLAIN, 15));
        panel.add(plant_Label);

        plant_Text = new JTextField();
        plant_Text.setBounds(400, 100, 100, 15);
        panel.add(plant_Text);

        plant_subLabel = new JLabel("each acre takes "+SEED_REQUIRED_PER_ACRE+" seed");
        plant_subLabel.setBounds(400,115,300,15);
        plant_subLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(plant_subLabel);


        // ================= Submit Button =========================
        submit_Button = new JButton("Submit");
        submit_Button.setBounds(400,150,80,25);
        submit_Button.addActionListener(new GUI());
        panel.add(submit_Button);

        status = new JLabel("");
        status.setBounds(10,180,600,25);
        panel.add(status);

        statusText = new JLabel("");
        statusText.setBounds(10,200, 300, 150);
        panel.add(statusText);
        setStatusText();

        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String trade = trade_Text.getText();
        String feed = feed_Text.getText();
        String plant = plant_Text.getText();

        trade = trade.equals("") ? "0" : trade;
        feed = feed.equals("") ? "0" : feed;
        plant = plant.equals("") ? "0" : plant;

        int tradeInt = Integer.parseInt(trade);
        int feedInt = Integer.parseInt(feed);
        int plantInt = Integer.parseInt(plant);

        Logic.submiter(tradeInt, feedInt, plantInt);
    }

    public static void setStatusText() {
        statusText.setText("<html>Hammurabi: I beg to report to you,<br>"
                                +" In Year "+k_year+", UNKNOWN people starved.<br>"
                                +" UNKNOWN people came to the city.<br>"
                                +" The city population is now "+k_peasants+".<br>"
                                +" The city now owns "+k_area+" acres.<br>"
                                +" You harvested "+k_harvest+" bushels.<br>"
                                +" Rats ate UNKNOWN bushels.<br>"
                                +" You now have "+k_grain+" bushels in store.<br>"
                                +" Land is trading at "+k_acre_price+" bushels per acre.</html>");
    }

    public static void textfieldRest(){
        trade_Text.setText("");
        feed_Text.setText("");
        plant_Text.setText("");
        status.setText("");
    }

    public static void setStatus(String Text){
        status.setText(Text);
    }
}