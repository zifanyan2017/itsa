package itsaerror404.message;

import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    private final String brokerURL = "ssl://b-da7dcee3-cd78-4018-afea-054f4e05331d-1.mq.ap-southeast-1.amazonaws.com:61617";
    private final String username = "itsa-error404";
    private final String password = "Y*YUYtf89*6f0)(75#*%hf)uf$";

    private final Message message = new Message();
    final ActiveMQConnectionFactory connectionFactory =
            message.createActiveMQConnectionFactory();
    final PooledConnectionFactory pooledConnectionFactory =
            message.createPooledConnectionFactory(connectionFactory);

    @RequestMapping(value="/sendmessage", method= RequestMethod.POST)
    public String sendMessage(@RequestParam(value="queue") String queue, @RequestParam(value="message") String message){
        JSONObject json = new JSONObject();

        try {
            final Connection producerConnection = pooledConnectionFactory.createConnection();
            producerConnection.start();

            final Session producerSession = producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            final Destination producerDestination = producerSession.createQueue(queue);
            final MessageProducer producer = producerSession.createProducer(producerDestination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            final TextMessage producerMessage = producerSession.createTextMessage(message);
            producer.send(producerMessage);

            producer.close();
            producerSession.close();
            producerConnection.close();

            json.put("status", "success");
        }
        catch (JMSException e){
            json.put("status", "fail");
            json.put("error", "Message not sent");
        }
        return json.toString();

    }

    @RequestMapping(value="/receivemessage", method= RequestMethod.GET)
    public String receiveMessage(@RequestParam(value="queue") String queue) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        while (true) {
            try {
                final Connection consumerConnection = connectionFactory.createConnection();
                consumerConnection.start();

                final Session consumerSession = consumerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                final Destination consumerDestination = consumerSession.createQueue(queue);
                final MessageConsumer consumer = consumerSession.createConsumer(consumerDestination);

                final javax.jms.Message consumerMessage = consumer.receive(1000);
                final TextMessage consumerTextMessage = (TextMessage) consumerMessage;

                try {
                    String text = consumerTextMessage.getText();
                    System.out.println("Message received: " + text);
                    array.add(text);
                } catch (NullPointerException npe) {
                    break;
                } finally {

                    consumer.close();
                    consumerSession.close();
                    consumerConnection.close();

                    json.put("status", "success");

                }

            } catch (JMSException e) {
                json.put("status", "fail");
                json.put("error", "Message not sent");
                return json.toString();
            }
        }
        json.put("content", array);
        return json.toString();

    }
}
