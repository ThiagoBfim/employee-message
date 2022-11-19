package com.aubay.touch.service.importer;

import com.aubay.touch.domain.*;
import com.aubay.touch.repository.ChannelRepository;
import com.aubay.touch.repository.GroupRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Component
public class CSVHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVHelper.class.getName());
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Message> csvToMessages(InputStream is) {
        try (var fileReader = new BufferedReader(new InputStreamReader(is));
             var csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT)) {

            List<Message> messages = new ArrayList<>();
            var csvRecords = csvParser.getRecords();
            String[] columns = null;
            for (var csvRecord : csvRecords) {
                if (columns == null) {
                    columns = new String[]{csvRecord.get(0).strip(), csvRecord.get(1).strip(), csvRecord.get(2).strip(), csvRecord.get(3).strip()};
                    validateColumns(columns);
                } else {
                    String title = csvRecord.get(0);
                    if (StringUtils.isNotBlank(title)) {
                        var message = new Message(
                                title,
                                csvRecord.get(1),
                                csvRecord.get(2),
                                csvRecord.get(3));

                        messages.add(message);
                    }
                }
            }

            return messages;
        } catch (Exception e) {
            LOGGER.error("Error parsing CSV to messages", e);
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage(), e);
        }
    }

    private static void validateColumns(String[] columns) {
        String message = null;
        if (!columns[0].toLowerCase().contains("title")) {
            message = "First column should be 'title'";
        }
        if (!columns[1].toLowerCase().contains("message")) {
            message = "Second column should be 'message'";
        }
        if (!columns[2].toLowerCase().contains("groups")) {
            message = "Third column should be 'groups'";
        }
        if (!columns[3].toLowerCase().contains("channels")) {
            message = "Fourth column should be 'channels'";
        }
        if (Objects.nonNull(message)) {
            throw new RuntimeException(message + "Current order: " + columns[0] + " | " + columns[1] + " | " + columns[2] + " | " + columns[3]);
        }
    }

    public static List<Employee> csvToEmployees(InputStream is) {
        try (var fileReader = new BufferedReader(new InputStreamReader(is));
             var csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT)) {

            List<Employee> employees = new ArrayList<>();
            var csvRecords = csvParser.getRecords();
            List<Channel> channels = new ArrayList<>();
            for (var csvRecord : csvRecords) {
                if (channels.isEmpty()) {
                    csvRecord.stream().skip(2).forEach(c -> {
                        channels.add(new Channel(c.strip()));
                    });
                } else {
                    String name = csvRecord.get(0);
                    if (StringUtils.isNotBlank(name)) {
                        Set<EmployeeChannel> employeeChannels = new HashSet<>();
                        for (int i = 1; i <= channels.size(); i++) {
                            String identifier = csvRecord.get(i + 1);
                            if (StringUtils.isNotBlank(identifier)) {
                                employeeChannels.add(new EmployeeChannel(channels.get(i - 1), identifier));
                            }
                        }
                        var employee = new Employee(name, csvRecord.get(1), employeeChannels);
                        employees.add(employee);
                    }
                }
            }

            return employees;
        } catch (Exception e) {
            LOGGER.error("Error parsing CSV to messages", e);
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage(), e);
        }
    }
}