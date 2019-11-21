import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import java.util.*;
import weka.core.Instance;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.classifiers.rules.OneR;

//프로그램 개발 환경: Java로 만들어진 Weka api를 사용하기 위해서 eclipse 를 이용해 java로 
//contact-lenses data을 oneR, decisiontree, naivebayes 3가지 알고리즘으로 각각 학습시켰다.
// 이 data의 속성값들은 전부 nominal 하기 때문에 Association rule을 추가로 적용했다.

public class LensesDomain {
   public static void main(String[] args) throws Exception {
      // TODO Auto-generated method stub
      String dataset = "/Program Files/Weka-3-8/data/contact-lenses.arff";
      DataSource source = new DataSource(dataset);// weka의 api를 통해 contact lenses domain의 data를 가져온다.
      Instances trainDataset = source.getDataSet();
      trainDataset.setClassIndex(trainDataset.numAttributes()-1);
      
	  Scanner sc = new Scanner(System.in);
	  System.out.println("======== Select ========"); //속성값들이 전부 nominal 이므로 classification rule 과 association rule 둘 중에서 선택한다.
	  System.out.println("1. Classification");
	  System.out.println("2. Association");
	  System.out.print(">> Select Number : ");
      int num = sc.nextInt();
      System.out.println(" ");
      
      if(num == 1) {//1번을 선택했을 경우
    	System.out.println("====== Classifier Select ======"); //사용할 알과리즘을 선택한다.
  		System.out.println("1. OneR");
  		System.out.println("2. J48");
  		System.out.println("3. Naive Bayes");
  		System.out.print(">> Select Number : ");
  		int algo = sc.nextInt();
  		System.out.println(" ");
  		
  		if (algo == 1) { //OneR
			OneR oneR = new OneR();
			oneR.buildClassifier(trainDataset); // OneR로 학습시킨다.
			System.out.println("===== Classifier output ====="); //결과로 얻은 분류모델의 지식베이스 구축
			System.out.println(oneR);// 분류 결과를 출력한다.
			System.out.println("  ");
		    Evaluation oneREval = new Evaluation(trainDataset);
		    oneREval.crossValidateModel(oneR, trainDataset, 10, new Random(1)); //10 fold로 학습시킨 결과를 출력한다.
		    System.out.println(oneREval.toSummaryString());
		    
		    System.out.println("====== Input New Instance ======");
			Instance newInst = trainDataset.instance(0); 
			for(int i=0;i<trainDataset.numAttributes()-1;i++) {// 새로운 instance를 입력하는 부분
				String name = trainDataset.attribute(i).name();
				System.out.println("<"+ name +">");
				System.out.print("( ");
				for(int j=0;j<trainDataset.attribute(i).numValues();j++) {
				System.out.print(j+1+"."+trainDataset.attribute(i).value(j)+" ");
				}
				System.out.printf(")\n");
				int attr = sc.nextInt();
				if(attr ==1) {
					newInst.setValue(i, trainDataset.attribute(i).value(0));
				}
				else if(attr==2) {
					newInst.setValue(i, trainDataset.attribute(i).value(1));
				}
				else if(attr==3) {
					newInst.setValue(i, trainDataset.attribute(i).value(2));
				}
			}
			
			double predNB = oneR.classifyInstance(newInst);
			String predString = trainDataset.classAttribute().value((int) predNB);
			System.out.println("======== 예측 결과 ========");// 학습을 통해 예측된 결과 출력
			System.out.println(" contact-lenses : " + predString);
		}
		
		else if (algo == 2) { //Decision Tree (코드 설명은 위와 동일하다.)
			J48 tree = new J48();
			tree.buildClassifier(trainDataset);
			System.out.println("===== Classifier output =====");
			System.out.println(tree);
		    Evaluation treeEval = new Evaluation(trainDataset);
		    treeEval.crossValidateModel(tree, trainDataset, 10, new Random(1)); 
		    System.out.println(treeEval.toSummaryString());
		    System.out.println("  ");
		    
		    System.out.println("====== Input New Instance ======");
			Instance newInst = trainDataset.instance(0); 
			for(int i=0;i<trainDataset.numAttributes()-1;i++) {// 새로운 instance를 입력하는 부분
				String name = trainDataset.attribute(i).name();
				System.out.println("<"+ name +">");
				System.out.print("( ");
				for(int j=0;j<trainDataset.attribute(i).numValues();j++) {
				System.out.print(j+1+"."+trainDataset.attribute(i).value(j)+" ");
				}
				System.out.printf(")\n");
				int attr = sc.nextInt();
				if(attr ==1) {
					newInst.setValue(i, trainDataset.attribute(i).value(0));
				}
				else if(attr==2) {
					newInst.setValue(i, trainDataset.attribute(i).value(1));
				}
				else if(attr==3) {
					newInst.setValue(i, trainDataset.attribute(i).value(2));
				}
			}
			double predNB = tree.classifyInstance(newInst);
			String predString = trainDataset.classAttribute().value((int) predNB);
			System.out.println("======== 예측 결과 ========");
			System.out.println(" contact-lenses : " + predString);
		} 
		
		else if (algo == 3) { // naviebayes (코드 설명은 위와 동일하다.)
			int numClasses = trainDataset.numClasses();

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
			for(int i=0;i<trainDataset.numAttributes()-1;i++) {// 새로운 instance를 입력하는 부분
				String name = trainDataset.attribute(i).name();
				System.out.println("<"+ name +">");
				System.out.print("( ");
				for(int j=0;j<trainDataset.attribute(i).numValues();j++) {
				System.out.print(j+1+"."+trainDataset.attribute(i).value(j)+" ");
				}
				System.out.printf(")\n");
				int attr = sc.nextInt();
				if(attr ==1) {
					newInst.setValue(i, trainDataset.attribute(i).value(0));
				}
				else if(attr==2) {
					newInst.setValue(i, trainDataset.attribute(i).value(1));
				}
				else if(attr==3) {
					newInst.setValue(i, trainDataset.attribute(i).value(2));
				}
			}
			double predNB = nb.classifyInstance(newInst);
			String predString = trainDataset.classAttribute().value((int) predNB);
			System.out.println("======== 예측 결과 ========");
			System.out.println(" contact-lenses : " + predString);
		}
      }
      else { //association rule을 적용한 경우
      System.out.println("=======  Association =======");
      Apriori model = new Apriori();
      model.buildAssociations(trainDataset);
      System.out.println(model);
      }
   }
}