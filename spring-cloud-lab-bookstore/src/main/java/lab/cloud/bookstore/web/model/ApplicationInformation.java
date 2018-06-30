package lab.cloud.bookstore.web.model;

public class ApplicationInformation {

    private final String baseUrl;

    public ApplicationInformation(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}