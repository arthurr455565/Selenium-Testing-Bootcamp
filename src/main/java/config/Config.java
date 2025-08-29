package config;

import java.util.Optional;

/**
 * Centralized configuration with sensible defaults.
 * Values can be overridden via system properties or environment variables.
 *
 * -DbaseUrl=https://example.com
 * -Dbrowser=chrome|firefox
 * -Dheadless=true|false
 */
public final class Config {

    private Config() {}

    public static String getBaseUrl() {
        return get("baseUrl", env("BASE_URL")).orElse("https://www.google.com");
    }

    public static String getBrowser() {
        return get("browser", env("BROWSER")).orElse("chrome");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(get("headless", env("HEADLESS")).orElse(defaultHeadless()));
    }

    public static int getImplicitWaitSeconds() {
        return Integer.parseInt(get("implicitWaitSeconds", env("IMPLICIT_WAIT_SECONDS")).orElse("5"));
    }

    public static int getExplicitWaitSeconds() {
        return Integer.parseInt(get("explicitWaitSeconds", env("EXPLICIT_WAIT_SECONDS")).orElse("10"));
    }

    private static Optional<String> get(String sysProp, Optional<String> env) {
        String fromSys = System.getProperty(sysProp);
        if (fromSys != null && !fromSys.isBlank()) return Optional.of(fromSys);
        return env.filter(v -> !v.isBlank());
    }

    private static Optional<String> env(String name) {
        return Optional.ofNullable(System.getenv(name));
    }

    private static String defaultHeadless() {
        String ci = System.getenv("CI");
        return ci != null && ci.equalsIgnoreCase("true") ? "true" : "false";
    }
}


