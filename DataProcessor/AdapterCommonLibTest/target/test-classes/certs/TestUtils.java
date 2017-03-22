package test.resources.certs;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class TestUtils {

	public static String getTempPath(String fileName){

		InputStream inputStream = TestUtils.class.getResourceAsStream(fileName);
		String tempPath = "";
		DataInputStream dis = null;
		FileOutputStream fos = null;
		try
		{
			dis = new DataInputStream(inputStream);
			byte fileContent[] = new byte[(int)dis.available()];
			dis.readFully(fileContent);
			File tempFile = File.createTempFile(fileName.substring(0, fileName.lastIndexOf('.')), fileName.substring(fileName.lastIndexOf('.')));
			tempFile.deleteOnExit();
			fos = new FileOutputStream(tempFile);
			fos.write(fileContent);
			fos.close();
			tempPath =  tempFile.getAbsolutePath();
		}
		catch (Exception e){
			System.err.println(e.getMessage());
		} finally {
			if (fos != null) {
				try  { fos.close(); } catch (Exception e) {}
			}
		}
		return tempPath;
	}
}
