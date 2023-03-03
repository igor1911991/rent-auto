package com.project.rentauto.service;


import com.project.rentauto.exeptions.ChangeStatusAutoException;
import com.project.rentauto.model.Auto;
import com.project.rentauto.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoServiceImpl implements AutoService{
    private final AutoRepository autoRepository;

    @Autowired
    public AutoServiceImpl(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }


    @Override
    public void saveAuto(Auto auto){
        autoRepository.save(auto);
    }

    @Override
    public List<Auto> listAuto() {
        return autoRepository.findAllByOrderById();
    }

    @Override
    public void blockAuto(Long id) {
        autoRepository.blockAuto(id);
    }

    @Override
    public void unlockAuto(Long id) {
        autoRepository.unlockAuto(id);
    }

    @Override
    public void changeStatusAuto(Long id) throws ChangeStatusAutoException{

        long autoOrderID = autoRepository.getAutoOrderID(id);
        if (autoOrderID > 0) throw new ChangeStatusAutoException(String.valueOf(autoOrderID));

        if(isAvailable(id)) {
            blockAuto(id);
        } else {
            unlockAuto(id);
        }
    }

    @Override
    public Boolean isAvailable(Long id){
        return autoRepository.available(id);
    }

    @Override
    public List<Auto> listAvailableAuto() {
        return autoRepository.getAvailableAuto();
    }

    @Override
    public Long getAutoOrderID(Long autoID) {
        return autoRepository.getAutoOrderID(autoID);
    }

    @Override
    public void setAutoOrderID(Long orderID, Long autoID) {
        autoRepository.setOrderID(orderID, autoID);
    }
}
