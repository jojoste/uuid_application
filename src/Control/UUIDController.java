package Control;

import Model.Database.Connection.ConnectionMongoDB;
import Model.Database.Connection.ConnectionMySQL;
import Model.Database.Connection.ConnectionPostgres;
import Model.Database.Connection.ConnectionRedis;
import Model.Database.PerformanceMeasurement.PerformanceMeasurementMongoDB;
import Model.Database.PerformanceMeasurement.PerformanceMeasurementMySQL;
import Model.Database.PerformanceMeasurement.PerformanceMeasurementPostgres;
import Model.Database.PerformanceMeasurement.PerformanceMeasurementRedis;
import Model.Database.Truncate.TruncateMongoDB;
import Model.Database.Truncate.TruncateMySQL;
import Model.Database.Truncate.TruncatePostgres;
import Model.Database.Truncate.TruncateRedis;
import View.CreateGraphs;
import View.ViewUUID;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;


public class UUIDController {

    private ViewUUID theView;
    private CreateGraphs myCreateGraphs;


    private PerformanceMeasurementPostgres myPerformanceMeasurementPostgres;
    private PerformanceMeasurementMySQL myPerformanceMeasurementMySQL;
    private PerformanceMeasurementMongoDB myPerformanceMeasurementMongoDB;
    private PerformanceMeasurementRedis myPerformanceMeasurementRedis;
    private Calculations myCalculations;


    private static final String dataEnding = LocalDateTime.now().toString();

    public UUIDController(ViewUUID theView) {
        this.theView = theView;
        myPerformanceMeasurementMySQL = new PerformanceMeasurementMySQL();
        myPerformanceMeasurementPostgres = new PerformanceMeasurementPostgres();
        myPerformanceMeasurementMongoDB = new PerformanceMeasurementMongoDB();
        myPerformanceMeasurementRedis = new PerformanceMeasurementRedis();


        myCreateGraphs = new CreateGraphs(theView);
        myCalculations = new Calculations();


        init();
        addListener();
        checkStatus();

    }

