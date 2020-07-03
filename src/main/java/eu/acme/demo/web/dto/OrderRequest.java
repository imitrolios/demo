package eu.acme.demo.web.dto;

import lombok.Data;

@Data
public class OrderRequest {

    String clientReferenceCode;

    //TODO: place required fields in order to create an order submitted by client
}
