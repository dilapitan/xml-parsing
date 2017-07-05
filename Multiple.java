import java.io.File;
import java.util.*;

class Multiple {
	public static void main(String args[]) {
		try {
			File dir = new File("/home/dilapitan/Desktop/xml-parsing/files/");
			File[] listOfFiles = dir.listFiles();
			Arrays.sort(listOfFiles);
			
			for (File path : listOfFiles) {
				if (path.isFile() && path.getName().endsWith(".xml"))
					System.out.println(path);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}