package br.com.cwi.automation_training.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Utils {

	private static String ambientePropertiesPath = "src/test/resources/automation.properties";;

	public static Properties loadTestProperties() throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		FileInputStream input = new FileInputStream(ambientePropertiesPath);
		prop.load(input);
		input.close();
		return prop;
	}

	public static String getTestProperty(String propertyKey) {
		try {
			Properties prop = loadTestProperties();
			return prop.getProperty(propertyKey).trim();
		} catch (Exception e) {
			String error_msg = String.format("Nao foi possivel carregar a propriedade %s. Erro %s", propertyKey,
					e.getMessage());
			throw new IllegalStateException(error_msg);
		}
	}

	public static Properties loadProperties(String propertiesPath) throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		FileInputStream input = new FileInputStream(propertiesPath);
		prop.load(input);
		input.close();
		return prop;
	}

	public static String getProperty(Properties prop, String propertyKey) {
		try {
			return prop.getProperty(propertyKey).trim();
		} catch (Exception e) {
			String error_msg = String.format("Nao foi possivel carregar a propriedade %s. Erro %s", propertyKey,
					e.getMessage());
			throw new IllegalStateException(error_msg);
		}
	}

	public static String[] getSubDirectoriesNames(String path) {
		File file = new File(path);
		String[] directories = file.list(new FilenameFilter() {
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		return directories;
	}
	
	public static String readFileToString(String path, Charset encoding) {
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(encoded, encoding);
	}
	
}