package persistence.fs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import persistence.exception.FilesystemException;
 
public class GpxWriter implements PathWriter{
	
	public GpxWriter(){
		super();
	}
	
	public String write(String content,String filename) throws FilesystemException{
		String filepath = null;
		try {
 
			File file = new File("/paths/"+filename+".gpx");
			int i=0;
			
			/* to prevent file overwriting */
			while(file.exists()){
				file = new File ("/paths/"+filename+i+".gpx");
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
}