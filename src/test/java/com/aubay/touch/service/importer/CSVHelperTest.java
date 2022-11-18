package com.aubay.touch.service.importer;

import com.aubay.touch.domain.Group;
import com.aubay.touch.domain.Message;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CSVHelperTest {

    @Test
    void csvToMessages() throws IOException {
        List<Message> messages = CSVHelper.csvToMessages(this.getClass().getClassLoader().getResource("messages.csv").openStream());
        Assertions.assertThat(messages).hasSize(2);
        Assertions.assertThat(messages.get(1).toString()).isEqualTo("Message{id=null, title='hello2', message='my message2', deliveryTime=null, groups=[Group{name='Java'}], deliveryChannel=[Channel{name='SMS'}]}");

    }
}