package learnRA;

public class StartWithSameChar
{
	public static void main(String[] args) 
	{
		String[] arr = {"ab","abb","abcd","abc","xyz"};
		String res = "";
		for(int i=0;i<arr[0].length();i++)
		{
			boolean flag = true;
			
			for(int j=1;j<arr.length;j++)
			{
				if(arr[0].charAt(i)!= arr[j].charAt(i))
				{
					flag = false;
				}
			}
			if(flag)
			{
				res = res +arr[0].charAt(i);
			}
		}
		
		
		System.out.println(res);
		
	}

}
