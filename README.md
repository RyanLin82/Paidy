# Paidy questions
**This is the Intellij, gradle, java project**

There are four questions that will be solved in this repository. It's simple but contains test solutions.  
Most functions can also have some flexibilities in the setting. However, It doesn't use Spring framework.  
But it can also be revised to the spring framework version easily.


## Question1 ordinal numbers
Provide the function can transfrom given input string to the ordinal numbers.  
Examples: 1 => 1st, 2 => 2nd

## Question2 Count the number of Sundays
Count the number of Sundays between two given dates.  
Example: (‘01-05-2021’, ‘30-05-2021’) => 5  
  
According to the requirements, the default week of the day is Sunday. This can be changed by the class constructer.  
If you create the class with integer 1, then it can count the week of Mondays in the two dates.

## Question3,4 Mask personal information
It includes two masks here. The method can mask the given input.  

Email mask:  
Email combines two parts, one is local-part; another is domain-part. It will keep the first and last characters in the local-part.  
Between first and last characters will be masked by (*). All email input will be trimmed and parsed to lowercase first.  
If the local-part length is less than 3, then all characters will be masked.  
  
Examples:  
case 1: Local-parT@domain-name.com => l*****t@domain-name.com.  
case 2: AA@domain-name.com => **@domain-name.com  
case 3: ABC@domain-name.com => A*B@domain-name.com  
case 4: A@domain-name.com => *@domain-name.com  
  
'*' can be customized by the class constructer.  

Phone number mask:  
Phone number contains at least 9 digits (0-9) and may contain ' ', '+' where ‘+’ is only accepted when is the first character.  
The spaces between the numbers will be changed to '-'. Two spaces (' ') are not allowed.  
The last four digits will be kept, others will be changed to '*'.  
    
Example:  
case1: +44 123 456 789 => +-*-*6-789  
case2: 44 123 456 789 => --**6-789  
case3: 44 123 456 789 => x becasue there are two spaces between "123" and "456".  
  
'-', '*' and the number of keeping digits can be customized by the class constructer.  
