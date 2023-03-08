package Owa;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream; 
import ilog.concert.*;
import ilog.cplex.*;

import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.SolveCallback;

import java.io.*;
import java.lang.invoke.LambdaConversionException;
import java.util.List;
import java.util.Scanner;
import java.math.BigDecimal;


public class owa_weightedsum {
	
	static boolean feasible;
    static double epsilon;
    static double stepsize;
    static double z2;
    static int numberOfIterations=0;
    static int numberofModelsSolved=0;
    
    static double[]ED = {0, 0, 0}; //minimum total value in that benefit type (evaluated by) 
    static double[]  EY={24599,17083,23437};
    static double[]  Utility = {0,2188.430565,2800,3173,3489,3753.579963,3983.3461,4187.659671,4372.444494,4541.695356,4698.24714};//{0,21.88430565,27.71322201,31.7289988,34.88945984,37.53579963,39.833461,41.87659671,43.72444494,45.41695356,46.9824714};//{0	,19.63	,21.04	,21.91058727	,22.55006986	,23.05891631	,23.48318598	,23.84798537	,24.16856646,	24.4549142,	24.71393457	,24.95060961};//arlik:5{0,	21.04	,22.55,	23.48318598,	24.16856646,	24.71393457	,25.16865555};//{0,	18.32,	19.63,	20.44330078	,21.03995914,	21.51472966,	21.91058727,	22.25095713,	22.55006986	,22.81724176	,23	,23.28,	23.48	,23.67190604	,23.84798537,	24.01308836,	24.16856646	,24.31553251,	24.4549142	,24.58749322	,24.71393457};// {0,21.88,	27.71,	31.73,	34.89,	37.54,	39.83,	41.88,	43.72,	45.42,	46.98};//Alpha=0.1{0,480,	897,	1292,	1674,	2047,	2412,	2771,	3125,	3475,	3820}; //Alpha=0.5{0,56.31,	80.46,	99.00,	114.62,	128.38,	140.83,	152.27,	162.92,	172.93,	182.39};//Alpha=0.9{0,9.63,	11.04,	11.91,	12.55,	13.06,	13.48,	13.85,	14.17,	14.45,	14.71};
    static double[] Threshold = {0,850,1700,2550,3400,4250,5100,5950,6800,7650,8500,9350};//aralik:5{0,1700,3400,5100,6800,8500,10200};//aralik:20{0,425,850,1275,1700,2125,2550,2975,3400,3825,4250,4675,5100,5525,5950,6375,6800,7225,7650,8075,8500};//{0,250,500,750,1000,1250,1500,1750,2000,2250,2500};
    static double [][] L = {{850,850,850,850,850,850,850,850,850,850},{850,850,850,850,850,850,850,850,850,850}};//{{1700,1700,1700,1700,1700},{1700,1700,1700,1700,1700}};//{{425,425,425,425,425,425,425,425,425,425,425,425,425,425,425,425,425,425,425,425},{425,425,425,425,425,425,425,425,425,425,425,425,425,425,425,425,425,425,425,425}}; //{{850,850,850,850,850,850,850,850,850,850},{850,850,850,850,850,850,850,850,850,850}};
    static int NumberOfGap= 10;
    
    static int NumberOfDistrictGroup = 0 ; //number of patient group
    static int PossibleCourseLocations = 0; //servise location places
    static int TypeOfCourse = 0; //hospital type
    static int [][] C; //cost of project
     
    //static int[][][] p;
    
    public static BufferedReader br;
  
