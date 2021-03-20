package ru.vershin.soap2rest.routes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

@Component
public class RestConfig extends RouteBuilder {
    private static final String METADATA = "text/plain";

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("undertow").host("localhost").port(9090).bindingMode(RestBindingMode.auto).scheme("http")
                .clientRequestValidation(true)
                .dataFormatProperty("prettyPrint", "true")
                .contextPath("/")
                .apiContextPath("/api-doc")
                    .apiProperty("api.title", "SoapToRest")
                    .apiProperty("api.version", "1.0")
                    .apiProperty("host", "")
                .enableCORS(true);


        rest("/calculator")

                .get("/Add/?{num1}&{num2}")
                .consumes(METADATA).produces(METADATA)
                .param().name("num1").type(RestParamType.path).description("add").dataType("int").endParam()
                .param().name("num2").type(RestParamType.path).description("add").dataType("int").endParam()
                .responseMessage().code(200).responseModel(ApiResponse.class).endResponseMessage() //OK
                .responseMessage().code(400).responseModel(ApiResponse.class).message("Unexpected body").endResponseMessage() //Wrong input
                .responseMessage().code(500).responseModel(ApiResponse.class).endResponseMessage() //Not-OK
               /* .route()
                .validate(xpath("num1").regex("\\d+"))
                .validate(rest("num2").regex("\\d+"))
                .endRest()*/
                .to("direct:additions")

                .get("/Subtract/?{num1}&{num2}")
                .consumes(METADATA).produces(METADATA)
                .param().name("num1").type(RestParamType.path).description("add").dataType("int").endParam()
                .param().name("num2").type(RestParamType.path).description("add").dataType("int").endParam()
                .to("direct:subtractions")

                .get("/Divide/?{num1}&{num2}")
                .consumes(METADATA).produces(METADATA)
                .param().name("num1").type(RestParamType.path).description("add").dataType("int").endParam()
                .param().name("num2").type(RestParamType.path).description("add").dataType("int").endParam()
                .to("direct:division")

                .get("/Multiply/?{num1}&{num2}")
                .consumes(METADATA).produces(METADATA)
                .param().name("num1").type(RestParamType.path).description("add").dataType("int").endParam()
                .param().name("num2").type(RestParamType.path).description("add").dataType("int").endParam()
                .to("direct:multiplication");

    }


}
