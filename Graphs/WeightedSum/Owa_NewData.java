

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


public class Owa_NewData {
	
	static boolean feasible;
    static double epsilon;
    static double stepsize;
    static double z2;
    static int numberOfIterations=0;
    static int numberofModelsSolved=0;
    
    static double[]ED = {0, 0}; //minimum total value in that benefit type
    static double[] EY = {18345, 12234}; //maximum total value in that benefit type  //2.nin 11537 olmasi lazim
    static double[] Utility = {0,8,15,21,26,30,33,35,36,36.5,36.75};
    static double[] Threshold = {0,250,500,750,1000,1250,1500,1750,2000,2250,2500};
    static double [][] L =  {{250,250,250,250,250,250,250,250,250,250},{250,250,250,250,250,250,250,250,250,250}};
    static int NumberOfGap= 10; //0 saydigi icin bunu 10 biraktim
    
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
				Scanner pscanner = new Scanner(new File("C:\\Users\\dakol\\Desktop\\NewData\\p_0jk.txt"));
		        int [][] p1=new int [PossibleCourseLocations][NumberOfDistrictGroup];
				for(int j = 0; j<PossibleCourseLocations; j++) {
					for(int k =0; k<NumberOfDistrictGroup; k++) {
						p1[j][k] = pscanner.nextInt();
						}
					}
						
			//Read how many patient group in which location type
			Scanner p2scanner = new Scanner(new File("C:\\Users\\dakol\\Desktop\\NewData\\p_1jk.txt"));
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
							        
				
													
					
		// cost of projects	
		Scanner Uscanner = new Scanner(new File("C:\\Users\\dakol\\Desktop\\NewData\\CostDistrict.txt"));
        C=new int [TypeOfCourse][PossibleCourseLocations];
        for(int i =0; i<TypeOfCourse; i++) {
        	for(int j = 0; j<PossibleCourseLocations; j++) {
				C[i][j] = Uscanner.nextInt();
				} }
		
			
       stepsize=0.0005;
       solve();

  
        //feasible=true;
        double total2 = 1;
        
        
                  //***************MODEL*********//
  
