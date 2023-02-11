package ru.itsrv23.taxiservicemonitoring.scheduler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Slf4j
@Service
public class Wf {
    @Qualifier("psqlDataSource")
    private final DataSource psqlDataSource;

//    @Qualifier("psql2DataSource")
//    @Autowired
//    private final DataSource psql2DataSource;
//    private final XlogMainRepository repository;
//    private final EntityManager entityManager;

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.support.chat}")
    private String chat;

    public Wf(DataSource psqlDataSource) {
        this.psqlDataSource = psqlDataSource;
    }

    @Scheduled(fixedDelay = 60_000L)
    public void checkerWfJDBC() throws SQLException {
        String sql = "select count(idx) as cnt from xlog_main where editdate >= now() - '1 minute'::interval and descr like ?";
        int count = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = psqlDataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%ошибка связи%");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            log.info("Count Alerts: {}", count);
            if (count >= 10) {
                doJob();
                sendAlert();
                log.info("Ok, time to sleep");
                Thread.sleep(180_000);
            }
        } catch (SQLException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            Objects.requireNonNull(connection).close();
            Objects.requireNonNull(statement).close();
            Objects.requireNonNull(resultSet).close();
        }


    }

    /*
    @Scheduled(fixedDelay = 6000L)
    public void checkerWf(){
        Long count = repository.selectCountError(OffsetDateTime.now().minus(5L, ChronoUnit.DAYS), "Переход");
        System.out.println("count JPA Repo= " + count);
    }

    @SneakyThrows
    @Scheduled(fixedDelay = 6000L)
    public void checkerWfJDBC2(){
        String sql =  "select count(*) as cnt from ads";
        Integer count = Integer.valueOf(-1);
        Connection connection = psql2DataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            count = resultSet.getInt(1);
        }
        System.out.println("count SHOP DB= " + count);
        doJob();
        Thread.sleep(5000);
        System.out.println("Task end ");
    }

    @Scheduled(fixedDelay = 6000L)
    public void checkerWfEM(){
        Query query = entityManager.createNativeQuery("select count(idx) from xlog_main where editdate >= :editdate and descr like :descr");
        query.setParameter("editdate",OffsetDateTime.now().minus(5L, ChronoUnit.DAYS));
        query.setParameter("descr","%Переход%");
        BigInteger count = (BigInteger)query.getSingleResult();
        System.out.println("count EM= " + count);
    }
*/
    @SneakyThrows
    private void doJob() throws IOException {
        String command = "/etc/init.d/taxiwf restart";
//        String command = "\"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe\"";
        Runtime.getRuntime().exec(command);
    }

    private void sendAlert() throws MalformedURLException {
        String urlStr = String.format("https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s", token, chat, "Аларм, все пропало! WF в агонии...!!!");
        URL url = new URL(urlStr);
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            log.info("ResponseCode = " + responseCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Objects.requireNonNull(con).disconnect();
        }

    }

}
