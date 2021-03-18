package ru.vershin.soap2rest.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.stereotype.Component;
import org.tempuri.Add;
import org.tempuri.AddResponse;
@Component
public class Soap2Rest extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:additions")
                .removeHeaders("CamelHttp*")
                .process(exchange -> {
                    Add add = new Add();
                    add.setIntA(Integer.parseInt(exchange.getIn().getHeader("num1").toString()));
                    add.setIntB(Integer.parseInt(exchange.getIn().getHeader("num2").toString()));
                    exchange.getIn().setBody(add);
                })
               .setHeader(CxfConstants.OPERATION_NAME, constant("{{endpoint.operation.add}}"))
                .setHeader(CxfConstants.OPERATION_NAMESPACE, constant("{{endpoint.namespace}}"))
                .to("cxf:bean:cxfConvertTemp")
                .process(exchange -> {
                    MessageContentsList response = (MessageContentsList) exchange.getIn().getBody();
                    AddResponse r = (AddResponse) response.get(0);
                    exchange.getIn().setBody("AddResult: "+r.getAddResult());
                })
                .to("mock:output");
    }
}
