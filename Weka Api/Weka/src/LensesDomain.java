import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import java.util.*;
import weka.core.Instance;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.classifiers.rules.OneR;

public class LensesDomain {
   public static void main(String[] args) throws Exception {
      // TODO Auto-generated method stub
      String dataset = "/Program Files/Weka-3-8/data/contact-lenses.arff";
      DataSource source = new DataSource(dataset);// contact lenses domain�� data�� �����´�.
      Instances trainDataset = source.getDataSet();
      trainDataset.setClassIndex(trainDataset.numAttributes()-1);
      System.out.println("=======   Attributes   ======="); //contact lense domain�� atrribute���� ����Ѵ�.
		for(int i=0;i<trainDataset.numAttributes();i++) {
				System.out.println(trainDataset.attribute(i));
		}
		System.out.println(" ");
      
	  Scanner sc = new Scanner(System.in);
	  System.out.println("======== Select ========"); //�Ӽ������� ���� nominal �̹Ƿ� classification rule �� association rule �� �߿��� �����Ѵ�.
	  System.out.println("1. Classification");
	  System.out.println("2. Association");
	  System.out.print(">> Select Number : ");
      int num = sc.nextInt();
      System.out.println(" ");
      
      if(num == 1) {
    	System.out.println("====== Classifier Select ======"); //����� �˰������� �����Ѵ�.
  		System.out.println("1. OneR");
  		System.out.println("2. J48");
  		System.out.println("3. Naive Bayes");
  		System.out.print(">> Select Number : ");
  		int algo = sc.nextInt();
  		System.out.println(" ");
  		
  		if (algo == 1) { //OneR
			OneR oneR = new OneR();
			oneR.buildClassifier(trainDataset); // OneR�� �н���Ų��.
			System.out.println("===== Classifier output =====");
			System.out.println(oneR);// �з� ����� ����Ѵ�.
			System.out.println("  ");
		    Evaluation oneREval = new Evaluation(trainDataset);
		    oneREval.crossValidateModel(oneR, trainDataset, 10, new Random(1)); //10 fold�� �н���Ų ����� ����Ѵ�.
		    System.out.println(oneREval.toSummaryString());
		    
			System.out.println("====== Input New Instance ======"); // ���ο� instance�� �Է��Ѵ�.
			System.out.print("Age : ");
			String age = sc.next();
			System.out.print("Spectacle-prescrip : ");
			String spec = sc.next();
			System.out.print("Astigmaism : ");
			String astig = sc.next();
			System.out.print("Tear-prod-rate : ");
			String tear = sc.next();

			Instance newInst = trainDataset.instance(0); //���ο� instance ���� �ְ� �ش� class ���� �����Ѵ�.
			newInst.setValue(0, age);
			newInst.setValue(1, spec);
			newInst.setValue(2, astig);
			newInst.setValue(3, tear);
			double predNB = oneR.classifyInstance(newInst);
			String predString = trainDataset.classAttribute().value((int) predNB);
			System.out.println("==============================");
			System.out.println("Play : " + predString);
		}
		
		else if (algo == 2) { //Decision Tree (�ڵ� ������ ���� �����ϴ�.)
			J48 tree = new J48();
			tree.buildClassifier(trainDataset);
			System.out.println("===== Classifier output =====");
			System.out.println(tree);
		    Evaluation treeEval = new Evaluation(trainDataset);
		    treeEval.crossValidateModel(tree, trainDataset, 10, new Random(1)); 
		    System.out.println(treeEval.toSummaryString());
		    System.out.println("  ");
			System.out.println("====== Input New Instance ======");
			System.out.print("Age : ");
			String age = sc.next();
			System.out.print("Spectacle-prescrip : ");
			String spec = sc.next();
			System.out.print("Astigmaism : ");
			String astig = sc.next();
			System.out.print("Tear-prod-rate : ");
			String tear = sc.next();

			Instance newInst = trainDataset.instance(0);
			newInst.setValue(0, age);
			newInst.setValue(1, spec);
			newInst.setValue(2, astig);
			newInst.setValue(3, tear);
			double predNB = tree.classifyInstance(newInst);
			String predString = trainDataset.classAttribute().value((int) predNB);
			System.out.println("==============================");
			System.out.println("Play : " + predString);
		} 
		
		else if (algo == 3) { // naviebayes (�ڵ� ������ ���� �����ϴ�.)
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
			System.out.print("Age : ");
			String age = sc.next();
			System.out.print("Spectacle-prescrip : ");
			String spec = sc.next();
			System.out.print("Astigmaism : ");
			String astig = sc.next();
			System.out.print("Tear-prod-rate : ");
			String tear = sc.next();

			Instance newInst = trainDataset.instance(0);
			newInst.setValue(0, age);
			newInst.setValue(1, spec);
			newInst.setValue(2, astig);
			newInst.setValue(3, tear);
			double predNB = nb.classifyInstance(newInst);
			String predString = trainDataset.classAttribute().value((int) predNB);
			System.out.println("==============================");
			System.out.println("Play : " + predString);
		}
      }
      else { //association���� �н��� ���
      System.out.println("=======  Association =======");
      Apriori model = new Apriori();
      model.buildAssociations(trainDataset);
      System.out.println(model);
      }
   }
}