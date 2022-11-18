package com.aubay.touch.service.importer;

import com.aubay.touch.domain.Employee;
import com.aubay.touch.domain.Message;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CSVHelperTest {

    @Test
    void csvToMessages() throws IOException {
        List<Message> messages = CSVHelper.csvToMessages(this.getClass().getClassLoader().getResource("messages.csv").openStream());
        assertThat(messages).hasSize(2);
        assertThat(messages.get(1).getGroups()).hasSize(2);
        assertThat(messages.get(1).toString())
                .isEqualTo("Message{id=null, title='hello2', message='my message2', deliveryTime=null, " +
                        "groups=[Group{name='Java'}, Group{name='DevOps'}], " +
                        "deliveryChannel=[Channel{name='SMS'}, Channel{name='E-mail'}]}");

    }

    @Test
    void csvToEmployees() throws IOException {
        List<Employee> employees = CSVHelper.csvToEmployees(this.getClass().getClassLoader().getResource("employees.csv").openStream());
        assertThat(employees).hasSize(2);
        assertThat(employees.get(0).toString())
                .isEqualTo("Employee{id=null, name='Thiago', groups=[Group{name='Java'}], messagesDelivered=[], " +
                        "employeeChannels=[" +
                        "EmployeeChannel{id=null, identifier=' 999999999', channel=Channel{name='Whatsapp'}}, " +
                        "EmployeeChannel{id=null, identifier=' thiago@mail.com', channel=Channel{name='Email'}}]}");
        assertThat(employees.get(1).getGroups()).hasSize(2);
        assertThat(employees.get(1).getEmployeeChannels()).hasSize(3);
        assertThat(employees.get(1).toString())
                .isEqualTo("Employee{id=null, name='Joseph', " +
                        "groups=[Group{name='Java'}, Group{name='DevOps'}], messagesDelivered=[], " +
                        "employeeChannels=[" +
                        "EmployeeChannel{id=null, identifier=' 8888888', channel=Channel{name='Whatsapp'}}, " +
                        "EmployeeChannel{id=null, identifier=' joseph', channel=Channel{name='Telegram'}}, " +
                        "EmployeeChannel{id=null, identifier='joseph@gmail.com', channel=Channel{name='Email'}}]}");

    }
}