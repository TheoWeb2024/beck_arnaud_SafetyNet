package com.rescueService.safetyNets.model;


import lombok.Data;


@Data
public class Firestation {
	


	private int id;

    private String address;

    private int stationNumber;
    
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}
      
}
