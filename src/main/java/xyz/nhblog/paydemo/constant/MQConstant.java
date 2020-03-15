package xyz.nhblog.paydemo.constant;

public interface MQConstant {
    String DELAY_QUEUE = "paydemo.delay.queue";

    String DELAY_EXCHANGE = "paydemo.delay.exchange";

    String DELAY_ROUTING_KEY = "paydemo.delay";

    String VERIFY_QUEUE = "paydemo.verify.queue";

    String ORDER_EXCHANGE = "paydemo.order.exchange";

    String VERIFY_ROUTING_KEY = "paydemo.verify";

    String NEW_QUEUE = "paydemo.newOrder.queue";

    String NEW_ROUTING_KEY = "paydemo.newOrder";

    String CANCEL_QUEUE = "paydemo.cancel.queue";

    String CANCEL_ROUTING_KEY = "paydemo.cancel";
}
