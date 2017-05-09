package commonUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;

import com.opencsv.CSVReader;


public class CsvUtils {
	
	private final Logger log;
	
	public CsvUtils(Logger log) {
		this.log=log;
	}
	
	public static ArrayList<String> loadCSVHeader(String fileName) throws Exception {
		ArrayList<String> result = new ArrayList<String>();
		
		//String fileName="C:/Documents and Settings/tntadmin/workspace2/a.csv";
		BufferedReader br=null;
		try {
			br = new BufferedReader( new FileReader(fileName));
			//String strLine = null;
			StringTokenizer st = null;
			int lineNumber = 0;
			//int tokenNumber = 0;
			
			while((fileName = br.readLine())!=null) {
				
				//break comma separated line using ","
				st = new StringTokenizer(fileName, ";");
		 
				ArrayList<String> oTokenList = new ArrayList<String>();
				if(lineNumber==0) {
					while(st.hasMoreTokens()) {
						//display csv values
						//tokenNumber++;
						//oTokenList.add(st.nextToken());
						
						try {
							oTokenList.add(st.nextToken());
						} catch (Exception e2) {
							throw new Exception ("Not readable signs. The input file contains non-double values." + e2.getMessage());
						}
						
						//System.out.println("Line # " + lineNumber + ", Token # " + tokenNumber + ", Token : "+ oTokenList.get(tokenNumber-1));
						
					}
				}
				
				result = oTokenList;
				break;
		 	}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File not found: " + fileName + "; " + e.getMessage());
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		} finally {
			if (br!=null) {
				br.close();
			}
		}
		
		return result;
	}
	
	
	/**
	 * Load profile data from a csv file.
	 * 
	 * 
	 * @param poFileName
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<ArrayList<Double>> loadCSVdata(String poFileName) throws Exception {
		ArrayList<ArrayList<Double>> oResult = new ArrayList<ArrayList<Double>>();
		
		//String fileName="C:/Documents and Settings/tntadmin/workspace2/a.csv";
		BufferedReader br=null;
		try {
			br = new BufferedReader( new FileReader(poFileName));
			//String strLine = null;
			StringTokenizer st = null;
			int lineNumber = 0;
			//int tokenNumber = 0;
			
			while((poFileName = br.readLine()) != null) {
				lineNumber++;
		 
				//break comma separated line using ","
				st = new StringTokenizer(poFileName, ";");
		 
				ArrayList<Double> oTokenList = new ArrayList<Double>();
				
				//Do not load the first row
				if(lineNumber>1) {
					while(st.hasMoreTokens()) {
						//display csv values
						//tokenNumber++;
						//oTokenList.add(st.nextToken());
						
						try {
							oTokenList.add(Double.valueOf(st.nextToken()));
						} catch (Exception e2) {
							throw new Exception ("Not readable signs. The input file contains non-double values." + e2.getMessage());
						}
						
						//System.out.println("Line # " + lineNumber + ", Token # " + tokenNumber + ", Token : "+ oTokenList.get(tokenNumber-1));
						
					}
					
					oResult.add(oTokenList);
					//reset token number
					//tokenNumber = 0;
				}
		 	}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File not found: " + poFileName + "; " + e.getMessage());
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		} finally {
			if (br!=null) {
				br.close();
			}
		}
		
		//System.out.print("Finished without errors");
				
		return oResult;
	}
	
	/**
	 * Load values into an array from a csv file
	 *  
	 * @param path
	 * @param separator
	 * @param datatype
	 * @return
	 * @throws Exception
	 */
	public int[][] loadIntegerArrayFromCSV(String path, char separator, int offset) throws Exception {
		
		String[][] array = loadStringArrayFromCSV(path, separator, offset);
			
		// Generate data
		final int[][] result = new int[array.length][array[0].length];
		
		//put values to double [][] matrix
		for(int i=0;i<array.length;i++) {
			for(int x=0;x<array[0].length;x++)
			{
				result[i][x]=Integer.valueOf(array[i][x]);
			}
		}
		
		return result;
	}
	
public int[] loadIntegerSingleArrayFromCSV(String path, char separator, int offset) throws Exception {
		
		String[][] array = loadStringArrayFromCSV(path, separator, offset);
			
		// Generate data
		final int[] result = new int [array[0].length] ;
		
		//put values to double [][] matrix
		for(int i=0;i<array.length;i++) {
			int counter=0;
			for (int j=0;j< array[0].length;j++)
			{
				result[counter]=Integer.valueOf(array[i][j]);
			counter++;
			}
		}
		
		return result;
	}


	
	public double[][] loadDoubleArrayFromCSV(String path, char separator, int offset) throws Exception {
		
		String[][] array = loadStringArrayFromCSV(path, separator, offset);
			
		// Generate data
		final double[][] result = new double[array.length][array[0].length];
		
		//put values to double [][] matrix
		for(int i=0;i<array.length;i++) {
			for(int x=0;x<array[0].length;x++)
			{
				result[i][x]=Double.valueOf(array[i][x]);
			}
		}
		
		return result;
	}
	
	public double[] loadDoublesingleArrayFromCSV(String path, char separator, int offset) throws Exception {
		
		String[] array = readHeaderFromCSV(path, separator, offset);
			
		// Generate data
		final double[] result = new double[array.length];
		
		//put values to double [][] matrix
		for(int i=0;i<array.length;i++) {
			
			{
				result[i]=Double.valueOf(array[i]);
			}
		}
		
		return result;
	}
	
	
	
	public String [] readHeaderFromCSV(String path, char separator, int offset)throws Exception {
	String [] header;
		try {
			CSVReader reader = new CSVReader(new FileReader(path), separator , '"' , offset);
			header = reader.readNext();
			reader.close();
		} catch (FileNotFoundException e) {
			log.error("File not found", e);
			throw new Exception (e.getMessage());
		}
		return header;
	}
	
	public String[][] loadStringArrayFromCSV(String path, char separator, int offset) throws Exception {
		
		//Read CSV file with list array
		//CSVReader reader = new CSVReader(new FileReader(path), ';' , '"' , 1);
		
		List<String[]> list; 
		try {
			CSVReader reader = new CSVReader(new FileReader(path), separator , '"' , offset);
			list = reader.readAll();
			reader.close();
		} catch (FileNotFoundException e) {
			log.error("File not found", e);
			throw new Exception (e.getMessage());
		} catch (IOException e) {
			log.error("Cannot read file", e);
			throw new Exception (e.getMessage());
		}
		
		// Convert from list to 2D array
		String[][] dataArr = new String[list.size()][];
		dataArr = list.toArray(dataArr);
		
		return dataArr;
	}
}
