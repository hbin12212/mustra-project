import java.util.*;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.classifiers.rules.OneR;

public class WeatherDomain {	 
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DataSource source = new DataSource("/Program Files/Weka-3-8/data/weather.numeric.arff");
		Instances trainDataset = source.getDataSet(); //weather domain data file dml dataset�� �����´�.
		trainDataset.setClassIndex(trainDataset.numAttributes() - 1);
		
		System.out.println("=======   Attributes   ======="); //weather domain�� attribute���� ����Ѵ�.
		for(int i=0;i<trainDataset.numAttributes();i++) {
			if(trainDataset.attribute(i).isNumeric()) { //numeric�� ��� min�� max���� ����Ѵ�.
				String name = trainDataset.attribute(i).name();
				System.out.println("@attribute "+name);
				double minN = trainDataset.attributeStats(i).numericStats.min;
				double maxN = trainDataset.attributeStats(i).numericStats.max;
				System.out.printf("Min: %f, Max: %f\n",minN,maxN);
			}
			else {
				System.out.println(trainDataset.attribute(i));
			}
		}
		System.out.println(" ");
		
		Scanner sc = new Scanner(System.in);
		System.out.println("====== Classifier Select ======"); //�з��� �� ����� �˰����� �����Ѵ�.
		System.out.println("1. OneR");
		System.out.println("2. J48");
		System.out.println("3. Naive Bayes");
		System.out.print(">> Select Number : ");
		int algo = sc.nextInt();
		System.out.println(" ");
		
		if (algo == 1) { //OneR�� ���
			OneR oneR = new OneR();
			oneR.buildClassifier(trainDataset); //OneR�� �н��� ��Ų��.
			System.out.println("===== Classifier output =====");
			System.out.println(oneR); // oneR �� �з� ����� ����Ѵ�.
			System.out.println("  ");
		    Evaluation oneREval = new Evaluation(trainDataset); //
		    oneREval.crossValidateModel(oneR, trainDataset, 10, new Random(1));
		    System.out.println(oneREval.toSummaryString());//10 fold�� �н���Ų ����� ����Ѵ�.
		    
			System.out.println("====== Input New Instance ======");
			System.out.print("Outlook : ");
			String outlook = sc.next();
			System.out.print("Temperature : ");
			int temperature = sc.nextInt();
			System.out.print("Humidity : ");
			int humidity = sc.nextInt();
			System.out.print("Windy : ");
			String windy = sc.next();

			Instance newInst = trainDataset.instance(0); //�Է��� new instance �� �־
			newInst.setValue(0, outlook);
			newInst.setValue(1, temperature);
			newInst.setValue(2, humidity);
			newInst.setValue(3, windy);
			double predNB = oneR.classifyInstance(newInst); // new instance�� ���� class ���� �����Ѵ�.
			String predString = trainDataset.classAttribute().value((int) predNB);
			System.out.println("==============================");
			System.out.println("Play : " + predString); //
		}
		
		else if (algo == 2) { //J48�� ���
			J48 tree = new J48();
			tree.buildClassifier(trainDataset); //J48�� �н��� ��Ų��.
			System.out.println("===== Classifier output =====");
			System.out.println(tree); //J48�� �з��� ����� ����Ѵ�.
		    Evaluation treeEval = new Evaluation(trainDataset);
		    treeEval.crossValidateModel(tree, trainDataset, 10, new Random(1)); //10 fold�� �н���Ų ����� ����Ѵ�.
		    System.out.println(treeEval.toSummaryString());
		    System.out.println("  ");
			System.out.println("====== Input New Instance ======");// ���ο� instance�� �Է��Ѵ�.

			System.out.print("Outlook : ");
			String outlook = sc.next();
			System.out.print("Temperature : ");
			int temperature = sc.nextInt();
			System.out.print("Humidity : ");
			int humidity = sc.nextInt();
			System.out.print("Windy : ");
			String windy = sc.next();
			
			Instance newInst = trainDataset.instance(0);
			newInst.setValue(0, outlook);
			newInst.setValue(1, temperature);
			newInst.setValue(2, humidity);
			newInst.setValue(3, windy);
			double predNB = tree.classifyInstance(newInst);
			String predString = trainDataset.classAttribute().value((int) predNB);
			System.out.println("==============================");
			System.out.println("Play : " + predString);
		} 
		
		else if (algo == 3) {// naiveBayes�� �н���Ų ���
			int numClasses = trainDataset.numClasses(); //���� 2 �˰����� �ڵ� ������ �����ϴ�.
			
			NaiveBayes nb = new NaiveBayes();
			nb.buildClassifier(trainDataset);
			System.out.println("===== Classifier output =====");
			System.out.println(nb);
		    Evaluation nbEval = new Evaluation(trainDataset);
		    nbEval.crossValidateModel(nb, trainDataset, 10, new Random(1)); 
		    System.out.println(nbEval.toSummaryString());
		    System.out.println("  ");
			
			System.out.println("====== Input New Instance ======");

			System.out.print("Outlook : ");
			String outlook = sc.next();
			System.out.print("Temperature : ");
			int temperature = sc.nextInt();
			System.out.print("Humidity : ");
			int humidity = sc.nextInt();
			System.out.print("Windy : ");
			String windy = sc.next();

			Instance newInst = trainDataset.instance(0);
			newInst.setValue(0, outlook);
			newInst.setValue(1, temperature);
			newInst.setValue(2, humidity);
			newInst.setValue(3, windy);
			double predNB = nb.classifyInstance(newInst);
			String predString = trainDataset.classAttribute().value((int) predNB);
			System.out.println("==============================");
			System.out.println("Play : " + predString);
		}
	}

}