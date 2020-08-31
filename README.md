# RenegotiatingDebts

### Backgroung

A few years ago, I worked as an apprentice in a company with credit collection, calling late-payers to ask them about when they would provide payment. If the client provided a date, I would have to update the value of the debt and then divide it with the correspondent interest. All this process would take a long time because it was made using only a simple calculator and a spreadsheet and, most of the time, I would have hung up the call, make all the calculations and then call the client again. Therefore, to save time and have a more efficient way to renegotiate debts, I decided to create a computer program that will make all the calculations.

### Expectations

This program will:

1.	have a simple and easy understanding interface.
2.	decide the value of the interest based on the total value of the debt and the difference between the original date and the date the client wants to pay.
3.	offer the user a chance to decide the interest manually, for exceptional cases.
4.	calculate the updated value of the debt for payment in just one date.
5.	divide the payment into new installments, according to user input, with interest. 
6.	after each step, display a table with the old value, old date, new value, and interest value for each one of the installments first inputted by the user.
7.	show a message if the information inputted does not match the type of information required, such as entering letters, when only numbers are required. 
8.	save results in a xls file, if desired.
9.	save time, once the user will be able to calculate the debt with the client still on the phone.


### Development

This product is a Java program and its main function is to update the value of a debt for a specific date and, then, if needed, divide the debt into installments. Once the information about the debt is obtained, the following steps should be follow:
 
1.	Create an installment object for each installment;
2.	Calculate the total value of the debt;
3.	Find the oldest debt;
4.	If the user chooses “Manual” as the interest, set interest as the percentage inputted;
5.	If the user chooses “Automatic” as the interest, calculate interest percentage, using the total value of the debt and how old the debt is;
6.	Set the interest and new value for each installment;
7.	Fit the values into the company’s norms;
8.	If needed, divide the debt into new installments;
9.	Display a table with updated information for each installment
10.	Save the results into xls file.

### Recommendations for Further Development

For further development, I would address some of special cases of renegotiation of debts that my program currently cannot deal with. For example, some clients of the company preferred to pay a larger amount of money in the date for the first payment and then pay the remaining of the debt in the other dates. My program cannot give the adequate values for this renegotiation, since it can only divide the updated value of the debt into installments of same value. So, it would be needed to create new text fields or windows that can address these exceptional cases, which can be easily done by polymorphism.


Also, it would be great to link my program to the program the company uses to store the information about the debts of their clients. Linking both programs would save time because the user would only have to export the installments that the client wants to renegotiate, instead of typing. This would provide a greater integration within the company, since it will not be needed to open a new program to renegotiate the debt.


Another recommendation is to link the program with the company’s website. Sometimes clients would call the company just to check updated value of their debt, without the intention to pay it. Having the program in the company’s website would allow clients to make simulations on their own and only call the company when they want to pay.



