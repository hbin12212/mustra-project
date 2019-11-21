import java.util.*;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.classifiers.rules.OneR;

// 프로그램 개발 환경: Java로 만들어진 Weka api를 사용하기 위해서 eclipse 를 이용해 java로 
// weatherdomain_numeric data을 oneR, decisiontree, naivebayes 3가지 알고리즘으로 각각 학습시켰다.

public class WeatherDomain {	 
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DataSource source = new DataSource("/Program Files/Weka-3-8/data/weather.numeric.arff");
		Instances trainDataset = source.getDataSet(); // weka의 api를 이용하여 weather domain data file의  dataset을 가져온다.
		trainDataset.setClassIndex(trainDataset.numAttributes() - 1);
		
		Scanner sc = new Scanner(System.in);
		System.out.println("====== Classifier Select ======"); //분류할 떄 사용할 알고리즘을 선택한다.
		System.out.println("1. OneR");
		System.out.println("2. J48");
		System.out.println("3. Naive Bayes");
		System.out.print(">> Select Number : ");
		int algo = sc.nextInt();
		System.out.println(" ");
		
		if (algo == 1) { //OneR일 경우
			OneR oneR = new OneR();
			oneR.buildClassifier(trainDataset); //OneR로 학습을 시킨다. 
			System.out.println("===== Classifier output ====="); //결과로 얻은 지식베이스 구축
			System.out.println(oneR); // oneR 의 분류 결과를 출력한다.
			System.out.println("  ");
		    Evaluation oneREval = new Evaluation(trainDataset); 
		    oneREval.crossValidateModel(oneR, trainDataset, 10, new Random(1));
		    System.out.println(oneREval.toSummaryString());//10 fold로 학습시킨 결과를 출력한다.
		    
			System.out.println("====== Input New Instance ======");// 새로운 데이터 입력
			Instance newInst = trainDataset.instance(0); 
			for(int i=0;i<trainDataset.numAttributes()-1;i++) {
				if(trainDataset.attribute(i).isNumeric()) { //numeric일 경우 min값 max삾을 출력한다.
					String name = trainDataset.attribute(i).name();
					System.out.println("<"+ name +">");
					double minN = trainDataset.attributeStats(i).numericStats.min;
					double maxN = trainDataset.attributeStats(i).numericStats.max;
					System.out.printf("( Min: %f, Max: %f )\n",minN,maxN);
					int att = sc.nextInt();
					newInst.setValue(i, att);
				}
				else {// nominal일 경우 속성값들 출력
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

			double predNB = oneR.classifyInstance(newInst); // new instance에 대한 class 값을 예측한다.
			String predString = trainDataset.classAttribute().value((int) predNB);
			System.out.println("======== 예측 결과 ========");
			System.out.println("Play : " + predString); 
		}
		
		else if (algo == 2) { //J48일 경우
			J48 tree = new J48();
			tree.buildClassifier(trainDataset); //J48로 학습을 시킨다.
			System.out.println("===== Classifier output =====");
			System.out.println(tree); //J48로 분류한 결과를 출력한다.
		    Evaluation treeEval = new Evaluation(trainDataset);
		    treeEval.crossValidateModel(tree, trainDataset, 10, new Random(1)); //10 fold로 학습시킨 결과를 출력한다.
		    System.out.println(treeEval.toSummaryString());
		    System.out.println("  ");
			System.out.println("====== Input New Instance ======");
			Instance newInst = trainDataset.instance(0); //입력한 new instance 를 넣어서
			for(int i=0;i<trainDataset.numAttributes()-1;i++) {
				if(trainDataset.attribute(i).isNumeric()) { //numeric일 경우 min값 max삾을 출력한다.
					String name = trainDataset.attribute(i).name();
					System.out.println("<"+ name +">");
					double minN = trainDataset.attributeStats(i).numericStats.min;
					double maxN = trainDataset.attributeStats(i).numericStats.max;
					System.out.printf("( Min: %f, Max: %f )\n",minN,maxN);
					int att = sc.nextInt();
					newInst.setValue(i, att);
				}
				else {// nominal일 경우 해당 속성의 값들을 출력
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
			double predNB = tree.classifyInstance(newInst);// new instance 학습
			String predString = trainDataset.classAttribute().value((int) predNB);
			System.out.println("======== 예측 결과 ========");
			System.out.println("Play : " + predString);
		} 
		
		else if (algo == 3) {// naiveBayes로 학습시킨 경우
			int numClasses = trainDataset.numClasses(); //위의  알고리즘들과 코드 설명이 동일하다.
			
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
			System.out.println("======== 예측 결과 ========");
			System.out.println("Play : " + predString);
		}
	}

}