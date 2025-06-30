package org.order.service;

import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CatalogService {

    private static String productServiceEndpoint = "http://localhost:3002";
    public Double getProductPrice(Long productId, String token) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getInterceptors().add((request, body, execution) -> {
                request.getHeaders().add("Authorization", "Bearer " + token);
                return execution.execute(request, body);
            });
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(productServiceEndpoint + "/products/" + productId, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                JSONObject productResponse = new JSONObject(responseEntity.getBody());
                return productResponse.getDouble("price");
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    public Optional<Boolean> buyProduct(Long productId, Integer quantity, String token) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getInterceptors().add((request, body, execution) -> {
                request.getHeaders().add("Authorization", "Bearer " + token);
                return execution.execute(request, body);
            });
            ResponseEntity<String> responseEntity = restTemplate.exchange(productServiceEndpoint + "/products/buy/" + productId + "?quantity=" + quantity, HttpMethod.PUT, null, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return Optional.of(true);
            }
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.empty();
    }
}
