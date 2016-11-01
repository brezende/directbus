package com.brezende.testgoeuro

import com.brezende.testgoeuro.service.RouteService
import spock.lang.Specification

import static java.io.File.*

class RouteServiceSpec extends Specification {

	def "Error creating an account - unexpected param"() {
		given:
		def dataFile = createTempFile("challenge", ".data")
		dataFile.deleteOnExit()
		dataFile << """
		3
		0 0 1 2 3 4
		1 3 1 6 5
		2 0 6 4
		"""
		def service = new RouteService(dataFile.canonicalPath)

		expect:
		service.directRoute(departure, arrival) == hasDirectRoute

		where:
		departure | arrival | hasDirectRoute
		1         | 2       | true
		2         | 6       | false
		2         | 2       | true
		8         | 8       | false
		6         | 3       | true
		4         | 6       | true
	}
}
