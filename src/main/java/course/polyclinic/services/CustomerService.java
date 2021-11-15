package course.polyclinic.services;

import course.polyclinic.components.Customer;
import course.polyclinic.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;
    public void saveCustomer(Customer customer){
        customerRepo.save(customer);
    }
    public void findById(Long id){
        customerRepo.findById(id);
    }
}
