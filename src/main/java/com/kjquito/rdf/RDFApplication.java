package com.kjquito.rdf;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

public class RDFApplication {

	public static void main(String[] args) {
		consulta1();
		// consulta2();
		// consulta3();
		// consulta4();
		// consulta5();

	}

	public static void consulta1() {
		String urlDB = "jdbc:virtuoso://localhost:1111";
		VirtGraph set = new VirtGraph(urlDB, "dba", "dba");
		String queryString = "PREFIX newOnto:<http://utpl.edu.ec/lod/dataCOVID/ontology/> "
				+ "PREFIX gn:<http://www.geonames.org/ontology#> "
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " + "PREFIX schema:<http://schema.org/>"
				+ "PREFIX dbo: <http://dbpedia.org/ontology/>" + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "SELECT ?cantidad ?nombre ?fecha FROM <http://localhost:8890/covidAfrica> WHERE {?var rdf:type newOnto:Confirmed_Cases ;"
				+ " newOnto:totalQuantity ?cantidad ;" + " gn:locatedIn ?pais;" + " schema:observationDate ?fecha."
				+ " ?pais dbo:name ?nombre." + " FILTER (?fecha = \"2020-06-15\")"
				+ "}ORDER BY DESC (xsd:integer(?cantidad))";

		Query query = QueryFactory.create(queryString);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(query, set);

		String message = "";
		JsonArray array = new JsonArray();

		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				JsonObject json = new JsonObject();
				QuerySolution soln = results.nextSolution();
				RDFNode cantidad = soln.get("cantidad");
				RDFNode nombre = soln.get("nombre");
				RDFNode fecha = soln.get("fecha");
				json.put("cantidad", String.valueOf(cantidad));
				json.put("nombre", String.valueOf(nombre));
				json.put("fecha", String.valueOf(fecha));
				array.add(json);
				message = array.toString();
			}
			System.out.println(message);
		} finally {
			vqe.close();
		}
	}

	public static void consulta2() {
		String[] args = {};
		String url;
		if (args.length == 0) {
			url = "jdbc:virtuoso://localhost:1111";
		} else {
			url = args[0];
		}

		VirtGraph set = new VirtGraph(url, "dba", "dba");
		String queryString = "PREFIX sio:<http://semanticscience.org/resource/>"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> " + "SELECT (sum(if($gender=\"male\",1,0)) as ?hombres)"
				+ "(sum(if($gender=\"female\",1,0)) as ?mujeres) FROM <http://localhost:8890/covidAfrica>" + "{"
				+ "	{SELECT DISTINCT ?person $gender WHERE {" + "		?person rdf:type sio:Patient;"
				+ "		foaf:gender $gender" + "	}GROUP BY ?person $gender }" + "}";

		Query query = QueryFactory.create(queryString);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(query, set);

		String message = "";
		JsonArray array = new JsonArray();
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				JsonObject json = new JsonObject();
				QuerySolution soln = results.nextSolution();
				RDFNode hombres = soln.get("hombres");
				RDFNode mujeres = soln.get("mujeres");
				String hombresR = hombres.toString().replaceAll("[\\^]", "-");
				String mujeresR = mujeres.toString().replaceAll("[\\^]", "-");
				String[] parts = hombresR.split("-");
				String[] parts1 = mujeresR.split("-");
				json.put("hombres", parts[0]);
				json.put("mujeres", parts1[0]);
				array.add(json);
				message = array.toString();
			}
			System.out.println(message);

		} finally {
			vqe.close();
		}
	}

	public static void consulta3() {
		String[] args = {};
		String url;
		if (args.length == 0) {
			url = "jdbc:virtuoso://localhost:1111";
		} else {
			url = args[0];
		}

		VirtGraph set = new VirtGraph(url, "dba", "dba");
		String queryString = "PREFIX newOnto:<http://utpl.edu.ec/lod/dataCOVID/ontology/>"
				+ "PREFIX gn:<http://www.geonames.org/ontology#>" + "PREFIX schema:<http://schema.org/>"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX dbo: <http://dbpedia.org/ontology/>" + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "SELECT ?cantidad ?nombre FROM <http://localhost:8890/covidAfrica> WHERE {"
				+ " ?var rdf:type newOnto:Deaths_Cases ;" + " newOnto:totalQuantity ?cantidad ;"
				+ " gn:locatedIn ?pais;" + " schema:observationDate ?fecha." + " ?pais dbo:name ?nombre."
				+ " FILTER (?fecha = \"2020-06-15\")" + "}ORDER BY DESC (xsd:integer(?cantidad))";

		Query query = QueryFactory.create(queryString);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(query, set);

		String message = "";
		JsonArray array = new JsonArray();
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				JsonObject json = new JsonObject();
				QuerySolution soln = results.nextSolution();
				RDFNode cantidad = soln.get("cantidad");
				RDFNode nombre = soln.get("nombre");
				json.put("catidad", String.valueOf(cantidad));
				json.put("nombre", String.valueOf(nombre));
				array.add(json);
				message = array.toString();
			}
			System.out.println(message);

		} finally {
			vqe.close();
		}
	}

	public static void consulta4() {
		String[] args = {};
		String url;
		if (args.length == 0) {
			url = "jdbc:virtuoso://localhost:1111";
		} else {
			url = args[0];
		}

		VirtGraph set = new VirtGraph(url, "dba", "dba");
		String queryString = "PREFIX newOnto:<http://utpl.edu.ec/lod/dataCOVID/ontology/>"
				+ "PREFIX gn:<http://www.geonames.org/ontology#>" + "PREFIX schema:<http://schema.org/>"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "SELECT ?case ?location FROM <http://localhost:8890/covidAfrica> WHERE {"
				+ " ?case rdf:type newOnto:CaseCovid;" + " newOnto:hasData ?paciente."
				+ " ?paciente newOnto:hasData ?informacion."
				+ " ?informacion newOnto:travel_history_location ?location." + "}";
		Query query = QueryFactory.create(queryString);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(query, set);
		String message = "";
		JsonArray array = new JsonArray();
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				JsonObject json = new JsonObject();
				QuerySolution soln = results.nextSolution();
				RDFNode cases = soln.get("case");
				RDFNode location = soln.get("location");
				String[] parts = cases.toString().split("/");
				json.put("case", String.valueOf(parts[5]));
				json.put("location", String.valueOf(location));
				array.add(json);
				message = array.toString();
			}
			System.out.println(message);

		} finally {
			vqe.close();
		}
	}

	public static void consulta5() {
		String[] args = {};
		String url;
		if (args.length == 0) {
			url = "jdbc:virtuoso://localhost:1111";
		} else {
			url = args[0];
		}

		VirtGraph set = new VirtGraph(url, "dba", "dba");
		String queryString = "PREFIX newOnto:<http://utpl.edu.ec/lod/dataCOVID/ontology/>"
				+ "PREFIX gn:<http://www.geonames.org/ontology#>" + "PREFIX schema:<http://schema.org/>"
				+ "PREFIX dbo: <http://dbpedia.org/ontology/>"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "SELECT ?nombre ?latitud ?longitud ?cantidad ?poblacion ?fecha FROM <http://localhost:8890/covidAfrica> WHERE {"
				+ " ?var rdf:type newOnto:Confirmed_Cases ;" + " newOnto:totalQuantity ?cantidad ;"
				+ " gn:locatedIn ?pais;" + " schema:observationDate ?fecha." + " ?pais dbo:name ?nombre;"
				+ " schema:latitude ?latitud;" + " schema:longitude ?longitud;" + " dbo:populationTotal ?poblacion."
				+ " FILTER (?fecha = \"2020-06-15\")" + "}ORDER BY DESC (xsd:integer(?cantidad))";
		Query query = QueryFactory.create(queryString);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(query, set);
		String message = "";
		JsonArray array = new JsonArray();
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				JsonObject json = new JsonObject();
				QuerySolution soln = results.nextSolution();
				RDFNode nombre = soln.get("nombre");
				RDFNode latitud = soln.get("latitud");
				RDFNode longitud = soln.get("longitud");
				RDFNode cantidad = soln.get("cantidad");
				RDFNode poblacion = soln.get("poblacion");
				RDFNode fecha = soln.get("fecha");
				json.put("nombre", String.valueOf(nombre));
				json.put("latitud", String.valueOf(latitud));
				json.put("longitud", String.valueOf(longitud));
				json.put("cantidad", Integer.parseInt(String.valueOf(cantidad)));
				json.put("poblacion", Integer.parseInt(String.valueOf(poblacion)));
				json.put("fecha", String.valueOf(fecha));
				array.add(json);
				message = array.toString();
			}
			System.out.println(message);

		} finally {
			vqe.close();
		}
	}
}
