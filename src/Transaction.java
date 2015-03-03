import java.util.Date;


public interface Transaction 
{
	public double getBalance();
	public String getType();
	public Date getDate();
	public double getAmount();	
}
