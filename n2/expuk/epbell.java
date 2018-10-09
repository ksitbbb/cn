import java.io.*;
import java.util.Scanner;

public class epbell
{
	public static void main(String[] args)
	{
		int in[][] = new int[10][10];
		Scanner scan = new Scanner(System.in);

		System.out.println("Enter the number of nodes: ");
		int n = scan.nextInt();

		System.out.println("Enter the node values:");
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				in[i][j] = scan.nextInt();
			}
		}

		System.out.println("ENter the source vertex:");
		int s = scan.nextInt();

		bellman();
	}

	static void bellman(int s,int n,int in[][])
	{
		int d[] = new int[100];
		for(int i=0;i<n;i++)
		{
			d[i] = in[s][i];
		}
		if(d[u]+in[u][v] < d[v])
		{
			d[v] = d[u]+in[u][v];
		}

	}
}
