import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account implements Comparable<Account>, Serializable
{
	private static final long serialVersionUID = 1L;
	private String id;
	private double balance;
	private static double annualInterestRate;
	private java.util.Date dateCreated;
	
	private List<Transaction> accountTransactionList = new ArrayList<Transaction>();
	
	public Account()
	{
		dateCreated = new java.util.Date();
	}
	
	public List<Transaction> getTransactionList()
	{
		List<Transaction> theCopy = new ArrayList<Transaction>();
		theCopy.addAll(accountTransactionList);
		return theCopy;
	}
	
	public Account(String newId, double newBalance)
	{
		id = newId;
		balance = newBalance;
		dateCreated = new java.util.Date();
	}
	
	public String toString()
	{
		DecimalFormat formatter = new DecimalFormat("$#,###,###.00");
		return "Account ID: " + id + ", Balance = " + formatter.format(balance); 
	}
	
	@Override
	public int compareTo(Account otherAccount)
	{
		if(this.balance < otherAccount.balance)
		{
			return -1;
		}
		else if(this.balance > otherAccount.balance)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	public boolean equals(Account account)
	{
		return id == account.id && balance == account.balance;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public double getBalance()
	{
		return balance;
	}
	
	public static double getAnnualInterestRate()
	{
		return annualInterestRate;
	}
	
	public void setId(String newId)
	{
		id = newId;
	}
	
	public void setBalance(double newBalance)
	{
		balance = newBalance;
	}
	
	public static void setAnnualInterestRate(double newAnnualInterestRate)
	{
		annualInterestRate = newAnnualInterestRate;
	}
	
	public double getMonthlyInterest()
	{
		return balance * (annualInterestRate / 1200);
	}
	
	public java.util.Date getDateCreated()
	{
		return dateCreated;
	}
	
	public void withdraw(double amount)
	{
		balance -= amount;
		accountTransactionList.add(new TransactionObject("Withdraw", amount, balance));
	}
	
	public void deposit(double amount)
	{
		balance += amount;
		accountTransactionList.add(new TransactionObject("Deposit", amount, balance));
	}
	
	class TransactionObject implements Transaction, Serializable
	{
		private static final long serialVersionUID = 1L;
		private Date dateOfTransaction;
		private String type;
		private double amount, balance;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM d, yyyy");
		
		TransactionObject(String type, double amount, double balance)
		{
			this.type = type;
			this.amount = amount;
			this.balance = balance;
			dateOfTransaction = new Date();
		}
		
		public String toString()
		{
			return "Transaction Date: " + dateFormat.format(dateOfTransaction) +
				   "\n			Type: " + type + 
				   "\n			Amount: " + amount +
				   "\n		Remaining Balance: " + balance;
		}
		
		public double getAmount()
		{
			return amount;
		}
		
		public double getBalance()
		{
			return balance;
		}
		
		public Date getDate()
		{
			return dateOfTransaction;
		}
		
		public String getType()
		{
			return type;
		}
	}
}