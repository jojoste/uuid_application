package View;

import Control.UUIDController;
import Model.Database.PerformanceMeasurement.PerformanceMeasurementPostgres;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.FileReader;
import java.io.IOException;

public class CreateGraphs {

    final private int graphWidth = 800;
    final private int graphHeight = 400;

    private String yAxisLabel = "Time needed [nanoseconds]";
    private String xAxisLabelPerformance = "Attempt Number";

    private ViewUUID theView;

    public CreateGraphs(ViewUUID theView) {
        this.theView = theView;
    }

    public Scene createInsertGraph() throws Exception {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Insertion of batches, each " + theView.getSetNumberOfInsertsBoxValue() * 21 / PerformanceMeasurementPostgres.getintervall() + " inserts.");
        yAxis.setLabel(yAxisLabel);


        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Insert Random Data");
        XYChart.Series postgreSQLseries = new XYChart.Series();
        postgreSQLseries.setName("PostgreSQL Inserts    ");
        XYChart.Series mySQLSeries = new XYChart.Series();
        mySQLSeries.setName("MySQL Inserts");
        XYChart.Series mongoDBSeries = new XYChart.Series();
        mongoDBSeries.setName("MongoDB Inserts");
        XYChart.Series redisSeries = new XYChart.Series();
        redisSeries.setName("Redis Inserts");


        try (CSVReader dataReader = new CSVReader(new FileReader("data/PerformanceMeasurement" + UUIDController.getDataEnding() + "/insertPerformance" + UUIDController.getDataEnding() + ".csv"))) {
            String[] nextLine;
            dataReader.readNext();
            dataReader.readNext();
            while ((nextLine = dataReader.readNext()) != null) {
                Integer attempt = Integer.valueOf(nextLine[0]);
                long timeNeededPostgreSQL = Long.parseLong(nextLine[1]);
                postgreSQLseries.getData().add(new XYChart.Data(attempt, timeNeededPostgreSQL));
                long timeNeededMySQL = Long.parseLong(nextLine[2]);
                mySQLSeries.getData().add(new XYChart.Data(attempt, timeNeededMySQL));
                long timeNeededMongoDB = Long.parseLong(nextLine[3]);
                mongoDBSeries.getData().add(new XYChart.Data(attempt, timeNeededMongoDB));
                long timeNeededRedis = Long.parseLong(nextLine[4]);
                redisSeries.getData().add(new XYChart.Data(attempt, timeNeededRedis));

            }
        }
        lineChart.getData().add(postgreSQLseries);
        lineChart.getData().add(mySQLSeries);
        lineChart.getData().add(mongoDBSeries);
        lineChart.getData().add(redisSeries);


        return new Scene(lineChart, graphWidth, graphHeight);
    }

    public Scene createIndexGraph() throws Exception {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(xAxisLabelPerformance);
        yAxis.setLabel(yAxisLabel);

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Index Databases");
        XYChart.Series postgreSQLseries = new XYChart.Series();
        postgreSQLseries.setName("PostgreSQL indexing    ");
        XYChart.Series mySQLSeries = new XYChart.Series();
        mySQLSeries.setName("MySQL indexing");
        XYChart.Series mongoDBSeries = new XYChart.Series();
        mongoDBSeries.setName("MongoDB indexing");

        try (CSVReader dataReader = new CSVReader(new FileReader("data/PerformanceMeasurement" + UUIDController.getDataEnding() + "/indexPerformance" + UUIDController.getDataEnding() + ".csv"))) {
            readDataFirstSet(postgreSQLseries, mySQLSeries, mongoDBSeries, dataReader);
        }
        lineChart.getData().add(postgreSQLseries);
        lineChart.getData().add(mySQLSeries);
        lineChart.getData().add(mongoDBSeries);

        return new Scene(lineChart, graphWidth, graphHeight);
    }

