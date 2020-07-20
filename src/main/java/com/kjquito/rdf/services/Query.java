package com.kjquito.rdf.services;

import java.util.ArrayList;
import java.util.List;
import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;
import com.kjquito.rdf.entity.*;

@Service
public class Query {
	@Value("${spring.datasource.url}")
	private String endpointUrl;

	public List<Results1> consulta1() {

		VirtGraph set = new VirtGraph(endpointUrl, "dba", "dba");
		String queryString = "PREFIX newOnto:<http://utpl.edu.ec/lod/dataCOVID/ontology/> "
				+ "PREFIX gn:<http://www.geonames.org/ontology#> "
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " + "PREFIX schema:<http://schema.org/>"
				+ "PREFIX dbo: <http://dbpedia.org/ontology/>" + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "SELECT ?cantidad ?nombre ?fecha FROM <http://localhost:8890/covidAfrica> WHERE {?var rdf:type newOnto:Confirmed_Cases ;"
				+ " newOnto:totalQuantity ?cantidad ;" + " gn:locatedIn ?pais;" + " schema:observationDate ?fecha."
				+ " ?pais dbo:name ?nombre." + " FILTER (?fecha = \"2020-06-15\")"
				+ "}ORDER BY DESC (xsd:integer(?cantidad))";

		org.apache.jena.query.Query query = QueryFactory.create(queryString);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(query, set);

		String message = "";
		JsonArray array = new JsonArray();
		List<Results1> lista = new ArrayList<Results1>();
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				JsonObject json = new JsonObject();
				QuerySolution soln = results.nextSolution();
				RDFNode cantidad = soln.get("cantidad");
				RDFNode nombre = soln.get("nombre");
				RDFNode fecha = soln.get("fecha");
				Results1 objResults1 = new Results1(Integer.parseInt(cantidad.toString()), nombre.toString(),
						fecha.toString());
				lista.add(objResults1);

			}
		} finally {
			vqe.close();
		}
		return lista;
	}

	public List<Results2> consulta2() {

		VirtGraph set = new VirtGraph(endpointUrl, "dba", "dba");
		String queryString = "PREFIX sio:<http://semanticscience.org/resource/>"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> " + "SELECT (sum(if($gender=\"male\",1,0)) as ?hombres)"
				+ "(sum(if($gender=\"female\",1,0)) as ?mujeres) FROM <http://localhost:8890/covidAfrica>" + "{"
				+ "	{SELECT DISTINCT ?person $gender WHERE {" + "		?person rdf:type sio:Patient;"
				+ "		foaf:gender $gender" + "	}GROUP BY ?person $gender }" + "}";

		org.apache.jena.query.Query query = QueryFactory.create(queryString);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(query, set);

		String message = "";
		JsonArray array = new JsonArray();
		List<Results2> lista = new ArrayList<Results2>();
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
				Results2 objResults1 = new Results2(parts[0], parts1[0]);
				lista.add(objResults1);
			}

		} finally {
			vqe.close();
		}

		return lista;

	}

	public List<Results3> consulta3() {

		VirtGraph set = new VirtGraph(endpointUrl, "dba", "dba");
		String queryString = "PREFIX newOnto:<http://utpl.edu.ec/lod/dataCOVID/ontology/>"
				+ "PREFIX gn:<http://www.geonames.org/ontology#>" + "PREFIX schema:<http://schema.org/>"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX dbo: <http://dbpedia.org/ontology/>" + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "SELECT ?cantidad ?nombre FROM <http://localhost:8890/covidAfrica> WHERE {"
				+ " ?var rdf:type newOnto:Deaths_Cases ;" + " newOnto:totalQuantity ?cantidad ;"
				+ " gn:locatedIn ?pais;" + " schema:observationDate ?fecha." + " ?pais dbo:name ?nombre."
				+ " FILTER (?fecha = \"2020-06-15\")" + "}ORDER BY DESC (xsd:integer(?cantidad))";

		org.apache.jena.query.Query query = QueryFactory.create(queryString);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(query, set);

		String message = "";
		JsonArray array = new JsonArray();
		List<Results3> lista = new ArrayList<Results3>();
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				JsonObject json = new JsonObject();
				QuerySolution soln = results.nextSolution();
				RDFNode cantidad = soln.get("cantidad");
				RDFNode nombre = soln.get("nombre");
				Results3 objResults1 = new Results3(Integer.parseInt(cantidad.toString()), nombre.toString());
				lista.add(objResults1);
			}

		} finally {
			vqe.close();
		}
		return lista;

	}

	public List<Results4> consulta4() {

		VirtGraph set = new VirtGraph(endpointUrl, "dba", "dba");
		String queryString = "PREFIX newOnto:<http://utpl.edu.ec/lod/dataCOVID/ontology/>"
				+ "PREFIX gn:<http://www.geonames.org/ontology#>" + "PREFIX schema:<http://schema.org/>"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "SELECT ?case ?location FROM <http://localhost:8890/covidAfrica> WHERE {"
				+ " ?case rdf:type newOnto:CaseCovid;" + " newOnto:hasData ?paciente."
				+ " ?paciente newOnto:hasData ?informacion."
				+ " ?informacion newOnto:travel_history_location ?location." + "}";

		org.apache.jena.query.Query query = QueryFactory.create(queryString);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(query, set);

		String message = "";
		JsonArray array = new JsonArray();
		List<Results4> lista = new ArrayList<Results4>();
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				JsonObject json = new JsonObject();
				QuerySolution soln = results.nextSolution();
				RDFNode cases = soln.get("case");
				RDFNode location = soln.get("location");
				String[] parts = cases.toString().split("/");
				Results4 objResults1 = new Results4(parts[5], location.toString());
				lista.add(objResults1);

			}

		} finally {
			vqe.close();
		}
		return lista;

	}

	public List<Results5> consulta5() {
		VirtGraph set = new VirtGraph(endpointUrl, "dba", "dba");
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

		org.apache.jena.query.Query query = QueryFactory.create(queryString);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(query, set);

		String message = "";
		JsonArray array = new JsonArray();
		List<Results5> lista = new ArrayList<Results5>();
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
				Results5 objResults1 = new Results5(nombre.toString(), Double.parseDouble(latitud.toString()),Double.parseDouble(longitud.toString()), Integer.parseInt(cantidad.toString()) ,Integer.parseInt(poblacion.toString()), fecha.toString());
				lista.add(objResults1);
			}

		} finally {
			vqe.close();
		}
		return lista;
	}

}