        while(feasible) {	
        	System.out.println(epsilon);
        	
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
							X_bar[i][b][m]= cplex.numVar(0, 250,IloNumVarType.Float );
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
				
					for(int b=0 ; b<NumberOfDistrictGroup; b++) {
						obj1.addTerm(0.0005,u[1][b]);
					}
								
				//IloLinearNumExpr obj1 = cplex.linearNumExpr();
					for(int b=0 ; b<NumberOfDistrictGroup; b++) {
						obj1.addTerm(1,u[0][b]);
					}
				
					cplex.addMaximize(obj1 );
	
				//************constraints***************/
				
			IloLinearNumExpr objective_cons = cplex.linearNumExpr();
			for (int i=1; i<2;i++){
				for(int b=0 ; b<NumberOfDistrictGroup; b++) {
					objective_cons.addTerm(1,u[i][b]);
				}
			}
			cplex.addGe(objective_cons, epsilon);
						
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
							cons7.addTerm( -((Utility[j+1]-Utility[j])/250), X_bar[i][b][j]);
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
	
				 //6 her tipten her paire en az bir tane ata 
						/*for now it is not neccessary
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
						
				cplex.exportModel("NewData_owa.lp");
				cplex.solve();
				
				
				 if(cplex.isPrimalFeasible()==false){
	                    feasible=false;
					 break;
	                }
				 
				 //print the objective value
				System.out.println("objective: " + cplex.getObjValue());

				
				for(int i=0;i<TypeOfCourse;i++){
					for (int j=0;j<PossibleCourseLocations;j++){
						if(cplex.getValue(y[i][j])>=0){
							System.out.println("y[" + (i) + "][" + (j) + "]="+cplex.getValue(y[i][j]));
						}
					}
				}

			/*	for (int i=0; i<TypeOfCourse;i++){
					for(int b=0;b<NumberOfDistrictGroup;b++) {
						for (int j=0;j<NumberOfGap;j++){			
							if(cplex.getValue(X_bar[i][b][j])>=0){
								System.out.println("X_bar[" + (i) + "][" + (b) + "] [" + (j) + "]="+cplex.getValue(X_bar[i][b][j]));
							}
						}
					} 
				}*/
				
				
				for(int i=0;i<TypeOfCourse;i++){
					for (int k=0;k<NumberOfDistrictGroup;k++){
						if(cplex.getValue(z[i][k])>=0){
							System.out.println("z[" + (i) + "][" + (k) + "]="+cplex.getValue(z[i][k]));
						}
					}
				}
				
				//calculations for other model objectives_trial_eff_graph
				double[] aa_i =new double [TypeOfCourse] ;		//ai= totalk uzerinden zik toplami bolu eyi-edi		
				double [] summation= new double [TypeOfCourse]; //k uzerinden zik toplami yani i kadar var
				
					
					for (int k=0;k<NumberOfDistrictGroup;k++){
						summation[0] = summation[0]+ (cplex.getValue(z[0][k]));
						//summation[1] = summation[1]+ (cplex.getValue(z[1][k]));	
						//aa_i[0] = (cplex.getValue(z[0][k]))/(summation[0]);
				}
					
					for (int k=0;k<NumberOfDistrictGroup;k++){
						summation[1] = summation[1]+ (cplex.getValue(z[1][k]));
						//z_ib_N[0][k] = (cplex.getValue(z[0][k]))/(sum[0]);
						//z_ib_N[1][k] = (cplex.getValue(z[1][k]))/(sum[1]);				
					}
				
					aa_i[0]= (summation[0])/EY[0];
					aa_i[1]=(summation[1])/EY[1];
				
				//System.out.println(damla[0][0]/sum[0]);
				System.out.println();
				System.out.println();
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
	
       /* for(int i=0;i<TypeOfCourse;i++){
				if((bb_i [i])>=0){
					System.out.println("bb[" + (i) + "]="+(bb_i [i]));
				}
			}*/
		
				
				//calculations for other model objectives
				double[][] z_ib_N =new double [TypeOfCourse][NumberOfDistrictGroup]  ;				
				double [] sum= new double [TypeOfCourse];
				
					
					for (int k=0;k<NumberOfDistrictGroup;k++){
						sum[0] = sum[0]+ (cplex.getValue(z[0][k]));
						sum[1] = sum[1]+ (cplex.getValue(z[1][k]));	
						z_ib_N[0][k] = (cplex.getValue(z[0][k]))/(sum[0]);
						z_ib_N[1][k] = (cplex.getValue(z[1][k]))/(sum[1]);
				}
					for (int k=0;k<NumberOfDistrictGroup;k++){
						z_ib_N[0][k] = (cplex.getValue(z[0][k]))/(sum[0]);
						z_ib_N[1][k] = (cplex.getValue(z[1][k]))/(sum[1]);				
					}
				
					for(int i=0; i<TypeOfCourse; i++) {
					    for(int k=0;k<NumberOfDistrictGroup;k++){
					    		System.out.println("z_ib_N[" + (i) + "][" + (k) + "]="+(z_ib_N[i][k]));}}
				
				//System.out.println(damla[0][0]/sum[0]);
				System.out.println();
				System.out.println();
				System.out.println();
				
				double[][] ff =new double [TypeOfCourse][NumberOfDistrictGroup];	
						for(int i=0; i<TypeOfCourse; i++) {
						    for(int k=0;k<NumberOfDistrictGroup;k++){
						    	if(z_ib_N[i][k] == 0) {
						    		ff[i][k]=0;
						    }
						    	if(z_ib_N[i][k] == 0.1) {
						    		ff[i][k]=8;
						    }
						    	if(z_ib_N[i][k] == 0.2) {
						    		ff[i][k]=15;
						    }
						    	if(z_ib_N[i][k] == 0.3) {
						    		ff[i][k]=21;
						    }
						    	if(z_ib_N[i][k] == 0.4) {
						    		ff[i][k]=26;
						    }
						    	if(z_ib_N[i][k] == 0.5) {
						    		ff[i][k]=30;
						    }
						    	if(z_ib_N[i][k] == 0.6) {
						    		ff[i][k]=33;
						    }
						    	if(z_ib_N[i][k] == 0.7) {
						    		ff[i][k]=35;
						    }
						    	if(z_ib_N[i][k] == 0.8) {
						    		ff[i][k]=36;
						    }
						    	if(z_ib_N[i][k] == 0.9) {
						    		ff[i][k]=36.5;
						    }
						    	if(z_ib_N[i][k] == 1) {
						    		ff[i][k]=36.75;
						    }
						    	if(z_ib_N[i][k] < 0.1 && z_ib_N[i][k] >0) {
						    		ff[i][k]=((8*z_ib_N[i][k])/0.1) +0 ;
						    }	
						    	if(z_ib_N[i][k] < 0.2 && z_ib_N[i][k] >0.1) {
						    		ff[i][k]=((7*(z_ib_N[i][k]-0.1))/0.1) +8 ;
						    }	
						    	if(z_ib_N[i][k] < 0.3 && z_ib_N[i][k] >0.2) {
						    		ff[i][k]=((6*(z_ib_N[i][k]-0.2))/0.1) +15 ;
						    }	
						    	if(z_ib_N[i][k] < 0.4 && z_ib_N[i][k] >0.3) {
						    		ff[i][k]=((5*(z_ib_N[i][k]-0.3))/0.1) +21 ;
						    }	
						    	if(z_ib_N[i][k] < 0.5 && z_ib_N[i][k] >0.4) {
						    		ff[i][k]=((4*(z_ib_N[i][k]-0.4))/0.1) +26 ;
						    }	
						    	if(z_ib_N[i][k] < 0.6 && z_ib_N[i][k] >0.5) {
						    		ff[i][k]=((3*(z_ib_N[i][k]-0.5))/0.1) +30 ;
						    }	
						    	if(z_ib_N[i][k] < 0.7 && z_ib_N[i][k] >0.6) {
						    		ff[i][k]=((2*(z_ib_N[i][k]-0.6))/0.1) +33 ;
						    }	
						    	if(z_ib_N[i][k] < 0.8 && z_ib_N[i][k] >0.7) {
						    		ff[i][k]=((1*(z_ib_N[i][k]-0.7))/0.1) +35 ;
						    }	
						    	if(z_ib_N[i][k] < 0.9 && z_ib_N[i][k] >0.8) {
						    		ff[i][k]=((0.5*(z_ib_N[i][k]-0.8))/0.1) +36 ;
						    }	
						    	if(z_ib_N[i][k] < 1 && z_ib_N[i][k] >0.9) {
						    		ff[i][k]=((0.25*(z_ib_N[i][k]-0.9))/0.1) +36.5 ;
						    }	
						    	if(z_ib_N[i][k] > 1 && z_ib_N[i][k] < 1.1) {
						    		ff[i][k]=((0.25*(z_ib_N[i][k]-1))/0.1) +36.5 ;
						    }	
						    	/*else {
						    		  System.out.println("You did it wrong my friend");
						    		}*/
						}
					}		
					
						
				double otherobject2=0;
				for(int i=0; i<TypeOfCourse; i++) {
					for(int b=0 ; b<NumberOfDistrictGroup; b++) {
						otherobject2= otherobject2+ (ff[i][b]);
					}
				}
				
				System.out.println(otherobject2);
	            System.out.println("Aggregated fairness objective value of the Model2 at the above");	
			
	        /*    for(int i=0;i<TypeOfCourse;i++){
					for (int k=0;k<NumberOfDistrictGroup;k++){
						if((ff[i][k])>=0){
							System.out.println("f[" + (i) + "][" + (k) + "]="+(ff[i][k]));
						}
					}
				}
	            	   */         
	            
				double otherobject1=0;
				double diff[]=new double[TypeOfCourse];
				for(int i=0; i<TypeOfCourse; i++) {
					diff[i] = 1/(EY[i]-ED[i]);
						}
				for(int i=0; i<TypeOfCourse; i++) {
					for(int k=0 ; k<NumberOfDistrictGroup; k++) {
						otherobject1=otherobject1+(diff[i]*100*(cplex.getValue(z[i][k])));
					}
				}
	            
	            System.out.println(otherobject1);
	            System.out.println("AE-L");
	            
	            
				double total1=0;
				for(int i=0; i<1; i++) {
					for(int b=0 ; b<NumberOfDistrictGroup; b++) {
                            total1 = total1 + (cplex.getValue(u[i][b]));
                           // System.out.println(cplex.getValue(u[i][b]));
                    }
                }

				System.out.println();
                System.out.println(total1);
                System.out.println("CW1");	
                System.out.println();
				
				total2=0;
				for(int i=1; i<2; i++) {
					for(int b=0 ; b<NumberOfDistrictGroup; b++) {
                            total2 = total2 + (cplex.getValue(u[i][b]));
                           // System.out.println(cplex.getValue(u[i][b]));
                    }
                }
				
                
                System.out.println(total2);
                System.out.println("CW2");	
               // System.out.println();

                
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
                
                //////denemeeeeeeeeeeeeeeeeeee!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
						
						
						
						FileWriter fileWriterr = new FileWriter("C:\\Users\\dakol\\Desktop\\OWA_objectives_nogapp.txt", true);
						fileWriterr.write(String.valueOf(otherobject1));
						fileWriterr.write("\n");
						fileWriterr.write("AE-L");
						fileWriterr.write("\n");
						fileWriterr.write(String.valueOf(otherobject2));
						fileWriterr.write("\n");
						fileWriterr.write("AF-C");
						fileWriterr.write("\n");
						fileWriterr.write(String.valueOf(otherobject_effgraph2));
						fileWriterr.write("\n");
						fileWriterr.write("AE-C");
						fileWriterr.write("\n");
						fileWriterr.write(String.valueOf(total1));
						fileWriterr.write("\n");
						fileWriterr.write("CW1");
						fileWriterr.write("\n");
						fileWriterr.write(String.valueOf(total2));
						fileWriterr.write("\n");
						fileWriterr.write("CW2");
						fileWriterr.write("\n");
						fileWriterr.write("\n");
						fileWriterr.write("\n");
	                    fileWriterr.close();		
	                    				
	                    FileWriter eff_graph = new FileWriter("C:\\Users\\dakol\\Desktop\\owa_NewData_AE_C.txt", true);
	                    eff_graph.write(String.valueOf(otherobject_effgraph2));
	                    eff_graph.write("\n");
	                    eff_graph.close();
				  
	                    FileWriter eff = new FileWriter("C:\\Users\\dakol\\Desktop\\owa_NewData_AE_L.txt", true);
						eff.write(String.valueOf(otherobject1));
						eff.write("\n");
	                    eff.close();
	                    
	                    FileWriter fair = new FileWriter("C:\\Users\\dakol\\Desktop\\owa_NewData_AF_C.txt", true);
	                    fair.write(String.valueOf(otherobject2));
	                    fair.write("\n");
	                    fair.close();
	                    
	                    FileWriter u0 = new FileWriter("C:\\Users\\dakol\\Desktop\\owa_CW1.txt", true);
						u0.write(String.valueOf(total1));
						u0.write("\n");
	                    u0.close();
	                    
	                    FileWriter u1 = new FileWriter("C:\\Users\\dakol\\Desktop\\owa_CW2.txt", true);
						u1.write(String.valueOf(total2));
						u1.write("\n");
	                    u1.close();
	                    
	                    
	                    FileWriter fw = new FileWriter("C:\\Users\\dakol\\Desktop\\OWA_NewData_y_val.txt", true);	
				            for(int i=0;i<TypeOfCourse;i++){
		    					for (int j=0;j<PossibleCourseLocations;j++){	
		    						double yy = cplex.getValue(y[i][j]);
		    						fw.write("y[" + (i) + "][" + (j) + "]="+String.valueOf(yy));						         
						            	fw.write("\n");
						            	
									}
		    					fw.write("\n");
								}
				            	fw.close();
	    						
                
				           	 FileWriter deneme= new FileWriter("C:\\Users\\dakol\\Desktop\\owa_NewData_deneme.txt", true);	
					       
					            	if(cplex.getValue(y[0][0])==1) {
		    							deneme.write("Etimesgut_H");
		    				    		deneme.write("\n");
		    				    }
		    						if(cplex.getValue(y[0][1])==1) {
		    							deneme.write("Cankaya_H");
		    				    		deneme.write("\n");
		    				    }
		    						if(cplex.getValue(y[0][2])==1) {
		    							deneme.write("Yenimahalle_H ");
		    				    		deneme.write("\n");
		    				    }
		    						if(cplex.getValue(y[0][3])==1) {
		    							deneme.write("Sincan_H ");
		    				    		deneme.write("\n");
		    				    }if(cplex.getValue(y[0][4])==1) {
	 							deneme.write("Cubuk_H");
	 				    		deneme.write("\n");
		    				    }
	 						if(cplex.getValue(y[0][5])==1) {
	 							deneme.write("Kecioren_H");
	 				    		deneme.write("\n");
	 						}
	 						if(cplex.getValue(y[0][6])==1) {
	 							deneme.write("Polatli_H");
	 				    		deneme.write("\n");
	 				    }
	 						if(cplex.getValue(y[0][7])==1) {
	 							deneme.write("Pursaklar_H");
	 				    		deneme.write("\n");
	 				    }
	 						if(cplex.getValue(y[0][8])==1) {
	 							deneme.write("Mamak_H ");
	 				    		deneme.write("\n");
	 				    }
	 					
	 				    if(cplex.getValue(y[0][9])==1) {
								deneme.write("Beypazari_H");
					    		deneme.write("\n");
	 				    }
							if(cplex.getValue(y[0][10])==1) {
								deneme.write("Golbasi_H");
					    		deneme.write("\n");
							}
							if(cplex.getValue(y[0][11])==1) {
								deneme.write("Altindag_H ");
					    		deneme.write("\n");
					    }
							if(cplex.getValue(y[0][12])==1) {
								deneme.write("sereflikochisar_H");
					    		deneme.write("\n");
					    }
							if(cplex.getValue(y[0][13])==1) {
								deneme.write("Kahramankazan_H ");
					    		deneme.write("\n");
					    }
							if(cplex.getValue(y[0][14])==1) {
								deneme.write("Elmadag_H ");
					    		deneme.write("\n");
					    }if(cplex.getValue(y[0][15])==1) {
							deneme.write("Akyurt_H ");
				    		deneme.write("\n");
					    }  		
					    
						if(cplex.getValue(y[1][0])==1) {
							deneme.write("Etimesgut_V");
				    		deneme.write("\n");
				    }
						if(cplex.getValue(y[1][1])==1) {
							deneme.write("Cankaya_V");
				    		deneme.write("\n");
				    }
						if(cplex.getValue(y[1][2])==1) {
							deneme.write("Yenimahalle_V ");
				    		deneme.write("\n");
				    }
						if(cplex.getValue(y[1][3])==1) {
							deneme.write("Sincan_V ");
				    		deneme.write("\n");
				    }if(cplex.getValue(y[1][4])==1) {
						deneme.write("Cubuk_V");
			    		deneme.write("\n");
				    }
					if(cplex.getValue(y[1][5])==1) {
						deneme.write("Kecioren_V");
			    		deneme.write("\n");
					}
					if(cplex.getValue(y[1][6])==1) {
						deneme.write("Polatli_V");
			    		deneme.write("\n");
			    }
					if(cplex.getValue(y[1][7])==1) {
						deneme.write("Pursaklar_V");
			    		deneme.write("\n");
			    }
					if(cplex.getValue(y[1][8])==1) {
						deneme.write("Mamak_V ");
			    		deneme.write("\n");
			    }				
			    if(cplex.getValue(y[1][9])==1) {
					deneme.write("Beypazari_V");
		    		deneme.write("\n");
			    }
				if(cplex.getValue(y[1][10])==1) {
					deneme.write("Golbasi_V");
		    		deneme.write("\n");
				}
				if(cplex.getValue(y[1][11])==1) {
					deneme.write("Altindag_V ");
		    		deneme.write("\n");
		    }
				if(cplex.getValue(y[1][12])==1) {
					deneme.write("Sereflikochisar_V");
		    		deneme.write("\n");
		    }
				if(cplex.getValue(y[1][13])==1) {
					deneme.write("Kahramankazan_V ");
		    		deneme.write("\n");
		    }
				if(cplex.getValue(y[1][14])==1) {
					deneme.write("Elmadag_V ");
		    		deneme.write("\n");
		    }if(cplex.getValue(y[1][15])==1) {
				deneme.write("Akyurt_V ");
	 		deneme.write("\n");
		    }  			 
			    						
					            deneme.write("\n");
					            deneme.write("\n");
					            deneme.write("\n");
					            deneme.close();	      	
				            	
				            	
					FileWriter fileWriter = new FileWriter("C:\\Users\\dakol\\Desktop\\OWA_NewData_z_value.txt", true);
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
                    fileWriter.close();
    	        
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
	
	
	
      static void solve(){   //public static void
		
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

	//Read how many patient group in which location type
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
						X_bar[i][b][m]= cplex.numVar(0, 250,IloNumVarType.Float );
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

            ////END OF DECISION VARIABLES////


            //Objective
			IloLinearNumExpr obj1 = cplex.linearNumExpr();
			
			
				for(int b=0 ; b<NumberOfDistrictGroup; b++) {
					obj1.addTerm(0.0005,u[1][b]);
				}
						
			
				for(int b=0 ; b<NumberOfDistrictGroup; b++) {
					obj1.addTerm(1,u[0][b]);
				}			
				cplex.addMaximize(obj1 );
				       
                    //constraints/////
          
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
						cons7.addTerm( -((Utility[j+1]-Utility[j])/250), X_bar[i][b][j]);
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
				
					
			 //6 her tipten her paire en az bir tane ata 
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
            cplex.exportModel("NewData_owa2.lp");
       
            
            System.out.println("obj = "+cplex.getObjValue());

			
			for(int i=0;i<TypeOfCourse;i++){
				for (int j=0;j<PossibleCourseLocations;j++){
					if(cplex.getValue(y[i][j])>=0){
						System.out.println("y[" + (i) + "][" + (j) + "]="+cplex.getValue(y[i][j]));
					}
				}
			}
				
			/*	for(int i=0;i<TypeOfCourse;i++){
				for(int b=0 ; b<NumberOfDistrictGroup; b++) {
					if(cplex.getValue(u[i][b])>=0){
						System.out.println("f[" + (i) + "][" + (b) + "]="+cplex.getValue(u[i][b]));
					}
				}
			}
			
			
			
			for (int i=0; i<TypeOfCourse;i++){
				for(int b=0;b<NumberOfDistrictGroup;b++) {
					for (int j=0;j<NumberOfGap;j++){			
						if(cplex.getValue(X_bar[i][b][j])>=0){
							System.out.println("X_bar[" + (i) + "][" + (b) + "] [" + (j) + "]="+cplex.getValue(X_bar[i][b][j]));
						}
					}
				} 
			}
			
			
			for(int i=0;i<TypeOfCourse;i++){
				for (int k=0;k<NumberOfDistrictGroup;k++){
					if(cplex.getValue(z[i][k])>=0){
						System.out.println("z[" + (i) + "][" + (k) + "]="+cplex.getValue(z[i][k]));
					}
				}
			}*/
			
			//calculations for other model objectives_trial_eff_graph
			double[] aa_i =new double [TypeOfCourse] ;		//ai= totalk uzerinden zik toplami bolu eyi-edi		
			double [] summation= new double [TypeOfCourse]; //k uzerinden zik toplami yani i kadar var
			
				
				for (int k=0;k<NumberOfDistrictGroup;k++){
					summation[0] = summation[0]+ (cplex.getValue(z[0][k]));
			
			}
				
				for (int k=0;k<NumberOfDistrictGroup;k++){
					summation[1] = summation[1]+ (cplex.getValue(z[1][k]));
							
				}
			
				aa_i[0]= (summation[0])/EY[0];
				aa_i[1]=(summation[1])/EY[1];
			
	
			System.out.println();
			System.out.println();
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
    System.out.println("AE-C");	

  /*  for(int i=0;i<TypeOfCourse;i++){
			if((bb_i [i])>=0){
				System.out.println("bb[" + (i) + "]="+(bb_i [i]));
			}
		}*/
	
			
			//calculations for other model objectives
			double[][] z_ib_N =new double [TypeOfCourse][NumberOfDistrictGroup]  ;				
			double[][] damla =new double [TypeOfCourse][NumberOfDistrictGroup]  ;
			double [] sum= new double [TypeOfCourse];
			
				
				for (int k=0;k<NumberOfDistrictGroup;k++){
					sum[0] = sum[0]+ (cplex.getValue(z[0][k]));
					sum[1] = sum[1]+ (cplex.getValue(z[1][k]));	
					z_ib_N[0][k] = (cplex.getValue(z[0][k]))/(sum[0]);
					z_ib_N[1][k] = (cplex.getValue(z[1][k]))/(sum[1]);
			}
				for (int k=0;k<NumberOfDistrictGroup;k++){
					z_ib_N[0][k] = (cplex.getValue(z[0][k]))/(sum[0]);
					z_ib_N[1][k] = (cplex.getValue(z[1][k]))/(sum[1]);				
				}
			
				for(int i=0; i<TypeOfCourse; i++) {
				    for(int k=0;k<NumberOfDistrictGroup;k++){
				    		System.out.println(z_ib_N[i][k]);}}
			
			//System.out.println(damla[0][0]/sum[0]);
			System.out.println();
			System.out.println();
			System.out.println();
			
			double[][] ff =new double [TypeOfCourse][NumberOfDistrictGroup];	
					for(int i=0; i<TypeOfCourse; i++) {
					    for(int k=0;k<NumberOfDistrictGroup;k++){
					    	if(z_ib_N[i][k] == 0) {
					    		ff[i][k]=0;
					    }
					    	if(z_ib_N[i][k] == 0.1) {
					    		ff[i][k]=8;
					    }
					    	if(z_ib_N[i][k] == 0.2) {
					    		ff[i][k]=15;
					    }
					    	if(z_ib_N[i][k] == 0.3) {
					    		ff[i][k]=21;
					    }
					    	if(z_ib_N[i][k] == 0.4) {
					    		ff[i][k]=26;
					    }
					    	if(z_ib_N[i][k] == 0.5) {
					    		ff[i][k]=30;
					    }
					    	if(z_ib_N[i][k] == 0.6) {
					    		ff[i][k]=33;
					    }
					    	if(z_ib_N[i][k] == 0.7) {
					    		ff[i][k]=35;
					    }
					    	if(z_ib_N[i][k] == 0.8) {
					    		ff[i][k]=36;
					    }
					    	if(z_ib_N[i][k] == 0.9) {
					    		ff[i][k]=36.5;
					    }
					    	if(z_ib_N[i][k] == 1) {
					    		ff[i][k]=36.75;
					    }
					    	if(z_ib_N[i][k] < 0.1 && z_ib_N[i][k] >0) {
					    		ff[i][k]=((8*z_ib_N[i][k])/0.1) +0 ;
					    }	
					    	if(z_ib_N[i][k] < 0.2 && z_ib_N[i][k] >0.1) {
					    		ff[i][k]=((7*(z_ib_N[i][k]-0.1))/0.1) +8 ;
					    }	
					    	if(z_ib_N[i][k] < 0.3 && z_ib_N[i][k] >0.2) {
					    		ff[i][k]=((6*(z_ib_N[i][k]-0.2))/0.1) +15 ;
					    }	
					    	if(z_ib_N[i][k] < 0.4 && z_ib_N[i][k] >0.3) {
					    		ff[i][k]=((5*(z_ib_N[i][k]-0.3))/0.1) +21 ;
					    }	
					    	if(z_ib_N[i][k] < 0.5 && z_ib_N[i][k] >0.4) {
					    		ff[i][k]=((4*(z_ib_N[i][k]-0.4))/0.1) +26 ;
					    }	
					    	if(z_ib_N[i][k] < 0.6 && z_ib_N[i][k] >0.5) {
					    		ff[i][k]=((3*(z_ib_N[i][k]-0.5))/0.1) +30 ;
					    }	
					    	if(z_ib_N[i][k] < 0.7 && z_ib_N[i][k] >0.6) {
					    		ff[i][k]=((2*(z_ib_N[i][k]-0.6))/0.1) +33 ;
					    }	
					    	if(z_ib_N[i][k] < 0.8 && z_ib_N[i][k] >0.7) {
					    		ff[i][k]=((1*(z_ib_N[i][k]-0.7))/0.1) +35 ;
					    }	
					    	if(z_ib_N[i][k] < 0.9 && z_ib_N[i][k] >0.8) {
					    		ff[i][k]=((0.5*(z_ib_N[i][k]-0.8))/0.1) +36 ;
					    }	
					    	if(z_ib_N[i][k] < 1 && z_ib_N[i][k] >0.9) {
					    		ff[i][k]=((0.25*(z_ib_N[i][k]-0.9))/0.1) +36.5 ;
					    }	
					    	if(z_ib_N[i][k] > 1 && z_ib_N[i][k] < 1.1) {
					    		ff[i][k]=((0.25*(z_ib_N[i][k]-1))/0.1) +36.5 ;
					    }	
					    	/*else {
					    		  System.out.println("You did it wrong my friend");
					    		}*/
					}
				}		
				
					
			/*		for(int i=0;i<TypeOfCourse;i++){
						for(int b=0 ; b<NumberOfDistrictGroup; b++) {
							if((ff[i][b])>=0){
								System.out.println("ff[" + (i) + "][" + (b) + "]="+(ff[i][b]));
							}
						}
					}
					
					for(int i=0;i<TypeOfCourse;i++){
						for (int b=0;b<NumberOfDistrictGroup;b++){
							if((z_ib_N[i][b])>=0){
								System.out.println("z_ik^N[" + (i) + "][" + (b) + "]="+(z_ib_N[i][b]));
							}
						}
					} 
					*/
										
					
			double otherobject2=0;
			for(int i=0; i<TypeOfCourse; i++) {
				for(int b=0 ; b<NumberOfDistrictGroup; b++) {
					otherobject2= otherobject2+ (ff[i][b]);
				}
			}
			
			System.out.println(otherobject2);
            System.out.println("AF-C");	
		
			
			double otherobject1=0;
			double diff[]=new double[TypeOfCourse];
			for(int i=0; i<TypeOfCourse; i++) {
				diff[i] = 1/(EY[i]-ED[i]);
					}
			for(int i=0; i<TypeOfCourse; i++) {
				for(int k=0 ; k<NumberOfDistrictGroup; k++) {
					otherobject1=otherobject1+(diff[i]*100*(cplex.getValue(z[i][k])));
				}
			}

            
            System.out.println(otherobject1);
            System.out.println("AE-L");
			
			
				double total1=0;			
				for(int b=0 ; b<NumberOfDistrictGroup; b++) {
                        total1 = total1 + (cplex.getValue(u[0][b]));
                        //System.out.println(cplex.getValue(u[0][b]));

                }
            

          
            System.out.println(total1);
            System.out.println("CW1");	
            System.out.println();
			
				double total2=0;			
				for(int b=0 ; b<NumberOfDistrictGroup; b++) {
                        total2 = total2 + (cplex.getValue(u[1][b]));
                       // System.out.println(cplex.getValue(u[1][b]));
                }
            
			
            //System.out.println();
			z2=total2;
            System.out.println(total2);
            System.out.println("CW2");	
            
            
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
					
			///DENEMEEEE!!!!!!!!!!!!!!!!!!!!
					
					FileWriter fileWriterr;
					try {
						fileWriterr = new FileWriter("C:\\Users\\dakol\\Desktop\\OWA_objectives_nogapp.txt", true);					
					fileWriterr.write(String.valueOf(otherobject1));
					fileWriterr.write("\n");
					fileWriterr.write("AE-L");
					fileWriterr.write("\n");
					fileWriterr.write(String.valueOf(otherobject2));
					fileWriterr.write("\n");
					fileWriterr.write("AF-C");
					fileWriterr.write("\n");
					fileWriterr.write(String.valueOf(otherobject_effgraph2));
					fileWriterr.write("\n");
					fileWriterr.write("AE-C");
					fileWriterr.write("\n");
					fileWriterr.write(String.valueOf(total1));
					fileWriterr.write("\n");
					fileWriterr.write("CW1");
					fileWriterr.write("\n");
					fileWriterr.write(String.valueOf(total2));
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
                    				
                    FileWriter eff_graph;
					try {
						eff_graph = new FileWriter("C:\\Users\\dakol\\Desktop\\owa_NewData_AE_C.txt", true);
					 
                    eff_graph.write(String.valueOf(otherobject_effgraph2));
                    eff_graph.write("\n");
                    eff_graph.close();}
                    catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			  
                    FileWriter eff;
					try {
						eff = new FileWriter("C:\\Users\\dakol\\Desktop\\owa_NewData_AE_L.txt", true);
					
					eff.write(String.valueOf(otherobject1));
					eff.write("\n");
                    eff.close();} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    
                    FileWriter fair;
					try {
						fair = new FileWriter("C:\\Users\\dakol\\Desktop\\owa_NewData_AF_C.txt", true);
					
                    fair.write(String.valueOf(otherobject2));
                    fair.write("\n");
                    fair.close();} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    
                    
                    FileWriter u0;
					try {
						u0 = new FileWriter("C:\\Users\\dakol\\Desktop\\owa_CW1.txt", true);
					
					u0.write(String.valueOf(total1));
					u0.write("\n");
                    u0.close();} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    
                    FileWriter u1;
					try {
						u1 = new FileWriter("C:\\Users\\dakol\\Desktop\\owa_CW2.txt", true);
					
					u1.write(String.valueOf(total2));
					u1.write("\n");
                    u1.close();} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
          
                    
                    FileWriter fw;
					try {
						fw = new FileWriter("C:\\Users\\dakol\\Desktop\\OWA_NewData_y_val.txt", true);
				
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
    			
			    	 FileWriter deneme;
					try {
						deneme = new FileWriter("C:\\Users\\dakol\\Desktop\\owa_NewData_deneme.txt", true);
	    						if(cplex.getValue(y[0][0])==1) {
	    							deneme.write("Etimesgut_H");
	    				    		deneme.write("\n");
	    				    }
	    						if(cplex.getValue(y[0][1])==1) {
	    							deneme.write("Cankaya_H");
	    				    		deneme.write("\n");
	    				    }
	    						if(cplex.getValue(y[0][2])==1) {
	    							deneme.write("Yenimahalle_H ");
	    				    		deneme.write("\n");
	    				    }
	    						if(cplex.getValue(y[0][3])==1) {
	    							deneme.write("Sincan_H ");
	    				    		deneme.write("\n");
	    				    }if(cplex.getValue(y[0][4])==1) {
 							deneme.write("Cubuk_H");
 				    		deneme.write("\n");
	    				    }
 						if(cplex.getValue(y[0][5])==1) {
 							deneme.write("Kecioren_H");
 				    		deneme.write("\n");
 						}
 						if(cplex.getValue(y[0][6])==1) {
 							deneme.write("Polatli_H");
 				    		deneme.write("\n");
 				    }
 						if(cplex.getValue(y[0][7])==1) {
 							deneme.write("Pursaklar_H");
 				    		deneme.write("\n");
 				    }
 						if(cplex.getValue(y[0][8])==1) {
 							deneme.write("Mamak_H ");
 				    		deneme.write("\n");
 				    }
 					
 				    if(cplex.getValue(y[0][9])==1) {
							deneme.write("Beypazari_H");
				    		deneme.write("\n");
 				    }
						if(cplex.getValue(y[0][10])==1) {
							deneme.write("Golbasi_H");
				    		deneme.write("\n");
						}
						if(cplex.getValue(y[0][11])==1) {
							deneme.write("Altindag_H ");
				    		deneme.write("\n");
				    }
						if(cplex.getValue(y[0][12])==1) {
							deneme.write("sereflikochisar_H");
				    		deneme.write("\n");
				    }
						if(cplex.getValue(y[0][13])==1) {
							deneme.write("Kahramankazan_H ");
				    		deneme.write("\n");
				    }
						if(cplex.getValue(y[0][14])==1) {
							deneme.write("Elmadag_H ");
				    		deneme.write("\n");
				    }if(cplex.getValue(y[0][15])==1) {
						deneme.write("Akyurt_H ");
			    		deneme.write("\n");
				    }  		
				    
					if(cplex.getValue(y[1][0])==1) {
						deneme.write("Etimesgut_V");
			    		deneme.write("\n");
			    }
					if(cplex.getValue(y[1][1])==1) {
						deneme.write("Cankaya_V");
			    		deneme.write("\n");
			    }
					if(cplex.getValue(y[1][2])==1) {
						deneme.write("Yenimahalle_V ");
			    		deneme.write("\n");
			    }
					if(cplex.getValue(y[1][3])==1) {
						deneme.write("Sincan_V ");
			    		deneme.write("\n");
			    }if(cplex.getValue(y[1][4])==1) {
					deneme.write("Cubuk_V");
		    		deneme.write("\n");
			    }
				if(cplex.getValue(y[1][5])==1) {
					deneme.write("Kecioren_V");
		    		deneme.write("\n");
				}
				if(cplex.getValue(y[1][6])==1) {
					deneme.write("Polatli_V");
		    		deneme.write("\n");
		    }
				if(cplex.getValue(y[1][7])==1) {
					deneme.write("Pursaklar_V");
		    		deneme.write("\n");
		    }
				if(cplex.getValue(y[1][8])==1) {
					deneme.write("Mamak_V ");
		    		deneme.write("\n");
		    }				
		    if(cplex.getValue(y[1][9])==1) {
				deneme.write("Beypazari_V");
	    		deneme.write("\n");
		    }
			if(cplex.getValue(y[1][10])==1) {
				deneme.write("Golbasi_V");
	    		deneme.write("\n");
			}
			if(cplex.getValue(y[1][11])==1) {
				deneme.write("Altindag_V ");
	    		deneme.write("\n");
	    }
			if(cplex.getValue(y[1][12])==1) {
				deneme.write("Sereflikochisar_V");
	    		deneme.write("\n");
	    }
			if(cplex.getValue(y[1][13])==1) {
				deneme.write("Kahramankazan_V ");
	    		deneme.write("\n");
	    }
			if(cplex.getValue(y[1][14])==1) {
				deneme.write("Elmadag_V ");
	    		deneme.write("\n");
	    }if(cplex.getValue(y[1][15])==1) {
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
					fileWriter = new FileWriter("C:\\Users\\dakol\\Desktop\\OWA_NewData_z_value.txt", true);
			
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
