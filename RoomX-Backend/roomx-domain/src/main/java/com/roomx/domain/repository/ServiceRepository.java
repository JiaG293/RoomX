package com.roomx.domain.repository;


import com.roomx.domain.model.aggrerate.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ServiceRepository {
    Optional<Service> findById(String id);
    Service save(Service service);
    void delete(Service service);
    void deleteById(String id);
    boolean checkServiceNameExists(String serviceName);
    Optional<Service> findByServiceName(String serviceName);
    boolean checkServiceCodeIsExists(String serviceCode);

    Optional<Service> findByServiceCode(String serviceCode);

    Optional<Service> findByIdAndStatus(String serviceId, String status);

    List<Service> saveAll(List<Service> services);
}
