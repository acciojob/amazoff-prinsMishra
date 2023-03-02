package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class OrderService {
    @Autowired
    OrderRepository orderRepository;


    public void addOrder(Order order){

        orderRepository.addOrder(order);
    }
    public void addPartner(String partnerID){
        orderRepository.addPartner(partnerID);
    }

    public void addOrderPartner(String orderID, String partnerID){
        orderRepository.addOrderPartner(orderID,partnerID);
    }
    public Order getOrderbyId(String orderId){
        return orderRepository.getOrderbyId(orderId);
    }
    public DeliveryPartner getPartnerbyId(String partnerId){
        return orderRepository.getPartnerbyId(partnerId);
    }

    public int getOrderCountByPartnerId(String partnerId){
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        return orderRepository.getOrdersByPartnerId(partnerId);
    }
    public List<String> getAllOrders(){
        return orderRepository.getAllOrders();
    }
    public int getCountOfUnassignedOrders(){
        return orderRepository.getCountOfUnassignedOrders();
    }
    public int getOrdersLeftAfterGivenTimeByPartnerId(String deliveryTime , String partnerId){
        String time[] = deliveryTime.split(":");
        int newTime = Integer.parseInt(time[0])*60 + Integer.parseInt(time[1]);

        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(newTime, partnerId);
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        int time = orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
        String hh = String.valueOf(time/60);
        String mm = String.valueOf(time%60);
        if(hh.length() <2){
            hh="0"+hh;
        }
        if(mm.length()<2){
            mm= "0"+mm;
        }
        return hh+":"+mm;


    }
    public void deletePartnerById(String partnerId){
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId){
        orderRepository.deleteOrderById(orderId);
    }
}
