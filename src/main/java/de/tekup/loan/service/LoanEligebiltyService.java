package de.tekup.loan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import de.tekup.loan.soap.ws.loaneligebilty.CustomerRequest;
import de.tekup.loan.soap.ws.loaneligebilty.ObjectFactory;
import de.tekup.loan.soap.ws.loaneligebilty.WsResponse;

@Service
public class LoanEligebiltyService {
	
	public WsResponse checkEligebilty(CustomerRequest customerRequest) {
		WsResponse response = new ObjectFactory().createWsResponse();
		
		List<String> criteriaMismatch = response.getCriteriaMismatch();
		
		if(!(customerRequest.getAge() >= 30 && customerRequest.getAge() <= 50)) {
			criteriaMismatch.add("Client age must be between 30 and 50.");
		}
		if( ! (customerRequest.getYearlyIncome() >= 25000)) {
			criteriaMismatch.add("Client must have an yearly income >= 25000.");
		}
		if( ! (customerRequest.getCibilScore() >= 500)) {
			criteriaMismatch.add("Client must have a cibilScore >= 500.");
		}
		
		if(criteriaMismatch.isEmpty()) {
			response.setIsEligeble(true);
			long amount = (long)((60 - customerRequest.getAge()) * customerRequest.getYearlyIncome() * 0.4);
			response.setApprovedAmount(amount);
		}else {
			response.setIsEligeble(false);
			response.setApprovedAmount(0);
		}
		
		return response;
	}

}
