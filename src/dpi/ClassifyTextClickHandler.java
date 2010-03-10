package dpi;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import com.aliasi.classify.Classifier;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.JointClassification;
import com.aliasi.lm.NGramProcessLM;
import com.aliasi.util.AbstractExternalizable;

public class ClassifyTextClickHandler implements MouseListener {

	public void mouseClicked(MouseEvent arg0) {
		SimpleTextClassifier textClassifier = new SimpleTextClassifier();
		try {
			DynamicLMClassifier<NGramProcessLM> classifier = textClassifier.trainClassifier();
			//Get text from text Area.
			String inputText = TextClassificationApplication.getTextAreaInformation();
			Classifier<CharSequence,JointClassification> compiledClassifier
		    = (Classifier<CharSequence,JointClassification>)
		      AbstractExternalizable.compile(classifier);
			JointClassification jc = compiledClassifier.classify(inputText);
			String bestCategory = jc.bestCategory();
			String details = jc.toString();
			TextClassificationApplication.setClassification(bestCategory);
			System.out.println(bestCategory);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {

	}

}
