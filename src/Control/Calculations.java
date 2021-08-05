package Control;

import java.io.File;

public class Calculations {

    public double getAverage(long[] myList) {
        long sum = 0;
        for (int i = 0; i < myList.length; i++) {
            sum = sum + myList[i];
        }


        return sum / myList.length/ 1e9;
    }

    public int getAmountOfItems(long[] myList) {
        return myList.length;
    }

    private double folderSize(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile())
                length += file.length();
            else
                length += folderSize(file);
        }
        return  (length  / 1e6);
    }

    public double  postgresSize(){
        return folderSize(new File("/Users/joelsteiner/Library/Application Support/Postgres"));
    }

    public double toSeconds(long myLongInput){
        return myLongInput/1e9;
    }


}
