package github.kyxap.com.config;

public class ConfigProps {
    public enum LoggerSettings {
        DEBUG("debug"), DEV_OUTPUT("dev-output");

        private final String description;

        LoggerSettings(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum App {
        URI_TO_GET_DATA_FROM("uri");

        private final String description;

        App(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
