package rkandula.tutorials;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    static CloseableHttpClient httpClient;

    public static void main( String[] args ) throws IOException, URISyntaxException {
        App a = new App();

        a.initHttpClient();

        // perform a simple get
        String studentResp = a.performGet("http://localhost:8000/student.json");
        List<Student> students = Student.parseFromJson(studentResp);
        for (Student s : students) {
            System.out.println(s);
        }

        // send some request params to get
        URIBuilder uriBuilder = new URIBuilder("https://httpbin.org/get");
        uriBuilder.addParameter("name", "rak kumar");
        uriBuilder.addParameter("email", "someone@gmail.com");
        uriBuilder.addParameter("age", "36");

        a.performGet(uriBuilder.build());

        // post - see the method
        a.performPost();

        a.shutdownHttpClient();
    }

    private String performGet(URI uri) throws IOException {
        HttpGet getReq = new HttpGet(uri);
        return sendReq(getReq);
    }

    private String performGet(String url) throws IOException {
        HttpGet getReq = new HttpGet(url);
        return sendReq(getReq);
    }

    private void performPost() throws IOException {
        StringEntity entity = new StringEntity(Student.dummyStudentAsJson(), ContentType.APPLICATION_JSON);

        HttpPost postReq = new HttpPost("https://httpbin.org/post");
        postReq.setEntity(entity);

        sendReq(postReq);
    }

    private String sendReq(HttpUriRequest req) throws IOException {
        try(CloseableHttpResponse response = httpClient.execute(req)) {
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String responseStr = EntityUtils.toString(entity);
                System.out.println("Response from " + req.getURI().getPath() + " " + req.getURI().getQuery());
                System.out.println(responseStr);
                return responseStr;
            }
        }
        return null;
    }

    private void initHttpClient() {
        if (App.httpClient == null) {
            App.httpClient = HttpClients.createDefault();
            System.out.println("Created http client");
        }
    }

    private void shutdownHttpClient() {
        if(App.httpClient != null) {
            try {
                App.httpClient.close();
            } catch (Exception e) {

            } finally {
                App.httpClient = null;
            }
        }
    }
}
