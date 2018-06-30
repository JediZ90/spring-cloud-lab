package lab.cloud.bookstore.servicebroker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lab.cloud.bookstore.servicebroker.model.ServiceBinding;

public interface ServiceBindingRepository extends JpaRepository<ServiceBinding, String> {}