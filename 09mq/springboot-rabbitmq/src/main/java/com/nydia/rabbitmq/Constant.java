package com.nydia.rabbitmq;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/8/31 00:04
 * @Description: Constant
 */
public class Constant {
    public static final String queue_1 = "queue-common";


    public static final String exchange_1 = "exchange-A";
    public static final String queue_2 = "queue-A";
    public static final String routing_key_1 = "routingKey-A";


    public static final String queue_delay = "queue-delay";
    public static final String exchange_delay = "exchange-delay";
    public static final String exchange_type_delay = "x-delayed-message";
    public static final String routing_key_delay = "routingKey-delay";



    public static final String exchange_transaction = "exchange-transaction";
    public static final String routing_key_transaction = "routingKey-transaction";
    public static final String queue_transaction = "queue-transaction";


}
