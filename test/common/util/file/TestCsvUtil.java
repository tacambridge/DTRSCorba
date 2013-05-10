package common.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import common.util.file.CsvUtil;

import junit.framework.TestCase;


/**
 * 
 * @author Terri-Anne
 *
 */
public class TestCsvUtil extends TestCase {

	private CsvUtil csvUtil;
	private final String FILE_DIRECTORY = System.getProperty("user.dir") + "/files/";
	private final String csvFile = "TEST";
	private final String FILEPATH = FILE_DIRECTORY + "SHOW_" + csvFile + ".csv";

	public void setUp() {
		System.out.println(FILEPATH);
		try {
			this.csvUtil = new CsvUtil(csvFile);
			FileWriter fileWriter = new FileWriter(FILEPATH);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}    	
	}

	public void tearDown () {
		this.csvUtil = null;
	}

	/*
	 * Write customer list to file
	 * Expected Result: all entries are written 
	 */
	public void testWriteOneRecordPerLine() {
		try {
			ConcurrentMap<Integer, Integer> list = new ConcurrentHashMap<Integer, Integer>();
			list.put(1,2);
			list.put(3,4);
			int size = list.size();
			csvUtil.writeCSV(list);

			int numberOfLines = 0;
			BufferedReader reader = new BufferedReader(new FileReader(FILEPATH));
			while (reader.readLine() != null) {
				numberOfLines++;
			}
			reader.close();

			System.out.println("numberOfLines " + numberOfLines);
			System.out.println("size " + size);
			assertEquals(size, numberOfLines);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	/*
	 * Read customer list from file
	 * Expected Result: all lines are read 
	 */
	public void testReadOneRecordPerLine() {
		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH));
			writer.write("1,2\n");
			writer.write("3,4\n");
			writer.close();

			ConcurrentMap<Integer, Integer> list = new ConcurrentHashMap<Integer, Integer>();
			list.putAll(csvUtil.readCSV());

			assertEquals(2, list.size());

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	public void testMethod() {

	}
}