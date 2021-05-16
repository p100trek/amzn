package amzn.bl.service.impl;

import amzn.bl.service.FileHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileHelperImpl implements FileHelper {

    @Override
    public InputStream getInputStream(String fileName) {
        try {
            return  new FileInputStream(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
