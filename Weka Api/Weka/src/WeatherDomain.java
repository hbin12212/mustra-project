import java.util.*;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.classifiers.rules.OneR;

// ���α׷� ���� ȯ��: Java�� ������� Weka api�� ����ϱ� ���ؼ� eclipse �� �̿��� java�� 
// weatherdomain_numeric data�� oneR, decisiontree, naivebayes 3���� �˰������� ���� �н����״�.

public class WeatherDomain {	 
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DataSource source = new DataSource("/Program Files/Weka-3-8/data/weather.numeric.arff");
		Instances trainDataset = source.getDataSet(); // weka�� api�� �̿��Ͽ� weather domain data file��  dataset�� �����´�.
		trainDataset.setClassIndex(trainDataset.numAttributes() - 1);
		
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
			System.out.println("===== Classifier output ====="); //����� ���� ���ĺ��̽� ����
			System.out.println(oneR); // oneR �� �з� ����� ����Ѵ�.
			System.out.println("  ");
		    Evaluation oneREval = new Evaluation(trainDataset); 
		    oneREval.crossValidateModel(oneR, trainDataset, 10, new Random(1));
		    System.out.println(oneREval.toSummaryString());//10 fold�� �н���Ų ����� ����Ѵ�.
		    
			System.out.println("====== Input New Instance ======");// ���ο� ������ �Է�
			Instance newInst = trainDataset.instance(0); 
			for(int i=0;i<trainDataset.numAttributes()-1;i++) {
				if(trainDataset.attribute(i).isNumeric()) { //numeric�� ��� min�� max���� ����Ѵ�.
					String name = trainDataset.attribute(i).name();
					System.out.println("<"+ name +">");
					double minN = trainDataset.attributeStats(i).numericStats.min;
					double maxN = trainDataset.attributeStats(i).numericStats.max;
					System.out.printf("( Min: %f, Max: %f )\n",minN,maxN);
					int att = sc.nextInt();
					newInst.setValue(i, att);
				}
				else {// nominal�� ��� �Ӽ����� ���
					String name = trainDataset.attribute(i).name();
					System.out.println("<"+ name +">");
					System.out.print("( ");
					for(int j=0;j<trainDataset.attribute(i).numValues();j++) {
					System.out.print(trainDataset.attribute(i).value(j)+" ");
					}
					System.out.printf(")\n");
					String attr = sc.next();
					newInst.setValue(i, attr);
				}
			}

			double predNB = oneR.classifyInstance(newInst); // new instance�� ���� class ���� �����Ѵ�.
			String predString = trainDataset.classAttribute().value((int) predNB);
			System.out.println("======== ���� ��� ========");
			System.out.println("Play : " + predString); 
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
			System.out.println("====== Input New Instance ======");
			Instance newInst = trainDataset.instance(0); //�Է��� new instance �� �־
			for(int i=0;i<trainDataset.numAttributes()-1;i++) {
				if(trainDataset.attribute(i).isNumeric()) { //numeric�� ��� min�� max���� ����Ѵ�.
					String name = trainDataset.attribute(i).name();
					System.out.println("<"+ name +">");
					double minN = trainDataset.attributeStats(i).numericStats.min;
					double maxN = trainDataset.attributeStats(i).numericStats.max;
					System.out.printf("( Min: %f, Max: %f )\n",minN,maxN);
					int att = sc.nextInt();
					newInst.setValue(i, att);
				}
				else {// nominal�� ��� �ش� �Ӽ��� ������ ���
					String name = trainDataset.attribute(i).name();
					System.out.println("<"+ name +">");
					System.out.print("( ");
					for(int j=0;j<trainDataset.attribute(i).numValues();j++) {
					System.out.print(trainDataset.attribute(i).value(j)+" ");
					}
					System.out.printf(")\n");
					String attr = sc.next();
					newInst.setValue(i, attr);
				}
			}
			double predNB = tree.classifyInstance(newInst);// new instance �н�
			String predString = trainDataset.classAttribute().value((int) predNB);
			System.out.println("======== ���� ��� ========");
			System.out.println("Play : " + predString);
		} 
		
		else if (algo == 3) {// naiveBayes�� �н���Ų ���
			int numClasses = trainDataset.numClasses(); //����  �˰����� �ڵ� ������ �����ϴ�.
			
			NaiveBayes nb = new NaiveBayes();
			nb.buildClassifier(trainDataset);
			System.out.println("===== Classifier output =====");
			System.out.println(nb);
		    Evaluation nbEval = new Evaluation(trainDataset);
		    nbEval.crossValidateModel(nb, trainDataset, 10, new Random(1)); 
		    System.out.println(nbEval.toSummaryString());
		    System.out.println("  ");
			
			System.out.println("====== Input New Instance ======");
			Instance newInst = trainDataset.instance(0); 
			for(int i=0;i<trainDataset.numAttributes()-1;i++) {
				if(trainDataset.attribute(i).isNumeric()) { 
					String name = trainDataset.attribute(i).name();
					System.out.println("<"+ name +">");
					double minN = trainDataset.attributeStats(i).numericStats.min;
					double maxN = trainDataset.attributeStats(i).numericStats.max;
					System.out.printf("( Min: %f, Max: %f )\n",minN,maxN);
					int att = sc.nextInt();
					newInst.setValue(i, att);
				}
				else {
					String name = trainDataset.attribute(i).name();
					System.out.println("<"+ name +">");
					System.out.print("( ");
					for(int j=0;j<trainDataset.attribute(i).numValues();j++) {
					System.out.print(trainDataset.attribute(i).value(j)+" ");
					}
					System.out.printf(")\n");
					String attr = sc.next();
					newInst.setValue(i, attr);
				}
			}
			double predNB = nb.classifyInstance(newInst);
			String predString = trainDataset.classAttribute().value((int) predNB);
			System.out.println("======== ���� ��� ========");
			System.out.println("Play : " + predString);
		}
	}

}