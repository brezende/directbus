package com.brezende.testgoeuro.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DirectRoute {

	@JsonProperty("dep_sid")
	private Integer departureStation;

	@JsonProperty("arr_sid")
	private Integer arrivalStation;

	@JsonProperty("direct_bus_route")
	private Boolean hasDirectRoute;

	public DirectRoute() {
		super();
	}

	public DirectRoute(
		Integer departureStation,
		Integer arrivalStation,
		Boolean hasDirectRoute
	) {
		setDepartureStation(departureStation);
		setArrivalStation(arrivalStation);
		setHasDirectRoute(hasDirectRoute);
	}

	public Integer getDepartureStation() {
		return departureStation;
	}

	public void setDepartureStation(Integer departureStation) {
		this.departureStation = departureStation;
	}

	public Integer getArrivalStation() {
		return arrivalStation;
	}

	public void setArrivalStation(Integer arrivalStation) {
		this.arrivalStation = arrivalStation;
	}

	public Boolean getHasDirectRoute() {
		return hasDirectRoute;
	}

	public void setHasDirectRoute(Boolean hasDirectRoute) {
		this.hasDirectRoute = hasDirectRoute;
	}
}
