package learnRA;

import java.util.Arrays;

public class ReversePrinting 
{
	public static void main(String[] args)
	{
		String src = "i am from mandya";
		String[] arr = src.split(" ");
		
		
		for(int i=0;i<arr.length;i++)
		{
			System.out.println(Arrays.toString(arr));
			
			String last = arr[0];
			
			for(int j=0;j<arr.length-1;j++)
			{
				arr[j] = arr[j+1];
			}
			
			arr[arr.length-1] = last;
			
		}
	}

}