    public Scene createDropIndexGraph() throws Exception {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(xAxisLabelPerformance);
        yAxis.setLabel(yAxisLabel);

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Drop Index Databases");
        XYChart.Series postgreSQLseries = new XYChart.Series();
        postgreSQLseries.setName("PostgreSQL dropping index    ");
        XYChart.Series mySQLSeries = new XYChart.Series();
        mySQLSeries.setName("MySQL dropping index");
        XYChart.Series mongoDBSeries = new XYChart.Series();
        mongoDBSeries.setName("MongoDB dropping index");

        try (CSVReader dataReader = new CSVReader(new FileReader("data/PerformanceMeasurement" + UUIDController.getDataEnding() + "/indexPerformance" + UUIDController.getDataEnding() + ".csv"))) {
            readDataSecondSet(postgreSQLseries, mySQLSeries, mongoDBSeries, dataReader);
        }
        lineChart.getData().add(postgreSQLseries);
        lineChart.getData().add(mySQLSeries);
        lineChart.getData().add(mongoDBSeries);


        return new Scene(lineChart, graphWidth, graphHeight);
    }

    public Scene createJoinGraph() throws Exception {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(xAxisLabelPerformance);
        yAxis.setLabel(yAxisLabel);

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Join Performance (before Indexing)");
        XYChart.Series postgreSQLseries = new XYChart.Series();
        postgreSQLseries.setName("PostgreSQL join    ");
        XYChart.Series mySQLSeries = new XYChart.Series();
        mySQLSeries.setName("MySQL join");
        XYChart.Series mongoDBSeries = new XYChart.Series();
        mongoDBSeries.setName("MongoDB join");

        try (CSVReader dataReader = new CSVReader(new FileReader("data/PerformanceMeasurement" + UUIDController.getDataEnding() + "/joinPerformance" + UUIDController.getDataEnding() + ".csv"))) {
            readDataFirstSet(postgreSQLseries, mySQLSeries, mongoDBSeries, dataReader);
        }
        lineChart.getData().add(postgreSQLseries);
        lineChart.getData().add(mySQLSeries);
        lineChart.getData().add(mongoDBSeries);


        return new Scene(lineChart, graphWidth, graphHeight);
    }

    public Scene createPostgresMysqlJoinScene() throws Exception {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(xAxisLabelPerformance);
        yAxis.setLabel(yAxisLabel);

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Join Performance (before Indexing)");
        XYChart.Series postgreSQLseries = new XYChart.Series();
        postgreSQLseries.setName("PostgreSQL join    ");
        XYChart.Series mySQLSeries = new XYChart.Series();
        mySQLSeries.setName("MySQL join");


        try (CSVReader dataReader = new CSVReader(new FileReader("data/PerformanceMeasurement" + UUIDController.getDataEnding() + "/joinPerformance" + UUIDController.getDataEnding() + ".csv"))) {
            String[] nextLine;
            dataReader.readNext();
            while ((nextLine = dataReader.readNext()) != null) {
                Integer attempt = Integer.valueOf(nextLine[0]);
                long timeNeededPostgreSQL = Long.parseLong(nextLine[1]);
                postgreSQLseries.getData().add(new XYChart.Data(attempt, timeNeededPostgreSQL));
                long timeNeededMySQL = Long.parseLong(nextLine[2]);
                mySQLSeries.getData().add(new XYChart.Data(attempt, timeNeededMySQL));
            }
        }
        lineChart.getData().add(postgreSQLseries);
        lineChart.getData().add(mySQLSeries);


        return new Scene(lineChart, graphWidth, graphHeight);


    }

    public Scene createPostgresqlComparisonGraph() throws Exception {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(xAxisLabelPerformance);
        yAxis.setLabel(yAxisLabel);

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Join Performance PostgreSQL before/after indexing");
        XYChart.Series postgreSQLseriesBeforeIndexing = new XYChart.Series();
        postgreSQLseriesBeforeIndexing.setName("PostgreSQL join before indexing    ");
        XYChart.Series postgreSQLseriesJoinAfterIndexing = new XYChart.Series();
        postgreSQLseriesJoinAfterIndexing.setName("PostgreSQL join after indexing");


        try (CSVReader dataReader = new CSVReader(new FileReader("data/PerformanceMeasurement" + UUIDController.getDataEnding() + "/joinPerformance" + UUIDController.getDataEnding() + ".csv"))) {
            String[] nextLine;
            dataReader.readNext();
            while ((nextLine = dataReader.readNext()) != null) {
                Integer attempt = Integer.valueOf(nextLine[0]);
                long timeNeededPostgreSQLBeforeIndexing = Long.parseLong(nextLine[1]);
                postgreSQLseriesBeforeIndexing.getData().add(new XYChart.Data(attempt, timeNeededPostgreSQLBeforeIndexing));
                long timeNeededPostgreSQLAfterIndexing = Long.parseLong(nextLine[4]);
                postgreSQLseriesJoinAfterIndexing.getData().add(new XYChart.Data(attempt, timeNeededPostgreSQLAfterIndexing));
            }
        }
        lineChart.getData().add(postgreSQLseriesBeforeIndexing);
        lineChart.getData().add(postgreSQLseriesJoinAfterIndexing);


        return new Scene(lineChart, graphWidth, graphHeight);

    }

