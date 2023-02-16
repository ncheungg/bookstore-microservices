/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Business;

import com.mycompany.Persistence.OrdersCRUD;
import io.grpc.stub.StreamObserver;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.event.EventReceive;
import io.kubemq.sdk.event.Subscriber;
import io.kubemq.sdk.subscription.EventsStoreType;
import io.kubemq.sdk.subscription.SubscribeRequest;
import io.kubemq.sdk.subscription.SubscribeType;
import io.kubemq.sdk.tools.Converter;
import java.io.IOException;
import javax.net.ssl.SSLException;

/**
 *
 * @author student
 */
public class Messaging {
    
    public static void Receiving_Events_Store(String cname) throws SSLException, ServerAddressNotSuppliedException {
        String ChannelName = cname;
        String ClientID = "hello-world-subscribe1r";
        String kubeMQAddress = System.getenv("kubeMQAddress");
        Subscriber subscriber = new Subscriber(kubeMQAddress);
        SubscribeRequest subscribeRequest = new SubscribeRequest();
        subscribeRequest.setChannel(ChannelName);
        subscribeRequest.setClientID(ClientID);
        subscribeRequest.setSubscribeType(SubscribeType.EventsStore);
        subscribeRequest.setEventsStoreType(EventsStoreType.StartAtSequence);
        subscribeRequest.setEventsStoreTypeValue(1);

        StreamObserver<EventReceive> streamObserver = new StreamObserver<EventReceive>() {

            @Override
            public void onNext(EventReceive value) {
                try {
                    String val=(String) Converter.FromByteArray(value.getBody());
                    System.out.printf("Event Received: EventID: %s, Channel: %s, Metadata: %s, Body: %s",
                            value.getEventId(), value.getChannel(), value.getMetadata(),
                            Converter.FromByteArray(value.getBody()));
                    
                    String[] msgParts = val.split(":");
                    
                    if (msgParts.length == 4) {
                        if (msgParts[0].equals("ORDER")) {
                            
                            int bookID = Integer.parseInt(msgParts[1]);
                            int userID = Integer.parseInt(msgParts[2]);
                            String date = msgParts[3];
                            
                            OrdersCRUD.addOrder(bookID, userID, date);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    System.out.printf("ClassNotFoundException: %s", e.getMessage());
                } catch (IOException e) {
                    System.out.printf("IOException: %s", e.getMessage());
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.printf("onError:  %s", t.getMessage());
            }

            @Override
            public void onCompleted() {

            }

        };
        subscriber.SubscribeToEvents(subscribeRequest, streamObserver);

    }
    
}
