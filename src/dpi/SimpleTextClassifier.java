package dpi;

import java.io.File;
import java.io.IOException;

import com.aliasi.classify.Classifier;
import com.aliasi.classify.ClassifierEvaluator;
import com.aliasi.classify.ConfusionMatrix;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.JointClassification;
import com.aliasi.lm.NGramProcessLM;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Files;

public class SimpleTextClassifier {
	private static String[] CATEGORIES = { "NewsText", "PolymerPapers" };
	private static String currentDir = System.getProperty("user.dir");
	private static int NGRAM_SIZE = 6;

	private static File TRAINING_DIR = new File(currentDir + "/Training");
	private static File TESTING_DIR = new File(currentDir + "/Testing");
	private DynamicLMClassifier<NGramProcessLM> classifier;
	private Classifier<CharSequence, JointClassification> compiledClassifier;

	public DynamicLMClassifier<NGramProcessLM> trainClassifier()
			throws IOException {
		classifier = DynamicLMClassifier.createNGramProcess(CATEGORIES,
				NGRAM_SIZE);
		for (int i = 0; i < CATEGORIES.length; ++i) {
			File classDir = new File(TRAINING_DIR, CATEGORIES[i]);
			if (!classDir.isDirectory()) {
				String msg = "Could not find training directory=" + classDir
						+ "\nHave you unpacked 4 newsgroups?";
				System.out.println(msg); // in case exception gets lost in shell
				throw new IllegalArgumentException(msg);
			}
			String[] trainingFiles = classDir.list();
			for (int j = 0; j < trainingFiles.length; ++j) {
				File file = new File(classDir, trainingFiles[j]);
				String text = Files.readFromFile(file, "ISO-8859-1");
				System.out.println("Training on " + CATEGORIES[i] + "/"
						+ trainingFiles[j]);
				classifier.train(CATEGORIES[i], text);
			}
		}
		return classifier;

	}

	private Classifier<CharSequence, JointClassification> compileClassifier()
			throws ClassNotFoundException, IOException {
		compiledClassifier = (Classifier<CharSequence, JointClassification>) AbstractExternalizable
				.compile(classifier);
		return compiledClassifier;
	}

	public void evaluateClassifier() throws IOException, ClassNotFoundException {
		compileClassifier();
		ClassifierEvaluator<CharSequence, JointClassification> evaluator = new ClassifierEvaluator<CharSequence, JointClassification>(
				compiledClassifier, CATEGORIES);
		for (int i = 0; i < CATEGORIES.length; ++i) {
			File classDir = new File(TESTING_DIR, CATEGORIES[i]);
			String[] testingFiles = classDir.list();
			for (int j = 0; j < testingFiles.length; ++j) {
				String text = Files.readFromFile(new File(classDir,
						testingFiles[j]), "ISO-8859-1");
				System.out.print("Testing on " + CATEGORIES[i] + "/"
						+ testingFiles[j] + " ");
				evaluator.addCase(CATEGORIES[i], text);
				JointClassification jc = compiledClassifier.classify(text);
				String bestCategory = jc.bestCategory();
				String details = jc.toString();
				System.out.println("Got best category of: " + bestCategory);
				System.out.println(jc.toString());
				System.out.println("---------------");
			}
		}
		ConfusionMatrix confMatrix = evaluator.confusionMatrix();
		System.out.println("Total Accuracy: " + confMatrix.totalAccuracy());

	}
	
}
