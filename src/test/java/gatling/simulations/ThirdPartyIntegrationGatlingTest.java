package gatling.simulations;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.rampUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.header;
import static io.gatling.javaapi.http.HttpDsl.headerRegex;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

/**
 * Performance test for the ThirdPartyIntegration entity.
 *
 * @see <a href="https://github.com/jhipster/generator-jhipster/tree/v8.5.0/generators/gatling#logging-tips">Logging tips</a>
 */
public class ThirdPartyIntegrationGatlingTest extends Simulation {

    String baseURL = Optional.ofNullable(System.getProperty("baseURL")).orElse("http://localhost:8080");

    HttpProtocolBuilder httpConf = http
        .baseUrl(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")
        .silentResources(); // Silence all resources like css or css so they don't clutter the results

    Map<String, String> headers_http = Map.of("Accept", "application/json");

    Map<String, String> headers_http_authentication = Map.of("Content-Type", "application/json", "Accept", "application/json");

    Map<String, String> headers_http_authenticated = Map.of("Accept", "application/json", "Authorization", "${access_token}");

    ChainBuilder scn = exec(http("First unauthenticated request").get("/api/account").headers(headers_http).check(status().is(401)))
        .exitHereIfFailed()
        .pause(10)
        .exec(
            http("Authentication")
                .post("/api/authenticate")
                .headers(headers_http_authentication)
                .body(StringBody("{\"username\":\"admin\", \"password\":\"admin\"}"))
                .asJson()
                .check(header("Authorization").saveAs("access_token"))
        )
        .exitHereIfFailed()
        .pause(2)
        .exec(http("Authenticated request").get("/api/account").headers(headers_http_authenticated).check(status().is(200)))
        .pause(10)
        .repeat(2)
        .on(
            exec(
                http("Get all thirdPartyIntegrations")
                    .get("/api/third-party-integrations")
                    .headers(headers_http_authenticated)
                    .check(status().is(200))
            )
                .pause(Duration.ofSeconds(10), Duration.ofSeconds(20))
                .exec(
                    http("Create new thirdPartyIntegration")
                        .post("/api/third-party-integrations")
                        .headers(headers_http_authenticated)
                        .body(
                            StringBody(
                                "{" +
                                "\"isDraft\": null" +
                                ", \"integrationName\": \"SAMPLE_TEXT\"" +
                                ", \"companyName\": \"SAMPLE_TEXT\"" +
                                ", \"description\": \"SAMPLE_TEXT\"" +
                                ", \"iconPath\": \"SAMPLE_TEXT\"" +
                                ", \"enabled\": null" +
                                ", \"accountNumber\": \"SAMPLE_TEXT\"" +
                                ", \"minimumAmount\": 0" +
                                ", \"maximumAmount\": 0" +
                                ", \"currencyCode\": \"SAMPLE_TEXT\"" +
                                ", \"paymentConfirmationTemplate\": \"SAMPLE_TEXT\"" +
                                ", \"paymentDetailTemplate\": \"SAMPLE_TEXT\"" +
                                ", \"integrationCategory\": \"PAYMENT\"" +
                                ", \"visiblity\": \"EVERYONE\"" +
                                ", \"confirmRecipientIdentity\": null" +
                                "}"
                            )
                        )
                        .asJson()
                        .check(status().is(201))
                        .check(headerRegex("Location", "(.*)").saveAs("new_thirdPartyIntegration_url"))
                )
                .exitHereIfFailed()
                .pause(10)
                .repeat(5)
                .on(
                    exec(
                        http("Get created thirdPartyIntegration")
                            .get("${new_thirdPartyIntegration_url}")
                            .headers(headers_http_authenticated)
                    ).pause(10)
                )
                .exec(
                    http("Delete created thirdPartyIntegration")
                        .delete("${new_thirdPartyIntegration_url}")
                        .headers(headers_http_authenticated)
                )
                .pause(10)
        );

    ScenarioBuilder users = scenario("Test the ThirdPartyIntegration entity").exec(scn);

    {
        setUp(
            users.injectOpen(rampUsers(Integer.getInteger("users", 100)).during(Duration.ofMinutes(Integer.getInteger("ramp", 1))))
        ).protocols(httpConf);
    }
}
