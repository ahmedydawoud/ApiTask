package MyPackage;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class CalculationController {
	
	@PersistenceContext 
	private EntityManager em;
	
	
	
	@POST
	@Path("/calc")
	public TheResult Calculate(Calculation c)
	{
		TheResult Result= new TheResult();
		try {
		Calculation newCalc = new Calculation();
		
		newCalc.setNumber1(c.getNumber1());
		newCalc.setNumber2(c.getNumber2());
		newCalc.setOperation(c.getOperation());
		em.persist(newCalc);
		 
		if(c.getOperation().equals("+"))
		{
			Result.setResult(c.getNumber1() + c.getNumber2());
		}
		else if(c.getOperation().equals("-"))
		{
			Result.setResult(c.getNumber1() - c.getNumber2());
		}
		else if(c.getOperation().equals("/"))
		{
			Result.setResult(c.getNumber1() / c.getNumber2());
		}
		else if(c.getOperation().equals("*"))
		{
			Result.setResult(c.getNumber1() * c.getNumber2());
		}
		
		return Result;
		
		}catch(Exception e)
		{
			throw new EJBException(e);
		}
	}
	
	@GET
	@Path("/calculations")
	public List<Calculation> getCalculations()
	{
		TypedQuery<Calculation> myQuery = em.createQuery("SELECT c FROM Calculation c",Calculation.class);
		List<Calculation> Calculations = myQuery.getResultList();
		
		return Calculations;
		
	}
}