    public Scene createMysqlComparisonGraph() throws Exception {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(xAxisLabelPerformance);
        yAxis.setLabel(yAxisLabel);

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Join Performance MySQL before/after indexing");
        XYChart.Series mysqlseriesBeforeIndexing = new XYChart.Series();
        mysqlseriesBeforeIndexing.setName("MySQL join before indexing    ");
        XYChart.Series mysqlseriesJoinAfterIndexing = new XYChart.Series();
        mysqlseriesJoinAfterIndexing.setName("MySQL join after indexing");


        try (CSVReader dataReader = new CSVReader(new FileReader("data/PerformanceMeasurement" + UUIDController.getDataEnding() + "/joinPerformance" + UUIDController.getDataEnding() + ".csv"))) {
            String[] nextLine;
            dataReader.readNext();
            while ((nextLine = dataReader.readNext()) != null) {
                Integer attempt = Integer.valueOf(nextLine[0]);
                long timeNeededMysqlBeforeIndexing = Long.parseLong(nextLine[2]);
                mysqlseriesBeforeIndexing.getData().add(new XYChart.Data(attempt, timeNeededMysqlBeforeIndexing));
                long timeNeededMysqlAfterIndexing = Long.parseLong(nextLine[5]);
                mysqlseriesJoinAfterIndexing.getData().add(new XYChart.Data(attempt, timeNeededMysqlAfterIndexing));
            }
        }
        lineChart.getData().add(mysqlseriesBeforeIndexing);
        lineChart.getData().add(mysqlseriesJoinAfterIndexing);


        return new Scene(lineChart, graphWidth, graphHeight);
    }

    public Scene createMongodbComparisonGraph() throws Exception{

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(xAxisLabelPerformance);
        yAxis.setLabel(yAxisLabel);

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Join Performance MongoDB before/after indexing");
        XYChart.Series mongodbseriesBeforeIndexing = new XYChart.Series();
        mongodbseriesBeforeIndexing.setName("MongoDB join before indexing    ");
        XYChart.Series mongodbseriesJoinAfterIndexing = new XYChart.Series();
        mongodbseriesJoinAfterIndexing.setName("MongoDB join after indexing");


        try (CSVReader dataReader = new CSVReader(new FileReader("data/PerformanceMeasurement" + UUIDController.getDataEnding() + "/joinPerformance" + UUIDController.getDataEnding() + ".csv"))) {
            String[] nextLine;
            dataReader.readNext();
            while ((nextLine = dataReader.readNext()) != null) {
                Integer attempt = Integer.valueOf(nextLine[0]);
                long timeNeededMongodbBeforeIndexing = Long.parseLong(nextLine[3]);
                mongodbseriesBeforeIndexing.getData().add(new XYChart.Data(attempt, timeNeededMongodbBeforeIndexing));
                long timeNeededMongodbAfterIndexing = Long.parseLong(nextLine[6]);
                mongodbseriesJoinAfterIndexing.getData().add(new XYChart.Data(attempt, timeNeededMongodbAfterIndexing));
            }
        }
        lineChart.getData().add(mongodbseriesBeforeIndexing);
        lineChart.getData().add(mongodbseriesJoinAfterIndexing);


        return new Scene(lineChart, graphWidth, graphHeight);
    }

