package View;

import Model.Database.Connection.ConnectionMongoDB;
import Model.Database.Connection.ConnectionMySQL;
import Model.Database.Connection.ConnectionPostgres;
import Model.Database.Connection.ConnectionRedis;
import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

public class ViewUUID extends JFrame {
    private JLabel postgresStatus;
    private JLabel mysqlStatus;
    private JPanel mongodbStatusPanel;
    private JTextArea GUI_Logger;
    private JButton CreateRandomButton;
    private JButton CreateInsertGraphButton;
    private JButton CreateIndexGraphButton;
    private JPanel MainPanel;
    private JPanel StatusPanel;
    private JLabel mongodbStatus;
    private JPanel postgresStatusPanel;
    private JPanel mysqlStatusPanel;
    private JPanel LoggerPanel;
    private JPanel WelcomePanel;
    private JLabel WelcomeLabel;
    private JPanel CreateRandomPanel;
    private JLabel CreateRandomLabel;
    private JPanel InsertPerformancePanel;
    private JPanel IndexPerformance;
    private JLabel CreateIndexGraphLabel;
    private JButton TruncateButton;
    private JPanel TruncatePanel;
    private JLabel TruncateLabel;
    private JLabel LoggerTitle;
    private JScrollPane scrollPaneGUI;
    private JFXPanel JavaFXGraphPanel;
    private JPanel DropIndexPerformancePanel;
    private JLabel DropIndexBraphLabel;
    private JButton DropIndexGraphButton;
    private JLabel joinAfterIndexLabel;
    private JButton joinAfterIndexPerformanceButton;
    private JPanel JoinAfterIndexPanel;
    private JButton joinPerformanceButton;
    private JLabel joinLabel;
    private JPanel JoinPanel;
    private JPanel ResultPanel;
    private JLabel MeasurementTitle;
    private JComboBox setNumberOfInserts;
    private JLabel postgresqlResultTitle;
    private JLabel postgresqlResultResults;
    private JLabel mysqlResultTitle;
    private JLabel mysqlResultResults;
    private JLabel mongodbResultTitle;
    private JLabel mongodbResultResults;
    private JLabel explanationLabel;
    private JLabel postgresTotalTitle;
    private JLabel postgresTotalResult;
    private JLabel mysqlTotalTitle;
    private JLabel mysqlTotalResult;
    private JLabel mongodbTotalTitle;
    private JLabel mongodbTotalResult;
    private JButton postgresqlMysqlJoinButton;
    private JButton joinmongodbComparisonButton;
    private JButton joinmysqlComparisonButton;
    private JButton postgresqlComparisonButton;
    private JButton postgreSQLMySQLJoinAfterButton;
    private JLabel redisTotalTitle;
    private JLabel redisTotalResult;
    private JPanel redisStatusPanel;
    private JLabel redisStatus;
    private JLabel redisExeTitle;
    private JLabel redisExeResult;
    private JScrollPane myLoggerScrollBar;


