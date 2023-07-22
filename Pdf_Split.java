import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.Splitter;

// Used PDF Box Library to perform the operations inside the pdf

public class Pdf_Split {

	public static void main(String[]args) throws IOException, COSVisitorException {
		
		
//		address of the old file 
		File oldFile =new File("C:\\pdf\\sample.pdf");
		PDDocument document = PDDocument.load(oldFile);
		
		Splitter splitter = new Splitter();
		
		List<PDDocument>splitPages = splitter.split(document);
		
		int num=1;
		for(PDDocument mydoc : splitPages) {
			mydoc.save("C:\\pdf\\extract\\split_0"+num+".pdf");
			num++;
			mydoc.close();
		}
		// output message to show in the console
		System.out.println("Your Pdf File has been Splitted");
	}
	
}
