package lab.cloud.bookstore.servicebroker.service;

import org.springframework.cloud.servicebroker.exception.ServiceInstanceDoesNotExistException;
import org.springframework.cloud.servicebroker.model.instance.CreateServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.CreateServiceInstanceResponse;
import org.springframework.cloud.servicebroker.model.instance.CreateServiceInstanceResponse.CreateServiceInstanceResponseBuilder;
import org.springframework.cloud.servicebroker.model.instance.DeleteServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.DeleteServiceInstanceResponse;
import org.springframework.cloud.servicebroker.model.instance.GetServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.GetServiceInstanceResponse;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Service;

import lab.cloud.bookstore.servicebroker.model.ServiceInstance;
import lab.cloud.bookstore.servicebroker.repository.ServiceInstanceRepository;
import lab.cloud.bookstore.web.service.BookStoreService;

import java.util.Optional;

@Service
public class BookStoreServiceInstanceService implements ServiceInstanceService {

    private final BookStoreService storeService;
    private final ServiceInstanceRepository instanceRepository;

    public BookStoreServiceInstanceService(BookStoreService storeService,
                    ServiceInstanceRepository instanceRepository) {
        this.storeService = storeService;
        this.instanceRepository = instanceRepository;
    }

    @Override
    public CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request) {
        String instanceId = request.getServiceInstanceId();

        CreateServiceInstanceResponseBuilder responseBuilder = CreateServiceInstanceResponse.builder();

        if (instanceRepository.existsById(instanceId)) {
            responseBuilder.instanceExisted(true);
        } else {
            storeService.createBookStore(instanceId);

            saveInstance(request, instanceId);
        }

        return responseBuilder.build();
    }

    @Override
    public GetServiceInstanceResponse getServiceInstance(GetServiceInstanceRequest request) {
        String instanceId = request.getServiceInstanceId();

        Optional<ServiceInstance> serviceInstance = instanceRepository.findById(instanceId);

        if (serviceInstance.isPresent()) {
            return GetServiceInstanceResponse.builder()
                            .serviceDefinitionId(serviceInstance.get().getServiceDefinitionId())
                            .planId(serviceInstance.get().getPlanId()).parameters(serviceInstance.get().getParameters())
                            .build();
        } else {
            throw new ServiceInstanceDoesNotExistException(instanceId);
        }
    }

    @Override
    public DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request) {
        String instanceId = request.getServiceInstanceId();

        if (instanceRepository.existsById(instanceId)) {
            storeService.deleteBookStore(instanceId);
            instanceRepository.deleteById(instanceId);

            return DeleteServiceInstanceResponse.builder().build();
        } else {
            throw new ServiceInstanceDoesNotExistException(instanceId);
        }
    }

    private void saveInstance(CreateServiceInstanceRequest request, String instanceId) {
        ServiceInstance serviceInstance = new ServiceInstance(instanceId, request.getServiceDefinitionId(),
                        request.getPlanId(), request.getParameters());
        instanceRepository.save(serviceInstance);
    }
}
