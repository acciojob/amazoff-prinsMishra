package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Repository


public class OrderRepository {
    Map<String,Order> orderdb = new HashMap<>();
    Map<String,DeliveryPartner> deliveryPartnerdb = new HashMap<>();
    Map<String,String> orderparternsdb = new HashMap<>();
    Map<String, List<String>> partenerOrderdb = new HashMap<>();
    public void addOrder(Order order){
        orderdb.put(order.getId(),order);
    }
    public void addPartner(String partnerID){
        deliveryPartnerdb.put(partnerID, new DeliveryPartner(partnerID));
    }
    public void addOrderPartner(String OrderID, String partnerID){

         if(orderdb.containsKey(OrderID) && deliveryPartnerdb.containsKey(partnerID)){
             orderparternsdb.put(OrderID,partnerID);

             List<String> currentOrders = new ArrayList<>();
             if(partenerOrderdb.containsKey(partnerID)){
                 currentOrders = partenerOrderdb.get(partnerID);
             }
             currentOrders.add(OrderID);
             partenerOrderdb.put(partnerID,currentOrders);
             // increase the orders numbers
             DeliveryPartner deliveryPartner = deliveryPartnerdb.get(partnerID);
             deliveryPartner.setNumberOfOrders(currentOrders.size());
         }
         
    }

    public Order getOrderbyId(String orderId){
        return orderdb.get(orderId);
    }
    public DeliveryPartner getPartnerbyId(String partnerId){
        return deliveryPartnerdb.get(partnerId);
    }

    public int getOrderCountByPartnerId(String partnerId){
        return partenerOrderdb.get(partnerId).size();
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        return partenerOrderdb.get(partnerId);
    }
    public List<String> getAllOrders(){
        List<String> orders = new ArrayList<>();
        for(String order : orderdb.keySet()){
            orders.add(order);
        }
        return orders;
    }
    public int getCountOfUnassignedOrders(){
        return orderdb.size() - orderparternsdb.size();
    }
    public int getOrdersLeftAfterGivenTimeByPartnerId(int Time, String partnerId){
        int count =0;
        List<String> orders = partenerOrderdb.get(partnerId);
        for(String orderId : orders){
            int deliveryTime = orderdb.get(orderId).getDeliveryTime();
            if(deliveryTime>Time){
                count++;
            }
        }
        return count;
    }
    public int getLastDeliveryTimeByPartnerId(String partnerId){
        int maxtime=0;
        List<String> orders = partenerOrderdb.get(partnerId);
         for(String orderId: orders){
             int currenttime = orderdb.get(orderId).getDeliveryTime();
             maxtime = Math.max(maxtime, currenttime);
         }
         return maxtime;

    }
    public void deletePartnerById(String partnerId){
        deliveryPartnerdb.remove(partnerId);

        List<String> listOforders = partenerOrderdb.get(partnerId);
        partenerOrderdb.remove(partnerId);

        for(String order : listOforders){
            orderparternsdb.remove(order);
        }
    }
    public void deleteOrderById(String orderId){
        orderdb.remove(orderId);

        String partnerId = orderparternsdb.get(orderId);
        orderparternsdb.remove(orderId);

         partenerOrderdb.get(partnerId).remove(orderId);

         deliveryPartnerdb.get(partnerId).setNumberOfOrders(partenerOrderdb.get(partnerId).size());


    }

}
