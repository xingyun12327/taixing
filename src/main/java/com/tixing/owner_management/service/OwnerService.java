// service/OwnerService.java
package com.tixing.owner_management.service;

import com.tixing.owner_management.entity.Owner;
import java.util.List;

public interface OwnerService {
    List<Owner> list();
    void add(Owner owner);
    void update(Owner owner);
    void delete(Integer id);
    Owner findById(Integer id);
}