    public Scene createJoinAfterIndexGraph() throws Exception {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(xAxisLabelPerformance);
        yAxis.setLabel(yAxisLabel);

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Join Performance after Indexing");
        XYChart.Series postgreSQLseries = new XYChart.Series();
        postgreSQLseries.setName("PostgreSQL Join after Index   ");
        XYChart.Series mySQLSeries = new XYChart.Series();
        mySQLSeries.setName("MySQL Join after Index");
        XYChart.Series mongoDBSeries = new XYChart.Series();
        mongoDBSeries.setName("MongoDB Join after Index");

        try (CSVReader dataReader = new CSVReader(new FileReader("data/PerformanceMeasurement" + UUIDController.getDataEnding() + "/joinPerformance" + UUIDController.getDataEnding() + ".csv"))) {
            readDataSecondSet(postgreSQLseries, mySQLSeries, mongoDBSeries, dataReader);
        }
        lineChart.getData().add(postgreSQLseries);
        lineChart.getData().add(mySQLSeries);
        lineChart.getData().add(mongoDBSeries);


        return new Scene(lineChart, graphWidth, graphHeight);
    }

    public Scene createPostgresMysqlJoinAfterScene() throws Exception {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(xAxisLabelPerformance);
        yAxis.setLabel(yAxisLabel);

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Join Performance PostgreSQL MySQL after Indexing)");
        XYChart.Series postgreSQLseries = new XYChart.Series();
        postgreSQLseries.setName("PostgreSQL join after index   ");
        XYChart.Series mySQLSeries = new XYChart.Series();
        mySQLSeries.setName("MySQL join after index");


        try (CSVReader dataReader = new CSVReader(new FileReader("data/PerformanceMeasurement" + UUIDController.getDataEnding() + "/joinPerformance" + UUIDController.getDataEnding() + ".csv"))) {
            String[] nextLine;
            dataReader.readNext();
            while ((nextLine = dataReader.readNext()) != null) {
                Integer attempt = Integer.valueOf(nextLine[0]);
                long timeNeededPostgreSQL = Long.parseLong(nextLine[4]);
                postgreSQLseries.getData().add(new XYChart.Data(attempt, timeNeededPostgreSQL));
                long timeNeededMySQL = Long.parseLong(nextLine[5]);
                mySQLSeries.getData().add(new XYChart.Data(attempt, timeNeededMySQL));
            }
        }
        lineChart.getData().add(postgreSQLseries);
        lineChart.getData().add(mySQLSeries);


        return new Scene(lineChart, graphWidth, graphHeight);


    }


    private void readDataFirstSet(XYChart.Series postgreSQLseries, XYChart.Series mySQLSeries, XYChart.Series mongoDBSeries, CSVReader dataReader) throws IOException, CsvValidationException {
        String[] nextLine;
        dataReader.readNext();
        while ((nextLine = dataReader.readNext()) != null) {
            Integer attempt = Integer.valueOf(nextLine[0]);
            long timeNeededPostgreSQL = Long.parseLong(nextLine[1]);
            postgreSQLseries.getData().add(new XYChart.Data(attempt, timeNeededPostgreSQL));
            long timeNeededMySQL = Long.parseLong(nextLine[2]);
            mySQLSeries.getData().add(new XYChart.Data(attempt, timeNeededMySQL));
            long timeNeededMongoDB = Long.parseLong(nextLine[3]);
            mongoDBSeries.getData().add(new XYChart.Data(attempt, timeNeededMongoDB));

        }
    }


    private void readDataSecondSet(XYChart.Series postgreSQLseries, XYChart.Series mySQLSeries, XYChart.Series mongoDBSeries, CSVReader dataReader) throws IOException, CsvValidationException {
        String[] nextLine;
        dataReader.readNext();
        while ((nextLine = dataReader.readNext()) != null) {
            Integer attempt = Integer.valueOf(nextLine[0]);
            long timeNeededPostgreSQL = Long.parseLong(nextLine[4]);
            postgreSQLseries.getData().add(new XYChart.Data(attempt, timeNeededPostgreSQL));
            long timeNeededMySQL = Long.parseLong(nextLine[5]);
            mySQLSeries.getData().add(new XYChart.Data(attempt, timeNeededMySQL));
            long timeNeededMongoDB = Long.parseLong(nextLine[6]);
            mongoDBSeries.getData().add(new XYChart.Data(attempt, timeNeededMongoDB));

        }
    }

    public Scene createWelcomeGraph() throws Exception {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(xAxisLabelPerformance);
        yAxis.setLabel(yAxisLabel);

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Your Performance Measurements will be displayed here!");


        return new Scene(lineChart, graphWidth, graphHeight);
    }
}
