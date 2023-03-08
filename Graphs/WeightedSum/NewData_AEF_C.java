package max_min;
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

public class NewData_AEF_C {
	
	static boolean feasible;
    static double epsilon;
    static double stepsize;
    static int numberOfIterations=0;
    static int numberofModelsSolved=0;
    
    static double[]ED = {0, 0}; //minimum total value in that benefit type (evaluated by) 
    static double[] EY = {18345, 12234}; //maximum total value in that benefit type (evaluated by) 
    static double[] Utility = {0,8,15,21,26,30,33,35,36,36.5,36.75};
    static double[] Threshold = {0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1};
    static int NumberOfGap= 10; 
    
    static int NumberOfDistrictGroup = 0 ; 
    
    
    static int PossibleCourseLocations = 0;
    static int TypeOfCourse = 0;
    static int [][] C; 
   
    
    public static BufferedReader br;

	public static void main(String[] args) {
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
						pscanner = new Scanner(new File("C:\\Users\\dakol\\Desktop\\NewData\\p_0jk.txt"));
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
					p2scanner = new Scanner(new File("C:\\Users\\dakol\\Desktop\\NewData\\p_1jk.txt"));
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
									
									

					int[][][] p = new int[TypeOfCourse][PossibleCourseLocations][NumberOfDistrictGroup];
								    
								        for(int j = 0; j<PossibleCourseLocations; j++) {
											for(int k =0; k<NumberOfDistrictGroup; k++) {
								            p[0][j][k]=p1[j][k];
								        } }
								    
								        for(int j = 0; j<PossibleCourseLocations; j++) {
											for(int k =0; k<NumberOfDistrictGroup; k++) {
								            p[1][j][k]=p2[j][k];
								        } }
								        
					
														
							
		
			Scanner Uscanner = null;
			try {
				Uscanner = new Scanner(new File("C:\\Users\\dakol\\Desktop\\NewData\\CostDistrict.txt"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        C=new int [TypeOfCourse][PossibleCourseLocations];
	        for(int i =0; i<TypeOfCourse; i++) {
	        	for(int j = 0; j<PossibleCourseLocations; j++) {
					C[i][j] = Uscanner.nextInt();
					} }
			
			
      stepsize=0.05;
      solve();


       double total2 = 1;
  
 
                 //***************MODEL*********//
       while(feasible) {	
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
				
				//A_bar is coming for calculation value at the other model
				IloNumVar[][][]A_bar = new IloNumVar [TypeOfCourse][NumberOfDistrictGroup][NumberOfGap];
				for (int i=0; i<TypeOfCourse;i++){
					for(int b=0;b<NumberOfDistrictGroup;b++) {
						for (int m=0;m<NumberOfGap;m++){					
							A_bar[i][b][m]= cplex.numVar(0, 250,IloNumVarType.Float );
							A_bar[i][b][m].setName("A_bar"+Integer.toString(i)+Integer.toString(b)+Integer.toString(m));
							}
						}
					} 
				

				//utility values for other model
				IloNumVar[][] x = new IloNumVar[TypeOfCourse][NumberOfDistrictGroup];
				for (int i=0; i<TypeOfCourse;i++){
					for (int b=0;b<NumberOfDistrictGroup;b++) {
						x[i][b]= cplex.numVar(0, Double.MAX_VALUE,IloNumVarType.Float );
						x[i][b].setName("x"+Integer.toString(i)+Integer.toString(b));
					}

				}
				
				//A_i
				IloNumVar[] a = new IloNumVar[TypeOfCourse];
				for (int i=0; i<TypeOfCourse;i++){
						a[i]= cplex.numVar(0, 1,IloNumVarType.Float );
						a[i].setName("a"+Integer.toString(i));
					}
				
				
				//efficiency_bar is coming 
				IloNumVar[][]efficiency_bar = new IloNumVar [TypeOfCourse][NumberOfGap];
				for (int i=0; i<TypeOfCourse;i++){
						for (int m=0;m<NumberOfGap;m++){					
							efficiency_bar[i][m]= cplex.numVar(0, 0.1,IloNumVarType.Float );
							efficiency_bar[i][m].setName("efficiency_bar"+Integer.toString(i)+Integer.toString(m));
							}
						}
					 
				
				//c_i
				IloNumVar[]c = new IloNumVar[TypeOfCourse];
				for (int i=0; i<TypeOfCourse;i++){
						c[i]= cplex.numVar(0, Double.MAX_VALUE,IloNumVarType.Float ); 
						c[i].setName("c"+Integer.toString(i));
					}
				

            //***********************OBJECTIVES*********************/
				
			
				//IloLinearNumExpr obj2 = cplex.linearNumExpr();
				IloLinearNumExpr obj1 = cplex.linearNumExpr();
				
				for(int i=0; i<TypeOfCourse; i++) {
						obj1.addTerm(1,c[i]);  
					}								
						
			
						
							for(int i=0; i<TypeOfCourse; i++) {
								for(int b=0 ; b<NumberOfDistrictGroup; b++) {
									obj1.addTerm(0.00005,u[i][b]);
								}
							}
						
		
					cplex.addMaximize(obj1 );
				
				//************constraints***************/
					//1
					
					IloLinearNumExpr objective_cons = cplex.linearNumExpr();
					for(int i=0; i<TypeOfCourse; i++) {
						for(int b=0 ; b<NumberOfDistrictGroup; b++) {
							objective_cons.addTerm(1,u[i][b]);
						}
					}
					cplex.addGe(objective_cons, epsilon);
					
							///constraints for the CW model (owa)////
					//1.1
					for(int i=0; i<TypeOfCourse; i++) {
						for(int b=0 ; b<NumberOfDistrictGroup; b++) {
							IloLinearNumExpr cons0 = cplex.linearNumExpr();
							cons0.addTerm( 1, z[i][b]);
							for(int j=0;j<NumberOfGap;j++) {			
								cons0.addTerm( -1, A_bar[i][b][j]);
							}
							cplex.addEq(cons0, 0);
							cons0.clear();
						}
					}
					
					//1.2
					for(int i=0; i<TypeOfCourse; i++) {
						for(int b=0 ; b<NumberOfDistrictGroup; b++) {
							IloLinearNumExpr cons7 = cplex.linearNumExpr();
							cons7.addTerm( 1, x[i][b]);
							for(int j=0;j<NumberOfGap;j++) {			
								cons7.addTerm( -((Utility[j+1]-Utility[j])/250), A_bar[i][b][j]);
							}
							cplex.addEq(cons7, 0);
							cons7.clear();
						}
					}
					
					//////// REAL CONSTRAINTS///////
					
					  //2
					for(int i=0; i<TypeOfCourse; i++) {
							IloLinearNumExpr cons_trial1= cplex.linearNumExpr();
							cons_trial1.addTerm( 1, a[i]);
							for(int j=0;j<NumberOfGap;j++) {			
								cons_trial1.addTerm( -1,efficiency_bar[i][j]);
							}
							cplex.addEq(cons_trial1, 0);
							cons_trial1.clear();
						}
					
					//3
					for(int i=0; i<TypeOfCourse; i++) {
							IloLinearNumExpr cons_trial2 = cplex.linearNumExpr();
							cons_trial2.addTerm( 1, c[i]);
							for(int j=0;j<NumberOfGap;j++) {			
								cons_trial2.addTerm( -((Utility[j+1]-Utility[j])/0.1), efficiency_bar[i][j]);
							}
							cplex.addEq(cons_trial2, 0);
							cons_trial2.clear();
						}
					
					//4
					
					double diff[]=new double[TypeOfCourse];
					for(int i=0; i<TypeOfCourse; i++) {
						diff[i] = (1/(EY[i]-ED[i]));
							}
					for(int i=0; i<TypeOfCourse; i++) {
						IloLinearNumExpr cons_trial = cplex.linearNumExpr();
						cons_trial.addTerm(1,a[i]);
						for(int k=0 ; k<NumberOfDistrictGroup; k++) {
							cons_trial.addTerm(-(diff[i]),z[i][k]);
						}					
					cplex.addEq(cons_trial, 0);
					cons_trial.clear();
					}
					
						//5
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
						
						//6
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
						
						//7 cost constraint 
						IloLinearNumExpr cons8 = cplex.linearNumExpr();
						for(int i=0 ; i<TypeOfCourse; i++) {
							for(int j=0;j<PossibleCourseLocations;j++) {	
								cons8.addTerm( C[i][j] , y[i][j]); //cons8.addTerm( 1 , (cplex.sum(x[k][j],-U[j])));			
							} 
			               //if i put here that means in each hospital type totals should be separately smaller than the number
						}
						cplex.addLe(cons8,6954289);
						cons8.clear();
						
						
						
						
										
						//8
						for(int i=0; i<TypeOfCourse; i++) {
							for(int k=0 ; k<NumberOfDistrictGroup; k++) {
								IloLinearNumExpr cons1 = cplex.linearNumExpr();
								cons1.addTerm(-1, z[i][k]);
								for(int j=0;j<PossibleCourseLocations;j++) {
									cons1.addTerm(p[i][j][k], y[i][j]); //cons1.addTerm(g[j][i]*Q[j][k], x[k][j]);
								
								}
								cplex.addEq(cons1, 0);
								cons1.clear();
							}

						}
						
						//9
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
											
						//10
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
					
						//11
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
							
						//12
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
					
								
						 /*
								
								for (int i=0; i<TypeOfCourse;i++) {
									IloLinearNumExpr cons6 = cplex.linearNumExpr();
									for(int k=0;k<NumberOfDistrictGroup;k++) {					 										
									cons6.addTerm(1,z[i][k]);							
								}								
									cplex.addGe(cons6, 1);
									cons6.clear();				
								}
								*/
						
							////SOLUTIONS AND CONSOLE PRINTS////
								
						cplex.exportModel("NewData_AEF_C.lp");
						cplex.solve();
						
						 if(cplex.isPrimalFeasible()==false){
			                    feasible=false;
			                    break;
			                }
						 
						 //print the objective value
						System.out.println("objective: " + cplex.getObjValue());

		
						
						double othervalue1=0;
						for(int i=0; i<1; i++) {
							for(int b=0 ; b<NumberOfDistrictGroup; b++) {
								othervalue1 = othervalue1 + (cplex.getValue(x[i][b]));
		                    }
		                }

		                
		                System.out.println(othervalue1);
		                System.out.println("CW1");	
						
						double othervalue2=0;
						for(int i=1; i<2; i++) {
							for(int b=0 ; b<NumberOfDistrictGroup; b++) {
								othervalue2 = othervalue2 + (cplex.getValue(x[i][b]));
		                    }
		                }
						System.out.println(othervalue2);
						 System.out.println("CW2");	
						
						
						double total1=0;
						for(int i=0; i<TypeOfCourse; i++) {
							diff[i] = 1/(EY[i]-ED[i]);
								}
						for(int i=0; i<TypeOfCourse; i++) {
							for(int k=0 ; k<NumberOfDistrictGroup; k++) {
								total1=total1+(diff[i]*100*(cplex.getValue(z[i][k])));
							}
						}

		                System.out.println();
		                System.out.println(total1);
		                System.out.println("AE-L"); 
		                System.out.println();
		                System.out.println();
		                
		               
		                
						total2=0;
						for(int i=0; i<TypeOfCourse; i++) {
							for(int b=0 ; b<NumberOfDistrictGroup; b++) {
		                            total2 = total2 + (cplex.getValue(u[i][b]));

		                    }
		                }    
						System.out.println(total2);
		                System.out.println("AF-C"); 
		                System.out.println();
						
						
						double othervalue3=0;
						for(int i=0; i<TypeOfCourse; i++) {						
								othervalue3 = othervalue3 + (cplex.getValue(c[i]));		                    
		                }

		                System.out.println(othervalue3);
		                System.out.println("AE-C");	
		                
		             
		               
		                double[][] A = new double[NumberOfDistrictGroup][TypeOfCourse];
		                double total3=0;
		                double total4=0;
		                double total5=0;
		                double total9=0;
		                double total10=0;
		                
		                double total6=0;
		                double total7=0;
		                double total8=0;
		                double total11=0;
		                double total12=0;
		                
		                //for(int i=0;i<TypeOfCourse;i++){
						for (int j=0;j<PossibleCourseLocations;j++){	
							if(cplex.getValue(y[0][j])>=0.9){
							total3 = total3 + (p[0][j][0]);	
							total4 = total4 + (p[0][j][1]);	
							total5 = total5 + (p[0][j][2]);	
							total9 = total9 + (p[0][j][3]);	
							total10 = total10 + (p[0][j][4]);	
												
							A[0][0]=total3;	
							A[1][0]=total4;
							A[2][0]=total5;
							A[3][0]=total9;	
							A[4][0]=total10;																				
								}
							}
						
						for (int j=0;j<PossibleCourseLocations;j++){	
							if(cplex.getValue(y[1][j])>=0.9){
								total6 = total6 + (p[1][j][0]);	
								total7 = total7 + (p[1][j][1]);	
								total8 = total8 + (p[1][j][2]);	
								total11 = total11 + (p[1][j][3]);	
								total12 = total12 + (p[1][j][4]);	
								
								A[0][1]=total6;
								A[1][1]=total7;
								A[2][1]=total8;
								A[3][1]=total11;
								A[4][1]=total12;
								}
							}
							
								System.out.println(total3);
								System.out.println(total4);
								System.out.println(total5);
								System.out.println(total9);
								System.out.println(total10);
								
								System.out.println(total6);
								System.out.println(total7);
								System.out.println(total8);
								System.out.println(total11);
								System.out.println(total12);
								
				//denemeeeee!!!!!!!!!
		                
								FileWriter fileWriterr;
								try {
									fileWriterr = new FileWriter("C:\\Users\\dakol\\Desktop\\NewData_AEF_C_objectives.txt", true);					
								fileWriterr.write(String.valueOf(total1));
								fileWriterr.write("\n");
								fileWriterr.write("AE-L");
								fileWriterr.write("\n");
								fileWriterr.write(String.valueOf(total2));
								fileWriterr.write("\n");
								fileWriterr.write("AF-C");
								fileWriterr.write("\n");
								fileWriterr.write(String.valueOf(othervalue3));
								fileWriterr.write("\n");
								fileWriterr.write("AE-C");
								fileWriterr.write("\n");
								fileWriterr.write(String.valueOf(othervalue1));
								fileWriterr.write("\n");
								fileWriterr.write("CW1");
								fileWriterr.write("\n");
								fileWriterr.write(String.valueOf(othervalue2));
								fileWriterr.write("\n");
								fileWriterr.write("CW2");
								fileWriterr.write("\n");
								fileWriterr.write("\n");
								fileWriterr.write("\n");
			                    fileWriterr.close();		}
			                    catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
					
			                    
			                    FileWriter owa;
								try {
									owa = new FileWriter("C:\\Users\\dakol\\Desktop\\owa_NewData_CW1.txt", true);
									owa.write(String.valueOf(othervalue1));
				                    owa.write("\n");
				                    owa.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			                    
						  
			                    FileWriter eff;
								try {
									eff = new FileWriter("C:\\Users\\dakol\\Desktop\\Owa_NewData_CW2.txt", true);
									eff.write(String.valueOf(othervalue2));
									eff.write("\n");
				                    eff.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
			                    
			                    FileWriter fair;
								try {
									fair = new FileWriter("C:\\Users\\dakol\\Desktop\\aggr_eff_NewData_AE_L.txt", true);
									   fair.write(String.valueOf(total1));
					                    fair.write("\n");
					                    fair.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			                 
			                    
			                    FileWriter u0;
								try {
									u0 = new FileWriter("C:\\Users\\dakol\\Desktop\\aggr_fair_NewData_AF_C.txt", true);
									u0.write(String.valueOf(total2));
									u0.write("\n");
				                    u0.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						
			                    
			                	 FileWriter deneme;
								try {
									deneme = new FileWriter("C:\\Users\\dakol\\Desktop\\NewData_AEF_C_deneme.txt", true);
								
							       
					            	if(cplex.getValue(y[0][0])>0.9) {
		    							deneme.write("Etimesgut_H");
		    				    		deneme.write("\n");
		    				    }
		    						if(cplex.getValue(y[0][1])>0.9) {
		    							deneme.write("Cankaya_H");
		    				    		deneme.write("\n");
		    				    }
		    						if(cplex.getValue(y[0][2])>0.9) {
		    							deneme.write("Yenimahalle_H ");
		    				    		deneme.write("\n");
		    				    }
		    						if(cplex.getValue(y[0][3])>0.9) {
		    							deneme.write("Sincan_H ");
		    				    		deneme.write("\n");
		    				    }if(cplex.getValue(y[0][4])>0.9) {
	 							deneme.write("Cubuk_H");
	 				    		deneme.write("\n");
		    				    }
	 						if(cplex.getValue(y[0][5])>0.9) {
	 							deneme.write("Kecioren_H");
	 				    		deneme.write("\n");
	 						}
	 						if(cplex.getValue(y[0][6])>0.9) {
	 							deneme.write("Polatli_H");
	 				    		deneme.write("\n");
	 				    }
	 						if(cplex.getValue(y[0][7])>0.9) {
	 							deneme.write("Pursaklar_H");
	 				    		deneme.write("\n");
	 				    }
	 						if(cplex.getValue(y[0][8])>0.9) {
	 							deneme.write("Mamak_H ");
	 				    		deneme.write("\n");
	 				    }
	 					
	 				    if(cplex.getValue(y[0][9])>0.9) {
								deneme.write("Beypazari_H");
					    		deneme.write("\n");
	 				    }
							if(cplex.getValue(y[0][10])>0.9) {
								deneme.write("Golbasi_H");
					    		deneme.write("\n");
							}
							if(cplex.getValue(y[0][11])>0.9) {
								deneme.write("Altindag_H ");
					    		deneme.write("\n");
					    }
							if(cplex.getValue(y[0][12])>0.9) {
								deneme.write("sereflikochisar_H");
					    		deneme.write("\n");
					    }
							if(cplex.getValue(y[0][13])>0.9) {
								deneme.write("Kahramankazan_H ");
					    		deneme.write("\n");
					    }
							if(cplex.getValue(y[0][14])>0.9) {
								deneme.write("Elmadag_H ");
					    		deneme.write("\n");
					    }if(cplex.getValue(y[0][15])>0.9) {
							deneme.write("Akyurt_H ");
				    		deneme.write("\n");
					    }  		
					    
						if(cplex.getValue(y[1][0])>0.9) {
							deneme.write("Etimesgut_V");
				    		deneme.write("\n");
				    }
						if(cplex.getValue(y[1][1])>0.9) {
							deneme.write("Cankaya_V");
				    		deneme.write("\n");
				    }
						if(cplex.getValue(y[1][2])>0.9) {
							deneme.write("Yenimahalle_V ");
				    		deneme.write("\n");
				    }
						if(cplex.getValue(y[1][3])>0.9) {
							deneme.write("Sincan_V ");
				    		deneme.write("\n");
				    }if(cplex.getValue(y[1][4])>0.9) {
						deneme.write("Cubuk_V");
			    		deneme.write("\n");
				    }
					if(cplex.getValue(y[1][5])>0.9) {
						deneme.write("Kecioren_V");
			    		deneme.write("\n");
					}
					if(cplex.getValue(y[1][6])>0.9) {
						deneme.write("Polatli_V");
			    		deneme.write("\n");
			    }
					if(cplex.getValue(y[1][7])>0.9) {
						deneme.write("Pursaklar_V");
			    		deneme.write("\n");
			    }
					if(cplex.getValue(y[1][8])>0.9) {
						deneme.write("Mamak_V ");
			    		deneme.write("\n");
			    }				
			    if(cplex.getValue(y[1][9])>0.9) {
					deneme.write("Beypazari_V");
		    		deneme.write("\n");
			    }
				if(cplex.getValue(y[1][10])>0.9) {
					deneme.write("Golbasi_V");
		    		deneme.write("\n");
				}
				if(cplex.getValue(y[1][11])>0.9) {
					deneme.write("Altindag_V ");
		    		deneme.write("\n");
		    }
				if(cplex.getValue(y[1][12])>0.9) {
					deneme.write("Sereflikochisar_V");
		    		deneme.write("\n");
		    }
				if(cplex.getValue(y[1][13])>0.9) {
					deneme.write("Kahramankazan_V ");
		    		deneme.write("\n");
		    }
				if(cplex.getValue(y[1][14])>0.9) {
					deneme.write("Elmadag_V ");
		    		deneme.write("\n");
		    }if(cplex.getValue(y[1][15])>0.9) {
				deneme.write("Akyurt_V ");
	 		deneme.write("\n");
		    }  			 
			    						
					            deneme.write("\n");
					            deneme.write("\n");
					            deneme.write("\n");
					            deneme.close();	 
					            } catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	  
			                  
			                    
			                    FileWriter fw;
								try {
									fw = new FileWriter("C:\\Users\\dakol\\Desktop\\NewData_AEF_C_y_values.txt", true);									
					            for(int i=0;i<TypeOfCourse;i++){
			    					for (int j=0;j<PossibleCourseLocations;j++){	
			    						double yy = cplex.getValue(y[i][j]);
			    						fw.write("y[" + (i) + "][" + (j) + "]="+String.valueOf(yy));
							            	fw.write("\n");							            	
										}
									}
					            	fw.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					    

					            	  FileWriter eff_graph;
									try {
										eff_graph = new FileWriter("C:\\Users\\dakol\\Desktop\\NewData_AE_C", true);
							             eff_graph.write(String.valueOf(othervalue3));
						                    eff_graph.write("\n");
						                    eff_graph.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
					       
									FileWriter fileWriter;
									try {
										fileWriter = new FileWriter("C:\\Users\\dakol\\Desktop\\NewData_AEF_C_z_values.txt", true);
								
									fileWriter.write(String.valueOf(total3));
									fileWriter.write("\n");
									fileWriter.write(String.valueOf(total4));
									fileWriter.write("\n");
									fileWriter.write(String.valueOf(total5));
									fileWriter.write("\n");
									fileWriter.write(String.valueOf(total9));			
									fileWriter.write("\n");
									fileWriter.write(String.valueOf(total10));
									fileWriter.write("\n");
									fileWriter.write(String.valueOf(total6));
									fileWriter.write("\n");
									fileWriter.write(String.valueOf(total7));
									fileWriter.write("\n");
									fileWriter.write(String.valueOf(total8));
									fileWriter.write("\n");
									fileWriter.write(String.valueOf(total11));
									fileWriter.write("\n");
									fileWriter.write(String.valueOf(total12));
									fileWriter.write("\n");
									fileWriter.write("\n");
									fileWriter.write("\n");
					                fileWriter.close();	} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
			                    		           
			                   

		                if(cplex.isPrimalFeasible()==true) {
		                    feasible=true;
		                    epsilon=total2+stepsize;
		                }
		                else {
		                    feasible=false;
		                }
			
		                
		                
					   } catch (IloException e) {
			                e.printStackTrace();
			            } 
					}
		        
		        long stopLP = System.nanoTime();
		        System.err.println("Solution time       = " + String.format("%.2f",(stopLP - startLP) / 1000000000.0));
		        System.err.println("# of models solved  = " + numberofModelsSolved);
		        
			        }
			
			
			
		     public static void solve(){
		    	 
	

				Scanner pscanner =null;
				try {
					pscanner  = new Scanner(new File("C:\\Users\\dakol\\Desktop\\NewData\\p_0jk.txt"));
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				}
		        int [][] p1=new int [PossibleCourseLocations][NumberOfDistrictGroup];
				for(int j = 0; j<PossibleCourseLocations; j++) {
					for(int k =0; k<NumberOfDistrictGroup; k++) {
						p1[j][k] = pscanner.nextInt();
						}
					}
						Scanner p2scanner=null;;
	
						
			try {
				p2scanner = new Scanner(new File("C:\\Users\\dakol\\Desktop\\NewData\\p_1jk.txt"));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			int [][] p2=new int [PossibleCourseLocations][NumberOfDistrictGroup];
			for(int j = 0; j<PossibleCourseLocations; j++) {
				for(int k =0; k<NumberOfDistrictGroup; k++) {
					p2[j][k] = p2scanner.nextInt();
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
				
		        try {

		            IloCplex cplex = new IloCplex();
		            numberofModelsSolved++;

		            ////DEFINE DECISION VARIBLES////
		            cplex.setParam(IloCplex.DoubleParam.EpGap, 0);
					cplex.setParam(IloCplex.Param.MIP.Tolerances.MIPGap,0 );  //1e-10

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
					
					//A_bar is coming for calculation value at the other model
					IloNumVar[][][]A_bar = new IloNumVar [TypeOfCourse][NumberOfDistrictGroup][NumberOfGap];
					for (int i=0; i<TypeOfCourse;i++){
						for(int b=0;b<NumberOfDistrictGroup;b++) {
							for (int m=0;m<NumberOfGap;m++){					
								A_bar[i][b][m]= cplex.numVar(0, 250,IloNumVarType.Float );
								A_bar[i][b][m].setName("A_bar"+Integer.toString(i)+Integer.toString(b)+Integer.toString(m));
								}
							}
						} 
					

					//utility values for other model
					IloNumVar[][] x = new IloNumVar[TypeOfCourse][NumberOfDistrictGroup];
					for (int i=0; i<TypeOfCourse;i++){
						for (int b=0;b<NumberOfDistrictGroup;b++) {
							x[i][b]= cplex.numVar(0, Double.MAX_VALUE,IloNumVarType.Float );
							x[i][b].setName("x"+Integer.toString(i)+Integer.toString(b));
						}

					}
					
					//A_i
					IloNumVar[] a = new IloNumVar[TypeOfCourse];
					for (int i=0; i<TypeOfCourse;i++){
							a[i]= cplex.numVar(0, 1,IloNumVarType.Float );
							a[i].setName("a"+Integer.toString(i));
						}
					
					
					//efficiency_bar is coming 
					IloNumVar[][]efficiency_bar = new IloNumVar [TypeOfCourse][NumberOfGap];
					for (int i=0; i<TypeOfCourse;i++){
							for (int m=0;m<NumberOfGap;m++){					
								efficiency_bar[i][m]= cplex.numVar(0, 0.1,IloNumVarType.Float );
								efficiency_bar[i][m].setName("efficiency_bar"+Integer.toString(i)+Integer.toString(m));
								}
							}
						 
					
					//c_i
					IloNumVar[]c = new IloNumVar[TypeOfCourse];
					for (int i=0; i<TypeOfCourse;i++){
							c[i]= cplex.numVar(0, Double.MAX_VALUE,IloNumVarType.Float ); //uib
							c[i].setName("c"+Integer.toString(i));
						}
					

		            ////END OF DECISION VARIABLES////


					//IloLinearNumExpr obj2 = cplex.linearNumExpr();
					IloLinearNumExpr obj1 = cplex.linearNumExpr();
					
					//1
					for(int i=0; i<TypeOfCourse; i++) {
							obj1.addTerm(1,c[i]);  ///constent needed
						}								
															
								for(int i=0; i<TypeOfCourse; i++) {
									for(int b=0 ; b<NumberOfDistrictGroup; b++) {
										obj1.addTerm(0.00005,u[i][b]);
									}
								}
							
			
						cplex.addMaximize(obj1 );
						       

						///constraints for the other model values////
						//1.1
						for(int i=0; i<TypeOfCourse; i++) {
							for(int b=0 ; b<NumberOfDistrictGroup; b++) {
								IloLinearNumExpr cons0 = cplex.linearNumExpr();
								cons0.addTerm( 1, z[i][b]);
								for(int j=0;j<NumberOfGap;j++) {			
									cons0.addTerm( -1, A_bar[i][b][j]);
								}
								cplex.addEq(cons0, 0);
								cons0.clear();
							}
						}
						
						//1.2
						for(int i=0; i<TypeOfCourse; i++) {
							for(int b=0 ; b<NumberOfDistrictGroup; b++) {
								IloLinearNumExpr cons7 = cplex.linearNumExpr();
								cons7.addTerm( 1, x[i][b]);
								for(int j=0;j<NumberOfGap;j++) {			
									cons7.addTerm( -((Utility[j+1]-Utility[j])/250), A_bar[i][b][j]);
								}
								cplex.addEq(cons7, 0);
								cons7.clear();
							}
						}
						/////real constraints//////
						
						  //3
						for(int i=0; i<TypeOfCourse; i++) {
								IloLinearNumExpr cons_trial1 = cplex.linearNumExpr();
								cons_trial1.addTerm( 1, a[i]);
								for(int j=0;j<NumberOfGap;j++) {			
									cons_trial1.addTerm( -1,efficiency_bar[i][j]);
								}
								cplex.addEq(cons_trial1, 0);
								cons_trial1.clear();
							}
						
						//4
						for(int i=0; i<TypeOfCourse; i++) {
								IloLinearNumExpr cons_trial2 = cplex.linearNumExpr();
								cons_trial2 .addTerm( 1, c[i]);
								for(int j=0;j<NumberOfGap;j++) {			
									cons_trial2 .addTerm( -((Utility[j+1]-Utility[j])/0.1), efficiency_bar[i][j]);
								}
								cplex.addEq(cons_trial2 , 0);
								cons_trial2 .clear();
							}
						
						//5
						
						double diff[]=new double[TypeOfCourse];
						for(int i=0; i<TypeOfCourse; i++) {
							diff[i] = (1/(EY[i]-ED[i]));
								}
						for(int i=0; i<TypeOfCourse; i++) {
							IloLinearNumExpr cons_trial = cplex.linearNumExpr();
							cons_trial.addTerm(1,a[i]);
							for(int k=0 ; k<NumberOfDistrictGroup; k++) {
								cons_trial.addTerm(-(diff[i]),z[i][k]); //*0.01
							}					
						cplex.addEq(cons_trial, 0);
						cons_trial.clear();
						}
						
		          
		          //6
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
					
					//7
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
					
					//8 cost constraint 
					IloLinearNumExpr cons8 = cplex.linearNumExpr();
					for(int i=0 ; i<TypeOfCourse; i++) {
						for(int j=0;j<PossibleCourseLocations;j++) {	
							cons8.addTerm( C[i][j] , y[i][j]); //cons8.addTerm( 1 , (cplex.sum(x[k][j],-U[j])));			
						} 
		               //if i put here that means in each hospital type totals should be separately smaller than the number
					}
					cplex.addLe(cons8,6954289);
					cons8.clear();
					
								
				
					//9
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
					
					//10
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
										
					//11
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
				
					//12
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
						
					//13
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
				
							
					 /*
							
							for (int i=0; i<TypeOfCourse;i++) {
								IloLinearNumExpr cons6 = cplex.linearNumExpr();
								for(int k=0;k<NumberOfDistrictGroup;k++) {					 										
								cons6.addTerm(1,z[i][k]);							
							}								
								cplex.addGe(cons6, 1);
								cons6.clear();				
							}
					  	*/

		            ////SOLUTIONS AND CONSOLE PRINTS////

		            cplex.solve();
		            cplex.exportModel("NewData_AEF_C_2.lp");
		       
		            
		            System.out.println("obj = "+cplex.getObjValue());

		        
			

					
					for(int i=0;i<TypeOfCourse;i++){
						for (int j=0;j<PossibleCourseLocations;j++){
							if(cplex.getValue(y[i][j])>=0){
								System.out.println("y[" + (i) + "][" + (j) + "]="+cplex.getValue(y[i][j]));
							}
						}
					}
					
			/*		for(int i=0;i<TypeOfCourse;i++){
						for (int b=0;b<NumberOfDistrictGroup;b++){
							if(cplex.getValue(u[i][b])>=0){
								System.out.println("f[" + (i) + "][" + (b) + "]="+cplex.getValue(u[i][b]));
							}
						}
					} 
					
					for(int i=0;i<TypeOfCourse;i++){
						for (int b=0;b<NumberOfDistrictGroup;b++){
							if(cplex.getValue(f[i][b])>=0){
								System.out.println("z_ik^N[" + (i) + "][" + (b) + "]="+cplex.getValue(f[i][b]));
							}
						}
					} 
					
					for(int i=0;i<TypeOfCourse;i++){
							for (int m=0;m<NumberOfGap;m++){
							if(cplex.getValue(efficiency_bar[i][m])>=0){
								System.out.println("efficiency_bar[" + (i) + "][" + (m) + "]="+cplex.getValue(efficiency_bar[i][m]));
							}
						}
					}
					
					
					for(int i=0;i<TypeOfCourse;i++){
							if(cplex.getValue(a[i])>=0){
								System.out.println("a[" + (i) + "]="+cplex.getValue(a[i]));
							}
						}
					
					for(int i=0;i<TypeOfCourse;i++){
						if(cplex.getValue(c[i])>=0){
							System.out.println("c[" + (i) + "]="+cplex.getValue(c[i]));
						}
					}
					
					
					for(int i=0;i<TypeOfCourse;i++){
						for(int b=0 ; b<NumberOfDistrictGroup; b++) {
							for (int m=0;m<NumberOfGap;m++){
							if(cplex.getValue(X_bar[i][b][m])>=0){
								System.out.println("X_bar[" + (i) + "][" + (b) + "][" + (m) + "]="+cplex.getValue(X_bar[i][b][m]));
							}
						}
					}
					}
					*/
					double othervalue3=0;
					for(int i=0; i<TypeOfCourse; i++) {
					
							othervalue3 = othervalue3 + (cplex.getValue(c[i]));
	                           // System.out.println(cplex.getValue(x[i][b]));
	                    
	                }

	                
	                System.out.println(othervalue3);
	                System.out.println("AE-C");	
					
	
					double othervalue1=0;
					for(int i=0; i<1; i++) {
						for(int b=0 ; b<NumberOfDistrictGroup; b++) {
							othervalue1 = othervalue1 + (cplex.getValue(x[i][b]));
		                       // System.out.println(cplex.getValue(x[i][b]));
		                }
		            }

		            
		            System.out.println(othervalue1);
		            System.out.println("CW1");	
					
					double othervalue2=0;
					for(int i=1; i<2; i++) {
						for(int b=0 ; b<NumberOfDistrictGroup; b++) {
							othervalue2 = othervalue2 + (cplex.getValue(x[i][b]));
		                        //System.out.println(cplex.getValue(x[i][b]));
		                }
		            }
					System.out.println(othervalue2);
					 System.out.println("CW2");	
					
					double total1=0;
					for(int i=0; i<TypeOfCourse; i++) {
						diff[i] = 1/(EY[i]-ED[i]);
							}
					for(int i=0; i<TypeOfCourse; i++) {
						for(int k=0 ; k<NumberOfDistrictGroup; k++) {
							total1=total1+(diff[i]*100*(cplex.getValue(z[i][k]))); //*1000
						}
					}

		            System.out.println();
		            System.out.println(total1);
		            System.out.println("AE-L"); 
					
		  
		            
					double[] aa_i =new double [TypeOfCourse] ;			
					double [] summation= new double [TypeOfCourse]; 
					
						
						for (int k=0;k<NumberOfDistrictGroup;k++){
							summation[0] = summation[0]+ (cplex.getValue(f[0][k]));
					}
						
						for (int k=0;k<NumberOfDistrictGroup;k++){
							summation[1] = summation[1]+ (cplex.getValue(f[1][k]));		
						}
					
						aa_i[0]= (summation[0])/EY[0];
						aa_i[1]=(summation[1])/EY[1];
					
						
					System.out.println(aa_i[0]);
					System.out.println("aai0above");
					System.out.println();
		            
		            double[] bb_i =new double [TypeOfCourse];	
					for(int i=0; i<TypeOfCourse; i++) {
					    	if(aa_i[i] == 0) {
					    		bb_i [i]=0;
					    }
					    	if(aa_i[i]  == 0.1) {
					    		bb_i [i]=8;
					    }
					    	if(aa_i[i]  == 0.2) {
					    		bb_i [i]=15;
					    }
					    	if(aa_i[i]  == 0.3) {
					    		bb_i [i]=21;
					    }
					    	if(aa_i[i]  == 0.4) {
					    		bb_i [i]=26;
					    }
					    	if(aa_i[i]  == 0.5) {
					    		bb_i [i]=30;
					    }
					    	if(aa_i[i] == 0.6) {
					    		bb_i [i]=33;
					    }
					    	if(aa_i[i]  == 0.7) {
					    		bb_i [i]=35;
					    }
					    	if(aa_i[i] == 0.8) {
					    		bb_i [i]=36;
					    }
					    	if(aa_i[i]  == 0.9) {
					    		bb_i [i]=36.5;
					    }
					    	if(aa_i[i]  == 1) {
					    		bb_i [i]=36.75;
					    }
					    	if(aa_i[i]  < 0.1 && aa_i[i]  >0) {
					    		bb_i [i]=((8*aa_i[i] )/0.1) +0 ;
					    }	
					    	if(aa_i[i]  < 0.2 && aa_i[i]  >0.1) {
					    		bb_i [i]=((7*(aa_i[i] -0.1))/0.1) +8 ;
					    }	
					    	if(aa_i[i] < 0.3 && aa_i[i]  >0.2) {
					    		bb_i [i]=((6*(aa_i[i] -0.2))/0.1) +15 ;
					    }	
					    	if(aa_i[i]  < 0.4 && aa_i[i]  >0.3) {
					    		bb_i [i]=((5*(aa_i[i] -0.3))/0.1) +21 ;
					    }	
					    	if(aa_i[i]  < 0.5 && aa_i[i]  >0.4) {
					    		bb_i [i]=((4*(aa_i[i] -0.4))/0.1) +26 ;
					    }	
					    	if(aa_i[i]  < 0.6 && aa_i[i]  >0.5) {
					    		bb_i [i]=((3*(aa_i[i] -0.5))/0.1) +30 ;
					    }	
					    	if(aa_i[i]  < 0.7 && aa_i[i]  >0.6) {
					    		bb_i [i]=((2*(aa_i[i] -0.6))/0.1) +33 ;
					    }	
					    	if(aa_i[i]  < 0.8 && aa_i[i]  >0.7) {
					    		bb_i [i]=((1*(aa_i[i] -0.7))/0.1) +35 ;
					    }	
					    	if(aa_i[i]  < 0.9 && aa_i[i]  >0.8) {
					    		bb_i [i]=((0.5*(aa_i[i] -0.8))/0.1) +36 ;
					    }	
					    	if(aa_i[i]  < 1 && aa_i[i]  >0.9) {
					    		bb_i [i]=((0.25*(aa_i[i] -0.9))/0.1) +36.5 ;
					    }	
					    	if(aa_i[i] > 1 && aa_i[i]  < 1.1) {
					    		bb_i [i]=((0.25*(aa_i[i] -1))/0.1) +36.5 ;
					    }	
					    	
					}
								
					
			double otherobject_effgraph2=0;
			for(int i=0; i<TypeOfCourse; i++) {
				otherobject_effgraph2= otherobject_effgraph2+ (bb_i [i]);
				}
			System.out.println(otherobject_effgraph2);
	        System.out.println("Graph efficiency objective value of the Model2 at the above");	
		            
		           
					double total2=0;
					for(int i=0; i<TypeOfCourse; i++) {
						for(int b=0 ; b<NumberOfDistrictGroup; b++) {
		                        total2 = total2 + (cplex.getValue(u[i][b]));

		                }
		            }
					
		         
		            //z2=total2;
		            System.out.println(total2);
		            System.out.println("AF-C"); 
		            System.out.println();
		            
		            
		            double[][] A = new double[NumberOfDistrictGroup][TypeOfCourse];
	                double total3=0;
	                double total4=0;
	                double total5=0;
	                double total9=0;
	                double total10=0;
	                
	                double total6=0;
	                double total7=0;
	                double total8=0;
	                double total11=0;
	                double total12=0;
	                
	                //for(int i=0;i<TypeOfCourse;i++){
					for (int j=0;j<PossibleCourseLocations;j++){	
						if(cplex.getValue(y[0][j])>=0.9){
						total3 = total3 + (p[0][j][0]);	
						total4 = total4 + (p[0][j][1]);	
						total5 = total5 + (p[0][j][2]);	
						total9 = total9 + (p[0][j][3]);	
						total10 = total10 + (p[0][j][4]);	
											
						A[0][0]=total3;	
						A[1][0]=total4;
						A[2][0]=total5;
						A[3][0]=total9;	
						A[4][0]=total10;																				
							}
						}
					
					for (int j=0;j<PossibleCourseLocations;j++){	
						if(cplex.getValue(y[1][j])>=0.9){
							total6 = total6 + (p[1][j][0]);	
							total7 = total7 + (p[1][j][1]);	
							total8 = total8 + (p[1][j][2]);	
							total11 = total11 + (p[1][j][3]);	
							total12 = total12 + (p[1][j][4]);	
							
							A[0][1]=total6;
							A[1][1]=total7;
							A[2][1]=total8;
							A[3][1]=total11;
							A[4][1]=total12;
							}
						}
						
							System.out.println(total3);
							System.out.println(total4);
							System.out.println(total5);
							System.out.println(total9);
							System.out.println(total10);
							
							System.out.println(total6);
							System.out.println(total7);
							System.out.println(total8);
							System.out.println(total11);
							System.out.println(total12);
							
			//denemeeeee!!!!!!!!!
							
							FileWriter fileWriterr;
							try {
								fileWriterr = new FileWriter("C:\\Users\\dakol\\Desktop\\NewData_AEF_C_objectives.txt", true);					
							fileWriterr.write(String.valueOf(total1));
							fileWriterr.write("\n");
							fileWriterr.write("AE-L");
							fileWriterr.write("\n");
							fileWriterr.write(String.valueOf(total2));
							fileWriterr.write("\n");
							fileWriterr.write("AF-C");
							fileWriterr.write("\n");
							fileWriterr.write(String.valueOf(othervalue3));
							fileWriterr.write("\n");
							fileWriterr.write("AE-C");
							fileWriterr.write("\n");
							fileWriterr.write(String.valueOf(othervalue1));
							fileWriterr.write("\n");
							fileWriterr.write("CW1");
							fileWriterr.write("\n");
							fileWriterr.write(String.valueOf(othervalue2));
							fileWriterr.write("\n");
							fileWriterr.write("CW2");
							fileWriterr.write("\n");
							fileWriterr.write("\n");
							fileWriterr.write("\n");
		                    fileWriterr.close();		}
		                    catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
				
		                    FileWriter owa;
							try {
								owa = new FileWriter("C:\\Users\\dakol\\Desktop\\owa_NewData_CW.txt", true);
						
		                    owa.write(String.valueOf(othervalue1));
		                    owa.write("\n");
		                    owa.close();	} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					  
		                    FileWriter eff;
							try {
								eff = new FileWriter("C:\\Users\\dakol\\Desktop\\Owa_NewData_CW2.txt", true);
						
							eff.write(String.valueOf(othervalue2));
							eff.write("\n");
		                    eff.close();	} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		                    
		                    FileWriter fair;
							try {
								fair = new FileWriter("C:\\Users\\dakol\\Desktop\\aggr_eff_NewData_AE_L.txt", true);
						
		                    fair.write(String.valueOf(total1));
		                    fair.write("\n");
		                    fair.close();	} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		                    
		                    FileWriter u0;
							try {
								u0 = new FileWriter("C:\\Users\\dakol\\Desktop\\aggr_fair_NewData_AF_C.txt", true);
						
							u0.write(String.valueOf(total2));
							u0.write("\n");
		                    u0.close();	} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		                    
		                  
		                    
		                    FileWriter fw;
							try {
								fw = new FileWriter("C:\\Users\\dakol\\Desktop\\NewData_AEF_C_y_values.txt", true);
						
				            for(int i=0;i<TypeOfCourse;i++){
		    					for (int j=0;j<PossibleCourseLocations;j++){	
		    						double yy = cplex.getValue(y[i][j]);
		    						fw.write("y[" + (i) + "][" + (j) + "]="+String.valueOf(yy));
						            	//fw.write(String.valueOf(yy));
						            	fw.write("\n");
						            	
									}
								}
				            	fw.close();	} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
				         

				            	  FileWriter eff_graph;
								try {
									eff_graph = new FileWriter("C:\\Users\\dakol\\Desktop\\NewData_AE_C.txt", true);
								
				                    eff_graph.write(String.valueOf(othervalue3));
				                    eff_graph.write("\n");
				                    eff_graph.close();} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							  
								
								FileWriter deneme;
								try {
									deneme = new FileWriter("C:\\Users\\dakol\\Desktop\\NewData_AEF_C_deneme.txt", true);
				    						if(cplex.getValue(y[0][0])>=0.9) {
				    							deneme.write("Etimesgut_H");
				    				    		deneme.write("\n");
				    				    }
				    						if(cplex.getValue(y[0][1])>=0.9) {
				    							deneme.write("Cankaya_H");
				    				    		deneme.write("\n");
				    				    }
				    						if(cplex.getValue(y[0][2])>=0.9) {
				    							deneme.write("Yenimahalle_H ");
				    				    		deneme.write("\n");
				    				    }
				    						if(cplex.getValue(y[0][3])>=0.9) {
				    							deneme.write("Sincan_H ");
				    				    		deneme.write("\n");
				    				    }if(cplex.getValue(y[0][4])>=0.9) {
			 							deneme.write("Cubuk_H");
			 				    		deneme.write("\n");
				    				    }
			 						if(cplex.getValue(y[0][5])>=0.9) {
			 							deneme.write("Kecioren_H");
			 				    		deneme.write("\n");
			 						}
			 						if(cplex.getValue(y[0][6])>=0.9) {
			 							deneme.write("Polatli_H");
			 				    		deneme.write("\n");
			 				    }
			 						if(cplex.getValue(y[0][7])>=0.9) {
			 							deneme.write("Pursaklar_H");
			 				    		deneme.write("\n");
			 				    }
			 						if(cplex.getValue(y[0][8])>=0.9) {
			 							deneme.write("Mamak_H ");
			 				    		deneme.write("\n");
			 				    }
			 					
			 				    if(cplex.getValue(y[0][9])>=0.9) {
										deneme.write("Beypazari_H");
							    		deneme.write("\n");
			 				    }
									if(cplex.getValue(y[0][10])>=0.9) {
										deneme.write("Golbasi_H");
							    		deneme.write("\n");
									}
									if(cplex.getValue(y[0][11])>=0.9) {
										deneme.write("Altindag_H ");
							    		deneme.write("\n");
							    }
									if(cplex.getValue(y[0][12])>=0.9) {
										deneme.write("sereflikochisar_H");
							    		deneme.write("\n");
							    }
									if(cplex.getValue(y[0][13])>=0.9) {
										deneme.write("Kahramankazan_H ");
							    		deneme.write("\n");
							    }
									if(cplex.getValue(y[0][14])>=0.9) {
										deneme.write("Elmadag_H ");
							    		deneme.write("\n");
							    }if(cplex.getValue(y[0][15])>=0.9) {
									deneme.write("Akyurt_H ");
						    		deneme.write("\n");
							    }  		
							    
								if(cplex.getValue(y[1][0])>=0.9) {
									deneme.write("Etimesgut_V");
						    		deneme.write("\n");
						    }
								if(cplex.getValue(y[1][1])>=0.9) {
									deneme.write("Cankaya_V");
						    		deneme.write("\n");
						    }
								if(cplex.getValue(y[1][2])>=0.9) {
									deneme.write("Yenimahalle_V ");
						    		deneme.write("\n");
						    }
								if(cplex.getValue(y[1][3])>=0.9) {
									deneme.write("Sincan_V ");
						    		deneme.write("\n");
						    }if(cplex.getValue(y[1][4])>=0.9) {
								deneme.write("Cubuk_V");
					    		deneme.write("\n");
						    }
							if(cplex.getValue(y[1][5])>=0.9) {
								deneme.write("Kecioren_V");
					    		deneme.write("\n");
							}
							if(cplex.getValue(y[1][6])>=0.9) {
								deneme.write("Polatli_V");
					    		deneme.write("\n");
					    }
							if(cplex.getValue(y[1][7])>=0.9) {
								deneme.write("Pursaklar_V");
					    		deneme.write("\n");
					    }
							if(cplex.getValue(y[1][8])>=0.9) {
								deneme.write("Mamak_V ");
					    		deneme.write("\n");
					    }				
					    if(cplex.getValue(y[1][9])>=0.9) {
							deneme.write("Beypazari_V");
				    		deneme.write("\n");
					    }
						if(cplex.getValue(y[1][10])>=0.9) {
							deneme.write("Golbasi_V");
				    		deneme.write("\n");
						}
						if(cplex.getValue(y[1][11])>=0.9) {
							deneme.write("Altindag_V ");
				    		deneme.write("\n");
				    }
						if(cplex.getValue(y[1][12])>=0.9) {
							deneme.write("Sereflikochisar_V");
				    		deneme.write("\n");
				    }
						if(cplex.getValue(y[1][13])>=0.9) {
							deneme.write("Kahramankazan_V ");
				    		deneme.write("\n");
				    }
						if(cplex.getValue(y[1][14])>=0.9) {
							deneme.write("Elmadag_V ");
				    		deneme.write("\n");
				    }if(cplex.getValue(y[1][15])>=0.9) {
						deneme.write("Akyurt_V ");
			 		deneme.write("\n");
				    }  		 
				    						
						            deneme.write("\n");
						            deneme.write("\n");
						            deneme.write("\n");
						            deneme.close();		
						            } catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}	
								
		                    
		                    FileWriter fileWriter;
							try {
								fileWriter = new FileWriter("C:\\Users\\dakol\\Desktop\\NewData_AEF_C_z_values.txt", true);
								fileWriter.write(String.valueOf(total3));
								fileWriter.write("\n");
								fileWriter.write(String.valueOf(total4));
								fileWriter.write("\n");
								fileWriter.write(String.valueOf(total5));
								fileWriter.write("\n");
								fileWriter.write(String.valueOf(total9));			
								fileWriter.write("\n");
								fileWriter.write(String.valueOf(total10));
								fileWriter.write("\n");
								fileWriter.write(String.valueOf(total6));
								fileWriter.write("\n");
								fileWriter.write(String.valueOf(total7));
								fileWriter.write("\n");
								fileWriter.write(String.valueOf(total8));
								fileWriter.write("\n");
								fileWriter.write(String.valueOf(total11));
								fileWriter.write("\n");
								fileWriter.write(String.valueOf(total12));
								fileWriter.write("\n");
								fileWriter.write("\n");
								fileWriter.write("\n");
		                    fileWriter.close();	} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		                    
							
		      
		            if(cplex.isPrimalFeasible()==true) {
		                feasible=true;
		                epsilon=total2+stepsize;
		            }
		            else {
		                feasible=false;
		            }

		        	} catch (IloException e) {
		        		e.printStackTrace();
		        	}
		        				
		    		
		

	}

}
