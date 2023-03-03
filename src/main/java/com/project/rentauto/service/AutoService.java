package com.project.rentauto.service;

import com.project.rentauto.exeptions.ChangeStatusAutoException;
import com.project.rentauto.model.Auto;

import java.util.List;

public interface AutoService {
    void saveAuto(Auto auto);
    List<Auto> listAuto();
    void blockAuto(Long id);
    void unlockAuto(Long id);
    void changeStatusAuto(Long id) throws ChangeStatusAutoException;
    Boolean isAvailable(Long id);
    List<Auto> listAvailableAuto();
    Long getAutoOrderID(Long autoID);
    void setAutoOrderID(Long orderID, Long autoID);

}
