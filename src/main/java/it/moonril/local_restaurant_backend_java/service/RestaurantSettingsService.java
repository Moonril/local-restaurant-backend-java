package it.moonril.local_restaurant_backend_java.service;


import it.moonril.local_restaurant_backend_java.dto.RestaurantSettingsDto;

import it.moonril.local_restaurant_backend_java.exceptions.NotFoundException;
import it.moonril.local_restaurant_backend_java.models.RestaurantSettings;
import it.moonril.local_restaurant_backend_java.repository.RestaurantSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



@Service
public class RestaurantSettingsService {

    @Autowired
    private RestaurantSettingsRepository settingsRepository;

    public RestaurantSettings saveSettings(RestaurantSettingsDto settingsDto) throws NotFoundException {
        RestaurantSettings settings = new RestaurantSettings();

        settings.setMaxCapacity(settingsDto.getMaxCapacity());
        settings.setMaxPartySize(settingsDto.getMaxPartySize());
        settings.setSlotDurationMinutes(settingsDto.getSlotDurationMinutes());
        settings.setOpeningTime(settingsDto.getOpeningTime());
        settings.setClosingTime(settingsDto.getClosingTime());
        settings.setClosedDays(settingsDto.getClosedDays());
        settings.setReservationAdvanceDays(settingsDto.getReservationAdvanceDays());
        settings.setMinAdvanceMinutes(settingsDto.getMinAdvanceMinutes());
        settings.setCancellationLimitHours(settingsDto.getCancellationLimitHours());

        return settingsRepository.save(settings);
    }

    public RestaurantSettings getSettings(int id) throws NotFoundException{
        return settingsRepository.findById(id).orElseThrow(() -> new NotFoundException("Settings with id: " + id + " not found"));
    }

    public Page<RestaurantSettings> getAllSettings(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return settingsRepository.findAll(pageable);
    }


    public RestaurantSettings updateSettings(int id, RestaurantSettingsDto settingsDto) throws NotFoundException {
        RestaurantSettings settingsToUpdate = getSettings(id);

        settingsToUpdate.setMaxCapacity(settingsDto.getMaxCapacity());
        settingsToUpdate.setMaxPartySize(settingsDto.getMaxPartySize());
        settingsToUpdate.setSlotDurationMinutes(settingsDto.getSlotDurationMinutes());
        settingsToUpdate.setOpeningTime(settingsDto.getOpeningTime());
        settingsToUpdate.setClosingTime(settingsDto.getClosingTime());
        settingsToUpdate.setClosedDays(settingsDto.getClosedDays());
        settingsToUpdate.setReservationAdvanceDays(settingsDto.getReservationAdvanceDays());
        settingsToUpdate.setMinAdvanceMinutes(settingsDto.getMinAdvanceMinutes());
        settingsToUpdate.setCancellationLimitHours(settingsDto.getCancellationLimitHours());




        return settingsRepository.save(settingsToUpdate);
    }




    public void deleteSettings(int id) throws NotFoundException{
        RestaurantSettings settingsToDelete = getSettings(id);

        settingsRepository.delete(settingsToDelete);
    }
}
