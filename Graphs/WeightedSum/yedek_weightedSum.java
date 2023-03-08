package yedek;
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


public class yedek_weightedSum {

	
	static boolean feasible;
    static double epsilon;
    static double stepsize;
    static double z2;
    static int numberOfIterations=0;
    static int numberofModelsSolved=0;
    
    static double[]ED = {0, 0, 0}; //minimum total value in that benefit type (evaluated by) 
    static double[]  EY={24599,17083,23437};
    static double[] Utility = {0,2188.430565,2800,3173,3489,3753.579963,3983.3461,4187.659671,4372.444494,4541.695356,4698.24714};//Utility = {0,8,15,21,26,30,33,35,36,36.5,36.75};//Utility = {0,8,15,21,26,30,33,35,36,36.5,36.75};
    static double[] Threshold = {0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1,1.1};
    static int NumberOfGap= 10; 
    
    static int NumberOfDistrictGroup = 0 ; //number of patient group
    
    
    static int PossibleCourseLocations = 0; 
    static int TypeOfCourse = 0; 
    static int [][] C; 
  
    
    public static BufferedReader br;
  
    
	
    
	public static void main(String[] args) throws Exception  {		
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
	        	

					//Read which patient group in which location type
					Scanner pscanner = null;
					try {
						pscanner = new Scanner(new File("C:\\Users\\dakol\\Desktop\\NewData\\p_0jk - Copy.txt"));
					} catch (FileNotFoundException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
			        int [][] p1=new int [PossibleCourseLocations][NumberOfDistrictGroup];
					for(int j = 0; j<PossibleCourseLocations; j++) {
						for(int k =0; k<NumberOfDistrictGroup; k++) {
							p1[j][k] = pscanner.nextInt();
							}
						}
							
				//Read how many patient group in which location type
				Scanner p2scanner = null;
				try {
					p2scanner = new Scanner(new File("C:\\Users\\dakol\\Desktop\\NewData\\p_1jk - Copy.txt"));
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				int [][] p2=new int [PossibleCourseLocations][NumberOfDistrictGroup];
				for(int j = 0; j<PossibleCourseLocations; j++) {
					for(int k =0; k<NumberOfDistrictGroup; k++) {
						p2[j][k] = p2scanner.nextInt();
							}
						}
				
				Scanner p3scanner = null;
				try {
					p3scanner = new Scanner(new File("C:\\Users\\dakol\\Desktop\\NewData\\p_2jk - Copy.txt"));
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				int [][] p3=new int [PossibleCourseLocations][NumberOfDistrictGroup];
				for(int j = 0; j<PossibleCourseLocations; j++) {
					for(int k =0; k<NumberOfDistrictGroup; k++) {
						p3[j][k] = p3scanner.nextInt();
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
					
		
			Scanner Uscanner = null;
			try {
				Uscanner = new Scanner(new File("C:\\Users\\dakol\\Desktop\\NewData\\Cost3Groups.txt"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        C=new int [TypeOfCourse][PossibleCourseLocations];
	        for(int i =0; i<TypeOfCourse; i++) {
	        	for(int j = 0; j<PossibleCourseLocations; j++) {
					C[i][j] = Uscanner.nextInt();
					
					} }
			
			


double alpha =0; 
  
                  //***************MODEL*********//
       while(alpha<=1){
        	System.out.println(epsilon);
        	
			try {
				IloCplex cplex = new IloCplex(); 			
				cplex.setParam(IloCplex.DoubleParam.EpGap, 0);
				cplex.setParam(IloCplex.Param.MIP.Tolerances.MIPGap, 0); 		
				numberofModelsSolved++;

			//**********DEFINE DECISION VARIBLES**************/
				

				IloNumVar[][] y = new IloNumVar[TypeOfCourse][PossibleCourseLocations];
				for (int i=0; i<TypeOfCourse;i++){
					for (int j=0;j<PossibleCourseLocations;j++){
						y[i][j]= cplex.boolVar();
						y[i][j].setName("y"+Integer.toString(i)+Integer.toString(j));
					}
				}
				
				//the girl has no name
				IloNumVar[][] f = new IloNumVar[TypeOfCourse][NumberOfDistrictGroup];
				for (int i=0; i<TypeOfCourse;i++){
					for (int b=0;b<NumberOfDistrictGroup;b++) {
						f[i][b]= cplex.numVar(0, 1,IloNumVarType.Float );
						f[i][b].setName("f"+Integer.toString(i)+Integer.toString(b));
					}
				}
				
			
				
				//X_bar is coming 
				IloNumVar[][][]X_bar = new IloNumVar [TypeOfCourse][NumberOfDistrictGroup][NumberOfGap];
				for (int i=0; i<TypeOfCourse;i++){
					for(int b=0;b<NumberOfDistrictGroup;b++) {
						for (int m=0;m<NumberOfGap;m++){					
							X_bar[i][b][m]= cplex.numVar(0, 0.1,IloNumVarType.Float );
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
				
				IloNumVar[][][] t = new IloNumVar [TypeOfCourse][NumberOfDistrictGroup][PossibleCourseLocations];
				for (int i=0; i<TypeOfCourse;i++){
					for(int b=0;b<NumberOfDistrictGroup;b++) {
						for (int j=0;j<PossibleCourseLocations;j++){
							t[i][b][j]= cplex.numVar(0, Double.MAX_VALUE,IloNumVarType.Float );
							t[i][b][j].setName("t"+Integer.toString(i)+Integer.toString(b)+Integer.toString(j));
							}
						}
					} 
				
		

             //***********************OBJECTIVES*********************/
				
			
	
				IloLinearNumExpr obj1 = cplex.linearNumExpr();
				
				double diff[]=new double[TypeOfCourse];
				for(int i=0; i<TypeOfCourse; i++) {
					diff[i] = 1/(EY[i]-ED[i]);
						}
				for(int i=0; i<TypeOfCourse; i++) {
					for(int k=0 ; k<NumberOfDistrictGroup; k++) {
						obj1.addTerm(alpha*diff[i]*10000,z[i][k]);
						obj1.addTerm((1-alpha),u[i][k]);
					}
				}
				
			
					cplex.addMaximize(obj1 );
				
			
					
			//////// REAL CONSTRAINTS///////
			
				//2
				for(int i=0; i<TypeOfCourse; i++) {
					for(int b=0 ; b<NumberOfDistrictGroup; b++) {
						IloLinearNumExpr cons0 = cplex.linearNumExpr();
						cons0.addTerm( 1, f[i][b]);
						for(int j=0;j<NumberOfGap;j++) {			
							cons0.addTerm( -1, X_bar[i][b][j]);
						}
						cplex.addEq(cons0, 0);
						cons0.clear();
					}
				}
				
				//3
				for(int i=0; i<TypeOfCourse; i++) {
					for(int b=0 ; b<NumberOfDistrictGroup; b++) {
						IloLinearNumExpr cons7 = cplex.linearNumExpr();
						cons7.addTerm( 1, u[i][b]);
						for(int j=0;j<NumberOfGap;j++) {			
							cons7.addTerm( -((Utility[j+1]-Utility[j])/0.1), X_bar[i][b][j]);
						}
						cplex.addEq(cons7, 0);
						cons7.clear();
					}
				}
				
				//4 cost constraint 
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
							cons1.addTerm(p[i][j][k], y[i][j]); 
						
						}
						cplex.addEq(cons1, 0);
						cons1.clear();
					}

				}
				
				//2
				for(int i=0; i<TypeOfCourse; i++) {
					for(int b=0 ; b<NumberOfDistrictGroup; b++) {
						IloLinearNumExpr cons2 = cplex.linearNumExpr();
						cons2.addTerm(-1, z[i][b]);
						for(int k=0;k<NumberOfDistrictGroup;k++) {
							for(int j=0;j<PossibleCourseLocations;j++) {
							cons2.addTerm(p[i][j][k], t[i][b][j]);	//cons2.addTerm(g[j][i]*Q[j][k],t[i][b][j][k]);	
							}
						}
						cplex.addEq(cons2 , 0);
						cons2.clear();
						
					}
				} 
									
				//3
				for (int i=0; i<TypeOfCourse;i++){
						for (int b=0;b<NumberOfDistrictGroup;b++){
							for (int j=0;j<PossibleCourseLocations;j++){	
							 IloLinearNumExpr cons3 = cplex.linearNumExpr();
							 cons3.addTerm( 1, t[i][b][j]);
							 cons3.addTerm( -1, y[i][j]);
							cplex.addLe(cons3, 0);
							cons3.clear();
						}

					}
				}
			
				//4
					for (int i=0; i<TypeOfCourse;i++){
							for (int b=0;b<NumberOfDistrictGroup;b++){
								for (int j=0;j<PossibleCourseLocations;j++){	
									
							IloLinearNumExpr cons4 = cplex.linearNumExpr();
							cons4.addTerm( 1, t[i][b][j]);
							 cons4.addTerm( -1, f[i][b]);
							cplex.addLe(cons4, 0);
							cons4.clear();
						}

					}
				} 
					
				//5
						for (int i=0; i<TypeOfCourse;i++){							
								for (int b=0;b<NumberOfDistrictGroup;b++){
									for (int j=0;j<PossibleCourseLocations;j++){		
										IloLinearNumExpr cons5 = cplex.linearNumExpr();
							cons5.addTerm( -1, t[i][b][j]);
							cons5.addTerm( +1, f[i][b]);
							cons5.addTerm( +1, y[i][j]);
							cplex.addLe(cons5, 1);
							cons5.clear();
						}

					}
				}
						//6 her tipten her paire en az bir tane ata 
							
							for (int i=0; i<TypeOfCourse;i++) {
								IloLinearNumExpr cons6 = cplex.linearNumExpr();
								for(int k=0;k<NumberOfDistrictGroup;k++) {					 										
								cons6.addTerm(1,z[i][k]);							
							}								
								cplex.addGe(cons6, 1);
								cons6.clear();				
							}
						
			
				
					////SOLUTIONS AND CONSOLE PRINTS////
						
				cplex.exportModel("yedek_NewData.lp");
				cplex.solve();
				
				 if(cplex.isPrimalFeasible()==false){
	                    feasible=false;
	                    break;
	                }
				 
				 //print the objective value
				System.out.println("objective: " + cplex.getObjValue());
          
                
                double[][] A = new double[NumberOfDistrictGroup][TypeOfCourse];
                double total3=0;
                double total4=0;
                double total5=0;
                //double total9=0;
                //double total10=0;
                
                double total6=0;
                double total7=0;
                double total8=0;
                
                double total19=0;
                double total20=0;
                double total21=0;
               // double total11=0;
                //double total12=0;
                
                //for(int i=0;i<TypeOfCourse;i++){
				for (int j=0;j<PossibleCourseLocations;j++){	
					if(cplex.getValue(y[0][j])>=0.9){
					total3 = total3 + (p[0][j][0]);	
					total4 = total4 + (p[0][j][1]);	
					total5 = total5 + (p[0][j][2]);	
					//total9 = total9 + (p[0][j][3]);	
					//total10 = total10 + (p[0][j][4]);	
										
					A[0][0]=total3;	
					A[1][0]=total4;
					A[2][0]=total5;
					//A[3][0]=total9;	
					//A[4][0]=total10;																				
						}
					}
				
				for (int j=0;j<PossibleCourseLocations;j++){	
					if(cplex.getValue(y[1][j])>=0.9){
						total6 = total6 + (p[1][j][0]);	
						total7 = total7 + (p[1][j][1]);	
						total8 = total8 + (p[1][j][2]);	
						//total11 = total11 + (p[1][j][3]);	
						//total12 = total12 + (p[1][j][4]);	
						
						A[0][1]=total6;
						A[1][1]=total7;
						A[2][1]=total8;
						//A[3][1]=total11;
						//A[4][1]=total12;
						}
					}
				
				for (int j=0;j<PossibleCourseLocations;j++){	
					if(cplex.getValue(y[2][j])>=0.9){
						total19 = total19 + (p[2][j][0]);	
						total20 = total20 + (p[2][j][1]);	
						total21 = total21 + (p[2][j][2]);	
						//total11 = total11 + (p[1][j][3]);	
						//total12 = total12 + (p[1][j][4]);	
						
						A[0][2]=total19;
						A[1][2]=total20;
						A[2][2]=total21;
						//A[3][1]=total11;
						//A[4][1]=total12;
						}
					}
					
						System.out.println(total3);
						System.out.println(total4);
						System.out.println(total5);
						//System.out.println(total9);
						//System.out.println(total10);
						
						System.out.println(total6);
						System.out.println(total7);
						System.out.println(total8);
						//System.out.println(total11);
						//System.out.println(total12);
						System.out.println(total19);
						System.out.println(total20);
						System.out.println(total21);
				
              
                
                
	                    
				        	FileWriter fileWriter;
							try {
								fileWriter = new FileWriter("C:\\Users\\dakol\\Desktop\\Yedek_weightedz_values.txt", true);
						
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
							fileWriter.write(String.valueOf(total19));
							fileWriter.write("\n");
							fileWriter.write(String.valueOf(total20));
							fileWriter.write("\n");
							fileWriter.write(String.valueOf(total21));
							fileWriter.write("\n");
							fileWriter.write("\n");
							fileWriter.write("\n");
			                fileWriter.close();	} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                

                alpha =alpha +0.1;               
			   } catch (IloException e) {
	                e.printStackTrace();
	            } 
			}
        
        long stopLP = System.nanoTime();
        System.err.println("Solution time       = " + String.format("%.2f",(stopLP - startLP) / 1000000000.0));
        System.err.println("# of models solved  = " + numberofModelsSolved);
        
	        }

}
