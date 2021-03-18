package ru.vershin.soap2rest.cxf;

import org.apache.camel.component.cxf.CxfEndpoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tempuri.Calculator;
import org.tempuri.CalculatorSoap;

@Configuration
public class CxfBeans {

    @Value("${endpoint.wsdl}")
    private String SOAP_URL;


    @Bean(name = "cxfConvertTemp")
    public CxfEndpoint buildCxfEndpoint() {
        CxfEndpoint cxf = new CxfEndpoint();
        cxf.setAddress(SOAP_URL);
        cxf.setServiceClass(CalculatorSoap.class);
        return cxf;
    }
}
