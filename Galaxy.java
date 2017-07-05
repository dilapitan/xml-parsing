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

class Galaxy {
	public static void main(String args[]) {

		ArrayList<String> valueTestContainer   = new ArrayList<String>();	// value attributes under <test>
		ArrayList<String> valueInputsContainer = new ArrayList<String>();	// value attributes under <inputs>

		try {

			// source: https://stackoverflow.com/questions/2460592/xpath-how-to-get-all-the-attribute-names-and-values-of-an-element
			// source: https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/

			// set up
			InputSource f = new InputSource("/home/dom/Desktop/xml-parsing/test.xml"); // file reading
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(f);
			
			doc.getDocumentElement().normalize();
			System.out.println("\n\n-- Tool name: " + doc.getDocumentElement().getAttribute("name") + " --");

			XPathFactory xpf = XPathFactory.newInstance();
			XPath xpath = xpf.newXPath();
			
	       	// <TEST>
	       	// using xpath to get to <param> via traversing the nodes
	        NodeList nlTest = (NodeList) xpath.evaluate("/tool/tests/test/param/@*", f, XPathConstants.NODESET);
	        
	       	// traversing the <param> under <test> and retrieving its attributes
	        int nlTestLength = nlTest.getLength();
	        for (int i = 0; i < nlTestLength; i++) {
	        	Attr atTest = (Attr) nlTest.item(i);
	        	String nameTest = atTest.getName();
	        	String valueTest = atTest.getValue();

	        	valueTestContainer.add(valueTest);
	        }

	        // <INPUTS>
	        NodeList nlInputs = (NodeList) xpath.evaluate("/tool/inputs/param/@*", f, XPathConstants.NODESET);

	        //traversing the <param> under <input> and cross checking its attributes in the value container under <test>
	        int nlInputsLength = nlInputs.getLength();
	        for (int i = 0; i < nlInputsLength; i++) {
	        	Attr atInputs = (Attr) nlInputs.item(i);
	        	String nameInputs = atInputs.getName();
	        	String valueInputs = atInputs.getValue();
	        	if (nameInputs.equals("name")) {	// checking only the 'name' attribute
	        		valueInputsContainer.add(valueInputs);
	        		valueInputsContainer.add("-1");	// magic number to make the length of the valueInputsContainer equal to the valueTestContainer
	        	}
	        }

	        /* 		Just a test. Actual is automation in the input fields using Selenium Web Driver.
	         *		Data that will be automated will come from the <test> / valueTestContainer 
	         *		via driver.sendKeys()
	         */		

	        System.out.println("-- Dummy for Input fields -- \n");

	        Inputs in = new Inputs();
	        int count = 1;

	        // printing the values of the <input> and its expected value from the <test>
	        for (int i = 0; i < nlTestLength; i+=2) {
	        	if ( (valueInputsContainer.get(i)).equals((valueTestContainer.get(i))) ) {
	        		in.name = valueTestContainer.get(i);
	        		in.expectedValue = valueTestContainer.get(i+1);
	        	}

	        	System.out.println("param " + count);
	        	count++;
	        	System.out.println("name: " + in.name);
	        	System.out.println("expected value: " + in.expectedValue + "\n");
	        }
 		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}