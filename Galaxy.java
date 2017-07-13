import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import javax.xml.xpath.*;
import org.xml.sax.InputSource; 
import org.w3c.dom.Attr;
import java.util.*;
import java.io.InputStream;

class Galaxy {
	public static void main(String args[]) {

		try {
			File dir = new File("/home/dom/Desktop/xml-parsing/xml-files/");
			File[] listOfFiles = dir.listFiles();
			Arrays.sort(listOfFiles);
			
			int toolCount = 1;
			for (File path : listOfFiles) {
				if (path.isFile() && path.getName().endsWith(".xml")) {
					retrieveValues(path, toolCount);
					toolCount++;
					break;
				}

			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void retrieveValues(File fileName, int toolCount) {

		ArrayList<String> valueTestContainer   = new ArrayList<String>();	// value attributes under <test>
		ArrayList<String> valueInputsContainer = new ArrayList<String>();	// value attributes under <inputs>

		try {

			// source: https://stackoverflow.com/questions/2460592/xpath-how-to-get-all-the-attribute-names-and-values-of-an-element
			// source: https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
			// source: https://stackoverflow.com/questions/2811001/how-to-read-xml-using-xpath-in-java

			// set up
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fileName);
			
			doc.getDocumentElement().normalize();
			System.out.println("\n\n=== Tool no:" + toolCount + " | name: " + doc.getDocumentElement().getAttribute("name") + " ===");

			XPathFactory xpf = XPathFactory.newInstance();
			XPath xpath = xpf.newXPath();
			
	       	// <TEST>

	       	// counting the <test>
			NodeList testTagList = doc.getElementsByTagName("test");  // from: https://www.mkyong.com/java/how-to-count-xml-elements-in-java-dom-parser/
			int testTagCount = testTagList.getLength();

	       	// using xpath to get to <param> via traversing the nodes
			XPathExpression expr = xpath.compile("/tool/tests/test/param/@*");
			NodeList nlTest = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

	       	// traversing the <param> under <test> and retrieving its attributes
	        int nlTestLength = nlTest.getLength();
	        for (int i = 0; i < nlTestLength; i++) {
	        	Attr atTest = (Attr) nlTest.item(i);
	        	String nameTest = atTest.getName();
	        	String valueTest = atTest.getValue();

	        	valueTestContainer.add(valueTest);
	        }

	        // <INPUTS>
	        XPathExpression expr2 = xpath.compile("/tool/inputs/param/@*");
			NodeList nlInputs = (NodeList) expr2.evaluate(doc, XPathConstants.NODESET);


	        //traversing the <param> under <input> and cross checking its attributes in the value container under <test>
	        int nlInputsLength = nlInputs.getLength();
		    for (int t = 0; t < testTagCount; t++) {
		        for (int i = 0; i < nlInputsLength; i++) {
		        	Attr atInputs = (Attr) nlInputs.item(i);
		        	String nameInputs = atInputs.getName();
		        	String valueInputs = atInputs.getValue();
		        	if (nameInputs.equals("name")) {		// checking the 'name' attribute
		        		valueInputsContainer.add(valueInputs);
		        	}
		        	else if (nameInputs.equals("type")) { 	// checking the 'type' attribute
		        		valueInputsContainer.add(valueInputs);
		        	}
		        }
		    }

	        /* 		Just a test. Actual is automation in the input fields using Selenium Web Driver.
	         *		Data that will be automated will come from the <test> / valueTestContainer 
	         *		via driver.sendKeys()
	         */		

	        int valuesPerTestTag = nlTestLength / testTagCount;
	       	int i = 0;
       		int ntl = nlTestLength;
   			int testTagCount2 = 1;
       		while (ntl != 0) {

       			int paramCount = 1;
       			Inputs in = new Inputs();
       			int temp = valuesPerTestTag;
       			int temp2 = temp;

       			System.out.println("--- Test tag: " + testTagCount2 + "\n");
       			while (temp != 0) {
       				if ( (valueInputsContainer.get(i)).equals((valueTestContainer.get(i))) ) {
		        		in.name = valueTestContainer.get(i);
		        		in.expectedValue = valueTestContainer.get(i+1);
		        		in.type = valueInputsContainer.get(i+1);
		        	}
		        	System.out.println("param " + paramCount);
		        	paramCount++;
		        	System.out.println("name: " + in.name);
		        	System.out.println("expected value: " + in.expectedValue);
		        	System.out.println("type: " + in.type  + "\n");
		        	i += 2;
		        	temp -= 2;
       			}

       			ntl -= temp2;
       			testTagCount2++;
       		}
 		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}