    public void init() {
        createDirectoryForData();
        theView.getCreateIndexGraphButton().setEnabled(false);
        theView.getCreateInsertGraphButton().setEnabled(false);
        theView.getDropIndexGraphButton().setEnabled(false);
        theView.getJoinAfterIndexPerformanceButton().setEnabled(false);
        theView.getJoinPerformanceButton().setEnabled(false);
        theView.getPostgresqlComparisonButton().setEnabled(false);
        theView.getPostgresqlMysqlJoinButton().setEnabled(false);
        theView.getJoinmongodbComparisonButton().setEnabled(false);
        theView.getJoinmysqlComparisonButton().setEnabled(false);
        theView.getPostgreSQLMySQLJoinAfterButton().setEnabled(false);
        initResultPanel();


        truncateAllTables();
        try {
            theView.getJavaFXGraphPanel().setScene(myCreateGraphs.createWelcomeGraph());
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    public void writeInsertCsvFile(PerformanceMeasurementPostgres myPerformanceMeasurementPostgres, PerformanceMeasurementMySQL myPerformanceMeasurementMySQL, PerformanceMeasurementMongoDB myPerformanceMeasurementMongoDB, PerformanceMeasurementRedis myPerformanceMeasurementRedis) throws IOException {
        FileWriter csvInsertWriter = new FileWriter("data/PerformanceMeasurement" + UUIDController.getDataEnding() + "//insertPerformance" + dataEnding + ".csv");
        csvInsertWriter.append("Try");
        csvInsertWriter.append(",");
        csvInsertWriter.append("PostgreSQL Create Random");
        csvInsertWriter.append(",");
        csvInsertWriter.append("MySQL Create Random");
        csvInsertWriter.append(",");
        csvInsertWriter.append("MongoDB Create Random");
        csvInsertWriter.append(",");
        csvInsertWriter.append("Redis Create Random");
        csvInsertWriter.append("\n");
        for (int i = 0; i < myPerformanceMeasurementMongoDB.getMyMongoCreation().length; i++) {
            csvInsertWriter.append(Integer.toString(i + 1));
            csvInsertWriter.append(",");
            csvInsertWriter.append(Long.toString(myPerformanceMeasurementPostgres.getPostgresCreation()[i]));
            csvInsertWriter.append(",");
            csvInsertWriter.append(Long.toString(myPerformanceMeasurementMySQL.getMySQLCreation()[i]));
            csvInsertWriter.append(",");
            csvInsertWriter.append(Long.toString(myPerformanceMeasurementMongoDB.getMyMongoCreation()[i]));
            csvInsertWriter.append(",");
            csvInsertWriter.append(Long.toString(myPerformanceMeasurementRedis.getMyRedisCreation()[i]));
            csvInsertWriter.append("\n");
        }

        csvInsertWriter.flush();
        csvInsertWriter.close();
    }

    public void writeIndexCsvFile(PerformanceMeasurementPostgres myPerformanceMeasurementPostgres, PerformanceMeasurementMySQL myPerformanceMeasurementMySQL, PerformanceMeasurementMongoDB myPerformanceMeasurementMongoDB) throws IOException {
        FileWriter csvIndexWriter = new FileWriter("data/PerformanceMeasurement" + UUIDController.getDataEnding() + "/indexPerformance" + dataEnding + ".csv");
        csvIndexWriter.append("Try");
        csvIndexWriter.append(",");
        csvIndexWriter.append("PostgreSQL Create Index");
        csvIndexWriter.append(",");
        csvIndexWriter.append("MySQL Create Index");
        csvIndexWriter.append(",");
        csvIndexWriter.append("MongoDB Create Index");
        csvIndexWriter.append(",");
        csvIndexWriter.append("PostgreSQL Drop Index");
        csvIndexWriter.append(",");
        csvIndexWriter.append("MySQL Drop Index");
        csvIndexWriter.append(",");
        csvIndexWriter.append("MongoDB Drop Index");
        csvIndexWriter.append("\n");
        for (int i = 0; i < myPerformanceMeasurementPostgres.getPostgresIndexPerformance().length; i++) {
            csvIndexWriter.append(Integer.toString(i + 1));
            csvIndexWriter.append(",");
            csvIndexWriter.append(Long.toString(myPerformanceMeasurementPostgres.getPostgresIndexPerformance()[i]));
            csvIndexWriter.append(",");
            csvIndexWriter.append(Long.toString(myPerformanceMeasurementMySQL.getMySQLIndexPerformance()[i]));
            csvIndexWriter.append(",");
            csvIndexWriter.append(Long.toString(myPerformanceMeasurementMongoDB.getMongoDBIndexPerformance()[i]));
            csvIndexWriter.append(",");
            csvIndexWriter.append(Long.toString(myPerformanceMeasurementPostgres.getPostgresDropIndexPerformance()[i]));
            csvIndexWriter.append(",");
            csvIndexWriter.append(Long.toString(myPerformanceMeasurementMySQL.getMySQLDropIndexPerformance()[i]));
            csvIndexWriter.append(",");
            csvIndexWriter.append(Long.toString(myPerformanceMeasurementMongoDB.getMongoDBDropIndexPerformance()[i]));
            csvIndexWriter.append("\n");
        }

        csvIndexWriter.flush();
        csvIndexWriter.close();
    }

    public void writeJoinCsvFile(PerformanceMeasurementPostgres myPerformanceMeasurementPostgres, PerformanceMeasurementMySQL myPerformanceMeasurementMySQL, PerformanceMeasurementMongoDB myPerformanceMeasurementMongoDB) throws IOException {
        FileWriter csvJoinWriter = new FileWriter("data/PerformanceMeasurement" + UUIDController.getDataEnding() + "/joinPerformance" + dataEnding + ".csv");
        csvJoinWriter.append("Try");
        csvJoinWriter.append(",");
        csvJoinWriter.append("PostgreSQL Join");
        csvJoinWriter.append(",");
        csvJoinWriter.append("MySQL Join");
        csvJoinWriter.append(",");
        csvJoinWriter.append("MongoDB Join");
        csvJoinWriter.append(",");
        csvJoinWriter.append("PostgreSQL Join after Indexing");
        csvJoinWriter.append(",");
        csvJoinWriter.append("MySQL Join after Indexing");
        csvJoinWriter.append(",");
        csvJoinWriter.append("MongoDB Join after Indexing");
        csvJoinWriter.append("\n");
        for (int i = 0; i < myPerformanceMeasurementPostgres.getPostgresIndexPerformance().length; i++) {
            csvJoinWriter.append(Integer.toString(i + 1));
            csvJoinWriter.append(",");
            csvJoinWriter.append(Long.toString(myPerformanceMeasurementPostgres.getPostgresJoinPerformance()[i]));
            csvJoinWriter.append(",");
            csvJoinWriter.append(Long.toString(myPerformanceMeasurementMySQL.getMySQLJoinPerformance()[i]));
            csvJoinWriter.append(",");
            csvJoinWriter.append(Long.toString(myPerformanceMeasurementMongoDB.getMongoDBJoinPerformance()[i]));
            csvJoinWriter.append(",");
            csvJoinWriter.append(Long.toString(myPerformanceMeasurementPostgres.getPostgresJoinAfterIndexPerformance()[i]));
            csvJoinWriter.append(",");
            csvJoinWriter.append(Long.toString(myPerformanceMeasurementMySQL.getMySQLJoinAfterIndexPerformance()[i]));
            csvJoinWriter.append(",");
            csvJoinWriter.append(Long.toString(myPerformanceMeasurementMongoDB.getMongoDBJoinAfterIndexPerformance()[i]));
            csvJoinWriter.append("\n");
        }

        csvJoinWriter.flush();
        csvJoinWriter.close();
    }


    public void checkStatus() {
        if (ConnectionMySQL.connectionMySQLReady() && ConnectionPostgres.connectionPostgresReady() && ConnectionMongoDB.connectionMongoDBReady() && ConnectionRedis.connectionRedisReady()) {
            theView.setGUI_LoggerText("All servers are ready, please proceed.");
        } else {
            theView.setGUI_LoggerText("Please check your servers!");
        }


        if (ConnectionPostgres.connectionPostgresReady()) {
            theView.setGUI_LoggerText("PostgreSQL server is ready!");
        } else {
            theView.setGUI_LoggerText("PostgreSQL server is not ready, please check!!");
        }

        if (ConnectionMySQL.connectionMySQLReady()) {
            theView.setGUI_LoggerText("MySQL server is ready!");
        } else {
            theView.setGUI_LoggerText("MySQL server is not ready, please check!");
        }

        if (ConnectionMongoDB.connectionMongoDBReady()) {
            theView.setGUI_LoggerText("MongoDB server is ready!");
        } else {
            theView.setGUI_LoggerText("MongoDB server is not ready, please check!");
        }

        if (ConnectionRedis.connectionRedisReady()) {
            theView.setGUI_LoggerText("Redis server is ready!");
        } else {
            theView.setGUI_LoggerText("Redis server is not ready, please check!");
        }
        theView.setGUI_LoggerCursorPosition();
    }


    public void addListener() {
        theView.setRandomButtonListener(new RandomButtonListener());
        theView.setCreateInsertGraphButtonListener(new CreateInsertGraphButtonListener());
        theView.setCreateIndexGraphButtonListener(new CreateIndexButtonListener());
        theView.setDropIndexGraphButtonListener(new DropIndexButtonListener());
        theView.setTruncateButtonListener(new TruncateButtonListener());
        theView.setJoinPerformanceButtonListener(new joinPerformanceButtonListener());
        theView.setJoinAfterIndexPerformanceButton(new joinAfterIndexPerformanceButtonListener());
        theView.setPostgresqlMysqlJoinButtonListener(new postgresqlMysqlJoinButtonListener());
        theView.setJoinmongodbComparisonButtonListener(new joinmongodbComparisonButtonListener());
        theView.setJoinmysqlComparisonButtonListener(new joinmysqlComparisonButtonListener());
        theView.setPostgresqlComparisonButton(new postgresqlComparisonButtonListener());
        theView.setPostgreSQLMySQLJoinAfterButtonListener(new PostgreSQLMySQLJoinAfterButtonListener());
    }

    protected class RandomButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    init();
                    myPerformanceMeasurementPostgres.performanceMeasurementPostgres(theView.getSetNumberOfInsertsBoxValue());
                    theView.setGUI_LoggerText("Random PostgreSQL inserts have been created.");
                    theView.setGUI_LoggerCursorPosition();
                    myPerformanceMeasurementMySQL.performanceMeasurementMySQL(theView.getSetNumberOfInsertsBoxValue());
                    theView.setGUI_LoggerText("Random MySQL inserts have been created.");
                    theView.setGUI_LoggerCursorPosition();
                    myPerformanceMeasurementMongoDB.performanceMeasurementMongoDB(theView.getSetNumberOfInsertsBoxValue());
                    theView.setGUI_LoggerText("Random MongoDB inserts have been created.");
                    theView.setGUI_LoggerCursorPosition();
                    try {
                        myPerformanceMeasurementRedis.performanceMeasurementRedis(theView.getSetNumberOfInsertsBoxValue());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    theView.setGUI_LoggerText("Random Redis inserts have been created. ");
                    theView.setGUI_LoggerText("Please proceed to the next step!");
                    theView.setGUI_LoggerCursorPosition();

                    myPerformanceMeasurementMongoDB.setAllCountersToZero();
                    myPerformanceMeasurementMySQL.setAllCountersToZero();
                    myPerformanceMeasurementPostgres.setAllCountersToZero();
                    myPerformanceMeasurementRedis.setAllCountersToZero();

                    try {
                        writeInsertCsvFile(myPerformanceMeasurementPostgres, myPerformanceMeasurementMySQL, myPerformanceMeasurementMongoDB, myPerformanceMeasurementRedis);
                        writeIndexCsvFile(myPerformanceMeasurementPostgres, myPerformanceMeasurementMySQL, myPerformanceMeasurementMongoDB);
                        writeJoinCsvFile(myPerformanceMeasurementPostgres, myPerformanceMeasurementMySQL, myPerformanceMeasurementMongoDB);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    theView.setGUI_LoggerText("The insert performance csv file has been created, you may now compare the  insert performance.");
                    theView.getCreateIndexGraphButton().setEnabled(true);
                    theView.getCreateInsertGraphButton().setEnabled(true);
                    theView.getDropIndexGraphButton().setEnabled(true);
                    theView.getJoinAfterIndexPerformanceButton().setEnabled(true);
                    theView.getJoinPerformanceButton().setEnabled(true);
                    theView.getPostgresqlComparisonButton().setEnabled(true);
                    theView.getPostgresqlMysqlJoinButton().setEnabled(true);
                    theView.getJoinmongodbComparisonButton().setEnabled(true);
                    theView.getJoinmysqlComparisonButton().setEnabled(true);
                    theView.getPostgreSQLMySQLJoinAfterButton().setEnabled(true);
                    try {
                        theView.getJavaFXGraphPanel().setScene(myCreateGraphs.createInsertGraph());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    setInsertPerformanceResults();
                    theView.setGUI_LoggerText("The Insert Graph is now shown.");
                    theView.setGUI_LoggerCursorPosition();
                    theView.getCreateRandomButton().setText("Create Random");
                    theView.getCreateRandomButton().setEnabled(true);
                    theView.getTruncateButton().setEnabled(true);
                }
            }).start();
            theView.getTruncateButton().setEnabled(false);
            theView.setGUI_LoggerText("Please wait, this may take several minutes...");
            theView.getCreateRandomButton().setText("Please wait...");
            theView.getCreateRandomButton().setEnabled(false);

        }

    }

