package br.com.cwi.automation_training.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.googlecode.seleniumjavaevidence.report.GenerateEvidenceReport;
import com.googlecode.seleniumjavaevidence.selenium.SeleniumEvidence;

public class EvidenceReport {
	
	private List<SeleniumEvidence> evidence = null;
	private String testError = null;
	
	public EvidenceReport(){
		this.evidence = new ArrayList<SeleniumEvidence>();
	}
	
	public void setError(String errorMessage) {
		this.testError = errorMessage;
	}
	
	public List<SeleniumEvidence> getEvidenceReport() {
		return this.evidence;
	}
		
	public void addEvidence(String log, WebDriver driver) {
		try {
			evidence.add(new SeleniumEvidence(log,
					((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64)));
		} catch (WebDriverException e) {
			this.setError(e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			this.setError(e.toString());
			e.printStackTrace();
		}
	}
	
	public void addEvidence(String log, String image) {
		try {
			String base24StringFromImage = Base64.encodeBase64String(FileUtils.readFileToByteArray(new File(image)));
			evidence.add(new SeleniumEvidence(log, base24StringFromImage));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void generateEvidenceReport(List<SeleniumEvidence> evidences, String testName, String authorName, String reportTitle) {
		try {
			GenerateEvidenceReport.generatePDFEvidence(evidences, testName, authorName, reportTitle, this.testError);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
