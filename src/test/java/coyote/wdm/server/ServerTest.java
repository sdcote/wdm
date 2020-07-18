package coyote.wdm.server;

import coyote.wdm.WebDriverManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Collection;
import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(Parameterized.class)
public class ServerTest {
    static final Logger log = getLogger(lookup().lookupClass());
    public static final String EXT = IS_OS_WINDOWS ? ".exe" : "";

    @Parameter(0)
    public String path;

    @Parameter(1)
    public String driver;

    public static String serverPort;

    @Parameters(name = "{index}: {0}")
    public static Collection<Object[]> data() {
        return asList(new Object[][] { { "chromedriver", "chromedriver" + EXT },
                { "firefoxdriver", "geckodriver" + EXT },
                { "operadriver", "operadriver" + EXT },
                { "phantomjs", "phantomjs" + EXT },
                { "edgedriver", "msedgedriver.exe" },
                { "iedriver", "IEDriverServer.exe" },
                { "chromedriver?os=WIN", "chromedriver.exe" },
                { "chromedriver?os=LINUX&chromeDriverVersion=2.41&forceCache=true",
                        "chromedriver" } });
    }

    @BeforeClass
    public static void startServer() throws IOException {
        serverPort = getFreePort();
        log.debug("Test is starting WebDriverManager server at port {}",
                serverPort);

        WebDriverManager.main(new String[] { "server", serverPort });
    }

    @Test
    public void testServer() throws IOException {
        String serverUrl = String.format("http://localhost:%s/%s", serverPort,
                path);

        int timeoutSeconds = 60;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(timeoutSeconds, SECONDS)
                .readTimeout(timeoutSeconds, SECONDS)
                .callTimeout(timeoutSeconds, SECONDS).build();

        Request request = new Request.Builder().url(serverUrl).build();

        // Assert response
        Response response = client.newCall(request).execute();
        assertTrue(response.isSuccessful());

        // Assert attachment
        String attachment = String.format("attachment; filename=\"%s\"",
                driver);

        List<String> headers = response.headers().values("Content-Disposition");
        log.debug("Assessing {} ... {} should contain {}", driver, headers,
                attachment);
        assertTrue(headers.contains(attachment));
    }

    public static String getFreePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return String.valueOf(socket.getLocalPort());
        }
    }

}
