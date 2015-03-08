# Word Count and Running Median 

This program does two things.

First, it takes in a set of text files from a directory and outputs the number of occurrences for each word.  For example, Word Count on a file containing the following passage:

> So call a big meeting,  
Get everyone out out,  
Make every Who holler,  
Make every Who shout shout.  

would return:

	a			1
	big			1  
	call		1  
	every		2  
	everyone	1  
	get			1  
	holler		1  
	make		2  
	meeting		1  
	out			2  
	shout		2  
	so			1  
	who			2  
	
Second, it keep track of the running median for the number of words per line of text.  For example, Word Count on a file containing the following passage:

> So call a big meeting,  
Get everyone out out,  
Make every Who holler,  
Make every Who shout shout.  

would return:

	5.0  
	4.5  
	4.0  
	4.5  
