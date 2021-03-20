package ru.vershin.soap2rest.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tempuri.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class Soap2Rest extends RouteBuilder {

    @Value("${endpoint.namespace}")
    private String ENDPOINT_NAMESPACE;
    private static final String ENDPOINT_URI="cxf:bean:cxfConvertTemp";

    private static void process(Exchange exchange) {
        List<Object> objectList = new ArrayList<>();
        objectList.add(Integer.parseInt(exchange.getIn().getHeader("num1").toString()));
        objectList.add(Integer.parseInt(exchange.getIn().getHeader("num2").toString()));
        exchange.getOut().setBody(objectList);
    }

    @Override
    public void configure() throws Exception {
        from("direct:additions")
                .removeHeaders("CamelHttp*")
                .process(Soap2Rest::process)
                .setHeader(CxfConstants.OPERATION_NAME, constant("{{endpoint.operation.add}}"))
                .setHeader(CxfConstants.OPERATION_NAMESPACE, constant(ENDPOINT_NAMESPACE))
                .to(ENDPOINT_URI)
                .process(exchange -> {
                    MessageContentsList response = (MessageContentsList) exchange.getIn().getBody();
                    AddResponse r = new AddResponse();
                    r.setAddResult((Integer) response.get(0));
                    exchange.getIn().setBody(r);
                })
                .to("mock:output");

        from("direct:subtractions")
                .removeHeaders("CamelHttp*")
                .process(Soap2Rest::process)
                .setHeader(CxfConstants.OPERATION_NAME, constant("{{endpoint.operation.subtract}}"))
                .setHeader(CxfConstants.OPERATION_NAMESPACE, constant(ENDPOINT_NAMESPACE))
                .to(ENDPOINT_URI)
                .process(exchange -> {
                    MessageContentsList response = (MessageContentsList) exchange.getIn().getBody();
                    SubtractResponse r = new SubtractResponse();
                    r.setSubtractResult((Integer) response.get(0));
                    exchange.getIn().setBody(r);
                })
                .to("mock:output");

        from("direct:division")
                .removeHeaders("CamelHttp*")
                .process(Soap2Rest::process)
                .setHeader(CxfConstants.OPERATION_NAME, constant("{{endpoint.operation.divide}}"))
                .setHeader(CxfConstants.OPERATION_NAMESPACE, constant(ENDPOINT_NAMESPACE))
                .to(ENDPOINT_URI)
                .process(exchange -> {
                    MessageContentsList response = (MessageContentsList) exchange.getIn().getBody();
                    DivideResponse r = new DivideResponse();
                    r.setDivideResult((Integer) response.get(0));
                    exchange.getIn().setBody(r);
                })
                .to("mock:output");

        from("direct:multiplication")
                .removeHeaders("CamelHttp*")
                .process(Soap2Rest::process)
                .setHeader(CxfConstants.OPERATION_NAME, constant("{{endpoint.operation.multiply}}"))
                .setHeader(CxfConstants.OPERATION_NAMESPACE, constant(ENDPOINT_NAMESPACE))
                .to(ENDPOINT_URI)
                .process(exchange -> {
                    MessageContentsList response = (MessageContentsList) exchange.getIn().getBody();
                    MultiplyResponse r = new MultiplyResponse();
                    r.setMultiplyResult((Integer) response.get(0));
                    exchange.getIn().setBody(r);
                })
                .to("mock:output");

    }


}