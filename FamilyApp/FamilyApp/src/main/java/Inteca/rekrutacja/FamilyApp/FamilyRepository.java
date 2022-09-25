package Inteca.rekrutacja.FamilyApp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.view.RedirectView;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static Inteca.rekrutacja.FamilyApp.FamilyAppApplication.getJson;

@Repository
public class FamilyRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while((cp = rd.read()) != -1)
            sb.append((char) cp);
        return sb.toString();
    }

    public List<Family> getAll() {
        return jdbcTemplate.query("Select id, familyName, nrOfInfants, nrOfChildren, nrOfAdults from Family",
                BeanPropertyRowMapper.newInstance(Family.class));
    }

    public Family getFamily(int id) throws IOException, JSONException {
        Family family = jdbcTemplate.queryForObject("Select id, familyName, nrOfInfants, nrOfChildren, nrOfAdults from Family "+"WHERE id = ?",
                BeanPropertyRowMapper.newInstance(Family.class), id);
        URL url = new URL("http://localhost:8081/familyMembers/getFamily/"+id);
        System.out.println(family);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = getJson(url);
        System.out.println(json);
        String sJson = json.toString();
        System.out.println(sJson);

        Gson gson = new Gson();
        Type FamilyMemberListType = new TypeToken<List<FamilyMember>>(){}.getType();
        List<FamilyMember> familyMembers = gson.fromJson(sJson, FamilyMemberListType);

        family.setMembers(familyMembers);
        System.out.println(family);

        return family;
    }


    //Tworzenie Rodzin
    public String createFamily(Family family) throws IOException, InterruptedException {
        System.out.println(family);
        RedirectView rv = new RedirectView();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "Insert into Family (familyName, nrOfAdults, nrOfChildren, nrOfInfants) VALUES (?,?,?,?)";
        AtomicInteger adultsCounter = new AtomicInteger();
        AtomicInteger childrenCounter = new AtomicInteger();
        AtomicInteger infantsCounter = new AtomicInteger();

        family.getMembers().forEach(member -> {
                    if(member.getAge()>=16)
                        adultsCounter.getAndIncrement();
                    else if(member.getAge()<4)
                        infantsCounter.getAndIncrement();
                    else
                        childrenCounter.getAndIncrement();
                });
        if(family.getNrOfAdults() == adultsCounter.intValue() && family.getNrOfChildren() == childrenCounter.intValue() && family.getNrOfInfants() == infantsCounter.intValue()) {
            System.out.println("Zgadza sie");
            System.out.println("Adults: " + adultsCounter + " / Children: " + childrenCounter + " / Infants: " + infantsCounter);

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[] { "ID" });
                ps.setString(1, family.getFamilyName());
                ps.setInt(2, family.getNrOfAdults());
                ps.setInt(3, family.getNrOfChildren());
                ps.setInt(4, family.getNrOfInfants());
                return ps;
            }, keyHolder);


            String postEndpoint =  "http://localhost:8081/familyMembers/createFamilyMembers";

            family.getMembers().forEach(member -> {
                    String inputJson = "{ \"familyId\": "+keyHolder.getKey().toString()+", \"familyName\": \""+family.getFamilyName()+"\", \"givenName\": \""+member.getGivenName()+"\", \"age\": "+member.getAge()+" }";
                    System.out.println(inputJson);
                    var request = HttpRequest.newBuilder()
                        .uri(URI.create(postEndpoint))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                        .build();
                var client = HttpClient.newHttpClient();

                HttpResponse<String> response = null;
                try {
                    response = client.send(request, HttpResponse.BodyHandlers.ofString());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(response.statusCode());
                System.out.println(response.body());
            });

            //model.addAttribute("family",family);
            //rv.setUrl("http://localhost:8081/familyMembers/createFamilyMembers");
            //return rv;
            //return new RedirectView("http://localhost:8080/families/");
        }
        else {
            String msg = "Blad! Liczba czlonkow rodziny jest niepoprawna! \n"+
            "Adults: " + adultsCounter + " ("+family.getNrOfAdults()+") / Children: " + childrenCounter + " ("+family.getNrOfChildren()+") / Infants: " + infantsCounter + " ("+family.getNrOfInfants()+")";
            System.out.println(msg);
            return msg;
        }

        return "Dodano rodzine: "+family.getFamilyName()+" o id: "+keyHolder.getKey().intValue();
    }
}
