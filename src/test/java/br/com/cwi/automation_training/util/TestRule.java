package br.com.cwi.automation_training.util;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.sikuli.script.App;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Screen;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import br.com.cwi.automation_training.pages.BasePage;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class TestRule extends TestWatcher {

	private static WebDriver driver;
	private static Screen sikuli;
	private static App sikuliApp;
	private static EvidenceReport evidenceReport;
	private static ExtentReports extentReport;
	private static Scenario scenario;
	private static ExtentTest extentTest;
	private static Logger logger = Logger.getLogger(TestRule.class);
	private static String activeAutomation;

	public TestRule() {
		super();
	}

	@Override
	protected void starting(Description description) {
		super.starting(description);
		
		//EXTENTREPORT
		new File("target/relatorios").mkdir();
		new File("target/relatorios/img").mkdir();
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("target/relatorios/" + description.getDisplayName().replace("tests.", "") + ".html");
		extentReport = new ExtentReports();
		extentReport.attachReporter(htmlReporter);
		extentReport.setSystemInfo("test.base_url", Utils.getTestProperty("test.base_url"));
		extentReport.setSystemInfo("test.application", Utils.getTestProperty("test.application"));
		extentReport.setSystemInfo("os.name", System.getProperty("os.name"));
	}

	@Before
	public void beforeCenario(Scenario scenario) {
	    this.scenario = scenario;
	    
		//INICIA EVIDENCIAS
		evidenceReport = new EvidenceReport();
		
		//INICIA EXTENTTEST
		extentTest = extentReport.createTest("Cenario: " + scenario.getName(), scenario.getName());
	}
	
	@After
	public void afterCenario(){
    	if(scenario.isFailed()){
    		if (driver != null || sikuliApp != null) {
        		BasePage basePage = new BasePage();
        		basePage.logPrintFail("O teste falhou");
			}
    	}
		
    	//FINALIZA EVIDENCIAS
    	String nomeCenario = scenario.getName();
		evidenceReport.generateEvidenceReport(evidenceReport.getEvidenceReport(), scenario.getName(), "Getnet", nomeCenario);
		
		//FINALIZA EXTENT REPORT
    	extentReport.flush();
    	
		//FINALIZA DRIVER
    	if (driver != null) {
    		driver.close();
    		driver.quit();
    		driver = null;
		}
    	
    	//FINALIZA SIKULI
    	if (sikuliApp != null) {
    		sikuliApp.close();
    		sikuliApp = null;
		}
	}
	
	@Override
	protected void finished(Description description) {
		super.finished(description);
	}

	public static WebDriver getDriver() {
		return driver;
	}
	
	public static ExtentTest getExtentTest() {
		return extentTest;
	}
	
	public static Scenario getScenario() {
		return scenario;
	}
	
	public static EvidenceReport getEvidenceReport() {
		return evidenceReport;
	}
	
	public static Screen getSikuli() {
		return sikuli;
	}
	
	public static App getSikuliApp() {
		return sikuliApp;
	}
	
	public static void setSikuliApp(App app) {
		sikuliApp = app;
	}
	
	public static ExtentReports getExtentReports(){
		return extentReport;
	}
	
	public static String getActiveAutomation(){
		return activeAutomation;
	}
	
	public static void openApplication(String application, String url) {
		switch (application) {
		case "chrome":
			openApplicationChrome(url);
			break;
		case "ie":
			openApplicationIE(url);
			break;
		case "winium":
			openApplicationWinium(url);
			break;
		case "sikuli":
			openApplicationSikuli(url);
			break;
		case "chromemobile":
			openApplicationChromeMobile(url);
			break;
		default:
			System.err.print("Property test.application: " + Utils.getTestProperty("test.application") + " nao encontrada.");
			break;
		}
	}
	
	public static void openApplicationChrome(String url){
		activeAutomation = "chrome";
		String downloadFilepath = System.getProperty("user.dir") + "/target/temp";
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("download.default_directory", downloadFilepath);
		chromePrefs.put("credentials_enable_service", false);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("disable-infobars");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, 
				   UnexpectedAlertBehaviour.ACCEPT);
		if (System.getProperty("os.name").contains("Windows")) {
			System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
		}
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(capabilities);
		driver.manage().window().maximize();
		driver.navigate().to(url);
	}
	
	public static void openApplicationIE(String url){
		activeAutomation = "ie";
		System.setProperty("webdriver.ie.driver", "src/test/resources/drivers/IEDriverServer.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
		driver = new InternetExplorerDriver(capabilities);
		driver.manage().window().maximize();
		driver.navigate().to(url);
	}
	
	public static void openApplicationWinium(String applicationPath){
		activeAutomation = "winium";
		try {
			DesktopOptions options = new DesktopOptions();
			options.setApplicationPath(applicationPath);
			driver = new WiniumDriver(new URL("http://localhost:9999"), options);
		} catch (Exception e) {
			logger.error("Erro ao iniciar Winium", e);
		}
	}
	
	public static void openApplicationSikuli(String applicationPath){
        startSikuli();
        sikuliApp = App.open(applicationPath);
	}
	
	public static void startSikuli(){
		activeAutomation = "sikuli";
        sikuli = new Screen();
        for (String directory : Utils.getSubDirectoriesNames("src/test/resources/sikuli-images/")) {
        	ImagePath.add("src/test/resources/sikuli-images/" + directory);
		}
	}
	
	public static void openApplicationChromeMobile(String url){
		activeAutomation = "chromemobile";
		String downloadFilepath = System.getProperty("user.dir") + "/target/temp";
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("download.default_directory", downloadFilepath);
		chromePrefs.put("credentials_enable_service", false);
		
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("disable-infobars");
		options.addArguments("--disable-print-preview");
		
		if (System.getProperty("os.name").contains("Windows")) 
			System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
			
		Map<String, String> mobileEmulation = new HashMap<String, String>();
		mobileEmulation.put("deviceName", "Google Nexus 5");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		options.setExperimentalOption("mobileEmulation", mobileEmulation);

		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		driver = new ChromeDriver(capabilities);
		driver.manage().window().maximize();
		driver.navigate().to(url);
	}
}