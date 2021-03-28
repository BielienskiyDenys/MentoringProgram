package com.epam.rest.batch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import com.epam.rest.entity.Event;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import sun.security.krb5.internal.Ticket;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private DataSource dataSource;

    @Bean
    public StaxEventItemReader<Event> eventItemReader(){
        Map<String, Class> aliases = new HashMap<>();
        aliases.put("event", Event.class);

        EventConverter converter = new EventConverter();
        XStreamMarshaller ummarshaller = new XStreamMarshaller();
        ummarshaller.setAliases(aliases);
        ummarshaller.setConverters(converter);

        StaxEventItemReader<Event> reader = new StaxEventItemReader<>();
        reader.setResource(new ClassPathResource("/data/events.xml"));
        reader.setFragmentRootElementName("event");
        reader.setUnmarshaller(ummarshaller);

        return reader;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .transactionManager(transactionManager)
                .<Event, Event>chunk(3)
                .reader(eventItemReader())
                .writer(writer())
                .faultTolerant()
                .skipLimit(10)
                .skip(DuplicateKeyException.class)
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Event> writer() {
        JdbcBatchItemWriter<Event> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(this.dataSource);
        writer.setSql("INSERT INTO events (id, title, place_id, speaker, event_type_id, date) VALUES (?,?,?,?,?,?)");
        writer.setItemPreparedStatementSetter(new EventItemPreparedStmSetter());
        return writer;
    }

    private  class EventItemPreparedStmSetter implements ItemPreparedStatementSetter<Event> {
        public void setValues(Event result, PreparedStatement ps) throws SQLException {
            ps.setLong(1, result.getId());
            ps.setString(2, result.getTitle());
            ps.setInt(3, result.getPlace().ordinal());
            ps.setString(4, result.getSpeaker());
            ps.setInt(5, result.getEventType().ordinal());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            ps.setDate(6, java.sql.Date.valueOf( dateFormat.format(result.getDateTime())));
        }
    }
}
