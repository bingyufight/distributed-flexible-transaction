package com.distributed.transaction.api;

import java.io.Serializable;

import com.distributed.transaction.common.Duplicatedable;

/**
 * 
 * @author yubing
 *
 */
public  class ParticipantId implements Duplicatedable<ParticipantId>,Serializable {

	private static final long serialVersionUID = -1050878993938877484L;

	private String ipHost;
	
	private int port;
	
	private String participantUUID;
	
	private  String globalParticipantUUID;
	
	
	public ParticipantId(){
		
	}
	public ParticipantId(String ipHost,int port,String participantUUid){
		this.ipHost = ipHost;
		this.port = port;
		this.participantUUID = participantUUid;
		globalParticipantUUID = ipHost +":" + port + "_"  + participantUUid;
		
	}
	
	public ParticipantId(ParticipantId copyInstance){
	        this(copyInstance.ipHost,copyInstance.port,copyInstance.participantUUID);
	}
	
	
	
	
	 public String getGlobalParticipantUUID() {
		return globalParticipantUUID;
	}

	@Override
	 public ParticipantId clone() { 
		 return new ParticipantId(this); 
	 }
}
