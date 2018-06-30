package lab.cloud.bookstore.servicebroker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lab.cloud.bookstore.servicebroker.model.ServiceInstance;

public interface ServiceInstanceRepository extends JpaRepository<ServiceInstance, String> {}