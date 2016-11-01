package com.brezende.testgoeuro.controller;

import com.brezende.testgoeuro.model.DirectRoute;
import com.brezende.testgoeuro.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path = "/api")
public class DirectRouteController {

	@Autowired
	private RouteService service;

	@RequestMapping(
		method = GET,
		path = "/direct"
	)
	public DirectRoute hasDirectRoute(
		@RequestParam(value="dep_sid") Integer depSid,
		@RequestParam(value="arr_sid") Integer arrSid
	) {
			return new DirectRoute(
				depSid,
				arrSid,
				service.directRoute(depSid, arrSid)
			);
	}
}
