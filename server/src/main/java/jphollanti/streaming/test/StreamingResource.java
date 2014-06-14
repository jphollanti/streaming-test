package jphollanti.streaming.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;

@Path("/streaming")
public class StreamingResource {

    private StreamHeader getHeader(String valueLabel) {
        StreamHeader streamHeader = new StreamHeader();
        streamHeader.setValueLabel(valueLabel);
        return streamHeader;
    }

    private StreamData getData(String id) {
        StreamData streamData = new StreamData();
        streamData.setId(id);
        streamData.setValue(3f);
        return streamData;
    }

    @GET
    @Produces("application/json")
    public Response streamKpi(
            @QueryParam("iterations") final long iterations,
            @QueryParam("sleep") final long sleep,
            @QueryParam("modulo") final long modulo) {
        final StreamingOutput out = new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                ObjectMapper mapper = new ObjectMapper();
                Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                writer.write(mapper.writeValueAsString(getHeader("duration")));
                for (int i=0; i<iterations; i++) {
                    writer.write(mapper.writeValueAsString(getData("stream-bit-" + i)));

                    if (i % modulo == 0) {
                        try {
                            Thread.sleep(sleep);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                writer.flush();
            }
        };
        return Response.ok(out).build();
    }
}
