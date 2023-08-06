package com.packdata.packdata;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packdata.packdata.model.CombinedPackData;
import com.packdata.packdata.model.Customer;
import com.packdata.packdata.model.Inventory;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/packdata")
public class CombinedDataController {

    private static final String PACK1_URL = "https://6466e9a7ba7110b663ab51f2.mockapi.io/api/v1/pack1";
    private static final String PACK2_URL = "https://6466e9a7ba7110b663ab51f2.mockapi.io/api/v1/pack2";

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/getCombinedData")
    public List<CombinedPackData> getCombinedData(@RequestBody Map<String, Integer> payload) throws JsonProcessingException {

        final Logger log = LoggerFactory.getLogger(CombinedDataController.class);

        Integer requestedCustomerId = payload.get("customer_id");
        log.info("requested customer id: " + requestedCustomerId);
        //Getting pack 1 data
        log.info("getting pack 1 data");
        String pack1Data = restTemplate.getForObject(PACK1_URL,String.class);

        //Getting pack 2 data
        log.info("getting pack 2 data");
        String pack2Data = restTemplate.getForObject(PACK2_URL,String.class);

        // ObjectMapper instantiation
		ObjectMapper objectMapper = new ObjectMapper();
        List<Customer> pack1CustomerList = objectMapper.readValue(pack1Data, new TypeReference<List<Customer>>(){});
        List<Customer> pack2CustomerList = objectMapper.readValue(pack2Data, new TypeReference<List<Customer>>(){});

    
        List<CombinedPackData> combinedPackDataList = new ArrayList<>(4);

        try {
                
            Optional<Customer> pack1Customer = pack1CustomerList.stream()
                .filter(a -> ( requestedCustomerId.equals( a.getCustomer_id() ) ) )
                .findFirst();
            log.info("pack1Customer " + pack1Customer);
        
            Optional<Customer> pack2Customer = pack2CustomerList.stream()
                .filter(a -> ( requestedCustomerId.equals( a.getCustomer_id() ) ) )
                .findFirst();
            log.info("pack2Customer " + pack2Customer.get());
            
            List<Inventory> pack1CustomerPackData = pack1Customer.get().getPack_data();
            List<String> pack1List =  getPackListFromCustomerPackData(pack1CustomerPackData);
            log.info("pack1List " + pack1List);

            List<Inventory> pack2CustomerPackData = pack2Customer.get().getPack_data();
            log.info("pack2CustomerPackData " + pack2CustomerPackData);
            List<String> pack2List =  getPackListFromCustomerPackData(pack2CustomerPackData);
            log.info("pack2List " + pack2List);
            
            CombinedPackData combinedPackData = new CombinedPackData();
            combinedPackData.setCustomer_id(requestedCustomerId);
            combinedPackData.setId(pack1Customer.get().getId());
            combinedPackData.setPack1(pack1List);
            combinedPackData.setPack2(pack2List);
            
            

            combinedPackDataList.add(combinedPackData);
            

        } catch (Exception e) {
            // TODO: handle exception
        }

        return combinedPackDataList;
    }

    private List<String> getPackListFromCustomerPackData(List<Inventory> pack1CustomerPackData) {
        return pack1CustomerPackData.stream()
        .map(a -> 
            new String(a.getIngredient() + " " + a.getQuantity() + a.getUnit().toString())
        )
        .collect(Collectors.toList());
    }


}