    protected class CreateInsertGraphButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                theView.getJavaFXGraphPanel().setScene(myCreateGraphs.createInsertGraph());
                theView.setGUI_LoggerText("The Insert Graph is now shown.");
                theView.setGUI_LoggerText("Please note: the first insert operation is omitted.");
                setInsertPerformanceResults();
                printFourResultsInLogger(myPerformanceMeasurementPostgres.getPostgresCreation(), myPerformanceMeasurementMySQL.getMySQLCreation(), myPerformanceMeasurementMongoDB.getMyMongoCreation(), myPerformanceMeasurementRedis.getMyRedisCreation(), "PostgreSQL insert", "MySQL insert", "MongoDB insert", "Redis insert");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    protected class CreateIndexButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                theView.getJavaFXGraphPanel().setScene(myCreateGraphs.createIndexGraph());
                theView.setGUI_LoggerText("The Index Graph is now shown.");
                createIndexPerformancResults();
                printThreeResultsInLogger(myPerformanceMeasurementPostgres.getPostgresIndexPerformance(), myPerformanceMeasurementMySQL.getMySQLIndexPerformance(), myPerformanceMeasurementMongoDB.getMongoDBIndexPerformance(), "PostgreSQL index", "MySQL index", "MongoDB index");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    protected class DropIndexButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                theView.getJavaFXGraphPanel().setScene(myCreateGraphs.createDropIndexGraph());
                theView.setGUI_LoggerText("The Drop Index Graph is now shown.");
                dropIndexPerformanceResults();
                printThreeResultsInLogger(myPerformanceMeasurementPostgres.getPostgresDropIndexPerformance(), myPerformanceMeasurementMySQL.getMySQLDropIndexPerformance(), myPerformanceMeasurementMongoDB.getMongoDBDropIndexPerformance(), "PostgreSQL drop index", "MySQL drop index", "MongoDB drop index");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    protected class joinPerformanceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                theView.getJavaFXGraphPanel().setScene(myCreateGraphs.createJoinGraph());
                theView.setGUI_LoggerText("The Join Graph is now shown.");
                joinPerformanceResults();
                printThreeResultsInLogger(myPerformanceMeasurementPostgres.getPostgresJoinPerformance(), myPerformanceMeasurementMySQL.getMySQLJoinPerformance(), myPerformanceMeasurementMongoDB.getMongoDBJoinPerformance(), "PostgreSQL join", "MySQL join", "MongoDB join");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    protected class joinAfterIndexPerformanceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                theView.getJavaFXGraphPanel().setScene(myCreateGraphs.createJoinAfterIndexGraph());
                theView.setGUI_LoggerText("The Join after Index Graph is now shown.");
                joinAfterIndexPerformanceResults();
                printThreeResultsInLogger(myPerformanceMeasurementPostgres.getPostgresJoinAfterIndexPerformance(), myPerformanceMeasurementMySQL.getMySQLJoinAfterIndexPerformance(), myPerformanceMeasurementMongoDB.getMongoDBJoinAfterIndexPerformance(), "PostgreSQL join after index", "MySQL join after index", "MongoDB join after index");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    protected class postgresqlMysqlJoinButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                theView.getJavaFXGraphPanel().setScene(myCreateGraphs.createPostgresMysqlJoinScene());
                theView.setGUI_LoggerText("The PostgreSQL and MySQL Join Performance is now shown.");
                joinPerformanceResults();
                printTwoResultsInLogger(myPerformanceMeasurementPostgres.getPostgresJoinPerformance(), myPerformanceMeasurementMySQL.getMySQLJoinPerformance(), "PostgreSQL join", "MySQL join");

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    protected class postgresqlComparisonButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                theView.getJavaFXGraphPanel().setScene(myCreateGraphs.createPostgresqlComparisonGraph());
                theView.setGUI_LoggerText("The PostgreSQL join performance before and after indexing is now shown.");
                joinPerformanceResults();
                printTwoResultsInLogger(myPerformanceMeasurementPostgres.getPostgresJoinPerformance(), myPerformanceMeasurementPostgres.getPostgresJoinAfterIndexPerformance(), "PostgreSQL join before index", "PostgreSQL join after index");

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    protected class joinmysqlComparisonButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                theView.getJavaFXGraphPanel().setScene(myCreateGraphs.createMysqlComparisonGraph());
                theView.setGUI_LoggerText("The MySQL join performance before and after indexing is now shown.");
                joinPerformanceResults();
                printTwoResultsInLogger(myPerformanceMeasurementMySQL.getMySQLJoinPerformance(), myPerformanceMeasurementMySQL.getMySQLJoinAfterIndexPerformance(), "MySQL join before index", "MySQL join after index");

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    protected class joinmongodbComparisonButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                theView.getJavaFXGraphPanel().setScene(myCreateGraphs.createMongodbComparisonGraph());
                theView.setGUI_LoggerText("The MongoDB join performance before and after indexing is now shown.");
                joinPerformanceResults();
                printTwoResultsInLogger(myPerformanceMeasurementMongoDB.getMongoDBJoinPerformance(), myPerformanceMeasurementMongoDB.getMongoDBJoinAfterIndexPerformance(), "MongoDB join before index", "MongoDB join after index");


            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    protected class PostgreSQLMySQLJoinAfterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                theView.getJavaFXGraphPanel().setScene(myCreateGraphs.createPostgresMysqlJoinAfterScene());
                theView.setGUI_LoggerText("The PostgreSQL and MySQL Join Performance after indexing is now shown.");
                joinAfterIndexPerformanceResults();
                printTwoResultsInLogger(myPerformanceMeasurementPostgres.getPostgresJoinAfterIndexPerformance(), myPerformanceMeasurementMySQL.getMySQLJoinAfterIndexPerformance(), "PostgreSQL after index", "MySQL after index");

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }


    protected class TruncateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            truncateAllTables();
        }
    }

    public static String getDataEnding() {
        return dataEnding;
    }

    private void truncateAllTables() {
        TruncatePostgres.TruncatePostgresTable("public.instance_object");
        TruncatePostgres.TruncatePostgresTable("public.relationclass");
        TruncatePostgres.TruncatePostgresTable("public.class");
        TruncatePostgres.TruncatePostgresTable("public.scene_type");
        TruncatePostgres.TruncatePostgresTable("public.role");
        TruncatePostgres.TruncatePostgresTable("public.metaobject");
        TruncatePostgres.TruncatePostgresTable("public.class_instance");
        TruncatePostgres.TruncatePostgresTable("public.role_instance");
        TruncatePostgres.TruncatePostgresTable("public.scene_instance");
        theView.setGUI_LoggerText("PostgreSQL tables have been cleared");
        TruncateMySQL.TruncateMySQLTable("test.instance_object");
        TruncateMySQL.TruncateMySQLTable("test.relationclass");
        TruncateMySQL.TruncateMySQLTable("test.class");
        TruncateMySQL.TruncateMySQLTable("test.scene_type");
        TruncateMySQL.TruncateMySQLTable("test.role");
        TruncateMySQL.TruncateMySQLTable("test.metaobject");
        TruncateMySQL.TruncateMySQLTable("test.class_instance");
        TruncateMySQL.TruncateMySQLTable("test.role_instance");
        TruncateMySQL.TruncateMySQLTable("test.scene_instance");
        theView.setGUI_LoggerText("MySQL tables have been cleared.");
        TruncateMongoDB.TruncateMongoDB();
        theView.setGUI_LoggerText("MongoDB tables have been cleared.");
        TruncateRedis.TruncateRedis();
        theView.setGUI_LoggerText("Redis database has been cleared.");
    }

    private void createDirectoryForData() {
        File theDir = new File("data/PerformanceMeasurement" + dataEnding);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
    }

    private void initResultPanel() {
        theView.getMeasurementTitle().setText("Your Results");
        theView.getPostgresqlResultTitle().setText("PostgreSQL:");
        theView.getPostgresqlResultResults().setText("-");
        theView.getMysqlResultTitle().setText("MySQL:");
        theView.getMysqlResultResults().setText("-");
        theView.getMongodbResultTitle().setText("MongoDB:");
        theView.getMongodbResultResults().setText("-");
        theView.getRedisTotalTitle().setText("Redis:");
        theView.getRedisTotalResult().setText("-");
        theView.getPostgresTotalTitle().setText("PostgreSQL Total Execution Time:");
        theView.getPostgresTotalResult().setText("-");
        theView.getMysqlTotalTitle().setText("MySQL Total Execution Time:");
        theView.getMysqlTotalResult().setText("-");
        theView.getMongodbTotalTitle().setText("MongoDB Total Execution Time:");
        theView.getMongodbTotalResult().setText("-");
        theView.getRedisExeTitle().setText("Redis Total Execution Time:");
        theView.getRedisExeResult().setText("-");
    }

    private void setInsertPerformanceResults() {
        theView.getMeasurementTitle().setText("Your Insert Results");
        theView.getPostgresqlResultTitle().setText("Average Time for PostgreSQL:");
        theView.getPostgresqlResultResults().setText(myCalculations.getAverage(myPerformanceMeasurementPostgres.getPostgresCreation()) + " seconds.");
        theView.getMysqlResultTitle().setText("Average Time for MySQL:");
        theView.getMysqlResultResults().setText(myCalculations.getAverage(myPerformanceMeasurementMySQL.getMySQLCreation()) + " seconds.");
        theView.getMongodbResultTitle().setText("Average Time for MongoDB:");
        theView.getMongodbResultResults().setText(myCalculations.getAverage(myPerformanceMeasurementMongoDB.getMyMongoCreation()) + " seconds.");
        theView.getRedisTotalTitle().setText("Average Time for Redis:");
        theView.getRedisTotalResult().setText(myCalculations.getAverage(myPerformanceMeasurementRedis.getMyRedisCreation()) + " seconds.");
        theView.getPostgresTotalResult().setText(myCalculations.toSeconds(myPerformanceMeasurementPostgres.getTotalTime()) + " seconds.");
        theView.getMysqlTotalResult().setText(myCalculations.toSeconds(myPerformanceMeasurementMySQL.getTotalTime()) + " seconds. ");
        theView.getMongodbTotalResult().setText(myCalculations.toSeconds(myPerformanceMeasurementMongoDB.getTotalTime()) + " seconds. ");
        theView.getRedisExeResult().setText(myCalculations.toSeconds(myPerformanceMeasurementRedis.getTotalTime()) + " seconds.");

    }

    private void createIndexPerformancResults() {
        theView.getMeasurementTitle().setText("Your Create Index Results");
        theView.getPostgresqlResultTitle().setText("Average Time for PostgreSQL:");
        theView.getPostgresqlResultResults().setText(myCalculations.getAverage(myPerformanceMeasurementPostgres.getPostgresIndexPerformance()) + " seconds.");
        theView.getMysqlResultTitle().setText("Average Time for MySQL:");
        theView.getMysqlResultResults().setText(myCalculations.getAverage(myPerformanceMeasurementMySQL.getMySQLIndexPerformance()) + " seconds.");
        theView.getMongodbResultTitle().setText("Average Time for MongoDB:");
        theView.getMongodbResultResults().setText(myCalculations.getAverage(myPerformanceMeasurementMongoDB.getMongoDBIndexPerformance()) + " seconds.");
        theView.getRedisTotalTitle().setText(" ");
        theView.getRedisTotalResult().setText(" ");
        theView.getRedisExeResult().setText("Please note: Redis only measures insert performance.");

    }

    private void dropIndexPerformanceResults() {
        theView.getMeasurementTitle().setText("Your Drop Index Results");
        theView.getPostgresqlResultTitle().setText("Average Time for PostgreSQL:");
        theView.getPostgresqlResultResults().setText(myCalculations.getAverage(myPerformanceMeasurementPostgres.getPostgresDropIndexPerformance()) + " seconds.");
        theView.getMysqlResultTitle().setText("Average Time for MySQL:");
        theView.getMysqlResultResults().setText(myCalculations.getAverage(myPerformanceMeasurementMySQL.getMySQLDropIndexPerformance()) + " seconds.");
        theView.getMongodbResultTitle().setText("Average Time for MongoDB:");
        theView.getMongodbResultResults().setText(myCalculations.getAverage(myPerformanceMeasurementMongoDB.getMongoDBDropIndexPerformance()) + " seconds.");
        theView.getRedisTotalResult().setText(" ");
        theView.getRedisTotalTitle().setText(" ");
        theView.getRedisExeResult().setText("Please note: Redis only measures insert performance.");
    }

    private void joinPerformanceResults() {
        theView.getMeasurementTitle().setText("Your Join (before Index) Results");
        theView.getPostgresqlResultTitle().setText("Average Time for PostgreSQL:");
        theView.getPostgresqlResultResults().setText(myCalculations.getAverage(myPerformanceMeasurementPostgres.getPostgresJoinPerformance()) + " seconds.");
        theView.getMysqlResultTitle().setText("Average Time for MySQL:");
        theView.getMysqlResultResults().setText(myCalculations.getAverage(myPerformanceMeasurementMySQL.getMySQLJoinPerformance()) + " seconds.");
        theView.getMongodbResultTitle().setText("Average Time for MongoDB:");
        theView.getMongodbResultResults().setText(myCalculations.getAverage(myPerformanceMeasurementMongoDB.getMongoDBJoinPerformance()) + " seconds.");
        theView.getRedisTotalResult().setText(" ");
        theView.getRedisTotalTitle().setText(" ");
        theView.getRedisExeResult().setText("Please note: Redis only measures insert performance.");
    }

    private void joinAfterIndexPerformanceResults() {
        theView.getMeasurementTitle().setText("Your Join after Index Results");
        theView.getPostgresqlResultTitle().setText("Average Time for PostgreSQL:");
        theView.getPostgresqlResultResults().setText(myCalculations.getAverage(myPerformanceMeasurementPostgres.getPostgresJoinAfterIndexPerformance()) + " seconds.");
        theView.getMysqlResultTitle().setText("Average Time for MySQL:");
        theView.getMysqlResultResults().setText(myCalculations.getAverage(myPerformanceMeasurementMySQL.getMySQLJoinAfterIndexPerformance()) + " seconds.");
        theView.getMongodbResultTitle().setText("Average Time for MongoDB:");
        theView.getMongodbResultResults().setText(myCalculations.getAverage(myPerformanceMeasurementMongoDB.getMongoDBJoinAfterIndexPerformance()) + " seconds.");
        theView.getRedisTotalResult().setText(" ");
        theView.getRedisTotalTitle().setText(" ");
        theView.getRedisExeResult().setText("Please note: Redis only measures insert performance.");

    }

    private void printFourResultsInLogger(long[] myMeasurement1, long[] myMeasurement2, long[] myMeasurement3, long[] myMeasurement4, String myMeasurementName1, String myMeasurementName2, String myMeasurementName3, String myMeasurementName4
    ) {
        for (int i = 0; i < myMeasurement1.length; i++) {
            int j = i + 1;
            theView.setGUI_LoggerText("Time needed for " + myMeasurementName1 + " " + j + " : " + myMeasurement1[i] / 1e9 + "\t" + myMeasurementName2 + " " + j + " : " + myMeasurement2[i] / 1e9 + "\t" + myMeasurementName3 + " " + j + " : " + myMeasurement3[i] / 1e9 + "\t" + myMeasurementName4 + " " + j + " : " + myMeasurement4[i] / 1e9);
        }
        theView.setGUI_LoggerText("All measurements are in seconds.");
        theView.setGUI_LoggerCursorPosition();
    }


    private void printThreeResultsInLogger(long[] myMeasurement1, long[] myMeasurement2, long[] myMeasurement3, String myMeasurementName1, String myMeasurementName2, String myMeasurementName3) {
        for (int i = 0; i < myMeasurement1.length; i++) {
            int j = i + 1;
            theView.setGUI_LoggerText("Time needed for " + myMeasurementName1 + " " + j + " : " + myMeasurement1[i] / 1e9 + "\t" + myMeasurementName2 + " " + j + " : " + myMeasurement2[i] / 1e9 + "\t" + myMeasurementName3 + " " + j + " : " + myMeasurement3[i] / 1e9);
        }
        theView.setGUI_LoggerText("All measurements are in seconds.");
        theView.setGUI_LoggerCursorPosition();
    }

    private void printTwoResultsInLogger(long[] myMeasurement1, long[] myMeasurement2, String myMeasurementName1, String myMeasurementName2) {
        for (int i = 0; i < myMeasurement1.length; i++) {
            int j = i + 1;
            theView.setGUI_LoggerText("Time needed for " + myMeasurementName1 + " " + j + " : " + myMeasurement1[i] / 1e9 + "\t" + myMeasurementName2 + " " + j + " : " + myMeasurement2[i] / 1e9);
        }
        theView.setGUI_LoggerText("All measurements are in seconds.");
        theView.setGUI_LoggerCursorPosition();
    }
}


