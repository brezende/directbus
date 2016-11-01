package com.brezende.testgoeuro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;

@Service
public class RouteService {

	private static Logger LOGGER = LoggerFactory.getLogger(RouteService.class);

	private Map<Integer, Set<Integer>> directRoutes;

	public RouteService(
		@Value("${data_file}") String routesFilePath
	) throws IOException {
		LOGGER.info("Parsing routes data file \"{}\"", routesFilePath);
		this.directRoutes = parseRoutesFile(routesFilePath);
		LOGGER.info("Done parsing");
	}

	private Map<Integer, Set<Integer>> parseRoutesFile(String routesFilePath) throws IOException {
		Map<Integer, Set<Integer>> directRoutes = new HashMap<>();
		try (Stream<String> lines = lines(get(routesFilePath))) {
			lines
				.forEach(line -> {
					String trimmed = line.trim();
					if (!trimmed.isEmpty()) {
						String[] integers = trimmed.split(" ");
						proccessRoute(directRoutes, integers);
					}
				})
			;
		}
		return directRoutes;
	}

	private void proccessRoute(
		Map<Integer, Set<Integer>> directRoutes,
		String[] integers
	) {
		for (int i=1; i<integers.length; i++) {
			for (int j=i+1; j<integers.length; j++) {
				int min = Integer.parseInt(integers[i]);
				int max = Integer.parseInt(integers[j]);
				if (min == max) continue;
				if (min > max) {
					int aux = min;
					min = max;
					max = aux;
				}
				getStationSet(directRoutes, min).add(max);
			}
		}
	}

	private Set<Integer> getStationSet(Map<Integer, Set<Integer>> directRoutes, int sid) {
		Set<Integer> stations = directRoutes.get(sid);
		if (stations == null) {
			stations = new HashSet<>();
			directRoutes.put(sid, stations);
		}
		return stations;
	}

	public boolean directRoute(int departure, int arrival) {
		if (departure == arrival) return directRoutes.containsKey(departure);
		int min = departure, max = arrival;
		if (min > max) {
			int aux = min;
			min = max;
			max = aux;
		}
		return directRoutes.get(min).contains(max);
	}
}
