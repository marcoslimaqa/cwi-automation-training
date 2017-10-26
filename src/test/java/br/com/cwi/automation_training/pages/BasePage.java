package br.com.cwi.automation_training.pages;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import br.com.cwi.automation_training.util.EvidenceReport;
import br.com.cwi.automation_training.util.TestRule;

public class BasePage {

	protected WebDriver driver = TestRule.getDriver();
	protected ExtentTest extentTest = TestRule.getExtentTest();
	protected ExtentReports extentReport = TestRule.getExtentReports();
	protected EvidenceReport evidenceReport = TestRule.getEvidenceReport();
	protected Screen sikuli = TestRule.getSikuli();
	protected App sikuliApp = TestRule.getSikuliApp();
	protected boolean isSikuliAutomation = "sikuli".equals(TestRule.getActiveAutomation());
	public final Logger logger = Logger.getLogger(BasePage.class);
	
	public BasePage() {
		
	}
	
	protected void wait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			logger.error("Erro ao executar wait(int seconds)", e);
		}
	}

	protected void waitMilliseconds(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			logger.error("Erro ao executar wait(int milliseconds)", e);
		}
	}

	protected WebElement waitElement(By by, int timeOutInSeconds) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
				.pollingEvery(200, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		return element;
	}

	protected WebElement waitElement(WebElement webElement, int timeOutInSeconds) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
				.pollingEvery(10, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).ignoring(ElementNotVisibleException.class);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(webElement));
		return element;
	}

	protected List<WebElement> waitElements(By by, int timeOutInSeconds) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
				.pollingEvery(10, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		List<WebElement> element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
		return element;
	}

	protected boolean waitNotPresent(By by, int timeOutInSeconds) {
		WebDriver driver = TestRule.getDriver();

		waitMilliseconds(500);
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
				.pollingEvery(100, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		
		boolean isElementInvisible = false;
		try {
			isElementInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
		} catch (Exception e) {
			return false;
		}
		return isElementInvisible;
	}
	
	protected void moveToElement(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	protected boolean isElementDisplayed(By by) {
		boolean isElementPresent = false;
		boolean isElementDisplayed = false;
		isElementPresent = !driver.findElements(by).isEmpty();
		if (isElementPresent) {
			isElementDisplayed = driver.findElement(by).isDisplayed();
		}
		return isElementPresent && isElementDisplayed;
	}

	protected void aguardarLoading() {
		try {
			waitElement(By.id("loading"), 3);
		} catch (Exception e) {
		}
		waitNotPresent(By.id("loading"), 120);
	}

	private String saveScreenshotInRelatoriosPath() {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		int milliseconds = calendar.get(Calendar.MILLISECOND);
		String datahora = "" + day + month + year + "_" + hours + minutes + seconds + milliseconds;
		String screenShotName = datahora + ".png";
		File scrFile = null;
		try {
			if (isSikuliAutomation) {
				scrFile = new File("target/relatorios/img/" + screenShotName);
				try {
					ImageIO.write(sikuli.capture(sikuliApp.window()).getImage(), "png", scrFile);
				} catch (Exception e) {
					ImageIO.write(sikuli.capture().getImage(), "png", scrFile);
					logger.debug("Erro ao obter screenshot do app, possivelmente o app '" + sikuliApp.getName() + "' nao esta em execucao."
							+ "Obtido screenshot da tela inteira.");
				}
			} else {
				scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File("target/relatorios/img/" + screenShotName));
			}
		} catch (IOException e) {
			logger.error("Erro ao salvar screenshot.", e);
		}
		return screenShotName;
	}

	protected void logPrint(String log) {
		String screenShotName = saveScreenshotInRelatoriosPath();
		try {
			extentTest.pass(log, MediaEntityBuilder.createScreenCaptureFromPath("../relatorios/img/" + screenShotName).build());
		} catch (IOException e) {
			logger.error("Erro ao executar logPrint()", e);
		}
		evidenceReport.addEvidence(log, "target/relatorios/img/" + screenShotName);
	}

	public void logPrintFail(String log) {
		String screenShotName = saveScreenshotInRelatoriosPath();
		try {
			extentTest.fail(log, MediaEntityBuilder.createScreenCaptureFromPath("../relatorios/img/" + screenShotName).build());
		} catch (IOException e) {
			logger.error("Erro ao executar logPrintFail()", e);
		}
		evidenceReport.addEvidence(log, "target/relatorios/img/" + screenShotName);
	}

	protected void logInfo(String log) {
		extentTest.info(log);
	}

	protected void logSkip(String log) {
		extentTest.skip(log);
	}

	protected void logFail(String log) {
		extentTest.fail(log);
	}

	protected void logError(String log) {
		extentTest.error(log);
	}

	protected void logPass(String log) {
		extentTest.pass(log);
	}

	protected ExtentTest createChildStart(String strNomeTeste) {
		ExtentTest parentTest = TestRule.getExtentTest();
		ExtentTest child = parentTest.createNode(strNomeTeste);
		return child;
	}

	protected void childLogFail(ExtentTest child, String log) {
		child.fail(log);
	}

	protected void childLogPass(ExtentTest child, String log) {
		child.pass(log);
	}
	
	protected void childLogInfo(ExtentTest child, String log) {
		child.info(log);
	}
	
	protected boolean imageExists(String imageFile, float similarity0to100) {
		Match image = sikuli.exists(new Pattern(imageFile).similar(similarity0to100/100));
        boolean imageExists = image != null;
        return imageExists;
	}
	
	protected boolean imageExists(String imageFile, float similarity0to100, double timeOutInSeconds) {
		Match image = sikuli.exists(new Pattern(imageFile).similar(similarity0to100/100), timeOutInSeconds);
        boolean imageExists = image != null;
        return imageExists;
	}

	protected boolean assertImageExists(String imageFile, float similarity0to100) {
		try {
			FileUtils.copyFile(new File(ImagePath.find(imageFile).getPath()), new File("target/relatorios/img/" + imageFile));
			extentTest.info("Imagem esperada: " + imageFile, MediaEntityBuilder.createScreenCaptureFromPath("../relatorios/img/" + imageFile).build());
		} catch (IOException e) {
			logger.error("Erro ao executar o metodo assertImageExists()", e);
		}

		Match image = sikuli.exists(new Pattern(imageFile).similar(similarity0to100/100));
        boolean imageExists = image != null;
        
        if (imageExists) {
        	image.highlight();
        	waitMilliseconds(30);
        	logPrint("Imagem encontrada com " + (image.getScore() * 100) + " % de similaridade");
        	image.highlight();
		} else {
			logFail("A imagem " + imageFile + " nao foi encontrada com a similaridade de " + similarity0to100 + " %");
		}
        return imageExists;
	}

	protected int click(String imageFile) {
		try {
			return sikuli.click(imageFile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected int click(String imageFile, float similarity0to100) {
		try {
			return sikuli.click(new Pattern(imageFile).similar(similarity0to100/100));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected int click(String imageFile, float similarity0to100, int x, int y) {
		try {
			return sikuli.click(new Pattern(imageFile).similar(similarity0to100/100).targetOffset(x,y));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected int doubleClick(String imageFile) {
		try {
			return sikuli.doubleClick(imageFile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected String getText(String imageFile, float similarity0to100) {
		clearClipboard();
		click(imageFile, similarity0to100);
		type("a", Key.CTRL);
		type("c", Key.CTRL);
		return getClipboard();
	}
	
	protected String getText(String imageFile, float similarity0to100, int x, int y) {
		clearClipboard();
		click(imageFile, similarity0to100, x, y);
		type("a", Key.CTRL);
		type("c", Key.CTRL);
		return getClipboard();
	}
	
	private void clearClipboard() {
	    StringSelection selection = new StringSelection("");
	    Clipboard clipboard2 = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard2.setContents(selection, selection);
	}
	
	private String getClipboard() {
		String clipboardText = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		try {
			clipboardText = (String) clipboard.getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException e) {
			logger.error("Erro ao obter texto da area de transferencia", e);
		}
		return clipboardText;
	}
	
	protected Match wait(String imagem, int tempoMaximoSegundos){
		try {
			return sikuli.wait(imagem, tempoMaximoSegundos);
		} catch (FindFailed e) {
			throw new RuntimeException(e);
		}
	}
	
	protected Match wait(String imagem, int tempoMaximoSegundos, float similarity0to100){
		try {
			return sikuli.wait(new Pattern(imagem).similar(similarity0to100/100), tempoMaximoSegundos);
		} catch (FindFailed e) {
			throw new RuntimeException(e);
		}
	}
	
	protected App appFocus(String appName){
		sikuliApp = App.focus(appName);
		TestRule.setSikuliApp(sikuliApp);
		return sikuliApp;
	}

	protected void multiType(String arg, int count) {
		for (int i = 0; i < count; i++) {
			type(arg);
		}
	}

	protected int type(String texto) {
		return sikuli.type(texto);
	}
	
	protected int type(String text, String modifiers) {
		return sikuli.type(text, modifiers);
	}
	
	protected int paste(String texto) {
		return sikuli.paste(texto);
	}

	protected void multiClick(int clicks, String imageFile) {
		try {
			for (int i = 0; i < clicks; i++) {
				sikuli.click(imageFile);
				waitMilliseconds(500);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}