	public static void main(String[] args) throws Exception {   	
		 long startLP = System.nanoTime();
	     double timeStart = System.nanoTime();
	     				
		
	     ////Read Patient group
	        String input="";
	        try{
	        	br=new BufferedReader(new FileReader("C:\\Users\\dakol\\Desktop\\NewData\\Entity number.txt"));
	            input=br.readLine();
	            String [] paper=input.split(" ");
	            NumberOfDistrictGroup = paper.length;
	            
	            System.out.println(NumberOfDistrictGroup);
	        }catch(IOException e){
	            e.printStackTrace();
	        }
			
	    ////Read hospital(Benefit) type
	        String input1="";
	        try{
	        	br=new BufferedReader(new FileReader("C:\\Users\\dakol\\Desktop\\NewData\\Course type.txt"));
	            input1=br.readLine();
	            String [] type=input1.split(" ");
	            TypeOfCourse =type.length;
	            System.out.println(TypeOfCourse);
	       

	        }catch(IOException e){
	            e.printStackTrace();
	        }
	        
	        
	        //Read locations
	        String input2="";
	        try{
	        	br=new BufferedReader(new FileReader("C:\\Users\\dakol\\Desktop\\NewData\\location set.txt"));
	            input2=br.readLine();
	            String [] ref=input2.split(" ");
	            PossibleCourseLocations = ref.length;
	            
	           System.out.println(PossibleCourseLocations); 
	    
	        }catch(IOException e){
	            e.printStackTrace();
	        }
        	

				//Read which patient group in which location typ
				Scanner pscanner = new Scanner(new File("C:\\Users\\dakol\\Desktop\\NewData\\p_0jk - Copy.txt"));
		        int [][] p1=new int [PossibleCourseLocations][NumberOfDistrictGroup];
				for(int j = 0; j<PossibleCourseLocations; j++) {
					for(int k =0; k<NumberOfDistrictGroup; k++) {
						p1[j][k] = pscanner.nextInt();
						}
					}
						
			//Read how many patient group in which location type
			Scanner p2scanner = new Scanner(new File("C:\\Users\\dakol\\Desktop\\NewData\\p_1jk - Copy.txt"));
			int [][] p2=new int [PossibleCourseLocations][NumberOfDistrictGroup];
			for(int j = 0; j<PossibleCourseLocations; j++) {
				for(int k =0; k<NumberOfDistrictGroup; k++) {
					p2[j][k] = p2scanner.nextInt();
						}
					}
			 
			Scanner	p3scanner = new Scanner(new File("C:\\Users\\dakol\\Desktop\\NewData\\p_2jk - Copy.txt"));
			int [][] p3=new int [PossibleCourseLocations][NumberOfDistrictGroup];
			for(int j = 0; j<PossibleCourseLocations; j++) {
				for(int k =0; k<NumberOfDistrictGroup; k++) {
					p3[j][k] = p3scanner.nextInt();
					System.out.println(p3[j][k]);
						}
					}
								
								

				int[][][] p = new int[TypeOfCourse][PossibleCourseLocations][NumberOfDistrictGroup];
							    
							        for(int j = 0; j<PossibleCourseLocations; j++) {
										for(int k =0; k<NumberOfDistrictGroup; k++) {
							            p[0][j][k]=p1[j][k];
							        } }
							    
							        for(int j = 0; j<PossibleCourseLocations; j++) {
										for(int k =0; k<NumberOfDistrictGroup; k++) {
							            p[1][j][k]=p2[j][k];
							        } }
							        for(int j = 0; j<PossibleCourseLocations; j++) {
										for(int k =0; k<NumberOfDistrictGroup; k++) {
							            p[2][j][k]=p3[j][k];
							        } }
				     
				
													
					
		// cost of projects	
		Scanner Uscanner = new Scanner(new File("C:\\Users\\dakol\\Desktop\\NewData\\Cost3Groups.txt"));
        C=new int [TypeOfCourse][PossibleCourseLocations];
        for(int i =0; i<TypeOfCourse; i++) {
        	for(int j = 0; j<PossibleCourseLocations; j++) {
				C[i][j] = Uscanner.nextInt();
			   	System.out.println(C[2][j]);
				} }
		
			

 

  
        //feasible=true;
       // double total2 = 1;
        double alpha1=1;
        double alpha2=0;
        
                  //***************MODEL*********//
  
        while(alpha1<=1) {	
        	
			try {
				IloCplex cplex = new IloCplex(); //defining cplex
				cplex.setParam(IloCplex.DoubleParam.EpGap, 0);
				numberofModelsSolved++;

			  //**********DEFINE DECISION VARIBLES**************/		
				cplex.setParam(IloCplex.Param.MIP.Tolerances.MIPGap, 0);  //1e-10

				IloNumVar[][] y = new IloNumVar[TypeOfCourse][PossibleCourseLocations];
				for (int i=0; i<TypeOfCourse;i++){
					for (int j=0;j<PossibleCourseLocations;j++){
						y[i][j]= cplex.boolVar();
						y[i][j].setName("y"+Integer.toString(i)+Integer.toString(j));
					}
				}
		
				
				//X_bar is coming 
				IloNumVar[][][]X_bar = new IloNumVar [TypeOfCourse][NumberOfDistrictGroup][NumberOfGap];
				for (int i=0; i<TypeOfCourse;i++){
					for(int b=0;b<NumberOfDistrictGroup;b++) {
						for (int m=0;m<NumberOfGap;m++){					
							X_bar[i][b][m]= cplex.numVar(0, 850,IloNumVarType.Float );//1000
							X_bar[i][b][m].setName("X_bar"+Integer.toString(i)+Integer.toString(b)+Integer.toString(m));
							}
						}
					} 
				

				//utilities
				IloNumVar[][] u = new IloNumVar[TypeOfCourse][NumberOfDistrictGroup];
				for (int i=0; i<TypeOfCourse;i++){
					for (int b=0;b<NumberOfDistrictGroup;b++) {
						u[i][b]= cplex.numVar(0, Double.MAX_VALUE,IloNumVarType.Float );
						u[i][b].setName("u"+Integer.toString(i)+Integer.toString(b));
					}

				}
				
				IloNumVar[][] z = new IloNumVar[TypeOfCourse][NumberOfDistrictGroup];
				for (int i=0; i<TypeOfCourse;i++){
					for (int k=0;k<NumberOfDistrictGroup;k++){
						z[i][k]= cplex.numVar(0, Double.MAX_VALUE,IloNumVarType.Float );
						z[i][k].setName("z"+Integer.toString(i)+Integer.toString(k));
					}
				}

             //***********************OBJECTIVES*********************/
				
						
			  //objective 1 (Maximize fairness)
				IloLinearNumExpr obj1 = cplex.linearNumExpr();
				//IloLinearNumExpr obj1 = cplex.linearNumExpr();
					for(int b=0 ; b<NumberOfDistrictGroup; b++) {
						obj1.addTerm(alpha1,u[0][b]);
						obj1.addTerm(alpha2,u[1][b]);
						obj1.addTerm((1-alpha1-alpha2),u[2][b]);
					}
				
					cplex.addMaximize(obj1 );
					System.out.println("alpha1");
					System.out.println("alpha2");
						
         		////////Real CONSTRAINTS///////
			
				//0
				for(int i=0; i<TypeOfCourse; i++) {
					for(int b=0 ; b<NumberOfDistrictGroup; b++) {
						IloLinearNumExpr cons0 = cplex.linearNumExpr();
						cons0.addTerm( 1, z[i][b]);
						for(int j=0;j<NumberOfGap;j++) {			
							cons0.addTerm( -1, X_bar[i][b][j]);
						}
						cplex.addEq(cons0, 0);
						cons0.clear();
					}
				}
				
				//7
				for(int i=0; i<TypeOfCourse; i++) {
					for(int b=0 ; b<NumberOfDistrictGroup; b++) {
						IloLinearNumExpr cons7 = cplex.linearNumExpr();
						cons7.addTerm( 1, u[i][b]);
						for(int j=0;j<NumberOfGap;j++) {			
							cons7.addTerm( -((Utility[j+1]-Utility[j])/850), X_bar[i][b][j]);
						//	System.out.println((Utility[j+1]-Utility[j])/850);
						//	System.out.println("DFFERENCE");
						}
						cplex.addEq(cons7, 0);
						cons7.clear();
					}
				}
				
				//8 cost constraint 
				IloLinearNumExpr cons8 = cplex.linearNumExpr();
				for(int i=0 ; i<TypeOfCourse; i++) {
					for(int j=0;j<PossibleCourseLocations;j++) {	
						cons8.addTerm( C[i][j] , y[i][j]); //cons8.addTerm( 1 , (cplex.sum(x[k][j],-U[j])));			
					} 
	               //if i put here that means in each hospital type totals should be separately smaller than the number
				}
				cplex.addLe(cons8,13371);
				cons8.clear();
				
								
				//1
				for(int i=0; i<TypeOfCourse; i++) {
					for(int k=0 ; k<NumberOfDistrictGroup; k++) {
						IloLinearNumExpr cons1 = cplex.linearNumExpr();
						cons1.addTerm(-1, z[i][k]);
						for(int j=0;j<PossibleCourseLocations;j++) {
						//	System.out.println(cplex.prod(g[j][i]*Q[j][k],x[k][j]));
							//double Qg = g[j][i]*Q[j][k];
							cons1.addTerm(p[i][j][k], y[i][j]); //cons1.addTerm(g[j][i]*Q[j][k], x[k][j]);
						
						}
						cplex.addEq(cons1, 0);
						cons1.clear();
					}

				}
	
			
				
					////SOLUTIONS AND CONSOLE PRINTS////
						
				cplex.exportModel("NewData_owa.lp");
				cplex.solve();
				
				
				
				 
				 //print the objective value
				System.out.println("objective: " + cplex.getObjValue());

				
				for(int i=0;i<TypeOfCourse;i++){
					for (int j=0;j<PossibleCourseLocations;j++){
						if(cplex.getValue(y[i][j])>=0){
							System.out.println("y[" + (i) + "][" + (j) + "]="+cplex.getValue(y[i][j]));
						}
					}
				}


				
				
				for(int i=0;i<TypeOfCourse;i++){
					for (int k=0;k<NumberOfDistrictGroup;k++){
						if(cplex.getValue(z[i][k])>=0){
							System.out.println("z[" + (i) + "][" + (k) + "]="+cplex.getValue(z[i][k]));
						}
					}
				}
				
				
			
	
	
		
                
                double[][] A = new double[NumberOfDistrictGroup][TypeOfCourse];
                double total3=0;
                double total4=0;
                double total5=0;

                
                double total6=0;
                double total7=0;
                double total8=0;
                
                double total16=0;
                double total17=0;
                double total18=0;

                
                //for(int i=0;i<TypeOfCourse;i++){
				for (int j=0;j<PossibleCourseLocations;j++){	
					if(cplex.getValue(y[0][j])>=0.9){
					total3 = total3 + (p[0][j][0]);	
					total4 = total4 + (p[0][j][1]);	
					total5 = total5 + (p[0][j][2]);	

										
					A[0][0]=total3;	
					A[1][0]=total4;
					A[2][0]=total5;
																			
						}
					}
				
				for (int j=0;j<PossibleCourseLocations;j++){	
					if(cplex.getValue(y[1][j])>=0.9){
						total6 = total6 + (p[1][j][0]);	
						total7 = total7 + (p[1][j][1]);	
						total8 = total8 + (p[1][j][2]);	

						
						A[0][1]=total6;
						A[1][1]=total7;
						A[2][1]=total8;

						}
					}
				
				for (int j=0;j<PossibleCourseLocations;j++){	
					if(cplex.getValue(y[2][j])>=0.9){
						total16 = total16 + (p[2][j][0]);	
						total17 = total17 + (p[2][j][1]);	
						total18 = total18 + (p[2][j][2]);	

						
						A[0][2]=total16;
						A[1][2]=total17;
						A[2][2]=total18;

						}
					}
					
						System.out.println(total3);
						System.out.println(total4);
						System.out.println(total5);
		
						
						System.out.println(total6);
						System.out.println(total7);
						System.out.println(total8);
					
         
	                    
	              
                
				         	
				            	
					FileWriter fileWriter = new FileWriter("C:\\Users\\dakol\\Desktop\\OWA_weighted_z_value.txt", true);
					fileWriter.write(String.valueOf(total3));
					fileWriter.write("\n");
					fileWriter.write(String.valueOf(total4));
					fileWriter.write("\n");
					fileWriter.write(String.valueOf(total5));
					fileWriter.write("\n");
					fileWriter.write(String.valueOf(total6));
					fileWriter.write("\n");
					fileWriter.write(String.valueOf(total7));
					fileWriter.write("\n");
					fileWriter.write(String.valueOf(total8));
					fileWriter.write("\n");
					fileWriter.write(String.valueOf(total16));
					fileWriter.write("\n");
					fileWriter.write(String.valueOf(total17));
					fileWriter.write("\n");
					fileWriter.write(String.valueOf(total18));
					fileWriter.write("\n");
					fileWriter.write("\n");
					fileWriter.write("\n");
                    fileWriter.close();
    	        
              alpha1++;
                
			   } catch (IloException e) {
	                e.printStackTrace();
	            } 
			}
      
        long stopLP = System.nanoTime();
        System.err.println("Solution time       = " + String.format("%.2f",(stopLP - startLP) / 1000000000.0));
        System.err.println("# of models solved  = " + numberofModelsSolved);
        
	        }

}
