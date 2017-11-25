package br.com.portalCliente.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum ProposalStatus {

	PROPOSAL(1),
	ACTIVE(2),
	ENDORSED(3),
	CANCELLED(4),
	EXPIRED(5),
	PROPOSAL_WITH_MOVEMENTS(6);

	private int value;

	private ProposalStatus(int value){
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	public static ProposalStatus fromValue(Integer value){
		
		if(value == null){
			return null;
		}
		
		ProposalStatus result = null;
		for(ProposalStatus p : ProposalStatus.values()){
			if(p.value == value){				
				result = p;
				break;
			}
		}
		return result;		
	}
	
	public static List<Integer> getValues(List<ProposalStatus> policyStatus){
		
		List<Integer> result = new ArrayList<Integer>();
		
		for(ProposalStatus p : policyStatus){
			result.add(p.getValue());
		}
		
		return result;		
	}
	
	public static List<Integer> getStatusValues(ProposalStatus... policyStatus){
		
		List<Integer> statusValues = new ArrayList<Integer>(); 
		
		for (ProposalStatus p : policyStatus) {
			statusValues.add(p.getValue());
		}
		
		return statusValues;
	}
	
}
