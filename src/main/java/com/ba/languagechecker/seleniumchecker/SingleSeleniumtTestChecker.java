package com.ba.languagechecker.seleniumchecker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

public class SingleSeleniumtTestChecker implements Runnable {
	private static Logger _log = Logger
			.getLogger(SingleSeleniumtTestChecker.class.getCanonicalName());
	private final String fileName;

	public SingleSeleniumtTestChecker(final String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void run() {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
		{
			String line = br.readLine();
			while (line != null) {
	            
				
				
				
				
				
	            line = br.readLine();
	        }
			

		} catch (FileNotFoundException e) {
			_log.error(e, e);
		} catch (IOException e) {
			_log.error(e, e);
		}

	}

}
