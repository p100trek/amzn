package amzn.configuration.properties;

import java.util.List;

public class AppProperties {

    private static AmznPropertiesConfiguration propertiesConfiguration = AmznPropertiesConfiguration.instance("application.properties");
    private static final String XLSX_READ_DIR = "amzn.xlsxRead.dir";
    private static final String XLSX_WRITE_DIR = "amzn.xlsxWrite.dir";
    private static final String XLSX_READ_HEADER_TO_SEARCH = "amzn.xlsxRead.headerToSearch";
    private static final String COLUMN_HEADER_TO_COPY = "amzn.column.copy.byHeader";
    private static final String SKIP_HEADER = "amzn.xlsxWrite.skipHeader";

    public static String xslxReadDir() {
        return propertiesConfiguration.getProperty(XLSX_READ_DIR);
    }

    public static String xslxWriteDir() {
        return propertiesConfiguration.getProperty(XLSX_WRITE_DIR);
    }

    public static List<String> columnHeaderToSearch(){ return propertiesConfiguration.getList(XLSX_READ_HEADER_TO_SEARCH);}

    public static void reconfigureAppProperties(String fileName){ propertiesConfiguration = AmznPropertiesConfiguration.instance(fileName); }

    public static List<String> columnHeaderToCopy(){ return propertiesConfiguration.getList(COLUMN_HEADER_TO_COPY); }

    public static boolean skipHeader(){return Boolean.getBoolean(propertiesConfiguration.getProperty(SKIP_HEADER));}
}
