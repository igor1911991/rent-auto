package com.project.rentauto.service;


import com.project.rentauto.exeptions.ChangeStatusAutoException;
import com.project.rentauto.model.Auto;
import com.project.rentauto.repository.AutoRepository;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutoServiceImplTest {

    private static final long ID = 1L;

    @Mock
    private AutoRepository autoRepository;

    @InjectMocks
    private AutoServiceImpl autoService;


    @Test
    void saveAuto_shouldCallRepository() {

        final Auto auto = mock(Auto.class);

        autoService.saveAuto(auto);

        verify(autoRepository).save(auto);
    }

    @Test
    void ListAuto_shouldCallFindAllByOrderById() {

        autoService.listAuto();

        verify(autoRepository).findAllByOrderById();
    }

    @Test
    void blockAuto_shouldCallBlockAuto() {

        autoService.blockAuto(ID);

        verify(autoRepository).blockAuto(ID);
    }

    @Test
    void unlockAuto_shouldCallUnlockAuto() {

        autoService.unlockAuto(ID);

        verify(autoRepository).unlockAuto(ID);
    }

    @Test
    void changeStatusAuto_shouldCallUnlockAuto() {

        when(autoRepository.available(ID)).thenReturn(false);

        autoService.changeStatusAuto(ID);

        verify(autoRepository).unlockAuto(ID);
    }

    @Test
    void changeStatusAuto_shouldCallBlockAuto() {

        when(autoRepository.available(ID)).thenReturn(true);

        autoService.changeStatusAuto(ID);

        verify(autoRepository).blockAuto(ID);
    }

    @Test
    void changeStatusAuto_shouldReturnChangeStatusAutoException() {

        when(autoRepository.getAutoOrderID(ID) > 0).thenThrow(ChangeStatusAutoException.class);

        assertThrows(ChangeStatusAutoException.class, () -> {autoService.changeStatusAuto(ID);});
    }

    @Test
    void isAvailable_shouldReturnTrue() {

        when(autoRepository.available(ID)).thenReturn(true);

        boolean autoAvailable = autoService.isAvailable(ID);

        assertTrue(autoAvailable);
    }

    @Test
    void isAvailable_shouldReturnFalse() {

        when(autoRepository.available(ID)).thenReturn(false);

        boolean autoAvailable = autoService.isAvailable(ID);

        assertFalse(autoAvailable);
    }

    @Test
    void listAvailableAuto_shouldCallGetAvailableAuto() {

        autoService.listAvailableAuto();

        verify(autoRepository).getAvailableAuto();
    }

    @Test
    void getAutoOrderID_shouldReturnAutoID() {

        when(autoRepository.getAutoOrderID(ID)).thenReturn(ID);

        Long autoOrderID = autoService.getAutoOrderID(ID);

        assertNotNull(autoOrderID);
        assertEquals(autoOrderID, ID);
    }

    @Test
    void setAutoOrderID_shouldCallSetOrderID() {

        autoService.setAutoOrderID(ID, ID);

        verify(autoRepository).setOrderID(ID, ID);
    }
}