package catalog.distpatcher;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class DistpatcherFunctionsTest {

    @Autowired
    private InputDestination input;

    @Autowired
    private OutputDestination output;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void willProcessAMessage() throws IOException {
        Long purchaseId = 127546L;

        Message<PurchaseAcceptedMessage> messageIn =
                MessageBuilder.withPayload(new PurchaseAcceptedMessage(242L)).build();
        Message<PurchasedDistpatchedMessage> messageOut =
                MessageBuilder.withPayload(new PurchasedDistpatchedMessage(242L)).build();

        this.input.send(messageIn);

        assertThat(mapper.readValue(this.output.receive().getPayload(), PurchasedDistpatchedMessage.class))
                .isEqualTo(messageOut.getPayload());

    }
}
