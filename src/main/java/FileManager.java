import java.io.File;

public class FileManager {
    private String root = System.getProperty("user.dir");
    private String DATA_SET = "f1_dataset_test.csv";
    private String DRIVERS = "drivers.txt";
    private String filePath_DataSet = root+ File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+DATA_SET;
    private String filePath_Drivers = root+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+DRIVERS;

    public String getFilePath_DataSet() {
        return filePath_DataSet;
    }

    public String getFilePath_Drivers() {
        return filePath_Drivers;
    }
}
