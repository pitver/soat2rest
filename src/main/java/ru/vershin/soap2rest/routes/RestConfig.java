package ru.vershin.soap2rest.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

@Component
public class RestConfig extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("undertow").host("localhost").port(9090).bindingMode(RestBindingMode.auto).scheme("http")
                .dataFormatProperty("prettyPrint", "true")
                .contextPath("/")
                .apiContextPath("/api-doc")
                    .apiProperty("api.title", "SoapToRest")
                    .apiProperty("api.version", "1.0")
                    .apiProperty("host", "")
                .enableCORS(true);


        rest("/calculator")

                .get("/Add/?{num1}&{num2}")
                .consumes("text/plain").produces("text/plain")
                .description("add")
                .param().name("num1").type(RestParamType.path).description("add").dataType("int").endParam()
                .param().name("num2").type(RestParamType.path).description("add").dataType("int").endParam()
                .to("direct:additions");


    }
}
