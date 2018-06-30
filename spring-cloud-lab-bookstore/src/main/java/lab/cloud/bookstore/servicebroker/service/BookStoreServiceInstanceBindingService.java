package lab.cloud.bookstore.servicebroker.service;

import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceAppBindingResponse.CreateServiceInstanceAppBindingResponseBuilder;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.binding.GetServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.GetServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.binding.GetServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import lab.cloud.bookstore.servicebroker.model.ServiceBinding;
import lab.cloud.bookstore.servicebroker.repository.ServiceBindingRepository;
import lab.cloud.bookstore.web.model.ApplicationInformation;
import lab.cloud.bookstore.web.model.User;
import lab.cloud.bookstore.web.service.UserService;

import static lab.cloud.bookstore.web.security.SecurityAuthorities.BOOK_STORE_ID_PREFIX;
import static lab.cloud.bookstore.web.security.SecurityAuthorities.FULL_ACCESS;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BookStoreServiceInstanceBindingService implements ServiceInstanceBindingService {

    private static final String URI_KEY = "uri";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";

    private final ServiceBindingRepository bindingRepository;
    private final UserService userService;
    private final ApplicationInformation applicationInformation;

    public BookStoreServiceInstanceBindingService(ServiceBindingRepository bindingRepository, UserService userService,
                    ApplicationInformation applicationInformation) {
        this.bindingRepository = bindingRepository;
        this.userService = userService;
        this.applicationInformation = applicationInformation;
    }

    @Override
    public CreateServiceInstanceBindingResponse createServiceInstanceBinding(
                    CreateServiceInstanceBindingRequest request) {
        CreateServiceInstanceAppBindingResponseBuilder responseBuilder = CreateServiceInstanceAppBindingResponse
                        .builder();

        Optional<ServiceBinding> binding = bindingRepository.findById(request.getBindingId());

        if (binding.isPresent()) {
            responseBuilder.bindingExisted(true).credentials(binding.get().getCredentials());
        } else {
            User user = createUser(request);

            Map<String, Object> credentials = buildCredentials(request.getServiceInstanceId(), user);
            saveBinding(request, credentials);

            responseBuilder.bindingExisted(false).credentials(credentials);
        }

        return responseBuilder.build();
    }

    @Override
    public GetServiceInstanceBindingResponse getServiceInstanceBinding(GetServiceInstanceBindingRequest request) {
        String bindingId = request.getBindingId();

        Optional<ServiceBinding> serviceBinding = bindingRepository.findById(bindingId);

        if (serviceBinding.isPresent()) {
            return GetServiceInstanceAppBindingResponse.builder().parameters(serviceBinding.get().getParameters())
                            .credentials(serviceBinding.get().getCredentials()).build();
        } else {
            throw new ServiceInstanceBindingDoesNotExistException(bindingId);
        }
    }

    @Override
    public void deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request) {
        String bindingId = request.getBindingId();

        if (bindingRepository.existsById(bindingId)) {
            bindingRepository.deleteById(bindingId);
            userService.deleteUser(bindingId);
        } else {
            throw new ServiceInstanceBindingDoesNotExistException(bindingId);
        }
    }

    private User createUser(CreateServiceInstanceBindingRequest request) {
        return userService.createUser(request.getBindingId(), FULL_ACCESS,
                        BOOK_STORE_ID_PREFIX + request.getServiceInstanceId());
    }

    private Map<String, Object> buildCredentials(String instanceId, User user) {
        String uri = buildUri(instanceId);

        Map<String, Object> credentials = new HashMap<>();
        credentials.put(URI_KEY, uri);
        credentials.put(USERNAME_KEY, user.getUsername());
        credentials.put(PASSWORD_KEY, user.getPassword());
        return credentials;
    }

    private String buildUri(String instanceId) {
        return UriComponentsBuilder.fromUriString(applicationInformation.getBaseUrl())
                        .pathSegment("bookstores", instanceId).build().toUriString();
    }

    private void saveBinding(CreateServiceInstanceBindingRequest request, Map<String, Object> credentials) {
        ServiceBinding serviceBinding = new ServiceBinding(request.getBindingId(), request.getParameters(),
                        credentials);
        bindingRepository.save(serviceBinding);
    }
}