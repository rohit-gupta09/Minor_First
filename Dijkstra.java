package minorFirst;
import java.util.*;
import java.lang.*;

public class Dijkstra {
	
	static final int vertices=6;
	
	int mindistance(int array[],Boolean set[])
	{
		int min=Integer.MAX_VALUE;
		int min_index=-1;
		for(int j=0;j<vertices;j++)
		{
			if(set[j]==false && array[j]<=min)
			{
				min=array[j];
				min_index=j;
			}
		}
		
		return min_index;
	}
	
	void printMinpath(int array[])
	{
		System.out.println("Vertices  \t Minimun Distance from Source");
		for(int i=0;i<vertices;i++)
		{
			System.out.println(i+" \t\t "+array[i]+"\n");
		}
	}
	
	void solution(int[][] graph,int source)
	{
		//nodes we have to visit
		int array[]=new int[vertices];
		
		//check whether the node is visited or not
		Boolean set[]=new Boolean[vertices];
		
		for(int i=0;i<vertices;i++)
		{
			array[i]=Integer.MAX_VALUE;
			set[i]=false;
		}
		
		array[source]=0;
		
		for(int j=0;j<vertices-1;j++)
		{
			int x=mindistance(array,set);
			set[x]=true;
			
			for(int k=0;k<vertices;k++)
			{
				if(!set[k] && graph[x][k]!=0 && array[x]!=Integer.MAX_VALUE && array[x]+graph[x][k]<array[k])
				{
					array[k]=array[x]+graph[x][k];
				}
			}
		}
		
		printMinpath(array);
	}
}

class Main
{
public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int x=sc.nextInt();
		Runtime runtime = Runtime.getRuntime();
		long memoryMax = runtime.maxMemory();
//		System.out.println(memoryMax);
//	    long beforememoryUsed = runtime.totalMemory() - runtime.freeMemory();
	    
	      
		int graph[][]=new int[][] {
			{0,2,1,0,0,0},
			{2,0,7,0,8,4},
			{1,7,0,7,0,3},
			{0,0,7,0,8,4},
			{0,8,0,8,0,5},
			{0,4,3,4,5,0}
		};
		
		Dijkstra di=new Dijkstra();
		long startTime = System.nanoTime();
//		System.out.println(startTime);
		di.solution(graph,x);
		long endTime = System.nanoTime();
	    long searchTime = (endTime - startTime);
//	    System.out.println(endTime);
	    System.out.println("Computation time : "+searchTime+" nanosecond");
	    long aftermemoryUsed = runtime.totalMemory() - runtime.freeMemory();
	    
//	    System.out.println(aftermemoryUsed);
	    double memoryUsedPercent = (aftermemoryUsed*100.0)/memoryMax;
	    System.out.println("memoryUsedPercent: " + memoryUsedPercent);
		
	}
}
