package jphollanti.streaming.test;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

public class StreamingClient {
    public static void main(String[] args) throws IOException {
        long iterations = 10000000;
        int sleep = 1000;
        int modulo = 1000;
        HttpGet method = new HttpGet("http://localhost:8080/StreamingTest/streaming"
            + "?iterations=" + iterations
            + "&sleep=" + sleep
            + "&modulo=" + modulo);

        HttpClient client = new DefaultHttpClient();
        HttpResponse httpResponse = client.execute(method);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            InputStream is = httpResponse.getEntity().getContent();
            ObjectMapper mapper = new ObjectMapper();
            JsonFactory jf = new JsonFactory();
            JsonParser parser = jf.createParser(is);

            StreamHeader header = mapper.readValue(parser, StreamHeader.class);
            System.out.println(header);

            MappingIterator<StreamData> it = mapper.readValues(parser, StreamData.class);
            while (it.hasNext()) {
                StreamData streamData = it.next();
                System.out.println(streamData);
            }
        }
    }
}
