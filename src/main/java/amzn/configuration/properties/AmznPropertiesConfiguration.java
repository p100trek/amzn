package amzn.configuration.properties;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.Arrays;
import java.util.List;

public class AmznPropertiesConfiguration {

    private static AmznPropertiesConfiguration propertiesConfiguration;
    private static String fileName;
    private FileBasedConfiguration config;

    private AmznPropertiesConfiguration(String fileName) {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class)
                .configure(params.properties().setFileName(fileName));

        try {
            config = builder.getConfiguration();
        } catch (ConfigurationException e) {
            //e.printStackTrace();
        }
    }

    public static synchronized AmznPropertiesConfiguration instance(String fileName) {
        if (propertiesConfiguration == null || !AmznPropertiesConfiguration.fileName.equalsIgnoreCase(fileName)) {
            propertiesConfiguration = new AmznPropertiesConfiguration(fileName);
        }
        AmznPropertiesConfiguration.fileName = fileName;
        return propertiesConfiguration;
    }

    protected String getProperty(String key) {
        return (String) config.getProperty(key);
    }

    protected List<String> getList(String key){
        String property = getProperty(key);
        return Arrays.asList(property.split(","));
    }
}