    public ViewUUID() {
        JFrame frame = new JFrame("UUID Performance Measurement");
        frame.setContentPane(MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /*public static void main(String[] args) {
        JFrame frame = new JFrame("ViewUUID");
        frame.setContentPane(new MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }*/

    public void setGUI_LoggerText(String myString) {
        GUI_Logger.append("\n" + myString);
    }

    public JLabel getRedisTotalTitle() {
        return redisTotalTitle;
    }

    public JLabel getRedisTotalResult() {
        return redisTotalResult;
    }

    public JButton getCreateRandomButton() {
        return CreateRandomButton;
    }

    public JButton getTruncateButton() {
        return TruncateButton;
    }

    public JLabel getRedisExeTitle() {
        return redisExeTitle;
    }

    public JLabel getRedisExeResult() {
        return redisExeResult;
    }

    public JLabel getPostgresStatus() {
        return postgresStatus;
    }

    public JButton getPostgresqlMysqlJoinButton() {
        return postgresqlMysqlJoinButton;
    }

    public JButton getJoinmongodbComparisonButton() {
        return joinmongodbComparisonButton;
    }

    public JButton getJoinmysqlComparisonButton() {
        return joinmysqlComparisonButton;
    }

    public JButton getPostgresqlComparisonButton() {
        return postgresqlComparisonButton;
    }

    public JLabel getPostgresTotalTitle() {
        return postgresTotalTitle;
    }

    public JLabel getPostgresTotalResult() {
        return postgresTotalResult;
    }

    public JLabel getMysqlTotalTitle() {
        return mysqlTotalTitle;
    }

    public JLabel getMysqlTotalResult() {
        return mysqlTotalResult;
    }

    public JLabel getMongodbTotalTitle() {
        return mongodbTotalTitle;
    }

    public JLabel getMongodbTotalResult() {
        return mongodbTotalResult;
    }

    public JLabel getMeasurementTitle() {
        return MeasurementTitle;
    }

    public JLabel getPostgresqlResultTitle() {
        return postgresqlResultTitle;
    }

    public JLabel getPostgresqlResultResults() {
        return postgresqlResultResults;
    }

    public JLabel getMysqlResultTitle() {
        return mysqlResultTitle;
    }

    public JLabel getMysqlResultResults() {
        return mysqlResultResults;
    }

    public JLabel getMongodbResultTitle() {
        return mongodbResultTitle;
    }

    public JLabel getMongodbResultResults() {
        return mongodbResultResults;
    }

    public JButton getCreateIndexGraphButton() {
        return CreateIndexGraphButton;
    }

    public JButton getCreateInsertGraphButton() {
        return CreateInsertGraphButton;
    }

    public JButton getDropIndexGraphButton() {
        return DropIndexGraphButton;
    }

    public JButton getJoinAfterIndexPerformanceButton() {
        return joinAfterIndexPerformanceButton;
    }

    public JButton getPostgreSQLMySQLJoinAfterButton() {
        return postgreSQLMySQLJoinAfterButton;
    }

    public JButton getJoinPerformanceButton() {
        return joinPerformanceButton;
    }

    public void setGUI_LoggerCursorPosition() {
        GUI_Logger.setCaretPosition(GUI_Logger.getDocument().getLength());
    }

    public JFXPanel getJavaFXGraphPanel() {
        return JavaFXGraphPanel;
    }

    public int getSetNumberOfInsertsBoxValue() {
        return new Integer(setNumberOfInserts.getSelectedItem().toString());
    }

    private void createUIComponents() {
        mongodbStatusPanel = new JPanel();
        mongodbStatus = new JLabel();

        postgresStatusPanel = new JPanel();
        postgresStatus = new JLabel();

        mysqlStatusPanel = new JPanel();
        mysqlStatus = new JLabel();

        redisStatusPanel = new JPanel();
        redisStatus = new JLabel();

        WelcomePanel = new JPanel();

        LoggerPanel = new JPanel();

        GUI_Logger = new JTextArea();
        scrollPaneGUI = new JScrollPane();

        GUI_Logger = new JTextArea();

        ArrayList<JButton> myButtons = new ArrayList<>();
        myButtons.add(CreateRandomButton = new JButton());
        myButtons.add(CreateInsertGraphButton = new JButton());
        myButtons.add(CreateIndexGraphButton = new JButton());
        myButtons.add(TruncateButton = new JButton());
        myButtons.add(DropIndexGraphButton = new JButton());
        myButtons.add(joinAfterIndexPerformanceButton = new JButton());
        myButtons.add(joinPerformanceButton = new JButton());
        myButtons.add(joinmysqlComparisonButton = new JButton());
        myButtons.add(joinmongodbComparisonButton = new JButton());
        myButtons.add(postgresqlComparisonButton = new JButton());
        myButtons.add(postgresqlMysqlJoinButton = new JButton());
        myButtons.add(postgreSQLMySQLJoinAfterButton = new JButton());


        if (ConnectionMySQL.connectionMySQLReady() && ConnectionPostgres.connectionPostgresReady() && ConnectionMongoDB.connectionMongoDBReady()) {
            for (JButton button : myButtons) {
                button.setEnabled(true);
            }
        } else {
            for (JButton button : myButtons) {
                button.setEnabled(false);
                button.setBackground(Color.red);
                button.setOpaque(true);
            }
        }

        if (ConnectionMongoDB.connectionMongoDBReady()) {
            mongodbStatus.setBackground(Color.green);
            mongodbStatusPanel.setBackground(Color.green);
        } else {
            mongodbStatus.setBackground(Color.red);
            mongodbStatusPanel.setBackground(Color.red);
        }

        if (ConnectionPostgres.connectionPostgresReady()) {
            postgresStatusPanel.setBackground(Color.green);
            postgresStatus.setBackground(Color.green);
        } else {
            postgresStatus.setBackground(Color.red);
            postgresStatusPanel.setBackground(Color.red);
        }

        if (ConnectionMySQL.connectionMySQLReady()) {
            mysqlStatusPanel.setBackground(Color.green);
            mysqlStatus.setBackground(Color.green);
        } else {
            mysqlStatusPanel.setBackground(Color.red);
            mysqlStatus.setBackground(Color.red);
        }

        if (ConnectionRedis.connectionRedisReady()) {
            redisStatusPanel.setBackground(Color.green);
            redisStatus.setBackground(Color.green);
        } else {
            redisStatusPanel.setBackground(Color.red);
            redisStatus.setBackground(Color.red);
        }

        LoggerPanel.setBackground(Color.white);

        WelcomePanel.setBackground(Color.white);


        scrollPaneGUI.setSize(300, 600);

        JavaFXGraphPanel = new JFXPanel();

        Border myGenericBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        ResultPanel = new JPanel();
        ResultPanel.setBorder(myGenericBorder);
        ResultPanel.setBackground(Color.white);
        MeasurementTitle = new JLabel();

        MeasurementTitle.setBackground(Color.black);

        postgresqlResultTitle = new JLabel();
        postgresqlResultResults = new JLabel();

        mysqlResultTitle = new JLabel();
        mysqlResultResults = new JLabel();

        mongodbResultTitle = new JLabel();
        mongodbResultResults = new JLabel();


        explanationLabel = new JLabel();

        postgresTotalResult = new JLabel();
        postgresTotalTitle = new JLabel();
        mysqlTotalResult = new JLabel();
        mysqlTotalTitle = new JLabel();
        mongodbTotalResult = new JLabel();
        mongodbTotalTitle = new JLabel();
        redisTotalResult = new JLabel();
        redisTotalTitle = new JLabel();
        redisExeResult = new JLabel();
        redisExeTitle = new JLabel();


    }

    public void setRandomButtonListener(ActionListener l) {
        this.CreateRandomButton.addActionListener(l);
    }

    public void setCreateInsertGraphButtonListener(ActionListener l) {
        this.CreateInsertGraphButton.addActionListener(l);
    }

    public void setDropIndexGraphButtonListener(ActionListener l) {
        this.DropIndexGraphButton.addActionListener(l);
    }

    public void setCreateIndexGraphButtonListener(ActionListener l) {
        this.CreateIndexGraphButton.addActionListener(l);
    }

    public void setPostgresqlMysqlJoinButtonListener(ActionListener l) {
        this.postgresqlMysqlJoinButton.addActionListener(l);
    }

    public void setJoinmongodbComparisonButtonListener(ActionListener l) {
        this.joinmongodbComparisonButton.addActionListener(l);
    }

    public void setJoinmysqlComparisonButtonListener(ActionListener l) {
        this.joinmysqlComparisonButton.addActionListener(l);
    }

    public void setPostgresqlComparisonButton(ActionListener l) {
        this.postgresqlComparisonButton.addActionListener(l);
    }

    public void setPostgreSQLMySQLJoinAfterButtonListener(ActionListener l) {
        this.postgreSQLMySQLJoinAfterButton.addActionListener(l);
    }

    public void setTruncateButtonListener(ActionListener l) {
        this.TruncateButton.addActionListener(l);
    }

    public void setJoinPerformanceButtonListener(ActionListener l) {
        this.joinPerformanceButton.addActionListener(l);
    }

    public void setJoinAfterIndexPerformanceButton(ActionListener l) {
        this.joinAfterIndexPerformanceButton.addActionListener(l);
    }


}
