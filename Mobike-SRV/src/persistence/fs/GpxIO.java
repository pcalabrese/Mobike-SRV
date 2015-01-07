package persistence.fs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import persistence.exception.FilesystemException;
 
public class GpxIO implements RouteIO{
	
	public GpxIO(){
		super();
	}
	
	public String write(String content,String filename) throws FilesystemException{
		String filepath = null;
		try {
 
			File file = new File("C:/gpxs/"+filename+".gpx");
			int i=0;
			
			/* to prevent file overwriting */
			while(file.exists()){
				file = new File("C:/gpxs/"+filename+i+".gpx");
				i++;
			}
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			
			//write content into file
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			filepath= file.getAbsolutePath();
			bw.close();
 
		} catch (IOException e) {
			throw new FilesystemException(e.getMessage());
		}
		return filepath;
		

	}

	@Override
	public String read(String filename) throws FilesystemException {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("C:/gpxs/"+filename));
		} 
		catch (FileNotFoundException e) {
			throw new FilesystemException(e.getMessage());
		}
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();

		        while (line != null) {
		            sb.append(line);
//		            sb.append("\n");
		            line = br.readLine();
		        }
		        return sb.toString();
		    } 
		    catch (Exception e) {
		    	throw new FilesystemException(e.getMessage());
		    }
		    finally {
		        try {
					br.close();
				} catch (IOException e) {
					throw new FilesystemException(e.getMessage());
				}
		    }
		   
		 
		/*try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("C:/gpxs/"+filename));
			
			System.out.println("C:/gpxs/"+filename);
			while ((sCurrentLine = br.readLine()) != null) {
				
				System.out.println(sCurrentLine);
				result.concat(sCurrentLine);
			}
			System.out.println(result);
 
		} catch (IOException e) {
			throw new FilesystemException(e.getMessage());
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException e) {
				throw new FilesystemException(e.getMessage());
			}
		}
		
		return result; */
	}

	

}