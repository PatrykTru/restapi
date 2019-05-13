package guru.springframework.api.restapi;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RestTemplateExamples {
    public static final String API_ROOT = "https://api.predic8.de/shop";

    @Test
    public void getCategories() throws Exception
    {
        String apiUrl = API_ROOT + "/categories/";

        RestTemplate restTemplate = new RestTemplate();

        JsonNode jsonNode = restTemplate.getForObject(apiUrl,JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void getCustomers() throws Exception
    {
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        JsonNode jsonNode = restTemplate.getForObject(apiUrl,JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void createCustomer() throws Exception
    {
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        Map<String , Object> postMap = new HashMap<>();
        postMap.put("firstname" , "Joe");
        postMap.put("lastname" , "Buck");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl , postMap , JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());    }



    @Test
    public void updateCustomer() throws Exception
    {
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        Map<String , Object> postMap = new HashMap<>();
        postMap.put("firstname" , "Michael");
        postMap.put("lastname" , "Weston");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl , postMap , JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String customerUrl =jsonNode.get("customer_url").textValue();

        String id = customerUrl.split("/")[3];

        System.out.println("Created customer id = " + id);
        postMap.put("firstname" , "Michael 2");
        postMap.put("lastname" , "Weston 2");

        restTemplate.put(apiUrl + id,postMap);

        JsonNode updatedNode = restTemplate.getForObject(apiUrl + id , JsonNode.class);

        System.out.println(updatedNode.toString());

    }

    @Test(expected = HttpClientErrorException.class)
    public void deleteCustomer() throws Exception {

        //create customer to update
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Les");
        postMap.put("lastname", "Claypool");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();

        String id = customerUrl.split("/")[3];

        System.out.println("Created customer id: " + id);

        restTemplate.delete(apiUrl + id); //expects 200 status

        System.out.println("Customer deleted");

        //should go boom on 404
        restTemplate.getForObject(apiUrl + id, JsonNode.class);

    }

}
